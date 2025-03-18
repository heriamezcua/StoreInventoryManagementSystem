package controllers;

import java.util.List;

import models.Client;
import models.Product;
import models.Sale;
import services.ClientService;
import services.InventoryService;
import services.SaleService;
import utils.FileManager;

/**
 * Controller responsible for handling file operations related to inventory, clients, and sales.
 * This class allows saving and loading data from files to ensure data persistence.
 */
public class FileController {
    private final InventoryService inventoryService;
    private final ClientService clientService;
    private final SaleService saleService;

    private static final String INVENTORY_FILE = "inventory.dat";
    private static final String CLIENTS_FILE = "clients.dat";
    private static final String SALES_FILE = "sales.dat";

    /**
     * Constructs a FileController with the specified services.
     *
     * @param inventoryService The service handling inventory operations.
     * @param clientService The service handling client operations.
     * @param saleService The service handling sales operations.
     */
    public FileController(InventoryService inventoryService, ClientService clientService, SaleService saleService) {
        this.inventoryService = inventoryService;
        this.clientService = clientService;
        this.saleService = saleService;
    }

    /**
     * Saves all data related to inventory, clients, and sales to their respective files.
     */
    public void saveAllData() {
        FileManager.saveToFile(INVENTORY_FILE, inventoryService.getAllItems());
        FileManager.saveToFile(CLIENTS_FILE, clientService.getAllClients());
        FileManager.saveToFile(SALES_FILE, saleService.getAllSales());
    }

    /**
     * Loads all data from the respective files and populates the services with the retrieved data.
     * Suppresses unchecked warnings due to generic type casting.
     */
    @SuppressWarnings("unchecked")
    public void loadAllData() {
        List<?> inventory = FileManager.loadFromFile(INVENTORY_FILE);
        if (inventory != null)
            inventoryService.loadAllItems((List<Product>) inventory);

        List<?> clients = FileManager.loadFromFile(CLIENTS_FILE);
        if (clients != null)
            clientService.loadAllClients((List<Client>) clients);

        List<?> sales = FileManager.loadFromFile(SALES_FILE);
        if (sales != null)
            saleService.loadAllSales((List<Sale>) sales);
    }
}