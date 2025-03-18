package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Client;
import models.Sale;

/**
 * Service class for managing clients. Provides methods to add, search, update,
 * and list clients.
 */
public class ClientService {
	private final Map<Long, Client> clients;

	/**
	 * Constructs a ClientService with an empty client list.
	 */
	public ClientService() {
		this.clients = new HashMap<>();
	}

	/**
	 * Adds a client to the system.
	 *
	 * @param client The client to add (cannot be null).
	 * @throws IllegalArgumentException if the client is null.
	 */
	public void addClient(Client client) {
		if (client == null) {
			throw new IllegalArgumentException("Client cannot be null.");
		}
		clients.put(client.getId(), client);
	}

	/**
	 * Searches for a client by ID.
	 *
	 * @param id The ID of the client (cannot be null).
	 * @return The client if found, otherwise null.
	 * @throws IllegalArgumentException if the ID is null.
	 */
	public Client searchClientById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Client ID cannot be null.");
		}
		return clients.get(id);
	}

	/**
	 * Searches for clients by name.
	 *
	 * @param name The name of the client (cannot be null or empty).
	 * @return A list of clients with the specified name.
	 * @throws IllegalArgumentException if the name is null or empty.
	 */
	public List<Client> searchClientsByName(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Client name cannot be null or empty.");
		}

		List<Client> result = new ArrayList<>();
		for (Client client : clients.values()) {
			if (client.getName().toLowerCase().contains(name.toLowerCase())) {
				result.add(client);
			}
		}
		return result;
	}

	/**
	 * Associates a sale with a client in their order history.
	 *
	 * @param clientId The ID of the client.
	 * @param sale     The sale to add to the client's order history.
	 * @throws IllegalArgumentException if the sale is null or the client does not
	 *                                  exist.
	 */
	public void addSaleToClientHistory(Long clientId, Sale sale) {
		if (sale == null) {
			throw new IllegalArgumentException("Sale cannot be null.");
		}

		Client client = searchClientById(clientId);
		if (client == null) {
			throw new IllegalArgumentException("Client not found.");
		}

		// Get the current order history of the client
		List<Sale> orderHistory = client.getOrderHistory();

		// If it's null, we might need to initialize the list
		if (orderHistory == null) {
			// Assuming Client has a setter for orderHistory
			orderHistory = new ArrayList<>();
			client.setOrderHistory(orderHistory);
		} else {
			// If the list is immutable (returned as a defensive copy), we need to create a
			// new one
			orderHistory = new ArrayList<>(orderHistory);
			client.setOrderHistory(orderHistory);
		}

		// Add the sale to the order history
		orderHistory.add(sale);
	}

	/**
	 * Lists all clients in the system.
	 *
	 * @return A list of all clients.
	 */
	public List<Client> listAllClients() {
		return new ArrayList<>(clients.values());
	}

	/**
	 * Retrieves the order history of a client.
	 *
	 * @param id The ID of the client.
	 * @return The client's order history.
	 * @throws IllegalArgumentException if the client is not found.
	 */
	public List<Sale> getClientOrderHistory(Long id) {
		Client client = searchClientById(id);
		if (client == null) {
			throw new IllegalArgumentException("Client not found.");
		}
		return client.getOrderHistory();
	}

	/**
	 * Returns all clients in the system.
	 *
	 * @return A list of all clients.
	 */
	public List<Client> getAllClients() {
		return new ArrayList<>(clients.values());
	}

	/**
	 * Loads a list of clients into the system, replacing existing data.
	 *
	 * @param clientList List of clients to load (cannot be null).
	 * @throws IllegalArgumentException if the client list is null.
	 */
	public void loadAllClients(List<Client> clientList) {
		if (clientList == null) {
			throw new IllegalArgumentException("Client list cannot be null.");
		}
		clients.clear();
		for (Client client : clientList) {
			clients.put(client.getId(), client);
		}
	}

}