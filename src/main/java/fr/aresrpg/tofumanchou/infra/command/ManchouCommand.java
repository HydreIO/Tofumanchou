package fr.aresrpg.tofumanchou.infra.command;

import fr.aresrpg.tofumanchou.domain.command.Command;

import java.util.Arrays;

/**
 * 
 * @since
 */
public class ManchouCommand implements Command {

	private String cmd;
	private String[] args;

	public ManchouCommand(String cmd, String[] args) {
		this.cmd = cmd;
		this.args = args;
	}

	@Override
	public String getCmd() {
		return cmd;
	}

	@Override
	public String[] getArgs() {
		return args;
	}

	@Override
	public String toString() {
		return "ManchouCommand [cmd=" + cmd + ", args=" + Arrays.toString(args) + "]";
	}

	public static Command parse(String line) {
		if (line.isEmpty()) return null;
		String[] all = line.split(" ");
		String cmd = all[0];
		if (cmd.isEmpty()) return null;
		if (all.length <= 1) return new ManchouCommand(cmd, new String[0]);
		String[] args = new String[all.length - 1];
		for (int i = 1; i < all.length; i++)
			args[i - 1] = all[i];
		return new ManchouCommand(cmd, args);
	}

	public static void main(String[] args) {
		System.out.println(parse("salut fdp ok"));
	}

}
