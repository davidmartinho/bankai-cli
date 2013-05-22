package pt.ist.bankai.velocity;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VelocityConfigurator {

	public static void config() {
		Velocity.setProperty("input.encoding", "UTF-8");
		Velocity.setProperty("output.encoding", "UTF-8");
		Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		Velocity.init();
	}

}
