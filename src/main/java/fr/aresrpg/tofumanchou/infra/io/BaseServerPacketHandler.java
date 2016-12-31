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
import fr.aresrpg.dofus.protocol.dialog.DialogLeavePacket;
import fr.aresrpg.dofus.protocol.dialog.server.*;
import fr.aresrpg.dofus.protocol.exchange.server.*;
import fr.aresrpg.dofus.protocol.fight.server.*;
import fr.aresrpg.dofus.protocol.friend.server.FriendListPacket;
import fr.aresrpg.dofus.protocol.game.actions.GameMoveAction;
import fr.aresrpg.dofus.protocol.game.actions.server.*;
import fr.aresrpg.dofus.protocol.game.client.GameCreatePacket;
import fr.aresrpg.dofus.protocol.game.movement.*;
import fr.aresrpg.dofus.protocol.game.server.*;
import fr.aresrpg.dofus.protocol.guild.server.GuildStatPacket;
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
import fr.aresrpg.dofus.protocol.waypoint.ZaapLeavePacket;
import fr.aresrpg.dofus.protocol.waypoint.server.ZaapCreatePacket;
import fr.aresrpg.dofus.protocol.waypoint.server.ZaapUseErrorPacket;
import fr.aresrpg.dofus.structures.Rank;
import fr.aresrpg.dofus.structures.character.AvailableCharacter;
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
import fr.aresrpg.tofumanchou.domain.Manchou;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.enums.Spells;
import fr.aresrpg.tofumanchou.domain.event.*;
import fr.aresrpg.tofumanchou.domain.io.Proxy;
import fr.aresrpg.tofumanchou.domain.io.Proxy.ProxyConnectionType;
import fr.aresrpg.tofumanchou.domain.util.concurrent.Executors;
import fr.aresrpg.tofumanchou.infra.config.Variables;
import fr.aresrpg.tofumanchou.infra.data.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @since
 */
public class BaseServerPacketHandler implements ServerPacketHandler {

	private ManchouAccount client;
	private ManchouProxy proxy;
	private String ticket;
	private Server current;

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

	public boolean isBot() {
		return !isMitm();
	}

	private void transmit(Packet pkt) {
		if (isBot()) return;
		proxy.getLocalConnection().send(pkt);
	}

	private void sendPkt(Packet pkt) {
		getClient().getConnection().send(pkt);
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
		client.setHc(pkt.getHashKey());
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
		if (isMitm()) client.setPerso(new ManchouPerso(client, pkt.getCharacter().getPseudo(), current));
		((PlayerInventory) getPerso().getInventory()).parseCharacter(pkt.getCharacter());
		new PersoSelectEvent(client, getPerso()).send();
		transmit(pkt);
	}

	@Override
	public void handle(AccountServerEncryptedHostPacket pkt) {
		log(pkt);
		this.ticket = pkt.getTicketKey();
		transmit(pkt);
	}

	@Override
	public void handle(AccountServerHostPacket pkt) {
		log(pkt);
		this.ticket = pkt.getTicketKey();
		if (isBot()) {
			client.getConnection().closeConnection();
			client.setConnection(new DofusConnection<>(getPerso().getPseudo(), SocketChannel.open(new InetSocketAddress(pkt.getIp(), pkt.getPort())), this, ProtocolRegistry.Bound.SERVER));
			client.getConnection().start();
		} else {
			String ip = pkt.getIp();
			ServerSocketChannel srvchannel = ServerSocketChannel.open();
			srvchannel.bind(new InetSocketAddress(0));
			int localPort = srvchannel.socket().getLocalPort();
			getProxy().getLocalConnection().send(new AccountServerHostPacket().setIp(Variables.PASSERELLE_IP).setPort(localPort).setTicketKey(pkt.getTicketKey()));
			getProxy().changeConnection(new DofusConnection<>("Local", srvchannel.accept(), getProxy().getLocalHandler(), Bound.CLIENT),
					ProxyConnectionType.LOCAL);
			getProxy().changeConnection(
					new DofusConnection<>("Remote", SocketChannel.open(new InetSocketAddress(ip, pkt.getPort())), getProxy().getRemoteHandler(), Bound.SERVER),
					ProxyConnectionType.REMOTE);
		}
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
		if (pkt.getMessage() == null) return;
		ManchouMap fight = (ManchouMap) getPerso().getMap();
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
			default:
				break;
		}
		InfoMessageEvent event = new InfoMessageEvent(client, pkt.getType(), pkt.getMessageId(), pkt.getExtraDatas());
		event.send();
		pkt.setType(event.getType());
		pkt.setMessageId(event.getMessageId());
		pkt.setExtraDatas(event.getExtraDatas());
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
		transmit(pkt);
	}

	@Override
	public void handle(GameActionStartPacket pkt) {
		log(pkt);
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
		if (pkt.getState() == GameType.FIGHT) {
			ManchouMap map = getPerso().getMap();
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
			m = Maps.loadMap(extractVariable, pkt.getDecryptKey());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ManchouMap map = ManchouMap.fromDofusMap(m);
		getPerso().setMap(map);
		new MapJoinEvent(client, map).send();
		transmit(pkt);
	}

	@Override
	public void handle(GameMapFramePacket pkt) {
		log(pkt);
		pkt.getFrames().forEach((id, frame) -> {
			ManchouCell cell = getPerso().getMap().getCells()[id];
			cell.applyFrame(frame);
			if (cell.isRessource() && cell.isRessourceSpawned()) new RessourceSpawnEvent(client, cell).send(); // event asynchrone
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
				new EntityLeaveMapEvent(client, actor.getId()).send(); // ASYNCHRONE
			});
			return;
		}
		pkt.getActors().forEach(e -> {
			switch (e.getFirst()) {
				case DEFAULT:
					MovementPlayer player = (MovementPlayer) (Object) e.getSecond();
					if (player.getId() == getPerso().getUUID()) getPerso().updateMovement(player);
					else {
						ManchouPlayerEntity parseMovement = ManchouPlayerEntity.parseMovement(player);
						getPerso().getMap().getEntities().put(player.getId(), parseMovement);
						new EntityPlayerJoinMapEvent(client, parseMovement).send(); // ASYNCHRONE
					}
					return;
				case CREATE_INVOCATION:
				case CREATE_MONSTER:
					MovementMonster mob = (MovementMonster) (Object) e.getSecond();
					ManchouMob parseMovement = ManchouMob.parseMovement(mob);
					getPerso().getMap().getEntities().put(mob.getId(), parseMovement);
					new MonsterJoinMapEvent(client, parseMovement).send(); // ASYNCHRONE
					return;
				case CREATE_MONSTER_GROUP:
					MovementMonsterGroup mobs = (MovementMonsterGroup) (Object) e.getSecond();
					ManchouMobGroup mobsgroup = ManchouMobGroup.parseMovement(mobs);
					getPerso().getMap().getEntities().put(mobs.getId(), mobsgroup);
					new MonsterGroupSpawnEvent(client, mobsgroup).send(); // ASYNCHRONE
					return;
				case CREATE_NPC:
					MovementNpc npc = (MovementNpc) (Object) e.getSecond();
					ManchouNpc npcm = ManchouNpc.parseMovement(npc);
					getPerso().getMap().getEntities().put(npc.getId(), npcm);
					new NpcJoinMapEvent(client, npcm).send(); // ASYNCHRONE
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
			Entity entity = getPerso().getMap().getEntities().get(p.getEntityId());
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
		switch (pkt.getType()) {
			case ERROR:
				break;
			case LIFE_CHANGE:
				GameLifeChangeAction actionl = (GameLifeChangeAction) pkt.getAction();
				Entity entt = getPerso().getMap().getEntities().get(actionl.getEntity());
				entt.setLife(actionl.getLife());
				EntityLifeChangeEvent event = new EntityLifeChangeEvent(client, entt, actionl.getLife());
				event.send();
				actionl.setEntity(event.getEntity().getUUID());
				actionl.setLife(event.getLife());
				break;
			case PA_CHANGE:
				GamePaChangeAction actionpa = (GamePaChangeAction) pkt.getAction();
				Entity enttt = getPerso().getMap().getEntities().get(actionpa.getEntity());
				enttt.setPa(actionpa.getPa());
				EntityPaChangeEvent eventt = new EntityPaChangeEvent(client, enttt, actionpa.getPa());
				eventt.send();
				actionpa.setEntity(eventt.getEntity().getUUID());
				actionpa.setPa(eventt.getPa());
				break;
			case PM_CHANGE:
				GamePmChangeAction actionpm = (GamePmChangeAction) pkt.getAction();
				Entity ee = getPerso().getMap().getEntities().get(actionpm.getEntity());
				ee.setPm(actionpm.getPm());
				EntityPmChangeEvent ev = new EntityPmChangeEvent(client, ee, actionpm.getPm());
				ev.send();
				actionpm.setEntity(ev.getEntity().getUUID());
				actionpm.setPm(ev.getPm());
				break;
			case KILL:
				GameKillAction actionk = (GameKillAction) pkt.getAction();
				Entity de = getPerso().getMap().getEntities().get(actionk.getKilled());
				EntityDieEvent eevent = new EntityDieEvent(client, de);
				eevent.send();
				actionk.setKilled(eevent.getEntity().getUUID());
				break;
			case SUMMON:
				GameSummonAction actions = (GameSummonAction) pkt.getAction();
				for (Entry<GameMovementAction, MovementAction> e : actions.getSummoned().entrySet()) {
					switch (e.getKey()) {
						case DEFAULT:
							MovementPlayer player = (MovementPlayer) (Object) e.getValue();
							if (player.getId() == getPerso().getUUID()) getPerso().updateMovement(player);
							else {
								ManchouPlayerEntity parseMovement = ManchouPlayerEntity.parseMovement(player);
								getPerso().getMap().getEntities().put(player.getId(), parseMovement);
								new EntityPlayerJoinMapEvent(client, parseMovement).send(); // ASYNCHRONE
							}
							return;
						case CREATE_INVOCATION:
						case CREATE_MONSTER:
							MovementMonster mob = (MovementMonster) (Object) e.getValue();
							ManchouMob parseMovement = ManchouMob.parseMovement(mob);
							getPerso().getMap().getEntities().put(mob.getId(), parseMovement);
							new MonsterJoinMapEvent(client, parseMovement).send(); // ASYNCHRONE
							return;
						case CREATE_MONSTER_GROUP:
							MovementMonsterGroup mobs = (MovementMonsterGroup) (Object) e.getValue();
							ManchouMobGroup mobsgroup = ManchouMobGroup.parseMovement(mobs);
							getPerso().getMap().getEntities().put(mobs.getId(), mobsgroup);
							new MonsterGroupSpawnEvent(client, mobsgroup).send(); // ASYNCHRONE
							return;
						case CREATE_NPC:
							MovementNpc npc = (MovementNpc) (Object) e.getValue();
							ManchouNpc npcm = ManchouNpc.parseMovement(npc);
							getPerso().getMap().getEntities().put(npc.getId(), npcm);
							new NpcJoinMapEvent(client, npcm).send(); // ASYNCHRONE
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
				break;
			case MOVE:
				GameMoveAction actionm = (GameMoveAction) pkt.getAction();
				int cell = actionm.getPath().get(actionm.getPath().size() - 1).getCellId();
				Entity enti = null;
				if (pkt.getEntityId() == getPerso().getUUID()) enti = getPerso();
				else enti = getPerso().getMap().getEntities().get(pkt.getEntityId());
				enti.setCellId(cell);
				EntityMoveEvent ec = new EntityMoveEvent(client, enti, actionm.getPath());
				ec.send();
				pkt.setEntityId(ec.getEntity().getUUID());
				actionm.setPath(ec.getPath());
				break;
			case DUEL_SERVER_ASK:
				GameDuelServerAction actiond = (GameDuelServerAction) pkt.getAction();
				getGameActionHandler().forEach(h -> h.onDuel(pkt.getEntityId(), actiond.getTargetId()));
				break;
			case ACCEPT_DUEL:
				GameAcceptDuelAction actionda = (GameAcceptDuelAction) pkt.getAction();
				getGameActionHandler().forEach(h -> h.onPlayerAcceptDuel(pkt.getEntityId(), actionda.getTargetId()));
				break;
			case REFUSE_DUEL:
				GameRefuseDuelAction actiondr = (GameRefuseDuelAction) pkt.getAction();
				getGameActionHandler().forEach(h -> h.onPlayerRefuseDuel(pkt.getEntityId(), actiondr.getTargetId()));
				break;
			case SPELL_LAUNCHED:
				GameSpellLaunchedAction actionsp = (GameSpellLaunchedAction) pkt.getAction();
				getGameActionHandler().forEach(h -> h.onSpellLaunched(actionsp.getSpellId(), actionsp.getCellId(), actionsp.getLvl()));
				break;
			case HARVEST_TIME:
				GameHarvestTimeAction actionh = (GameHarvestTimeAction) pkt.getAction();
				if (pkt.getEntityId() == getPerso().getUUID()) {
					if (isBot() && getPerso().getJob() != null) Executors.SCHEDULED.schedule(() -> {
						int action = 0;
						switch (getPerso().getJob().getType()) {
							case JOB_BUCHERON:
								action = 1;
								break;

							default:
								break;
						}
						sendPkt(new GameActionACKPacket().setActionId(action));
					} , actionh.getTime(), TimeUnit.MILLISECONDS);
					HarvestTimeReceiveEvent event = new HarvestTimeReceiveEvent(client, actionh.getCellId(), actionh.getTime());
					event.send();
					actionh.setCellId(event.getCellId());
					actionh.setTime(event.getTime());
				} else {
					Entity entity = getPerso().getMap().getEntities().get(pkt.getEntityId());
					Player p = (Player) entity;
					PlayerStoleYourRessourceEvent event = new PlayerStoleYourRessourceEvent(client, actionh.getCellId(), actionh.getTime(), p);
					event.send();
					actionh.setCellId(event.getCellId());
					actionh.setTime(event.getTime());
					pkt.setEntityId(event.getThief().getUUID());
				}
				break;
			case TACLE:
				new PlayerTacledEvent(client).send();
				break;
			default:
				break;
		}
		transmit(pkt);
	}

	@Override
	public void handle(GameServerReadyPacket pkt) {
		log(pkt);
		transmit(pkt);
		getGameHandler().forEach(h -> h.onPlayerReadyToFight(pkt.getEntityId(), pkt.isReady()));
	}

	@Override
	public void handle(GameStartToPlayPacket pkt) {
		log(pkt);
		transmit(pkt);
		getGameHandler().forEach(GameServerHandler::onFightStart);
	}

	@Override
	public void handle(GameTurnFinishPacket pkt) {
		log(pkt);
		transmit(pkt);
		Fight fight = getPerso().getFightInfos().getCurrentFight();
		if (fight == null) {
			LOGGER.severe(
					"TurnFinishPacket received before fight initialisation ! skiping.. | No worries the server will send all infos again, this appen sometimes when you keep reconnecting in a fight");
			return;
		}
		getGameHandler().forEach(h -> h.onEntityTurnEnd(pkt.getEntityId()));
	}

	@Override
	public void handle(GameTurnListPacket pkt) {
		log(pkt);
		transmit(pkt);
		Fight fight = getPerso().getFightInfos().getCurrentFight();
		if (fight == null) {
			LOGGER.severe(
					"TurnListPacket received before fight initialisation ! skiping.. | No worries the server will send all infos again, this appen sometimes when you keep reconnecting in a fight");
			return;
		}
		getGameHandler().forEach(h -> h.onFightTurnInfos(pkt.getTurns()));
	}

	@Override
	public void handle(GameTurnMiddlePacket pkt) {
		log(pkt);
		transmit(pkt);
		Fight fight = getPerso().getFightInfos().getCurrentFight();
		if (fight == null) {
			LOGGER.severe(
					"TurnMiddlePacket received before fight initialisation ! skiping.. | No worries the server will send all infos again, this appen sometimes when you keep reconnecting in a fight");
			return;
		}
		for (FightEntity e : pkt.getEntities())
			fight.addEntity(e);
		getGameHandler().forEach(h -> h.onFighterInfos(pkt.getEntities()));
	}

	@Override
	public void handle(GameTurnReadyPacket pkt) {
		log(pkt);
		transmit(pkt);
		Fight fight = getPerso().getFightInfos().getCurrentFight();
		if (fight == null) {
			LOGGER.severe(
					"TurnReadyPacket received before fight initialisation ! skiping.. | No worries the server will send all infos again, this appen sometimes when you keep reconnecting in a fight");
			return;
		}
		getGameHandler().forEach(h -> h.onEntityTurnReady(pkt.getEntityId()));
	}

	@Override
	public void handle(GameTurnStartPacket pkt) {
		log(pkt);
		transmit(pkt);
		Fight fight = getPerso().getFightInfos().getCurrentFight();
		if (fight == null) {
			LOGGER.severe(
					"TurnStartPacket received before fight initialisation ! skiping.. | No worries the server will send all infos again, this appen sometimes when you keep reconnecting in a fight");
			return;
		}
		fight.setCurrentTurn(pkt.getCharacterId());
		getPerso().getAbilities().getFightAbility().getBotThread().unpause();
		getGameHandler().forEach(h -> h.onEntityTurnStart(pkt.getCharacterId(), pkt.getTime()));
	}

	@Override
	public void handle(ExchangeRequestOkPacket pkt) {
		log(pkt);
		transmit(pkt);
		getExchangeHandler().forEach(h -> h.onExchangeRequestOk(pkt.getPlayerId(), pkt.getTargetId(), pkt.getExchange()));
	}

	@Override
	public void handle(DialogLeavePacket pkt) {
		log(pkt);
		transmit(pkt);
		getDialogHandler().forEach(DialogServerHandler::onDialogLeave);
	}

	@Override
	public void handle(DialogCreateOkPacket pkt) {
		log(pkt);
		transmit(pkt);
		getPerso().getAbilities().getBaseAbility().getBotThread().unpause();
		getDialogHandler().forEach(h -> h.onDialogCreate(pkt.getNpcId()));
	}

	@Override
	public void handle(DialogQuestionPacket pkt) {
		log(pkt);
		transmit(pkt);
		getDialogHandler().forEach(h -> h.onQuestion(pkt.getQuestion(), pkt.getQuestionParam(), pkt.getResponse()));
	}

	@Override
	public void handle(DialogPausePacket pkt) {
		log(pkt);
		transmit(pkt);
		getDialogHandler().forEach(DialogServerHandler::onDialogPause);
	}

	@Override
	public void handle(ExchangeReadyPacket pkt) {
		log(pkt);
		transmit(pkt);
		getExchangeHandler().forEach(h -> h.onExchangeReady(pkt.getExtraData()));
	}

	@Override
	public void handle(ItemAddOkPacket pkt) {
		log(pkt);
		transmit(pkt);
		for (Item i : pkt.getItems())
			getPerso().getInventory().getContents().put(i.getUid(), i); // on add direct car quand c juste une update de quantité il y a un autre packet
		getItemHandler().forEach(h -> h.onItemsAdd(pkt.getItems()));
	}

	@Override
	public void handle(ItemAddErrorPacket pkt) {
		log(pkt);
		transmit(pkt);
		getItemHandler().forEach(h -> h.onItemAddError(pkt.getResult()));
	}

	@Override
	public void handle(ItemDropErrorPacket pkt) {
		log(pkt);
		transmit(pkt);
		getItemHandler().forEach(h -> h.onItemDropError(pkt.getResult()));
	}

	@Override
	public void handle(ItemRemovePacket pkt) {
		log(pkt);
		transmit(pkt);
		System.out.println("Item remove id : " + pkt.getItemuid());
		getPerso().getInventory().getContents().remove(pkt.getItemuid());
		getItemHandler().forEach(h -> h.onItemRemove(pkt.getItemuid()));
	}

	@Override
	public void handle(ItemQuantityUpdatePacket pkt) {
		log(pkt);
		transmit(pkt);
		getPerso().getInventory().getItem(pkt.getItemUid()).setQuantity(pkt.getAmount());
		getItemHandler().forEach(h -> h.onItemQuantityUpdate(pkt.getItemUid(), pkt.getAmount()));
	}

	@Override
	public void handle(ItemMovementConfirmPacket pkt) {
		log(pkt);
		transmit(pkt);
		getPerso().getInventory().getItem(pkt.getItemUid()).setPosition(pkt.getPosition());
		getItemHandler().forEach(h -> h.onItemMove(pkt.getItemUid(), pkt.getPosition()));
	}

	@Override
	public void handle(ItemToolPacket pkt) {
		log(pkt);
		transmit(pkt);
		getPerso().getBotInfos().updateCurrentJob(pkt.getJobId());
		getItemHandler().forEach(h -> h.onItemToolEquip(pkt.getJobId()));
	}

	@Override
	public void handle(ItemWeightPacket pkt) {
		log(pkt);
		transmit(pkt);
		getPerso().getStatsInfos().setPods(pkt.getCurrentWeight());
		getPerso().getStatsInfos().setMaxPods(pkt.getMaxWeight());
		getItemHandler().forEach(h -> h.onPodsUpdate(pkt.getCurrentWeight(), pkt.getMaxWeight()));
	}

	@Override
	public void handle(AccountStatsPacket pkt) {
		log(pkt);
		transmit(pkt);
		ManchouPerso s = getPerso();
		int xp = pkt.getXp();
		int xpLow = pkt.getXpLow();
		int xpHight = pkt.getXpHigh();
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
	public void handle(ExchangeCraftPacket pkt) { // TODO
		log(pkt);
		transmit(pkt);
		getExchangeHandler().forEach(h -> h.onCraft(pkt.getResult()));
	}

	@Override
	public void handle(ExchangeLocalMovePacket pkt) { // TODO
		log(pkt);
		transmit(pkt);
		getExchangeHandler().forEach(h -> h.onLocalMove(pkt.getItemType(), pkt.getItemAmount(), pkt.getLocalKama()));
	}

	@Override
	public void handle(ExchangeDistantMovePacket pkt) { // TODO
		log(pkt);
		transmit(pkt);
		getExchangeHandler().forEach(h -> h.onDistantMove(pkt.getMoved(), pkt.isAdd(), pkt.getKamas(), pkt.getRemainingHours()));
	}

	@Override
	public void handle(ExchangeCoopMovePacket pkt) { // TODO
		log(pkt);
		transmit(pkt);
		getExchangeHandler().forEach(h -> h.onCoopMove(pkt.getMoved(), pkt.getKamas(), pkt.isAdd()));
	}

	@Override
	public void handle(ExchangeStorageMovePacket pkt) {
		log(pkt);
		transmit(pkt);
		switch (getPerso().getAbilities().getBaseAbility().getStates().currentInventory) {
			case BANK:
				if (pkt.getMoved() != null) {
					if (pkt.isAdd()) getAccount().getBanque().getContents().put(pkt.getMoved().getUid(), pkt.getMoved());
					else {
						Map<Long, Item> contents = getAccount().getBanque().getContents();
						Item moved = pkt.getMoved();
						Item itemInBank = contents.get(moved.getUid());
						if (moved.getQuantity() >= itemInBank.getQuantity()) contents.remove(moved.getUid());
						else itemInBank.setQuantity(itemInBank.getQuantity() - moved.getQuantity());
					}
				}
				if (pkt.getKamas() != -1) getAccount().getBanque().setKamas(pkt.getKamas());
				break;
			default:
				break;
		}
		getExchangeHandler().forEach(h -> h.onStorageMove(pkt.getMoved(), pkt.getKamas(), pkt.isAdd()));
	}

	@Override
	public void handle(ExchangeShopMovePacket pkt) {
		log(pkt);
		transmit(pkt);
		getExchangeHandler().forEach(h -> h.onShopMove(pkt.getMoved(), pkt.isAdd()));
	}

	@Override
	public void handle(ExchangeCraftPublicPacket pkt) {
		log(pkt);
		transmit(pkt);
		getExchangeHandler().forEach(h -> h.onCraftPublic(pkt.isCraftPublicMode(), pkt.getItemid(), pkt.getMultiCraftSkill()));
	}

	@Override
	public void handle(ExchangeSellToNpcResultPacket pkt) {
		log(pkt);
		transmit(pkt);
		getExchangeHandler().forEach(h -> h.onSellToNpc(pkt.isSuccess()));
	}

	@Override
	public void handle(ExchangeBuyToNpcResultPacket pkt) {
		log(pkt);
		transmit(pkt);
		getExchangeHandler().forEach(h -> h.onBuyToNpc(pkt.isSuccess()));
	}

	@Override
	public void handle(ExchangeCraftLoopPacket pkt) {
		log(pkt);
		transmit(pkt);
		getExchangeHandler().forEach(h -> h.onCraftLoop(pkt.getIndex()));
	}

	@Override
	public void handle(ExchangeCraftLoopEndPacket pkt) {
		log(pkt);
		transmit(pkt);
		getExchangeHandler().forEach(h -> h.onCraftLoopEnd(pkt.getResult()));
	}

	@Override
	public void handle(ExchangeLeaveResultPacket pkt) {
		log(pkt);
		transmit(pkt);
		getExchangeHandler().forEach(h -> h.onLeave(pkt.isSuccess()));
	}

	@Override
	public void handle(PartyRefusePacket pkt) {
		log(pkt);
		transmit(pkt);
		getPartyHandler().forEach(PartyServerHandler::onPlayerRefuse);
	}

	@Override
	public void handle(PartyInviteRequestOkPacket pkt) {
		log(pkt);
		transmit(pkt);
		getPartyHandler().forEach(h -> h.onInvitePlayerInGroup(pkt.getInviter(), pkt.getInvited()));
	}

	@Override
	public void handle(PartyInviteRequestErrorPacket pkt) {
		log(pkt);
		transmit(pkt);
		getPartyHandler().forEach(h -> h.onInviteFail(pkt.getReason()));
	}

	@Override
	public void handle(PartyLeaderPacket pkt) {
		log(pkt);
		transmit(pkt);
		getPartyHandler().forEach(h -> h.onGroupLeaderUpdate(pkt.getLeaderId()));
	}

	@Override
	public void handle(PartyCreateOkPacket pkt) {
		log(pkt);
		transmit(pkt);
		getPartyHandler().forEach(PartyServerHandler::onJoinGroupOk);
	}

	@Override
	public void handle(PartyCreateErrorPacket pkt) {
		log(pkt);
		transmit(pkt);
		getPartyHandler().forEach(h -> h.onJoinGroupError(pkt.getReason()));
	}

	@Override
	public void handle(PartyPlayerLeavePacket pkt) {
		log(pkt);
		transmit(pkt);
		getPartyHandler().forEach(h -> h.onPlayerLeaveGroup(pkt.getPlayer()));
	}

	@Override
	public void handle(PartyFollowReceivePacket pkt) {
		log(pkt);
		transmit(pkt);
		if (pkt.isSuccess())
			getPartyHandler().forEach(h -> h.onFollow(pkt.getFollowed()));
		else getPartyHandler().forEach(PartyServerHandler::onStopFollow);
	}

	@Override
	public void handle(PartyMovementPacket pkt) {
		log(pkt);
		transmit(pkt);
		getPartyHandler().forEach(h -> Arrays.stream(pkt.getMembers()).forEach(m -> h.onPartyMemberUpdate(pkt.getMove(), m)));
	}

	@Override
	public void handle(GameTeamPacket pkt) {
		log(pkt);
		transmit(pkt);
		getGameHandler().forEach(h -> h.onFightTeams(pkt.getFirstId(), pkt.getEntities()));
	}

	@Override
	public void handle(JobSkillsPacket pkt) {
		log(pkt);
		transmit(pkt);
		for (Job j : pkt.getJobs())
			getPerso().getBotInfos().getJobs().add(new ManchouJob(j.getType()));
		getJobHandler().forEach(h -> Arrays.stream(pkt.getJobs()).forEach(h::onPlayerJobInfo));
	}

	@Override
	public void handle(JobXpPacket pkt) {
		log(pkt);
		transmit(pkt);
		for (JobInfo j : pkt.getInfos())
			for (ManchouJob job : getPerso().getBotInfos().getJobs())
				if (j.getJob() == job.getType()) {
					job.setLvl(j.getLvl());
					job.setMaxXp(j.getXpMax());
					job.setMinXp(j.getXpMin());
					job.setXp(j.getXp());
				}
		getPerso().getAbilities().getBaseAbility().getBotThread().unpause();
		getJobHandler().forEach(h -> Arrays.stream(pkt.getInfos()).forEach(h::onJobXp));
	}

	@Override
	public void handle(JobLevelPacket pkt) {
		log(pkt);
		transmit(pkt);
		for (ManchouJob j : getPerso().getBotInfos().getJobs())
			if (j.getType() == pkt.getJob()) j.setLvl(pkt.getLvl());
		getJobHandler().forEach(h -> h.onJobLvl(pkt.getJob(), pkt.getLvl()));
	}

	@Override
	public void handle(GameSpawnPacket pkt) {
		log(pkt);
		transmit(pkt);
		if (pkt.isCreated()) {
			getPerso().getFightInfos().getFightsOnMap().add(pkt.getFight());
			getGameHandler().forEach(h -> h.onFightSpawn(pkt.getFight()));
		}
	}

	@Override
	public void handle(FightCountPacket pkt) {
		log(pkt);
		transmit(pkt);
		getFightHandler().forEach(h -> h.onFightCount(pkt.getCount()));
	}

	@Override
	public void handle(FightListPacket pkt) {
		log(pkt);
		transmit(pkt);
		getFightHandler().forEach(h -> pkt.getFights().forEach(h::onFightInfos));
	}

	@Override
	public void handle(FightDetailsPacket pkt) {
		log(pkt);
		transmit(pkt);
		getFightHandler().forEach(h -> h.onFightDetails(pkt.getDetailsId(), pkt.getT0(), pkt.getT1()));
	}

	@Override
	public void handle(InfoCompassPacket pkt) {
		log(pkt);
		transmit(pkt);
		getInfoHandler().forEach(h -> h.onCompass(pkt.getX(), pkt.getY()));
	}

	@Override
	public void handle(InfoCoordinatePacket pkt) {
		log(pkt);
		transmit(pkt);
		getInfoHandler().forEach(h -> pkt.getPlayers().forEach(h::onFollowedPlayerMove));
	}

}
