package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.Spell;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.List;

/**
 * 
 * @since
 */
public class SpellListEvent implements Event<SpellListEvent> {

	private static final EventBus<SpellListEvent> BUS = new EventBus<>(SpellListEvent.class);
	private Account client;
	private List<Spell> spells;

	/**
	 * @param client
	 * @param spells
	 */
	public SpellListEvent(Account client, List<Spell> spells) {
		this.client = client;
		this.spells = spells;
	}

	/**
	 * @return the spells
	 */
	public List<Spell> getSpells() {
		return spells;
	}

	/**
	 * @param spells
	 *            the spells to set
	 */
	public void setSpells(List<Spell> spells) {
		this.spells = spells;
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
	public EventBus<SpellListEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "SpellListEvent [client=" + client + ", spells=" + spells + "]";
	}

}
