package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.game.FightType;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class FightJoinEvent implements Event<FightJoinEvent> {

	private static final EventBus<FightJoinEvent> BUS = new EventBus<>(FightJoinEvent.class);
	private Account client;
	private FightType fightType;
	private boolean isSpectator;
	private int startTimer;
	private boolean cancelButton;
	private boolean isDuel;

	/**
	 * @param client
	 * @param fightType
	 * @param isSpectator
	 * @param startTimer
	 * @param cancelButton
	 * @param isDuel
	 */
	public FightJoinEvent(Account client, FightType fightType, boolean isSpectator, int startTimer, boolean cancelButton, boolean isDuel) {
		this.client = client;
		this.fightType = fightType;
		this.isSpectator = isSpectator;
		this.startTimer = startTimer;
		this.cancelButton = cancelButton;
		this.isDuel = isDuel;
	}

	/**
	 * @return the fightType
	 */
	public FightType getFightType() {
		return fightType;
	}

	/**
	 * @param fightType
	 *            the fightType to set
	 */
	public void setFightType(FightType fightType) {
		this.fightType = fightType;
	}

	/**
	 * @return the isSpectator
	 */
	public boolean isSpectator() {
		return isSpectator;
	}

	/**
	 * @param isSpectator
	 *            the isSpectator to set
	 */
	public void setSpectator(boolean isSpectator) {
		this.isSpectator = isSpectator;
	}

	/**
	 * @return the startTimer
	 */
	public int getStartTimer() {
		return startTimer;
	}

	/**
	 * @param startTimer
	 *            the startTimer to set
	 */
	public void setStartTimer(int startTimer) {
		this.startTimer = startTimer;
	}

	/**
	 * @return the cancelButton
	 */
	public boolean isCancelButton() {
		return cancelButton;
	}

	/**
	 * @param cancelButton
	 *            the cancelButton to set
	 */
	public void setCancelButton(boolean cancelButton) {
		this.cancelButton = cancelButton;
	}

	/**
	 * @return the isDuel
	 */
	public boolean isDuel() {
		return isDuel;
	}

	/**
	 * @param isDuel
	 *            the isDuel to set
	 */
	public void setDuel(boolean isDuel) {
		this.isDuel = isDuel;
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
	public EventBus<FightJoinEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "FightJoinEvent [client=" + client + ", fightType=" + fightType + ", isSpectator=" + isSpectator + ", startTimer=" + startTimer + ", cancelButton=" + cancelButton + ", isDuel=" + isDuel
				+ "]";
	}

}
