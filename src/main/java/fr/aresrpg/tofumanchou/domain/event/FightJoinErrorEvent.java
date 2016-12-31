package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.game.JoinError;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class FightJoinErrorEvent implements Event<FightJoinErrorEvent> {

	private static final EventBus<FightJoinErrorEvent> BUS = new EventBus<>(FightJoinErrorEvent.class);
	private Account client;
	private JoinError error;

	/**
	 * @param client
	 * @param error
	 */
	public FightJoinErrorEvent(Account client, JoinError error) {
		this.client = client;
		this.error = error;
	}

	/**
	 * @return the error
	 */
	public JoinError getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(JoinError error) {
		this.error = error;
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
	public EventBus<FightJoinErrorEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "FightJoinErrorEvent [client=" + client + ", error=" + error + "]";
	}

}
