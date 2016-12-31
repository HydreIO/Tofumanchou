package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * An event triggered when the perso lvl up
 * 
 * @since
 */
public class LevelUpEvent implements Event<LevelUpEvent> {

	private static final EventBus<LevelUpEvent> BUS = new EventBus<>(LevelUpEvent.class);
	private Account client;
	private int newLvl;

	/**
	 * @param client
	 * @param newLvl
	 */
	public LevelUpEvent(Account client, int newLvl) {
		this.client = client;
		this.newLvl = newLvl;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @param newLvl
	 *            the newLvl to set
	 */
	public void setNewLvl(int newLvl) {
		this.newLvl = newLvl;
	}

	/**
	 * @return the newLvl
	 */
	public int getNewLvl() {
		return newLvl;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<LevelUpEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return true;
	}

	@Override
	public String toString() {
		return "LevelUpEvent [client=" + client + ", newLvl=" + newLvl + "]";
	}

}
