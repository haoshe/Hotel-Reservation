package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

public class CustomerService {

    Collection<Customer> customers = new HashSet<Customer>();
    private static CustomerService customerService;


    private CustomerService(){
    }

    public static CustomerService getInstance(){
        if(Objects.isNull(customerService)){
            customerService = new CustomerService();
        }
        return customerService;
    }


    public void addCustomer(String email, String firstname, String lastname){
       Customer newCustomer = new Customer(email,firstname,lastname);
       customers.add(newCustomer);
    }

    //retrieve a customer from the map by email
    public Customer getCustomer(String customerEmail){
       for(Customer customer : customers){
           if(customer.getEmail().equals(customerEmail)){
               return customer;
           }
           return null;
       }
       return null;
    }

   public Collection<Customer> getAllCustomers(){
        return customers;
   }


}
