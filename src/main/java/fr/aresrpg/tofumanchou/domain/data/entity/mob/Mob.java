package fr.aresrpg.tofumanchou.domain.data.entity.mob;

import fr.aresrpg.dofus.structures.Orientation;
import fr.aresrpg.tofumanchou.domain.data.entity.*;

/**
 * 
 * @since
 */
public interface Mob extends Entity {

	int getEntityType();

	int getPowerLvl();

	int getCellId();

	int getScaleX();

	int getScaleY();

	Orientation getOrientation();

	EntityColor getColors();

	EntityAccessory[] getAccessories();

	int getLife();

	int getLifeMax();

	int getPA();

	int getPM();

	int[] getResistances();

	int getTeam();

	boolean isDead();

}
