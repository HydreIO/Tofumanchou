package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.Waypoint;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class ZaapGuiOpenEvent implements Event<ZaapGuiOpenEvent> {

	private static final EventBus<ZaapGuiOpenEvent> BUS = new EventBus<>(ZaapGuiOpenEvent.class);
	private Account client;
	private int respawnWaypoint;
	private Waypoint[] waypoints;

	/**
	 * @param client
	 * @param respawnWaypoint
	 * @param waypoints
	 */
	public ZaapGuiOpenEvent(Account client, int respawnWaypoint, Waypoint[] waypoints) {
		this.client = client;
		this.respawnWaypoint = respawnWaypoint;
		this.waypoints = waypoints;
	}

	/**
	 * @return the respawnWaypoint
	 */
	public int getRespawnWaypoint() {
		return respawnWaypoint;
	}

	/**
	 * @param respawnWaypoint
	 *            the respawnWaypoint to set
	 */
	public void setRespawnWaypoint(int respawnWaypoint) {
		this.respawnWaypoint = respawnWaypoint;
	}

	/**
	 * @return the waypoints
	 */
	public Waypoint[] getWaypoints() {
		return waypoints;
	}

	/**
	 * @param waypoints
	 *            the waypoints to set
	 */
	public void setWaypoints(Waypoint[] waypoints) {
		this.waypoints = waypoints;
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
	public EventBus<ZaapGuiOpenEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
