package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.structures.game.Effect;
import fr.aresrpg.dofus.structures.item.ItemCategory;
import fr.aresrpg.tofumanchou.domain.data.ItemsData;
import fr.aresrpg.tofumanchou.domain.data.ItemsData.LangItem;
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

	private ManchouItem() {

	}

	public static ManchouItem fromProtocolItem(fr.aresrpg.dofus.structures.item.Item item) {
		ManchouItem i = new ManchouItem();
		i.uuid = item.getUid();
		System.out.println(item);
		if (item.getItemTypeId() == -1) return i;
		LangItem langItem = ItemsData.get(item.getItemTypeId());
		i.name = langItem.getName();
		i.desc = langItem.getDesc();
		i.category = langItem.getCategory();
		i.typeId = item.getItemTypeId();
		i.amount = item.getQuantity();
		i.position = item.getPosition();
		i.effects = item.getEffects();
		i.price = item.getPrice();
		i.skin = item.getSkin();
		i.remainingHours = item.getRemainingHours();
		i.pods = langItem.getPod();
		return i;
	}

	public fr.aresrpg.dofus.structures.item.Item serializeProtocol() {
		return new fr.aresrpg.dofus.structures.item.Item(uuid, typeId, amount, position, effects, price, skin);
	}

	@Override
	public long getUUID() {
		return uuid;
	}

	/**
	 * @return the uuid
	 */
	public long getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(long uuid) {
		this.uuid = uuid;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(ItemCategory category) {
		this.category = category;
	}

	/**
	 * @param typeId
	 *            the typeId to set
	 */
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @param effects
	 *            the effects to set
	 */
	public void setEffects(Effect[] effects) {
		this.effects = effects;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @param skin
	 *            the skin to set
	 */
	public void setSkin(int skin) {
		this.skin = skin;
	}

	/**
	 * @param remainingHours
	 *            the remainingHours to set
	 */
	public void setRemainingHours(int remainingHours) {
		this.remainingHours = remainingHours;
	}

	/**
	 * @param pods
	 *            the pods to set
	 */
	public void setPods(int pods) {
		this.pods = pods;
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
