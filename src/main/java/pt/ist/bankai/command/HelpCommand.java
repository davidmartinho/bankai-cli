package pt.ist.bankai.command;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import pt.ist.bankai.BankaiConfig;
import pt.ist.bankai.Console;

public class HelpCommand extends Command {

	public static void register() {
		Console.registerCommand(new HelpCommand());
	}

	public static final String NAME = "help";

	@Override
	protected void execute(VelocityContext ctx) throws IOException {
		ctx.put("version", BankaiConfig.VERSION);

		final Template t = Velocity.getTemplate("templates/bankai.version.vm");

		final Writer writer = new StringWriter();
		t.merge(ctx, writer);

		System.out.println(writer.toString());

		Console.getJCommander().usage();

	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected boolean requiresMavenProject() {
		return false;
	}
}
