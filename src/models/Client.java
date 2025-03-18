package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a client with a unique ID, name, and order history. Implements
 * Serializable to allow object serialization.
 */
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;
	private static long counter = 1;
	private final long id;
	private String name;
	private List<Sale> orderHistory;

	/**
	 * Constructs a new Client with the given name. Assigns a unique ID to the
	 * client.
	 *
	 * @param name The name of the client.
	 */
	public Client(String name) {
		this.name = name;
		this.id = counter++; // Unique random ID
		this.orderHistory = new ArrayList<>();
	}

	/**
	 * Gets the unique ID of the client.
	 *
	 * @return The client ID.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the name of the client.
	 *
	 * @return The client's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the order history of the client.
	 *
	 * @return A list of the client's past sales.
	 */
	public List<Sale> getOrderHistory() {
		return orderHistory;
	}

	/**
	 * Sets the order history for this client.
	 * 
	 * @param orderHistory The new order history list to set
	 */
	public void setOrderHistory(List<Sale> orderHistory) {
		this.orderHistory = orderHistory;
	}

	/**
	 * Adds a sale to the client's order history.
	 *
	 * @param sale The sale to add to the order history.
	 * @throws IllegalArgumentException if the sale is null.
	 */
	public void addSaleToHistory(Sale sale) {
		if (sale == null) {
			throw new IllegalArgumentException("Sale cannot be null.");
		}
		orderHistory.add(sale);
	}
}
