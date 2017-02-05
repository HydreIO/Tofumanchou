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
import fr.aresrpg.commons.domain.log.AnsiColors.AnsiColor;
import fr.aresrpg.commons.domain.util.ArrayUtils;
import fr.aresrpg.dofus.protocol.*;
import fr.aresrpg.dofus.protocol.ProtocolRegistry.Bound;
import fr.aresrpg.dofus.protocol.account.AccountKeyPacket;
import fr.aresrpg.dofus.protocol.account.AccountRegionalVersionPacket;
import fr.aresrpg.dofus.protocol.account.client.*;
import fr.aresrpg.dofus.protocol.account.server.*;
import fr.aresrpg.dofus.protocol.aks.Aks0MessagePacket;
import fr.aresrpg.dofus.protocol.basic.server.BasicConfirmPacket;
import fr.aresrpg.dofus.protocol.chat.ChatSubscribeChannelPacket;
import fr.aresrpg.dofus.protocol.chat.server.ChatMessageOkPacket;
import fr.aresrpg.dofus.protocol.chat.server.ChatServerMessagePacket;
import fr.aresrpg.dofus.protocol.dialog.DialogLeavePacket;
import fr.aresrpg.dofus.protocol.dialog.server.*;
import fr.aresrpg.dofus.protocol.exchange.server.*;
import fr.aresrpg.dofus.protocol.fight.server.*;
import fr.aresrpg.dofus.protocol.friend.server.FriendListPacket;
import fr.aresrpg.dofus.protocol.game.actions.GameMoveAction;
import fr.aresrpg.dofus.protocol.game.actions.client.GameAcceptDuelAction;
import fr.aresrpg.dofus.protocol.game.actions.client.GameRefuseDuelAction;
import fr.aresrpg.dofus.protocol.game.actions.server.*;
import fr.aresrpg.dofus.protocol.game.client.*;
import fr.aresrpg.dofus.protocol.game.movement.*;
import fr.aresrpg.dofus.protocol.game.server.*;
import fr.aresrpg.dofus.protocol.guild.server.*;
import fr.aresrpg.dofus.protocol.hello.server.HelloConnectionPacket;
import fr.aresrpg.dofus.protocol.hello.server.HelloGamePacket;
import fr.aresrpg.dofus.protocol.info.server.*;
import fr.aresrpg.dofus.protocol.item.server.*;
import fr.aresrpg.dofus.protocol.job.server.*;
import fr.aresrpg.dofus.protocol.mount.server.MountXpPacket;
import fr.aresrpg.dofus.protocol.party.PartyRefusePacket;
import fr.aresrpg.dofus.protocol.party.server.*;
import fr.aresrpg.dofus.protocol.specialization.server.SpecializationSetPacket;
import fr.aresrpg.dofus.protocol.spell.server.SpellChangeOptionPacket;
import fr.aresrpg.dofus.protocol.spell.server.SpellListPacket;
import fr.aresrpg.dofus.protocol.subarea.server.SubareaListPacket;
import fr.aresrpg.dofus.protocol.subway.SubwayLeavePacket;
import fr.aresrpg.dofus.protocol.subway.server.SubwayCreatePacket;
import fr.aresrpg.dofus.protocol.tutorial.server.TutorialCreatePacket;
import fr.aresrpg.dofus.protocol.waypoint.ZaapLeavePacket;
import fr.aresrpg.dofus.protocol.waypoint.server.ZaapCreatePacket;
import fr.aresrpg.dofus.protocol.waypoint.server.ZaapUseErrorPacket;
import fr.aresrpg.dofus.structures.Rank;
import fr.aresrpg.dofus.structures.character.AvailableCharacter;
import fr.aresrpg.dofus.structures.character.Character;
import fr.aresrpg.dofus.structures.character.PartyMember;
import fr.aresrpg.dofus.structures.game.*;
import fr.aresrpg.dofus.structures.item.Item;
import fr.aresrpg.dofus.structures.job.Job;
import fr.aresrpg.dofus.structures.job.JobInfo;
import fr.aresrpg.dofus.structures.map.DofusMap;
import fr.aresrpg.dofus.structures.server.DofusServer;
import fr.aresrpg.dofus.structures.server.Server;
import fr.aresrpg.dofus.structures.stat.Stat;
import fr.aresrpg.dofus.structures.stat.StatValue;
import fr.aresrpg.dofus.util.*;
import fr.aresrpg.dofus.util.Pathfinding.Node;
import fr.aresrpg.tofumanchou.domain.Manchou;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.entity.mob.Mob;
import fr.aresrpg.tofumanchou.domain.data.entity.npc.Npc;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Perso;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Player;
import fr.aresrpg.tofumanchou.domain.data.enums.*;
import fr.aresrpg.tofumanchou.domain.event.*;
import fr.aresrpg.tofumanchou.domain.event.aproach.*;
import fr.aresrpg.tofumanchou.domain.event.chat.*;
import fr.aresrpg.tofumanchou.domain.event.duel.DuelRequestEvent;
import fr.aresrpg.tofumanchou.domain.event.entity.*;
import fr.aresrpg.tofumanchou.domain.event.exchange.*;
import fr.aresrpg.tofumanchou.domain.event.fight.*;
import fr.aresrpg.tofumanchou.domain.event.friend.FriendListsEvent;
import fr.aresrpg.tofumanchou.domain.event.group.*;
import fr.aresrpg.tofumanchou.domain.event.guild.GuildStatsEvent;
import fr.aresrpg.tofumanchou.domain.event.item.*;
import fr.aresrpg.tofumanchou.domain.event.map.*;
import fr.aresrpg.tofumanchou.domain.event.player.*;
import fr.aresrpg.tofumanchou.domain.io.Proxy;
import fr.aresrpg.tofumanchou.domain.io.Proxy.ProxyConnectionType;
import fr.aresrpg.tofumanchou.domain.util.Functions;
import fr.aresrpg.tofumanchou.domain.util.concurrent.Executors;
import fr.aresrpg.tofumanchou.infra.config.Variables;
import fr.aresrpg.tofumanchou.infra.data.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 
 * @since
 */
public class BaseServerPacketHandler implements ServerPacketHandler {

	private static ServerSocketChannel SERVER_SOCKET;

	private ManchouAccount client;
	private ManchouProxy proxy;
	private String ticket;
	private Server current;
	private String hc;

	private Balking balking = new Balking();

	public BaseServerPacketHandler(Account client) {
		Objects.requireNonNull(client);
		this.client = (ManchouAccount) client;
	}

	public BaseServerPacketHandler(Proxy proxy) {
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

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public boolean isMitm() {
		return proxy != null;
	}

	@Override
	public boolean parse(ProtocolRegistry registry, String packet) {
		if (registry == null && isMitm()) {
			System.out.println("[RECEIVE direct] " + packet);
			if (getProxy().getLocalConnection().getChannel().isOpen()) try {
				((SocketChannel) getProxy().getLocalConnection().getChannel()).write(ByteBuffer.wrap(packet.getBytes()));
			} catch (IOException e) {
				LOGGER.error(e);
				proxy.shutdown();
			}
			return true;
		}
		throw new UnsupportedOperationException();
	}

	public boolean isBot() {
		return !isMitm();
	}

	private void transmit(Packet pkt) {
		if (isBot()) return;
		if (proxy.getLocalConnection().getChannel().isOpen()) try {
			proxy.getLocalConnection().send(pkt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendPkt(Packet pkt) {
		if (getClient().getConnection().getChannel().isOpen()) try {
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
	public void handle(HelloConnectionPacket pkt) {
		log(pkt);
		this.hc = pkt.getHashKey();
		if (isBot()) {
			sendPkt(new AccountAuthPacket()
					.setPseudo(client.getAccountName())
					.setHashedPassword(Crypt.hash(client.getPassword(), pkt.getHashKey()))
					.setVersion("1.29.1"));
		}
		transmit(pkt);
	}

	@Override
	public void handle(HelloGamePacket pkt) {
		log(pkt);
		if (isBot()) sendPkt(new AccountTicketPacket().setTicket(ticket));
		transmit(pkt);
	}

	@Override
	public void handle(AccountKeyPacket pkt) {
		log(pkt);
		if (isBot()) sendPkt(new AccountKeyPacket().setKey(pkt.getKey()).setData(pkt.getData()));
		transmit(pkt);
	}

	@Override
	public void handle(FriendListPacket pkt) {
		log(pkt);
		getPerso().setOfflineFriends(pkt.getOfflineFriends());
		getPerso().setOnlineFriends(pkt.getOnlineFriends());
		FriendListsEvent friendListsEvent = new FriendListsEvent(client, pkt.getOfflineFriends(), pkt.getOnlineFriends());
		friendListsEvent.send();
		pkt.setOfflineFriends(friendListsEvent.getOfflinesFriends());
		pkt.setOnlineFriends(friendListsEvent.getOnlinesFriends());
		transmit(pkt);
	}

	@Override
	public void handle(AccountRegionalVersionPacket pkt) {
		log(pkt);
		if (isBot()) {
			sendPkt(new AccountGetGiftsPacket().setLanguage("fr"));
			sendPkt(new AccountIdentityPacket().setIdentity(Crypt.getRandomNetworkKey()));
			sendPkt(new AccountGetCharactersPacket());
		}
		transmit(pkt);
	}

	@Override
	public void handle(ChatMessageOkPacket pkt) {
		log(pkt);
		ChatMsgEvent event = new ChatMsgEvent(client, pkt.getChat(), pkt.getPlayerId(), pkt.getPseudo(), pkt.getMsg());
		event.send();
		pkt.setChat(event.getChat());
		pkt.setPlayerId(event.getPlayerId());
		pkt.setPseudo(event.getPseudo());
		pkt.setMsg(event.getMsg());
		transmit(pkt);
	}

	@Override
	public void handle(ChatSubscribeChannelPacket pkt) {
		log(pkt);
		Arrays.stream(pkt.getChannels()).forEach(c -> getPerso().getChannels().put(c, pkt.isAdd()));
		if (pkt.isAdd()) {
			ChannelSubscribeEvent event = new ChannelSubscribeEvent(client, pkt.getChannels());
			event.send();
			pkt.setChannels(event.getChannels());
		} else {
			ChannelUnsubscribeEvent event = new ChannelUnsubscribeEvent(client, pkt.getChannels());
			event.send();
			pkt.setChannels(event.getChannels());
		}
		transmit(pkt);
	}

	@Override
	public void handle(ZaapLeavePacket pkt) {
		log(pkt);
		new ZaapGuiLeaveEvent(client).send();
		transmit(pkt);
	}

	@Override
	public void handle(AccountCharactersListPacket pkt) {
		log(pkt);
		if (pkt.getCharacters() != null && pkt.getCharacters().length != 0) this.current = Server.fromId(pkt.getCharacters()[0].getServerId());
		if (isBot())
			for (AvailableCharacter c : pkt.getCharacters())
			if (client.getPerso().getPseudo().equals(c.getPseudo())) {
			ManchouPerso p = getPerso();
			p.setUuid(c.getId());
			p.setLvl(c.getLevel());
			p.setColors(c.getColor1(), c.getColor2(), c.getColor3());
			p.setAccessories(c.getAccessories());
			p.setMerchant(c.isMerchant());
			p.setServer(Server.fromId(c.getServerId()));
			p.setDead(c.isDead());
			p.setDeathCount(c.getDeathCount());
			p.setLvlMax(c.getLvlMax());
			sendPkt(new AccountSelectCharacterPacket().setCharacterId(c.getId()));
			}
		CharacterListEvent event = new CharacterListEvent(client, pkt.getSubscriptionTime(), pkt.getPersoTot(), pkt.getCharacters());
		event.send();
		pkt.setSubscriptionTime(event.getSubscriptionTime());
		pkt.setPersoTot(event.getPersoTot());
		pkt.setCharacters(event.getCharacters());
		transmit(pkt);
	}

	@Override
	public void handle(AccountCommunityPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(AccountHostPacket pkt) {
		log(pkt);
		for (DofusServer s : pkt.getServers())
			if (s.getId() == Server.ERATZ.getId()) {
				Manchou.ERATZ.setState(s.getState());
				Manchou.ERATZ.setServerPopulation(s.getServerPopulation());
				new ServerStateEvent(client, Server.ERATZ).send();
			} else {
				Manchou.HENUAL.setState(s.getState());
				Manchou.HENUAL.setServerPopulation(s.getServerPopulation());
				new ServerStateEvent(client, Server.HENUAL).send();
			}
		transmit(pkt);
	}

	@Override
	public void handle(AccountLoginErrPacket pkt) {
		log(pkt);
		LoginErrorEvent event = new LoginErrorEvent(client, pkt.getErr(), pkt.getTime(), pkt.getVersion());
		event.send();
		pkt.setErr(event.getError());
		pkt.setTime(event.getMinutes());
		pkt.setVersion(event.getVersion());
		transmit(pkt);
	}

	@Override
	public void handle(AccountLoginOkPacket pkt) {
		log(pkt);
		pkt.setAdmin(true);
		transmit(pkt);
	}

	@Override
	public void handle(AccountPseudoPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(AccountQuestionPacket pkt) {
		log(pkt);
		if (isBot()) Executors.SCHEDULED.schedule(() -> sendPkt(new AccountListServersPacket()), 2, TimeUnit.SECONDS);
		transmit(pkt);
	}

	@Override
	public void handle(AccountQueuePosition pkt) {
		log(pkt);
		RealmQueuePositionEvent event = new RealmQueuePositionEvent(client, pkt.getPosition(), pkt.getTotalSubscriber(), pkt.getTotalNoSubscribed(), pkt.getPositionInQueue(), pkt.isSubscribed());
		event.send();
		pkt.setPosition(event.getPosition());
		pkt.setPositionInQueue(event.getPositionInQueue());
		pkt.setSubscribed(event.isSub());
		pkt.setTotalNoSubscribed(event.getTotalNoSub());
		pkt.setTotalSubscriber(event.getTotalSub());
		transmit(pkt);
	}

	@Override
	public void handle(AccountRestrictionsPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(AccountSelectCharacterOkPacket pkt) {
		log(pkt);
		if (isMitm()) {
			if (client.getPerso() == null) {
				ManchouPerso manchouPerso = new ManchouPerso(client, current, pkt.getCharacter());
				client.setPerso(manchouPerso);
			}
			((ManchouPerso) client.getPerso()).setMitm(true);
		} else {
			ManchouPerso p = (ManchouPerso) client.getPerso();
			Character c = pkt.getCharacter();
			p.setUuid(c.getId());
			p.setClasse(Classe.getClasse(c.getGfxId()));
			// tphis.sex = c.getSex();
			p.setLvl(c.getLevel());
			p.setGuild(c.getGuild());
			p.setColors(new ManchouColors(c.getColor1(), c.getColor2(), c.getColor3()));
		}
		((PlayerInventory) getPerso().getInventory()).parseCharacter(pkt.getCharacter());
		new PersoSelectEvent(client, getPerso()).send();
		transmit(pkt);
	}

	@Override
	public void handle(AccountServerEncryptedHostPacket pkt) {
		log(pkt);
		this.ticket = pkt.getTicketKey();
		try {
			if (isBot()) {
				client.getConnection().closeConnection();
				SocketChannel socket = SocketChannel.open(new InetSocketAddress(pkt.getIp(), pkt.getPort()));
				client.setConnection(new DofusConnection<>(getPerso().getPseudo(), socket, this, ProtocolRegistry.Bound.SERVER));
				Executors.FIXED.execute(() -> {
					try {
						client.getConnection().start();
					} catch (Exception e) {
						ClientCrashEvent event = new ClientCrashEvent(client, e);
						event.send();
						LOGGER.error(e);
						proxy.shutdown();
					}
				});
			} else {
				String ip = pkt.getIp();
				if (SERVER_SOCKET == null) {
					SERVER_SOCKET = ServerSocketChannel.open();
					SERVER_SOCKET.bind(new InetSocketAddress(0));
				}
				int localPort = SERVER_SOCKET.socket().getLocalPort();
				getProxy().getLocalConnection().send(new AccountServerHostPacket().setIp(Variables.PASSERELLE_IP).setPort(localPort).setTicketKey(pkt.getTicketKey()));
				SocketChannel client = SERVER_SOCKET.accept();
				getProxy().changeConnection(new DofusConnection<>("Local", client, getProxy().getLocalHandler(), Bound.CLIENT),
						ProxyConnectionType.LOCAL);
				SocketChannel server = SocketChannel.open(new InetSocketAddress(ip, pkt.getPort()));
				Manchou.SOCKETS.add(new Pair<SocketChannel, SocketChannel>(client, server));
				getProxy().changeConnection(
						new DofusConnection<>("Remote", server, getProxy().getRemoteHandler(), Bound.SERVER),
						ProxyConnectionType.REMOTE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handle(AccountServerHostPacket pkt) {
		log(pkt);
		this.ticket = pkt.getTicketKey();
		transmit(pkt);
	}

	@Override
	public void handle(AccountServerListPacket pkt) {
		log(pkt);
		SubscriptionAndPersoNumberEvent event = new SubscriptionAndPersoNumberEvent(client, pkt.getSubscriptionDuration(), pkt.getCharacters());
		event.send();
		pkt.setCharacters(event.getCharacters());
		pkt.setSubscriptionDuration(event.getSubTime());
		if (isBot()) sendPkt(new AccountAccessServerPacket().setServerId(getPerso().getServer().getId()));
		transmit(pkt);
	}

	@Override
	public void handle(AccountTicketOkPacket pkt) {
		log(pkt);
		if (isBot()) {
			sendPkt(new AccountKeyPacket().setKey(pkt.getKey()).setData(pkt.getData()));
			sendPkt(new AccountRegionalVersionPacket());
		}
		transmit(pkt);
	}

	@Override
	public void handle(AccountTicketPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeCreatePacket pkt) {
		log(pkt);
		getPerso().setCurrentInv(pkt.getType());
		ExchangeCreateEvent event = new ExchangeCreateEvent(client, pkt.getType(), pkt.getData(), pkt.isSuccess());
		event.send();
		pkt.setType(event.getType());
		pkt.setData(event.getData());
		pkt.setSuccess(event.isSuccess());
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeListPacket pkt) {
		log(pkt);
		switch (pkt.getInvType()) {
			case BANK:
				client.getBank().setKamas(pkt.getKamas());
				client.getBank().updateContent(pkt.getItems());
				break;
			default:
				break;
		}
		ExchangeListEvent event = new ExchangeListEvent(client, pkt.getInvType(), pkt.getItems(), pkt.getKamas());
		event.send();
		pkt.setInvType(event.getInvType());
		pkt.setItems(event.getItems());
		pkt.setKamas(event.getKamas());
		transmit(pkt);
	}

	@Override
	public void handle(BasicConfirmPacket pkt) {
		log(pkt);
		transmit(pkt);
		// useless
	}

	@Override
	public void handle(Aks0MessagePacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(GuildStatPacket pkt) {
		log(pkt);
		GuildStatsEvent event = new GuildStatsEvent(client, pkt.getGuild());
		event.send();
		pkt.setGuild(event.getGuild());
		transmit(pkt);
	}

	@Override
	public void handle(InfoMessagePacket pkt) {
		log(pkt);
		ManchouMap fight = (ManchouMap) getPerso().getMap();
		if (pkt.getMessage() != null) {
			switch (pkt.getMessage()) {
				case FIGHT_ATTRIBUTE_ALLOW_GROUP_ACTIVE:
					fight.setGroupBlocked(true);
					break;
				case FIGHT_ATTRIBUTE_ALLOW_GROUP_NOT_ACTIVE:
					fight.setGroupBlocked(false);
					break;
				case FIGHT_ATTRIBUTE_DENY_ACTIVE:
					fight.setBlocked(true);
					break;
				case FIGHT_ATTRIBUTE_DENY_NOT_ACTIVE:
					fight.setBlocked(false);
					break;
				case FIGHT_ATTRIBUTE_DENY_SPECTATE_ACTIVE:
					fight.setSpecBlocked(true);
					break;
				case FIGHT_ATTRIBUTE_DENY_SPECTATE_NOT_ACTIVE:
					fight.setSpecBlocked(false);
					break;
				case FIGHT_ATTRIBUTE_NEED_HELP_ACTIVE:
					fight.setHelpNeeded(true);
					break;
				case FIGHT_ATTRIBUTE_NEED_HELP_NOT_ACTIVE:
					fight.setHelpNeeded(false);
					break;
				case EARN_KAMAS:
					int kamas = Integer.parseInt(pkt.getExtraDatas());
					client.getBank().addKamas(kamas);
					break;
				case CURRENT_ADRESS:
					if (isBot()) sendPkt(new GameCreatePacket().setGameType(GameType.SOLO));
					break;
				default:
					break;
			}
		}
		InfoMessageEvent event = new InfoMessageEvent(client, pkt.getType(), pkt.getMessageId(), pkt.getExtraDatas());
		event.send();
		pkt.setType(event.getType());
		pkt.setMessageId(event.getMessageId());
		pkt.setExtraDatas(event.getExtraDatas());
		if (isMitm() && !balking.receive(InfoMessagePacket.class).overflow(10))
			transmit(pkt);
	}

	@Override
	public void handle(MountXpPacket pkt) {
		log(pkt);
		MountXpEvent event = new MountXpEvent(client, pkt.getPercent());
		event.send();
		pkt.setPercent(event.getPercent());
		transmit(pkt);
	}

	@Override
	public void handle(SpecializationSetPacket pkt) {
		log(pkt);
		SpecializationEvent event = new SpecializationEvent(client, pkt.getSpecialization());
		event.send();
		pkt.setSpecialization(event.getSpecialization());
		transmit(pkt);
	}

	@Override
	public void handle(SpellChangeOptionPacket pkt) {
		log(pkt);
		SpellOptionEvent event = new SpellOptionEvent(client, pkt.canUseAllSpell());
		event.send();
		pkt.setCanUseAllSpell(event.canUseAllSpells());
		transmit(pkt);
	}

	@Override
	public void handle(SpellListPacket pkt) {
		log(pkt);
		pkt.getSpells().forEach(s -> {
			Spells type = Spells.valueOf(s.getId());
			getPerso().getSpells().put(type, new ManchouSpell(type, s.getLevel(), s.getPosition()));
		});
		SpellListEvent event = new SpellListEvent(client, pkt.getSpells());
		event.send();
		pkt.setSpells(event.getSpells());
		transmit(pkt);
	}

	@Override
	public void handle(SubareaListPacket pkt) {
		log(pkt);
		SubareaEvent event = new SubareaEvent(client, pkt.getSubareas());
		event.send();
		pkt.setSubareas(event.getSubareas());
		transmit(pkt);
	}

	@Override
	public void handle(ZaapCreatePacket pkt) {
		log(pkt);
		getPerso().getZaaps().addAll(Arrays.stream(pkt.getWaypoints()).map(w -> Zaap.getWithMap(w.getId())).collect(Collectors.toList()));
		ZaapGuiOpenEvent event = new ZaapGuiOpenEvent(client, pkt.getRespawnWaypoint(), pkt.getWaypoints());
		event.send();
		pkt.setRespawnWaypoint(event.getRespawnWaypoint());
		pkt.setWaypoints(event.getWaypoints());
		transmit(pkt);
	}

	@Override
	public void handle(ZaapUseErrorPacket pkt) {
		log(pkt);
		new ZaapUseErrorEvent(client).send();
		transmit(pkt);
	}

	@Override
	public void handle(GameActionFinishPacket pkt) {
		log(pkt);
		LOGGER.debug(AnsiColor.RED + "ACTION FINISH " + pkt.getCharacterId() + " ack:" + pkt.getAckId());
		if (isBot()) sendPkt(new GameActionACKPacket().setActionId(pkt.getAckId()));
		transmit(pkt);
	}

	@Override
	public void handle(GameActionStartPacket pkt) {
		log(pkt);
		LOGGER.debug(AnsiColor.RED + "ACTION START " + pkt.getCharacterId());
		transmit(pkt);
	}

	@Override
	public void handle(GameEffectPacket pkt) {
		log(pkt);
		Set<Entity> ents = new HashSet<>();
		for (Entity e : getPerso().getMap().getEntities().values()) {
			if (!ArrayUtils.contains(e.getUUID(), pkt.getEntities())) continue;
			e.getEffects().add(pkt.getEffect());
			ents.add(e);
		}
		EntitiesReceiveSpellEffectEvent event = new EntitiesReceiveSpellEffectEvent(client, ents, pkt.getEffect());
		event.send();
		pkt.setEffect(event.getEffect());
		pkt.setEntities(event.getEntities().stream().mapToLong(Entity::getUUID).toArray());
		transmit(pkt);
	}

	@Override
	public void handle(GameEndPacket pkt) {
		log(pkt);
		getPerso().getMap().getEntities().clear();
		getPerso().getSpells().values().forEach(s -> ((ManchouSpell) s).setRelance(0));
		FightEndEvent event = new FightEndEvent(client, pkt.getDuration(), pkt.getFirstPlayerId(), pkt.getBonus(), pkt.getResult());
		event.send();
		pkt.setBonus(event.getBonus());
		pkt.setDuration(event.getDuration());
		pkt.setFirstPlayerId(event.getFightId());
		pkt.setResult(event.getResult());
		if (isBot()) sendPkt(new GameCreatePacket().setGameType(GameType.SOLO));
		transmit(pkt);
	}

	@Override
	public void handle(GameFightChallengePacket pkt) {
		log(pkt);
		FightChallengeEvent event = new FightChallengeEvent(client, pkt.getChallenge());
		event.send();
		pkt.setChallenge(event.getChallenge());
		transmit(pkt);
	}

	@Override
	public void handle(GameJoinPacket pkt) {
		log(pkt);
		ManchouMap map = getPerso().getMap();
		map.getEntities().clear();
		getPerso().cancelRunner();
		if (pkt.getState() == GameType.FIGHT) {
			map.setEnded(false);
			map.setFightType(pkt.getFightType());
			map.setSpectator(pkt.isSpectator());
			map.setStartTimer(pkt.getStartTimer());
			map.setDuel(pkt.isDuel());
			map.getFightsOnMap().clear();
			FightJoinEvent event = new FightJoinEvent(client, pkt.getFightType(), pkt.isSpectator(), pkt.getStartTimer(), pkt.isCancelButton(), pkt.isDuel());
			event.send();
			pkt.setFightType(event.getFightType());
			pkt.setSpectator(event.isSpectator());
			pkt.setStartTimer(event.getStartTimer());
			pkt.setCancelButton(event.isCancelButton());
			pkt.setDuel(event.isDuel());
		} else new GameJoinEvent(client).send();
		transmit(pkt);
	}

	@Override
	public void handle(GameMapDataPacket pkt) {
		log(pkt);
		DofusMap m = null;
		try {
			InputStream downloadMap = Maps.downloadMap(pkt.getMapId(), pkt.getSubid());
			Map<String, Object> extractVariable = SwfVariableExtractor.extractVariable(downloadMap);
			m = Maps.loadMap(extractVariable, pkt.getDecryptKey(), pkt.getSubid());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ManchouMap map = ManchouMap.fromDofusMap(m);
		getPerso().setMap(map);
		new MapJoinEvent(client, map).send();
		if (isBot()) sendPkt(new GameExtraInformationPacket());
		transmit(pkt);
	}

	@Override
	public void handle(GameMapFramePacket pkt) {
		log(pkt);
		pkt.getFrames().forEach((id, frame) -> {
			ManchouCell cell = getPerso().getMap().getCells()[id];
			cell.applyFrame(frame);
			new FrameUpdateEvent(client, cell, frame.getId()).send();
			if (cell.isRessource() && cell.isRessourceSpawned()) new RessourceSpawnEvent(client, cell).send();
		});
		transmit(pkt);
	}

	@Override
	public void handle(GameMovementPacket pkt) {
		log(pkt);
		if (pkt.getType() == GameMovementType.REMOVE) {
			pkt.getActors().forEach(v -> {
				MovementRemoveActor actor = (MovementRemoveActor) (Object) v.getSecond();
				getPerso().getMap().getEntities().remove(actor.getId());
				for (ManchouCell cell : getPerso().getMap().getCells())
					cell.getEntitiesOn().removeIf(e -> e.getUUID() == actor.getId());
				new EntityLeaveMapEvent(client, actor.getId()).send();
			});
			transmit(pkt);
			return;
		}
		pkt.getActors().forEach(e -> {
			ManchouCell cell = getPerso().getMap().getCells()[e.getSecond().getCellId()];
			switch (e.getFirst()) {
				case DEFAULT:
					MovementPlayer player = (MovementPlayer) (Object) e.getSecond();
					if (player.getId() == getPerso().getUUID()) {
						getPerso().updateMovement(player);
						getPerso().getMap().getEntities().put(player.getId(), getPerso());
						cell.addEntityOn(getPerso());
						new EntityPlayerJoinMapEvent(client, getPerso()).send();
					} else {
						ManchouPlayerEntity parseMovement = ManchouPlayerEntity.parseMovement(player);
						getPerso().getMap().getEntities().put(player.getId(), parseMovement);
						cell.addEntityOn(parseMovement);
						new EntityPlayerJoinMapEvent(client, parseMovement).send();
					}
					return;
				case CREATE_INVOCATION:
				case CREATE_MONSTER:
					MovementMonster mob = (MovementMonster) (Object) e.getSecond();
					ManchouMob parseMovement = ManchouMob.parseMovement(mob);
					getPerso().getMap().getEntities().put(mob.getId(), parseMovement);
					cell.addEntityOn(parseMovement);
					new MonsterJoinMapEvent(client, parseMovement).send();
					return;
				case CREATE_MONSTER_GROUP:
					MovementMonsterGroup mobs = (MovementMonsterGroup) (Object) e.getSecond();
					ManchouMobGroup mobsgroup = ManchouMobGroup.parseMovement(mobs);
					getPerso().getMap().getEntities().put(mobs.getId(), mobsgroup);
					cell.addEntityOn(mobsgroup);
					new MonsterGroupSpawnEvent(client, mobsgroup).send();
					return;
				case CREATE_NPC:
					MovementNpc npc = (MovementNpc) (Object) e.getSecond();
					ManchouNpc npcm = ManchouNpc.parseMovement(npc);
					getPerso().getMap().getEntities().put(npc.getId(), npcm);
					cell.addEntityOn(npcm);
					new NpcJoinMapEvent(client, npcm).send();
					return;
				default:
					break;
			}
		});
		transmit(pkt);
	}

	@Override
	public void handle(GamePositionsPacket pkt) {
		log(pkt);
		pkt.getPositions().forEach(p -> {
			Entity entity = p.getEntityId() == getPerso().getUUID() ? getPerso() : getPerso().getMap().getEntities().get(p.getEntityId());
			entity.setCellId(p.getPosition());
			new EntityChangePlaceInFightEvent(client, entity, p.getPosition()).send();
		});
		transmit(pkt);
	}

	@Override
	public void handle(GamePositionStartPacket pkt) {
		log(pkt);
		ManchouMap map = getPerso().getMap();
		map.setTeam0Places(pkt.getPlacesTeam0());
		map.setTeam1Places(pkt.getPlacesTeam1());
		FightPositionsReceiveEvent event = new FightPositionsReceiveEvent(client, pkt.getPlacesTeam0(), pkt.getPlacesTeam1());
		event.send();
		pkt.setPlacesTeam0(event.getPlacesTeam0());
		pkt.setPlacesTeam1(event.getPlacesTeam1());
		transmit(pkt);
	}

	@Override
	public void handle(GameServerActionPacket pkt) {
		log(pkt);
		boolean transmit = true;
		if (pkt.getLastAction() != -1 && pkt.getEntityId() == getPerso().getUUID()) getPerso().setLastAction(pkt.getLastAction());
		switch (pkt.getType()) {
			case ERROR:
				ActionErrorEvent actionErrorEvent = new ActionErrorEvent(client, pkt.getLastAction());
				actionErrorEvent.send();
				pkt.setLastAction(actionErrorEvent.getLastAction());
				break;
			case LIFE_CHANGE:
				GameLifeChangeAction actionl = (GameLifeChangeAction) pkt.getAction();
				Entity entt = getPerso().getMap().getEntities().get(actionl.getEntity());
				entt.setLife(entt.getLife() + actionl.getLife());
				EntityLifeChangeEvent event = new EntityLifeChangeEvent(client, entt, actionl.getLife());
				event.send();
				actionl.setEntity(event.getEntity().getUUID());
				actionl.setLife(event.getLife());
				break;
			case PA_CHANGE:
				GamePaChangeAction actionpa = (GamePaChangeAction) pkt.getAction();
				Entity enttt = getPerso().getMap().getEntities().get(actionpa.getEntity());
				enttt.setPa(enttt.getPa() + actionpa.getPa());
				EntityPaChangeEvent eventt = new EntityPaChangeEvent(client, enttt, actionpa.getPa());
				eventt.send();
				actionpa.setEntity(eventt.getEntity().getUUID());
				actionpa.setPa(eventt.getPa());
				break;
			case PM_CHANGE:
				GamePmChangeAction actionpm = (GamePmChangeAction) pkt.getAction();
				Entity ee = getPerso().getMap().getEntities().get(actionpm.getEntity());
				ee.setPm(ee.getPm() + actionpm.getPm());
				EntityPmChangeEvent ev = new EntityPmChangeEvent(client, ee, actionpm.getPm());
				ev.send();
				actionpm.setEntity(ev.getEntity().getUUID());
				actionpm.setPm(ev.getPm());
				break;
			case KILL:
				GameKillAction actionk = (GameKillAction) pkt.getAction();
				Entity de = getPerso().getMap().getEntities().get(actionk.getKilled());
				de.setDead(true);
				EntityDieEvent eevent = new EntityDieEvent(client, de);
				eevent.send();
				actionk.setKilled(eevent.getEntity().getUUID());
				break;
			case SUMMON:
				GameSummonAction actions = (GameSummonAction) pkt.getAction();
				for (Entry<GameMovementAction, MovementAction> e : actions.getSummoned().entrySet()) {
					ManchouCell cell = getPerso().getMap().getCells()[e.getValue().getCellId()];
					switch (e.getKey()) {
						case DEFAULT:
							MovementPlayer player = (MovementPlayer) (Object) e.getValue();
							if (player.getId() == getPerso().getUUID()) {
								getPerso().updateMovement(player);
								cell.addEntityOn(getPerso());
							} else {
								ManchouPlayerEntity parseMovement = ManchouPlayerEntity.parseMovement(player);
								getPerso().getMap().getEntities().put(player.getId(), parseMovement);
								cell.addEntityOn(parseMovement);
								new EntityPlayerJoinMapEvent(client, parseMovement).send();
							}
							return;
						case CREATE_INVOCATION:
						case CREATE_MONSTER:
							MovementMonster mob = (MovementMonster) (Object) e.getValue();
							ManchouMob parseMovement = ManchouMob.parseMovement(mob);
							getPerso().getMap().getEntities().put(mob.getId(), parseMovement);
							cell.addEntityOn(parseMovement);
							new MonsterJoinMapEvent(client, parseMovement).send();
							return;
						case CREATE_MONSTER_GROUP:
							MovementMonsterGroup mobs = (MovementMonsterGroup) (Object) e.getValue();
							ManchouMobGroup mobsgroup = ManchouMobGroup.parseMovement(mobs);
							getPerso().getMap().getEntities().put(mobs.getId(), mobsgroup);
							cell.addEntityOn(mobsgroup);
							new MonsterGroupSpawnEvent(client, mobsgroup).send();
							return;
						case CREATE_NPC:
							MovementNpc npc = (MovementNpc) (Object) e.getValue();
							ManchouNpc npcm = ManchouNpc.parseMovement(npc);
							getPerso().getMap().getEntities().put(npc.getId(), npcm);
							cell.addEntityOn(npcm);
							new NpcJoinMapEvent(client, npcm).send();
							return;
						default:
							break;
					}
				}
				break;
			case FIGHT_JOIN_ERROR:
				GameJoinErrorAction actionj = (GameJoinErrorAction) pkt.getAction();
				FightJoinErrorEvent e2 = new FightJoinErrorEvent(client, actionj.getError());
				e2.send();
				actionj.setError(e2.getError());
				if (isMitm())
					transmit = !balking.receive(GameJoinErrorAction.class).overflow(10);
				break;
			case MOVE:
				GameMoveAction actionm = (GameMoveAction) pkt.getAction();
				int cell = actionm.getPath().get(actionm.getPath().size() - 1).getCellId();
				Entity enti = null;
				if (pkt.getEntityId() == getPerso().getUUID()) enti = getPerso();
				else enti = getPerso().getMap().getEntities().get(pkt.getEntityId());
				if (enti == null) break;
				ManchouCell[] cells = getPerso().getMap().getCells();
				cells[enti.getCellId()].removeEntityOn(enti);
				cells[cell].addEntityOn(enti);
				ManchouMap map = getPerso().getMap();
				List<Node> nodes = new ArrayList();
				long time = 0;
				if (enti == getPerso()) {
					nodes = Functions.getNodes(enti.getCellId(), actionm.getPath(), getPerso().getMap());
					time = (long) (Pathfinding.getPathTime(nodes, map.getProtocolCells(), map.getWidth(), map.getHeight(), getPerso().hasMount()) * 30);
					LOGGER.warning("time = " + time);
					if (map.isEnded()) {
						getPerso().setMoving(true);
						getPerso().setLastMoved(System.currentTimeMillis());
						Queue<Node> queue = new LinkedList<>(nodes);
						ScheduledFuture<?> schFuture = Executors.SCHEDULED.scheduleAtFixedRate(() -> getPerso().positionRunner(queue), 0, time / nodes.size(), TimeUnit.MILLISECONDS);
						getPerso().setMoveListener(schFuture);
					} else if (isBot()) Executors.SCHEDULED.schedule(getPerso()::endAction, time, TimeUnit.MILLISECONDS);
				}
				enti.setCellId(cell);
				EntityMoveEvent ec = new EntityMoveEvent(client, enti, time, actionm.getPath(), nodes);
				ec.send();
				pkt.setEntityId(ec.getEntity().getUUID());
				actionm.setPath(ec.getPath());
				break;
			case DUEL_SERVER_ASK:
				GameDuelServerAction actiond = (GameDuelServerAction) pkt.getAction();
				Player sender = (Player) getPerso().getMap().getEntities().get(pkt.getEntityId());
				Player target = (Player) getPerso().getMap().getEntities().get(actiond.getTargetId());
				if (target.getUUID() == getPerso().getUUID()) getPerso().setDefied(true, sender.getUUID());
				DuelRequestEvent duelRequestEvent = new DuelRequestEvent(client, sender, target);
				duelRequestEvent.send();
				pkt.setEntityId(duelRequestEvent.getSender().getUUID());
				actiond.setTargetId(duelRequestEvent.getTarget().getUUID());
				if (isMitm())
					transmit = !balking.receive(GameDuelServerAction.class).overflow(10);
				break;
			case ACCEPT_DUEL:
				GameAcceptDuelAction actionda = (GameAcceptDuelAction) pkt.getAction();
				Player sendera = (Player) getPerso().getMap().getEntities().get(pkt.getEntityId());
				Player targeta = (Player) getPerso().getMap().getEntities().get(actionda.getTargetId());
				PlayerAcceptDuelEvent playerAcceptDuelEvent = new PlayerAcceptDuelEvent(client, sendera, targeta);
				playerAcceptDuelEvent.send();
				pkt.setEntityId(playerAcceptDuelEvent.getSender().getUUID());
				actionda.setTargetId(playerAcceptDuelEvent.getTarget().getUUID());
				break;
			case REFUSE_DUEL:
				GameRefuseDuelAction actiondr = (GameRefuseDuelAction) pkt.getAction();
				Player senderr = (Player) getPerso().getMap().getEntities().get(pkt.getEntityId());
				Player targetr = (Player) getPerso().getMap().getEntities().get(actiondr.getTargetId());
				PlayerRefuseDuelEvent refusevent = new PlayerRefuseDuelEvent(client, senderr, targetr);
				refusevent.send();
				pkt.setEntityId(refusevent.getSender().getUUID());
				actiondr.setTargetId(refusevent.getTarget().getUUID());
				if (isMitm())
					transmit = !balking.receive(GameRefuseDuelAction.class).overflow(10);
				break;
			case SPELL_LAUNCHED:
				GameSpellLaunchedAction actionsp = (GameSpellLaunchedAction) pkt.getAction();
				EntityLaunchSpellEvent lsevent = new EntityLaunchSpellEvent(client, actionsp.getSpellId(), actionsp.getCellId(), actionsp.getLvl());
				lsevent.send();
				actionsp.setCellId(lsevent.getCellId());
				actionsp.setLvl(lsevent.getSpellLvl());
				actionsp.setSpellId(lsevent.getSpellId());
				break;
			case HARVEST_TIME:
				GameHarvestTimeAction actionh = (GameHarvestTimeAction) pkt.getAction();
				if (pkt.getEntityId() == getPerso().getUUID()) {
					if (isBot()) Executors.SCHEDULED.schedule(getPerso()::endAction, actionh.getTime() + 500, TimeUnit.MILLISECONDS);
					HarvestTimeReceiveEvent eventh = new HarvestTimeReceiveEvent(client, actionh.getCellId(), actionh.getTime(), getPerso());
					eventh.send();
					actionh.setCellId(eventh.getCellId());
					actionh.setTime(eventh.getTime());
				} else {
					Entity entity = getPerso().getMap().getEntities().get(pkt.getEntityId());
					Player p = (Player) entity;
					if (p == null) break;
					HarvestTimeReceiveEvent eventss = new HarvestTimeReceiveEvent(client, actionh.getCellId(), actionh.getTime(), p);
					eventss.send();
					actionh.setCellId(eventss.getCellId());
					actionh.setTime(eventss.getTime());
					pkt.setEntityId(eventss.getPlayer().getUUID());
				}
				break;
			case TACLE:
				new PlayerTacledEvent(client).send();
				break;
			default:
				break;
		}
		if (transmit) transmit(pkt);
	}

	@Override
	public void handle(GameServerReadyPacket pkt) {
		log(pkt);
		Entity entity = getPerso().getMap().getEntities().get(pkt.getEntityId());
		if (entity == null) {
			transmit(pkt);
			return;
		}
		EntityReadyToFight event = new EntityReadyToFight(client, entity, pkt.isReady());
		event.send();
		pkt.setEntityId(event.getEntity().getUUID());
		pkt.setReady(event.isReady());
		transmit(pkt);
	}

	@Override
	public void handle(GameStartToPlayPacket pkt) {
		log(pkt);
		new FightStartEvent(client).send();
		transmit(pkt);
	}

	@Override
	public void handle(GameTurnFinishPacket pkt) {
		log(pkt);
		Entity entity = getPerso().getMap().getEntities().get(pkt.getEntityId());
		if (entity == null) {
			transmit(pkt);
			return;
		}
		EntityTurnEndEvent event = new EntityTurnEndEvent(client, entity);
		event.send();
		pkt.setEntityId(event.getEntity().getUUID());
		transmit(pkt);
	}

	@Override
	public void handle(GameTurnListPacket pkt) {
		log(pkt);
		FightTurnOrderReceiveEvent event = new FightTurnOrderReceiveEvent(client, Arrays.stream(pkt.getTurns()).mapToObj(getPerso().getMap().getEntities()::get).toArray(Entity[]::new));
		event.send();
		pkt.setTurns(Arrays.stream(event.getTurns()).filter(Objects::nonNull).mapToLong(Entity::getUUID).toArray());
		transmit(pkt);
	}

	@Override
	public void handle(GameTurnMiddlePacket pkt) {
		log(pkt);
		for (FightEntity e : pkt.getEntities()) {
			Entity ent = getPerso().getMap().getEntities().get(e.getId());
			if (ent == null) continue;
			if (ent instanceof Perso) {
				ManchouPerso player = (ManchouPerso) ent;
				player.setLife(e.getLife());
				player.setLifeMax(e.getLifeMax());
				player.setPa(e.getPa());
				player.setPm(e.getPm());
				player.setDead(e.isDead());
			} else if (ent instanceof Player) {
				ManchouPlayerEntity player = (ManchouPlayerEntity) ent;
				player.setLife(e.getLife());
				player.setLifeMax(e.getLifeMax());
				player.setPa(e.getPa());
				player.setPm(e.getPm());
				player.setDead(e.isDead());
			} else if (ent instanceof Mob) {
				ManchouMob mob = (ManchouMob) ent;
				mob.setLife(e.getLife());
				mob.setLifeMax(e.getLifeMax());
				mob.setPa(e.getPa());
				mob.setPm(e.getPm());
				mob.setDead(e.isDead());
			}
		}
		transmit(pkt);
	}

	@Override
	public void handle(GameTurnReadyPacket pkt) {
		log(pkt);
		if (isBot()) sendPkt(new GameTurnOkPacket());
		transmit(pkt);
	}

	@Override
	public void handle(GameTurnStartPacket pkt) {
		log(pkt);
		Entity e = getPerso().getMap().getEntities().get(pkt.getCharacterId());
		if (e == null) {
			transmit(pkt);
			return;
		}
		getPerso().getMap().setCurrentTurn(e);
		EntityTurnStartEvent event = new EntityTurnStartEvent(client, e, pkt.getTime());
		event.send();
		pkt.setCharacterId(event.getEntity().getUUID());
		pkt.setTime(event.getTime());
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeRequestOkPacket pkt) {
		log(pkt);
		ManchouMap map = getPerso().getMap();
		Entity player = map.getEntities().get(pkt.getPlayerId());
		Entity target = map.getEntities().get(pkt.getTargetId());
		if (target.getUUID() == getPerso().getUUID()) getPerso().setInvitedExchange(true);
		ExchangeAcceptedEvent event = new ExchangeAcceptedEvent(client, (Player) player, (Player) target, pkt.getExchange());
		event.send();
		pkt.setExchange(event.getExchange());
		pkt.setPlayerId(event.getSender().getUUID());
		pkt.setTargetId(event.getTarget().getUUID());
		transmit(pkt);
	}

	@Override
	public void handle(DialogLeavePacket pkt) {
		log(pkt);
		new DialogLeaveEvent(client).send();
		transmit(pkt);
	}

	@Override
	public void handle(DialogCreateOkPacket pkt) {
		log(pkt);
		DialogCreateEvent event = new DialogCreateEvent(client, (Npc) getPerso().getMap().getEntities().get(pkt.getNpcId()));
		event.send();
		pkt.setNpcId(event.getNpc().getUUID());
		transmit(pkt);
	}

	@Override
	public void handle(DialogQuestionPacket pkt) {
		log(pkt);
		DialogQuestionReceiveEvent event = new DialogQuestionReceiveEvent(client, pkt.getQuestion(), pkt.getResponse(), pkt.getQuestionParam());
		event.send();
		pkt.setQuestion(event.getQuestion());
		pkt.setQuestionParam(event.getQuestionParam());
		pkt.setResponse(event.getResponse());
		transmit(pkt);
	}

	@Override
	public void handle(DialogPausePacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeReadyPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(ItemAddOkPacket pkt) {
		log(pkt);
		Set<ManchouItem> collect = pkt.getItems().stream().map(ManchouItem::fromProtocolItem).collect(Collectors.toSet());
		getPerso().getInventory().addContent(pkt.getItems());
		ItemsAddedEvent event = new ItemsAddedEvent(client, collect);
		event.send();
		pkt.setItems(event.getItems().stream().map(i -> ((ManchouItem) i).serializeProtocol()).collect(Collectors.toSet()));
		transmit(pkt);
	}

	@Override
	public void handle(ItemAddErrorPacket pkt) {
		log(pkt);
		ItemAddErrorEvent event = new ItemAddErrorEvent(client, pkt.getResult());
		event.send();
		pkt.setResult(event.getResult());
		transmit(pkt);
	}

	@Override
	public void handle(ItemDropErrorPacket pkt) {
		log(pkt);
		ItemDropErrorEvent event = new ItemDropErrorEvent(client, pkt.getResult());
		event.send();
		pkt.setResult(event.getResult());
		transmit(pkt);
	}

	@Override
	public void handle(ItemRemovePacket pkt) {
		log(pkt);
		fr.aresrpg.tofumanchou.domain.data.item.Item removed = getPerso().getInventory().getContents().remove(pkt.getItemuid());
		ItemRemovedEvent event = new ItemRemovedEvent(client, removed);
		event.send();
		pkt.setItemuid(event.getItem().getUUID());
		transmit(pkt);
	}

	@Override
	public void handle(ItemQuantityUpdatePacket pkt) {
		log(pkt);
		fr.aresrpg.tofumanchou.domain.data.item.Item item = getPerso().getInventory().getItem(pkt.getItemUid());
		int lastQ = item.getAmount();
		item.setAmount(pkt.getAmount());
		ItemQuantityUpdateEvent event = new ItemQuantityUpdateEvent(client, item, lastQ, pkt.getAmount());
		event.send();
		pkt.setItemUid(event.getItem().getUUID());
		pkt.setAmount(event.getNewAmount());
		transmit(pkt);
	}

	@Override
	public void handle(ItemMovementConfirmPacket pkt) {
		log(pkt);
		ManchouItem item = (ManchouItem) getPerso().getInventory().getItem(pkt.getItemUid());
		item.setPosition(pkt.getPosition());
		ItemMovedEvent event = new ItemMovedEvent(client, item, pkt.getPosition());
		event.send();
		pkt.setItemUid(event.getItem().getUUID());
		pkt.setPosition(event.getPosition());
		transmit(pkt);
	}

	@Override
	public void handle(ItemToolPacket pkt) {
		log(pkt);
		getPerso().updateJob(pkt.getJobId());
		JobItemEquipEvent event = new JobItemEquipEvent(client, pkt.getJobId());
		event.send();
		pkt.setJobId(event.getJob());
		transmit(pkt);
	}

	@Override
	public void handle(ItemWeightPacket pkt) {
		log(pkt);
		getPerso().setPods(pkt.getCurrentWeight());
		getPerso().setMaxPods(pkt.getMaxWeight());
		PodsUpdateEvent event = new PodsUpdateEvent(client, pkt.getCurrentWeight(), pkt.getMaxWeight());
		event.send();
		pkt.setCurrentWeight(event.getCurrentPods());
		pkt.setMaxWeight(event.getMaxPods());
		transmit(pkt);
	}

	@Override
	public void handle(AccountStatsPacket pkt) {
		log(pkt);
		transmit(pkt);
		ManchouPerso s = getPerso();
		long xp = pkt.getXp();
		long xpLow = pkt.getXpLow();
		long xpHight = pkt.getXpHigh();
		int kamas = pkt.getKama();
		int bonuspts = pkt.getBonusPoints();
		int spellpts = pkt.getBonusPointsSpell();
		Alignement align = pkt.getAlignment();
		Rank rank = pkt.getRank();
		int life = pkt.getLife();
		int lifemax = pkt.getLifeMax();
		int energy = pkt.getEnergy();
		int energymax = pkt.getEnergyMax();
		int init = pkt.getInitiative();
		int pp = pkt.getProspection();
		Map<Stat, StatValue> stats = pkt.getStats();
		s.setXp(xp);
		s.setXpLow(xpLow);
		s.setXpHight(xpHight);
		s.getInventory().setKamas(kamas);
		s.setStatsPoints(bonuspts);
		s.setSpellsPoints(spellpts);
		s.setAlignement(align);
		s.setRank(rank);
		s.setLife(life);
		s.setLifeMax(lifemax);
		s.setEnergy(energy);
		s.setEnergyMax(energymax);
		s.setInitiative(init);
		s.setProspection(pp);
		s.setStats(stats);
		new PersoStatsEvent(client, xp, xpLow, xpHight, kamas, bonuspts, spellpts, align, pkt.getFakeAlignment(), rank, life, lifemax, energy, energymax, init, pp, stats, pkt.getExtradatas()).send();
	}

	@Override
	public void handle(AccountNewLevelPacket pkt) {
		log(pkt);
		transmit(pkt);
		getPerso().setLvl(pkt.getNewlvl());
		new LevelUpEvent(client, pkt.getNewlvl()).send();
	}

	@Override
	public void handle(AccountServerQueuePacket pkt) {
		log(pkt);
		transmit(pkt);
		new ServerQueuePositionEvent(client, pkt.getPosition());
	}

	@Override
	public void handle(ExchangeCraftPacket pkt) {
		log(pkt);
		CraftResultEvent event = new CraftResultEvent(client, pkt.getResult());
		event.send();
		pkt.setResult(event.getResult()); // inutile car le packet write n'est pas encore écrit lel
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeLocalMovePacket pkt) {
		log(pkt);
		ExchangeLocalMoveEvent event = new ExchangeLocalMoveEvent(client, pkt.getItemType(), pkt.getItemAmount(), pkt.getLocalKama(), pkt.isAdd());
		event.send();
		pkt.setAdd(event.isAdd());
		pkt.setItemAmount(event.getItemAmount());
		pkt.setItemType(event.getItemType());
		pkt.setLocalKama(event.getLocalKama());
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeDistantMovePacket pkt) {
		log(pkt);
		ManchouItem item = ManchouItem.fromProtocolItem(pkt.getMoved());
		ExchangeDistantMoveEvent event = new ExchangeDistantMoveEvent(client, item, pkt.isAdd(), pkt.getRemainingHours(), pkt.getKamas());
		event.send();
		pkt.setAdd(event.isAdd());
		pkt.setKamas(event.getKamas());
		pkt.setMoved(((ManchouItem) event.getMoved()).serializeProtocol());
		pkt.setRemainingHours(event.getRemainingHours());
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeCoopMovePacket pkt) {
		log(pkt);
		ManchouItem item = ManchouItem.fromProtocolItem(pkt.getMoved());
		ExchangeCoopMoveEvent event = new ExchangeCoopMoveEvent(client, item, pkt.getKamas(), pkt.isAdd());
		event.send();
		pkt.setAdd(event.isAdd());
		pkt.setKamas(event.getKamas());
		pkt.setMoved(((ManchouItem) event.getMoved()).serializeProtocol());
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeStorageMovePacket pkt) {
		log(pkt);
		switch (getPerso().getCurrentInv()) {
			case BANK:
				if (pkt.getMoved() != null) {
					if (pkt.isAdd()) client.getBank().getContents().put(pkt.getMoved().getUid(), ManchouItem.fromProtocolItem(pkt.getMoved()));
					else {
						Map<Long, fr.aresrpg.tofumanchou.domain.data.item.Item> contents = client.getBank().getContents();
						Item moved = pkt.getMoved();
						ManchouItem itemInBank = (ManchouItem) contents.get(moved.getUid());
						if (moved.getQuantity() >= itemInBank.getAmount()) contents.remove(moved.getUid());
						else itemInBank.setAmount(itemInBank.getAmount() - moved.getQuantity());
					}
				}
				if (pkt.getKamas() != -1) client.getBank().setKamas(pkt.getKamas());
				break;
			default:
				break;
		}
		ExchangeStorageMoveEvent event = new ExchangeStorageMoveEvent(client, ManchouItem.fromProtocolItem(pkt.getMoved()), pkt.getKamas(), pkt.isAdd());
		event.send();
		pkt.setAdd(event.isAdd());
		pkt.setKamas(event.getKamas());
		if (event.getMoved() != null) pkt.setMoved(((ManchouItem) event.getMoved()).serializeProtocol());
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeShopMovePacket pkt) {
		log(pkt);
		ExchangeShopMoveEvent event = new ExchangeShopMoveEvent(client, ManchouItem.fromProtocolItem(pkt.getMoved()), pkt.isAdd());
		event.send();
		pkt.setAdd(event.isAdd());
		pkt.setMoved(((ManchouItem) event.getItem()).serializeProtocol());
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeCraftPublicPacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeSellToNpcResultPacket pkt) {
		log(pkt);
		SellItemToNpcResultEvent event = new SellItemToNpcResultEvent(client, pkt.isSuccess());
		event.send();
		pkt.setSuccess(event.isSuccess());
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeBuyToNpcResultPacket pkt) {
		log(pkt);
		BuyItemToNpcResultEvent event = new BuyItemToNpcResultEvent(client, pkt.isSuccess());
		event.send();
		pkt.setSuccess(event.isSuccess());
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeCraftLoopPacket pkt) {
		log(pkt);
		CraftLoopIndexEvent event = new CraftLoopIndexEvent(client, pkt.getIndex());
		event.send();
		pkt.setIndex(event.getIndex());
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeCraftLoopEndPacket pkt) {
		log(pkt);
		CraftLoopEndEvent event = new CraftLoopEndEvent(client, pkt.getResult());
		event.send();
		pkt.setResult(event.getResult());
		transmit(pkt);
	}

	@Override
	public void handle(ExchangeLeaveResultPacket pkt) {
		log(pkt);
		ExchangeResultEvent event = new ExchangeResultEvent(client, pkt.isSuccess());
		event.send();
		pkt.setSuccess(event.isSuccess());
		transmit(pkt);
	}

	@Override
	public void handle(PartyRefusePacket pkt) {
		log(pkt);
		new PlayerRefuseGroupInvitationEvent(client).send();
		if (isMitm() && !balking.receive(PartyRefusePacket.class).overflow(10))
			transmit(pkt);
	}

	@Override
	public void handle(PartyInviteRequestOkPacket pkt) {
		log(pkt);
		if (pkt.getInvited().equalsIgnoreCase(getPerso().getPseudo())) getPerso().setInvitedGrp(true);
		GroupInvitationAcceptedEvent event = new GroupInvitationAcceptedEvent(client, pkt.getInviter(), pkt.getInvited());
		event.send();
		pkt.setInvited(event.getInvited());
		pkt.setInviter(event.getInviter());
		if (isMitm() && !balking.receive(PartyInviteRequestOkPacket.class).overflow(10))
			transmit(pkt);
	}

	@Override
	public void handle(PartyInviteRequestErrorPacket pkt) {
		log(pkt);
		GroupInviteErrorEvent event = new GroupInviteErrorEvent(client, pkt.getReason());
		event.send();
		pkt.setReason(event.getReason());
		if (isMitm() && !balking.receive(PartyInviteRequestErrorPacket.class).overflow(10))
			transmit(pkt);
	}

	@Override
	public void handle(PartyLeaderPacket pkt) {
		log(pkt);
		GroupLeaderUpdateEvent event = new GroupLeaderUpdateEvent(client, pkt.getLeaderId());
		event.send();
		pkt.setLeaderId(event.getLeaderId());
		transmit(pkt);
	}

	@Override
	public void handle(PartyCreateOkPacket pkt) {
		log(pkt);
		new GroupCreatedEvent(client).send();
		transmit(pkt);
	}

	@Override
	public void handle(PartyCreateErrorPacket pkt) {
		log(pkt);
		GroupCreateErrorEvent event = new GroupCreateErrorEvent(client, pkt.getReason());
		event.send();
		pkt.setReason(event.getReason());
		transmit(pkt);
	}

	@Override
	public void handle(PartyPlayerLeavePacket pkt) {
		log(pkt);
		PlayerLeaveGroupEvent event = new PlayerLeaveGroupEvent(client, pkt.getPlayer());
		event.send();
		pkt.setPlayer(event.getPlayer());
		transmit(pkt);
	}

	@Override
	public void handle(PartyFollowReceivePacket pkt) {
		log(pkt);
		if (pkt.isSuccess()) {
			GroupPlayerFollowEvent event = new GroupPlayerFollowEvent(client, pkt.getFollowed());
			event.send();
			pkt.setFollowed(event.getFollowed());
		} else {
			GroupPlayerStopFollowEvent event = new GroupPlayerStopFollowEvent(client, pkt.getFollowed());
			event.send();
			pkt.setFollowed(event.getFollowed());
		}
		transmit(pkt);
	}

	@Override
	public void handle(PartyMovementPacket pkt) {
		log(pkt);
		ManchouPlayerEntity[] array = Arrays.stream(pkt.getMembers()).map(m -> {
			ManchouPlayerEntity p = new ManchouPlayerEntity();
			p.setPseudo(m.getName());
			p.setColors(new ManchouColors(m.getColor1(), m.getColor2(), m.getColor3()));
			p.setLife(m.getLife());
			p.setLifeMax(m.getMaxLife());
			p.setLevel(m.getLvl());
			p.setInitiative(m.getInitiative());
			p.setProspection(m.getProspection());
			p.setGfx(m.getGfxFile());
			p.setSide(m.getSide());
			p.setAccessories(m.getAccessories());
			return p;
		}).toArray(ManchouPlayerEntity[]::new);
		GroupMembersUpdateEvent event = new GroupMembersUpdateEvent(client, pkt.getMove(), array);
		event.send();
		pkt.setMove(event.getUpdate());
		pkt.setMembers(Arrays.stream(event.getMembers()).map(i -> (ManchouPlayerEntity) i).map(m -> new PartyMember(m.getUUID(), m.getPseudo(), m.getGfx(), m.getColors().getFirstColor(),
				m.getColors().getSecondColor(), m.getColors().getThirdColor(), m.getLife(), m.getLifeMax(), m.getLevel(), m.getInitiative(), m.getProspection(), m.getSide(), m.getAccessories()))
				.toArray(PartyMember[]::new));
		transmit(pkt);
	}

	@Override
	public void handle(GameTeamPacket pkt) {
		log(pkt);
		FightTeamEvent event = new FightTeamEvent(client, pkt.getFirstId(), pkt.getEntities());
		event.send();
		pkt.setFirstId(event.getFightId());
		pkt.setEntities(event.getTeams());
		transmit(pkt);
	}

	@Override
	public void handle(JobSkillsPacket pkt) {
		log(pkt);
		for (Job j : pkt.getJobs())
			getPerso().getJobs().put(j.getType(), new ManchouJob(j.getType()));
		PlayerJobsReceiveEvent event = new PlayerJobsReceiveEvent(client, pkt.getJobs());
		event.send(); // niks j'allow pas l'edit
		transmit(pkt);
	}

	@Override
	public void handle(JobXpPacket pkt) {
		log(pkt);
		for (JobInfo j : pkt.getInfos())
			for (fr.aresrpg.tofumanchou.domain.data.Job jjob : getPerso().getJobs().values()) {
				ManchouJob job = (ManchouJob) jjob;
				if (j.getJob() == job.getType()) {
					job.setLvl(j.getLvl());
					job.setMaxXp(j.getXpMax());
					job.setMinXp(j.getXpMin());
					job.setXp(j.getXp());
				}
			}
		PlayerJobsInfosReceiveEvent event = new PlayerJobsInfosReceiveEvent(client, getPerso().getJobs().values());
		event.send();
		pkt.setInfos(event.getJobs().stream().map(j -> (ManchouJob) j).map(ManchouJob::serializeProtocol).toArray(JobInfo[]::new));
		transmit(pkt);
	}

	@Override
	public void handle(JobLevelPacket pkt) {
		log(pkt);
		for (fr.aresrpg.tofumanchou.domain.data.Job j : getPerso().getJobs().values())
			if (j.getType() == pkt.getJob()) ((ManchouJob) j).setLvl(pkt.getLvl());
		JobLevelUpEvent event = new JobLevelUpEvent(client, getPerso().getJobs().get(pkt.getJob()), pkt.getLvl());
		event.send();
		pkt.setJob(event.getJob().getType());
		pkt.setLvl(event.getLvl());
		transmit(pkt);
	}

	@Override
	public void handle(GameSpawnPacket pkt) {
		log(pkt);
		if (pkt.isCreated()) {
			getPerso().getMap().getFightsOnMap().add(pkt.getFight());
			FightSpawnEvent event = new FightSpawnEvent(client, pkt.getFight());
			event.send();
			pkt.setFight(event.getFight());
		} else {
			getPerso().getMap().getFightsOnMap().remove(pkt.getFight());
			FightDespawnEvent event = new FightDespawnEvent(client, pkt.getFight());
			event.send();
			pkt.setFight(event.getFight());
		}
		transmit(pkt);
	}

	@Override
	public void handle(FightCountPacket pkt) {
		log(pkt);
		FightCountEvent event = new FightCountEvent(client, pkt.getCount());
		event.send();
		pkt.setCount(event.getCount());
		transmit(pkt);
	}

	@Override
	public void handle(FightListPacket pkt) {
		log(pkt);
		FightListEvent event = new FightListEvent(client, pkt.getFights());
		event.send();
		pkt.setFights(event.getFights());
		transmit(pkt);
	}

	@Override
	public void handle(FightDetailsPacket pkt) {
		log(pkt);
		FightDetailsEvent event = new FightDetailsEvent(client, pkt.getDetailsId(), pkt.getT0(), pkt.getT1());
		event.send();
		pkt.setId(event.getId());
		pkt.setT0(event.getT0());
		pkt.setT1(event.getT1());
		transmit(pkt);
	}

	@Override
	public void handle(InfoCompassPacket pkt) {
		log(pkt);
		CompassCoordinatesEvent event = new CompassCoordinatesEvent(client, pkt.getX(), pkt.getY());
		event.send();
		pkt.setX(event.getX());
		pkt.setY(event.getY());
		transmit(pkt);
	}

	@Override
	public void handle(InfoCoordinatePacket pkt) {
		log(pkt);
		GroupMembersPositionsEvent event = new GroupMembersPositionsEvent(client, pkt.getPlayers());
		event.send();
		pkt.setPlayers(event.getPlayers());
		transmit(pkt);
	}

	@Override
	public void handle(SubwayLeavePacket pkt) {
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(GameCellObjectPacket pkt) { // TODO faire packet
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(GameCellUpdatePacket pkt) { // TODO creer event
		log(pkt);
		DofusMap map = getPerso().getMap().serialize();
		pkt.updateCells(map);
		getPerso().getMap().updateFields(map);
		transmit(pkt);
	}

	@Override
	public void handle(ChatServerMessagePacket pkt) { // TODO creer event
		log(pkt);
		transmit(pkt);
	}

	@Override
	public void handle(GuildJoinErrorPacket pkt) {
		log(pkt);
		if (isMitm() && !balking.receive(pkt.getError().getClass()).overflow(10))
			transmit(pkt);
	}

	@Override
	public void handle(GuildInvitedPacket pkt) {
		log(pkt);
		getPerso().setInvitedGuild(true, pkt.getSprite());
		GuildInvitedEvent event = new GuildInvitedEvent(client, pkt.getPlayer(), pkt.getGuild());
		event.send();
		pkt.setGuild(event.getGuild());
		pkt.setPlayer(event.getSender());
		transmit(pkt);
	}

	@Override
	public void handle(SubwayCreatePacket pkt) {
		log(pkt);
		ZaapiGuiOpenEvent event = new ZaapiGuiOpenEvent(client, pkt.getCurrent(), pkt.getWaypoints());
		event.send();
		pkt.setCurrent(event.getCurrent());
		pkt.setWaypoints(event.getWaypoints());
		transmit(pkt);
	}

	@Override
	public void handle(TutorialCreatePacket pkt) {
		log(pkt);
		TutorialCreatedEvent event = new TutorialCreatedEvent(client, pkt.getTutoId(), pkt.getLongid());
		event.send();
		pkt.setTutoId(event.getTutoId());
		pkt.setLongid(event.getLongId());
		transmit(pkt);
	}

}
