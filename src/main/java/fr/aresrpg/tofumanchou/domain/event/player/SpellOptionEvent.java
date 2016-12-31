package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class SpellOptionEvent implements Event<SpellOptionEvent> {

	private static final EventBus<SpellOptionEvent> BUS = new EventBus<>(SpellOptionEvent.class);
	private Account client;
	private boolean canUseAllSpells;

	/**
	 * @param client
	 * @param canUseAllSpells
	 */
	public SpellOptionEvent(Account client, boolean canUseAllSpells) {
		this.client = client;
		this.canUseAllSpells = canUseAllSpells;
	}

	public boolean canUseAllSpells() {
		return canUseAllSpells;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @param canUseAllSpells
	 *            the canUseAllSpells to set
	 */
	public void setCanUseAllSpells(boolean canUseAllSpells) {
		this.canUseAllSpells = canUseAllSpells;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<SpellOptionEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "SpellOptionEvent [client=" + client + ", canUseAllSpells=" + canUseAllSpells + "]";
	}

}
