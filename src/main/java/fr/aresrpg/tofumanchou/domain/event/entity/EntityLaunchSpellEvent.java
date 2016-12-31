package fr.aresrpg.tofumanchou.domain.event.entity;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class EntityLaunchSpellEvent implements Event<EntityLaunchSpellEvent> {

	private static final EventBus<EntityLaunchSpellEvent> BUS = new EventBus<>(EntityLaunchSpellEvent.class);
	private Account client;
	private int spellId;
	private int cellId;
	private int spellLvl;

	/**
	 * @param client
	 * @param spellId
	 * @param cellId
	 * @param spellLvl
	 */
	public EntityLaunchSpellEvent(Account client, int spellId, int cellId, int spellLvl) {
		this.client = client;
		this.spellId = spellId;
		this.cellId = cellId;
		this.spellLvl = spellLvl;
	}

	/**
	 * @return the spellId
	 */
	public int getSpellId() {
		return spellId;
	}

	/**
	 * @param spellId
	 *            the spellId to set
	 */
	public void setSpellId(int spellId) {
		this.spellId = spellId;
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
	 * @return the spellLvl
	 */
	public int getSpellLvl() {
		return spellLvl;
	}

	/**
	 * @param spellLvl
	 *            the spellLvl to set
	 */
	public void setSpellLvl(int spellLvl) {
		this.spellLvl = spellLvl;
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
	public EventBus<EntityLaunchSpellEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "EntityLaunchSpellEvent [client=" + client + ", spellId=" + spellId + ", cellId=" + cellId + ", spellLvl=" + spellLvl + "]";
	}

}
