package fr.aresrpg.tofumanchou.infra.io;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.commons.domain.util.schedule.Schedule;
import fr.aresrpg.commons.domain.util.schedule.Scheduled;
import fr.aresrpg.tofumanchou.domain.Manchou;
import fr.aresrpg.tofumanchou.domain.util.concurrent.Executors;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @since
 */
public class Lifetimer implements Scheduled {

	private static final Lifetimer instance = new Lifetimer();

	private Lifetimer() {
		Executors.SCHEDULER.register(this);
	}

	public static void init() {
		;
	}

	@Schedule(rate = 10, unit = TimeUnit.SECONDS)
	public void checkOnline() {
		ByteBuffer buffer = ByteBuffer.allocate(128);
		buffer.put(("BN\n\0").getBytes());
		System.out.println("=================");
		Manchou.SOCKETS.removeIf(pair -> {
			LOGGER.debug("-> Trying socket ! ");
			try {
				int read = pair.getFirst().read(buffer);
				System.out.println(" read = " + read);
				if (read == -1) {
					pair.getFirst().close();
					pair.getSecond().close();
					throw new IOException();
				}
			} catch (IOException e) {
				LOGGER.debug("Removing a socket !");
				return true;
			}
			return false;
		});
	}

}
