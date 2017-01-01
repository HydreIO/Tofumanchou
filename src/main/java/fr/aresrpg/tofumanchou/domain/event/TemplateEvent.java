package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * An event triggered when a server change his state
 * 
 * @since
 */
public class TemplateEvent implements Event<TemplateEvent> {

	private static final EventBus<TemplateEvent> BUS = new EventBus<>(TemplateEvent.class);
	private Account client;

	@Override
	public EventBus<TemplateEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
