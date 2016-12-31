package fr.aresrpg.tofumanchou.domain.data.entity.mob;

import fr.aresrpg.dofus.structures.item.Accessory;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.entity.EntityColor;

/**
 * 
 * @since
 */
public interface MobGroup extends Entity {

	int getStars();

	int[] getLvls();

	EntityColor getColors();

	Accessory[] getAccessories();

	int[] getEntitiesTypes();

}
