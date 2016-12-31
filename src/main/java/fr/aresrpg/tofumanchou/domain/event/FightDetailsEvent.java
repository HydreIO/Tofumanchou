package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.game.FightDetail;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @since
 */
public class FightDetailsEvent implements Event<FightDetailsEvent> {

	private static final EventBus<FightDetailsEvent> BUS = new EventBus<>(FightDetailsEvent.class);
	private Account client;
	private long id;
	private List<FightDetail> t0 = new ArrayList<>();
	private List<FightDetail> t1 = new ArrayList<>();

	/**
	 * @param client
	 * @param id
	 * @param t0
	 * @param t1
	 */
	public FightDetailsEvent(Account client, long id, List<FightDetail> t0, List<FightDetail> t1) {
		this.client = client;
		this.id = id;
		this.t0 = t0;
		this.t1 = t1;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the t0
	 */
	public List<FightDetail> getT0() {
		return t0;
	}

	/**
	 * @param t0
	 *            the t0 to set
	 */
	public void setT0(List<FightDetail> t0) {
		this.t0 = t0;
	}

	/**
	 * @return the t1
	 */
	public List<FightDetail> getT1() {
		return t1;
	}

	/**
	 * @param t1
	 *            the t1 to set
	 */
	public void setT1(List<FightDetail> t1) {
		this.t1 = t1;
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
	public EventBus<FightDetailsEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
