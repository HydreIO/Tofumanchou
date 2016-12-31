package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.Arrays;

/**
 * 
 * @since
 */
public class FightPositionsReceiveEvent implements Event<FightPositionsReceiveEvent> {

	private static final EventBus<FightPositionsReceiveEvent> BUS = new EventBus<>(FightPositionsReceiveEvent.class);
	private Account client;
	private int[] placesTeam0;
	private int[] placesTeam1;

	/**
	 * @param client
	 * @param placesTeam0
	 * @param placesTeam1
	 */
	public FightPositionsReceiveEvent(Account client, int[] placesTeam0, int[] placesTeam1) {
		this.client = client;
		this.placesTeam0 = placesTeam0;
		this.placesTeam1 = placesTeam1;
	}

	/**
	 * @return the placesTeam0
	 */
	public int[] getPlacesTeam0() {
		return placesTeam0;
	}

	/**
	 * @param placesTeam0
	 *            the placesTeam0 to set
	 */
	public void setPlacesTeam0(int[] placesTeam0) {
		this.placesTeam0 = placesTeam0;
	}

	/**
	 * @return the placesTeam1
	 */
	public int[] getPlacesTeam1() {
		return placesTeam1;
	}

	/**
	 * @param placesTeam1
	 *            the placesTeam1 to set
	 */
	public void setPlacesTeam1(int[] placesTeam1) {
		this.placesTeam1 = placesTeam1;
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
	public EventBus<FightPositionsReceiveEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "FightPositionsReceiveEvent [client=" + client + ", placesTeam0=" + Arrays.toString(placesTeam0) + ", placesTeam1=" + Arrays.toString(placesTeam1) + "]";
	}

}
