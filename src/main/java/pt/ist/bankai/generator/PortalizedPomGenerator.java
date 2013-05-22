package pt.ist.bankai.generator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PortalizedPomGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(PortalizedPomGenerator.class);

	public static void generate(VelocityContext ctx, boolean force) throws IOException {

		final Template t = Velocity.getTemplate("templates/portalized.pom.xml.vm");

		final Writer writer = new StringWriter();
		t.merge(ctx, writer);

		File portalizedPomFile = new File("pom.xml");

		if (!force && portalizedPomFile.exists()) {
			LOG.error("The file " + portalizedPomFile.getPath() + " already exists. Use -f or --force to overwrite it");
		} else {
			LOG.info("Generating " + portalizedPomFile.getPath());
			FileUtils.write(portalizedPomFile, writer.toString());
		}

	}

}
