package fabe0940.ai;

import fabe0940.ai.Constants;
import fabe0940.ai.util.Printable;
import fabe0940.ai.util.Point;
import fabe0940.ai.data.Board;
import fabe0940.ai.data.BoardState;
import fabe0940.ai.data.CellState;
import fabe0940.ai.data.TurnState;
import fabe0940.ai.search.Minimax;
import fabe0940.ai.search.CellEvaluator;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Game implements Printable {
	private Board board;
	private TurnState turnState;
	private Minimax minimax;
	private BufferedReader stdin;

	public static TurnState nextTurn(TurnState state) {
		TurnState next;

		if (state == TurnState.RED_TURN) {
			next = TurnState.BLACK_TURN;
		} else {
			next = TurnState.RED_TURN;
		}

		return next;
	}

	private void printHeader() {
		int i;

		System.out.format("\n");
		for (i = 1; i <= Board.dimensions.x; i++) {
			System.out.format("%d ", i);
		}
		System.out.format("\n");

		return;
	}

	public Game() {
		board = new Board();
		turnState = TurnState.RED_TURN;
		minimax = new Minimax(new CellEvaluator());
		stdin = new BufferedReader(new InputStreamReader(System.in));

		return;
	}

	public void print() {
		printHeader();

		board.print();

		return;
	}

	public void playerTurn() {
		int move;
		boolean validMove;

		validMove = false;

		while (!validMove) {
			move = 0;

			while (move < 1 || move > Board.dimensions.x) {
				switch (turnState) {
					case RED_TURN:
						System.out.format("red's move: ");
						break;
					case BLACK_TURN:
						System.out.format("black's move: ");
						break;
				}

				try {
					move = Integer.parseInt(stdin.readLine());
				} catch (Exception e) {
					System.out.format("Unable to parse input\n");
				}
			}

			board.setState(board.move(turnState, move - 1, true));

			switch (board.getState()) {
				case INVALID:
					validMove = false;
					System.out.format("Invalid move\n");
					break;
				case VALID:
					turnState = nextTurn(turnState);
					validMove = true;
					break;
				case DRAW:
				case RED_WIN:
				case BLACK_WIN:
					turnState = TurnState.GAME_OVER;
					validMove = true;
					break;
			}
		}

		return;
	}

	public void aiTurn() {
		board.setState(
			board.move(turnState, minimax.search(board, turnState), true)
		);

		switch (board.getState()) {
			case INVALID:
				System.err.format("Invalid AI move\n");
				System.exit(1);
				break;
			case VALID:
				turnState = nextTurn(turnState);
				break;
			case DRAW:
			case RED_WIN:
			case BLACK_WIN:
				turnState = TurnState.GAME_OVER;
				break;
		}

		return;
	}

	public Board getBoard() {
		return board;
	}

	public TurnState getTurnState() {
		return turnState;
	}

	public BoardState getBoardState() {
		return board.getState();
	}
}
