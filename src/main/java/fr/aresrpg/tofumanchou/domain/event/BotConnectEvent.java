package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Perso;

/**
 * An event triggered when a server change his state
 * 
 * @since
 */
public class BotConnectEvent implements Event<BotConnectEvent> {

	private static final EventBus<BotConnectEvent> BUS = new EventBus<>(BotConnectEvent.class);
	private Account client;
	private Perso perso;

	/**
	 * @param client
	 * @param perso
	 */
	public BotConnectEvent(Account client, Perso perso) {
		this.client = client;
		this.perso = perso;
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
	 * @return the perso
	 */
	public Perso getPerso() {
		return perso;
	}

	/**
	 * @param perso
	 *            the perso to set
	 */
	public void setPerso(Perso perso) {
		this.perso = perso;
	}

	@Override
	public EventBus<BotConnectEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
