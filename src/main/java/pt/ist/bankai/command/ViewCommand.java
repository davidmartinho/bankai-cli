package pt.ist.bankai.command;

import java.io.IOException;

import org.apache.velocity.VelocityContext;

import pt.ist.bankai.Console;
import pt.ist.bankai.command.converter.MarionetteViewTypeConverter;
import pt.ist.bankai.domain.MarionetteViewType;
import pt.ist.bankai.generator.ViewGenerator;
import pt.ist.bankai.generator.ItemViewTemplateGenerator;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Manages Bankai views")
public class ViewCommand extends Command {

	public static final String NAME = "view";

	@Parameter(names = { "-n", "-name" }, description = "View name", required = true)
	private String viewName;

	@Parameter(names = { "-t", "-type" }, converter = MarionetteViewTypeConverter.class, description = "Type of the View, defaults to ItemView")
	private MarionetteViewType type = MarionetteViewType.ITEM;

	@Parameter(names = "-itemView", description = "ItemView of the CollectionView")
	private String itemView;

	@Parameter(names = "-emptyView", description = "The view to be displayed if the collections is empty")
	private String emptyView;

	@Parameter(names = "-itemViewContainer", description = "The container to be displayed the itemViews")
	private String itemViewContainer;

	@Parameter(names = "-tag", description = "Optional tag name")
	private String tagName;

	@Parameter(names = { "-f", "--force" })
	private boolean force = false;

	@Override
	protected void execute(VelocityContext ctx) throws IOException {
		ctx.put("viewName", viewName);
		ctx.put("type", type);
		switch (type) {
		case ITEM:
			ctx.put("tagName", tagName);
			ItemViewTemplateGenerator.generate(ctx, force);
			break;
		case COLLECTION:
			ctx.put("itemView", itemView);
			ctx.put("emptyView", emptyView);
			break;
		case COMPOSITE:
			ctx.put("itemView", itemView);
			ctx.put("itemViewContainer", itemViewContainer);
			break;
		}
		ViewGenerator.generate(ctx, force);
	}

	@Override
	public String getName() {
		return NAME;
	}

	public static void register() {
		Console.registerCommand(new ViewCommand());
	}

	@Override
	protected boolean requiresMavenProject() {
		return true;
	}

}
