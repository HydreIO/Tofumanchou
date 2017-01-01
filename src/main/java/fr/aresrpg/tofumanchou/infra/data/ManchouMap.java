package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.structures.game.FightSpawn;
import fr.aresrpg.dofus.structures.game.FightType;
import fr.aresrpg.dofus.structures.map.Cell;
import fr.aresrpg.dofus.structures.map.DofusMap;
import fr.aresrpg.tofumanchou.domain.data.MapsData;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.map.Carte;

import java.awt.Point;
import java.util.*;

/**
 * 
 * @since
 */
public class ManchouMap implements Carte {

	private int mapid;
	private int x;
	private int y;
	private int width;
	private int height;
	private int backgroundId;
	private boolean outdoor;
	private int musicId;
	private ManchouCell[] cells;
	private Map<Long, Entity> entities = new HashMap<>();
	private FightType fightType;
	private int[] team0Places;
	private int[] team1Places;
	private boolean blocked;
	private boolean specBlocked;
	private boolean groupBlocked;
	private boolean helpNeeded;
	private boolean ended;
	private Entity currentTurn;
	private boolean spectator;
	private int startTimer;
	private boolean duel;
	private Set<FightSpawn> fightsOnMap;
	private int capabilities;
	private String area;
	private String subarea;

	public static ManchouMap fromDofusMap(DofusMap map) {
		ManchouMap m = new ManchouMap();
		m.mapid = map.getId();
		Point coords = MapsData.getCoords(map.getId());
		m.x = coords.x;
		m.y = coords.y;
		m.width = map.getWidth();
		m.height = map.getHeight();
		m.backgroundId = map.getBackgroundId();
		m.musicId = map.getMusicId();
		m.cells = parseCells(map.getCells(), map.getWidth(), map.getHeight());
		m.outdoor = map.isOutdoor();
		m.capabilities = map.getCapabilities();
		m.area = MapsData.getArea(map.getId());
		m.subarea = MapsData.getSubArea(map.getId());
		return m;
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

	/**
	 * @return the mapid
	 */
	public int getMapid() {
		return mapid;
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
	public void setEntities(Map<Long, Entity> entities) {
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
				+ Arrays.toString(cells) + ", entities=" + entities + ", fightType=" + fightType + ", team0Places=" + Arrays.toString(team0Places) + ", team1Places=" + Arrays.toString(team1Places)
				+ ", blocked=" + blocked + ", specBlocked=" + specBlocked + ", groupBlocked=" + groupBlocked + ", helpNeeded=" + helpNeeded + ", ended=" + ended + ", currentTurn=" + currentTurn
				+ ", spectator=" + spectator + ", startTimer=" + startTimer + ", duel=" + duel + "]";
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
