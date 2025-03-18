package controllers;

import java.util.List;

import models.Client;
import models.Sale;
import services.ClientService;
import views.ClientView;

/**
 * Controller class for managing client-related operations. It interacts with
 * the ClientView to display client information and the ClientService to handle
 * business logic.
 */
public class ClientController {

	private final ClientView view;
	private final ClientService clientService;

	/**
	 * Constructs a new ClientController with the specified view and client service.
	 *
	 * @param view          the ClientView used for displaying client-related
	 *                      information
	 * @param clientService the ClientService responsible for handling client
	 *                      operations
	 */
	public ClientController(ClientView view, ClientService clientService) {
		this.view = view;
		this.clientService = clientService;
	}

	/**
	 * Displays the client management menu and handles user choices.
	 */
	public void processMenu() {
		boolean returnToMain = false;

		while (!returnToMain) {
			int choice = view.showMenu();

			switch (choice) {
			case 1:
				registerClient();
				break;
			case 2:
				searchClientById();
				break;
			case 3:
				searchClientsByName();
				break;
			case 4:
				listAllClients();
				break;
			case 5:
				viewClientOrderHistory();
				break;
			case 0:
				returnToMain = true;
				break;
			default:
				view.showError("Invalid choice. Please try again.");
				break;
			}
		}
	}

	/**
	 * Handles registering a new client.
	 */
	private void registerClient() {
		String name = view.getClientName();

		if (name.isEmpty()) {
			view.showError("Operation cancelled: Client name cannot be empty.");
			return;
		}

		try {
			Client newClient = new Client(name);
			clientService.addClient(newClient);
			view.showClientRegistrationSuccess(newClient.getId());
		} catch (IllegalArgumentException e) {
			view.showError("Error registering client: " + e.getMessage());
		}
	}

	/**
	 * Handles searching for a client by ID.
	 */
	private void searchClientById() {
		Long clientId = view.getClientIdForSearch();
		if (clientId == null) {
			view.showError("Operation cancelled: Invalid client ID.");
			return;
		}

		try {
			Client client = clientService.searchClientById(clientId);
			if (client != null) {
				view.displayClient(client);
			} else {
				view.showClientNotFound(clientId);
			}
		} catch (Exception e) {
			view.showError("Error searching for client: " + e.getMessage());
		}
	}

	/**
	 * Handles searching for clients by name.
	 */
	private void searchClientsByName() {
		String searchName = view.getClientNameForSearch();

		if (searchName.isEmpty()) {
			view.showError("Operation cancelled: Search name cannot be empty.");
			return;
		}

		try {
			List<Client> clients = clientService.searchClientsByName(searchName);
			if (clients.isEmpty()) {
				view.showNoClientsFound(searchName);
			} else {
				view.showClientsMatchingHeader(searchName);
				view.displayClientList(clients);
			}
		} catch (Exception e) {
			view.showError("Error searching for clients: " + e.getMessage());
		}
	}

	/**
	 * Handles listing all clients.
	 */
	private void listAllClients() {
		view.showAllClientsHeader();

		try {
			List<Client> clients = clientService.listAllClients();
			if (clients.isEmpty()) {
				view.showNoClientsRegistered();
			} else {
				view.displayClientList(clients);
			}
		} catch (Exception e) {
			view.showError("Error listing clients: " + e.getMessage());
		}
	}

	/**
	 * Handles viewing a client's order history.
	 */
	private void viewClientOrderHistory() {
		Long clientId = view.getClientIdForOrderHistory();
		if (clientId == null) {
			view.showError("Operation cancelled: Invalid client ID.");
			return;
		}

		try {
			Client client = clientService.searchClientById(clientId);
			if (client == null) {
				view.showClientNotFound(clientId);
				return;
			}

			List<Sale> orderHistory = clientService.getClientOrderHistory(clientId);
			if (orderHistory == null || orderHistory.isEmpty()) {
				view.showNoOrderHistory(client.getName());
			} else {
				view.showOrderHistoryHeader(client.getName());
				view.displayOrderHistory(orderHistory);
			}
		} catch (Exception e) {
			view.showError("Error viewing order history: " + e.getMessage());
		}
	}
}