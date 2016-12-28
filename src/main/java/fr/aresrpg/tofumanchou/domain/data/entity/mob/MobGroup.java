package fr.aresrpg.tofumanchou.domain.data.entity.mob;

import fr.aresrpg.tofumanchou.domain.data.entity.Entity;

/**
 * 
 * @since
 */
public interface MobGroup extends Entity {

	int getStars();

	int getCellId();

	Mob[] getMobs();

}
