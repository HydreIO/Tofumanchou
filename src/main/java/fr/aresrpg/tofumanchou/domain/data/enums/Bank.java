package fr.aresrpg.tofumanchou.domain.data.enums;

import java.awt.Point;

/**
 * 
 * @since
 */
public enum Bank {
	ASTRUB(4, -16),
	SUFOKIA(14, 25),
	AMAKNA(2, -2);
	private int x, y;

	private Bank(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	public Point getCoords() {
		return new Point(x, y);
	}
}
