package fr.aresrpg.tofumanchou.domain.util;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

/**
 * 
 * @since
 */
public class PercentPrinter {

	private final int total;
	private int current;
	private int lastPercent;

	public PercentPrinter(int total) {
		this.total = total;
	}

	private int getPercent() {
		return 100 * current / total;
	}

	public void incrementAndPrint(String format) {
		++current;
		int percent = getPercent();
		if (percent < 10) percent = 1;
		else if (percent < 20) percent = 10;
		else if (percent < 30) percent = 20;
		else if (percent < 40) percent = 30;
		else if (percent < 50) percent = 40;
		else if (percent < 60) percent = 50;
		else if (percent < 70) percent = 60;
		else if (percent < 80) percent = 70;
		else if (percent < 90) percent = 80;
		else if (percent < 100) percent = 90;
		else percent = 100;
		if (percent <= lastPercent) return;
		switch (percent) {
			case 1:
			case 10:
			case 20:
			case 30:
			case 40:
			case 50:
			case 60:
			case 70:
			case 80:
			case 90:
			case 100:
				lastPercent = percent;
				LOGGER.info(String.format(format, percent));
				break;
			default:
				break;
		}
	}

	public static void main(String[] args) {
		int total = 7800;
		PercentPrinter printer = new PercentPrinter(total);
		for (int i = 0; i < total; i++)
			printer.incrementAndPrint("Loading.. [%s%%]");
	}

	@Override
	public String toString() {
		return "PercentPrinter [total=" + total + ", current=" + current + ", lastPercent=" + lastPercent + "]";
	}
}
