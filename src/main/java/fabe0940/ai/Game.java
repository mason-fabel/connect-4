package fabe0940.ai;

import fabe0940.ai.Constants;
import fabe0940.ai.util.Printable;
import fabe0940.ai.util.Point;
import fabe0940.ai.data.Board;
import fabe0940.ai.data.BoardState;
import fabe0940.ai.data.CellState;
import fabe0940.ai.data.TurnState;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Game implements Printable {
	private Board board;
	private TurnState turnState;
	private BufferedReader stdin;

	private void printHeader() {
		int i;

		System.out.format("\n");
		for (i = 1; i <= Board.dimensions.x; i++) {
			System.out.format("%d ", i);
		}
		System.out.format("\n");

		return;
	}

	private TurnState nextTurn(TurnState state) {
		return state == TurnState.RED_TURN ? TurnState.BLACK_TURN : TurnState.RED_TURN;
	}

	private boolean checkGoal(Board b, TurnState state, Point move) {
		boolean checkUp;
		boolean checkDown;
		boolean checkLeft;
		boolean checkRight;
		boolean result;
		boolean valid;
		int count;
		CellState goal;

		result = false;
		goal = CellState.EMPTY;

		switch (state) {
			case RED_TURN:
				goal = CellState.RED;
				break;
			case BLACK_TURN:
				goal = CellState.BLACK;
				break;
		}

		checkLeft = move.x >= Math.floor(Board.dimensions.x / 2.0);
		checkRight = move.x < Math.ceil(Board.dimensions.x / 2.0);
		checkUp = move.y < Math.ceil(Board.dimensions.y / 2.0);
		checkDown = move.y >= Math.floor(Board.dimensions.y / 2.0);

		if (checkLeft) {
			if (Constants.DEBUG) {
				System.out.format("Checking left: ");
				move.print();
				System.out.format("\n");
			}

			valid = true;
			for (count = 1; count < 4; count++) {
				if (b.getCell(move.x - count, move.y) != goal) valid = false;
			}

			result = result || valid;
		}

		if (checkRight) {
			if (Constants.DEBUG) {
				System.out.format("Checking right: ");
				move.print();
				System.out.format("\n");
			}

			valid = true;
			for (count = 1; count < 4; count++) {
				if (b.getCell(move.x + count, move.y) != goal) valid = false;
			}

			result = result || valid;
		}

		if (checkUp) {
			if (Constants.DEBUG) {
				System.out.format("Checking up: ");
				move.print();
				System.out.format("\n");
			}

			valid = true;
			for (count = 1; count < 4; count++) {
				if (b.getCell(move.x, move.y + count) != goal) valid = false;
			}

			result = result || valid;
		}

		if (checkLeft && checkUp) {
			if (Constants.DEBUG) {
				System.out.format("Checking up/left: ");
				move.print();
				System.out.format("\n");
			}

			valid = true;
			for (count = 1; count < 4; count++) {
				if (b.getCell(move.x - count, move.y + count) != goal) valid = false;
			}

			result = result || valid;
		}

		if (checkRight && checkUp) {
			if (Constants.DEBUG) {
				System.out.format("Checking up/right: ");
				move.print();
				System.out.format("\n");
			}

			valid = true;
			for (count = 1; count < 4; count++) {
				if (b.getCell(move.x + count, move.y + count) != goal) valid = false;
			}

			result = result || valid;
		}

		if (checkDown) {
			if (Constants.DEBUG) {
				System.out.format("Checking down: ");
				move.print();
				System.out.format("\n");
			}

			valid = true;
			for (count = 1; count < 4; count++) {
				if (b.getCell(move.x, move.y - count) != goal) valid = false;
			}

			result = result || valid;
		}

		if (checkLeft && checkDown) {
			if (Constants.DEBUG) {
				System.out.format("Checking down/left: ");
				move.print();
				System.out.format("\n");
			}

			valid = true;
			for (count = 1; count < 4; count++) {
				if (b.getCell(move.x - count, move.y - count) != goal) valid = false;
			}

			result = result || valid;
		}

		if (checkRight && checkDown) {
			if (Constants.DEBUG) {
				System.out.format("Checking down/right: ");
				move.print();
				System.out.format("\n");
			}

			valid = true;
			for (count = 1; count < 4; count++) {
				if (b.getCell(move.x + count, move.y - count) != goal) valid = false;
			}

			result = result || valid;
		}

		return result;
	}

	private boolean checkDraw(Board b) {
		boolean result;
		int x;
		int y;

		result = true;

		for (x = 0; x < Board.dimensions.x; x++) {
			for (y = 0; y < Board.dimensions.y; y++) {
				if (b.getCell(x, y) == CellState.EMPTY) result = false;
			}
		}

		return result;
	}

	private BoardState move(Board b, TurnState state, int col) {
		int y;
		BoardState result;
		Point move;

		result = BoardState.INVALID;

		if (col < 0 || col >= Board.dimensions.x) return BoardState.INVALID;

		move = new Point();
		move.x = col;
		move.y = -1;

		for (y = 0; y < Board.dimensions.y; y++) {
			if (b.getCell(move.x, y) == CellState.EMPTY) {
				move.y = y;
				break;
			}
		}

		if (move.y < 0) return BoardState.INVALID;

		switch (state) {
			case RED_TURN:
				b.setCell(CellState.RED, move);
				break;
			case BLACK_TURN:
				b.setCell(CellState.BLACK, move);
				break;
		}

		if (checkGoal(b, state, move)) {
			switch (state) {
				case RED_TURN:
					result = BoardState.RED_WIN;
					break;
				case BLACK_TURN:
					result = BoardState.BLACK_WIN;
					break;
			}
		} else if (checkDraw(b)) {
			result = BoardState.DRAW;
		} else {
			result = BoardState.VALID;
		}

		return result;
	}

	public Game() {
		board = new Board();
		turnState = TurnState.RED_TURN;
		stdin = new BufferedReader(new InputStreamReader(System.in));

		return;
	}

	public void print() {
		printHeader();

		board.print();

		return;
	}

	public void turn() {
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

			board.setState(move(board, turnState, move - 1));

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
