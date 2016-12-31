package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Player;

/**
 * Experimental event
 * 
 * @since
 */
public class PlayerStoleYourRessourceEvent implements Event<PlayerStoleYourRessourceEvent> {

	private static final EventBus<PlayerStoleYourRessourceEvent> BUS = new EventBus<>(PlayerStoleYourRessourceEvent.class);
	private Account client;
	private int cellId;
	private long time;
	private Player thief;

	/**
	 * @param client
	 * @param cellId
	 * @param time
	 * @param thief
	 */
	public PlayerStoleYourRessourceEvent(Account client, int cellId, long time, Player thief) {
		this.client = client;
		this.cellId = cellId;
		this.time = time;
		this.thief = thief;
	}

	/**
	 * @return the thief
	 */
	public Player getThief() {
		return thief;
	}

	/**
	 * @param thief
	 *            the thief to set
	 */
	public void setThief(Player thief) {
		this.thief = thief;
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
	public EventBus<PlayerStoleYourRessourceEvent> getBus() {
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
