import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;

public class FoodiesBurgerCenter_Array{
    // Define constants for current burger stock and burgers per order
    private static final int Current_Burger_Stock = 50;
    private static final int Burgers_per_Order = 5;
    // Define arrays to store customer names in the queues
    private static String[] cashierQueue1 = new String[2];
    private static String[] cashierQueue2 = new String[3];
    private static String[] cashierQueue3 = new String[5];
    // Initialize variable to track current burger stock
    private static int burgersAvailability = Current_Burger_Stock;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu of options
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
            System.out.println("\t\t\t\t\t\t\t\t999 or EXT: Exit the Program.");
            System.out.println("------------------------------------------------------------------------------------");
            // Prompt user for choice
            System.out.print("Enter Your Choice: ");
            // Prompt the user to enter their choice from the menu of options

            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "100":
                case "vfq":
                    ViewAllQueues();
                    break;
                case "101":
                case "veq":
                    ViewEmptyQueues();
                    break;
                case "102":
                case "acq":
                    AddCustomer(scanner);
                    break;
                case "103":
                case "rcq":
                    RemoveCustomer(scanner);
                    break;
                case "104":
                case "pcq":
                    RemoveServedCustomer();
                    break;
                case "105":
                case "vcs":
                    ViewCustomersSorted();
                    break;
                case "106":
                case "spd":
                    storeProgramData();
                    break;
                case "107":
                case "lpd":
                    LoadData();
                    break;
                case "108":
                case "stk":
                    ViewRemainingStock();
                    break;
                case "109":
                case "afx":
                    AddBurgersToStock(scanner);
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
    // This method displays the queues of all cashiers along with icons indicating if the queue is empty or not.
    private static void ViewAllQueues() {
        System.out.println("\t\t\t\t\t\t*****************");
        System.out.println("\t\t\t\t\t\t*   CASHIERS    *");
        System.out.println("\t\t\t\t\t\t*****************");
        System.out.println("\t\t\t\t\t\tCashier 1: " + Arrays.toString(getQueueWithIcons(cashierQueue1)));
        System.out.println("\t\t\t\t\t\tCashier 2: " + Arrays.toString(getQueueWithIcons(cashierQueue2)));
        System.out.println("\t\t\t\t\t\tCashier 3: " + Arrays.toString(getQueueWithIcons(cashierQueue3)));
    }

    // This method checks if a given queue is empty or not.
    private static boolean isEmptyQueue(String[] queue) {
        // Iterate through each element in the queue.
        for (String customer : queue) {
            if (customer != null) {
                // Check if the current element is not null (indicating the presence of a customer).
                return false;
            }
        }
        return true;
    }

    // This method displays the queues that are currently empty.
    private static void ViewEmptyQueues() {
        System.out.println("Empty Queues: ");
        if (isEmptyQueue(cashierQueue1)) {
            System.out.println("Cashier 1 is Empty");
        }
        if (isEmptyQueue(cashierQueue2)) {
            System.out.println("Cashier 2 is Empty ");
        }
        if (isEmptyQueue(cashierQueue3)) {
            System.out.println("Cashier 3 is Empty ");
        }
        if (!isEmptyQueue(cashierQueue1) && !isEmptyQueue(cashierQueue2) && !isEmptyQueue(cashierQueue3)) {
            System.out.println("No Empty Queues to Show... At least one customer in each Queue.");
        }
    }

    // This method returns a queue with icons indicating if a customer is present in the queue or not.
    // This method takes in an array of Strings called "queue" as a parameter.
    private static String[] getQueueWithIcons(String[] queue) {
        // A new array of Strings called "queueWithSymbols" is created with the same length as the "queue" array.
        String[] queueWithSymbols = new String[queue.length];
        // A for loop is used to iterate through each element in the "queue" array.
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == null) {
                // If the current element in the "queue" array is null, an "X" is added to the corresponding index in the "queueWithSymbols" array.
                queueWithSymbols[i] = "X";
            } else {
                // If the current element in the "queue" array is not null, an "O" is added to the corresponding index in the "queueWithSymbols" array.
                queueWithSymbols[i] = "O";
            }
        }
        // The "queueWithSymbols" array is returned.
        return queueWithSymbols;
    }
    // This method stores the program data to a file.
    private static void storeProgramData() {
        try {
            FileWriter writer = new FileWriter("program_data.txt");

            // Write the data for QUEUE1
            // Create a new array called queue1Array with the same length as the cashierQueue1 array.
            String[] queue1Array = new String[cashierQueue1.length];
            // Initialize a variable called queue1Count to keep track of the number of elements in the queue1Array.
            int queue1Count = 0;
            for (String customer : cashierQueue1) {
                // Iterate through each element in the cashierQueue1 array.
                if (customer != null) {
                    // Check if the current element is not null.
                    queue1Array[queue1Count] = customer;
                    // Assign the current element to the corresponding index in the queue1Array.
                    queue1Count++;
                    // Increment the queue1Count variable to keep track of the number of elements in the queue1Array.
                }
            }
            writer.write("QUEUE1:" + Arrays.toString(Arrays.copyOf(queue1Array, queue1Count)) + "\n");

            // Write the data for QUEUE2
            String[] queue2Array = new String[cashierQueue2.length];
            int queue2Count = 0;
            for (String customer : cashierQueue2) {
                if (customer != null) {
                    queue2Array[queue2Count] = customer;
                    queue2Count++;
                }
            }
            writer.write("QUEUE2:" + Arrays.toString(Arrays.copyOf(queue2Array, queue2Count)) + "\n");

            // Write the data for QUEUE3
            // Create a new array called queue3Array with the same length as the cashierQueue3 array.
            String[] queue3Array = new String[cashierQueue3.length];
            // Initialize a variable called queue3Count to keep track of the number of elements in the queue3Array.

            int queue3Count = 0;
            for (String customer : cashierQueue3) {
                // Iterate through each element in the cashierQueue3 array.
                if (customer != null) {
                    // Check if the current element is not null.
                    queue3Array[queue3Count] = customer;
                    // Assign the current element to the corresponding index in the queue3Array.
                    queue3Count++;
                    // Increment the queue3Count variable to keep track of the number of elements in the queue3Array.
                }
            }
            writer.write("QUEUE3:" + Arrays.toString(Arrays.copyOf(queue3Array, queue3Count)) + "\n");

            // Write the data for current_burger_stock
            writer.write("Current Burger Stock:" + burgersAvailability + "\n");

            // Close the FileWriter object called "writer".
            writer.close();

            // Print a message to the console indicating that the program data has been stored successfully.
            System.out.println("Program data stored successfully.");
        } catch (IOException e) {
            // If an IOException is thrown, print an error message to the console with the specific error message provided by the exception.
            System.out.println("Error storing program data: " + e.getMessage());
        }
    }
    // This method is used to load data from a text file
    private static void LoadData() {
        System.out.println("---Loading Data---");
        try {
            // FileReader is used to read data from a file
            FileReader fileReader = new FileReader("program_data.txt");
            // BufferedReader is used to read data from a character input stream
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            // Reading data line by line until the end of the file is reached
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            // Closing the BufferedReader
            bufferedReader.close();
            System.out.println("Data Loaded Successfully!");
            // This catch block handles any IOException that might occur while loading data
        } catch (IOException e) {
            // Printing an error message if an exception is caught
            System.out.println("ERROR while loading data");
        }
    }
    // This method is called "AddCustomer" and takes in a Scanner object called "scanner" as a parameter.
    private static void AddCustomer(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        // Check if the customer name is valid
        if (customerName == null || customerName.isEmpty() || customerName.trim().isEmpty()) {
            System.out.println("Missing Customer Name. Please provide a name before proceeding....");
            return;
        }

        // Check if the customer name contains numeric characters
        if (ContainsNumberValues(customerName)) {
            System.out.println("The customer name cannot contain INTEGERS. Please re-enter!!");
            return;
        }

        System.out.print("Which cashier would you like to handle your purchase? Select a Cashier number (1, 2, or 3): ");
        int preferredCashier = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Select the appropriate cashier queue based on the user's choice
        String[] selectedCashier = null;
        int maxQueueSize = 0;
        switch (preferredCashier) {
            case 1:
                selectedCashier = cashierQueue1;
                maxQueueSize = 2;
                break;
            case 2:
                selectedCashier = cashierQueue2;
                maxQueueSize = 3;
                break;
            case 3:
                selectedCashier = cashierQueue3;
                maxQueueSize = 5;
                break;
            default:
                System.out.println("The cashier you have chosen is not valid. Please choose a given cashier number");
                return;
        }

        // Check if there is an available index in the selected cashier queue
        int availableIndex = FindAvailableIndex(selectedCashier);
        if (availableIndex != -1) {
            // Add the customer to the selected cashier queue
            selectedCashier[availableIndex] = customerName;
            System.out.println("Customer " + customerName + " added to Cashier " + preferredCashier + ".");
        } else {
            System.out.println("Cashier " + preferredCashier + " is full. Sorry, the selected cashier is not available. Please choose another one. Customer is not added.");
        }

        // Check if the number of available burgers is low or out of stock
        if (burgersAvailability <= 10) {
            System.out.println("Warning: Low stock! Remaining burgers: " + burgersAvailability);
        }
        if (burgersAvailability == 0) {
            System.out.println("Unfortunately!!! We are out of stock for burgers at the moment.");
        }
    }
    private static boolean ContainsNumberValues(String text) {
        // Iterate through each character in the text
        for (char c : text.toCharArray()) {
            // Check if the character is a digit
            if (Character.isDigit(c)) {
                return true;// If a digit is found, return true
            }
        }
        return false;
    }
    private static int FindAvailableIndex(String[] queue) {
        // Iterate through each index in the queue array
        for (int i = 0; i < queue.length; i++) {
            // Check if the current index is null
            if (queue[i] == null) {
                return i;// If an available index is found, return it
            }
        }
        return -1;
    }
    // This method is called "RemoveCustomer" and takes in a Scanner object called "scanner" as a parameter.
    private static void RemoveCustomer(Scanner scanner) {
        System.out.print("Enter cashier number (1, 2, or 3): ");
        // The user's input is read as an integer called "cashierNumber".
        int cashierNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
// A String array called "selectedCashier" is initialized to null.
        String[] selectedCashier = null;
// A switch statement is used to set the "selectedCashier" variable based on the user's chosen cashier.
        switch (cashierNumber) {
            case 1:
                selectedCashier = cashierQueue1;
                break;
            case 2:
                selectedCashier = cashierQueue2;
                break;
            case 3:
                selectedCashier = cashierQueue3;
                break;
            default:
                System.out.println("Invalid cashier number. Customer not removed. Please check the cashier number...");
                return;
        }

// An if statement is used to check if the "selectedCashier" array is empty.
        if (isEmptyQueue(selectedCashier)) {
            // If the "selectedCashier" array is empty, this line prints a message to the console indicating that there are no customers to remove.
            System.out.println("Cashier " + cashierNumber + " is already empty. No customers to remove.");
        } else {
            // If the "selectedCashier" array is not empty, this line prints a message to the console asking the user to enter a customer index to remove.
            System.out.print("Enter customer index to remove (0 to " + (selectedCashier.length - 1) + "): ");
            // The user's input is read as an integer called "customerIndex".
            int customerIndex = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
// An if statement is used to check if the "customerIndex" is a valid index in the "selectedCashier" array and if there is a customer at that index.
            if (customerIndex >= 0 && customerIndex < selectedCashier.length && selectedCashier[customerIndex] != null) {
                // If the "customerIndex" is valid and there is a customer at that index, the customer is removed from the "selectedCashier" array and the removed customer's name is stored in a String called "removedCustomer".
                String removedCustomer = selectedCashier[customerIndex];
                selectedCashier[customerIndex] = null;
                // This line prints a message to the console indicating that the customer has been removed from the chosen cashier's queue.
                System.out.println("Customer " + removedCustomer + " removed from Cashier " + cashierNumber + ".");
            } else {
                // If the "customerIndex" is not valid or there is no customer at that index, this line prints an error message to the console and the method returns without removing a customer.
                System.out.println("Invalid customer index. Customer can not be removed.");
            }
        }
    }
    // This method is called "RemoveServedCustomer" and does not take in any parameters.
    private static void RemoveServedCustomer() {
        // Check if Cashier 1 has customers
        if (!isEmptyQueue(cashierQueue1)) {
            String removedCustomer = cashierQueue1[0]; // Get the first customer in Cashier 1
            shiftQueueLeft(cashierQueue1); // Shift the queue to the left to remove the served customer
            reduceBurgerStock(Burgers_per_Order); // Reduce the burger stock by the number of burgers per order
            System.out.println("Customer " + removedCustomer + " removed from Cashier 1.");

            // Check if the burger stock is low
            if (burgersAvailability <= 10) {
                System.out.println("Warning: Low stock! Remaining burgers: " + burgersAvailability);
            }

            // Check if the burger stock is empty
            if (burgersAvailability == 0) {
                System.out.println("Burger stock is empty...");
            }
        }
        // Check if Cashier 2 has customers
        else if (!isEmptyQueue(cashierQueue2)) {
            String removedCustomer = cashierQueue2[0]; // Get the first customer in Cashier 2
            shiftQueueLeft(cashierQueue2); // Shift the queue to the left to remove the served customer
            reduceBurgerStock(Burgers_per_Order); // Reduce the burger stock by the number of burgers per order
            System.out.println("Customer " + removedCustomer + " removed from Cashier 2.");

            if (burgersAvailability <= 10) {
                System.out.println("Warning: Low stock! Remaining burgers: " + burgersAvailability);
            }

            if (burgersAvailability == 0) {
                System.out.println("Burger stock is empty...");
            }
        }
        // Check if Cashier 3 has customers
        else if (!isEmptyQueue(cashierQueue3)) {
            String removedCustomer = cashierQueue3[0]; // Get the first customer in Cashier 3
            shiftQueueLeft(cashierQueue3); // Shift the queue to the left to remove the served customer
            reduceBurgerStock(Burgers_per_Order); // Reduce the burger stock by the number of burgers per order
            System.out.println("Customer " + removedCustomer + " removed from Cashier 3.");

            if (burgersAvailability <= 10) {
                System.out.println("Warning: Low stock! Remaining burgers: " + burgersAvailability);
            }

            if (burgersAvailability == 0) {
                System.out.println("Burger stock is empty...");
            }
        }
        else {
            System.out.println("All cashiers are empty. No customers to remove.");
        }
    }

    private static void reduceBurgerStock(int amount) {
        // Check if there are enough burgers in stock
        if (burgersAvailability >= amount) {
            burgersAvailability -= amount; // Reduce the burger stock by the specified amount
        } else {
            System.out.println("Cannot remove a customer. No burgers in stock.");
        }
    }

    // This method is called "shiftQueueLeft" and takes in a String array called "queue" as a parameter.
    // A for loop is used to iterate through each element in the "queue" array except for the last element.
    private static void shiftQueueLeft(String[] queue) {
        for (int i = 0; i < queue.length - 1; i++) {
            // Loop through the entire array except for the last element
            queue[i] = queue[i + 1];// Shift each element one index to the left
        }
        queue[queue.length - 1] = null; // Set the last element to null to remove any references to the previous first element
    }
    // This method creates an array of all customers in all queues, sorts them alphabetically, and prints them out
    private static void ViewCustomersSorted() {
        String[] allCustomers = new String[cashierQueue1.length + cashierQueue2.length + cashierQueue3.length];
        // Create an array to hold all customers in all queues
        int index = 0;// Keep track of the number of customers added to the array so far
        for (String customer : cashierQueue1) {// Loop through all customers in queue 1
            if (customer != null) {// If the customer is not null (i.e., there is a customer in that position in the queue)
                allCustomers[index++] = customer;// Add the customer to the array and increment the index
            }
        }
        for (String customer : cashierQueue2) {// Loop through all customers in queue 2
            if (customer != null) {// If the customer is not null
                allCustomers[index++] = customer;// Add the customer to the array and increment the index
            }
        }
        for (String customer : cashierQueue3) {// Loop through all customers in queue 3
            if (customer != null) { // If the customer is not null
                allCustomers[index++] = customer;// Add the customer to the array and increment the index
            }
        }

        if (index == 0) {// If there are no customers in any of the queues
            System.out.println("No customers in the queues.");
        } else {
            sortCustomers(allCustomers, index);// Sort the array of customers alphabetically
            System.out.println("Customers Sorted in Alphabetical Order:");
            for (int i = 0; i < index; i++) {// Loop through all customers in the array
                System.out.println(allCustomers[i]);
            }
        }
    }
    // This method sorts an array of customers in alphabetical order
    private static void sortCustomers(String[] customers, int size) {
        for (int i = 0; i < size - 1; i++) {// Loop through the array from the beginning, up to the second-to-last element
            for (int j = 0; j < size - i - 1; j++) {// Loop through the array from the beginning, up to the second-to-last element minus the number of times we've already looped through the array
                if (customers[j].compareTo(customers[j + 1]) > 0) {// If the current element is greater than the next element in the array
                    String temp = customers[j];// Swap the current element with the next element in the array
                    customers[j] = customers[j + 1];
                    customers[j + 1] = temp;
                }
            }
        }
    }
    // This method prints out the remaining stock of burgers
    private static void ViewRemainingStock() {
        System.out.println("Remaining Burgers Stock: " + burgersAvailability);
    }
    // This method adds burgers to the stock
    private static void AddBurgersToStock(Scanner scanner) {
        System.out.print("Enter the number of Burgers to Add: ");
        int burgersToAdd = scanner.nextInt();// Get the number of burgers to add from the user
        scanner.nextLine(); // Consume the newline character

        burgersAvailability += burgersToAdd;// Add the burgers to the stock
        System.out.println("Added " + burgersToAdd + " burgers to the Stock. New stock is : " + burgersAvailability);
        // Print out a message indicating how many burgers were added to the stock, and what the new stock level is
    }
}
