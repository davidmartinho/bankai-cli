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

public class IndexHtmlGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(IndexHtmlGenerator.class);

	public static void generate(String baseFolderName, VelocityContext ctx, boolean force) throws IOException {
		final Template t = Velocity.getTemplate("templates/index.html.vm");

		final Writer writer = new StringWriter();
		t.merge(ctx, writer);

		File indexFile = new File(baseFolderName + "/src/main/webapp/" + ctx.get("artifactId") + "/index.html");
		indexFile.getParentFile().mkdirs();

		if (!force && indexFile.exists()) {
			LOG.error("The file " + indexFile.getPath() + " already exists. Use -f or --force to overwrite it");
		} else {
			LOG.info("Generating " + indexFile.getPath());
			FileUtils.write(indexFile, writer.toString());
		}
	}
}
