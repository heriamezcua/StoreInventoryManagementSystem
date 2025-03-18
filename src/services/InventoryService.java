package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Category;
import models.Product;

/**
 * Service class for managing the inventory of products. Provides methods to
 * add, search, update, and list products.
 */
public class InventoryService {

	private final Map<Long, Product> inventory;

	/**
	 * Constructs an InventoryService with an empty inventory.
	 */
	public InventoryService() {
		this.inventory = new HashMap<>();
	}

	/**
	 * Adds a product to the inventory. If the product already exists, its stock is
	 * updated.
	 *
	 * @param product The product to add (cannot be null).
	 * @throws IllegalArgumentException if the product is null.
	 */
	public void addProduct(Product product) {
		if (product == null) {
			throw new IllegalArgumentException("Product cannot be null.");
		}
		inventory.put(product.getId(), product);
	}

	/**
	 * Searches for a product by ID.
	 *
	 * @param id The ID of the product (cannot be null).
	 * @return The product if found, otherwise null.
	 * @throws IllegalArgumentException if the ID is null.
	 */
	public Product searchProductById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Product ID cannot be null.");
		}
		return inventory.get(id);
	}

	/**
	 * Searches for products by category.
	 *
	 * @param category The category to filter by (cannot be null).
	 * @return A list of products in the specified category.
	 * @throws IllegalArgumentException if the category is null.
	 */
	public List<Product> searchProductsByCategory(Category category) {
		if (category == null) {
			throw new IllegalArgumentException("Category cannot be null.");
		}
		List<Product> products = new ArrayList<>();
		for (Product product : inventory.values()) {
			if (product.getCategory() == category) {
				products.add(product);
			}
		}
		return products;
	}

	/**
	 * Updates the stock of a given product.
	 *
	 * @param id       The ID of the product.
	 * @param newStock The new stock value (must be non-negative).
	 * @throws IllegalArgumentException if the product is not found or the new stock
	 *                                  is negative.
	 */
	public void updateStock(Long id, double newStock) {
		Product product = searchProductById(id);
		if (product == null) {
			throw new IllegalArgumentException("Product not found.");
		}
		if (newStock < 0) {
			throw new IllegalArgumentException("Stock cannot be negative.");
		}
		product.setStock(newStock);
	}

	/**
	 * Lists all products in the inventory.
	 *
	 * @return A list of all products.
	 */
	public List<Product> listAllProducts() {
		return new ArrayList<>(inventory.values());
	}

	/**
	 * Returns all items in the inventory.
	 *
	 * @return A list of all products.
	 */
	public List<Product> getAllItems() {
		return new ArrayList<>(inventory.values());
	}

	/**
	 * Loads a list of products into the inventory, replacing existing data.
	 *
	 * @param items List of products to load (cannot be null).
	 * @throws IllegalArgumentException if the item list is null.
	 */
	public void loadAllItems(List<Product> items) {
		if (items == null) {
			throw new IllegalArgumentException("Item list cannot be null.");
		}
		inventory.clear();
		for (Product product : items) {
			inventory.put(product.getId(), product);
		}
	}
}
