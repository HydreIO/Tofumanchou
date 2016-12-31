package fr.aresrpg.tofumanchou.domain.event.aproach;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.character.AvailableCharacter;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.Arrays;

/**
 * 
 * @since
 */
public class CharacterListEvent implements Event<CharacterListEvent> {

	private static final EventBus<CharacterListEvent> BUS = new EventBus<>(CharacterListEvent.class);
	private Account client;
	private long subscriptionTime;
	private int persoTot;
	private AvailableCharacter[] characters;

	/**
	 * @param client
	 * @param subscriptionTime
	 * @param persoTot
	 * @param characters
	 */
	public CharacterListEvent(Account client, long subscriptionTime, int persoTot, AvailableCharacter[] characters) {
		this.client = client;
		this.subscriptionTime = subscriptionTime;
		this.persoTot = persoTot;
		this.characters = characters;
	}

	/**
	 * @return the subscriptionTime
	 */
	public long getSubscriptionTime() {
		return subscriptionTime;
	}

	/**
	 * @param subscriptionTime
	 *            the subscriptionTime to set
	 */
	public void setSubscriptionTime(long subscriptionTime) {
		this.subscriptionTime = subscriptionTime;
	}

	/**
	 * @return the persoTot
	 */
	public int getPersoTot() {
		return persoTot;
	}

	/**
	 * @param persoTot
	 *            the persoTot to set
	 */
	public void setPersoTot(int persoTot) {
		this.persoTot = persoTot;
	}

	/**
	 * @return the characters
	 */
	public AvailableCharacter[] getCharacters() {
		return characters;
	}

	/**
	 * @param characters
	 *            the characters to set
	 */
	public void setCharacters(AvailableCharacter... characters) {
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
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<CharacterListEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "CharacterListEvent [client=" + client + ", subscriptionTime=" + subscriptionTime + ", persoTot=" + persoTot + ", characters=" + Arrays.toString(characters) + "]";
	}

}
