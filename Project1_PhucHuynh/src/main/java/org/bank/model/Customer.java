package org.bank.model;
/**
 * Customer model class is created to give provide info about bank customers and control access of the customer data since
 * the instance variables  in customers account are encapsulated
 */

public class Customer {
    private String username;
    private String password;
    private String customerName;

    private String contact;
    private String status;
    private int customerId;


    //Constructors
    public Customer(String username, String password, String customerName, String contact, String status, int customerId) {
        this.username = username;
        this.password = password;
        this.customerName = customerName;


        this.contact = contact;
        this.status = status;
        this.customerId = customerId;

    }

    public Customer(String username, String password, String customerName, String contact, String status) {
        this.username = username;
        this.password = password;
        this.customerName = customerName;
        this.contact = contact;
        this.status = status;
    }



    public Customer() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", customerName='" + customerName + '\'' +
                ", contact=" + contact +
                ", status='" + status + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}

