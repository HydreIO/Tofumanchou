/*******************************************************************************
 * BotFather (C) - Dofus 1.29
 * This class is part of an AresRPG Project.
 *
 * @author Sceat {@literal <sceat@aresrpg.fr>}
 * 
 *         Created 2016
 *******************************************************************************/
package fr.aresrpg.tofumanchou.domain.data.enums;

/**
 * 
 * @since
 */
public enum Classe {

	SADIDA(100, 101, 102, 103),
	OSAMODAS(20, 21, 22, 23),
	ENUTROF(30, 31, 32, 33),
	SRAM(40, 41, 42, 43),
	XELOR(50, 51, 52, 53),
	PANDAWA(120, 121, 122, 123),
	ECAFLIP(60, 61, 62, 63),
	ENIRIPSA(70, 71, 72, 73),
	IOP(80, 81, 82, 83),
	CRA(90, 91, 92, 93),
	FECA(10, 11, 12, 13),
	SACRIEUR(110, 111, 112, 113);

	private int male, female, creature, grave;

	private Classe(int male, int female, int creature, int grave) {
		this.male = male;
		this.female = female;
		this.creature = creature;
		this.grave = grave;
	}

	public static Classe getClasse(int sprite) {
		for (Classe c : values())
			if (c.hasSprite(sprite)) return c;
		return null;
	}

	public boolean hasSprite(int sprite) {
		return male == sprite || female == sprite || creature == sprite || grave == sprite;
	}

}
