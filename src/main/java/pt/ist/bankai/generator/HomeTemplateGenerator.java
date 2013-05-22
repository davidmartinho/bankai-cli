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

public class HomeTemplateGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(HomeTemplateGenerator.class);

	public static void generate(String baseFolderName, VelocityContext ctx, boolean force) throws IOException {

		final Template t = Velocity.getTemplate("templates/home.template.html.vm");

		final Writer writer = new StringWriter();
		t.merge(ctx, writer);

		File homeTemplateFile = new File(baseFolderName + "/src/main/webapp/" + ctx.get("artifactId") + "/templates/Home.html");
		homeTemplateFile.getParentFile().mkdirs();

		if (!force && homeTemplateFile.exists()) {

			LOG.error("The file " + homeTemplateFile.getPath() + " already exists. User -f or --force to overwrite it");
		} else {
			LOG.info("Generating example template " + homeTemplateFile.getPath());
			FileUtils.write(homeTemplateFile, writer.toString());
		}

	}
}
