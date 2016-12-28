package fr.aresrpg.tofumanchou.domain.data.entity.player;

import fr.aresrpg.dofus.structures.PlayerRestriction;
import fr.aresrpg.dofus.structures.Rank;
import fr.aresrpg.dofus.structures.game.Alignement;
import fr.aresrpg.dofus.structures.server.Server;
import fr.aresrpg.dofus.structures.stat.Stat;
import fr.aresrpg.dofus.structures.stat.StatValue;
import fr.aresrpg.tofumanchou.domain.data.entity.*;
import fr.aresrpg.tofumanchou.domain.data.enums.Classe;
import fr.aresrpg.tofumanchou.domain.data.enums.Genre;

import java.util.Map;

/**
 * 
 * @since
 */
public interface Player extends Entity {

	String getPseudo();

	Classe getClasse();

	EntityColor getColors();

	Genre getSex();

	int getLevel();

	int getLife();

	int getLifeMax();

	int getInitiative();

	void setInitiative();

	Alignement getAlignement();

	Rank getRank();

	int getProspection();

	Map<Stat, StatValue> getStats();

	StatValue getStat(Stat stat);

	EntityAccessory[] getAccessories();

	Server getServer();

	boolean isMerchant();

	boolean isDead();

	int getDeathCount();

	int getLvlMax();

	int getGuild();

	int getTeam();

	int getAura();

	int getEmot();

	int getEmotTimer();

	String getGuildName();

	String[] getEmblem();

	PlayerRestriction getRestriction();

}
