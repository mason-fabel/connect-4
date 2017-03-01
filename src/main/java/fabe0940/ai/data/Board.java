package fabe0940.ai.data;

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
						System.out.format("%c ", 'R');
						break;
					case BLACK:
						System.out.format("%c ", 'B');
						break;
				}
			}
			System.out.format("\n");
		}

		return;
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
