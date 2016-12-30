package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.protocol.exchange.server.ExchangeCreatePacket.ExchangeData;
import fr.aresrpg.dofus.structures.Exchange;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class ExchangeCreateEvent implements Event<ExchangeCreateEvent> {

	private static final EventBus<ExchangeCreateEvent> BUS = new EventBus<>(ExchangeCreateEvent.class);
	private Account client;
	private Exchange type;
	private ExchangeData data;
	private boolean success;

	/**
	 * @param client
	 * @param type
	 * @param data
	 * @param success
	 */
	public ExchangeCreateEvent(Account client, Exchange type, ExchangeData data, boolean success) {
		this.client = client;
		this.type = type;
		this.data = data;
		this.success = success;
	}

	/**
	 * @return the type
	 */
	public Exchange getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Exchange type) {
		this.type = type;
	}

	/**
	 * @return the data
	 */
	public ExchangeData getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(ExchangeData data) {
		this.data = data;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
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
	public EventBus<ExchangeCreateEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "ExchangeCreateEvent [client=" + client + ", type=" + type + ", data=" + data + ", success=" + success + "]";
	}

}
