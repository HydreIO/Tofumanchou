package fr.aresrpg.tofumanchou.domain.event.exchange;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.Exchange;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Player;

/**
 * 
 * @since
 */
public class ExchangeAcceptedEvent implements Event<ExchangeAcceptedEvent> {

	private static final EventBus<ExchangeAcceptedEvent> BUS = new EventBus<>(ExchangeAcceptedEvent.class);
	private Account client;
	private Player sender;
	private Player target;
	private Exchange exchange;

	/**
	 * @param client
	 * @param sender
	 * @param target
	 * @param exchange
	 */
	public ExchangeAcceptedEvent(Account client, Player sender, Player target, Exchange exchange) {
		this.client = client;
		this.sender = sender;
		this.target = target;
		this.exchange = exchange;
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
	 * @return the exchange
	 */
	public Exchange getExchange() {
		return exchange;
	}

	/**
	 * @param exchange
	 *            the exchange to set
	 */
	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
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
	public EventBus<ExchangeAcceptedEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
