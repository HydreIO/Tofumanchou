package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.protocol.game.movement.MovementNpc;
import fr.aresrpg.dofus.structures.Orientation;
import fr.aresrpg.dofus.structures.game.Effect;
import fr.aresrpg.dofus.structures.item.Accessory;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.entity.EntityColor;
import fr.aresrpg.tofumanchou.domain.data.entity.npc.Npc;
import fr.aresrpg.tofumanchou.domain.data.enums.Genre;

import java.util.*;

/**
 * 
 * @since
 */
public class ManchouNpc implements Npc {

	private int customArtwork;
	private int extraclip;
	private Accessory[] accessories;
	private EntityColor colors;
	private Orientation orientation;
	private int cellId;
	private int scaleY;
	private int scaleX;
	private Genre sex;
	private int entityType;
	private Set<Effect> effects = new HashSet<>();
	private long uuid;
	private int pa;
	private int life;
	private int pm;

	public static ManchouNpc parseMovement(MovementNpc npc) {
		ManchouNpc m = new ManchouNpc();
		m.customArtwork = npc.getCustomArtwork();
		m.extraclip = npc.getExtraclip();
		m.accessories = npc.getAccessories();
		m.colors = new ManchouColors(npc.getColor1(), npc.getColor2(), npc.getColor3());
		m.orientation = npc.getOrientation();
		m.cellId = npc.getCellId();
		m.scaleX = npc.getScaleX();
		m.scaleY = npc.getScaleY();
		m.sex = Genre.valueOf(npc.getSex());
		m.entityType = npc.getSpriteType();
		m.uuid = npc.getId();
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
	public int getEntityType() {
		return entityType;
	}

	@Override
	public Genre getSex() {
		return sex;
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
	public int getCellId() {
		return cellId;
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public EntityColor getColors() {
		return colors;
	}

	@Override
	public Accessory[] getAccesories() {
		return accessories;
	}

	@Override
	public int getExtraClip() {
		return extraclip;
	}

	@Override
	public int getCustomArtwork() {
		return customArtwork;
	}

	@Override
	public String toString() {
		return "ManchouNpc [customArtwork=" + customArtwork + ", extraclip=" + extraclip + ", accesories=" + Arrays.toString(accessories) + ", colors=" + colors + ", orientation=" + orientation
				+ ", cellId=" + cellId + ", scaleY=" + scaleY + ", scaleX=" + scaleX + ", sex=" + sex + ", entityType=" + entityType + ", effects=" + effects + ", uuid=" + uuid + "]";
	}

	@Override
	public void setCellId(int cellid) {
		this.cellId = cellid;
	}

	@Override
	public int getLife() {
		return life;
	}

	@Override
	public void setLife(int life) {
		this.life = life;
	}

	@Override
	public int getPa() {
		return pa;
	}

	@Override
	public void setPa(int pa) {
		this.pa = pa;
	}

	@Override
	public int getPm() {
		return pm;
	}

	@Override
	public void setPm(int pm) {
		this.pm = pm;
	}

	@Override
	public boolean isDead() {
		return true;
	}

	@Override
	public void setDead(boolean dead) {

	}

}
