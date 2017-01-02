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

import fr.aresrpg.commons.domain.log.AnsiColors.AnsiColor;
import fr.aresrpg.commons.domain.log.Logger;
import fr.aresrpg.dofus.protocol.DofusConnection;
import fr.aresrpg.dofus.protocol.ProtocolRegistry.Bound;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.event.MitmConnectEvent;
import fr.aresrpg.tofumanchou.domain.io.Proxy;
import fr.aresrpg.tofumanchou.domain.util.concurrent.Executors;
import fr.aresrpg.tofumanchou.infra.data.ManchouAccount;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class ManchouProxy implements Proxy {

	private DofusConnection localConnection;
	private DofusConnection remoteConnection;

	private BaseClientPacketHandler localHandler;
	private BaseServerPacketHandler remoteHandler;
	private Account account;
	private String hc;

	public ManchouProxy(Account account, SocketChannel localChannel, SocketChannel remoteChannel) throws IOException {
		this.remoteHandler = new BaseServerPacketHandler(this);
		this.localHandler = new BaseClientPacketHandler(this);
		changeConnection(new DofusConnection<>("Local", localChannel, localHandler, Bound.CLIENT), ProxyConnectionType.LOCAL);
		changeConnection(new DofusConnection<>("Remote", remoteChannel, remoteHandler, Bound.SERVER), ProxyConnectionType.REMOTE);
	}

	public void setAccount(Account account) {
		this.account = account;
		((ManchouAccount) account).setProxy(this);
		((ManchouAccount) account).setConnection(remoteConnection);
		localHandler.setClient(account);
		remoteHandler.setClient(account);
		new MitmConnectEvent(account).send();
	}

	public String getHc() {
		return this.hc;
	}

	public Account getAccount() {
		return account;
	}

	public void setHc(String hc) {
		this.hc = hc;
	}

	public Logger getLogger() {
		return account.getLogger();
	}

	public void shutdown() {
		getLocalConnection().closeConnection();
		getRemoteConnection().closeConnection();
	}

	/**
	 * @return the localConnection
	 */
	public DofusConnection getLocalConnection() {
		return localConnection;
	}

	/**
	 * @return the remoteConnection
	 */
	public DofusConnection getRemoteConnection() {
		return remoteConnection;
	}

	public void changeConnection(DofusConnection connection, ProxyConnectionType type) {
		LOGGER.info("Starting '" + connection.getLabel() + "' connection !");
		try {
			if (type == ProxyConnectionType.LOCAL) {
				if (this.localConnection != null) this.localConnection.closeConnection();
				this.localConnection = connection;
			} else {
				if (this.remoteConnection != null) this.remoteConnection.closeConnection();
				this.remoteConnection = connection;
				if (account != null) ((ManchouAccount) account).setConnection(connection);
			}
			Executors.CACHED.execute(() -> {
				LOGGER.info("Connection '" + connection.getLabel() + "' Started !");
				try {
					connection.start();
				} catch (Exception e) {
					LOGGER.info(AnsiColor.CYAN + "Client déconnecté !");
					shutdown();
				}
			});
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * @return the localHandler
	 */
	public BaseClientPacketHandler getLocalHandler() {
		return localHandler;
	}

	/**
	 * @return the remoteHandler
	 */
	public BaseServerPacketHandler getRemoteHandler() {
		return remoteHandler;
	}

	@Override
	public Account getClient() {
		return account;
	}

}
