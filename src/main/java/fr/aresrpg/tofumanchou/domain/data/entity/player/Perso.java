package fr.aresrpg.tofumanchou.domain.data.entity.player;

import fr.aresrpg.dofus.structures.Chat;
import fr.aresrpg.dofus.structures.Friend;
import fr.aresrpg.dofus.structures.job.Jobs;
import fr.aresrpg.tofumanchou.domain.data.*;
import fr.aresrpg.tofumanchou.domain.data.enums.Spells;
import fr.aresrpg.tofumanchou.domain.data.inventory.Inventory;
import fr.aresrpg.tofumanchou.domain.data.map.Carte;

import java.util.List;
import java.util.Map;

/**
 * 
 * @since
 */
public interface Perso extends Player {

	Account getAccount();

	Inventory getInventory();

	void connect();

	int getXp();

	int getXpMin();

	int getXpMax();

	Job getJob();

	Map<Jobs, Job> getJobs();

	int getStatsPoints();

	int getSpellsPoints();

	int getEnergy();

	int getEnergyMax();

	List<String> getOfflinesFriends();

	List<Friend> getOnlineFriends();

	Map<Chat, Boolean> getChannels();

	Carte getMap();

	Map<Spells, Spell> getSpells();

	int getPods();

	int getMaxPods();
}
