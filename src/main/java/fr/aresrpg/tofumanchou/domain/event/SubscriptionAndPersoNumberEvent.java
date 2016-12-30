package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.Map;

/**
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
		super();
		this.client = client;
		this.subTime = subTime;
		this.characters = characters;
	}

	/**
	 * @return the subTime
	 */
	public long getSubTime() {
		return subTime;
	}

	/**
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
		return true;
	}

	@Override
	public String toString() {
		return "SubscriptionAndPersoNumberEvent [client=" + client + ", subTime=" + subTime + ", characters=" + characters + "]";
	}

}
