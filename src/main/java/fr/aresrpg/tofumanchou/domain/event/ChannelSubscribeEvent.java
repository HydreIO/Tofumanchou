package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.Chat;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.Arrays;

/**
 * 
 * @since
 */
public class ChannelSubscribeEvent implements Event<ChannelSubscribeEvent> {

	private static final EventBus<ChannelSubscribeEvent> BUS = new EventBus<>(ChannelSubscribeEvent.class);
	private Account client;
	private Chat[] channels;

	/**
	 * @param client
	 * @param channels
	 */
	public ChannelSubscribeEvent(Account client, Chat[] channels) {
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
	public EventBus<ChannelSubscribeEvent> getBus() {
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
