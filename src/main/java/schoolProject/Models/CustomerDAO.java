package schoolProject.Models;

import java.util.List;

public interface CustomerDAO {

    // C
    public void createCustomer(Customer customer);
    
    // R
    public List<Customer> readCustomers();
    
    // U
    public void updateCustomer(Customer customer);
    
    // D
    public void deleteCustomer(Customer customer);
}