package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class DialogQuestionReceiveEvent implements Event<DialogQuestionReceiveEvent> {

	private static final EventBus<DialogQuestionReceiveEvent> BUS = new EventBus<>(DialogQuestionReceiveEvent.class);
	private Account client;
	private int question;
	private int[] response;
	private int[] questionParam;

	/**
	 * @param client
	 * @param question
	 * @param response
	 * @param questionParam
	 */
	public DialogQuestionReceiveEvent(Account client, int question, int[] response, int[] questionParam) {
		this.client = client;
		this.question = question;
		this.response = response;
		this.questionParam = questionParam;
	}

	/**
	 * @return the question
	 */
	public int getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(int question) {
		this.question = question;
	}

	/**
	 * @return the response
	 */
	public int[] getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(int[] response) {
		this.response = response;
	}

	/**
	 * @return the questionParam
	 */
	public int[] getQuestionParam() {
		return questionParam;
	}

	/**
	 * @param questionParam
	 *            the questionParam to set
	 */
	public void setQuestionParam(int[] questionParam) {
		this.questionParam = questionParam;
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
	public EventBus<DialogQuestionReceiveEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
