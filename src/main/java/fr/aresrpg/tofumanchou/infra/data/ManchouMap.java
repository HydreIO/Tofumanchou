package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.structures.game.FightType;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.map.Carte;
import fr.aresrpg.tofumanchou.domain.data.map.Cell;

import java.util.Arrays;
import java.util.Map;

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
	private int musicId;
	private Cell[] cells;
	private Map<Long, Entity> entities;
	private FightType fightType;
	private int[] team0Places;
	private int[] team1Places;
	private boolean blocked;
	private boolean specBlocked;
	private boolean groupBlocked;
	private boolean helpNeeded;
	private boolean ended;
	private Entity currentTurn;

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
	public Cell[] getCells() {
		return cells;
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
	public void setCells(Cell[] cells) {
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
	public String toString() {
		return "ManchouMap [mapid=" + mapid + ", x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", backgroundId=" + backgroundId + ", musicId=" + musicId + ", cells="
				+ Arrays.toString(cells) + ", entities=" + entities + ", fightType=" + fightType + ", team0Places=" + Arrays.toString(team0Places) + ", team1Places=" + Arrays.toString(team1Places)
				+ ", blocked=" + blocked + ", specBlocked=" + specBlocked + ", groupBlocked=" + groupBlocked + ", helpNeeded=" + helpNeeded + ", ended=" + ended + ", currentTurn=" + currentTurn + "]";
	}

}
