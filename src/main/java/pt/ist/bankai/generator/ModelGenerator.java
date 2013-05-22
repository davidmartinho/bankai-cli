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

public class ModelGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(ModelGenerator.class);

	public static void generate(VelocityContext ctx, boolean force) throws IOException {
		final Template t = Velocity.getTemplate("templates/model.js.vm");

		final Writer writer = new StringWriter();
		t.merge(ctx, writer);

		File modelFile = new File(BankaiConfig.DEFAULT_WEBAPP_PATH + ctx.get("artifactId") + "/js/models/" + ctx.get("modelName")
				+ ".js");
		modelFile.getParentFile().mkdirs();

		if (!force && modelFile.exists()) {
			LOG.info("The file " + modelFile.getPath() + " already exists. Use -f or --force options to overwrite it");

		} else {

			LOG.info("Generating " + modelFile.getPath());
			FileUtils.write(modelFile, writer.toString());
		}
	}
}
