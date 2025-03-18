package views;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import models.Client;
import models.Product;
import models.Sale;

/**
 * Represents the Sale view in the application, handling user interactions
 * through console input.
 */
public class SaleView {

	private final Scanner scanner;

	/**
	 * Constructs a SaleView with the specified Scanner for user input.
	 *
	 * @param scanner the Scanner object used to read input from the console
	 */
	public SaleView(Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * Displays the main sales menu and returns user's choice.
	 * 
	 * @return The selected menu option.
	 */
	public int displayMainMenu() {
		System.out.println("\n=== SALES MANAGEMENT ===");
		System.out.println("1. Register new sale");
		System.out.println("2. Search sale by ID");
		System.out.println("3. List all sales");
		System.out.println("4. Show sales statistics");
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
	 * Displays invalid choice message.
	 */
	public void displayInvalidChoice() {
		System.out.println("Invalid choice. Please try again.");
	}

	/**
	 * Displays message when no clients are registered.
	 */
	public void displayNoClientsWarning() {
		System.out.println("No clients registered in the system. Please register a client first.");
	}

	/**
	 * Displays operation cancelled message.
	 */
	public void displayOperationCancelled() {
		System.out.println("Operation cancelled.");
	}

	/**
	 * Displays client not found message.
	 * 
	 * @param clientId The ID of the client that wasn't found.
	 */
	public void displayClientNotFound(Long clientId) {
		System.out.println("Client with ID " + clientId + " not found.");
	}

	/**
	 * Displays message about registering a sale for a client.
	 * 
	 * @param client The client for whom the sale is being registered.
	 */
	public void displayRegisteringSaleFor(Client client) {
		System.out.println("\n=== REGISTER NEW SALE ===");
		System.out.println("Registering sale for client: " + client.getName());
	}

	/**
	 * Prompts for and gets client ID from user.
	 * 
	 * @return The client ID or null if invalid.
	 */
	public Long getClientId() {
		System.out.print("Enter client ID (0 to cancel): ");
		try {
			return Long.parseLong(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Displays message when no products are available.
	 */
	public void displayNoProductsWarning() {
		System.out.println("No products available in inventory. Sale cancelled.");
	}

	/**
	 * Prompts for and gets product ID from user.
	 * 
	 * @return The product ID or null if invalid.
	 */
	public Long getProductId() {
		System.out.print("Enter product ID (0 to finish): ");
		try {
			return Long.parseLong(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Displays invalid product ID message.
	 */
	public void displayInvalidProductId() {
		System.out.println("Invalid product ID. Please try again.");
	}

	/**
	 * Displays empty sale cancelled message.
	 */
	public void displayEmptySaleCancelled() {
		System.out.println("No products added to sale. Operation cancelled.");
	}

	/**
	 * Displays product not found message.
	 * 
	 * @param productId The ID of the product that wasn't found.
	 */
	public void displayProductNotFound(Long productId) {
		System.out.println("Product with ID " + productId + " not found.");
	}

	/**
	 * Displays product out of stock message.
	 * 
	 * @param product The out-of-stock product.
	 */
	public void displayProductOutOfStock(Product product) {
		System.out.println("Product " + product.getName() + " is out of stock.");
	}

	/**
	 * Prompts for and gets product quantity from user.
	 * 
	 * @param maxStock The maximum available stock.
	 * @return The quantity or null if invalid.
	 */
	public Double getProductQuantity(double maxStock) {
		System.out.print("Enter quantity (max " + maxStock + "): ");
		try {
			return Double.parseDouble(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Displays invalid quantity message.
	 */
	public void displayInvalidQuantity() {
		System.out.println("Invalid quantity. Please enter a positive number.");
	}

	/**
	 * Displays not enough stock message.
	 * 
	 * @param availableStock The available stock quantity.
	 */
	public void displayNotEnoughStock(double availableStock) {
		System.out.println("Not enough stock. Available: " + availableStock);
	}

	/**
	 * Displays information about a product added to the sale.
	 *
	 * @param product      The product added.
	 * @param quantity     The quantity added.
	 * @param productTotal The total price for this product.
	 * @param totalPrice   The current total price of the sale.
	 */
	public void displayProductAdded(Product product, double quantity, double productTotal, double totalPrice) {
		System.out.println(
				"Added " + quantity + " of " + product.getName() + " (" + String.format("%.2f", productTotal) + "€)");
		System.out.println("Current total: " + String.format("%.2f", totalPrice) + "€");
	}

	/**
	 * Asks user if they want to add more products.
	 * 
	 * @return True if user wants to add more products, false otherwise.
	 */
	public boolean confirmAddMoreProducts() {
		System.out.print("Add more products? (Y/N): ");
		String answer = scanner.nextLine().trim().toUpperCase();
		return answer.startsWith("Y");
	}

	/**
	 * Displays sale added to client history message.
	 * 
	 * @param client The client whose history was updated.
	 */
	public void displaySaleAddedToClientHistory(Client client) {
		System.out.println("Sale added to client " + client.getName() + "'s order history.");
	}

	/**
	 * Displays warning about client history update.
	 * 
	 * @param errorMessage The error message.
	 */
	public void displayClientHistoryWarning(String errorMessage) {
		System.out.println("Warning: Could not add sale to client history: " + errorMessage);
		System.out.println("Sale was registered but not associated with the client.");
	}

	/**
	 * Displays successful sale registration message.
	 * 
	 * @param sale The registered sale.
	 */
	public void displaySaleRegisteredSuccess(Sale sale) {
		System.out.println("\nSale registered successfully! Sale ID: " + sale.getId());
		System.out.println("Total amount: " + String.format("%.2f", sale.getTotalPrice()) + "€");
	}

	/**
	 * Displays sale registration error message.
	 * 
	 * @param errorMessage The error message.
	 */
	public void displaySaleRegistrationError(String errorMessage) {
		System.out.println("Error registering sale: " + errorMessage);
	}

	/**
	 * Prompts for and gets sale ID from user.
	 * 
	 * @return The sale ID or null if invalid.
	 */
	public Long getSaleId() {
		System.out.println("\n=== SEARCH SALE BY ID ===");
		System.out.print("Enter sale ID: ");
		try {
			return Long.parseLong(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Displays invalid sale ID message.
	 */
	public void displayInvalidSaleId() {
		System.out.println("Operation cancelled: Invalid sale ID.");
	}

	/**
	 * Displays sale not found message.
	 * 
	 * @param saleId The ID of the sale that wasn't found.
	 */
	public void displaySaleNotFound(Long saleId) {
		System.out.println("No sale found with ID: " + saleId);
	}

	/**
	 * Displays sale search error message.
	 * 
	 * @param errorMessage The error message.
	 */
	public void displaySaleSearchError(String errorMessage) {
		System.out.println("Error searching for sale: " + errorMessage);
	}

	/**
	 * Displays sale owner information.
	 * 
	 * @param owner The client who owns the sale.
	 */
	public void displaySaleOwner(Client owner) {
		System.out.println("\nThis sale belongs to client: " + owner.getName() + " (ID: " + owner.getId() + ")");
	}

	/**
	 * Displays message when no sales are registered.
	 */
	public void displayNoSalesRegistered() {
		System.out.println("\n=== ALL SALES ===");
		System.out.println("No sales registered in the system.");
	}

	/**
	 * Displays list sales error message.
	 * 
	 * @param errorMessage The error message.
	 */
	public void displayListSalesError(String errorMessage) {
		System.out.println("Error listing sales: " + errorMessage);
	}

	/**
	 * Displays message when no sales data is available.
	 */
	public void displayNoSalesDataAvailable() {
		System.out.println("\n=== SALES STATISTICS ===");
		System.out.println("No sales data available.");
	}

	/**
	 * Displays sales statistics.
	 * 
	 * @param totalSales        Total number of sales.
	 * @param totalRevenue      Total revenue from all sales.
	 * @param clientSalesCount  Number of sales associated with clients.
	 * @param topClient         The client with the most sales.
	 * @param maxSales          The number of sales for the top client.
	 * @param productQuantities Map of product IDs to quantities sold.
	 * @param productNames      Map of product IDs to product names.
	 */
	public void displaySalesStatistics(int totalSales, double totalRevenue, int clientSalesCount, Client topClient,
			int maxSales, Map<Long, Double> productQuantities, Map<Long, String> productNames) {
		System.out.println("\n=== SALES STATISTICS ===");
		System.out.println("Total number of sales: " + totalSales);
		System.out.println("Total revenue: " + String.format("%.2f", totalRevenue) + "€");
		System.out.println("Client-associated sales: " + clientSalesCount);
		System.out.println("Anonymous sales: " + (totalSales - clientSalesCount));

		if (topClient != null) {
			System.out.println("Top client: " + topClient.getName() + " with " + maxSales + " purchases");
		}

		// Display top 5 most sold products
		System.out.println("\nTop selling products:");
		System.out.println("--------------------------------------------");
		System.out.printf("%-25s | %-10s\n", "Product", "Quantity Sold");
		System.out.println("--------------------------------------------");

		productQuantities.entrySet().stream().sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue())).limit(5)
				.forEach(entry -> {
					System.out.printf("%-25s | %-10.2f\n", truncateString(productNames.get(entry.getKey()), 25),
							entry.getValue());
				});

		System.out.println("--------------------------------------------");
	}

	/**
	 * Displays a sale's details.
	 * 
	 * @param sale The sale to display.
	 */
	public void displaySale(Sale sale) {
		System.out.println("\nSale Details:");
		System.out.println("ID: " + sale.getId());
		System.out.println("Date: " + sale.getDate());
		System.out.println("Total Price: " + String.format("%.2f", sale.getTotalPrice()) + "€");

		System.out.println("\nProducts Sold:");
		System.out.println("--------------------------------------------------");
		System.out.printf("%-25s | %-10s | %-10s\n", "Product", "Quantity", "Subtotal");
		System.out.println("--------------------------------------------------");

		for (Map.Entry<Product, Double> entry : sale.getProductsSold().entrySet()) {
			Product product = entry.getKey();
			Double quantity = entry.getValue();
			double subtotal = product.getPrice() * quantity;

			System.out.printf("%-25s | %-10.2f | %-9.2f€\n", truncateString(product.getName(), 25), quantity, subtotal);
		}

		System.out.println("--------------------------------------------------");
		System.out.println("Total: " + String.format("%.2f", sale.getTotalPrice()) + "€");
	}

	/**
	 * Displays a list of sales in tabular format.
	 * 
	 * @param sales The list of sales to display.
	 */
	public void displaySaleList(List<Sale> sales) {
		System.out.println("\n=== ALL SALES ===");
		System.out.println("------------------------------------------------------");
		System.out.printf("%-6s | %-12s | %-10s | %-15s\n", "ID", "Date", "Total", "Products");
		System.out.println("------------------------------------------------------");

		for (Sale sale : sales) {
			System.out.printf("%-6d | %-12s | %-9.2f€ | %-15s\n", sale.getId(), sale.getDate(), sale.getTotalPrice(),
					sale.getProductsSold().size() + " products");
		}
		System.out.println("------------------------------------------------------");
		System.out.println("Total sales: " + sales.size());
	}

	/**
	 * Displays a list of products in tabular format.
	 * 
	 * @param products The list of products to display.
	 */
	public void displayProductList(List<Product> products) {
		System.out.println("\nAvailable Products:");
		System.out.println("--------------------------------------------------");
		System.out.printf("%-6s | %-25s | %-10s | %-10s\n", "ID", "Name", "Price", "Stock");
		System.out.println("--------------------------------------------------");

		for (Product product : products) {
			System.out.printf("%-6d | %-25s | %-9.2f€ | %-10.2f\n", product.getId(),
					truncateString(product.getName(), 25), product.getPrice(), product.getStock());
		}
		System.out.println("--------------------------------------------------");
	}

	/**
	 * Displays a list of clients in tabular format.
	 * 
	 * @param clients The list of clients to display.
	 */
	public void displayClientList(List<Client> clients) {
		System.out.println("\nAvailable Clients:");
		System.out.println("--------------------------");
		System.out.printf("%-6s | %-25s\n", "ID", "Name");
		System.out.println("--------------------------");

		for (Client client : clients) {
			System.out.printf("%-6d | %-25s\n", client.getId(), truncateString(client.getName(), 25));
		}
		System.out.println("--------------------------");
	}

	/**
	 * Truncates a string to a specific length and adds "..." if needed.
	 * 
	 * @param str    The string to truncate.
	 * @param length The maximum length.
	 * @return The truncated string.
	 */
	private String truncateString(String str, int length) {
		if (str == null) {
			return "";
		}
		if (str.length() <= length) {
			return str;
		}
		return str.substring(0, length - 3) + "...";
	}
}