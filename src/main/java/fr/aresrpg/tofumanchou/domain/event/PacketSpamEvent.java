package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;

/**
 * An event triggered when a server change his state
 * 
 * @since
 */
public class PacketSpamEvent implements Event<PacketSpamEvent> {

	private static final EventBus<PacketSpamEvent> BUS = new EventBus<>(PacketSpamEvent.class);
	private Class clazz;
	private int count;

	/**
	 * @param client
	 * @param clazz
	 * @param count
	 */
	public PacketSpamEvent(Class clazz, int count) {
		this.clazz = clazz;
		this.count = count;
	}

	/**
	 * @return the clazz
	 */
	public Class getClazz() {
		return clazz;
	}

	/**
	 * @param clazz
	 *            the clazz to set
	 */
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public EventBus<PacketSpamEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "PacketSpamEvent [clazz=" + clazz + ", count=" + count + "]";
	}

}
