package fr.aresrpg.tofumanchou.domain.event.duel;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Player;

/**
 * 
 * @since
 */
public class DuelRequestEvent implements Event<DuelRequestEvent> {

	private static final EventBus<DuelRequestEvent> BUS = new EventBus<>(DuelRequestEvent.class);
	private Account client;
	private Player sender, target;

	/**
	 * @param client
	 * @param sender
	 * @param target
	 */
	public DuelRequestEvent(Account client, Player sender, Player target) {
		this.client = client;
		this.sender = sender;
		this.target = target;
	}

	/**
	 * @return the sender
	 */
	public Player getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(Player sender) {
		this.sender = sender;
	}

	/**
	 * @return the target
	 */
	public Player getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(Player target) {
		this.target = target;
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
	public EventBus<DuelRequestEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
