package pt.ist.bankai.domain;

public class RequireDependency {

	private String path;
	private String varName;

	public RequireDependency(String path, String varName) {
		this.path = path;
		this.varName = varName;
	}

	public String getPath() {
		return path;
	}

	public String getVarName() {
		return varName;
	}

}
