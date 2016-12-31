package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.protocol.game.server.GameTeamPacket.TeamEntity;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class FightTeamEvent implements Event<FightTeamEvent> {

	private static final EventBus<FightTeamEvent> BUS = new EventBus<>(FightTeamEvent.class);
	private Account client;
	private long fightId;
	private TeamEntity[] teams;

	/**
	 * @param client
	 * @param fightId
	 * @param teams
	 */
	public FightTeamEvent(Account client, long fightId, TeamEntity[] teams) {
		this.client = client;
		this.fightId = fightId;
		this.teams = teams;
	}

	/**
	 * @return the fightId
	 */
	public long getFightId() {
		return fightId;
	}

	/**
	 * @param fightId
	 *            the fightId to set
	 */
	public void setFightId(long fightId) {
		this.fightId = fightId;
	}

	/**
	 * @return the teams
	 */
	public TeamEntity[] getTeams() {
		return teams;
	}

	/**
	 * @param teams
	 *            the teams to set
	 */
	public void setTeams(TeamEntity[] teams) {
		this.teams = teams;
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
	public EventBus<FightTeamEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
