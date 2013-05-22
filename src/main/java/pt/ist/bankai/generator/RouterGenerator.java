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

public class RouterGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(RouterGenerator.class);

	public static void generate(String baseFolderName, VelocityContext ctx, boolean force) throws IOException {

		final Template t = Velocity.getTemplate("templates/router.js.vm");

		final Writer writer = new StringWriter();
		t.merge(ctx, writer);

		File routerFile = new File(baseFolderName + "/src/main/webapp/" + ctx.get("artifactId") + "/js/router.js");
		routerFile.getParentFile().mkdirs();

		if (!force && routerFile.exists()) {
			LOG.error("The file " + routerFile.getPath() + " already exists. Use -f or --force options to overwrite it.");
		} else {
			LOG.info("Generating " + routerFile.getPath());
			FileUtils.write(routerFile, writer.toString());
		}

	}
}
