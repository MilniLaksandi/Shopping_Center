import java.util.ArrayList;

class FoodQueue {
    private ArrayList<Customer> customers; // ArrayList to store customers in the food queue
    private int capacity; // Maximum capacity of the food queue

    public FoodQueue(int capacity) {
        this.capacity = capacity; // Set the maximum capacity of the food queue
        this.customers = new ArrayList<>(capacity); // Create an ArrayList with the specified capacity to hold the customers
    }


    public int getSize() {
        return customers.size(); // Return the current size of the food queue
    }

    public int getCapacity() {
        return capacity; // Return the maximum capacity of the food queue
    }

    public boolean isEmpty() {
        return customers.isEmpty(); // Check if the food queue is empty and return a boolean value
    }

    public boolean isFull() {
        return customers.size() == capacity; // Check if the food queue is full and return a boolean value
    }

    public ArrayList<Customer> getCustomers() {
        return customers; // Return the ArrayList of customers in the food queue
    }


    public int getCashierNumber() {
        if (this == FoodCenterr.cashierQueue1) { // Check if the current instance is the same as the cashierQueue1 instance
            return 1; // Return 1 to indicate the cashier number is 1
        } else if (this == FoodCenterr.cashierQueue2) { // Check if the current instance is the same as the cashierQueue2 instance
            return 2; // Return 2 to indicate the cashier number is 2
        } else if (this == FoodCenterr.cashierQueue3) { // Check if the current instance is the same as the cashierQueue3 instance
            return 3; // Return 3 to indicate the cashier number is 3
        } else {
            return -1; // Return -1 if the current instance does not match any of the cashier queues
        }
    }


    public void addCustomer(Customer customer) {
        if (!isFull()) { // Check if the food queue is not full
            customers.add(customer); // Add the customer to the food queue
        }
    }

    public Customer removeCustomer(int index) {
        if (isValidIndex(index)) { // Check if the index is valid
            return customers.remove(index); // Remove and return the customer at the specified index
        }
        return null; // Return null if the index is invalid
    }

    public boolean isValidIndex(int index) {
        return index >= 0 && index < customers.size(); // Check if the index is within the valid range of indices for the customers ArrayList
    }

    public double getIncome(double burgerPrice) {
        double income = 0.0; // Variable to store the total income
        for (Customer customer : customers) { // Iterate over each customer in the food queue
            income += customer.getBurgersRequired() * burgerPrice; // Calculate the income by multiplying the number of burgers required by the burger price and adding it to the total income
        }
        return income; // Return the total income
    }


    public String getDataString(String prefix) {
        // Generates a formatted string representation of the customers' data in the queue

        StringBuilder sb = new StringBuilder();

        // Iterate over each customer in the queue
        for (Customer customer : customers) {
            // Append the prefix, customer's full name, burgers required, and a new line character
            sb.append(prefix).append(customer.getFullName()).append(":").append(customer.getBurgersRequired()).append("\n");
        }

        // Return the final formatted string representation of the customers' data
        return sb.toString();
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < capacity; i++) {
            if (i < customers.size()) { // Check if there is a customer at the current position
                sb.append("O"); // Append "O" to represent an occupied position
            } else {
                sb.append("X"); // Append "X" to represent an unoccupied position
            }
            if (i != capacity - 1) { // Check if it's not the last position
                sb.append(" "); // Append a space after each position except the last one
            }
        }
        return sb.toString(); // Return the resulting string representation
    }

}