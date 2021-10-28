package model;


import utilities.Utilities;
import java.util.Objects;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String email, String firstName, String lastName) {
        if(!Utilities.isEmailValid(email)){
            throw new IllegalArgumentException("Email is not valid");
        }else{
            this.email = email;
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return firstName.equals(customer.firstName) && lastName.equals(customer.lastName) && email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "First Name: " + firstName + ",  Last Name: " + lastName + ",  Email : " + email + "\n";
    }
}
