package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.commons.domain.util.ArrayUtils;
import fr.aresrpg.dofus.structures.game.FightSpawn;
import fr.aresrpg.dofus.structures.game.FightType;
import fr.aresrpg.dofus.structures.item.Interractable;
import fr.aresrpg.dofus.structures.map.Cell;
import fr.aresrpg.dofus.structures.map.DofusMap;
import fr.aresrpg.dofus.util.Pathfinding;
import fr.aresrpg.dofus.util.Pathfinding.Node;
import fr.aresrpg.dofus.util.Pathfinding.PathValidator;
import fr.aresrpg.tofumanchou.domain.data.MapsData;
import fr.aresrpg.tofumanchou.domain.data.MapsData.MapDataBean;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.entity.mob.MobGroup;
import fr.aresrpg.tofumanchou.domain.data.enums.DofusMobs;
import fr.aresrpg.tofumanchou.domain.data.map.Carte;

import java.awt.Point;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;

/**
 * 
 * @since
 */
public class ManchouMap implements Carte {

	private int mapid;
	private int x;
	private int y;
	private long date;
	private int width;
	private int height;
	private int backgroundId;
	private boolean outdoor;
	private int musicId;
	private ManchouCell[] cells;
	private ConcurrentMap<Long, Entity> entities = new ConcurrentHashMap<>();
	private FightType fightType;
	private int[] team0Places;
	private int[] team1Places;
	private boolean blocked;
	private boolean specBlocked;
	private boolean groupBlocked;
	private boolean helpNeeded;
	private boolean ended = true;
	private Entity currentTurn;
	private boolean spectator;
	private int startTimer;
	private boolean duel;
	private Set<FightSpawn> fightsOnMap = new HashSet<>();
	private int capabilities;
	private String area;
	private String subarea;

	public static ManchouMap fromDofusMap(DofusMap map) {
		ManchouMap m = new ManchouMap();
		m.mapid = map.getId();
		MapDataBean data = MapsData.getData(map.getId());
		Point coords = new Point(data.getX(), data.getY());
		m.x = coords.x;
		m.y = coords.y;
		m.date = map.getDate();
		m.width = map.getWidth();
		m.height = map.getHeight();
		m.backgroundId = map.getBackgroundId();
		m.musicId = map.getMusicId();
		m.cells = parseCells(map.getCells(), map.getWidth(), map.getHeight());
		m.outdoor = map.isOutdoor();
		m.capabilities = map.getCapabilities();
		m.area = data.getArea();
		m.subarea = data.getSubarea();
		return m;
	}

	public void updateFields(DofusMap map) {
		mapid = map.getId();
		MapDataBean data = MapsData.getData(map.getId());
		Point coords = new Point(data.getX(), data.getY());
		x = coords.x;
		y = coords.y;
		date = map.getDate();
		width = map.getWidth();
		height = map.getHeight();
		backgroundId = map.getBackgroundId();
		musicId = map.getMusicId();
		cells = parseCells(map.getCells(), map.getWidth(), map.getHeight());
		outdoor = map.isOutdoor();
		capabilities = map.getCapabilities();
		area = data.getArea();
		subarea = data.getSubarea();
	}

	public Predicate<Cell> cellAccessible() {
		return c -> {
			ManchouCell cl = cells[c.getId()];
			return cl.lineOfSight && !cl.hasEntityOn(); // FIXME
		};
	}

	public long getDate() {
		return date;
	}

	public DofusMap serialize() {
		return new DofusMap(mapid, date, width, height, musicId, capabilities, outdoor, backgroundId, getProtocolCells());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		return obj instanceof ManchouMap && ((ManchouMap) obj).getMapId() == mapid;
	}

	public boolean hasSpawnedRessources(Interractable... interractable) {
		for (ManchouCell c : cells) {
			Interractable ress = c.getInterractable();
			if (ress != null && c.isRessourceSpawned() && ArrayUtils.contains(ress, interractable)) return true;
		}
		return false;
	}

	public boolean hasMobGroupWithout(Predicate<DofusMobs> avoid) {
		for (Entity e : getEntities().values()) {
			if (e instanceof MobGroup) {
				MobGroup gr = (MobGroup) e;
				if (Arrays.stream(gr.getEntitiesTypes()).mapToObj(DofusMobs::byId).anyMatch(avoid)) continue;
				return true;
			}
		}
		return false;
	}

	public MobGroup getAccessibleMobGroupWithout(int playerPos, Predicate<DofusMobs> avoid) {
		for (Entity e : getEntities().values()) {
			if (e instanceof MobGroup) {
				MobGroup gr = (MobGroup) e;
				if (Arrays.stream(gr.getEntitiesTypes()).mapToObj(DofusMobs::byId).anyMatch(avoid)) continue;
				List<Node> cellPath = Pathfinding.getCellPath(playerPos, gr.getCellId(), getProtocolCells(), width, height, Pathfinding::getNeighbors,
						PathValidator.alwaysTrue());
				if (cellPath == null) continue;
				return gr;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		return mapid;
	}

	private static ManchouCell[] parseCells(fr.aresrpg.dofus.structures.map.Cell[] cells, int width, int height) {
		return Arrays.stream(cells).map(l -> ManchouCell.parseCell(l, width, height)).toArray(ManchouCell[]::new);
	}

	/**
	 * @return the spectator
	 */
	public boolean isSpectator() {
		return spectator;
	}

	/**
	 * @return the outdoor
	 */
	@Override
	public boolean isOutdoor() {
		return outdoor;
	}

	/**
	 * @param outdoor
	 *            the outdoor to set
	 */
	public void setOutdoor(boolean outdoor) {
		this.outdoor = outdoor;
	}

	/**
	 * @param spectator
	 *            the spectator to set
	 */
	public void setSpectator(boolean spectator) {
		this.spectator = spectator;
	}

	public int distance(Point map) {
		return Math.abs(map.x - getX()) + Math.abs(map.y - getY());
	}

	public Point toPoint() {
		return new Point(x, y);
	}

	public boolean isOnCoords(int x, int y) {
		return this.x == x && this.y == y;
	}

	@Override
	public int getMapId() {
		return mapid;
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
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getBackgroundId() {
		return backgroundId;
	}

	@Override
	public int getMusicId() {
		return musicId;
	}

	@Override
	public ManchouCell[] getCells() {
		return cells;
	}

	public Cell[] getProtocolCells() {
		return Arrays.stream(cells).map(ManchouCell::serialize).toArray(Cell[]::new);
	}

	@Override
	public Map<Long, Entity> getEntities() {
		return entities;
	}

	@Override
	public FightType getFightType() {
		return fightType;
	}

	@Override
	public int[] getTeam0Places() {
		return team0Places;
	}

	@Override
	public int[] getTeam1Places() {
		return team1Places;
	}

	@Override
	public boolean isBlocked() {
		return blocked;
	}

	@Override
	public boolean isSpecBlocked() {
		return specBlocked;
	}

	@Override
	public boolean isGroupBlocked() {
		return groupBlocked;
	}

	@Override
	public boolean isHelpNeeded() {
		return helpNeeded;
	}

	@Override
	public boolean isEnded() {
		return ended;
	}

	@Override
	public Entity getCurrentTurn() {
		return currentTurn;
	}

	/**
	 * @param mapid
	 *            the mapid to set
	 */
	public void setMapid(int mapid) {
		this.mapid = mapid;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @param backgroundId
	 *            the backgroundId to set
	 */
	public void setBackgroundId(int backgroundId) {
		this.backgroundId = backgroundId;
	}

	/**
	 * @param musicId
	 *            the musicId to set
	 */
	public void setMusicId(int musicId) {
		this.musicId = musicId;
	}

	/**
	 * @param cells
	 *            the cells to set
	 */
	public void setCells(ManchouCell[] cells) {
		this.cells = cells;
	}

	/**
	 * @param entities
	 *            the entities to set
	 */
	public void setEntities(ConcurrentMap<Long, Entity> entities) {
		this.entities = entities;
	}

	/**
	 * @param fightType
	 *            the fightType to set
	 */
	public void setFightType(FightType fightType) {
		this.fightType = fightType;
	}

	/**
	 * @param team0Places
	 *            the team0Places to set
	 */
	public void setTeam0Places(int[] team0Places) {
		this.team0Places = team0Places;
	}

	/**
	 * @param team1Places
	 *            the team1Places to set
	 */
	public void setTeam1Places(int[] team1Places) {
		this.team1Places = team1Places;
	}

	/**
	 * @param blocked
	 *            the blocked to set
	 */
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	/**
	 * @param specBlocked
	 *            the specBlocked to set
	 */
	public void setSpecBlocked(boolean specBlocked) {
		this.specBlocked = specBlocked;
	}

	/**
	 * @param groupBlocked
	 *            the groupBlocked to set
	 */
	public void setGroupBlocked(boolean groupBlocked) {
		this.groupBlocked = groupBlocked;
	}

	/**
	 * @param helpNeeded
	 *            the helpNeeded to set
	 */
	public void setHelpNeeded(boolean helpNeeded) {
		this.helpNeeded = helpNeeded;
	}

	/**
	 * @param ended
	 *            the ended to set
	 */
	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	/**
	 * @param currentTurn
	 *            the currentTurn to set
	 */
	public void setCurrentTurn(Entity currentTurn) {
		this.currentTurn = currentTurn;
	}

	@Override
	public int getFightStartTimer() {
		return startTimer;
	}

	@Override
	public boolean isDuel() {
		return duel;
	}

	/**
	 * @return the startTimer
	 */
	public int getStartTimer() {
		return startTimer;
	}

	/**
	 * @param startTimer
	 *            the startTimer to set
	 */
	public void setStartTimer(int startTimer) {
		this.startTimer = startTimer;
	}

	/**
	 * @param duel
	 *            the duel to set
	 */
	public void setDuel(boolean duel) {
		this.duel = duel;
	}

	@Override
	public Set<FightSpawn> getFightsOnMap() {
		return fightsOnMap;
	}

	@Override
	public int getCapabilities() {
		return capabilities;
	}

	/**
	 * @param capabilities
	 *            the capabilities to set
	 */
	public void setCapabilities(int capabilities) {
		this.capabilities = capabilities;
	}

	@Override
	public String toString() {
		return "ManchouMap [mapid=" + mapid + ", x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", backgroundId=" + backgroundId + ", musicId=" + musicId + ", cells="
				+ cells.length + ", entities=" + entities.size() + ", fightType=" + fightType + ", team0Places=" + Arrays.toString(team0Places) + ", team1Places=" + Arrays.toString(team1Places)
				+ ", blocked=" + blocked + ", specBlocked=" + specBlocked + ", groupBlocked=" + groupBlocked + ", helpNeeded=" + helpNeeded + ", ended=" + ended + ", spectator=" + spectator
				+ ", startTimer=" + startTimer + ", duel=" + duel + "]";
	}

	@Override
	public String getArea() {
		return area;
	}

	@Override
	public String getSubarea() {
		return subarea;
	}

}
