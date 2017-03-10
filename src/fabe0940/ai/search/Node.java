package fabe0940.ai.search;

import fabe0940.ai.data.Board;
import java.util.List;
import java.util.ArrayList;

public class Node {
	public int value;
	public int move;
	public int alpha;
	public int beta;
	public Board board;
	public List<Node> children;

	public Node(Board b) {
		board = b;
		value = 0;
		move = 0;
		children = new ArrayList<Node>();

		return;
	}
}
