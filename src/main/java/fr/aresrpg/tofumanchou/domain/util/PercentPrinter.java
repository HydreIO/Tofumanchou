package fr.aresrpg.tofumanchou.domain.util;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

/**
 * 
 * @since
 */
public class PercentPrinter {

	public static final String FORMAT = "[%s%%]";
	private final int total;
	private int current;
	private int last;

	public PercentPrinter(int total) {
		this.total = total;
	}

	private int getPercent() {
		return 100 * current / total;
	}

	public void incrementAndPrint(String format) {
		++current;
		int percent = getPercent();
		if (last < percent && percent / 10 * 10 == percent) {
			last = percent;
			LOGGER.info(String.format(format, percent));
		}
	}

	public static void main(String[] args) {
		int total = 7800;
		PercentPrinter printer = new PercentPrinter(total);
		for (int i = 0; i < total; i++)
			printer.incrementAndPrint("Loading.. " + FORMAT);
	}

	@Override
	public String toString() {
		return "PercentPrinter [total=" + total + ", current=" + current + "]";
	}

}
