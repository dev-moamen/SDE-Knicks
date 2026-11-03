package schoolProject.Models;

public class CustomerManager {

    public static CustomerDAO cDAO;

    public static void setDAO(CustomerDAO cDAO) {
        CustomerManager.cDAO = cDAO; 
    }

    public static void manageCustomers() {
        Customer Ahmed = new Customer("Ahmed", "ahmed.com", Customer.GenderType.Male, 15);
        cDAO.createCustomer(Ahmed);
    }
}