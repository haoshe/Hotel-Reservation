package model;

import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String email, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
//        String emailRegEx = "^(.+)@(.+).com$";
//        Pattern pattern = Pattern.compile(emailRegEx);
//        if(!pattern.matcher(email).matches()){
//            throw new IllegalArgumentException("please fill in valid email address");
//        }
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "First Name: " + firstName + ", Last Name: " + lastName + ", Email : " + email + "\n";
    }
}
