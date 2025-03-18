package models;

/**
 * Enumeration representing different product categories.
 */
public enum Category {
	FRUIT("Fruit"), VEGETABLE("Vegetable"), OTHER("Other");

	private final String name;

	/**
	 * Constructs a Category enum with a specified name.
	 *
	 * @param name The display name of the category.
	 */
	Category(String name) {
		this.name = name;
	}

	/**
	 * Gets the display name of the category.
	 *
	 * @return The category name as a string.
	 */
	public String getCategory() {
		return this.name;
	}

}