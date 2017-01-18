package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * An event triggered when a server change his state
 * 
 * @since
 */
public class GuildInvitedEvent implements Event<GuildInvitedEvent> {

	private static final EventBus<GuildInvitedEvent> BUS = new EventBus<>(GuildInvitedEvent.class);
	private Account client;
	private String sender;
	private String guild;

	public GuildInvitedEvent(Account client, String sender, String guild) {
		this.client = client;
		this.sender = sender;
		this.guild = guild;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * @return the guild
	 */
	public String getGuild() {
		return guild;
	}

	/**
	 * @param guild
	 *            the guild to set
	 */
	public void setGuild(String guild) {
		this.guild = guild;
	}

	@Override
	public EventBus<GuildInvitedEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
