package fr.aresrpg.tofumanchou.domain.data.entity.player;

import fr.aresrpg.dofus.protocol.Packet;
import fr.aresrpg.dofus.protocol.exchange.client.ExchangeMoveItemsPacket.MovedItem;
import fr.aresrpg.dofus.structures.*;
import fr.aresrpg.dofus.structures.item.ItemCategory;
import fr.aresrpg.dofus.structures.job.Jobs;
import fr.aresrpg.dofus.util.Pathfinding.Node;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.Job;
import fr.aresrpg.tofumanchou.domain.data.Spell;
import fr.aresrpg.tofumanchou.domain.data.enums.*;
import fr.aresrpg.tofumanchou.domain.data.inventory.Inventory;
import fr.aresrpg.tofumanchou.domain.data.item.Item;
import fr.aresrpg.tofumanchou.domain.data.map.Carte;
import fr.aresrpg.tofumanchou.infra.data.PlayerInventory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @since
 */
public interface Perso extends Player {

	Account getAccount();

	Inventory getInventory();

	void connect();

	void disconnect();

	long getXp();

	long getXpMin();

	long getXpMax();

	Job getJob();

	Map<Jobs, Job> getJobs();

	int getStatsPoints();

	int getSpellsPoints();

	void sendPacketToServer(Packet pkt);

	void sendPacketToClient(Packet pkt);

	int getEnergy();

	int getEnergyMax();

	List<String> getOfflinesFriends();

	List<Friend> getOnlineFriends();

	Map<Chat, Boolean> getChannels();

	Carte getMap();

	Map<Spells, Spell> getSpells();

	int getPods();

	void leaveZaapi();

	void leaveZaap();

	void activateChat(Chat... chts);

	void desactivateChat(Chat... chts);

	int getMaxPods();

	void startCraft();

	void startCraft(int nbr);

	void cancelCraft();

	void replaceCraft();

	Set<Zaap> getZaaps();

	void moveToCell(int cellid, boolean diagonals, boolean avoidMobs);

	/**
	 * move the player using a path
	 * 
	 * @param p
	 *            the path
	 * @deprecated this method doesn't support the path cancelling ! use moveToCell instead
	 * @return the path time
	 */
	@Deprecated
	void move(List<Node> p);

	void moveToRandomCell();

	void launchSpell(Spell spell, int cellid);

	void setFightPosition(int pos);

	void endTurn();

	void beReady(boolean ready);

	void blockSpec();

	void blockCombat();

	void blockToGroup();

	void joinFight(long fightId);

	void sit(boolean sit);

	void speakToNpc(int npcid);

	void followGroupMember(int player);

	void buyToNpc(int npcid);

	void npcTalkChoice(int questionId, int responseId);

	void addFriend(String name);

	void removeFriend(String name);

	void getFriendList();

	void npcBuyChoice(int itemId, int quantity);

	void useZaap(Zaap destination);

	void useZaapi(Zaapi destination);

	void moveItem(MovedItem items);

	void moveKama(long amount);

	void destroyItem(long uid, int amount);

	void useItem(long itemuid);

	void interract(Skills s, int cell);

	void leaveDialog();

	void leaveExchange();

	void confirmExchange();

	void speak(Chat canal, String msg); // Impl note: si msg trop long split en plusieurs msg;

	void sendPm(String playername, String msg); // Impl note: si msg trop long split en plusieurs msg;

	void equip(EquipmentPosition pos, int itemId); // equip un item

	void dismantle(EquipmentPosition pos); // déséquiper

	void invitPlayerToGroup(String pname);

	void invitPlayerToGroupAndCancel(String name, long cancelAfter, TimeUnit unit);

	void acceptGroupInvitation(boolean accept);

	void defiPlayer(long id);

	void defiPlayerAndCancel(long id, long cancelAfter, TimeUnit unit);

	void acceptDefiRequest(long playerid, boolean accept);

	void echangeWith(long id);

	void acceptEchangeRequest(boolean accept);

	void acceptGuildInvitation(boolean accept);

	void sendSmiley(Smiley emot);

	default void useRessourceBags() {
		PlayerInventory inv = (PlayerInventory) getInventory();
		inv.getItemsByCategory(ItemCategory.RESOURCEBAG).forEach(i -> useItemStack(i.getUUID()));
	}

	default void useItemStack(long uid) {
		PlayerInventory inv = (PlayerInventory) getInventory();
		Item item = inv.getItem(uid);
		if (item != null)
			for (int i = 0; i < item.getAmount(); i++)
			useItem(item.getUUID());
	}

	default void useAllItemsWithType(int itemType) {
		Set<fr.aresrpg.tofumanchou.domain.data.item.Item> items = ((PlayerInventory) getInventory()).getItems(itemType);
		if (items == null || items.isEmpty()) return;
		items.forEach(i -> useItem(i.getUUID()));
	}

	public static enum BuyResult {
		SUCCESS("Sucess"),
		NO_KAMA("Kamas insuffisants"),
		NO_PODS("Pods insuffisants");

		private String reason;

		private BuyResult(String reason) {
			this.reason = reason;
		}

		/**
		 * @return the reason
		 */
		public String getReason() {
			return reason;
		}
	}

}
