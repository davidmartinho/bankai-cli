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

public class HomeViewGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(HomeViewGenerator.class);

	public static void generate(String baseFolderName, VelocityContext ctx, boolean force) throws IOException {

		final Template t = Velocity.getTemplate("templates/home.view.js.vm");

		final Writer writer = new StringWriter();
		t.merge(ctx, writer);

		File homeViewFile = new File(baseFolderName + "/src/main/webapp/" + ctx.get("artifactId") + "/js/views/Home.js");
		homeViewFile.getParentFile().mkdirs();

		if (!force && homeViewFile.exists()) {
			LOG.error("The file " + homeViewFile.getPath() + " already exists. Use -f or --force to overwrite it.");
		} else {
			LOG.info("Generating " + homeViewFile.getPath());
			FileUtils.write(homeViewFile, writer.toString());
		}
	}
}
