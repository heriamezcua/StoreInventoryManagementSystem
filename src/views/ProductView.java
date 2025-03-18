package views;

import java.util.List;
import java.util.Scanner;

import models.Category;
import models.Product;

/**
 * Represents the product view in the application, handling user interactions
 * through console input.
 */
public class ProductView {

	private final Scanner scanner;

	/**
	 * Constructs a ProductView with the specified Scanner for user input.
	 *
	 * @param scanner the Scanner object used to read input from the console
	 */
	public ProductView(Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * Displays the main product management menu.
	 * 
	 * @return The selected menu option.
	 */
	public int displayMainMenu() {
		System.out.println("\n=== PRODUCT MANAGEMENT ===");
		System.out.println("1. Add new product");
		System.out.println("2. Update product stock");
		System.out.println("3. Search product by ID");
		System.out.println("4. List products by category");
		System.out.println("5. List all products");
		System.out.println("0. Return to main menu");
		System.out.print("Enter your choice: ");

		return getMenuChoice();
	}

	/**
	 * Gets the user's menu choice.
	 * 
	 * @return The selected menu option.
	 */
	private int getMenuChoice() {
		try {
			return Integer.parseInt(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			return -1; // Invalid choice
		}
	}

	/**
	 * Displays a section header.
	 * 
	 * @param title The header title.
	 */
	public void displayHeader(String title) {
		System.out.println("\n=== " + title + " ===");
	}

	/**
	 * Displays a message to the user.
	 * 
	 * @param message The message to display.
	 */
	public void displayMessage(String message) {
		System.out.println(message);
	}

	/**
	 * Gets a string value from user input.
	 * 
	 * @param prompt The prompt to display to the user.
	 * @return The string input.
	 */
	public String getStringInput(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine().trim();
	}

	/**
	 * Gets a double value from user input.
	 * 
	 * @param prompt The prompt to display to the user.
	 * @return The double value or -1 if invalid.
	 */
	public double getDoubleInput(String prompt) {
		System.out.print(prompt);
		try {
			return Double.parseDouble(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("Invalid number format.");
			return -1;
		}
	}

	/**
	 * Gets a long value from user input.
	 * 
	 * @param prompt The prompt to display to the user.
	 * @return The long value or null if invalid.
	 */
	public Long getLongInput(String prompt) {
		System.out.print(prompt);
		try {
			return Long.parseLong(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("Invalid number format.");
			return null;
		}
	}

	/**
	 * Prompts the user to select a product category.
	 * 
	 * @return The selected Category or null if invalid.
	 */
	public Category selectCategory() {
		System.out.println("\nAvailable Categories:");
		Category[] categories = Category.values();

		for (int i = 0; i < categories.length; i++) {
			System.out.println((i + 1) + ". " + categories[i]);
		}

		System.out.print("Select category (1-" + categories.length + "): ");
		try {
			int selection = Integer.parseInt(scanner.nextLine().trim());
			if (selection >= 1 && selection <= categories.length) {
				return categories[selection - 1];
			}
		} catch (NumberFormatException e) {
			// Invalid input handling is below
		}

		return null;
	}

	/**
	 * Displays a product's details.
	 * 
	 * @param product The product to display.
	 */
	public void displayProduct(Product product) {
		System.out.println("\nProduct Details:");
		System.out.println("ID: " + product.getId());
		System.out.println("Name: " + product.getName());
		System.out.println("Price: " + product.getPrice() + "€");
		System.out.println("Stock: " + product.getStock());
		System.out.println("Category: " + product.getCategory());
	}

	/**
	 * Displays a list of products in tabular format.
	 * 
	 * @param products The list of products to display.
	 */
	public void displayProductList(List<Product> products) {
		System.out.println("\n------------------------------------------------------------");
		System.out.printf("%-6s | %-20s | %-10s | %-10s | %-15s\n", "ID", "Name", "Price", "Stock", "Category");
		System.out.println("------------------------------------------------------------");

		for (Product product : products) {
			System.out.printf("%-6d | %-20s | %-10.2f | %-10.2f | %-15s\n", product.getId(), product.getName(),
					product.getPrice(), product.getStock(), product.getCategory());
		}
		System.out.println("------------------------------------------------------------");
		System.out.println("Total products: " + products.size());
	}
}