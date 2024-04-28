
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;

public class FoodCenterr {
    // Constants
    private static final int Current_Burger_Stock = 50; // Total number of burgers in stock


    private static final int QUEUE1_CAPACITY = 2; // Capacity of Queue 1
    private static final int QUEUE2_CAPACITY = 3; // Capacity of Queue 2
    private static final int QUEUE3_CAPACITY = 5; // Capacity of Queue 3

    private static final double BURGER_PRICE = 650.00;// Price of a burger

    // Queues
    public static final FoodQueue cashierQueue1 = new FoodQueue(QUEUE1_CAPACITY);// Queue 1 for cashier
    public static final FoodQueue cashierQueue2 = new FoodQueue(QUEUE2_CAPACITY);// Queue 2 for cashier
    public static final FoodQueue cashierQueue3 = new FoodQueue(QUEUE3_CAPACITY);// Queue 3 for cashier

    private static int burgersAvailability = Current_Burger_Stock;// Remaining burgers in stock
    // Waiting List
    private static final CircularQueue<Customer> waitingList = new CircularQueue<>(QUEUE1_CAPACITY + QUEUE2_CAPACITY + QUEUE3_CAPACITY);

    // Circular queue to hold customers on the waiting list
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t*****  FOODIES FAVE BURGER CENTER  *****");
            System.out.println("------------------------------------------------------------------------------------");
            System.out.println("\t\t\t\t\t\t\t\tPLEASE SELECT AN OPTION");
            System.out.println("\t\t\t\t\t\t\t\t100 or VFQ: view all Queues.");
            System.out.println("\t\t\t\t\t\t\t\t101 or VEQ: View all Empty Queues.");
            System.out.println("\t\t\t\t\t\t\t\t102 or ACQ: Add customer to a Queue.");
            System.out.println("\t\t\t\t\t\t\t\t103 or RCQ: Remove a customer from Queue.(From a specific location)");
            System.out.println("\t\t\t\t\t\t\t\t104 or PCQ: Remove a served customer.");
            System.out.println("\t\t\t\t\t\t\t\t105 or VCS: View Customers Sorted in alphabetical order (Do not use library sort routine)");
            System.out.println("\t\t\t\t\t\t\t\t106 or SPD: Store Program Data into files");
            System.out.println("\t\t\t\t\t\t\t\t107 or LPD: Load Program Data from file.");
            System.out.println("\t\t\t\t\t\t\t\t108 or STK: View Remaining burgers Stock.");
            System.out.println("\t\t\t\t\t\t\t\t109 or AFS: Add burgers to Stock.");
            System.out.println("\t\t\t\t\t\t\t\t110 or IFQ: View Income of All Queues.");
            System.out.println("\t\t\t\t\t\t\t\t999 or EXT: Exit the Program.");
            System.out.println("------------------------------------------------------------------------------------");
            // Prompt user for choice
            System.out.print("Enter Your Choice: ");

            String input = scanner.nextLine();

            input = input.trim().toLowerCase();

            switch (input) {
                case "100":
                case "vfq":
                    viewAllQueues();// Calls the method to view all queues
                    break;
                case "101":
                case "veq":
                    viewEmptyQueues();// Calls the method to view empty queues
                    break;
                case "102":
                case "acq":
                    addCustomer(scanner);
                    break;
                case "103":
                case "rcq":
                    removeCustomer(scanner);
                    break;
                case "104":
                case "pcq":
                    removeServedCustomer();
                    break;
                case "105":
                case "vcs":
                    viewCustomersSorted();
                    break;
                case "106":
                case "spd":
                    storeProgramData();
                    break;
                case "107":
                case "lpd":
                    loadData();
                    break;
                case "108":
                case "stk":
                    viewRemainingStock();
                    break;
                case "109":
                case "afs":
                    addBurgersToStock(scanner);
                    break;
                case "110":
                case "ifq":
                    printIncome();
                    break;
                case "999":
                case "ext":
                    System.out.println("Thank you for choosing us. We'd love to see you Again. Have a Wonderful DAY!!!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // Prints all the queues and their current status
    private static void viewAllQueues() {
        System.out.println();
        System.out.println("\t\t\t\t\t\t*****************");
        System.out.println("\t\t\t\t\t\t*   Cashiers    *");
        System.out.println("\t\t\t\t\t\t*****************");

        System.out.println("\t\t\t\t\t\tCashier 1: " + cashierQueue1);// Print the contents of cashierQueue1
        System.out.println("\t\t\t\t\t\tCashier 2: " + cashierQueue2);// Print the contents of cashierQueue2
        System.out.println("\t\t\t\t\t\tCashier 3: " + cashierQueue3);// Print the contents of cashierQueue3
        System.out.println();
        System.out.println("\t\t\t\t\t\tO-Occupied  X– Not Occupied");
    }

    private static void viewEmptyQueues() {
        System.out.println("Empty Queues:");
        // Print "Cashier 1" if cashierQueue1 is empty
        if (cashierQueue1.isEmpty()) {
            System.out.println("Cashier 1 is Empty");
        }
        // Print "Cashier 2" if cashierQueue1 is empty
        if (cashierQueue2.isEmpty()) {
            System.out.println("Cashier 2 is Empty");
        }
        // Print "Cashier 3" if cashierQueue1 is empty
        if (cashierQueue3.isEmpty()) {
            System.out.println("Cashier 3 is Empty");
        }
    }

    private static void storeProgramData() {
        try {
            FileWriter writer = new FileWriter("program_data.txt");
            writer.write(cashierQueue1.getDataString("QUEUE1:"));
            writer.write(cashierQueue2.getDataString("QUEUE2:"));
            writer.write(cashierQueue3.getDataString("QUEUE3:"));
            writer.close();
            System.out.println("Program data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing program data: " + e.getMessage());
        }
    }


    private static void loadData() {
        System.out.println("---Loading Data---");
        try {
            FileReader fileReader = new FileReader("program_data.txt"); // Create a FileReader object to read from the file "program_data.txt"
            BufferedReader bufferedReader = new BufferedReader(fileReader); // Create a BufferedReader object to read lines from the FileReader
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line); // Print each line read from the file
            }

            bufferedReader.close(); // Close the BufferedReader
            System.out.println("Data Loaded Successfully!");
        } catch (IOException e) {
            System.out.println("ERROR while loading data");
        }
    }


    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter customer first name: ");
        String firstName = scanner.nextLine();

        // Validate first name
        while (!isStringOnly(firstName)) {
            System.out.println("Invalid input. The customer name cannot contain INTEGERS. Please re-enter!!");
            System.out.print("Enter customer first name: ");
            firstName = scanner.nextLine();
        }

        System.out.print("Enter customer last name: ");
        String lastName = scanner.nextLine();

        // Validate last name
        while (!isStringOnly(lastName)) {
            System.out.println("Invalid input. The customer name cannot contain INTEGERS. Please re-enter!!");
            System.out.print("Enter customer last name: ");
            lastName = scanner.nextLine();
        }

        int burgersRequired;
        while (true) {
            System.out.print("Enter the number of burgers required: ");
            String burgersInput = scanner.nextLine();

            try {
                burgersRequired = Integer.parseInt(burgersInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        Customer customer = new Customer(firstName, lastName, burgersRequired);
        FoodQueue shortestQueue = getShortestQueue();

        // Add customer to the shortest queue with available capacity
        if (shortestQueue != null && shortestQueue.getSize() < shortestQueue.getCapacity()) {
            shortestQueue.addCustomer(customer);
            System.out.println("Customer " + customer.getFullName() + " added to Cashier " + shortestQueue.getCashierNumber() + ".");
        } else {
            // If no queue has available capacity, add customer to the waiting list if it's not full
            if (!waitingList.isFull()) {
                waitingList.enqueue(customer);
                System.out.println("All cashiers are full. Added customer " + customer.getFullName() + " to the waiting list.");
            } else {
                // If waiting list is also full, customer cannot be added
                System.out.println("All cashiers and waiting list are full. Customer not added.");
            }
        }

        // Check stock availability and display warnings
        if (burgersAvailability <= 10) {
            System.out.println("Warning: Low stock! Remaining burgers: " + burgersAvailability);
        }
        if (burgersAvailability == 0) {
            System.out.println("Unfortunately!!! We are out of stock for burgers at the moment.");
        }
    }

    // Checks if a given input consists of only letters (alphabets)
    private static boolean isStringOnly(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }


    private static FoodQueue getShortestQueue() {
        FoodQueue shortestQueue = null;

        // Check if cashierQueue1 has available capacity and assign it as the current shortestQueue if it is
        if (cashierQueue1.getSize() < cashierQueue1.getCapacity()) {
            shortestQueue = cashierQueue1;
        }

        // Check if cashierQueue2 has available capacity and is shorter than the current shortestQueue (if any), then assign it as the new shortestQueue
        if (cashierQueue2.getSize() < cashierQueue2.getCapacity() && (shortestQueue == null || cashierQueue2.getSize() < shortestQueue.getSize())) {
            shortestQueue = cashierQueue2;
        }

        // Check if cashierQueue3 has available capacity and is shorter than the current shortestQueue (if any), then assign it as the new shortestQueue
        if (cashierQueue3.getSize() < cashierQueue3.getCapacity() && (shortestQueue == null || cashierQueue3.getSize() < shortestQueue.getSize())) {
            shortestQueue = cashierQueue3;
        }

        return shortestQueue; // Return the shortestQueue found (can be null if all queues are full)
    }


    private static void removeCustomer(Scanner scanner) {
        System.out.print("Enter cashier number (1, 2, or 3): ");
        int cashierNumber = scanner.nextInt();
        scanner.nextLine();

        FoodQueue selectedCashier = null;
        switch (cashierNumber) {
            case 1:
                selectedCashier = cashierQueue1; // Assign the selectedCashier as cashierQueue1
                break;
            case 2:
                selectedCashier = cashierQueue2; // Assign the selectedCashier as cashierQueue2
                break;
            case 3:
                selectedCashier = cashierQueue3; // Assign the selectedCashier as cashierQueue3
                break;
            default:
                System.out.println("Invalid cashier number. Customer not removed.");
                return; // Exit the method if the cashier number is invalid
        }

        if (selectedCashier.isEmpty()) {
            System.out.println("Cashier " + cashierNumber + " is already empty. No customers to remove.");
        } else {
            System.out.print("Enter customer index to remove (0 to " + (selectedCashier.getSize() - 1) + "): ");
            int customerIndex = scanner.nextInt();
            scanner.nextLine();

            if (selectedCashier.isValidIndex(customerIndex)) {
                Customer removedCustomer = selectedCashier.removeCustomer(customerIndex);
                System.out.println("Customer " + removedCustomer.getFullName() + " removed from Cashier " + cashierNumber + ".");
            } else {
                System.out.println("Invalid customer index. Customer not removed.");
            }
        }
    }


    private static void removeServedCustomer() {
        if (!cashierQueue1.isEmpty()) {
            // Remove customer from Cashier 1
            Customer removedCustomer = cashierQueue1.removeCustomer(0);
            // Reduce burger stock
            reduceBurgerStock(removedCustomer.getBurgersRequired());
            System.out.println("Customer " + removedCustomer.getFullName() + " removed from Cashier 1.");
        } else if (!cashierQueue2.isEmpty()) {
            // Remove customer from Cashier 2
            Customer removedCustomer = cashierQueue2.removeCustomer(0);
            // Reduce burger stock
            reduceBurgerStock(removedCustomer.getBurgersRequired());
            System.out.println("Customer " + removedCustomer.getFullName() + " removed from Cashier 2.");
        } else if (!cashierQueue3.isEmpty()) {
            // Remove customer from Cashier 3
            Customer removedCustomer = cashierQueue3.removeCustomer(0);
            // Reduce burger stock
            reduceBurgerStock(removedCustomer.getBurgersRequired());
            System.out.println("Customer " + removedCustomer.getFullName() + " removed from Cashier 3.");
        } else {
            // No customers in the cashier queues
            System.out.println("All cashiers and waiting list are empty. No customers to remove.");
            return;
        }

        // Check if there are customers in the waiting list
        if (!waitingList.isEmpty()) {
            // Get the next customer from the waiting list
            Customer nextCustomer = waitingList.dequeue();
            // Get the shortest cashier queue
            FoodQueue shortestQueue = getShortestQueue();
            if (shortestQueue != null) {
                // Add the next customer to the shortest queue
                shortestQueue.addCustomer(nextCustomer);
                System.out.println("Next customer from the waiting list, " + nextCustomer.getFullName() + ", added to Cashier " + shortestQueue.getCashierNumber() + ".");
            } else {
                System.out.println("All cashiers are full. Next customer from the waiting list not added.");
            }

            // Reduce burger stock
            reduceBurgerStock(nextCustomer.getBurgersRequired());
            System.out.println("Customer " + nextCustomer.getFullName() + " removed from the waiting list.");
        }

        // Check burger stock
        if (burgersAvailability <= 10) {
            System.out.println("Warning: Low stock! Remaining burgers: " + burgersAvailability);
        }
        if (burgersAvailability == 0) {
            System.out.println("Unfortunately!!! We are out of stock for burgers at the moment.");
        }
    }


    private static void reduceBurgerStock(int amount) {
        // Check if the available burger stock is greater than or equal to the specified amount
        if (burgersAvailability >= amount) {
            // Reduce the available burger stock by the specified amount
            burgersAvailability -= amount;
        } else {
            // Print a message indicating that there are not enough burgers in stock
            System.out.println("Cannot remove a customer. No burgers in stock.");
        }
    }


    private static void viewCustomersSorted() {
        ArrayList<Customer> allCustomers = new ArrayList<>(); // Create an ArrayList to store all customers

        allCustomers.addAll(cashierQueue1.getCustomers()); // Add customers from cashierQueue1 to allCustomers
        allCustomers.addAll(cashierQueue2.getCustomers()); // Add customers from cashierQueue2 to allCustomers
        allCustomers.addAll(cashierQueue3.getCustomers()); // Add customers from cashierQueue3 to allCustomers

        if (allCustomers.isEmpty()) {
            System.out.println("Queue is empty."); // Print a message if allCustomers is empty
        } else {
            sortCustomers(allCustomers); // Sort allCustomers in alphabetical order
            System.out.println("All Customers Sorted in alphabetical order:");
            for (Customer customer : allCustomers) {
                System.out.println(customer.getFullName()); // Print the full name of each customer in allCustomers
            }
        }
    }


    private static void sortCustomers(ArrayList<Customer> customers) {
        // Sort the customers list using a custom comparator
        Collections.sort(customers, new Comparator<Customer>() {
            public int compare(Customer c1, Customer c2) {
                // Compare the full names of the customers and return the result
                return c1.getFullName().compareTo(c2.getFullName());
            }
        });
    }


    private static void viewRemainingStock() {
        System.out.println("Remaining burgers stock: " + burgersAvailability);
    }

    private static void addBurgersToStock(Scanner scanner) {
        System.out.print("Enter the number of burgers to add: ");
        int burgersToAdd = scanner.nextInt();
        scanner.nextLine();

        burgersAvailability += burgersToAdd; // Increase the available burger stock by the specified amount
        System.out.println("Added " + burgersToAdd + " burgers to the stock."); // Print a message indicating the number of burgers added
        System.out.println("New stock: " + burgersAvailability); // Print the updated stock availability
    }


    private static void printIncome() {
        double income1 = cashierQueue1.getIncome(BURGER_PRICE); // Calculate the income of cashierQueue1
        double income2 = cashierQueue2.getIncome(BURGER_PRICE); // Calculate the income of cashierQueue2
        double income3 = cashierQueue3.getIncome(BURGER_PRICE); // Calculate the income of cashierQueue3

        System.out.println("\t\t\t\t\t\t*****************");
        System.out.println("\t\t\t\t\t\tIncome of cashiers"); // Print a header for the income of each queue
        System.out.println("\t\t\t\t\t\t*****************");
        System.out.println("\t\t\t\t\t\tCashier 1:Rs. " + income1); // Print the income of cashierQueue1
        System.out.println("\t\t\t\t\t\tCashier 2:Rs. " + income2); // Print the income of cashierQueue2
        System.out.println("\t\t\t\t\t\tCashier 3:Rs. " + income3); // Print the income of cashierQueue3
    }
}

class CircularQueue<T> {
    private Object[] elements; // Array to store the elements of the circular queue
    private int front; // Index of the front element
    private int rear; // Index of the rear element
    private int capacity; // Maximum capacity of the circular queue
    private int size; // Current size of the circular queue

    public CircularQueue(int capacity) {
        this.capacity = capacity; // Set the maximum capacity of the circular queue
        this.elements = new Object[capacity]; // Create an array with the specified capacity to hold the elements
        this.front = -1; // Initialize the front index as -1
        this.rear = -1; // Initialize the rear index as -1
        this.size = 0; // Initialize the size as 0
    }


    public boolean isEmpty() {
        return size == 0; // Check if the size of the circular queue is 0
    }

    public boolean isFull() {
        return size == capacity; // Check if the size of the circular queue is equal to its capacity
    }

    public int getSize() {
        return size; // Return the current size of the circular queue
    }

    public void enqueue(T item) {
        if (isFull()) {
            throw new IllegalStateException("CircularQueue is full."); // Throw an exception if the circular queue is already full
        }

        if (isEmpty()) {
            front = 0; // Set the front index to 0 if the circular queue is empty
        }

        rear = (rear + 1) % capacity; // Update the rear index to the next position considering the circular nature
        elements[rear] = item; // Store the item at the rear index
        size++; // Increment the size of the circular queue
    }

    public T dequeue() {
        if (isEmpty()) {
            return null; // Return null if the circular queue is empty
        }

        T item = (T) elements[front]; // Get the item at the front index
        elements[front] = null; // Set the element at the front index to null

        if (front == rear) {
            front = -1; // Reset the front index to -1 if the circular queue becomes empty after dequeueing
            rear = -1; // Reset the rear index to -1 if the circular queue becomes empty after dequeueing
        } else {
            front = (front + 1) % capacity; // Update the front index to the next position considering the circular nature
        }

        size--; // Decrement the size of the circular queue
        return item; // Return the dequeued item
    }
}