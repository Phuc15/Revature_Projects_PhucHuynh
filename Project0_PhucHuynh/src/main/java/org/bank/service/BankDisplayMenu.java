package org.bank.service;

import org.bank.exeption.BankException;
import org.bank.model.BankAccount;
import org.bank.model.Customer;

import org.bank.service.implementation.CustomerServiceImplementation;
import org.bank.service.implementation.EmployeeFunctionImplementation;


import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankDisplayMenu {


    private EmployeeFunction employeeFunction = new EmployeeFunctionImplementation();
    private CustomerService customerService = new CustomerServiceImplementation();

    /*
     * displayMenu() that provide the UI for the users, customers,and employees to interact with and x the console when entering x
     * @return a string
     */

    public void displayMenu()  {
try {


    System.out.println("please select an option:");
    System.out.println("1. Log in as an existing customer");
    System.out.println("2. Log in as an employee");
    System.out.println("3. Sign up for a new customer account");
    System.out.println("x. Exiting the System");
    Scanner scanner = new Scanner(System.in);
    String option = scanner.next();
    //using switch statement to provide the options
    switch (option) {
        case "1":
            System.out.println("Emter your username:");
            String loginUsername = scanner.next();
            System.out.println("Enter your password:");
            String loginPassword = scanner.next();
            //validate the user name and password to see if they both exists in the same rwo in the database
            boolean validation = false;

            validation = customerService.validateAccount(loginUsername, loginPassword);

            if (validation == true) { // it will will load the displayCustomerMenu if validation is true
                displayCustomerMenu(loginUsername);
            } else {
                //letting the user know that its not valid
                System.out.println("Invalid username or password. Please try again");
            }
            displayMenu();
            break;
        case "2":
            /*
             * assume there is only one employee with username:erica12 & password:phucsonmy
             */

            System.out.println("Emter username:");
            String loginUsername1 = scanner.next();
            System.out.println("Enter password:");
            String loginPassword1 = scanner.next();
            if (loginUsername1.equalsIgnoreCase("erica12") && loginPassword1.equals("phucsonmy")) {
                displayEmployeeMenu();
            } else {
                System.out.println("Your username and password are invalid. Please try again!");
                displayMenu();
            }

            displayMenu();
            break;
        case "3":
            try {
                Customer newCustomer;
                String applicationStatus = "Pending";
                // ask for first name
                System.out.println("Enter your your full name: ");
                String customerName = scanner.next();
                System.out.println("Enter your contact");
                long contact = scanner.nextLong();
                System.out.println("Enter your username: ");
                String userName = scanner.next();
                System.out.println("Emter your password: ");
                String password = scanner.next();
                newCustomer = new Customer(userName, password, customerName, contact, applicationStatus);
                employeeFunction.registerForNewAccount(newCustomer);
                System.out.println("Congrats! Thanks for chosing ABC bank. Your account is pending to be approved.");
            } catch (BankException e) {
                System.out.println(e.getMessage());
            }
            displayMenu();
            break;
        default:
            if (!option.equalsIgnoreCase("x")) {
                System.out.println("The input is not valid. please re-enter");
                displayMenu();
            }
    }
}catch (BankException e){
    System.out.println(e);

}

    }

    //--------------------------Customer console display----------------------------

    public void displayCustomerMenu(String loginUsername) throws BankException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an option: ");
        System.out.println("1) Apply for a new bank account");
        System.out.println("2) View balance");
        System.out.println("3) Making a deposit");
        System.out.println("4) Withdrawing");
        System.out.println("5) Make a transfer");
        System.out.println("6) Accept pending transfer");
        System.out.println("7) Logout");
        int customerOption = scanner.nextInt();
        switch (customerOption) {
            case 7:
                displayMenu();
                break;
            case 1:

                boolean status = true;
                String checkingType = "Checking";
                String savingType = "Saving";
                while (status) {


                    System.out.println("Select an option: ");
                    System.out.println("1) Create a checking account.");
                    System.out.println("2) Create a saving amount");
                    System.out.println("3) Create a checking and a saving account");
                    int opt = scanner.nextInt();
                    if (opt == 1) {
                        System.out.println("Enter the amount:");
                        double amount = scanner.nextDouble();
                        if (amount < 0) {
                            System.out.println("Amount entered is invalid. Please enter a positive value.");
                            amount = scanner.nextDouble();
                        } else {
                            customerService.applyForBankAccount(loginUsername, checkingType, amount);
                            System.out.println("You successfully opened " + checkingType + " with the amount $" + amount);
                            status = false;
                        }

                    } else if (opt == 2) {
                        System.out.println("Enter the amount:");
                        double amountsav = scanner.nextDouble();
                        if (amountsav < 0) {
                            System.out.println("Amount entered is invalid. Please enter a positive value.");
                            amountsav = scanner.nextDouble();
                        } else {
                            customerService.applyForBankAccount(loginUsername, savingType, amountsav);
                            System.out.println("You successfully opened " + savingType + " with the amount $" + amountsav);
                            status = false;
                        }
                    } else if (opt == 3) {
                        System.out.println("Enter the checking account amount");
                        double checkingAmount = scanner.nextDouble();
                        if (checkingAmount < 0) {
                            System.out.println("Amount entered is invalid. Please enter a positive value.");
                            checkingAmount = scanner.nextDouble();

                        } else {
                            customerService.applyForBankAccount(loginUsername, checkingType, checkingAmount);
                            System.out.println("You successfully opened " + checkingType + " with the amount $+" + checkingAmount);

                        }

                        System.out.println("Enter the saving account amount");
                        double savingAmount = scanner.nextDouble();
                        if (savingAmount < 0) {
                            System.out.println("Amount entered is invalid. Please enter positive value.");
                            savingAmount = scanner.nextDouble();
                        } else {
                            customerService.applyForBankAccount(loginUsername, savingType, savingAmount);
                            System.out.println("You successfully opened " + savingType + " with the amount $" + savingAmount);
                        }
                        status = false;
                    } else {

                        status = true;
                    }

                    status = false;
                }
                displayCustomerMenu(loginUsername);
                break;
            case 2:
                try {

                    List<BankAccount> list = new ArrayList<>();
                    list = customerService.viewBankAccount(loginUsername);
                    System.out.println(loginUsername);
                    for (BankAccount a : list) {
                        System.out.println(a.getAccountType() + ": " + a.getBalance());
                    }
                } catch (BankException e) {
                    System.out.println(e.getMessage());
                }
                 displayCustomerMenu(loginUsername);
                break;
            case 3:
                try {
                    String checking = "Checking";
                    String saving = "Saving";
                    System.out.println("Please select an option:");
                    System.out.println("1) Deposit to your checking: ");
                    System.out.println("2) Deposit to your saving: ");
                    int choice = scanner.nextInt();
                    if (choice == 1) {
                        System.out.println("Enter an amount: ");
                        double depositAmount = scanner.nextDouble();
                        if (depositAmount >= 0) {
                            customerService.deposit(loginUsername, checking, depositAmount);
                            System.out.println("You successfully deposited $" + depositAmount + " to your " + checking + " account");
                        } else {
                            System.out.println("invalid amount. Please try again");
                        }
                    } else if (choice == 2) {
                        System.out.println("Enter an amount: ");
                        double depositSavingAmount = scanner.nextDouble();
                        if (depositSavingAmount >= 0) {
                            customerService.deposit(loginUsername, saving, depositSavingAmount);
                            System.out.println("You successfully deposited $" + depositSavingAmount + " to your " + saving + " account");
                        } else {
                            System.out.println("Invalid amount. Please try again");
                        }

                    } else {
                        System.out.println("Invalid input. Please re-select");
                    }
                } catch (BankException e) {
                    System.out.println(e);
                }
                 displayCustomerMenu(loginUsername);
                break;
            case 4:
                try {
                    String checking = "Checking";
                    String saving = "Saving";
                    System.out.println("Please select an option:");
                    System.out.println("1) Withdraw from your checking: ");
                    System.out.println("2) Withdraw from your saving: ");
                    int choice = scanner.nextInt();
                    if (choice == 1) {
                        System.out.println("Enter an amount: ");
                        double withdrawAmount = scanner.nextDouble();
                        if (withdrawAmount > 0) {
                            customerService.withdraw(loginUsername, checking, withdrawAmount);
                            System.out.println("You successfully withdrew $" + withdrawAmount + " from your " + checking + " account");
                        } else {
                            System.out.println("invalid amount. Please try again");
                        }
                    } else if (choice == 2) {
                        System.out.println("Enter an amount: ");
                        double withdrawSavingAmount = scanner.nextDouble();
                        if (withdrawSavingAmount >= 0) {
                            customerService.withdraw(loginUsername, saving, withdrawSavingAmount);
                            System.out.println("You successfully withdrew $" + withdrawSavingAmount + " from your " + saving + " account");
                        } else {
                            System.out.println("Invalid amount. Please try again");
                        }

                    } else {
                        System.out.println("Invalid input. Please re-select");
                    }
                } catch (BankException e) {
                    System.out.println(e);
                }
                displayCustomerMenu(loginUsername);
                break;
            case 5:
                try {
                    String checking = "Checking";
                    String saving = "Saving";
                    System.out.println("Please select an option:");
                    System.out.println("1) Make a transfer from your checking:");
                    System.out.println("2) Make a transfer from your saving:");
                    int choice = scanner.nextInt();
                    if (choice == 1) {
                        System.out.println("Enter username of the account that you want to make a transfer to");
                        String toUsername = scanner.next();
                        System.out.println("Enter an amount: ");
                        double transferAmount = scanner.nextDouble();

                        if (transferAmount > 0) {
                            customerService.makeTransfer(loginUsername, toUsername, checking, transferAmount);
                            System.out.println("You successfully transferred $" + transferAmount + " to " + toUsername + " account");
                        } else {
                            System.out.println("invalid amount. Please try again");
                        }
                    } else if (choice == 2) {
                        System.out.println("Enter username of the account that you want to make a transfer to");
                        String toUsername = scanner.toString();
                        System.out.println("Enter an amount: ");
                        double transferFromSavingAmount = scanner.nextDouble();

                        if (transferFromSavingAmount >= 0) {
                            customerService.makeTransfer(loginUsername, toUsername, saving, transferFromSavingAmount);
                            System.out.println("You successfully transferred $" + transferFromSavingAmount + " to  " + toUsername + " account");
                        } else {
                            System.out.println("Invalid amount. Please try again");
                        }

                    } else {
                        System.out.println("Invalid input. Please re-select");
                    }

                } catch (BankException e) {
                    System.out.println(e);
                }
                 displayCustomerMenu(loginUsername);
                break;
            case 6:

                customerService.displayPendingTransaction(loginUsername);
                String checking = "Checking";
                String saving = "Saving";
                System.out.println("Please select an option:");
                System.out.println("1) Deposit the pending transfer amount to your checking");
                System.out.println("2) Deposit the pending transfer amount  to your checking");
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        customerService.acceptPendingTransfer(loginUsername, checking);
                        break;
                    case 2:
                        customerService.acceptPendingTransfer(loginUsername, saving);
                        break;
                    default:
                        System.out.println("Invalid input. Try again!");
                        displayCustomerMenu(loginUsername);
                }
            default:
                System.out.println("Invalid input. Please re-enter");
                 displayCustomerMenu(loginUsername);
        }

    }

    public void displayEmployeeMenu() {

        try {
            System.out.println("Select an option: ");
            System.out.println("1) View customer's bank account by customerID");
            System.out.println("2) Display all pending accounts");
            System.out.println("3) Approve account by customerID");
            System.out.println("4) Reject account by customerID");
            System.out.println("5) Display all the previous transaction from customers");
            System.out.println("6) Logout");
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Enter the customer ID: ");
                    int id = scanner.nextInt();
                    List<BankAccount> list = new ArrayList<>();
                    list = employeeFunction.displayCustomerBankAccountById(id);
                    System.out.println("Customer with an id: " + id);
                    for (BankAccount a : list) {
                        System.out.println(a.getAccountType() + " account balance: $" + a.getBalance() + " , Previous transaction: $" + a.getPreviousTransaction());
                    }
                     displayEmployeeMenu();
                    break;
                case 2:
                    List<Customer> list1 = new ArrayList<>();
                    list1 = employeeFunction.displayAllPendingCustomer();
                    for (Customer customer : list1) {
                        System.out.println("Customer id:" + customer.getCustomerId() + "|| Name:" + customer.getCustomerName() + "|| Contact:" + customer.getContact());
                    }
                     displayEmployeeMenu();
                case 3:
                    System.out.println("Please enter customer id");
                    int customerId = scanner.nextInt();
                    boolean state = false;
                    state = employeeFunction.approveCustomerAccountById(customerId);
                    if (state == true) {
                        System.out.println("The account with id: " + customerId + "  is approved");
                    } else {
                        System.out.println("Unsuccessfully approve the account with id:  " + customerId);
                    }
                     displayEmployeeMenu();
                    break;
                case 4:
                    System.out.println("Please enter customer id");
                    int customerIdReject = scanner.nextInt();
                    boolean state1 = false;
                    state1 = employeeFunction.rejectCustomerAccountById(customerIdReject);
                    if (state1 == true) {
                        System.out.println("The account with id: " + customerIdReject + "  is rejected");
                    } else {
                        System.out.println("Unsuccessfully reject the account with id:  " + customerIdReject);
                    }
                     displayEmployeeMenu();
                    break;
                case 5:
                    System.out.println("Enter customer ID");
                    int customerId1 = scanner.nextInt();
                    List<BankAccount> list2 = new ArrayList<>();
                    list2 = employeeFunction.displayCustomerBankAccountById(customerId1);
                    if(list2.isEmpty()){
                        System.out.println("Previous transaction not found for this id " + customerId1);
                    }else{
                        System.out.println("Customer Id :" + customerId1);
                        for (BankAccount a: list2) {
                            System.out.println(a.getAccountType() + " account has the previous transaction of " + a.getPreviousTransaction());
                        }
                    }
                    displayEmployeeMenu();
                    break;
                case 6:
                    displayMenu();
                    break;

                default:
                    System.out.println("Please re-select from 1-6");
                     displayEmployeeMenu();
            }
        } catch (BankException e) {
            System.out.println(e);
        }

    }
}