package fr.aresrpg.tofumanchou.domain.data.enums;

/**
 * 
 * @since
 */
public enum Genre {
	MALE,
	FEMALE;

	public static Genre valueOf(int code) {
		if (code == 0) return MALE;
		else if (code == 1) return FEMALE;
		throw new NullPointerException("The code " + code + " is an unknow genre !");
	}
}
