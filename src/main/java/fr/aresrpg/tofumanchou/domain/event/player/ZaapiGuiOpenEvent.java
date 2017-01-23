package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.Waypoint;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class ZaapiGuiOpenEvent implements Event<ZaapiGuiOpenEvent> {

	private static final EventBus<ZaapiGuiOpenEvent> BUS = new EventBus<>(ZaapiGuiOpenEvent.class);
	private Account client;
	private int current;
	private Waypoint[] waypoints;

	/**
	 * @param client
	 * @param respawnWaypoint
	 * @param waypoints
	 */
	public ZaapiGuiOpenEvent(Account client, int respawnWaypoint, Waypoint[] waypoints) {
		this.client = client;
		this.current = respawnWaypoint;
		this.waypoints = waypoints;
	}

	/**
	 * @return the respawnWaypoint
	 */
	public int getCurrent() {
		return current;
	}

	/**
	 * @param current
	 *            the current to set
	 */
	public void setCurrent(int current) {
		this.current = current;
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
	public EventBus<ZaapiGuiOpenEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
