## Introduction

The Store Inventory Management System is a Java-based application designed to efficiently manage products, clients, and sales. It provides a structured approach to organizing inventory, tracking transactions, and generating reports.

## Application Structure
```bash
StoreInventoryManagementSystem
├── src
│   ├── controllers
│   │   ├── ClientController.java     # Manages client-related operations
│   │   ├── FileController.java       # Handles file-related operations
│   │   ├── MainController.java       # Central controller coordinating system logic
│   │   ├── ProductController.java    # Manages product-related operations
│   │   ├── ReportController.java     # Handles report generation
│   │   ├── SaleController.java       # Manages sales transactions
│   │
│   ├── exceptions
│   │   ├── FileManagerException.java # Custom exception for file handling errors
│   │
│   ├── main
│   │   ├── StoreApp.java             # Main application class, initializes the system
│   │
│   ├── models
│   │   ├── Category.java             # Represents product categories
│   │   ├── Client.java               # Represents clients in the system
│   │   ├── Product.java              # Represents products in the inventory
│   │   ├── Sale.java                 # Represents sales transactions
│   │
│   ├── services
│   │   ├── ClientService.java        # Business logic for client operations
│   │   ├── InventoryService.java     # Handles inventory management logic
│   │   ├── SaleService.java          # Manages sales-related operations
│   │
│   ├── utils
│   │   ├── FileManager.java          # Provides file handling utilities
│   │
│   ├── views
│   │   ├── ClientView.java           # Displays client-related data
│   │   ├── ProductView.java          # Displays product-related data
│   │   ├── ReportView.java           # Displays generated reports
│   │   ├── SaleView.java             # Displays sales-related data
│
├── module-info.java                   # Module descriptor for Java module system
```

## Goals  
My main objectives of developing this Store Inventory Management System include:  

- **1. Deepening Knowledge of Java Collections**  
  Implementing custom data structures and utilizing Java’s built-in collections (Lists, Maps, Sets) efficiently for managing inventory data.  

- **2. Applying MVC Architecture**  
  Structuring the application using the Model-View-Controller pattern to separate concerns and improve maintainability.  

- **3. Exploring File Handling and Persistence**  
  Implementing file management techniques for data storage, retrieval, and manipulation.  

- **4. Practicing Software Design Principles**  
  Applying SOLID principles and best practices to write clean, efficient, and scalable code.

## Control-Flow Diagram
### Main
![flow-control-sistem](https://github.com/user-attachments/assets/fd10ef7f-395c-494a-92d0-7b27d6e71efd)

## Product
![Product-controller](https://github.com/user-attachments/assets/650a9270-9d56-47ae-8400-63096cdcda18)

## Client
![Client-controller](https://github.com/user-attachments/assets/e12b6cde-9a1d-423f-8cd1-b6f3725c0029)

## Sale
![sale-controller](https://github.com/user-attachments/assets/67378f7c-244f-432e-b7ca-a6f7760d5b28)

## Report
![report-controller](https://github.com/user-attachments/assets/46f2824f-d874-48be-a891-2ea7170e2151)

## How To Use
1. Clone this github repository in your computer
 ```bash
git clone https://github.com/heriamezcua/StoreInventoryManagementSystem.git
```

3. Compile the source code
```bash
javac -d .\bin .\src\models\*.java .\src\controllers\*.java .\src\services\*.java .\src\views\*.java .\src\main\*.java .\src\utils\*.java .\src\exceptions\*.java
```

3. Execute the game
```bash
java -cp .\bin main.StoreApp
```

## Demo

### App stars
![image](https://github.com/user-attachments/assets/590f56de-e89d-483a-a2ad-9399f38ce055)

### Product Management
![image](https://github.com/user-attachments/assets/2d3b06dd-7fae-44ca-9e1a-273722daeff6)

### Client Management
![image](https://github.com/user-attachments/assets/620fff47-b7b5-48a4-8b2e-ade5a3e6c35e)

### Sales Management
![image](https://github.com/user-attachments/assets/c0ec4c18-2633-45a0-8cac-9f52d816c427)

### Reports Management
![image](https://github.com/user-attachments/assets/dac8fcf4-2382-4c16-90cc-bc1e1a7aed5f)

### Save/Load Data
![image](https://github.com/user-attachments/assets/a6ab4959-5154-4238-a661-b531fbbf6a57)

