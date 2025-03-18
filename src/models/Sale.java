package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

/**
 * Represents a sale transaction, including the date, products sold, total
 * price, and associated client ID. Implements Serializable to allow object
 * serialization.
 */
public class Sale implements Serializable {

	private static final long serialVersionUID = 1L;
	private static long counter = 1;
	private final long id;
	private LocalDate date;
	private Map<Product, Double> productsSold;
	private double totalPrice;
	private Long clientId; // Client ID associated with the sale

	/**
	 * Constructs a new Sale with the given attributes.
	 *
	 * @param date         The date of the sale (cannot be null).
	 * @param productsSold A map containing products and their quantities (cannot be
	 *                     null, and quantities must be non-negative).
	 * @param totalPrice   The total price of the sale (must be non-negative).
	 * @param clientId     The ID of the client making the purchase (cannot be
	 *                     null).
	 * @throws IllegalArgumentException if any parameter is invalid.
	 */
	public Sale(LocalDate date, Map<Product, Double> productsSold, double totalPrice, Long clientId) {
		if (date == null) {
			throw new IllegalArgumentException("The date cannot be null");
		}
		if (productsSold == null) {
			throw new IllegalArgumentException("The list of products sold cannot be null.");
		}
		if (clientId == null) {
			throw new IllegalArgumentException("Client ID cannot be null.");
		}

		// Validate that none of the keys (Product) in the map are null
		for (Map.Entry<Product, Double> entry : productsSold.entrySet()) {
			if (entry.getKey() == null) {
				throw new IllegalArgumentException("Cannot be null products in a sale.");
			}
			if (entry.getValue() == null || entry.getValue() < 0) {
				throw new IllegalArgumentException("The quantity of products cannot be null or negative.");
			}
		}

		// Validate totalPrice (it should not be negative)
		if (totalPrice < 0) {
			throw new IllegalArgumentException("The total price of a sale cannot be negative.");
		}

		this.id = counter++; // Unique sequential ID
		this.date = date;
		this.productsSold = productsSold;
		this.totalPrice = totalPrice;
		this.clientId = clientId;
	}

	/**
	 * Gets the unique ID of the sale.
	 *
	 * @return The sale ID.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the date of the sale.
	 *
	 * @return The sale date.
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Gets the products sold in this sale along with their quantities.
	 *
	 * @return A map containing products as keys and quantities as values.
	 */
	public Map<Product, Double> getProductsSold() {
		return productsSold;
	}

	/**
	 * Gets the total price of the sale.
	 *
	 * @return The total price of the sale.
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Gets the client ID associated with this sale.
	 *
	 * @return The client's ID.
	 */
	public Long getClientId() {
		return clientId;
	}
}
