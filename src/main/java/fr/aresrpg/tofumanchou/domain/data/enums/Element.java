package fr.aresrpg.tofumanchou.domain.data.enums;

/**
 * 
 * @since
 */
public enum Element {

	NEUTRE('N'),
	TERRE('E'),
	FEU('F'),
	AIR('A'),
	EAU('W');

	private char code;

	private Element(char code) {
		this.code = code;
	}

	public char getCode() {
		return code;
	}

	public static Element valueOf(char code) {
		for (Element e : values())
			if (e.getCode() == code) return e;
		return null;
	}

}
