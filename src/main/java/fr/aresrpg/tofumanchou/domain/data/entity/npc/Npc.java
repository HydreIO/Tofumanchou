package fr.aresrpg.tofumanchou.domain.data.entity.npc;

import fr.aresrpg.dofus.structures.Orientation;
import fr.aresrpg.tofumanchou.domain.data.entity.*;
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

	EntityAccessory[] getAccesories();

	int getExtraClip();

	int getCustomArtwork();

}
