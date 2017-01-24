package fr.aresrpg.tofumanchou.domain.data.map;

import fr.aresrpg.dofus.structures.item.Interractable;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;

import java.util.Set;

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

	/**
	 * Return true si on peut marcher sur la cellule
	 * 
	 * @param last
	 *            les case comme le blé peuvent être traversée tant que ce n'est pas la ou va s'arreter le personnage (mettre false tt le temps pour les combats)
	 * @return
	 */
	boolean isWalkeable(boolean last);

	boolean isTeleporter();

	Set<Entity> getEntitiesOn();

}
