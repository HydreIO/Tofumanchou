package fr.aresrpg.tofumanchou.domain.data.map;

import fr.aresrpg.dofus.structures.item.Interractable;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;

/**
 * 
 * @since
 */
public interface Cell {

	int getId();

	int getX();

	int getY();

	boolean isInterractable();

	Interractable getInterractable();

	boolean isRessourceSpawned();

	boolean isWalkeable();

	boolean isTeleporter();

	Entity getEntityOn();

}
