package org.bank.model;

public class Customer  {
    private String username;
    private String password;
    private String customerName;

    private long contact;
    private String status;
    private int customerId;


    //Constructors


    public Customer(String username, String password, String customerName, long contact, String status, int customerId) {
        this.username = username;
        this.password = password;
        this.customerName = customerName;



        this.contact = contact;
        this.status = status;
        this.customerId = customerId;

    }

    public Customer(String username, String password, String customerName,  long contact, String status) {
        this.username = username;
        this.password = password;
      this.customerName = customerName;
        this.contact = contact;
        this.status = status;
    }
    public Customer(){}

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

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
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

