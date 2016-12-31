package fr.aresrpg.tofumanchou.domain.event.fight;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.game.FightSpawn;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class FightSpawnEvent implements Event<FightSpawnEvent> {

	private static final EventBus<FightSpawnEvent> BUS = new EventBus<>(FightSpawnEvent.class);
	private Account client;
	private FightSpawn fight;

	/**
	 * @param client
	 * @param fight
	 */
	public FightSpawnEvent(Account client, FightSpawn fight) {
		this.client = client;
		this.fight = fight;
	}

	/**
	 * @return the fight
	 */
	public FightSpawn getFight() {
		return fight;
	}

	/**
	 * @param fight
	 *            the fight to set
	 */
	public void setFight(FightSpawn fight) {
		this.fight = fight;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<FightSpawnEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
