package pt.ist.bankai.command;

import java.net.BindException;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.bankai.Console;
import pt.ist.bankai.server.BankaiServer;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Manages the Bankai Agile Development Server")
public class ServerCommand extends Command {

	private static final Logger LOG = LoggerFactory.getLogger(BankaiServer.class);

	public static final String NAME = "server";

	@Parameter(names = "-port", description = "The server's port (defaults to 8080)")
	public String port = "8080";

	@Override
	protected void execute(VelocityContext ctx) throws Exception {
		String artifactId = (String) ctx.get("artifactId");
		BankaiServer server = new BankaiServer(artifactId, Integer.parseInt(port));
		try {
			server.start();
			LOG.info("Starting Bankai Server...");
			LOG.info("Bankai Server is now listening on port " + port);
			LOG.info("Application URL: http://localhost:" + port + "/" + artifactId + "/");

			while (true) {
			}
		} catch (BindException e) {
			LOG.error("Port " + port
					+ " is already in use.\nSpecify another port using -port option:\ne.g.: bankai server -port 8090");
		}
	}

	@Override
	public String getName() {
		return NAME;
	}

	public static void register() {
		Console.registerCommand(new ServerCommand());
	}

	@Override
	protected boolean requiresMavenProject() {
		return true;
	}
}
