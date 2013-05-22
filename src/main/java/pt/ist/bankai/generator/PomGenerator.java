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

public class PomGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(PomGenerator.class);

	public static void generate(String baseFolderName, VelocityContext ctx, boolean force) throws IOException {

		final Template t = Velocity.getTemplate("templates/pom.xml.vm");

		final Writer writer = new StringWriter();
		t.merge(ctx, writer);

		File pomFile = new File(baseFolderName + "/pom.xml");
		pomFile.getParentFile().mkdirs();

		if (!force && pomFile.exists()) {
			LOG.error("The file " + pomFile.getPath() + " already exists. Use -f or --force to overwrite it");
		} else {
			LOG.info("Generating " + pomFile.getPath());
			FileUtils.write(pomFile, writer.toString());
		}
	}
}
