package pt.ist.bankai.command;

import java.io.IOException;

import org.apache.velocity.VelocityContext;

import pt.ist.bankai.Console;
import pt.ist.bankai.generator.CollectionGenerator;
import pt.ist.bankai.generator.ModelGenerator;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Manages Backbone Collections")
public class CollectionCommand extends Command {

	public static void register() {
		Console.registerCommand(new CollectionCommand());
	}

	public static final String NAME = "collection";

	@Parameter(names = { "-n", "--name" }, description = "The name of the Collection and respective Model", required = true)
	private String collectionName;

	@Parameter(names = { "-u", "--url" }, description = "Collection's API URL", required = true)
	private String url;

	@Parameter(names = { "-m", "--model" }, description = "The name of the Model of the Collection (defaults to the Collection name if omitted)")
	private String modelName;

	@Parameter(names = { "-f", "--force" })
	private boolean force = false;

	@Override
	protected void execute(VelocityContext ctx) throws IOException {
		ctx.put("collectionName", collectionName);
		ctx.put("url", url);
		if (modelName == null) {
			modelName = collectionName;
		}
		ctx.put("modelName", modelName);
		ctx.put("urlRoot", url);

		CollectionGenerator.generate(ctx);
		ModelGenerator.generate(ctx, force);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected boolean requiresMavenProject() {
		return true;
	}
}
