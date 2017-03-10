package fabe0940.ai.data;

import fabe0940.ai.Constants;
import fabe0940.ai.Game;
import fabe0940.ai.util.Point;
import fabe0940.ai.util.Printable;
import fabe0940.ai.data.BoardState;
import fabe0940.ai.data.CellState;

public class Board implements Printable {
	public static final Point dimensions = new Point(7, 6);

	private CellState[][] cells;
	private BoardState state;

	public Board() {
		int x;
		int y;

		cells = new CellState[dimensions.x][dimensions.y];
		state = BoardState.VALID;

		for (x = 0; x < dimensions.x; x++) {
			for (y = 0; y < dimensions.y; y++) {
				setCell(CellState.EMPTY, x, y);
			}
		}

		return;
	}

	public Board(Board b) {
		int x;
		int y;

		cells = new CellState[dimensions.x][dimensions.y];
		state = b.getState();

		for (x = 0; x < dimensions.x; x++) {
			for (y = 0; y < dimensions.y; y++) {
				setCell(b.getCell(x, y), x, y);
			}
		}

		return;
	}

	public void print() {
		int x;
		int y;

		for (y = dimensions.y - 1; y >= 0; y--) {
			for (x = 0; x < dimensions.x; x++) {
				switch (getCell(x, y)) {
					case EMPTY:
						System.out.format("%c ", '.');
						break;
					case RED:
						System.out.format("%c ", 'X');
						break;
					case BLACK:
						System.out.format("%c ", 'O');
						break;
				}
			}
			System.out.format("\n");
		}

		return;
	}

	public boolean checkGoal(TurnState state, Point move) {
		boolean valid;
		boolean result;
		int i;
		int offset;
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

		/* Check for vertical win */
		valid = true;
		for (i = 0; i < 4; i++) {
			if (move.y - i < 0 || getCell(move.x, move.y - i) != goal) {
				valid = false;
				break;
			}
		}
		result = result || valid;

		/* Check for horizontal win */
		for (offset = 0; offset < 4; offset++) {
			valid = true;
			for (i = 0; i < 4; i++) {
				if (move.x - offset + i < 0
					|| move.x - offset + i >= Board.dimensions.x
					|| getCell(move.x - offset + i, move.y) != goal
				) {
					valid = false;
					break;
				}
			}
			result = result || valid;
		}

		/* Check / diagonal win */
		for (offset = 0; offset < 4; offset++) {
			valid = true;
			for (i = 0; i < 4; i++) {
				if (move.x - offset + i < 0 || move.y - offset + i < 0
					|| move.x - offset + i >= Board.dimensions.x
					|| move.y - offset + i >= Board.dimensions.y
					|| getCell(move.x - offset + i, move.y - offset + i)
						!= goal
				) {
					valid = false;
					break;
				}
			}
			result = result || valid;
		}

		/* Check \ diagonal win */
		for (offset = 0; offset < 4; offset++) {
			valid = true;
			for (i = 0; i < 4; i++) {
				if (move.x + offset - i < 0 || move.y - offset + i < 0
					|| move.x + offset - i >= Board.dimensions.x
					|| move.y - offset + i >= Board.dimensions.y
					|| getCell(move.x + offset - i, move.y - offset + i)
						!= goal
				) {
					valid = false;
					break;
				}
			}
			result = result || valid;
		}

		return result;
	}

	public boolean checkDraw() {
		boolean result;
		int x;
		int y;

		result = true;

		for (x = 0; x < Board.dimensions.x; x++) {
			for (y = 0; y < Board.dimensions.y; y++) {
				if (getCell(x, y) == CellState.EMPTY) result = false;
			}
		}

		return result;
	}

	public BoardState move(TurnState state, int col, boolean debug) {
		int y;
		BoardState result;
		Point move;

		result = BoardState.INVALID;

		if (col < 0 || col >= Board.dimensions.x) return BoardState.INVALID;

		move = new Point();
		move.x = col;
		move.y = -1;

		for (y = 0; y < Board.dimensions.y; y++) {
			if (getCell(move.x, y) == CellState.EMPTY) {
				move.y = y;
				break;
			}
		}

		if (move.y < 0) return BoardState.INVALID;

		switch (state) {
			case RED_TURN:
				if (debug) {
					System.out.format("Placing red piece at: ");
					move.print();
					System.out.format("\n");
				}
				setCell(CellState.RED, move);
				break;
			case BLACK_TURN:
				if (debug) {
					System.out.format("Placing black piece at: ");
					move.print();
					System.out.format("\n");
				}
				setCell(CellState.BLACK, move);
				break;
		}

		if (checkGoal(state, move)) {
			switch (state) {
				case RED_TURN:
					result = BoardState.RED_WIN;
					break;
				case BLACK_TURN:
					result = BoardState.BLACK_WIN;
					break;
			}
		} else if (checkDraw()) {
			result = BoardState.DRAW;
		} else {
			result = BoardState.VALID;
		}

		return result;
	}

	public BoardState move(TurnState state, int col) {
		return move(state, col, false);
	}

	public BoardState getState() {
		return state;
	}

	public CellState getCell(Point p) {
		return getCell(p.x, p.y);
	}

	public CellState getCell(int x, int y) {
		return cells[x][y];
	}

	public void setState(BoardState s) {
		state = s;

		return;
	}

	public void setCell(CellState state, Point p) {
		setCell(state, p.x, p.y);

		return;
	}

	public void setCell(CellState state, int x, int y) {
		cells[x][y] = state;

		return;
	}
}
