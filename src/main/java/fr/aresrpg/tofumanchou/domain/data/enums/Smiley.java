package fr.aresrpg.tofumanchou.domain.data.enums;

import fr.aresrpg.commons.domain.util.Randoms;

/**
 * 
 * @since
 */
public enum Smiley {

	CONTENT,
	PAS_CONTENT,
	BLESSE,
	TIRE_LANQUE,
	YEUX_ROUGE,
	PLEURE_RIRE,
	APEURE,
	BOUCHE_W,
	CRANE,
	ETONE,
	AMOUREUX,
	PLEURE,
	SUEUR,
	X_X,
	MOQUEUR;

	/**
	 * @return the id
	 */
	public int getId() {
		return ordinal() + 1;
	}

	public static Smiley getRandomSmiley() {
		return values()[Randoms.nextInt(values().length)];
	}

	public static Smiley getRandomBadSmiley() {
		Smiley[] s = { PAS_CONTENT, BLESSE, YEUX_ROUGE, APEURE, MOQUEUR, BOUCHE_W, CRANE, ETONE, SUEUR };
		return s[Randoms.nextInt(s.length)];
	}

	public static Smiley getRandomTrollSmiley() {
		Smiley[] s = { TIRE_LANQUE, PLEURE_RIRE, ETONE, AMOUREUX, MOQUEUR, APEURE, X_X };
		return s[Randoms.nextInt(s.length)];
	}

	public static Smiley valueOf(int code) {
		for (Smiley e : values())
			if (e.getId() == code) return e;
		return null;
	}

}
