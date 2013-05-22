package pt.ist.bankai.command;

import java.io.IOException;

import org.apache.velocity.VelocityContext;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import pt.ist.bankai.Console;
import pt.ist.bankai.generator.ModelGenerator;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Manages Bankai models")
public class ModelCommand extends Command {

	public static final String NAME = "model";

	@Parameter(names = { "-n", "--name" }, description = "Model Name", required = true)
	private String modelName;

	@Parameter(names = { "-u", "--urlRoot" }, description = "Model URL Root", required = true)
	private String urlRoot;

	@Parameter(names = { "-f", "--force" })
	private boolean force = false;

	@Override
	protected void execute(VelocityContext ctx) throws IOException, XmlPullParserException {
		ctx.put("modelName", modelName);
		ctx.put("urlRoot", urlRoot);

		ModelGenerator.generate(ctx, force);
	}

	@Override
	public String getName() {
		return NAME;
	}

	public static void register() {
		Console.registerCommand(new ModelCommand());
	}

	@Override
	protected boolean requiresMavenProject() {
		return true;
	}

}
