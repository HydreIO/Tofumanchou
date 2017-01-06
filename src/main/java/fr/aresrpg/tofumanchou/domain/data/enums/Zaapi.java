package fr.aresrpg.tofumanchou.domain.data.enums;

import java.awt.Point;

/**
 * 
 * @since
 */
public enum Zaapi {

	BONTA_BANQUE(456, 4308, City.BONTA, new Point(-29, -58)),
	BONTA_BAGRUTTE(267, 4218, City.BONTA, new Point(-32, -60)),
	BONTA_HDV_CHASSEUR(131, 4287, City.BONTA, new Point(-30, -50)),
	BONTA_HDV_BOULANGER(547, 2221, City.BONTA, new Point(-26, -52)),
	BONTA_ZAAP(135, 4263, City.BONTA, new Point(-32, -58)),
	BONTA_MILICE(254, 6159, City.BONTA, new Point(-33, -57));

	private int cellid, mapid;
	private City city;
	private Point coords;

	private Zaapi(int cellid, int mapid, City city, Point coords) {
		this.cellid = cellid;
		this.coords = coords;
		this.mapid = mapid;
		this.city = city;
	}

	/**
	 * @return the coords
	 */
	public Point getCoords() {
		return coords;
	}

	/**
	 * @return the mapid
	 */
	public int getMapid() {
		return mapid;
	}

	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * @return the cellid
	 */
	public int getCellid() {
		return cellid;
	}
}
