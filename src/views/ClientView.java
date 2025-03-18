package views;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import models.Client;
import models.Product;
import models.Sale;

/**
 * Represents the client view in the application, handling user interactions
 * through console input.
 */
public class ClientView {
	private final Scanner scanner;

	/**
	 * Constructs a ClientView with the specified Scanner for user input.
	 *
	 * @param scanner the Scanner object used to read input from the console
	 */
	public ClientView(Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * Displays the client management menu.
	 * 
	 * @return The user's choice.
	 */
	public int showMenu() {
		System.out.println("\n=== CLIENT MANAGEMENT ===");
		System.out.println("1. Register new client");
		System.out.println("2. Search client by ID");
		System.out.println("3. Search clients by name");
		System.out.println("4. List all clients");
		System.out.println("5. View client order history");
		System.out.println("0. Return to main menu");
		System.out.print("Enter your choice: ");

		return getMenuChoice();
	}

	/**
	 * Gets the user's menu choice.
	 * 
	 * @return The selected menu option.
	 */
	public int getMenuChoice() {
		try {
			return Integer.parseInt(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			return -1; // Invalid choice
		}
	}

	/**
	 * Displays an error message.
	 * 
	 * @param message The error message to display.
	 */
	public void showError(String message) {
		System.out.println(message);
	}

	/**
	 * Prompts for and gets client name input.
	 * 
	 * @return The entered client name.
	 */
	public String getClientName() {
		System.out.println("\n=== REGISTER NEW CLIENT ===");
		System.out.print("Enter client name: ");
		return scanner.nextLine().trim();
	}

	/**
	 * Displays a success message after registering a client.
	 * 
	 * @param clientId The ID of the newly registered client.
	 */
	public void showClientRegistrationSuccess(long clientId) {
		System.out.println("Client registered successfully! Client ID: " + clientId);
	}

	/**
	 * Prompts for and gets client ID input for search.
	 * 
	 * @return The entered client ID, or null if invalid.
	 */
	public Long getClientIdForSearch() {
		System.out.println("\n=== SEARCH CLIENT BY ID ===");
		return getLongInput("Enter client ID: ");
	}

	/**
	 * Prompts for and gets client ID input for order history.
	 * 
	 * @return The entered client ID, or null if invalid.
	 */
	public Long getClientIdForOrderHistory() {
		System.out.println("\n=== VIEW CLIENT ORDER HISTORY ===");
		return getLongInput("Enter client ID: ");
	}

	/**
	 * Prompts for and gets client name for search.
	 * 
	 * @return The entered search name.
	 */
	public String getClientNameForSearch() {
		System.out.println("\n=== SEARCH CLIENTS BY NAME ===");
		System.out.print("Enter client name (partial name is acceptable): ");
		return scanner.nextLine().trim();
	}

	/**
	 * Displays the "List All Clients" header.
	 */
	public void showAllClientsHeader() {
		System.out.println("\n=== ALL CLIENTS ===");
	}

	/**
	 * Displays a client's details.
	 * 
	 * @param client The client to display.
	 */
	public void displayClient(Client client) {
		System.out.println("\nClient Details:");
		System.out.println("ID: " + client.getId());
		System.out.println("Name: " + client.getName());
	}

	/**
	 * Displays a list of clients in tabular format.
	 * 
	 * @param clients The list of clients to display.
	 */
	public void displayClientList(List<Client> clients) {
		System.out.println("\n--------------------------");
		System.out.printf("%-6s | %-25s\n", "ID", "Name");
		System.out.println("--------------------------");

		for (Client client : clients) {
			System.out.printf("%-6d | %-25s\n", client.getId(), truncateString(client.getName(), 25));
		}
		System.out.println("--------------------------");
		System.out.println("Total clients: " + clients.size());
	}

	/**
	 * Displays a message when no clients are found.
	 * 
	 * @param searchName The search term used.
	 */
	public void showNoClientsFound(String searchName) {
		System.out.println("No clients found matching: " + searchName);
	}

	/**
	 * Displays a header for clients matching a search term.
	 * 
	 * @param searchName The search term used.
	 */
	public void showClientsMatchingHeader(String searchName) {
		System.out.println("\nClients matching \"" + searchName + "\":");
	}

	/**
	 * Displays a message when no clients are registered.
	 */
	public void showNoClientsRegistered() {
		System.out.println("No clients registered in the system.");
	}

	/**
	 * Displays a message when a client is not found.
	 * 
	 * @param clientId The client ID that was searched for.
	 */
	public void showClientNotFound(Long clientId) {
		System.out.println("No client found with ID: " + clientId);
	}

	/**
	 * Displays a header for a client's order history.
	 * 
	 * @param clientName The name of the client.
	 */
	public void showOrderHistoryHeader(String clientName) {
		System.out.println("\nOrder History for " + clientName + ":");
	}

	/**
	 * Displays a message when a client has no order history.
	 * 
	 * @param clientName The name of the client.
	 */
	public void showNoOrderHistory(String clientName) {
		System.out.println("Client " + clientName + " has no order history.");
	}

	/**
	 * Displays a client's order history in tabular format.
	 * 
	 * @param orderHistory The list of sales to display.
	 */
	public void displayOrderHistory(List<Sale> orderHistory) {
		System.out.println("\n----------------------------------------------------------------------");
		System.out.printf("%-6s | %-12s | %-8s | %-25s\n", "Sale ID", "Date", "Total", "Items");
		System.out.println("----------------------------------------------------------------------");

		for (Sale sale : orderHistory) {
			System.out.printf("%-6d | %-12s | %-7.2f€ | ", sale.getId(), sale.getDate(), sale.getTotalPrice());

			// Format the products sold
			Map<Product, Double> productsSold = sale.getProductsSold();
			if (productsSold.isEmpty()) {
				System.out.println("No items");
			} else {
				boolean first = true;
				for (Map.Entry<Product, Double> entry : productsSold.entrySet()) {
					Product product = entry.getKey();
					Double quantity = entry.getValue();

					if (!first) {
						System.out.print(", ");
					}
					System.out.printf("%s (%.1f)", product.getName(), quantity);
					first = false;
				}
				System.out.println();
			}
		}
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Total orders: " + orderHistory.size());
	}

	/**
	 * Gets a long value from user input.
	 * 
	 * @param prompt The prompt to display to the user.
	 * @return The long value or null if invalid.
	 */
	private Long getLongInput(String prompt) {
		System.out.print(prompt);
		try {
			return Long.parseLong(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("Invalid number format.");
			return null;
		}
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