package pt.ist.bankai.generator;

import java.io.File;
import java.io.IOException;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APIFolderGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(APIFolderGenerator.class);

	public static void generate(String baseFolderName, VelocityContext ctx, boolean force) throws IOException {

		File apiFolder = new File(baseFolderName + "/src/main/webapp/api/" + ctx.get("artifactId") + "/");
		LOG.info("Generating folders for static API development...");
		apiFolder.mkdirs();

	}
}
