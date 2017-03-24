package fabe0940.ai.search;

import fabe0940.ai.data.Board;
import fabe0940.ai.data.CellState;
import fabe0940.ai.data.TurnState;

public class CellEvaluator implements Evaluator {
	private static final int[][] weights = {
		{ 3, 4, 5, 5, 4, 3 },
		{ 4, 6, 8, 8, 6, 4 },
		{ 5, 8, 11, 11, 8, 5},
		{ 7, 10, 13, 13, 10, 7 },
		{ 5, 8, 11, 11, 8, 5},
		{ 4, 6, 8, 8, 6, 4 },
		{ 3, 4, 5, 5, 4, 3 },
	};

	public CellEvaluator() {
		return;
	}

	public int evaluate(Board b, TurnState state) {
		int x;
		int y;
		int value;
		CellState owned;

		owned = CellState.EMPTY;

		switch (state) {
			case RED_TURN:
				owned = CellState.RED;
				break;
			case BLACK_TURN:
				owned = CellState.BLACK;
				break;
		}

		value = 0;

		for (x = 0; x < Board.dimensions.x; x++) {
			for (y = 0; y < Board.dimensions.y; y++) {
				if (b.getCell(x, y) == owned) value += weights[x][y];
			}
		}

		return value;
	}
}
