package pt.ist.bankai.command;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.bankai.velocity.VelocityConfigurator;

public abstract class Command {

	private static final Logger LOG = LoggerFactory.getLogger(Command.class);

	public final void perform() throws Exception {
		VelocityConfigurator.config();
		VelocityContext ctx = new VelocityContext();
		try {
			MavenXpp3Reader reader = new MavenXpp3Reader();
			Model model = reader.read(new FileReader("pom.xml"));
			ctx.put("artifactId", model.getArtifactId());
			ctx.put("applicationName", model.getName());
			ctx.put("originalPomVersion", model.getVersion());
			ctx.put("originalBankaiVersion", model.getProperties().get("version.pt.ist.bankai"));
		} catch (FileNotFoundException e) {
			if (requiresMavenProject()) {
				LOG.error("Could not find pom.xml");
				LOG.error("Make sure you're in the project's root folder.");
				System.exit(-1);
			}
		}
		execute(ctx);
	}

	protected abstract void execute(VelocityContext ctx) throws Exception;

	public abstract String getName();

	protected abstract boolean requiresMavenProject();

}
