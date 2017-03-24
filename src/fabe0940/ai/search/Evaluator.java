package fabe0940.ai.search;

import fabe0940.ai.data.Board;
import fabe0940.ai.data.TurnState;

public interface Evaluator {
	public int evaluate(Board b, TurnState state);
}
