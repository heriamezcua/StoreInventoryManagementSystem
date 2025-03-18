package models;

import java.io.Serializable;

/**
 * Represents a product with a unique ID, name, price, stock, and category.
 * Implements Serializable to allow object serialization.
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private static long counter = 1;
	private final long id;
	private String name;
	private double price;
	private double stock; // kg or units
	private Category category;

	/**
	 * Constructs a new Product with the given attributes.
	 *
	 * @param name     The name of the product (cannot be null or empty).
	 * @param price    The price of the product (must be non-negative).
	 * @param stock    The available stock (must be non-negative).
	 * @param category The category of the product (cannot be null).
	 * @throws IllegalArgumentException if any parameter is invalid.
	 */
	public Product(String name, double price, double stock, Category category) {

		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Product name cannot be null or empty.");
		}
		if (price < 0) {
			throw new IllegalArgumentException("Price cannot be negative.");
		}
		if (stock < 0) {
			throw new IllegalArgumentException("Stock cannot be negative.");
		}
		if (category == null) {
			throw new IllegalArgumentException("Category cannot be null.");
		}

		this.id = counter++; // Unique random ID
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.category = category;
	}

	/**
	 * Gets the unique ID of the product.
	 *
	 * @return The product ID.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the name of the product.
	 *
	 * @return The product name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the price of the product.
	 *
	 * @return The product price.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Gets the available stock of the product.
	 *
	 * @return The stock amount in kg or units.
	 */
	public double getStock() {
		return stock;
	}

	/**
	 * Gets the category of the product.
	 *
	 * @return The product category.
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Sets the stock of the product.
	 *
	 * @param stock The new stock amount (must be non-negative).
	 */
	public void setStock(double stock) {
		this.stock = stock;
	}

}
