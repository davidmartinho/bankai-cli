package pt.ist.bankai;

import java.util.HashMap;
import java.util.Map;

import pt.ist.bankai.command.CollectionCommand;
import pt.ist.bankai.command.Command;
import pt.ist.bankai.command.HelpCommand;
import pt.ist.bankai.command.InitCommand;
import pt.ist.bankai.command.ModelCommand;
import pt.ist.bankai.command.PortalizeCommand;
import pt.ist.bankai.command.ServerCommand;
import pt.ist.bankai.command.ViewCommand;

import com.beust.jcommander.JCommander;

public class Console {

	private static final Map<String, Command> COMMAND_MAP = new HashMap<String, Command>();
	private static JCommander JC = new JCommander();

	public static final void registerCommand(Command cmd) {
		COMMAND_MAP.put(cmd.getName(), cmd);
		JC.addCommand(cmd.getName(), cmd);
	}

	public static void main(String[] args) throws Exception {
		CollectionCommand.register();
		InitCommand.register();
		ModelCommand.register();
		ServerCommand.register();
		HelpCommand.register();
		ViewCommand.register();
		PortalizeCommand.register();
		JC.parse(args);
		Command cmd = COMMAND_MAP.get(JC.getParsedCommand());
		if (cmd != null) {
			cmd.perform();
		}
	}

	public static Map<String, JCommander> getCommandMap() {
		return JC.getCommands();
	}

	public static JCommander getJCommander() {
		return JC;
	}
}
