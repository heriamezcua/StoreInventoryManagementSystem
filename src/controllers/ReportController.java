package controllers;

import services.InventoryService;
import services.ClientService;
import services.SaleService;
import models.Product;
import models.Client;
import models.Sale;
import views.ReportView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for generating various reports from the system data.
 */
public class ReportController {
    private final ReportView view;
    private final InventoryService inventoryService;
    private final ClientService clientService;
    private final SaleService saleService;

    /**
     * Constructor for ReportController.
     * 
     * @param view             View for user interface
     * @param inventoryService Service for inventory management
     * @param clientService    Service for client management
     * @param saleService      Service for sales management
     */
    public ReportController(ReportView view, InventoryService inventoryService, ClientService clientService,
            SaleService saleService) {
        this.view = view;
        this.inventoryService = inventoryService;
        this.clientService = clientService;
        this.saleService = saleService;
    }

    /**
     * Displays the reports menu and handles user choices.
     */
    public void showMenu() {
        boolean returnToMain = false;

        while (!returnToMain) {
            int choice = view.displayMainMenu();

            switch (choice) {
            case 1:
                generateSalesReport();
                break;
            case 2:
                generateInventoryReport();
                break;
            case 3:
                generateClientReport();
                break;
            case 4:
                generateProductPerformanceReport();
                break;
            case 0:
                returnToMain = true;
                break;
            default:
                view.displayMessage("Invalid choice. Please try again.");
                break;
            }
        }
    }

    /**
     * Method to generate a sales report.
     */
    private void generateSalesReport() {
        view.displayHeader("SALES REPORT");
        view.displayMessage("Please enter the date range for the report:");

        LocalDate startDate = view.getDateInput("Start date (YYYY-MM-DD): ");
        if (startDate == null) {
            return;
        }

        LocalDate endDate = view.getDateInput("End date (YYYY-MM-DD): ");
        if (endDate == null) {
            return;
        }

        if (endDate.isBefore(startDate)) {
            view.displayMessage("End date cannot be before start date.");
            return;
        }

        List<Sale> sales = saleService.getSalesByDateRange(startDate, endDate);

        if (sales.isEmpty()) {
            view.displayMessage("No sales found in the specified date range.");
            return;
        }

        double totalRevenue = 0;
        Map<Product, Double> productQuantities = new HashMap<>();

        // Calculate total revenue and product quantities
        for (Sale sale : sales) {
            totalRevenue += sale.getTotalPrice();

            // Accumulate product quantities
            for (Map.Entry<Product, Double> entry : sale.getProductsSold().entrySet()) {
                Product product = entry.getKey();
                Double quantity = entry.getValue();
                productQuantities.put(product, productQuantities.getOrDefault(product, 0.0) + quantity);
            }
        }

        // Prepare data for displaying products sold
        Map<String, Double[]> productSummary = new HashMap<>();
        for (Map.Entry<Product, Double> entry : productQuantities.entrySet()) {
            Product product = entry.getKey();
            Double quantity = entry.getValue();
            double productRevenue = product.getPrice() * quantity;
            
            productSummary.put(product.getName(), new Double[]{quantity, productRevenue});
        }

        // Display the sales report
        view.displaySalesReport(sales, startDate, endDate, totalRevenue, productSummary, clientService);
        view.waitForInput();
    }

    /**
     * Method to generate an inventory report.
     */
    private void generateInventoryReport() {
        view.displayHeader("INVENTORY REPORT");

        List<Product> products = inventoryService.listAllProducts();

        if (products.isEmpty()) {
            view.displayMessage("No products found in the inventory.");
            return;
        }

        view.displayInventoryReport(products);
        view.waitForInput();
    }

    /**
     * Method to generate a client report.
     */
    private void generateClientReport() {
        view.displayHeader("CLIENT REPORT");

        List<Client> clients = clientService.listAllClients();

        if (clients.isEmpty()) {
            view.displayMessage("No clients found in the system.");
            return;
        }

        Map<Long, ClientSummary> clientSummaries = new HashMap<>();
        
        for (Client client : clients) {
            List<Sale> clientOrders = clientService.getClientOrderHistory(client.getId());
            int orderCount = clientOrders.size();
            double totalSpent = 0;

            for (Sale sale : clientOrders) {
                totalSpent += sale.getTotalPrice();
            }
            
            clientSummaries.put(client.getId(), new ClientSummary(client.getName(), orderCount, totalSpent));
        }

        view.displayClientReport(clients, clientSummaries);
        view.waitForInput();
    }

    /**
     * Method to generate a product performance report.
     */
    private void generateProductPerformanceReport() {
        view.displayHeader("PRODUCT PERFORMANCE REPORT");

        List<Sale> allSales = saleService.listAllSales();

        if (allSales.isEmpty()) {
            view.displayMessage("No sales data available for product performance report.");
            return;
        }

        Map<Product, Double> productQuantities = new HashMap<>();
        Map<Product, Double> productRevenue = new HashMap<>();

        // Collect data from all sales
        for (Sale sale : allSales) {
            for (Map.Entry<Product, Double> entry : sale.getProductsSold().entrySet()) {
                Product product = entry.getKey();
                Double quantity = entry.getValue();
                double revenue = product.getPrice() * quantity;

                productQuantities.put(product, productQuantities.getOrDefault(product, 0.0) + quantity);
                productRevenue.put(product, productRevenue.getOrDefault(product, 0.0) + revenue);
            }
        }

        // Convert to list for sorting
        List<Map.Entry<Product, Double>> productRevenueList = new ArrayList<>(productRevenue.entrySet());
        productRevenueList.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        double totalRevenue = productRevenueList.stream().mapToDouble(Map.Entry::getValue).sum();

        view.displayProductPerformanceReport(productRevenueList, productQuantities, totalRevenue);
        view.waitForInput();
    }
    
    /**
     * Helper class to store client summary information.
     */
    public static class ClientSummary {
        private final String name;
        private final int orderCount;
        private final double totalSpent;
        
        public ClientSummary(String name, int orderCount, double totalSpent) {
            this.name = name;
            this.orderCount = orderCount;
            this.totalSpent = totalSpent;
        }
        
        public String getName() {
            return name;
        }
        
        public int getOrderCount() {
            return orderCount;
        }
        
        public double getTotalSpent() {
            return totalSpent;
        }
    }
}