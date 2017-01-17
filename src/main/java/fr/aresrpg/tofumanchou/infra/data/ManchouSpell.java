package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.tofumanchou.domain.data.Spell;
import fr.aresrpg.tofumanchou.domain.data.enums.Spells;

/**
 * 
 * @since
 */
public class ManchouSpell implements Spell {

	private final Spells type;
	private int spellLvl = 1;
	private int position;
	private int relance;
	private int minPo;
	private int maxPo;

	public ManchouSpell(Spells type) {
		this(type, 1, -1);
	}

	public ManchouSpell(Spells type, int lvl, int position) {
		this.type = type;
		this.spellLvl = lvl;
		this.position = position;
	}

	/**
	 * @return the relance
	 */
	public int getRelance() {
		return relance;
	}

	/**
	 * @param minPo
	 *            the minPo to set
	 */
	public void setMinPo(int minPo) {
		this.minPo = minPo;
	}

	/**
	 * @param maxPo
	 *            the maxPo to set
	 */
	public void setMaxPo(int maxPo) {
		this.maxPo = maxPo;
	}

	/**
	 * @param relance
	 *            the relance to set
	 */
	public void setRelance(int relance) {
		this.relance = relance;
	}

	public void decrementRelance() {
		this.relance--;
		if (this.relance < 0) this.relance = 0;
	}

	/**
	 * @return the minPo
	 */
	public int getMinPo() {
		return 0;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the maxPo
	 */
	public int getMaxPo() {
		return 11;
	}

	/**
	 * @param spellLvl
	 *            the spellLvl to set
	 */
	public void setSpellLvl(int spellLvl) {
		this.spellLvl = spellLvl;
	}

	/**
	 * @return the type
	 */
	public Spells getType() {
		return type;
	}

	/**
	 * @return the spellLvl
	 */
	public int getSpellLvl() {
		return spellLvl;
	}

	/**
	 * @param lvl
	 *            the lvl to set
	 */
	public void setLvl(int lvl) {
		this.spellLvl = lvl;
	}

	@Override
	public String toString() {
		return "Spell [type=" + type + ", spellLvl=" + spellLvl + ", position=" + position + "]";
	}

}
