package fr.aresrpg.tofumanchou.domain.event.chat;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.Chat;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class ChatMsgEvent implements Event<ChatMsgEvent> {

	private static final EventBus<ChatMsgEvent> BUS = new EventBus<>(ChatMsgEvent.class);
	private Account client;
	private Chat chat;
	private int playerId;
	private String pseudo;
	private String msg;

	/**
	 * @param client
	 * @param chat
	 * @param playerId
	 * @param pseudo
	 * @param msg
	 */
	public ChatMsgEvent(Account client, Chat chat, int playerId, String pseudo, String msg) {
		this.client = client;
		this.chat = chat;
		this.playerId = playerId;
		this.pseudo = pseudo;
		this.msg = msg;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @param chat
	 *            the chat to set
	 */
	public void setChat(Chat chat) {
		this.chat = chat;
	}

	/**
	 * @param playerId
	 *            the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * @param pseudo
	 *            the pseudo to set
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the chat
	 */
	public Chat getChat() {
		return chat;
	}

	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<ChatMsgEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return true;
	}

	@Override
	public String toString() {
		return "ChatMsgEvent [client=" + client + ", chat=" + chat + ", playerId=" + playerId + ", pseudo=" + pseudo + ", msg=" + msg + "]";
	}

}
