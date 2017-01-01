package fr.aresrpg.tofumanchou.infra.io;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.commons.domain.condition.Option;
import fr.aresrpg.commons.domain.log.Logger;
import fr.aresrpg.commons.domain.log.LoggerBuilder;
import fr.aresrpg.tofumanchou.domain.Manchou;
import fr.aresrpg.tofumanchou.domain.io.Bridge;

import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @since
 */
public class ManchouBridge implements Bridge {

	public static final InetSocketAddress SERVER_ADRESS = new InetSocketAddress("80.239.173.166", 443);
	private boolean running;
	private Logger logger;

	public ManchouBridge(String label, ServerSocketChannel channel) {
		this.logger = new LoggerBuilder(label).setUseConsoleHandler(true, true, Option.none(), Option.none()).build();
		Selector slc = Manchou.getSelector();
		running = true;
		while (running) {
			logger.info("En attente d'une connection..");
			try {
				slc.select();
				Set<SelectionKey> keys = slc.selectedKeys();
				Iterator<SelectionKey> it = keys.iterator();
				while (it.hasNext()) {
					SelectionKey key = it.next();
					it.remove();
					if (key.isAcceptable()) {
						SocketChannel client = channel.accept();
						client.configureBlocking(false);
						LOGGER.info("Client Accepted [" + client.getRemoteAddress() + "]");
						new ManchouProxy(null, client, SocketChannel.open(SERVER_ADRESS));
					}
				}
			} catch (Exception e) {
				LOGGER.error(e, "Connection lost !");
				shutdown();
			}
		}
	}

	@Override
	public void shutdown() {
		running = false;
		LOGGER.info("Closing '" + logger.getName() + "'");
	}

}
