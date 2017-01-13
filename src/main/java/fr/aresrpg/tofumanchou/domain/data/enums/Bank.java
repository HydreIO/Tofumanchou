package fr.aresrpg.tofumanchou.domain.data.enums;

import java.awt.Point;

/**
 * 
 * @since
 */
public enum Bank {
	ASTRUB(4, -16, 7549),
	SUFOKIA(14, 25, -1),
	BONTA(-32, -58, 5703),
	AMAKNA(2, -2, -1);
	private int x, y, mapId;

	private Bank(int x, int y, int mapId) {
		this.x = x;
		this.y = y;
		this.mapId = mapId;
	}

	/**
	 * @return the cellid
	 */
	public int getMapId() {
		return mapId;
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
