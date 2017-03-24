package fabe0940.ai.search;

import fabe0940.ai.Constants;
import fabe0940.ai.util.Point;
import fabe0940.ai.data.Board;
import fabe0940.ai.data.BoardState;
import fabe0940.ai.data.CellState;
import fabe0940.ai.data.TurnState;

public class Minimax {
	private static final int MAX_DEPTH = 8;
	private Evaluator eval;

	private void printTree(Node n, int depth) {
		int i;

		for (i = 0; i < depth; i++) System.out.format("|   ");
		System.out.format("node: (move, value) = (%d, %d)\n",
			n.move, n.value);

		for (Node child : n.children) {
			printTree(child, depth + 1);
		}

		return;
	}

	private TurnState opponent(TurnState player) {
		TurnState opponent;

		opponent = TurnState.GAME_OVER;

		switch (player) {
			case RED_TURN:
				opponent = TurnState.BLACK_TURN;
				break;
			case BLACK_TURN:
				opponent = TurnState.RED_TURN;
				break;
		}

		return opponent;
	}

	private int _search(Node node, TurnState player, int alpha, int beta,
		int depth
	) {
		boolean isDraw;
		boolean isLoss;
		boolean isWin;
		int i;
		int x;
		int y;
		int value;
		int childValue;
		Node child;
		TurnState currentPlayer;

		value = 0;

		isDraw = node.board.checkDraw();
		isLoss = false;
		isWin = false;

		for (x = 0; x < Board.dimensions.x; x++) {
			for (y = Board.dimensions.y - 1; y >= 0; y--) {
				if (node.board.getCell(x, y) != CellState.EMPTY) {
					currentPlayer = TurnState.GAME_OVER;

					switch (node.board.getCell(x, y)) {
						case RED:
							currentPlayer = TurnState.RED_TURN;
							break;
						case BLACK:
							currentPlayer = TurnState.BLACK_TURN;
							break;
					}

					if (currentPlayer == player) {
						isWin = isWin || node.board.checkGoal(currentPlayer,
							new Point(x, y));
					} else {
						isLoss = isLoss || node.board.checkGoal(currentPlayer,
							new Point(x, y));
					}

					break;
				}
			}
		}

		for (x = 0; x < Board.dimensions.x; x++) {
			for (y = 0; y < Board.dimensions.y; y++) {
			}
		}

		if (isLoss) {
			value = -1000000;
		} else if (isDraw) {
			value = 0;
		} else if (isWin) {
			value = 1000000;
		} else if (depth == MAX_DEPTH) {
			value = eval.evaluate(node.board,
				depth % 2 == 0 ? player : opponent(player));
		} else {
			if (depth % 2 == 0) {
				/* max */
				value = Integer.MIN_VALUE;

				for (i = 0; i < Board.dimensions.x; i++) {
					child = new Node(new Board(node.board));
					if (child.board.move(player, i) == BoardState.INVALID) {
						continue;
					}

					node.children.add(child);

					childValue = _search(child, player, alpha, beta,
						depth + 1);

					if (childValue > value) {
						value = childValue;
						node.move = i;
					}

					alpha = childValue > alpha ? childValue : alpha;
					if (beta <= alpha) break;
				}
			} else {
				/* min */
				value = Integer.MAX_VALUE;

				for (i = 0; i < Board.dimensions.x; i++) {
					child = new Node(new Board(node.board));
					if (child.board.move(opponent(player), i)
						== BoardState.INVALID)
					{
						continue;
					}

					node.children.add(child);

					childValue = _search(child, player, alpha, beta,
						 depth + 1);

					if (childValue < value) {
						value = childValue;
						node.move = i;
					}

					beta = childValue < beta ? childValue : beta;
					if (beta <= alpha) break;
				}
			}
		}

		node.value = value;

		return value;
	}

	public Minimax(Evaluator e) {
		eval = e;

		return;
	}

	public int search(Board b, TurnState player) {
		int max;
		Node root;
		Node move;

		root = new Node(b);

		max = _search(root, player, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);

		if (Constants.DEBUG) printTree(root, 0);

		if (Constants.LOG) {
			System.out.format("search max: %d\n", max);
			System.out.format("search move: %d\n", root.move);
		}

		return root.move;
	}
}
