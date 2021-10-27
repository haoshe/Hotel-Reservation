package service;

import model.Customer;

import java.util.*;

public class CustomerService {

    Map<String,Customer> customers = new HashMap<>();
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
       customers.put(email,newCustomer);
    }

    //retrieve a customer from the map by email
    public Customer getCustomer(String customerEmail){
       if(customers.containsKey(customerEmail)){
               return customers.get(customerEmail);
       }
       return null;
    }

   public Collection<Customer> getAllCustomers(){
        Collection<Customer> allCustomers = customers.values();
        return allCustomers;
   }


}
