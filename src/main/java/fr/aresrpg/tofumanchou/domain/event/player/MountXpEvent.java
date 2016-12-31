package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class MountXpEvent implements Event<MountXpEvent> {

	private static final EventBus<MountXpEvent> BUS = new EventBus<>(MountXpEvent.class);
	private Account client;
	private int percent;

	/**
	 * @param client
	 * @param percent
	 */
	public MountXpEvent(Account client, int percent) {
		this.client = client;
		this.percent = percent;
	}

	/**
	 * @return the percent
	 */
	public int getPercent() {
		return percent;
	}

	/**
	 * @param percent
	 *            the percent to set
	 */
	public void setPercent(int percent) {
		this.percent = percent;
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
	public EventBus<MountXpEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "MountXpEvent [client=" + client + ", percent=" + percent + "]";
	}

}
