package fr.aresrpg.tofumanchou.domain.data.enums;

/**
 * 
 * @since
 */
public enum Zaap {

	ASTRUB(311, 7411),
	BONTA(170, 4263),
	BRAKMAR(597, 5295),
	SCARAFEUILLE(323, 1242),
	PLAINE_ROCHEUSE(165, 3250),
	BORD_FORET_MALEFIQUE(295, 935),
	TAINELA(268, 6954),
	MILIFUTAIE(186, 528);

	private int cellId;
	private int mapId;

	private Zaap(int cell, int map) {
		this.cellId = cell;
		this.mapId = map;
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
