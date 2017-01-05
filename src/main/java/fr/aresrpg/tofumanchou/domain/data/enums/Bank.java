package fr.aresrpg.tofumanchou.domain.data.enums;

import java.awt.Point;

/**
 * 
 * @since
 */
public enum Bank {
	ASTRUB(4, -16, 142),
	SUFOKIA(14, 25, 269),
	AMAKNA(2, -2, 238);
	private int x, y, cellid;

	private Bank(int x, int y, int cellid) {
		this.x = x;
		this.y = y;
		this.cellid = cellid;
	}

	/**
	 * @return the cellid
	 */
	public int getCellid() {
		return cellid;
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
