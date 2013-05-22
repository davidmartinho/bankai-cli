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

public class ItemViewTemplateGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(ItemViewTemplateGenerator.class);

	public static void generate(VelocityContext ctx, boolean force) throws IOException {

		final Template t = Velocity.getTemplate("templates/item.view.template.html.vm");

		final Writer writer = new StringWriter();
		t.merge(ctx, writer);

		File viewTemplateFile = new File(BankaiConfig.DEFAULT_WEBAPP_PATH + ctx.get("artifactId") + "/templates/"
				+ ctx.get("viewName") + ".html");
		viewTemplateFile.getParentFile().mkdirs();

		if (!force && viewTemplateFile.exists()) {
			LOG.error("The file " + viewTemplateFile.getPath() + " already exists. User -f or --force to overwrite it");
		} else {
			LOG.info("Generating ItemView template " + viewTemplateFile.getPath());
			FileUtils.write(viewTemplateFile, writer.toString());
		}

	}

}
