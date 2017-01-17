package fr.aresrpg.tofumanchou.domain.event.map;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.infra.data.ManchouCell;

/**
 * 
 * @since
 */
public class FrameUpdateEvent implements Event<FrameUpdateEvent> {

	private static final EventBus<FrameUpdateEvent> BUS = new EventBus<>(FrameUpdateEvent.class);
	private Account client;
	private ManchouCell cell;
	private int frame;

	/**
	 * @param client
	 * @param cell
	 * @param frame
	 */
	public FrameUpdateEvent(Account client, ManchouCell cell, int frame) {
		this.client = client;
		this.cell = cell;
		this.frame = frame;
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
	 * @return the cell
	 */
	public ManchouCell getCell() {
		return cell;
	}

	/**
	 * @param cell
	 *            the cell to set
	 */
	public void setCell(ManchouCell cell) {
		this.cell = cell;
	}

	/**
	 * @return the frame
	 */
	public int getFrame() {
		return frame;
	}

	/**
	 * @param frame
	 *            the frame to set
	 */
	public void setFrame(int frame) {
		this.frame = frame;
	}

	@Override
	public EventBus<FrameUpdateEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "RessourceSpawnEvent [client=" + client + ", type=" + cell + "]";
	}

}
