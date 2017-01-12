package fr.aresrpg.tofumanchou.domain.data.enums;

/**
 * 
 * @since
 */
public enum City {

	BONTA,
	BRAKMAR;

	public static City getWithY(int y) {
		if (y < -40) return BONTA;
		return BRAKMAR;
	}

	public static City valueOf(int id) {
		if (id == 1) return BONTA;
		if (id == 2) return BRAKMAR;
		return null;
	}
}
