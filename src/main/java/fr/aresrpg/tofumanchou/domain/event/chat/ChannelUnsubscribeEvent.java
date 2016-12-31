package fr.aresrpg.tofumanchou.domain.event.chat;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.Chat;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.Arrays;

/**
 * 
 * @since
 */
public class ChannelUnsubscribeEvent implements Event<ChannelUnsubscribeEvent> {

	private static final EventBus<ChannelUnsubscribeEvent> BUS = new EventBus<>(ChannelUnsubscribeEvent.class);
	private Account client;
	private Chat[] channels;

	/**
	 * @param client
	 * @param channels
	 */
	public ChannelUnsubscribeEvent(Account client, Chat[] channels) {
		super();
		this.client = client;
		this.channels = channels;
	}

	/**
	 * @return the channels
	 */
	public Chat[] getChannels() {
		return channels;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @param channels
	 *            the channels to set
	 */
	public void setChannels(Chat[] channels) {
		this.channels = channels;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<ChannelUnsubscribeEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return true;
	}

	@Override
	public String toString() {
		return "ChannelSubscribeEvent [client=" + client + ", channels=" + Arrays.toString(channels) + "]";
	}

}
