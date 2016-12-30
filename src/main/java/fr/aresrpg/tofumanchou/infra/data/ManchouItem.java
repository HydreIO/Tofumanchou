package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.structures.game.Effect;
import fr.aresrpg.dofus.structures.item.ItemCategory;
import fr.aresrpg.tofumanchou.domain.data.item.Item;

/**
 * 
 * @since
 */
public class ManchouItem implements Item {

	private long uuid;
	private String name;
	private String desc;
	private ItemCategory category;
	private int typeId;
	private int amount;
	private int position;
	private Effect[] effects;
	private int price;
	private int skin;
	private int remainingHours;
	private int pods;

	/**
	 * @param uuid
	 * @param name
	 * @param desc
	 * @param category
	 * @param typeId
	 * @param amount
	 * @param position
	 * @param effects
	 * @param price
	 * @param skin
	 * @param remainingHours
	 * @param pods
	 */
	public ManchouItem(long uuid, String name, String desc, ItemCategory category, int typeId, int amount, int position, Effect[] effects, int price, int skin, int remainingHours, int pods) {
		this.uuid = uuid;
		this.name = name;
		this.desc = desc;
		this.category = category;
		this.typeId = typeId;
		this.amount = amount;
		this.position = position;
		this.effects = effects;
		this.price = price;
		this.skin = skin;
		this.remainingHours = remainingHours;
		this.pods = pods;
	}

	@Override
	public long getUUID() {
		return uuid;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public ItemCategory getCategory() {
		return category;
	}

	@Override
	public String showInfos() {
		return "{x" + amount + " " + getName() + "(id:" + getUUID() + ")(pods:" + (getPods() * getAmount()) + ")}";
	}

	@Override
	public int getTypeId() {
		return typeId;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public Effect[] getEffects() {
		return effects;
	}

	@Override
	public int getPrice() {
		return price;
	}

	@Override
	public int getSkin() {
		return skin;
	}

	@Override
	public int getRemainingHours() {
		return remainingHours;
	}

	@Override
	public boolean isStackableWith(Item i) {
		return i.getTypeId() == typeId && i.getSkin() == skin && sameEffect(i);
	}

	public boolean sameEffect(Item it) {
		if (effects == null && it.getEffects() == null) return true;
		if (effects.length != it.getEffects().length) return false;
		for (int i = 0; i < effects.length; i++)
			if (!effects[i].equals(it.getEffects()[i])) return false;
		return true;
	}

	@Override
	public void setAmount(int i) {
		amount = i;
	}

	@Override
	public int getPods() {
		return pods;
	}

}
