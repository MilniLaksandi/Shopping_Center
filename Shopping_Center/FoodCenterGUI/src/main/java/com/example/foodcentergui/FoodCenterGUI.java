package com.example.foodcentergui;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;

public class FoodCenterGUI extends Application {
    public static FoodQueue cashierQueue1;
    public static FoodQueue cashierQueue2;
    public static FoodQueue cashierQueue3;

    public FoodQueue getQUEUE1() {
        return cashierQueue1;
    }

    public FoodQueue getQUEUE2() {
        return cashierQueue2;
    }

    public FoodQueue getQUEUE3() {
        return cashierQueue3;
    }
    private CircularQueue<Customer> waitingList;
    private int burgersAvailability = 50;

    private TableView<Customer> tableView;
    private ObservableList<Customer> customerList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize the queues, waiting list, and burger stock
        cashierQueue1 = new FoodQueue(2);
        cashierQueue2 = new FoodQueue(3);
        cashierQueue3 = new FoodQueue(5);
        waitingList = new CircularQueue<>(10);
        burgersAvailability= 50;

        // Create the GUI components
        Label titleLabel = new Label("Foodies Fave Food Center");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        tableView = new TableView<>();
        customerList = FXCollections.observableArrayList();
        tableView.setItems(customerList);

        TableColumn<Customer, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(data -> data.getValue().firstNameProperty());

        TableColumn<Customer, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(data -> data.getValue().lastNameProperty());

        TableColumn<Customer, Integer> burgersRequiredColumn = new TableColumn<>("Burgers Required");
        burgersRequiredColumn.setCellValueFactory(data -> data.getValue().burgersRequiredProperty().asObject());

        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, burgersRequiredColumn);

        Button viewAllQueuesButton = new Button("View All Queues");
        viewAllQueuesButton.setOnAction(e -> viewAllQueues());

        Button viewEmptyQueuesButton = new Button("View Empty Queues");
        viewEmptyQueuesButton.setOnAction(e -> viewEmptyQueues());

        Button addCustomerButton = new Button("Add Customer");
        addCustomerButton.setOnAction(e -> addCustomer());

        Button removeCustomerButton = new Button("Remove Customer");
        removeCustomerButton.setOnAction(e -> removeCustomer());

        Button removeServedCustomerButton = new Button("Remove Served Customer");
        removeServedCustomerButton.setOnAction(e -> removeServedCustomer());

        Button viewCustomersSortedButton = new Button("View Customers Sorted");
        viewCustomersSortedButton.setOnAction(e -> viewCustomersSorted());

        Button viewRemainingStockButton = new Button("View Remaining Stock");
        viewRemainingStockButton.setOnAction(e -> viewRemainingStock());

        Button addBurgersToStockButton = new Button("Add Burgers to Stock");
        addBurgersToStockButton.setOnAction(e -> addBurgersToStock());

        Button printIncomeButton = new Button("Print Income");
        printIncomeButton.setOnAction(e -> printIncome());

        VBox buttonsBox = new VBox(10,
                viewAllQueuesButton,
                viewEmptyQueuesButton,
                addCustomerButton,
                removeCustomerButton,
                removeServedCustomerButton,
                viewCustomersSortedButton,
                viewRemainingStockButton,
                addBurgersToStockButton,
                printIncomeButton
        );
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(10));

        HBox root = new HBox(20, tableView, buttonsBox);
        root.setPadding(new Insets(10));

        // Set up the scene and stage
        Scene scene = new Scene(new VBox(titleLabel, root), 800, 400);
        primaryStage.setTitle("Food Center Operator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void viewAllQueues() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All Queues");
        alert.setHeaderText(null);
        alert.setContentText("Cashier 1: " + cashierQueue1 + "\n" +
                "Cashier 2: " + cashierQueue2 + "\n" +
                "Cashier 3: " + cashierQueue3 + "\n" +
                "O-Occupied  X-Not Occupied");
        alert.showAndWait();
    }

    private void viewEmptyQueues() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Empty Queues");
        alert.setHeaderText(null);

        StringBuilder emptyQueues = new StringBuilder();
        if (cashierQueue1.isEmpty()) {
            emptyQueues.append("Cashier 1\n");
        }
        if (cashierQueue2.isEmpty()) {
            emptyQueues.append("Cashier 2\n");
        }
        if (cashierQueue3.isEmpty()) {
            emptyQueues.append("Cashier 3\n");
        }

        if (emptyQueues.length() > 0) {
            alert.setContentText(emptyQueues.toString());
        } else {
            alert.setContentText("No empty queues.");
        }

        alert.showAndWait();
    }

    private void addCustomer() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Customer");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter customer first name:");

        String firstName = dialog.showAndWait().orElse("");
        if (firstName.isEmpty()) return;

        dialog.setContentText("Enter customer last name:");
        String lastName = dialog.showAndWait().orElse("");
        if (lastName.isEmpty()) return;

        int burgersRequired = getBurgersRequired();
        if (burgersRequired == -1) return;

        Customer customer = new Customer(firstName, lastName, burgersRequired);
        FoodQueue shortestQueue = getShortestQueue();

        if (shortestQueue != null && shortestQueue.getSize() < shortestQueue.getCapacity()) {
            shortestQueue.addCustomer(customer);
            customerList.add(customer);
            showAlert(Alert.AlertType.INFORMATION, "Customer Added", "Customer " + customer.getFullName() +
                    " added to Cashier " + shortestQueue.getCashierNumber() + ".");
        } else {
            if (!waitingList.isFull()) {
                waitingList.enqueue(customer);
                showAlert(Alert.AlertType.INFORMATION, "Customer Added", "All cashiers are full. Customer " +
                        customer.getFullName() + " added to the waiting list.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Waiting List Full", "Waiting list is full. Customer not added.");
            }
        }

        if (burgersAvailability <= 10) {
            showAlert(Alert.AlertType.WARNING, "Low Stock", "Warning: Low stock! Remaining burgers: " + burgersAvailability);
        }
        if ( burgersAvailability== 0) {
            showAlert(Alert.AlertType.WARNING, "No Stock", "Burger stock is empty...");
        }
    }

    private int getBurgersRequired() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Customer");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the number of burgers required:");

        int burgersRequired = -1;

        try {
            burgersRequired = Integer.parseInt(dialog.showAndWait().orElse(""));
            if (burgersRequired < 0) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Number of burgers required must be a positive integer.");
                return -1;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid number format.");
            return -1;
        }

        return burgersRequired;
    }

    private FoodQueue getShortestQueue() {
        if (cashierQueue1.getSize() < cashierQueue1.getCapacity()) {
            return cashierQueue1;
        } else if (cashierQueue2.getSize() < cashierQueue2.getCapacity()) {
            return cashierQueue2;
        } else if (cashierQueue3.getSize() < cashierQueue3.getCapacity()) {
            return cashierQueue3;
        } else {
            return null;
        }
    }

    private void removeCustomer() {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            showAlert(Alert.AlertType.WARNING, "No Customer Selected", "Please select a customer to remove.");
            return;
        }

        Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
        FoodQueue selectedCashier = findCashierForCustomer(selectedCustomer);

        if (selectedCashier == null) {
            showAlert(Alert.AlertType.ERROR, "Customer Not Found", "Selected customer is not in any queue.");
            return;
        }

        selectedCashier.removeCustomer(selectedCustomer);
        customerList.remove(selectedCustomer);

        showAlert(Alert.AlertType.INFORMATION, "Customer Removed", "Customer " + selectedCustomer.getFullName() +
                " removed from Cashier " + selectedCashier.getCashierNumber() + ".");
    }

    private FoodQueue findCashierForCustomer(Customer customer) {
        if (cashierQueue1.getCustomers().contains(customer)) {
            return cashierQueue1;
        } else if (cashierQueue2.getCustomers().contains(customer)) {
            return cashierQueue2;
        } else if (cashierQueue3.getCustomers().contains(customer)) {
            return cashierQueue3;
        } else {
            return null;
        }
    }

    private void removeServedCustomer() {
        if (!cashierQueue1.isEmpty()) {
            removeCustomerFromQueue(cashierQueue1);
        } else if (!cashierQueue2.isEmpty()) {
            removeCustomerFromQueue(cashierQueue2);
        } else if (!cashierQueue3.isEmpty()) {
            removeCustomerFromQueue(cashierQueue3);
        } else {
            showAlert(Alert.AlertType.WARNING, "No Customers", "All cashiers and waiting list are empty. No customers to remove.");
        }
    }

    private void removeCustomerFromQueue(FoodQueue queue) {
        Customer removedCustomer = queue.removeCustomer(0);
        reduceBurgerStock(removedCustomer.getBurgersRequired());
        customerList.remove(removedCustomer);

        showAlert(Alert.AlertType.INFORMATION, "Customer Removed", "Customer " + removedCustomer.getFullName() +
                " removed from Cashier " + queue.getCashierNumber() + ".");
        moveCustomerFromWaitingList();
    }

    private void moveCustomerFromWaitingList() {
        if (!waitingList.isEmpty()) {
            Customer nextCustomer = waitingList.dequeue();
            FoodQueue shortestQueue = getShortestQueue();
            if (shortestQueue != null) {
                shortestQueue.addCustomer(nextCustomer);
                customerList.add(nextCustomer);
                showAlert(Alert.AlertType.INFORMATION, "Customer Added", "Next customer from the waiting list, " +
                        nextCustomer.getFullName() + ", added to Cashier " + shortestQueue.getCashierNumber() + ".");
            } else {
                showAlert(Alert.AlertType.WARNING, "All Cashiers Full", "All cashiers are full. Next customer from the waiting list not added.");
            }

            reduceBurgerStock(nextCustomer.getBurgersRequired());
            showAlert(Alert.AlertType.INFORMATION, "Customer Removed", "Customer " + nextCustomer.getFullName() +
                    " removed from the waiting list.");
        }
        checkBurgerStock();
    }

    private void reduceBurgerStock(int amount) {
        if (burgersAvailability>= amount) {
            burgersAvailability -= amount;
        } else {
            showAlert(Alert.AlertType.ERROR, "No Burgers in Stock", "Cannot remove a customer. No burgers in stock.");
        }
    }

    private void viewCustomersSorted() {
        ArrayList<Customer> allCustomers = new ArrayList<>();

        allCustomers.addAll(cashierQueue1.getCustomers());
        allCustomers.addAll(cashierQueue2.getCustomers());
        allCustomers.addAll(cashierQueue3.getCustomers());

        if (allCustomers.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Empty Queue", "Queue is empty.");
        } else {
            sortCustomers(allCustomers);
            StringBuilder sortedCustomers = new StringBuilder("All Customers Sorted in alphabetical order:\n");
            for (Customer customer : allCustomers) {
                sortedCustomers.append(customer.getFullName()).append("\n");
            }
            showAlert(Alert.AlertType.INFORMATION, "Sorted Customers", sortedCustomers.toString());
        }
    }

    private void sortCustomers(ArrayList<Customer> customers) {
        customers.sort(Comparator.comparing(Customer::getFullName));
    }

    private void viewRemainingStock() {
        showAlert(Alert.AlertType.INFORMATION, "Remaining Stock", "Remaining burgers stock: " + burgersAvailability);
    }

    private void addBurgersToStock() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Burgers to Stock");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the number of burgers to add:");

        int burgersToAdd;

        try {
            burgersToAdd = Integer.parseInt(dialog.showAndWait().orElse(""));
            if (burgersToAdd <= 0) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Number of burgers to add must be a positive integer.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid number format.");
            return;
        }

        burgersAvailability+= burgersToAdd;
        showAlert(Alert.AlertType.INFORMATION, "Burgers Added", "Added " + burgersToAdd + " burgers to the stock.\nNew stock: " + burgersAvailability);
    }

    private void printIncome() {
        double income1 = cashierQueue1.getIncome(650.00);
        double income2 = cashierQueue2.getIncome(650.00);
        double income3 = cashierQueue3.getIncome(650.00);

        showAlert(Alert.AlertType.INFORMATION, "Income", "Income of each queue:\n" +
                "Cashier 1: " + income1 + "\n" +
                "Cashier 2: " + income2 + "\n" +
                "Cashier 3: " + income3);
    }

    private void checkBurgerStock() {
        if (burgersAvailability <= 10) {
            showAlert(Alert.AlertType.WARNING, "Low Stock", "Warning: Low stock! Remaining burgers: " + burgersAvailability);
        }
        if (burgersAvailability == 0) {
            showAlert(Alert.AlertType.WARNING, "No Stock", "Burger stock is empty...");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

class Customer {
    private String firstName;
    private String lastName;
    private int burgersRequired;

    public Customer(String firstName, String lastName, int burgersRequired) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.burgersRequired = burgersRequired;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBurgersRequired() {
        return burgersRequired;
    }

    public StringProperty firstNameProperty() {
        return new SimpleStringProperty(firstName);
    }

    public StringProperty lastNameProperty() {
        return new SimpleStringProperty(lastName);
    }

    public IntegerProperty burgersRequiredProperty() {
        return new SimpleIntegerProperty(burgersRequired);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}

class FoodQueue {
    private ArrayList<Customer> customers;
    private int capacity;

    public FoodQueue(int capacity) {
        this.capacity = capacity;
        this.customers = new ArrayList<>(capacity);
    }

    public int getSize() {
        return customers.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isEmpty() {
        return customers.isEmpty();
    }

    public boolean isFull() {
        return customers.size() == capacity;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public int getCashierNumber() {
        if (this.equals(FoodCenterGUI.cashierQueue1)) {
            return 1;
        } else if (this.equals(FoodCenterGUI.cashierQueue2)) {
            return 2;
        } else if (this.equals(FoodCenterGUI.cashierQueue3)) {
            return 3;
        } else {
            return -1;
        }
    }


    public void addCustomer(Customer customer) {
        if (!isFull()) {
            customers.add(customer);
        }
    }

    public Customer removeCustomer(int index) {
        if (isValidIndex(index)) {
            return customers.remove(index);
        }
        return null;
    }

    public boolean isValidIndex(int index) {
        return index >= 0 && index < customers.size();
    }

    public double getIncome(double burgerPrice) {
        double income = 0.0;
        for (Customer customer : customers) {
            income += customer.getBurgersRequired() * burgerPrice;
        }
        return income;
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < capacity; i++) {
            if (i < customers.size()) {
                sb.append("O");
            } else {
                sb.append("X");
            }
            if (i != capacity - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}

class CircularQueue<T> {
    private Object[] elements;
    private int front;
    private int rear;
    private int capacity;
    private int size;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[capacity];
        this.front = -1;
        this.rear = -1;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int getSize() {
        return size;
    }

    public void enqueue(T item) {
        if (isFull()) {
            throw new IllegalStateException("CircularQueue is full.");
        }

        if (isEmpty()) {
            front = 0;
        }

        rear = (rear + 1) % capacity;
        elements[rear] = item;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T item = (T) elements[front];
        elements[front] = null;

        if (front == rear) {
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % capacity;
        }

        size--;
        return item;
    }
}
