package main;

import controllers.MainController;
import services.ClientService;
import services.InventoryService;
import services.SaleService;

/**
 * Entry point for the Store application.
 * <p>
 * This class initializes the necessary services and starts the main controller 
 * to handle application logic.
 * </p>
 */
public class StoreApp {


    /**
     * Main method to launch the application.
     *
     * @param args command-line arguments (not used)
     */
	public static void main(String[] args) {
		// Initialize services
		InventoryService inventoryService = new InventoryService();
		ClientService clientService = new ClientService();
		SaleService saleService = new SaleService(inventoryService);

		// Create and start the main controller
		MainController mainController = new MainController(inventoryService, clientService, saleService);
		mainController.start();
	}

}