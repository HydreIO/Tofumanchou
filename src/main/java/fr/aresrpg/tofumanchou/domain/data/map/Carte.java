package fr.aresrpg.tofumanchou.domain.data.map;

import fr.aresrpg.dofus.structures.game.FightType;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;

import java.util.Map;
import java.util.Set;

/**
 * 
 * @since
 */
public interface Carte { // pas envie de l'apeller Map et DofusMap est déja prit alors fuck :)

	int getMapId();

	int getX();

	int getY();

	int getWidth();

	int getHeight();

	int getBackgroundId();

	int getCapabilities();

	int getMusicId();

	Cell[] getCells();

	boolean isOutdoor();

	Set<Integer> getFightsOnMap();

	boolean isSpectator();

	int getFightStartTimer();

	boolean isDuel();

	Map<Long, Entity> getEntities();

	FightType getFightType();

	int[] getTeam0Places();

	int[] getTeam1Places();

	boolean isBlocked();

	boolean isSpecBlocked();

	boolean isGroupBlocked();

	boolean isHelpNeeded();

	boolean isEnded();

	Entity getCurrentTurn();

}
