package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.map.Carte;

/**
 * Be carrefull with this event, dofus send the entities/frames/infos etc in separate packets so the map as it is here is empty
 * 
 * @since
 */
public class MapJoinEvent implements Event<MapJoinEvent> {

	private static final EventBus<MapJoinEvent> BUS = new EventBus<>(MapJoinEvent.class);
	private Account client;
	private Carte map;

	/**
	 * @param client
	 * @param map
	 */
	public MapJoinEvent(Account client, Carte map) {
		this.client = client;
		this.map = map;
	}

	/**
	 * @return the map
	 */
	public Carte getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(Carte map) {
		this.map = map;
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
	public EventBus<MapJoinEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
