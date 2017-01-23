package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.protocol.game.movement.MovementMonster;
import fr.aresrpg.dofus.structures.Orientation;
import fr.aresrpg.dofus.structures.game.Effect;
import fr.aresrpg.dofus.structures.item.Accessory;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.entity.EntityColor;
import fr.aresrpg.tofumanchou.domain.data.entity.mob.Mob;

import java.util.*;

/**
 * 
 * @since
 */
public class ManchouMob implements Mob {

	private long uuid;
	private Set<Effect> effects = new HashSet<>();
	private int cellId;
	private int scaleX;
	private int scaleY;
	private Orientation orientation;
	private int entityType;
	private int spriteType;
	private boolean noFlip;
	private int powerLvl;
	private EntityColor colors;
	private Accessory[] accessories;
	private int life;
	private int lifeMax;
	private int pa;
	private int pm;
	private int[] resistances;
	private int team;
	private boolean dead;

	public static ManchouMob parseMovement(MovementMonster mo) {
		ManchouMob m = new ManchouMob();
		m.uuid = mo.getId();
		m.cellId = mo.getCellId();
		m.scaleX = mo.getScaleX();
		m.scaleY = mo.getScaleY();
		m.orientation = mo.getOrientation();
		m.entityType = mo.getEntitytype();
		m.spriteType = mo.getSpriteType();
		m.noFlip = mo.isNoFlip();
		m.powerLvl = mo.getPowerLvl();
		m.colors = new ManchouColors(mo.getColor1(), mo.getColor2(), mo.getColor3());
		m.accessories = mo.getAccessories();
		m.life = mo.getLife();
		m.lifeMax = mo.getLife();
		m.pa = mo.getPa();
		m.pm = mo.getPm();
		m.resistances = mo.getResi();
		m.team = mo.getTeam();
		m.dead = m.life < 1;
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
	public int getEntityType() {
		return entityType;
	}

	@Override
	public int getPowerLvl() {
		return powerLvl;
	}

	@Override
	public EntityColor getColors() {
		return colors;
	}

	@Override
	public Accessory[] getAccessories() {
		return accessories;
	}

	@Override
	public int getLife() {
		return life;
	}

	@Override
	public int getLifeMax() {
		return lifeMax;
	}

	@Override
	public int getPA() {
		return pa;
	}

	@Override
	public int getPM() {
		return pm;
	}

	@Override
	public int[] getResistances() {
		return resistances;
	}

	@Override
	public int getTeam() {
		return team;
	}

	@Override
	public boolean isDead() {
		return dead;
	}

	@Override
	public void setCellId(int cellid) {
		this.cellId = cellid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(long uuid) {
		this.uuid = uuid;
	}

	/**
	 * @param effects
	 *            the effects to set
	 */
	public void setEffects(Set<Effect> effects) {
		this.effects = effects;
	}

	/**
	 * @param scaleX
	 *            the scaleX to set
	 */
	public void setScaleX(int scaleX) {
		this.scaleX = scaleX;
	}

	/**
	 * @param scaleY
	 *            the scaleY to set
	 */
	public void setScaleY(int scaleY) {
		this.scaleY = scaleY;
	}

	/**
	 * @param orientation
	 *            the orientation to set
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * @param entityType
	 *            the entityType to set
	 */
	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}

	/**
	 * @param spriteType
	 *            the spriteType to set
	 */
	public void setSpriteType(int spriteType) {
		this.spriteType = spriteType;
	}

	/**
	 * @param noFlip
	 *            the noFlip to set
	 */
	public void setNoFlip(boolean noFlip) {
		this.noFlip = noFlip;
	}

	/**
	 * @param powerLvl
	 *            the powerLvl to set
	 */
	public void setPowerLvl(int powerLvl) {
		this.powerLvl = powerLvl;
	}

	/**
	 * @param colors
	 *            the colors to set
	 */
	public void setColors(EntityColor colors) {
		this.colors = colors;
	}

	/**
	 * @param accessories
	 *            the accessories to set
	 */
	public void setAccessories(Accessory[] accessories) {
		this.accessories = accessories;
	}

	/**
	 * @param life
	 *            the life to set
	 */
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * @param lifeMax
	 *            the lifeMax to set
	 */
	public void setLifeMax(int lifeMax) {
		this.lifeMax = lifeMax;
	}

	/**
	 * @param pa
	 *            the pa to set
	 */
	public void setPa(int pa) {
		this.pa = pa;
	}

	/**
	 * @param pm
	 *            the pm to set
	 */
	public void setPm(int pm) {
		this.pm = pm;
	}

	/**
	 * @param resistances
	 *            the resistances to set
	 */
	public void setResistances(int[] resistances) {
		this.resistances = resistances;
	}

	/**
	 * @param team
	 *            the team to set
	 */
	public void setTeam(int team) {
		this.team = team;
	}

	/**
	 * @param dead
	 *            the dead to set
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	@Override
	public int getPa() {
		return pa;
	}

	@Override
	public int getPm() {
		return pm;
	}

	@Override
	public String toString() {
		return "ManchouMob [uuid=" + uuid + ", effects=" + effects + ", cellId=" + cellId + ", scaleX=" + scaleX + ", scaleY=" + scaleY + ", orientation=" + orientation + ", entityType=" + entityType
				+ ", spriteType=" + spriteType + ", noFlip=" + noFlip + ", powerLvl=" + powerLvl + ", colors=" + colors + ", accessories=" + Arrays.toString(accessories) + ", life=" + life
				+ ", lifeMax=" + lifeMax + ", pa=" + pa + ", pm=" + pm + ", resistances=" + Arrays.toString(resistances) + ", team=" + team + ", dead=" + dead + "]";
	}

}
