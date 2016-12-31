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

	void setCellId(int cellid);

	int getScaleX();

	int getLife();

	void setLife(int life);

	int getPa();

	void setPa(int pa);

	int getPm();

	void setPm(int pm);

	int getScaleY();

	Orientation getOrientation();

}
