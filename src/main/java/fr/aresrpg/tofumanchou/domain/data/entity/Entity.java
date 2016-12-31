package fr.aresrpg.tofumanchou.domain.data.entity;

import fr.aresrpg.dofus.structures.Orientation;
import fr.aresrpg.dofus.structures.game.Effect;

import java.util.Set;

/**
 * 
 * @since
 */
public interface Entity {

	long getUUID();

	Set<Effect> getEffects();

	int getCellId();

	int getScaleX();

	int getScaleY();

	Orientation getOrientation();

}
