/*******************************************************************************
 * BotFather (C) - Dofus 1.29
 * This class is part of an AresRPG Project.
 *
 * @author Sceat {@literal <sceat@aresrpg.fr>}
 * 
 *         Created 2016
 *******************************************************************************/
package fr.aresrpg.tofumanchou.infra.io;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.dofus.protocol.*;
import fr.aresrpg.dofus.protocol.account.AccountKeyPacket;
import fr.aresrpg.dofus.protocol.account.AccountRegionalVersionPacket;
import fr.aresrpg.dofus.protocol.account.client.*;
import fr.aresrpg.dofus.protocol.basic.client.BasicChatMessageSendPacket;
import fr.aresrpg.dofus.protocol.basic.client.BasicUseSmileyPacket;
import fr.aresrpg.dofus.protocol.chat.ChatSubscribeChannelPacket;
import fr.aresrpg.dofus.protocol.conquest.client.WorldInfosJoinPacket;
import fr.aresrpg.dofus.protocol.conquest.client.WorldInfosLeavePacket;
import fr.aresrpg.dofus.protocol.dialog.DialogLeavePacket;
import fr.aresrpg.dofus.protocol.dialog.client.*;
import fr.aresrpg.dofus.protocol.emote.client.EmoteUsePacket;
import fr.aresrpg.dofus.protocol.exchange.ExchangeLeavePacket;
import fr.aresrpg.dofus.protocol.exchange.client.*;
import fr.aresrpg.dofus.protocol.fight.client.*;
import fr.aresrpg.dofus.protocol.friend.client.*;
import fr.aresrpg.dofus.protocol.game.client.*;
import fr.aresrpg.dofus.protocol.info.client.InfoMapPacket;
import fr.aresrpg.dofus.protocol.item.client.*;
import fr.aresrpg.dofus.protocol.job.client.JobChangeStatsPacket;
import fr.aresrpg.dofus.protocol.mount.client.MountPlayerPacket;
import fr.aresrpg.dofus.protocol.party.PartyRefusePacket;
import fr.aresrpg.dofus.protocol.party.client.*;
import fr.aresrpg.dofus.protocol.spell.client.SpellBoostPacket;
import fr.aresrpg.dofus.protocol.spell.client.SpellMoveToUsedPacket;
import fr.aresrpg.dofus.protocol.waypoint.ZaapLeavePacket;
import fr.aresrpg.dofus.protocol.waypoint.client.ZaapUsePacket;
import fr.aresrpg.dofus.structures.server.Server;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.io.Proxy;
import fr.aresrpg.tofumanchou.infra.data.ManchouAccount;
import fr.aresrpg.tofumanchou.infra.data.ManchouPerso;

import java.io.IOException;
import java.util.Objects;

/**
 * 
 * @since
 */
public class BaseClientPacketHandler implements ClientPacketHandler {

	private ManchouAccount client;
	private ManchouProxy proxy;
	private String ticket;
	private Server current;

	public BaseClientPacketHandler(Proxy proxy) {
		Objects.requireNonNull(proxy);
		this.proxy = (ManchouProxy) proxy;
		this.client = (ManchouAccount) proxy.getClient();
	}

	public ManchouProxy getProxy() {
		return proxy;
	}

	public ManchouAccount getClient() {
		return client;
	}

	public ManchouPerso getPerso() {
		return (ManchouPerso) client.getPerso();
	}

	public void setClient(Account client) {
		this.client = (ManchouAccount) client;
	}

	private void transmit(Packet pkt) {
		try {
			proxy.getLocalConnection().send(pkt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendPkt(Packet pkt) {
		try {
			getClient().getConnection().send(pkt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void event(Event event) {
		if (client == null) throw new NullPointerException("The client cannot be null !");
		event.send();
	}

	protected void log(Packet pkt) {
		if (getPerso() == null) LOGGER.info("[RCV:]< " + pkt);
		else LOGGER.info("[" + getPerso().getPseudo() + ":RCV:]< " + pkt);
	}

	@Override
	public void register(DofusConnection<?> connection) {

	}

	@Override
	public void handle(AccountKeyPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(AccountRegionalVersionPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ChatSubscribeChannelPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ZaapLeavePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(PartyAcceptPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(PartyRefusePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(BasicUseSmileyPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(AccountAuthPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(AccountAccessServerPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(AccountGetCharactersPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(AccountGetGiftsPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(AccountGetQueuePosition pkt) {
		log(pkt);

	}

	@Override
	public void handle(AccountIdentityPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(AccountListServersPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(AccountSelectCharacterPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(AccountSetCharacterPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(GameActionACKPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(GameClientActionPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(GameClientReadyPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(GameCreatePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(GameEndTurnPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(GameExtraInformationPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(GameFreeMySoulPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(GameLeavePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(GameSetPlayerPositionPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(GameTurnEndPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(GameTurnOkPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(InfoMapPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(MountPlayerPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeAcceptPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ZaapUsePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(EmoteUsePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeRequestPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeMoveItemsPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeSendReadyPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeShopPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeMoveKamasPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeSellToNpcPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeBuyToNpcPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeDisconnectAsMerchantPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeAskToDisconnectAsMerchantPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeHdvPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeGetCrafterForJobPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeMountPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeParkMountPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeReplayCraftPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeRepeatCraftPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(DialogBeginPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(DialogCreatePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(DialogLeavePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(DialogResponsePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(FightBlockSpectatePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(FightRestrictGroupPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(FightBlockAllPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(FightNeedHelpPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(GameActionCancel pkt) {
		log(pkt);

	}

	@Override
	public void handle(ItemMovementPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ItemDropPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ItemDestroyPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ItemUsePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ExchangeLeavePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(PartyInvitePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(PartyLeavePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(PartyFollowPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(PartyWherePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(PartyFollowAllPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(JobChangeStatsPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(WorldInfosJoinPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(WorldInfosLeavePacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(ItemSkinPacket pkt) {
		log(pkt);

	}

	@Override
	public void handle(FriendGetListPacket pkt) {
		log(pkt);
	}

	@Override
	public void handle(FriendAddPacket pkt) {
		log(pkt);
	}

	@Override
	public void handle(FriendRemovePacket pkt) {
		log(pkt);
	}

	@Override
	public void handle(BasicChatMessageSendPacket pkt) {
		log(pkt);
	}

	@Override
	public void handle(SpellMoveToUsedPacket pkt) {
		log(pkt);
	}

	@Override
	public void handle(SpellBoostPacket pkt) {
		log(pkt);
	}

}
