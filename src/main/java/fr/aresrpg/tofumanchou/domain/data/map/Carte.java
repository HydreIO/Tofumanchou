package fr.aresrpg.tofumanchou.domain.data.map;

import fr.aresrpg.dofus.structures.game.FightSpawn;
import fr.aresrpg.dofus.structures.game.FightType;
import fr.aresrpg.dofus.util.StringJoiner;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Player;

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

	Set<FightSpawn> getFightsOnMap();

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

	String getArea();

	String getSubarea();

	default String getInfos() {
		return getArea() + " (" + getSubarea() + ")[" + getX() + "," + getY() + "]";
	}

	default String getCoordsInfos() {
		return new StringJoiner(",", "[", "]").add(getX()).add(getY()).toString();
	}

	default String getNameOf(long playerId) {
		Entity entity = getEntities().get(playerId);
		if (entity == null) return null;
		return ((Player) entity).getPseudo();
	}

	default long getIdOf(String name) {
		for (Entity p : getEntities().values())
			if (p instanceof Player && ((Player) p).getPseudo().equalsIgnoreCase(name)) return p.getUUID();
		return 0;
	}

}
