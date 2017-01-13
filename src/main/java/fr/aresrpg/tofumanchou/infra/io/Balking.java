package fr.aresrpg.tofumanchou.infra.io;

import fr.aresrpg.tofumanchou.domain.event.PacketSpamEvent;
import fr.aresrpg.tofumanchou.domain.util.concurrent.Executors;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @since
 */
public class Balking {

	private ConcurrentMap<Class, AtomicInteger> packets = new ConcurrentHashMap<>();
	private Set<Class> needEvent = new HashSet<>();

	public Balking() {
		Executors.SCHEDULER.register(this::reset, 2, TimeUnit.SECONDS);
	}

	void reset() {
		for (Class c : needEvent) {
			AtomicInteger atomicInteger = packets.get(c);
			if (atomicInteger == null) continue;
			new PacketSpamEvent(c, atomicInteger.get()).send();
		}
		packets.clear();
		needEvent.clear();
	}

	public BalkingRecord receive(Class clazz) {
		AtomicInteger at = getOrCreate(clazz);
		at.incrementAndGet();
		needEvent.add(clazz);
		return new BalkingRecord(at);
	}

	AtomicInteger getOrCreate(Class clazz) {
		AtomicInteger nbr = packets.get(clazz);
		if (nbr == null) packets.put(clazz, nbr = new AtomicInteger());
		return nbr;
	}

	public static class BalkingRecord {
		AtomicInteger record;

		public BalkingRecord(AtomicInteger record) {
			this.record = record;
		}

		public boolean overflow(int max) {
			return record.get() > max;
		}

		public int get() {
			return record.get();
		}

	}
}
