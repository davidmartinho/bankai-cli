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

public class MessagesGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(MessagesGenerator.class);

	public static void generate(String baseFolderName, VelocityContext ctx, boolean force) throws IOException {

		final Template tEn = Velocity.getTemplate("templates/messages.en.js.vm");
		final Template tPt = Velocity.getTemplate("templates/messages.pt.js.vm");

		final Writer enWriter = new StringWriter();
		tEn.merge(ctx, enWriter);

		final Writer ptWriter = new StringWriter();
		tPt.merge(ctx, ptWriter);

		File enFile = new File(baseFolderName + "/src/main/webapp/" + ctx.get("artifactId") + "/js/nls/messages.js");
		File ptFile = new File(baseFolderName + "/src/main/webapp/" + ctx.get("artifactId") + "/js/nls/pt-pt/messages.js");
		enFile.getParentFile().mkdirs();
		ptFile.getParentFile().mkdirs();

		if (!force && enFile.exists()) {
			LOG.error("The file " + enFile.getPath() + " already exists. Use -f or --force options to overwrite it.");
		} else {
			LOG.info("Generating " + enFile.getPath());
			FileUtils.write(enFile, enWriter.toString(), "UTF-8");
		}

		if (!force && ptFile.exists()) {
			LOG.error("The file " + ptFile.getPath() + " already exists. Use -f or --force options to overwrite it.");
		} else {
			LOG.info("Generating " + ptFile.getPath());
			FileUtils.write(ptFile, ptWriter.toString(), "UTF-8");
		}

	}
}
