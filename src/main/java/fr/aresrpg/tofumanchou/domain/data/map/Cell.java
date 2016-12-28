package fr.aresrpg.tofumanchou.domain.data.map;

import fr.aresrpg.dofus.structures.item.Interractable;
import fr.aresrpg.tofumanchou.domain.data.entity.mob.Mob;
import fr.aresrpg.tofumanchou.domain.data.entity.mob.MobGroup;

/**
 * 
 * @since
 */
public interface Cell {

	int getId();

	int getX();

	int getY();

	Interractable getInterractable();

	boolean isRessourceSpawned();

	boolean isWalkeable();

	boolean isTeleporter();

	Mob getMobOn();

	MobGroup getMobGoupOn();

}
