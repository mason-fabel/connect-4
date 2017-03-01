package fabe0940.ai;

import fabe0940.ai.Game;
import fabe0940.ai.data.TurnState;

public class Connect4 {
	public static void main(String[] args) {
		Game g;

		g = new Game();

		while (g.getTurnState() != TurnState.GAME_OVER) {
			g.print();
			g.turn();
		}

		g.print();
		switch (g.getBoardState()) {
			case RED_WIN:
				System.out.format("Red wins\n");
				break;
			case BLACK_WIN:
				System.out.format("Black wins\n");
				break;
			case DRAW:
				System.out.format("It's a draw\n");
				break;
		}

		return;
	}
}
