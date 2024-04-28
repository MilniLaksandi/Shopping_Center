
public class Customer {
    private String firstName; // First name of the customer
    private String lastName; // Last name of the customer
    private int burgersRequired; // Number of burgers required by the customer

    // Constructor
    public Customer(String firstName, String lastName, int burgersRequired) {
        this.firstName = firstName; // Initialize the first name of the customer
        this.lastName = lastName; // Initialize the last name of the customer
        this.burgersRequired = burgersRequired; // Initialize the number of burgers required by the customer
    }


    public String getFullName() {
        return firstName + " " + lastName; // Concatenate the first name and last name to form the full name of the customer
    }

    public int getBurgersRequired() {
        return burgersRequired; // Return the number of burgers required by the customer
    }

}