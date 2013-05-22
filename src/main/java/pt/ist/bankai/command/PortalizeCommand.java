package pt.ist.bankai.command;

import org.apache.velocity.VelocityContext;

import pt.ist.bankai.Console;
import pt.ist.bankai.generator.AppsJsonGenerator;
import pt.ist.bankai.generator.PortalizedIndexHtmlGenerator;
import pt.ist.bankai.generator.PortalizedPomGenerator;

import com.beust.jcommander.Parameter;

public class PortalizeCommand extends Command {

	public static final String NAME = "portalize";

	@Parameter(names = { "-ae", "--accessExpression" })
	private String accessExpression = "anyone";

	@Parameter(names = { "-f", "--force" })
	private boolean force;

	@Override
	protected void execute(VelocityContext ctx) throws Exception {
		ctx.put("accessExpression", accessExpression);
		AppsJsonGenerator.generate(ctx, force);
		PortalizedIndexHtmlGenerator.generate(ctx, force);
		PortalizedPomGenerator.generate(ctx, force);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected boolean requiresMavenProject() {
		return true;
	}

	public static void register() {
		Console.registerCommand(new PortalizeCommand());
	}

}
