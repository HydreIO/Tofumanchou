package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.infra.data.ManchouPerso;

/**
 * An event triggered when a server change his state
 * 
 * @since
 */
public class PersoMoveEndEvent implements Event<PersoMoveEndEvent> {

	private static final EventBus<PersoMoveEndEvent> BUS = new EventBus<>(PersoMoveEndEvent.class);
	private Account client;
	private ManchouPerso perso;

	public PersoMoveEndEvent(Account client, ManchouPerso perso) {
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
	public ManchouPerso getPerso() {
		return perso;
	}

	/**
	 * @param perso
	 *            the perso to set
	 */
	public void setPerso(ManchouPerso perso) {
		this.perso = perso;
	}

	@Override
	public EventBus<PersoMoveEndEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
