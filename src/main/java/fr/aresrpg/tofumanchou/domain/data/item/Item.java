package fr.aresrpg.tofumanchou.domain.data.item;

import fr.aresrpg.dofus.structures.game.Effect;
import fr.aresrpg.dofus.structures.item.ItemCategory;

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

	int getPods();

	ItemCategory getCategory();

	int getPosition();

	Effect[] getEffects();

	int getPrice();

	int getSkin();

	int getRemainingHours();

	boolean isStackableWith(Item i);

	void setAmount(int i);

	String showInfos();

	String showInfos(int amount);

}
