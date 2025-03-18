package controllers;

import java.util.Scanner;

import services.ClientService;
import services.InventoryService;
import services.SaleService;
import views.ClientView;
import views.ProductView;
import views.ReportView;
import views.SaleView;

/**
 * Main controller that coordinates the different controllers of the
 * application.
 */
public class MainController {

	private final Scanner scanner;
	private final ProductController productController;
	private final ClientController clientController;
	private final SaleController saleController;
	private final ReportController reportController;
	private final FileController fileController;
	private boolean running;

	/**
	 * Constructs a MainController and initializes all necessary controllers.
	 *
	 * @param inventoryService the service responsible for managing product
	 *                         inventory
	 * @param clientService    the service responsible for managing client data
	 * @param saleService      the service responsible for handling sales
	 *                         transactions
	 */
	public MainController(InventoryService inventoryService, ClientService clientService, SaleService saleService) {
		this.scanner = new Scanner(System.in);
		ProductView productView = new ProductView(scanner);
		this.productController = new ProductController(productView, inventoryService);

		ClientView clientView = new ClientView(scanner);
		this.clientController = new ClientController(clientView, clientService);

		SaleView saleView = new SaleView(scanner);
		this.saleController = new SaleController(saleView, saleService, inventoryService, clientService);

		ReportView reportView = new ReportView(scanner);
		this.reportController = new ReportController(reportView, inventoryService, clientService, saleService);

		this.fileController = new FileController(inventoryService, clientService, saleService);
		this.running = false;
	}

	/**
	 * Starts the application and manages the main loop.
	 */
	public void start() {
		running = true;

		// Try to load any saved data when starting
		try {
			fileController.loadAllData();
			System.out.println("Data loaded successfully.");
		} catch (Exception e) {
			System.out.println("Warning: Could not load data. " + e.getMessage());
		}

		System.out.println("=== Welcome to Inventory Management System ===");

		while (running) {
			showMainMenu();
			int choice = getMenuChoice();
			processMenuChoice(choice);
		}

		// Close resources
		scanner.close();
		System.out.println("Thank you for using Inventory Management System. Goodbye!");
	}

	/**
	 * Displays the main menu options.
	 */
	private void showMainMenu() {
		System.out.println("\n=== MAIN MENU ===");
		System.out.println("1. Product Management");
		System.out.println("2. Client Management");
		System.out.println("3. Sales Management");
		System.out.println("4. Reports");
		System.out.println("5. Save/Load Data");
		System.out.println("0. Exit");
		System.out.print("Enter your choice: ");
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
	 * Processes the user's menu choice.
	 * 
	 * @param choice The selected menu option.
	 */
	private void processMenuChoice(int choice) {
		switch (choice) {
		case 1:
			productController.showMenu();
			break;
		case 2:
			// Updated to use the new processMenu method instead of showMenu
			clientController.processMenu();
			break;
		case 3:
			saleController.showMenu();
			break;
		case 4:
			reportController.showMenu();
			break;
		case 5:
			handleFileOperations();
			break;
		case 0:
			exitApplication();
			break;
		default:
			System.out.println("Invalid choice. Please try again.");
			break;
		}
	}

	/**
	 * Handles file operations menu.
	 */
	private void handleFileOperations() {
		System.out.println("\n=== FILE OPERATIONS ===");
		System.out.println("1. Save all data");
		System.out.println("2. Load all data");
		System.out.println("0. Return to main menu");
		System.out.print("Enter your choice: ");

		int choice = getMenuChoice();

		switch (choice) {
		case 1:
			try {
				fileController.saveAllData();
				System.out.println("Data saved successfully.");
			} catch (Exception e) {
				System.out.println("Error saving data: " + e.getMessage());
			}
			break;
		case 2:
			try {
				fileController.loadAllData();
				System.out.println("Data loaded successfully.");
			} catch (Exception e) {
				System.out.println("Error loading data: " + e.getMessage());
			}
			break;
		case 0:
			// Return to main menu
			break;
		default:
			System.out.println("Invalid choice.");
			break;
		}
	}

	/**
	 * Exits the application.
	 */
	private void exitApplication() {
		System.out.println("\nDo you want to save data before exiting? (y/n)");
		String response = scanner.nextLine().trim().toLowerCase();

		if (response.equals("y") || response.equals("yes")) {
			try {
				fileController.saveAllData();
				System.out.println("Data saved successfully.");
			} catch (Exception e) {
				System.out.println("Error saving data: " + e.getMessage());
			}
		}

		running = false;
	}
}