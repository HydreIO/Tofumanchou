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
import fr.aresrpg.dofus.protocol.chat.server.ChatMessageOkPacket;
import fr.aresrpg.dofus.protocol.conquest.client.WorldInfosJoinPacket;
import fr.aresrpg.dofus.protocol.conquest.client.WorldInfosLeavePacket;
import fr.aresrpg.dofus.protocol.dialog.DialogLeavePacket;
import fr.aresrpg.dofus.protocol.dialog.client.*;
import fr.aresrpg.dofus.protocol.emote.client.EmoteUsePacket;
import fr.aresrpg.dofus.protocol.exchange.ExchangeLeavePacket;
import fr.aresrpg.dofus.protocol.exchange.client.*;
import fr.aresrpg.dofus.protocol.fight.client.*;
import fr.aresrpg.dofus.protocol.friend.client.*;
import fr.aresrpg.dofus.protocol.game.actions.GameActions;
import fr.aresrpg.dofus.protocol.game.actions.GameMoveAction;
import fr.aresrpg.dofus.protocol.game.client.*;
import fr.aresrpg.dofus.protocol.guild.client.GuildRefuseInvitPacket;
import fr.aresrpg.dofus.protocol.info.client.InfoMapPacket;
import fr.aresrpg.dofus.protocol.item.client.*;
import fr.aresrpg.dofus.protocol.job.client.JobChangeStatsPacket;
import fr.aresrpg.dofus.protocol.mount.client.MountPlayerPacket;
import fr.aresrpg.dofus.protocol.party.PartyRefusePacket;
import fr.aresrpg.dofus.protocol.party.client.*;
import fr.aresrpg.dofus.protocol.spell.client.SpellBoostPacket;
import fr.aresrpg.dofus.protocol.spell.client.SpellMoveToUsedPacket;
import fr.aresrpg.dofus.protocol.subway.SubwayLeavePacket;
import fr.aresrpg.dofus.protocol.subway.client.SubwayUsePacket;
import fr.aresrpg.dofus.protocol.tutorial.client.TutorialQuitPacket;
import fr.aresrpg.dofus.protocol.waypoint.ZaapLeavePacket;
import fr.aresrpg.dofus.protocol.waypoint.client.ZaapUsePacket;
import fr.aresrpg.dofus.structures.Chat;
import fr.aresrpg.tofumanchou.domain.Accounts;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.event.AdminCommandEvent;
import fr.aresrpg.tofumanchou.domain.event.ClientCrashEvent;
import fr.aresrpg.tofumanchou.domain.io.Proxy;
import fr.aresrpg.tofumanchou.infra.data.ManchouAccount;
import fr.aresrpg.tofumanchou.infra.data.ManchouPerso;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;

/**
 * 
 * @since
 */
public class BaseClientPacketHandler implements ClientPacketHandler {

	private ManchouAccount client;
	private ManchouProxy proxy;
	private boolean state_machine = false;

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
		if (client == null) return null;
		return (ManchouPerso) client.getPerso();
	}

	public void setClient(Account client) {
		this.client = (ManchouAccount) client;
	}

	private void transmit(Packet pkt) {
		try {
			proxy.getRemoteConnection().send(pkt);
		} catch (IOException e) {
			ClientCrashEvent event = new ClientCrashEvent(client, e);
			event.send();
			LOGGER.error(e);
			proxy.shutdown();
		}
	}

	private void sendPkt(Packet pkt) {
		try {
			getClient().getConnection().send(pkt);
		} catch (IOException e) {
			ClientCrashEvent event = new ClientCrashEvent(client, e);
			event.send();
			LOGGER.error(e);
			proxy.shutdown();
		}
	}

	@Override
	public boolean parse(ProtocolRegistry registry, String packet) {
		if (state_machine && registry == null) {
			try {
				System.out.println("[SEND direct] -> " + packet);
				((SocketChannel) getProxy().getRemoteConnection().getChannel()).write(ByteBuffer.wrap(packet.getBytes()));
			} catch (IOException e) {
				ClientCrashEvent event = new ClientCrashEvent(client, e);
				event.send();
				LOGGER.error(e);
				proxy.shutdown();
			}
			return true;
		}
		throw new UnsupportedOperationException();
	}

	private void event(Event event) {
		if (client == null) throw new NullPointerException("The client cannot be null !");
		event.send();
	}

	protected void log(Packet pkt) {
		if (getPerso() == null) LOGGER.info("[SND:]> " + pkt);
		else LOGGER.info("[" + getPerso().getPseudo() + ":SND:]> " + pkt);
	}

	@Override
	public void register(DofusConnection<?> connection) {

	}

	@Override
	public void handle(AccountKeyPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(AccountRegionalVersionPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(ChatSubscribeChannelPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ZaapLeavePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(PartyAcceptPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(PartyRefusePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(BasicUseSmileyPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(AccountAuthPacket pkt) {
		log(pkt);
		Account account = Accounts.getAccount(pkt.getPseudo());
		if (account == null) {
			proxy.shutdown();
			throw new NullPointerException("This account is not registered !");
		}
		proxy.setAccount(account);
		transmit(pkt);
		state_machine = true;
	}

	@Override
	public void handle(AccountAccessServerPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(AccountGetCharactersPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(AccountGetGiftsPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(AccountGetQueuePosition pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(AccountIdentityPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(AccountListServersPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(AccountSelectCharacterPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(AccountSetCharacterPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(GameActionACKPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(GameClientActionPacket pkt) {
		log(pkt);
		if (pkt.getType() == GameActions.MOVE) {
			GameMoveAction action = (GameMoveAction) pkt.getAction();
			LOGGER.debug("clientAction = " + action);
		}
		transmit(pkt);

	}

	@Override
	public void handle(GameClientReadyPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(GameCreatePacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(GameEndTurnPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(GameExtraInformationPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(GameFreeMySoulPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(GameLeavePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(GameSetPlayerPositionPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(GameTurnEndPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(GameTurnOkPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(InfoMapPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(MountPlayerPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeAcceptPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ZaapUsePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(EmoteUsePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeRequestPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeMoveItemsPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeSendReadyPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeShopPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeMoveKamasPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeSellToNpcPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeBuyToNpcPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeDisconnectAsMerchantPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeAskToDisconnectAsMerchantPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeHdvRequestPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeGetCrafterForJobPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeMountPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeParkMountPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeReplayCraftPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeRepeatCraftPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(DialogBeginPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(DialogCreatePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(DialogLeavePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(DialogResponsePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(FightBlockSpectatePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(FightRestrictGroupPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(FightBlockAllPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(FightNeedHelpPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(GameActionCancelPacket pkt) {
		log(pkt);
		LOGGER.debug("Cancelling at " + pkt.getParams());
		transmit(pkt);

	}

	@Override
	public void handle(ItemMovementPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ItemDropPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ItemDestroyPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ItemUsePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ExchangeLeavePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(PartyInvitePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(PartyLeavePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(PartyFollowPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(PartyWherePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(PartyFollowAllPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(JobChangeStatsPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(WorldInfosJoinPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(WorldInfosLeavePacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(ItemSkinPacket pkt) {
		log(pkt);
		transmit(pkt);

	}

	@Override
	public void handle(FriendGetListPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(FriendAddPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(FriendRemovePacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(BasicChatMessageSendPacket pkt) {
		log(pkt);
		Chat chatType = pkt.getChatType();
		if (chatType == Chat.ADMIN || chatType == Chat.MEETIC) {
			ChatMessageOkPacket pkt2 = new ChatMessageOkPacket();
			pkt2.setChat(chatType);
			pkt2.setMsg(pkt.getMsg());
			pkt2.setPseudo(getPerso().getPseudo());
			getPerso().sendPacketToClient(pkt2);
			new AdminCommandEvent(client, getPerso(), pkt.getMsg()).send();
		} else transmit(pkt);
	}

	@Override
	public void handle(SpellMoveToUsedPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(SpellBoostPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(SubwayLeavePacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(SubwayUsePacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(GuildRefuseInvitPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(TutorialQuitPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

}
