package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.game.FightChallenge;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class FightChallengeEvent implements Event<FightChallengeEvent> {

	private static final EventBus<FightChallengeEvent> BUS = new EventBus<>(FightChallengeEvent.class);
	private Account client;
	private FightChallenge challenge;

	/**
	 * @param client
	 * @param challenge
	 */
	public FightChallengeEvent(Account client, FightChallenge challenge) {
		this.client = client;
		this.challenge = challenge;
	}

	/**
	 * @return the challenge
	 */
	public FightChallenge getChallenge() {
		return challenge;
	}

	/**
	 * @param challenge
	 *            the challenge to set
	 */
	public void setChallenge(FightChallenge challenge) {
		this.challenge = challenge;
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
	public EventBus<FightChallengeEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "FightChallengeEvent [client=" + client + ", challenge=" + challenge + "]";
	}

}
