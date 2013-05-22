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

public class PortalizedIndexHtmlGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(PortalizedIndexHtmlGenerator.class);

	public static void generate(VelocityContext ctx, boolean force) throws IOException {

		final Template t = Velocity.getTemplate("templates/portalized.index.html.vm");

		final Writer writer = new StringWriter();
		t.merge(ctx, writer);

		File portalizedIndexHtmlFile = new File(BankaiConfig.DEFAULT_WEBAPP_PATH + ctx.get("artifactId") + "/index.html");
		portalizedIndexHtmlFile.getParentFile().mkdirs();

		if (!force && portalizedIndexHtmlFile.exists()) {
			LOG.error("The file " + portalizedIndexHtmlFile.getPath() + " already exists. Use -f or --force to overwrite it");
		} else {
			LOG.info("Generating " + portalizedIndexHtmlFile.getPath());
			FileUtils.write(portalizedIndexHtmlFile, writer.toString());
		}

	}

}
