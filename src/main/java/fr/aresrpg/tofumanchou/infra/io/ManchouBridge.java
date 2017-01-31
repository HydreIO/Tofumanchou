package fr.aresrpg.tofumanchou.infra.io;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.dofus.util.Pair;
import fr.aresrpg.tofumanchou.domain.Manchou;
import fr.aresrpg.tofumanchou.domain.io.Bridge;
import fr.aresrpg.tofumanchou.infra.config.Variables;

import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @since
 */
public class ManchouBridge implements Bridge {

	public static final InetSocketAddress SERVER_ADRESS = new InetSocketAddress(Variables.SERVER_IP, 443);
	private boolean running;

	public ManchouBridge(String label, ServerSocketChannel channel) {
		Selector slc = Manchou.getSelector();
		running = true;
		while (running) {
			LOGGER.info("En attente d'une connection..");
			try {
				LOGGER.debug("selecting !");
				slc.select();
				Set<SelectionKey> keys = slc.selectedKeys();
				Iterator<SelectionKey> it = keys.iterator();
				while (it.hasNext()) {
					LOGGER.debug("it next");
					SelectionKey key = it.next();
					it.remove();
					if (key.isAcceptable()) {
						SocketChannel client = channel.accept();
						client.configureBlocking(false);
						LOGGER.info("Client Accepted [" + client.getRemoteAddress() + "]");
						SocketChannel server = SocketChannel.open(SERVER_ADRESS);
						Manchou.SOCKETS.add(new Pair<SocketChannel, SocketChannel>(client, server));
						new ManchouProxy(client, server);
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
		LOGGER.info("Closing bridge");
	}

}
