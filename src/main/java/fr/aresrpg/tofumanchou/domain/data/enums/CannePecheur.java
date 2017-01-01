/*******************************************************************************
 * BotFather (C) - Dofus 1.29
 * This class is part of an AresRPG Project.
 *
 * @author Sceat {@literal <sceat@aresrpg.fr>}
 *  
 * Created 2016
 *******************************************************************************/
package fr.aresrpg.tofumanchou.domain.data.enums;

/**
 * 
 * @since
 */
public enum CannePecheur {

	APPRENTI(1),
	COURTE(1),
	STANDARD(10),
	CUBIQUE(30),
	TRICOTER(30),
	GRANDE_CANNE(40),
	CHO(60),
	BATON_AMOUR(60),
	TELESCOPIQUE(70),
	GRANDE_PERCHE(100),
	CANNE_HARPON(100);

	private int lvl;

	private CannePecheur(int lvlRequis) {
		this.lvl = lvlRequis;
	}

	/**
	 * @return the lvl
	 */
	public int getLvl() {
		return lvl;
	}

	public static CannePecheur getBestForLvl(int lvl) {
		if (lvl > 99) return CANNE_HARPON;
		if (lvl > 69) return TELESCOPIQUE;
		if (lvl > 59) return BATON_AMOUR;
		if (lvl > 39) return GRANDE_CANNE;
		if (lvl > 29) return TRICOTER;
		if (lvl > 9) return STANDARD;
		if (lvl > 0) return COURTE;
		return APPRENTI;
	}

}
