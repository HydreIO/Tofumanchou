package fr.aresrpg.tofumanchou.domain.data.entity.npc;

import fr.aresrpg.dofus.structures.Orientation;
import fr.aresrpg.dofus.structures.item.Accessory;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.entity.EntityColor;
import fr.aresrpg.tofumanchou.domain.data.enums.Genre;

/**
 * 
 * @since
 */
public interface Npc extends Entity {

	int getEntityType();

	Genre getSex();

	int getScaleX();

	int getScaleY();

	int getCellId();

	Orientation getOrientation();

	EntityColor getColors();

	Accessory[] getAccesories();

	int getExtraClip();

	int getCustomArtwork();

}
