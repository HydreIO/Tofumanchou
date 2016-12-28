package fr.aresrpg.tofumanchou.domain.data.item;

import fr.aresrpg.dofus.structures.game.Effect;

/**
 * 
 * @since
 */
public interface Item {

	long getUUID();

	String getName();

	String getDesc();

	int getTypeId();

	int getAmount();

	int getPosition();

	Effect[] getEffects();

	int getPrice();

	int getSkin();

	int getRemainingHours();

}
