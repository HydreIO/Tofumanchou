package fr.aresrpg.tofumanchou.domain.event.fight;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.game.FightResult;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class FightEndEvent implements Event<FightEndEvent> {

	private static final EventBus<FightEndEvent> BUS = new EventBus<>(FightEndEvent.class);
	private Account client;
	private int duration;
	private int firstPlayerId; // fight id
	private int bonus = -1;
	private FightResult result;

	/**
	 * @param client
	 * @param duration
	 * @param firstPlayerId
	 * @param bonus
	 * @param result
	 */
	public FightEndEvent(Account client, int duration, int firstPlayerId, int bonus, FightResult result) {
		this.client = client;
		this.duration = duration;
		this.firstPlayerId = firstPlayerId;
		this.bonus = bonus;
		this.result = result;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the firstPlayerId
	 */
	public int getFightId() {
		return firstPlayerId;
	}

	/**
	 * @param firstPlayerId
	 *            the firstPlayerId to set
	 */
	public void setFirstPlayerId(int firstPlayerId) {
		this.firstPlayerId = firstPlayerId;
	}

	/**
	 * @return the bonus
	 */
	public int getBonus() {
		return bonus;
	}

	/**
	 * @param bonus
	 *            the bonus to set
	 */
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	/**
	 * @return the result
	 */
	public FightResult getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(FightResult result) {
		this.result = result;
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
	public EventBus<FightEndEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
