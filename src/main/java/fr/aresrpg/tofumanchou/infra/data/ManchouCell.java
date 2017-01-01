package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.structures.item.Interractable;
import fr.aresrpg.dofus.structures.map.Frame;
import fr.aresrpg.dofus.util.Maps;
import fr.aresrpg.dofus.util.Pathfinding;
import fr.aresrpg.dofus.util.Pathfinding.Node;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.entity.mob.MobGroup;
import fr.aresrpg.tofumanchou.domain.data.map.Carte;
import fr.aresrpg.tofumanchou.domain.data.map.Cell;

import java.util.List;

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
	protected int movement;
	protected int layerGroundNum;
	protected int groundSlope;
	protected int x, y;
	protected boolean layerGroundFlip;
	protected int layerObject1Num;
	protected int layerObject1Rot;
	protected boolean layerObject1Flip;
	protected boolean layerObject2Flip;
	protected boolean layerObject2Interactive;
	protected int layerObject2Num;
	protected int frame;
	private Entity entityOn;

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
	 * @param entityOn
	 */
	public ManchouCell(int id, int mapWidth, int mapHeight, boolean lineOfSight, int layerGroundRot, int groundLevel, int movement, int layerGroundNum, int groundSlope, int x, int y,
		boolean layerGroundFlip, int layerObject1Num, int layerObject1Rot, boolean layerObject1Flip, boolean layerObject2Flip, boolean layerObject2Interactive, int layerObject2Num, int frame) {
		this.id = id;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.lineOfSight = lineOfSight;
		this.layerGroundRot = layerGroundRot;
		this.groundLevel = groundLevel;
		this.movement = movement;
		this.layerGroundNum = layerGroundNum;
		this.groundSlope = groundSlope;
		this.x = x;
		this.y = y;
		this.layerGroundFlip = layerGroundFlip;
		this.layerObject1Num = layerObject1Num;
		this.layerObject1Rot = layerObject1Rot;
		this.layerObject1Flip = layerObject1Flip;
		this.layerObject2Flip = layerObject2Flip;
		this.layerObject2Interactive = layerObject2Interactive;
		this.layerObject2Num = layerObject2Num;
		this.frame = frame;
	}

	private ManchouCell() {
	}

	public static ManchouCell parseCell(fr.aresrpg.dofus.structures.map.Cell cell, int width, int height) {
		ManchouCell c = new ManchouCell();
		c.id = cell.getId();
		c.mapWidth = width;
		c.mapHeight = height;
		c.lineOfSight = cell.isLineOfSight();
		c.layerGroundRot = cell.getLayerGroundRot();
		c.groundLevel = cell.getGroundLevel();
		c.movement = cell.getMovement();
		c.layerGroundNum = cell.getLayerGroundNum();
		c.groundSlope = cell.getGroundSlope();
		c.x = cell.getX();
		c.y = cell.getY();
		c.layerGroundFlip = cell.isLayerGroundFlip();
		c.layerObject1Num = cell.getLayerObject1Num();
		c.layerObject1Rot = cell.getLayerObject1Rot();
		c.layerObject1Flip = cell.isLayerObject1Flip();
		c.layerObject2Flip = cell.isLayerObject2Flip();
		c.layerObject2Interactive = cell.isLayerObject2Interactive();
		c.layerObject2Num = cell.getLayerObject2Num();
		c.frame = cell.getFrame();
		return c;
	}

	public fr.aresrpg.dofus.structures.map.Cell serialize() {
		return new fr.aresrpg.dofus.structures.map.Cell(id, mapWidth, lineOfSight, layerGroundRot, groundLevel, movement, layerGroundNum, groundSlope, x, y, layerGroundFlip, layerObject1Num,
				layerObject1Rot, layerObject1Flip, layerObject2Flip, layerObject2Interactive, layerObject2Num);
	}

	public int getRandomNeighborCell(Carte map, boolean diagonale, List<Integer> avoid) {
		Node[] neighbors = diagonale ? Pathfinding.getNeighbors(new Node(getX(), getY())) : Pathfinding.getNeighborsWithoutDiagonals(new Node(getX(), getY()));
		for (Node n : neighbors) {
			int id = Maps.getId(n.getX(), n.getY(), mapWidth, mapHeight);
			if (avoid.contains(id) || !Maps.isInMap(n.getX(), n.getY(), mapWidth, mapHeight)) continue;
			Cell cell = map.getCells()[id];
			if (cell.isWalkeable() && cell.getEntityOn() == null) return id;
		}
		return -1;
	}

	public void applyFrame(Frame frame) {
		this.frame = frame.getId();
		if (frame.isInteractive() != null) {
			this.layerObject2Interactive = frame.isInteractive();
		}
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

	@Override
	public Interractable getInterractable() {
		return Interractable.fromId(layerObject2Num);
	}

	public boolean isRessource() {
		Interractable i = getInterractable();
		if (i == null) return false;
		return Interractable.isRessource(i.getId());
	}

	@Override
	public boolean isInterractable() {
		return Interractable.isInterractable(layerObject2Num);
	}

	@Override
	public boolean isRessourceSpawned() {
		if (!isInterractable()) return false;
		return frame == 0 || frame == 5;
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
		return movement != 0 && !isInterractable();
	}

	@Override
	public boolean isTeleporter() {
		return movement == 2 || layerObject1Num == 1030 || layerObject2Num == 1030;
	}

	public boolean isTeleporter1030() {
		return layerObject1Num == 1030 || layerObject2Num == 1030;
	}

	@Override
	public Entity getEntityOn() {
		return entityOn;
	}

	public boolean hasMobOn() {
		Entity e = getEntityOn();
		if (e == null) return false;
		return e instanceof MobGroup;
	}
}
