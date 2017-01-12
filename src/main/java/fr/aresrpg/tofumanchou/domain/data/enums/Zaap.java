package fr.aresrpg.tofumanchou.domain.data.enums;

import fr.aresrpg.tofumanchou.infra.data.ManchouCell;

import java.awt.Point;
import java.util.Arrays;

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
	MILIFUTAIE(186, 528, new Point(5, 7)),
	TODO_1(268, 9454, null),
	TODO_2(126, 951, null),
	TODO_3(193, 164, null),
	TODO_4(340, 1158, null),
	TODO_5(249, 8037, null),
	TODO_6(310, 8437, null),
	TODO_7(223, 8088, null),
	TODO_8(358, 8125, null),
	TODO_9(207, 8163, null),
	TODO_10(269, 10643, null),
	TODO_11(326, 11170, null),
	TODO_12(150, 1841, null),
	TODO_13(212, 844, null),
	TODO_14(401, 11210, null),
	TODO_15(186, 3022, null),
	TODO_16(253, 6855, null),
	TODO_17(104, 6137, null),
	TODO_18(354, 4739, null),
	TODO_19(253, 8785, null),
	TODO_20(200, 2191, null),
	TODO_21(199, 10297, null),
	TODO_22(282, 10349, null),
	TODO_23(138, 10304, null),
	TODO_24(195, 10317, null),
	;

	private int cellId;
	private int mapId;
	private Point coords;

	private Zaap(int cell, int map, Point coords) {
		this.cellId = cell;
		this.coords = coords;
		this.mapId = map;
	}

	public static Zaap getWithMap(int mapid) {
		for (Zaap z : values())
			if (z.getMapId() == mapid) return z;
		return null;
	}

	public static int[] getMapsIds() {
		return Arrays.stream(values()).mapToInt(Zaap::getMapId).toArray();
	}

	public int findCellId(ManchouCell[] cells) {
		for (ManchouCell c : cells)
			if (c.isZaap()) return c.getId();
		return -1;
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
	public int getDestCellId() {
		return cellId;
	}

	/**
	 * @return the mapId
	 */
	public int getMapId() {
		return mapId;
	}

}
