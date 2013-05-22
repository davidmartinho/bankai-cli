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
import pt.ist.bankai.domain.MarionetteViewType;

public class ViewGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(ViewGenerator.class);

	public static void generate(VelocityContext ctx, boolean force) throws IOException {
		MarionetteViewType type = (MarionetteViewType) ctx.get("type");
		String templateFilename = null;
		switch (type) {
		case ITEM:
			templateFilename = "item.view.js.vm";
			break;
		case COLLECTION:
			templateFilename = "collection.view.js.vm";
			break;
		case COMPOSITE:
			templateFilename = "composite.view.js.vm";
			break;
		}

		final Template template = Velocity.getTemplate("templates/" + templateFilename);

		final Writer writer = new StringWriter();
		template.merge(ctx, writer);

		File viewFile = new File(BankaiConfig.DEFAULT_WEBAPP_PATH + ctx.get("artifactId") + "/js/views/" + ctx.get("viewName")
				+ ".js");
		viewFile.getParentFile().mkdirs();

		if (!force && viewFile.exists()) {
			LOG.error("The file " + viewFile.getPath() + " already exists. Use -f or --force options to overwrite it.");
		} else {
			LOG.info("Generating " + viewFile.getPath());
			FileUtils.write(viewFile, writer.toString());
		}
	}
}
