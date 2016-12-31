package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Perso;

/**
 * An event triggered when the server confirm the selection of a perso
 * 
 * @since
 */
public class PersoSelectEvent implements Event<PersoSelectEvent> {

	private static final EventBus<PersoSelectEvent> BUS = new EventBus<>(PersoSelectEvent.class);
	private Account client;
	private Perso perso;

	/**
	 * @param client
	 * @param perso
	 */
	public PersoSelectEvent(Account client, Perso perso) {
		this.client = client;
		this.perso = perso;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @param perso
	 *            the perso to set
	 */
	public void setPerso(Perso perso) {
		this.perso = perso;
	}

	/**
	 * @return the perso
	 */
	public Perso getPerso() {
		return perso;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<PersoSelectEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return true;
	}

}
