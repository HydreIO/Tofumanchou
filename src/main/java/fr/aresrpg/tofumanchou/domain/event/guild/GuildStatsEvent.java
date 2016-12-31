package fr.aresrpg.tofumanchou.domain.event.guild;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.guild.Guild;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class GuildStatsEvent implements Event<GuildStatsEvent> {

	private static final EventBus<GuildStatsEvent> BUS = new EventBus<>(GuildStatsEvent.class);
	private Account client;
	private Guild guild;

	/**
	 * @param client
	 * @param guild
	 */
	public GuildStatsEvent(Account client, Guild guild) {
		this.client = client;
		this.guild = guild;
	}

	/**
	 * @return the guild
	 */
	public Guild getGuild() {
		return guild;
	}

	/**
	 * @param guild
	 *            the guild to set
	 */
	public void setGuild(Guild guild) {
		this.guild = guild;
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
	public EventBus<GuildStatsEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "GuildStatsEvent [client=" + client + ", guild=" + guild + "]";
	}

}
