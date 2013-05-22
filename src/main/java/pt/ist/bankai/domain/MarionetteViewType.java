package pt.ist.bankai.domain;

public enum MarionetteViewType {

	COLLECTION("CollectionView"), ITEM("ItemView"), COMPOSITE("CompositeView");

	private String name;

	private MarionetteViewType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static MarionetteViewType fromString(String value) {
		if (value.toLowerCase().equals(COLLECTION.name().toLowerCase())) {
			return COLLECTION;
		} else if (value.toLowerCase().equals(ITEM.name().toLowerCase())) {
			return ITEM;
		} else if (value.toLowerCase().equals(COMPOSITE.name().toLowerCase())) {
			return COMPOSITE;
		} else {
			return null;
		}
	}
}
