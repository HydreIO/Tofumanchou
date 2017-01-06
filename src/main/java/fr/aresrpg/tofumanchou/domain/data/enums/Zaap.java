package fr.aresrpg.tofumanchou.domain.data.enums;

import java.awt.Point;

/**
 * 
 * @since
 */
public enum Zaap {

	ASTRUB(311, 7411, new Point(4, -19)),
	BONTA(170, 4263, new Point(-32, -58)),
	BRAKMAR(597, 5295, new Point(-25, 40)),
	SCARAFEUILLE(323, 1242, new Point(-1, 24)),
	PLAINE_ROCHEUSE(165, 3250, new Point(-14, -47)),
	BORD_FORET_MALEFIQUE(295, 935, new Point(-1, 13)),
	TAINELA(268, 6954, new Point(1, -32)),
	MILIFUTAIE(186, 528, new Point(5, 7));

	private int cellId;
	private int mapId;
	private Point coords;

	private Zaap(int cell, int map, Point coords) {
		this.cellId = cell;
		this.coords = coords;
		this.mapId = map;
	}

	/**
	 * @return the coords
	 */
	public Point getCoords() {
		return coords;
	}

	/**
	 * @return the cellId
	 */
	public int getCellId() {
		return cellId;
	}

	/**
	 * @return the mapId
	 */
	public int getMapId() {
		return mapId;
	}

}
