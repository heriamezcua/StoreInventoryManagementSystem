package views;

import models.Client;
import models.Product;
import models.Sale;
import controllers.ReportController.ClientSummary;
import services.ClientService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * View class for generating and displaying reports.
 */
public class ReportView {
	private final Scanner scanner;

	/**
	 * Constructor for ReportView.
	 * 
	 * @param scanner Scanner for user input
	 */
	public ReportView(Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * Displays the main reports menu.
	 * 
	 * @return The selected menu option.
	 */
	public int displayMainMenu() {
		System.out.println("\n=== REPORTS MANAGEMENT ===");
		System.out.println("1. Sales Report");
		System.out.println("2. Inventory Report");
		System.out.println("3. Client Report");
		System.out.println("4. Product Performance Report");
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
	 * Gets a LocalDate from user input.
	 * 
	 * @param prompt The prompt to display to the user.
	 * @return The LocalDate or null if invalid.
	 */
	public LocalDate getDateInput(String prompt) {
		System.out.print(prompt);
		try {
			String dateString = scanner.nextLine().trim();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return LocalDate.parse(dateString, formatter);
		} catch (Exception e) {
			System.out.println("Invalid date format. Please use YYYY-MM-DD.");
			return null;
		}
	}

	/**
	 * Waits for the user to press Enter to continue.
	 */
	public void waitForInput() {
		System.out.println("\nPress Enter to continue...");
		scanner.nextLine();
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

	/**
	 * Displays a sales report.
	 * 
	 * @param sales          List of sales for the report
	 * @param startDate      Start date of the report period
	 * @param endDate        End date of the report period
	 * @param totalRevenue   Total revenue for the period
	 * @param productSummary Map of product names to quantity and revenue arrays
	 * @param clientService  Client service to look up client names
	 */
	public void displaySalesReport(List<Sale> sales, LocalDate startDate, LocalDate endDate, double totalRevenue,
			Map<String, Double[]> productSummary, ClientService clientService) {
		System.out.println("\n=== SALES REPORT ===");
		System.out.println("Date Range: " + startDate + " to " + endDate);
		System.out.println("------------------------------");
		System.out.println("Sale ID | Date       | Total    | Client");
		System.out.println("------------------------------");

		for (Sale sale : sales) {
			Client client = clientService.searchClientById(sale.getClientId());
			String clientName = (client != null) ? client.getName() : "Anonymous";

			System.out.printf("%-7d | %-10s | %-8.2f€ | %s\n", sale.getId(), sale.getDate(), sale.getTotalPrice(),
					clientName);
		}

		System.out.println("------------------------------");
		System.out.printf("Total Sales: %d\n", sales.size());
		System.out.printf("Total Revenue: %.2f€\n", totalRevenue);

		System.out.println("\n=== PRODUCTS SOLD ===");
		System.out.println("Product              | Quantity | Total Revenue");
		System.out.println("------------------------------");

		for (Map.Entry<String, Double[]> entry : productSummary.entrySet()) {
			String productName = entry.getKey();
			Double quantity = entry.getValue()[0];
			Double productRevenue = entry.getValue()[1];

			System.out.printf("%-20s | %-8.1f | %-8.2f€\n", truncateString(productName, 20), quantity, productRevenue);
		}
	}

	/**
	 * Displays an inventory report.
	 * 
	 * @param products List of products in inventory
	 */
	public void displayInventoryReport(List<Product> products) {
		System.out.println("Product ID | Name                | Price    | Stock");
		System.out.println("-----------------------------------------------------");

		for (Product product : products) {
			System.out.printf("%-10d | %-20s | %-8.2f€ | %.1f\n", product.getId(),
					truncateString(product.getName(), 20), product.getPrice(), product.getStock());
		}

		System.out.println("-----------------------------------------------------");
		System.out.printf("Total Products: %d\n", products.size());
	}

	/**
	 * Displays a client report.
	 * 
	 * @param clients         List of clients
	 * @param clientSummaries Map of client IDs to their summary information
	 */
	public void displayClientReport(List<Client> clients, Map<Long, ClientSummary> clientSummaries) {
		System.out.println("Client ID | Name                | Orders | Total Spent");
		System.out.println("-----------------------------------------------------");

		for (Client client : clients) {
			ClientSummary summary = clientSummaries.get(client.getId());

			System.out.printf("%-9d | %-20s | %-6d | %-8.2f€\n", client.getId(), truncateString(summary.getName(), 20),
					summary.getOrderCount(), summary.getTotalSpent());
		}

		System.out.println("-----------------------------------------------------");
		System.out.printf("Total Clients: %d\n", clients.size());
	}

	/**
	 * Displays a product performance report.
	 * 
	 * @param productRevenueList Sorted list of products by revenue
	 * @param productQuantities  Map of products to quantities sold
	 * @param totalRevenue       Total revenue across all products
	 */
	public void displayProductPerformanceReport(List<Map.Entry<Product, Double>> productRevenueList,
			Map<Product, Double> productQuantities, double totalRevenue) {
		System.out.println("Product              | Quantity | Revenue  | % of Total Revenue");
		System.out.println("-----------------------------------------------------");

		for (Map.Entry<Product, Double> entry : productRevenueList) {
			Product product = entry.getKey();
			Double revenue = entry.getValue();
			Double quantity = productQuantities.get(product);
			double percentOfTotal = (revenue / totalRevenue) * 100;

			System.out.printf("%-20s | %-8.1f | %-8.2f€ | %.1f%%\n", truncateString(product.getName(), 20), quantity,
					revenue, percentOfTotal);
		}

		System.out.println("-----------------------------------------------------");
		System.out.printf("Total Revenue: %.2f€\n", totalRevenue);
	}
}