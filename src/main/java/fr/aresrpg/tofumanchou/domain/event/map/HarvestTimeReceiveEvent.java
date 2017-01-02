package fr.aresrpg.tofumanchou.domain.event.map;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Player;

/**
 * 
 * @since
 */
public class HarvestTimeReceiveEvent implements Event<HarvestTimeReceiveEvent> {

	private static final EventBus<HarvestTimeReceiveEvent> BUS = new EventBus<>(HarvestTimeReceiveEvent.class);
	private Account client;
	private int cellId;
	private long time;
	private Player player;

	/**
	 * @param client
	 * @param cellId
	 * @param time
	 * @param player
	 */
	public HarvestTimeReceiveEvent(Account client, int cellId, long time, Player player) {
		this.client = client;
		this.cellId = cellId;
		this.time = time;
		this.player = player;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the cellId
	 */
	public int getCellId() {
		return cellId;
	}

	/**
	 * @param cellId
	 *            the cellId to set
	 */
	public void setCellId(int cellId) {
		this.cellId = cellId;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(long time) {
		this.time = time;
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
	public EventBus<HarvestTimeReceiveEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "HarvestTimeReceiveEvent [client=" + client + ", cellId=" + cellId + ", time=" + time + "]";
	}

}
