package fabe0940.ai.util;

import fabe0940.ai.util.Printable;

public class Point implements Printable {
	public int x;
	public int y;

	public Point() {
		x = 0;
		y = 0;

		return;
	}

	public Point(int x_init, int y_init) {
		x = x_init;
		y = y_init;

		return;
	}

	public Point(Point p) {
		x = p.x;
		y = p.y;

		return;
	}

	public void print() {
		System.out.format("(%d, %d)", x, y);

		return;
	}
}
