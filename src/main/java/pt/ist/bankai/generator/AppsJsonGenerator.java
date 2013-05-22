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

import pt.ist.bankai.BankaiConfig;

public class AppsJsonGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(AppsJsonGenerator.class);

	public static void generate(VelocityContext ctx, boolean force) throws IOException {

		final Template t = Velocity.getTemplate("templates/apps.json.vm");

		final Writer writer = new StringWriter();
		t.merge(ctx, writer);

		String artifactId = (String) ctx.get("artifactId");

		File appsJsonFile = new File(BankaiConfig.DEFAULT_WEBAPP_PATH + artifactId + "/apps.json");

		if (!force && appsJsonFile.exists()) {
			LOG.error("The file " + appsJsonFile.getPath() + " already exists. User -f or --force options to overwrite it.");
		} else {
			LOG.info("Generating " + appsJsonFile.getPath());
			FileUtils.write(appsJsonFile, writer.toString());
		}

	}
}
