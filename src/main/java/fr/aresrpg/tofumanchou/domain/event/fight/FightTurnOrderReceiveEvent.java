package fr.aresrpg.tofumanchou.domain.event.fight;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;

/**
 * 
 * @since
 */
public class FightTurnOrderReceiveEvent implements Event<FightTurnOrderReceiveEvent> {

	private static final EventBus<FightTurnOrderReceiveEvent> BUS = new EventBus<>(FightTurnOrderReceiveEvent.class);
	private Account client;
	private Entity[] turns;

	/**
	 * @param client
	 * @param turns
	 */
	public FightTurnOrderReceiveEvent(Account client, Entity[] turns) {
		this.client = client;
		this.turns = turns;
	}

	/**
	 * @return the turns
	 */
	public Entity[] getTurns() {
		return turns;
	}

	/**
	 * @param turns
	 *            the turns to set
	 */
	public void setTurns(Entity[] turns) {
		this.turns = turns;
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
	public EventBus<FightTurnOrderReceiveEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
