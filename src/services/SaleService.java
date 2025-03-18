package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.Product;
import models.Sale;

/**
 * Service class for managing sales transactions and inventory updates.
 */
public class SaleService {

    private final Map<Long, Sale> sales;
    private final InventoryService inventoryService; // To manage the stock

    /**
     * Constructs a SaleService with an associated InventoryService.
     *
     * @param inventoryService The inventory service used for stock management.
     */
    public SaleService(InventoryService inventoryService) {
        this.sales = new HashMap<>();
        this.inventoryService = inventoryService;
    }

    /**
     * Adds a sale, updates inventory stock, and ensures valid transactions.
     *
     * @param sale The sale to add.
     * @throws IllegalArgumentException if the sale is null, empty, or contains invalid products or quantities.
     */
    public void addSale(Sale sale) {
        if (sale == null || sale.getProductsSold() == null || sale.getProductsSold().isEmpty()) {
            throw new IllegalArgumentException("Sale cannot be null or empty.");
        }

        // Check and update stock before registering the sale
        for (Map.Entry<Product, Double> entry : sale.getProductsSold().entrySet()) {
            Product product = entry.getKey();
            double quantitySold = entry.getValue();

            if (product == null || quantitySold <= 0) {
                throw new IllegalArgumentException("Invalid product or quantity in sale.");
            }

            // Get the product from inventory by ID
            Product inventoryProduct = inventoryService.searchProductById(product.getId());
            if (inventoryProduct == null) {
                throw new IllegalArgumentException("Product not found in inventory: " + product.getName());
            }

            // Validate stock availability
            if (inventoryProduct.getStock() < quantitySold) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }

            // Reduce stock
            inventoryService.updateStock(product.getId(), inventoryProduct.getStock() - quantitySold);
        }

        // Register the sale using its ID
        sales.put(sale.getId(), sale);
    }

    /**
     * Retrieves a sale by its ID.
     *
     * @param id The ID of the sale.
     * @return The sale if found, otherwise null.
     * @throws IllegalArgumentException if the ID is null.
     */
    public Sale getSaleById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Sale ID cannot be null.");
        }
        return sales.get(id);
    }

    /**
     * Retrieves a list of all recorded sales.
     *
     * @return A list containing all sales.
     */
    public List<Sale> listAllSales() {
        return new ArrayList<>(sales.values());
    }
    
    /**
     * Retrieves sales within a specific date range.
     *
     * @param startDate The start date of the range (inclusive).
     * @param endDate The end date of the range (inclusive).
     * @return A list of sales within the specified date range.
     * @throws IllegalArgumentException if startDate or endDate is null, or if startDate is after endDate.
     */
    public List<Sale> getSalesByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null.");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        
        return sales.values().stream()
                .filter(sale -> {
                    LocalDate saleDate = sale.getDate();
                    return (saleDate.isEqual(startDate) || saleDate.isAfter(startDate)) && 
                           (saleDate.isEqual(endDate) || saleDate.isBefore(endDate));
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Retrieves all sales.
     *
     * @return A list containing all sales.
     */
    public List<Sale> getAllSales() {
        return new ArrayList<>(sales.values());
    }

    /**
     * Loads a list of sales into the service, replacing any existing records.
     *
     * @param saleList The list of sales to load.
     * @throws IllegalArgumentException if the provided list is null.
     */
    public void loadAllSales(List<Sale> saleList) {
        if (saleList == null) {
            throw new IllegalArgumentException("Sale list cannot be null.");
        }
        sales.clear();
        for (Sale sale : saleList) {
            sales.put(sale.getId(), sale);
        }
    }
}
