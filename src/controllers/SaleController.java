package controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Client;
import models.Product;
import models.Sale;
import services.ClientService;
import services.InventoryService;
import services.SaleService;
import views.SaleView;

/**
 * Controller responsible for managing sales operations.
 */
public class SaleController {

	private final SaleView saleView;
	private final SaleService saleService;
	private final InventoryService inventoryService;
	private final ClientService clientService;

	/**
	 * Constructs a SaleController with the necessary services and view.
	 *
	 * @param saleView         the view for displaying sales-related information
	 * @param saleService      the service handling sale transactions
	 * @param inventoryService the service managing product inventory
	 * @param clientService    the service managing client data
	 */
	public SaleController(SaleView saleView, SaleService saleService, InventoryService inventoryService,
			ClientService clientService) {
		this.saleView = saleView;
		this.saleService = saleService;
		this.inventoryService = inventoryService;
		this.clientService = clientService;
	}

    /**
     * Displays the main sales menu and handles user choices.
     */
	public void showMenu() {
		boolean returnToMain = false;

		while (!returnToMain) {
			int choice = saleView.displayMainMenu();

			switch (choice) {
			case 1:
				registerClientSale();
				break;
			case 2:
				searchSaleById();
				break;
			case 3:
				listAllSales();
				break;
			case 4:
				showSalesStatistics();
				break;
			case 0:
				returnToMain = true;
				break;
			default:
				saleView.displayInvalidChoice();
				break;
			}
		}
	}

	/**
	 * Handles registering a new sale for a specific client.
	 */
	private void registerClientSale() {
		// Display available clients
		List<Client> availableClients = clientService.listAllClients();
		if (availableClients.isEmpty()) {
			saleView.displayNoClientsWarning();
			return;
		}

		saleView.displayClientList(availableClients);

		// Get client ID from user
		Long clientId = saleView.getClientId();

		if (clientId == null || clientId == 0) {
			saleView.displayOperationCancelled();
			return;
		}

		// Find client in system
		Client client = clientService.searchClientById(clientId);
		if (client == null) {
			saleView.displayClientNotFound(clientId);
			return;
		}

		saleView.displayRegisteringSaleFor(client);
		registerSaleInternal(client);
	}

	/**
	 * Internal method for handling sale registration with optional client
	 * association.
	 * 
	 * @param client The client for whom the sale is being registered, or null for
	 *               anonymous sale.
	 */
	private void registerSaleInternal(Client client) {
		Map<Product, Double> productsToSell = new HashMap<>();
		double totalPrice = 0.0;
		boolean addingProducts = true;

		while (addingProducts) {
			// Show available products
			List<Product> availableProducts = inventoryService.listAllProducts();
			saleView.displayProductList(availableProducts);

			if (availableProducts.isEmpty()) {
				saleView.displayNoProductsWarning();
				return;
			}

			// Get product ID from user
			Long productId = saleView.getProductId();

			if (productId == null) {
				saleView.displayInvalidProductId();
				continue;
			}

			if (productId == 0) {
				if (productsToSell.isEmpty()) {
					saleView.displayEmptySaleCancelled();
					return;
				}
				addingProducts = false;
				continue;
			}

			// Search for product in inventory
			Product product = inventoryService.searchProductById(productId);
			if (product == null) {
				saleView.displayProductNotFound(productId);
				continue;
			}

			// Check if product has enough stock
			if (product.getStock() <= 0) {
				saleView.displayProductOutOfStock(product);
				continue;
			}

			// Get quantity from user
			Double quantity = saleView.getProductQuantity(product.getStock());
			if (quantity == null || quantity <= 0) {
				saleView.displayInvalidQuantity();
				continue;
			}

			if (quantity > product.getStock()) {
				saleView.displayNotEnoughStock(product.getStock());
				continue;
			}

			// Add product to sale
			productsToSell.put(product, quantity);
			double productTotal = product.getPrice() * quantity;
			totalPrice += productTotal;

			saleView.displayProductAdded(product, quantity, productTotal, totalPrice);

			// Ask if user wants to add more products
			addingProducts = saleView.confirmAddMoreProducts();
		}

		// Create and register the sale
		try {
			// Make sure client is not null before getting ID
			Long clientId = (client != null) ? client.getId() : null;

			Sale sale = new Sale(LocalDate.now(), productsToSell, totalPrice, clientId);
			saleService.addSale(sale);

			// Associate sale with client if applicable
			if (client != null) {
				try {
					client.addSaleToHistory(sale);
					saleView.displaySaleAddedToClientHistory(client);
				} catch (Exception e) {
					saleView.displayClientHistoryWarning(e.getMessage());
				}
			}

			saleView.displaySaleRegisteredSuccess(sale);
		} catch (IllegalArgumentException e) {
			saleView.displaySaleRegistrationError(e.getMessage());
		}
	}

	/**
	 * Handles searching for a sale by ID.
	 */
	private void searchSaleById() {
		Long saleId = saleView.getSaleId();
		if (saleId == null) {
			saleView.displayInvalidSaleId();
			return;
		}

		try {
			Sale sale = saleService.getSaleById(saleId);
			if (sale != null) {
				saleView.displaySale(sale);

				// Try to find which client this sale belongs to
				Client owner = findSaleOwner(sale);
				if (owner != null) {
					saleView.displaySaleOwner(owner);
				}
			} else {
				saleView.displaySaleNotFound(saleId);
			}
		} catch (Exception e) {
			saleView.displaySaleSearchError(e.getMessage());
		}
	}

	/**
	 * Finds which client a sale belongs to, if any.
	 * 
	 * @param sale The sale to find the owner for.
	 * @return The client who owns the sale, or null if no owner found.
	 */
	private Client findSaleOwner(Sale sale) {
		List<Client> clients = clientService.listAllClients();
		for (Client client : clients) {
			List<Sale> clientSales = client.getOrderHistory();
			if (clientSales != null) {
				for (Sale clientSale : clientSales) {
					if (clientSale.getId() == sale.getId()) {
						return client;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Handles listing all sales.
	 */
	private void listAllSales() {
		try {
			List<Sale> sales = saleService.listAllSales();
			if (sales.isEmpty()) {
				saleView.displayNoSalesRegistered();
			} else {
				saleView.displaySaleList(sales);
			}
		} catch (Exception e) {
			saleView.displayListSalesError(e.getMessage());
		}
	}

	/**
	 * Displays sales statistics.
	 */
	private void showSalesStatistics() {
		List<Sale> sales = saleService.listAllSales();
		if (sales.isEmpty()) {
			saleView.displayNoSalesDataAvailable();
			return;
		}

		// Calculate total revenue
		double totalRevenue = 0.0;
		for (Sale sale : sales) {
			totalRevenue += sale.getTotalPrice();
		}

		// Find most sold products
		Map<Long, Double> productQuantities = new HashMap<>();
		Map<Long, String> productNames = new HashMap<>();

		for (Sale sale : sales) {
			for (Map.Entry<Product, Double> entry : sale.getProductsSold().entrySet()) {
				Product product = entry.getKey();
				Double quantity = entry.getValue();

				productQuantities.put(product.getId(), productQuantities.getOrDefault(product.getId(), 0.0) + quantity);
				productNames.put(product.getId(), product.getName());
			}
		}

		// Calculate client statistics
		int clientSalesCount = 0;
		Client topClient = null;
		int maxSales = 0;

		List<Client> clients = clientService.listAllClients();
		for (Client client : clients) {
			List<Sale> clientSales = client.getOrderHistory();
			if (clientSales != null && !clientSales.isEmpty()) {
				clientSalesCount += clientSales.size();
				if (clientSales.size() > maxSales) {
					topClient = client;
					maxSales = clientSales.size();
				}
			}
		}

		// Display statistics through the view
		saleView.displaySalesStatistics(sales.size(), totalRevenue, clientSalesCount, topClient, maxSales,
				productQuantities, productNames);
	}
}