package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.game.Effect;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;

import java.util.Set;

/**
 * 
 * @since
 */
public class EntitiesReceiveSpellEffectEvent implements Event<EntitiesReceiveSpellEffectEvent> {

	private static final EventBus<EntitiesReceiveSpellEffectEvent> BUS = new EventBus<>(EntitiesReceiveSpellEffectEvent.class);
	private Account client;
	private Set<Entity> entities;
	private Effect effect;

	/**
	 * @param client
	 * @param entities
	 * @param effect
	 */
	public EntitiesReceiveSpellEffectEvent(Account client, Set<Entity> entities, Effect effect) {
		this.client = client;
		this.entities = entities;
		this.effect = effect;
	}

	/**
	 * @return the entities
	 */
	public Set<Entity> getEntities() {
		return entities;
	}

	/**
	 * @param entities
	 *            the entities to set
	 */
	public void setEntities(Set<Entity> entities) {
		this.entities = entities;
	}

	/**
	 * @return the effect
	 */
	public Effect getEffect() {
		return effect;
	}

	/**
	 * @param effect
	 *            the effect to set
	 */
	public void setEffect(Effect effect) {
		this.effect = effect;
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
	public EventBus<EntitiesReceiveSpellEffectEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "EntitiesReceiveSpellEffectEvent [client=" + client + ", entities=" + entities + ", effect=" + effect + "]";
	}

}
