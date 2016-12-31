package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.protocol.game.movement.MovementMonster;
import fr.aresrpg.dofus.structures.Orientation;
import fr.aresrpg.dofus.structures.game.Effect;
import fr.aresrpg.dofus.structures.item.Accessory;
import fr.aresrpg.tofumanchou.domain.data.entity.EntityColor;
import fr.aresrpg.tofumanchou.domain.data.entity.mob.Mob;

import java.util.HashSet;
import java.util.Set;

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

}
