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

public class CollectionGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(CollectionGenerator.class);

	public static void generate(VelocityContext ctx) throws IOException {
		final Template t = Velocity.getTemplate("templates/collection.js.vm");

		final Writer writer = new StringWriter();
		t.merge(ctx, writer);

		File collectionFile = new File(BankaiConfig.DEFAULT_WEBAPP_PATH + ctx.get("artifactId") + "/js/collections/"
				+ ctx.get("collectionName") + ".js");
		collectionFile.getParentFile().mkdirs();

		if (collectionFile.exists()) {
			LOG.error("The file " + collectionFile.getPath() + " already exists. Use -f or --force options to overwrite it.");
		} else {
			LOG.info("Generating " + collectionFile.getPath());
			FileUtils.write(collectionFile, writer.toString());
		}
	}
}
