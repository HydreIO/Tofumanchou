package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.InfosMsgType;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class InfoMessageEvent implements Event<InfoMessageEvent> {

	private static final EventBus<InfoMessageEvent> BUS = new EventBus<>(InfoMessageEvent.class);
	private Account client;
	private InfosMsgType type;
	private int messageId;
	private String extraDatas;

	/**
	 * @param client
	 * @param type
	 * @param messageId
	 * @param extraDatas
	 */
	public InfoMessageEvent(Account client, InfosMsgType type, int messageId, String extraDatas) {
		this.client = client;
		this.type = type;
		this.messageId = messageId;
		this.extraDatas = extraDatas;
	}

	/**
	 * @return the type
	 */
	public InfosMsgType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(InfosMsgType type) {
		this.type = type;
	}

	/**
	 * @return the messageId
	 */
	public int getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the extraDatas
	 */
	public String getExtraDatas() {
		return extraDatas;
	}

	/**
	 * @param extraDatas
	 *            the extraDatas to set
	 */
	public void setExtraDatas(String extraDatas) {
		this.extraDatas = extraDatas;
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
	public EventBus<InfoMessageEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "InfoMessageEvent [client=" + client + ", type=" + type + ", messageId=" + messageId + ", extraDatas=" + extraDatas + "]";
	}

}
