package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.Map;

/**
 * Event received before the selection of the server
 * 
 * @since
 */
public class SubscriptionAndPersoNumberEvent implements Event<SubscriptionAndPersoNumberEvent> {

	private static final EventBus<SubscriptionAndPersoNumberEvent> BUS = new EventBus<>(SubscriptionAndPersoNumberEvent.class);
	private Account client;
	private long subTime;
	private Map<Integer, Integer> characters;

	/**
	 * @param client
	 * @param subTime
	 * @param characters
	 */
	public SubscriptionAndPersoNumberEvent(Account client, long subTime, Map<Integer, Integer> characters) {
		this.client = client;
		this.subTime = subTime;
		this.characters = characters;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @param subTime
	 *            the subTime to set
	 */
	public void setSubTime(long subTime) {
		this.subTime = subTime;
	}

	/**
	 * @param characters
	 *            the characters to set
	 */
	public void setCharacters(Map<Integer, Integer> characters) {
		this.characters = characters;
	}

	/**
	 * @return the subTime in ms
	 */
	public long getSubTime() {
		return subTime;
	}

	/**
	 * A map serverid,persoCount
	 * 
	 * @return the characters
	 */
	public Map<Integer, Integer> getCharacters() {
		return characters;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<SubscriptionAndPersoNumberEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "SubscriptionAndPersoNumberEvent [client=" + client + ", subTime=" + subTime + ", characters=" + characters + "]";
	}

}
