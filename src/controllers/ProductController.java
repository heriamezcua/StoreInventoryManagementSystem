package controllers;

import java.util.List;

import models.Category;
import models.Product;
import services.InventoryService;
import views.ProductView;

/**
 * Controller responsible for managing product-related operations.
 */
public class ProductController {

	private final ProductView view;
	private final InventoryService inventoryService;

	/**
	 * Constructs a ProductController with the specified view and inventory service.
	 *
	 * @param view             the view responsible for displaying product-related
	 *                         information
	 * @param inventoryService the service handling inventory management operations
	 */
	public ProductController(ProductView view, InventoryService inventoryService) {
		this.view = view;
		this.inventoryService = inventoryService;
	}

	/**
	 * Displays the product management menu and handles user input.
	 */
	public void showMenu() {
		boolean returnToMain = false;

		while (!returnToMain) {
			int choice = view.displayMainMenu();

			switch (choice) {
			case 1:
				addProduct();
				break;
			case 2:
				updateProductStock();
				break;
			case 3:
				searchProductById();
				break;
			case 4:
				listProductsByCategory();
				break;
			case 5:
				listAllProducts();
				break;
			case 0:
				returnToMain = true;
				break;
			default:
				view.displayMessage("Invalid choice. Please try again.");
				break;
			}
		}
	}

	/**
	 * Handles adding a new product to the inventory.
	 */
	private void addProduct() {
		view.displayHeader("ADD NEW PRODUCT");

		String name = view.getStringInput("Enter product name: ");

		double price = view.getDoubleInput("Enter product price: ");
		if (price < 0) {
			view.displayMessage("Operation cancelled: Price cannot be negative.");
			return;
		}

		double stock = view.getDoubleInput("Enter initial stock (kg or units): ");
		if (stock < 0) {
			view.displayMessage("Operation cancelled: Stock cannot be negative.");
			return;
		}

		Category category = view.selectCategory();
		if (category == null) {
			view.displayMessage("Operation cancelled: Invalid category selection.");
			return;
		}

		try {
			Product newProduct = new Product(name, price, stock, category);
			inventoryService.addProduct(newProduct);
			view.displayMessage("Product added successfully! Product ID: " + newProduct.getId());
		} catch (IllegalArgumentException e) {
			view.displayMessage("Error adding product: " + e.getMessage());
		}
	}

	/**
	 * Handles updating a product's stock.
	 */
	private void updateProductStock() {
		view.displayHeader("UPDATE PRODUCT STOCK");

		Long productId = view.getLongInput("Enter product ID: ");
		if (productId == null) {
			view.displayMessage("Operation cancelled: Invalid product ID.");
			return;
		}

		Product product = inventoryService.searchProductById(productId);
		if (product == null) {
			view.displayMessage("Product not found!");
			return;
		}

		view.displayMessage("Current stock for '" + product.getName() + "': " + product.getStock());
		double newStock = view.getDoubleInput("Enter new stock value: ");

		try {
			inventoryService.updateStock(productId, newStock);
			view.displayMessage("Stock updated successfully!");
		} catch (IllegalArgumentException e) {
			view.displayMessage("Error updating stock: " + e.getMessage());
		}
	}

	/**
	 * Handles searching for a product by ID.
	 */
	private void searchProductById() {
		view.displayHeader("SEARCH PRODUCT BY ID");

		Long productId = view.getLongInput("Enter product ID: ");
		if (productId == null) {
			view.displayMessage("Operation cancelled: Invalid product ID.");
			return;
		}

		try {
			Product product = inventoryService.searchProductById(productId);
			if (product != null) {
				view.displayProduct(product);
			} else {
				view.displayMessage("No product found with ID: " + productId);
			}
		} catch (Exception e) {
			view.displayMessage("Error searching for product: " + e.getMessage());
		}
	}

	/**
	 * Handles listing products by category.
	 */
	private void listProductsByCategory() {
		view.displayHeader("LIST PRODUCTS BY CATEGORY");

		Category category = view.selectCategory();
		if (category == null) {
			view.displayMessage("Operation cancelled: Invalid category selection.");
			return;
		}

		try {
			List<Product> products = inventoryService.searchProductsByCategory(category);
			if (products.isEmpty()) {
				view.displayMessage("No products found in category: " + category);
			} else {
				view.displayMessage("Products in category: " + category);
				view.displayProductList(products);
			}
		} catch (Exception e) {
			view.displayMessage("Error listing products: " + e.getMessage());
		}
	}

	/**
	 * Handles listing all products in the inventory.
	 */
	private void listAllProducts() {
		view.displayHeader("ALL PRODUCTS");

		try {
			List<Product> products = inventoryService.listAllProducts();
			if (products.isEmpty()) {
				view.displayMessage("No products in inventory.");
			} else {
				view.displayProductList(products);
			}
		} catch (Exception e) {
			view.displayMessage("Error listing products: " + e.getMessage());
		}
	}
}