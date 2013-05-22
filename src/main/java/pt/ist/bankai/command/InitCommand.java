package pt.ist.bankai.command;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.bankai.Console;
import pt.ist.bankai.generator.APIFolderGenerator;
import pt.ist.bankai.generator.HomeTemplateGenerator;
import pt.ist.bankai.generator.HomeViewGenerator;
import pt.ist.bankai.generator.IndexHtmlGenerator;
import pt.ist.bankai.generator.MessagesGenerator;
import pt.ist.bankai.generator.PomGenerator;
import pt.ist.bankai.generator.RouterGenerator;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Manages Bankai collections")
public class InitCommand extends Command {

	private static final Logger LOG = LoggerFactory.getLogger(InitCommand.class);

	public static void register() {
		Console.registerCommand(new InitCommand());
	}

	public static final String NAME = "init";

	@Parameter(names = { "-n", "--name" }, description = "The name of the application", required = true)
	private String applicationName;

	@Parameter(names = { "-id", "--artifactId" })
	private String artifactId;

	@Parameter(names = { "-v", "--version" })
	private String version = "1.0-SNAPSHOT";

	@Parameter(names = { "-f", "--force" })
	private boolean force = false;

	@Parameter(names = "--bankaiVersion")
	private String bankaiVersion = "0.0.1-SNAPSHOT";

	private String hyphenizeApplicationName(String applicationName) {
		return applicationName.replaceAll(" ", "-").toLowerCase();
	}

	@Override
	protected void execute(VelocityContext ctx) throws Exception {
		ctx.put("applicationName", applicationName);
		if (artifactId == null) {
			artifactId = hyphenizeApplicationName(applicationName);
		}
		ctx.put("artifactId", artifactId);
		ctx.put("version", version);
		ctx.put("bankaiVersion", bankaiVersion);

		PomGenerator.generate(artifactId, ctx, force);
		IndexHtmlGenerator.generate(artifactId, ctx, force);
		MessagesGenerator.generate(artifactId, ctx, force);
		HomeTemplateGenerator.generate(artifactId, ctx, force);
		RouterGenerator.generate(artifactId, ctx, force);
		HomeViewGenerator.generate(artifactId, ctx, force);
		APIFolderGenerator.generate(artifactId, ctx, force);

		LOG.info("BANKAI! Your Bankai scaffolded webapp was successfully generated.");
		LOG.info("Use the help command to list available commands (e.g. bankai help)");
		LOG.info("Enter the project folder " + artifactId + "(e.g. cd " + artifactId + ")");
		LOG.info("Run the Bankai Server:" + artifactId + " (e.g. bankai server)");

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
