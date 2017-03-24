package fabe0940.ai;

import fabe0940.ai.Game;
import fabe0940.ai.data.TurnState;

public class Connect4 {
	public static void main(String[] args) {
		int i;
		Game g;
		TurnState aiTurn;

		aiTurn = TurnState.NONE;

		switch (args.length) {
			case 0:
				aiTurn = TurnState.NONE;
				break;
			case 1:
				switch (Integer.parseInt(args[0])) {
					case 0:
						System.out.format("You will go second as black.\n");
						aiTurn = TurnState.RED_TURN;
						break;
					case 1:
						System.out.format("You will go first as red.\n");
						aiTurn = TurnState.BLACK_TURN;
						break;
					default:
						System.err.format("Unable to parse argument: %s\n",
							args[0]);
						System.exit(1);
				}
				break;
			default:
				for (i = 1; i < args.length; i++) {
					System.err.format("Unknown argument: %s\n", args[i]);
				}
				System.exit(1);
		}

		System.out.format("Red is X's, black is O's.\n");

		g = new Game();

		while (g.getTurnState() != TurnState.GAME_OVER) {
			g.print();

			if (g.getTurnState() == aiTurn) {
				g.aiTurn();
			} else {
				g.playerTurn();
			}
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
