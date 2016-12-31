package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.game.FightInfos;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.List;

/**
 * 
 * @since
 */
public class FightListEvent implements Event<FightListEvent> {

	private static final EventBus<FightListEvent> BUS = new EventBus<>(FightListEvent.class);
	private Account client;
	private List<FightInfos> fights;

	/**
	 * @param client
	 * @param list
	 */
	public FightListEvent(Account client, List<FightInfos> list) {
		this.client = client;
		this.fights = list;
	}

	/**
	 * @return the fights
	 */
	public List<FightInfos> getFights() {
		return fights;
	}

	/**
	 * @param fights
	 *            the fights to set
	 */
	public void setFights(List<FightInfos> fights) {
		this.fights = fights;
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
	public EventBus<FightListEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
