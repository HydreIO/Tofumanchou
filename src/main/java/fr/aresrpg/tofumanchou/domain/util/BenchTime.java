package fr.aresrpg.tofumanchou.domain.util;

import java.util.function.LongSupplier;

/**
 * 
 * @since
 */
public class BenchTime implements LongSupplier {

	private long start;

	public BenchTime() {
		this.start = System.currentTimeMillis();
	}

	@Override
	public long getAsLong() {
		return System.currentTimeMillis() - start;
	}

}
