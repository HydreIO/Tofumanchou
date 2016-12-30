package fr.aresrpg.tofumanchou.domain.data;

import fr.aresrpg.tofumanchou.domain.data.enums.Spells;

/**
 * 
 * @since
 */
public interface Spell {
	Spells getType();

	int getSpellLvl();

	int getPosition();

	int getRelance();

	int getMinPo();

	int getMaxPo();

}
