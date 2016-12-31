package fr.aresrpg.tofumanchou.domain.event.map;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.map.Cell;

/**
 * 
 * @since
 */
public class RessourceSpawnEvent implements Event<RessourceSpawnEvent> { // ASYNCHRONOUS EVENT

	private static final EventBus<RessourceSpawnEvent> BUS = new EventBus<>(RessourceSpawnEvent.class);
	private Account client;
	private Cell type;

	/**
	 * @param client
	 * @param type
	 */
	public RessourceSpawnEvent(Account client, Cell type) {
		this.client = client;
		this.type = type;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @return the type
	 */
	public Cell getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Cell type) {
		this.type = type;
	}

	@Override
	public EventBus<RessourceSpawnEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return true;
	}

	@Override
	public String toString() {
		return "RessourceSpawnEvent [client=" + client + ", type=" + type + "]";
	}

}
