package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.protocol.game.movement.MovementMonsterGroup;
import fr.aresrpg.dofus.structures.Orientation;
import fr.aresrpg.dofus.structures.game.Effect;
import fr.aresrpg.dofus.structures.item.Accessory;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.entity.EntityColor;
import fr.aresrpg.tofumanchou.domain.data.entity.mob.MobGroup;

import java.util.*;

/**
 * 
 * @since
 */
public class ManchouMobGroup implements MobGroup {

	private long uuid;
	private Set<Effect> effects = new HashSet<>();
	private int cellId;
	private int scaleX;
	private int scaleY;
	private Orientation orientation;
	private int stars;
	private int[] entities;
	private int[] lvls;
	private EntityColor colors;
	private Accessory[] accessories;

	public static ManchouMobGroup parseMovement(MovementMonsterGroup g) {
		ManchouMobGroup m = new ManchouMobGroup();
		m.uuid = g.getId();
		m.cellId = g.getCellId();
		m.scaleX = g.getScaleX();
		m.scaleY = g.getScaleY();
		m.orientation = g.getOrientation();
		m.stars = g.getBonusValue();
		m.entities = g.getEntitytype();
		m.lvls = g.getLvl();
		m.colors = new ManchouColors(g.getColor1(), g.getColor2(), g.getColor3());
		m.accessories = g.getAccessories();
		return m;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		return obj instanceof Entity && ((Entity) obj).getUUID() == uuid;
	}

	@Override
	public int hashCode() {
		return (int) uuid;
	}

	@Override
	public long getUUID() {
		return uuid;
	}

	@Override
	public Set<Effect> getEffects() {
		return effects;
	}

	@Override
	public int getCellId() {
		return cellId;
	}

	@Override
	public int getScaleX() {
		return scaleX;
	}

	@Override
	public int getScaleY() {
		return scaleY;
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public int getStars() {
		return stars;
	}

	@Override
	public int[] getEntitiesTypes() {
		return entities;
	}

	@Override
	public int[] getLvls() {
		return lvls;
	}

	@Override
	public EntityColor getColors() {
		return colors;
	}

	@Override
	public Accessory[] getAccessories() {
		return accessories;
	}

	/**
	 * @return the uuid
	 */
	public long getUuid() {
		return uuid;
	}

	/**
	 * @return the entities
	 */
	public int[] getEntities() {
		return entities;
	}

	@Override
	public String toString() {
		return "ManchouMobGroup [uuid=" + uuid + ", effects=" + effects + ", cellId=" + cellId + ", scaleX=" + scaleX + ", scaleY=" + scaleY + ", orientation=" + orientation + ", stars=" + stars
				+ ", entities=" + Arrays.toString(entities) + ", lvls=" + Arrays.toString(lvls) + ", colors=" + colors + ", accessories=" + Arrays.toString(accessories) + "]";
	}

	@Override
	public void setCellId(int cellid) {
		this.cellId = cellid;
	}

	@Override
	public int getLife() {
		return 0;
	}

	@Override
	public void setLife(int life) {

	}

	@Override
	public int getPa() {
		return 0;
	}

	@Override
	public void setPa(int pa) {

	}

	@Override
	public int getPm() {
		return 0;
	}

	@Override
	public void setPm(int pm) {

	}

}
