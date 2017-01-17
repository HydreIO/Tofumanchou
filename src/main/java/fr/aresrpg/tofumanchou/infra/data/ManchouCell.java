package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.commons.domain.util.ArrayUtils;
import fr.aresrpg.dofus.Constants;
import fr.aresrpg.dofus.structures.item.Interractable;
import fr.aresrpg.dofus.structures.map.Frame;
import fr.aresrpg.dofus.util.Maps;
import fr.aresrpg.dofus.util.Pathfinding;
import fr.aresrpg.dofus.util.Pathfinding.Node;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.entity.mob.MobGroup;
import fr.aresrpg.tofumanchou.domain.data.map.Carte;
import fr.aresrpg.tofumanchou.domain.data.map.Cell;

import java.awt.Point;
import java.util.*;

/**
 * 
 * @since
 */
public class ManchouCell implements Cell {

	protected int id;
	protected int mapWidth;
	protected int mapHeight;
	protected boolean lineOfSight;
	protected int layerGroundRot;
	protected int groundLevel;
	private int movement;
	protected int layerGroundNum;
	protected int groundSlope;
	protected int x, y;
	protected boolean layerGroundFlip;
	private int layerObject1Num;
	protected int layerObject1Rot;
	protected boolean layerObject1Flip;
	protected boolean layerObject2Flip;
	protected boolean layerObject2Interactive;
	private int layerObject2Num;
	protected int frame;
	protected int permanentLevel;
	protected boolean active;
	protected String layerObjectExternal = "";
	protected boolean layerObjectExternalInteractive = false;
	private String layerObjectExternalData = "";
	protected boolean layerObjectExternalAutoSize;
	private Set<Entity> entitiesOn = new HashSet<>();;

	/**
	 * @param id
	 * @param mapWidth
	 * @param mapHeight
	 * @param lineOfSight
	 * @param layerGroundRot
	 * @param groundLevel
	 * @param movement
	 * @param layerGroundNum
	 * @param groundSlope
	 * @param x
	 * @param y
	 * @param layerGroundFlip
	 * @param layerObject1Num
	 * @param layerObject1Rot
	 * @param layerObject1Flip
	 * @param layerObject2Flip
	 * @param layerObject2Interactive
	 * @param layerObject2Num
	 * @param frame
	 */
	public ManchouCell(int id, int mapWidth, int mapHeight, boolean lineOfSight, int layerGroundRot, int groundLevel, int movement, int layerGroundNum, int groundSlope, int x, int y,
		boolean layerGroundFlip, int layerObject1Num, int layerObject1Rot, boolean layerObject1Flip, boolean layerObject2Flip, boolean layerObject2Interactive, int layerObject2Num, int frame) {
		this.id = id;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.lineOfSight = lineOfSight;
		this.layerGroundRot = layerGroundRot;
		this.groundLevel = groundLevel;
		this.setMovement(movement);
		this.layerGroundNum = layerGroundNum;
		this.groundSlope = groundSlope;
		this.x = x;
		this.y = y;
		this.layerGroundFlip = layerGroundFlip;
		this.setLayerObject1Num(layerObject1Num);
		this.layerObject1Rot = layerObject1Rot;
		this.layerObject1Flip = layerObject1Flip;
		this.layerObject2Flip = layerObject2Flip;
		this.layerObject2Interactive = layerObject2Interactive;
		this.setLayerObject2Num(layerObject2Num);
		this.frame = frame;
	}

	private ManchouCell() {
	}

	public boolean fieldsEquals(ManchouCell cell) {
		return cell.id == id &&
				cell.mapWidth == mapWidth &&
				cell.mapHeight == mapHeight &&
				//	cell.lineOfSight == lineOfSight &&
				cell.layerGroundRot == layerGroundRot &&
				cell.groundLevel == groundLevel &&
				cell.movement == movement &&
				cell.layerGroundNum == layerGroundNum &&
				cell.groundSlope == groundSlope &&
				cell.x == x &&
				cell.y == y &&
				cell.layerGroundFlip == layerGroundFlip &&
				cell.layerObject1Num == layerObject1Num &&
				cell.layerObject1Rot == layerObject1Rot &&
				cell.layerObject1Flip == layerObject1Flip &&
				cell.layerObject2Flip == layerObject2Flip &&
				cell.layerObject2Interactive == layerObject2Interactive &&
				cell.layerObject2Num == layerObject2Num;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		return obj instanceof ManchouCell && ((ManchouCell) obj).getId() == id;
	}

	@Override
	public int hashCode() {
		return id;
	}

	public static ManchouCell parseCell(fr.aresrpg.dofus.structures.map.Cell cell, int width, int height) {
		ManchouCell c = new ManchouCell();
		c.id = cell.getId();
		c.mapWidth = width;
		c.mapHeight = height;
		c.lineOfSight = cell.isLineOfSight();
		c.layerGroundRot = cell.getLayerGroundRot();
		c.groundLevel = cell.getGroundLevel();
		c.setMovement(cell.getMovement());
		c.layerGroundNum = cell.getLayerGroundNum();
		c.groundSlope = cell.getGroundSlope();
		c.x = cell.getXRot();
		c.y = cell.getYRot();
		c.layerGroundFlip = cell.isLayerGroundFlip();
		c.setLayerObject1Num(cell.getLayerObject1Num());
		c.layerObject1Rot = cell.getLayerObject1Rot();
		c.layerObject1Flip = cell.isLayerObject1Flip();
		c.layerObject2Flip = cell.isLayerObject2Flip();
		c.layerObject2Interactive = cell.isLayerObject2Interactive();
		c.setLayerObject2Num(cell.getLayerObject2Num());
		c.frame = cell.getFrame();
		c.active = cell.isActive();
		c.permanentLevel = cell.getPermanentLevel();
		c.layerObjectExternal = cell.getLayerObjectExternal();
		c.layerObjectExternalInteractive = cell.isLayerObjectExternalInteractive();
		c.layerObjectExternalData = cell.getLayerObjectExternalData();
		c.layerObjectExternalInteractive = cell.isLayerObjectExternalInteractive();
		return c;
	}

	public Point toPoint() {
		return new Point(x, y);
	}

	public fr.aresrpg.dofus.structures.map.Cell serialize() {
		fr.aresrpg.dofus.structures.map.Cell cell = new fr.aresrpg.dofus.structures.map.Cell(id, active, lineOfSight, layerGroundRot, groundLevel, getMovement(), layerGroundNum, groundSlope,
				layerGroundFlip, getLayerObject1Num(),
				layerObject1Rot,
				layerObject1Flip, layerObject2Flip, layerObject2Interactive, getLayerObject2Num());
		cell.setPermanentLevel(permanentLevel);
		cell.setLayerObjectExternal(layerObjectExternal);
		cell.setLayerObjectExternalInteractive(layerObjectExternalInteractive);
		cell.setLayerObjectExternalData(layerObjectExternalData);
		cell.setLayerObjectExternalInteractive(layerObjectExternalInteractive);
		cell.setX(Maps.getX(id, mapWidth));
		cell.setY(Maps.getY(id, mapWidth));
		cell.setxRot(Maps.getXRotated(id, mapWidth, mapHeight));
		cell.setyRot(Maps.getYRotated(id, mapWidth, mapHeight));
		return cell;
	}

	public int getRandomNeighborCell(Carte map, boolean diagonale, List<Integer> avoid) {
		Node[] neighbors = diagonale ? Pathfinding.getNeighbors(new Node(getX(), getY())) : Pathfinding.getNeighborsWithoutDiagonals(new Node(getX(), getY()));
		for (Node n : neighbors) {
			if (!Maps.isInMapRotated(n.getX(), n.getY(), mapWidth, mapHeight)) continue;
			int id = Maps.getIdRotated(n.getX(), n.getY(), mapWidth, mapHeight);
			if (avoid.contains(id) || !Maps.isInMap(id, mapWidth, mapHeight)) continue;
			Cell cell = map.getCells()[id];
			if (cell.isWalkeable() && !((ManchouCell) cell).hasMobGroupOn()) return id;
		}
		return -1;
	}

	public void applyFrame(Frame frame) {
		this.frame = frame.getId();
		if (frame.isInteractive() != null) {
			this.layerObject2Interactive = frame.isInteractive();
		}
	}

	public boolean isOnSameLineOrCollumn(ManchouCell c) {
		return c.getX() == x || c.getY() == y;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	/**
	 * @return the mapWidth
	 */
	public int getMapWidth() {
		return mapWidth;
	}

	/**
	 * @return the mapHeight
	 */
	public int getMapHeight() {
		return mapHeight;
	}

	/**
	 * @return the layerGroundRot
	 */
	public int getLayerGroundRot() {
		return layerGroundRot;
	}

	/**
	 * @return the groundLevel
	 */
	public int getGroundLevel() {
		return groundLevel;
	}

	/**
	 * @return the layerGroundNum
	 */
	public int getLayerGroundNum() {
		return layerGroundNum;
	}

	/**
	 * @return the groundSlope
	 */
	public int getGroundSlope() {
		return groundSlope;
	}

	/**
	 * @return the layerGroundFlip
	 */
	public boolean isLayerGroundFlip() {
		return layerGroundFlip;
	}

	/**
	 * @return the layerObject1Rot
	 */
	public int getLayerObject1Rot() {
		return layerObject1Rot;
	}

	/**
	 * @return the layerObject1Flip
	 */
	public boolean isLayerObject1Flip() {
		return layerObject1Flip;
	}

	/**
	 * @return the layerObject2Flip
	 */
	public boolean isLayerObject2Flip() {
		return layerObject2Flip;
	}

	/**
	 * @return the layerObject2Interactive
	 */
	public boolean isLayerObject2Interactive() {
		return layerObject2Interactive;
	}

	/**
	 * @return the frame
	 */
	public int getFrame() {
		return frame;
	}

	/**
	 * @return the permanentLevel
	 */
	public int getPermanentLevel() {
		return permanentLevel;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @return the layerObjectExternal
	 */
	public String getLayerObjectExternal() {
		return layerObjectExternal;
	}

	/**
	 * @return the layerObjectExternalInteractive
	 */
	public boolean isLayerObjectExternalInteractive() {
		return layerObjectExternalInteractive;
	}

	/**
	 * @return the layerObjectExternalData
	 */
	public String getLayerObjectExternalData() {
		return layerObjectExternalData;
	}

	/**
	 * @return the layerObjectExternalAutoSize
	 */
	public boolean isLayerObjectExternalAutoSize() {
		return layerObjectExternalAutoSize;
	}

	/**
	 * @param entityOn
	 *            the entityOn to set
	 */
	public void addEntityOn(Entity entityOn) {
		entitiesOn.add(entityOn);
	}

	public void removeEntityOn(Entity entityon) {
		entitiesOn.remove(entityon);
	}

	@Override
	public Interractable getInterractable() {
		return Interractable.fromId(getInterractableId());
	}

	public int getInterractableId() {
		if (layerObject2Num == 0) return layerObject1Num;
		return layerObject2Num;
	}

	public boolean isRessource() {
		Interractable i = getInterractable();
		if (i == null) return false;
		return Interractable.isRessource(i.getId());
	}

	@Override
	public boolean isInterractable() {
		if (movement == 1) return true;
		return Interractable.isInterractable(getInterractableId());
	}

	@Override
	public boolean isRessourceSpawned() {
		if (!isInterractable()) return false;
		return layerObject2Interactive;
	}

	public int distance(int cellid) {
		return Maps.distance(id, cellid, mapWidth, mapHeight);
	}

	public int distance(Cell cell) {
		return distance(cell.getId());
	}

	public int distanceManathan(int cellid) {
		return Maps.distanceManathan(getId(), cellid, mapWidth, mapHeight);
	}

	public int distanceManathan(Cell cell) {
		return distanceManathan(cell.getId());
	}

	@Override
	public boolean isWalkeable() {
		return getMovement() != 0 && !isInterractable();
	}

	@Override
	public boolean isTeleporter() {
		return ArrayUtils.contains(layerObject1Num, Constants.TELEPORT_TEXTURES) || ArrayUtils.contains(layerObject2Num, Constants.TELEPORT_TEXTURES);
	}

	public boolean isZaapOrZaapi() {
		return isZaap() || isZaapi();
	}

	public boolean isZaap() {
		return Interractable.isZaap(layerObject1Num) || Interractable.isZaap(layerObject2Num);
	}

	public boolean isZaapi() {
		return Interractable.isZaapi(layerObject1Num) || Interractable.isZaapi(layerObject2Num);
	}

	@Override
	public Set<Entity> getEntitiesOn() {
		return entitiesOn;
	}

	public boolean hasMobGroupOn() {
		for (Entity e : getEntitiesOn())
			if (e != null && e instanceof MobGroup) return true;
		return false;
	}

	public boolean hasEntityOn() {
		return !entitiesOn.isEmpty();
	}

	@Override
	public String toString() {
		return "ManchouCell [id=" + id + ", mapWidth=" + mapWidth + ", mapHeight=" + mapHeight + ", lineOfSight=" + lineOfSight + ", layerGroundRot=" + layerGroundRot + ", groundLevel=" + groundLevel
				+ ", movement=" + movement + ", layerGroundNum=" + layerGroundNum + ", groundSlope=" + groundSlope + ", x=" + x + ", y=" + y + ", layerGroundFlip=" + layerGroundFlip
				+ ", layerObject1Num=" + layerObject1Num + ", layerObject1Rot=" + layerObject1Rot + ", layerObject1Flip=" + layerObject1Flip + ", layerObject2Flip=" + layerObject2Flip
				+ ", layerObject2Interactive=" + layerObject2Interactive + ", layerObject2Num=" + layerObject2Num + ", frame=" + frame + ", permanentLevel=" + permanentLevel + ", active=" + active
				+ ", layerObjectExternal=" + layerObjectExternal + ", layerObjectExternalInteractive=" + layerObjectExternalInteractive + ", layerObjectExternalData=" + layerObjectExternalData
				+ ", layerObjectExternalAutoSize=" + layerObjectExternalAutoSize + ", entitiesOn=" + entitiesOn + "]";
	}

	/**
	 * @return the lineOfSight
	 */
	public boolean isLineOfSight() {
		return lineOfSight;
	}

	/**
	 * @return the movement
	 */
	public int getMovement() {
		return movement;
	}

	/**
	 * @param movement
	 *            the movement to set
	 */
	public void setMovement(int movement) {
		this.movement = movement;
	}

	/**
	 * @return the layerObject1Num
	 */
	public int getLayerObject1Num() {
		return layerObject1Num;
	}

	/**
	 * @param layerObject1Num
	 *            the layerObject1Num to set
	 */
	public void setLayerObject1Num(int layerObject1Num) {
		this.layerObject1Num = layerObject1Num;
	}

	/**
	 * @return the layerObject2Num
	 */
	public int getLayerObject2Num() {
		return layerObject2Num;
	}

	/**
	 * @param layerObject2Num
	 *            the layerObject2Num to set
	 */
	public void setLayerObject2Num(int layerObject2Num) {
		this.layerObject2Num = layerObject2Num;
	}

}
