package org.bank.service;

import org.apache.log4j.Logger;
import org.bank.exeption.BankException;
import org.bank.model.BankAccount;
import org.bank.model.Customer;
import org.bank.service.implementation.CustomerServiceImplementation;
import org.bank.service.implementation.EmployeeFunctionImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * BankDisplayMenu is created to provide the UI for the users, customers,and employees to interact with and x the console when entering x
 *
 * @return a string
 */
public class BankDisplayMenu {

    private Logger logger = Logger.getLogger(BankDisplayMenu.class);
    private EmployeeFunction employeeFunction = new EmployeeFunctionImplementation();
    private CustomerService customerService = new CustomerServiceImplementation();


    /*
     * This method display the main console which is login
     *  Note: The registerfortnewaccount() can be refactored and the code could be more organized
     *
     */
    public void displayMenu() {
        try {

            logger.info("please select an option:");
            logger.info("1. Log in as an existing customer");
            logger.info("2. Log in as an employee");
            logger.info("3. Sign up for a new customer account");
            logger.info("x. Exiting the System");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();
            //using switch statement to provide the options
            switch (option) {
                case "1":
                    logger.info("Emter your username:");
                    String loginUsername = scanner.next();
                    logger.info("Enter your password:");
                    String loginPassword = scanner.next();
                    //validate the user name and password to see if they both exists in the same rwo in the database
                    boolean validation = false;

                    validation = customerService.validateAccount(loginUsername, loginPassword);

                    if (validation == true) { // it will will load the displayCustomerMenu if validation is true
                        displayCustomerMenu(loginUsername);
                    } else {
                        //letting the user know that its not valid
                        logger.info("Invalid username or password. Please try again");
                    }
                    displayMenu();
                    break;
                case "2":
                    /*
                     * assume there is only one employee with username:erica12 & password:phucsonmy
                     */

                    logger.info("Emter username:");
                    String loginUsername1 = scanner.next();
                    logger.info("Enter password:");
                    String loginPassword1 = scanner.next();
                    if (loginUsername1.equalsIgnoreCase("erica12") && loginPassword1.equals("phucsonmy")) {
                        displayEmployeeMenu();
                    } else {
                        logger.info("Your username and password are invalid. Please try again!");
                        displayMenu();
                    }

                    displayMenu();
                    break;
                case "3":
                    try {
                        Customer newCustomer;
                        String applicationStatus = "Pending";
                        // ask for first name
                        logger.info("Enter your name: ");
                        String customerName = scanner.nextLine();
                        //ask for phone numer
                        logger.info("Enter your contact in the format ###-###-####");
                        String contact = scanner.next();
                        //ask for username
                        logger.info("Enter username: ");
                        String userName = scanner.next();
                        //ask for password
                        logger.info("Emter password: ");
                        String password = scanner.next();
                        if(contact.matches("[0-9]{3}-[0-9]{3}-[0-9]{4}")) {
                            newCustomer = new Customer(userName, password, customerName, contact, applicationStatus);
                            employeeFunction.registerForNewAccount(newCustomer);
                            logger.info("Congrats! Thanks for chosing ABC bank. Your account is pending to be approved.");

                        }else {
                            logger.info("Invalid input. Please try again");
                        }

                    } catch (BankException e) {
                        logger.error(e.getMessage());
                    }
                    displayMenu();
                    break;
                default:
                    if(!option.equalsIgnoreCase("x")) {
                        logger.info("The input is not valid. please re-enter");
                        displayMenu();
                    }

            }
        } catch (BankException e) {
            logger.error(e);

        }

    }

    //--------------------------Customer console display----------------------------

    public void displayCustomerMenu(String loginUsername) throws BankException {

        Scanner scanner = new Scanner(System.in);
        logger.info("Select an option: ");
        logger.info("1) Apply for a new bank account");
        logger.info("2) View balance");
        logger.info("3) Deposit");
        logger.info("4) Withdraw");
        logger.info("5) Make a transfer");
        logger.info("6) Accept pending transfer");
        logger.info("7) Logout");
        String customerOption = scanner.next();
        switch (customerOption) {
            case "1":

                boolean status = true;
                String checkingType = "Checking";
                String savingType = "Saving";
                while (status) {


                    logger.info("Select an option: ");
                    logger.info("1) Create a checking account.");
                    logger.info("2) Create a saving amount");
                    logger.info("3) Create a checking and a saving account");
                    int opt = scanner.nextInt();
                    if (opt == 1) {
                        logger.info("Enter the amount:");
                        double amount = scanner.nextDouble();
                        if (amount < 0) {
                            logger.info("Amount entered is invalid. Please enter a positive value.");
                        } else {
                            customerService.applyForBankAccount(loginUsername, checkingType, amount);
                            logger.info("You successfully opened " + checkingType + " with the amount $" + amount);
                            status = false;
                        }

                    } else if (opt == 2) {
                        logger.info("Enter the amount:");
                        double amountsav = scanner.nextDouble();
                        if (amountsav < 0) {
                            logger.info("Amount entered is invalid. Please enter a positive value.");
                            amountsav = scanner.nextDouble();
                        } else {
                            customerService.applyForBankAccount(loginUsername, savingType, amountsav);
                            logger.info("You successfully opened " + savingType + " with the amount $" + amountsav);
                            status = false;
                        }
                    } else if (opt == 3) {
                        logger.info("Enter the checking account amount");
                        double checkingAmount = scanner.nextDouble();
                        if (checkingAmount < 0) {
                            logger.info("Amount entered is invalid. Please enter a different value.");

                        } else {
                            customerService.applyForBankAccount(loginUsername, checkingType, checkingAmount);
                            logger.info("You successfully opened " + checkingType + " with the amount $+" + checkingAmount);

                        }

                        logger.info("Enter the saving account amount");
                        double savingAmount = scanner.nextDouble();
                        if (savingAmount < 0) {
                            logger.info("Amount entered is invalid. Please enter positive value.");
                            savingAmount = scanner.nextDouble();
                        } else {
                            customerService.applyForBankAccount(loginUsername, savingType, savingAmount);
                            logger.info("You successfully opened " + savingType + " with the amount $" + savingAmount);
                        }
                        status = false;
                    } else {

                        status = true;
                    }

                    status = false;
                }
                displayCustomerMenu(loginUsername);
                break;
            case "2":
                try {

                    List<BankAccount> list = new ArrayList<>();
                    list = customerService.viewBankAccount(loginUsername);
                    logger.info(loginUsername);
                    for (BankAccount a : list) {
                        logger.info(a.getAccountType() + ": " + a.getBalance());
                    }
                } catch (BankException e) {
                    logger.info(e.getMessage());
                }
                displayCustomerMenu(loginUsername);
                break;
            case "3":
                try {
                    String checking = "Checking";
                    String saving = "Saving";
                    logger.info("Please select an option:");
                    logger.info("1) Deposit to your checking: ");
                    logger.info("2) Deposit to your saving: ");
                    int choice = scanner.nextInt();
                    if (choice == 1) {
                        logger.info("Enter an amount: ");
                        double depositAmount = scanner.nextDouble();
                        if (depositAmount >= 0) {
                            customerService.deposit(loginUsername, checking, depositAmount);
                            logger.info("You successfully deposited $" + depositAmount + " to your " + checking + " account");
                        } else {
                            logger.info("invalid amount. Please try again");
                        }
                    } else if (choice == 2) {
                        logger.info("Enter an amount: ");
                        double depositSavingAmount = scanner.nextDouble();
                        if (depositSavingAmount >= 0) {
                            customerService.deposit(loginUsername, saving, depositSavingAmount);
                            logger.info("You successfully deposited $" + depositSavingAmount + " to your " + saving + " account");
                        } else {
                            logger.info("Invalid amount. Please try again");
                        }
                    } else {
                        logger.info("Invalid input. Please re-select");
                    }
                } catch (BankException e) {
                    logger.error(e);
                }
                displayCustomerMenu(loginUsername);
                break;
            case "4":
                try {
                    String checking = "Checking";
                    String saving = "Saving";
                    logger.info("Please select an option:");
                    logger.info("1) Withdraw from your checking: ");
                    logger.info("2) Withdraw from your saving: ");
                    int choice = scanner.nextInt();
                    if (choice == 1) {
                        logger.info("Enter an amount: ");
                        double withdrawAmount = scanner.nextDouble();
                        if (withdrawAmount > 0) {
                            customerService.withdraw(loginUsername, checking, withdrawAmount);
                            logger.info("You successfully withdrew $" + withdrawAmount + " from your " + checking + " account");
                        } else {
                            logger.info("invalid amount. Please try again");
                        }
                    } else if (choice == 2) {
                        logger.info("Enter an amount: ");
                        double withdrawSavingAmount = scanner.nextDouble();
                        if (withdrawSavingAmount >= 0) {
                            customerService.withdraw(loginUsername, saving, withdrawSavingAmount);
                            logger.info("You successfully withdrew $" + withdrawSavingAmount + " from your " + saving + " account");
                        } else {
                            logger.info("Invalid amount. Please try again");
                        }

                    } else {
                        logger.info("Invalid input. Please re-select");
                    }
                } catch (BankException e) {
                    logger.error(e);
                }
                displayCustomerMenu(loginUsername);
                break;
            case "5":
                try {
                    String checking = "Checking";
                    String saving = "Saving";
                    logger.info("Please select an option:");
                    logger.info("1) Make a transfer from your checking:");
                    logger.info("2) Make a transfer from your saving:");
                    int choice = scanner.nextInt();
                    if (choice == 1) {
                        logger.info("Enter username of the account that you want to make a transfer to");
                        String toUsername = scanner.next();
                        logger.info("Enter an amount: ");
                        double transferAmount = scanner.nextDouble();

                        if (transferAmount > 0) {
                            customerService.makeTransfer(loginUsername, toUsername, checking, transferAmount);
                            logger.info("You successfully transferred $" + transferAmount + " to " + toUsername + " account");
                        } else {
                            logger.info("invalid amount. Please try again");
                        }
                    } else if (choice == 2) {
                        logger.info("Enter username of the account that you want to make a transfer to");
                        String toUsername = scanner.toString();
                        logger.info("Enter an amount: ");
                        double transferFromSavingAmount = scanner.nextDouble();

                        if (transferFromSavingAmount >= 0) {
                            customerService.makeTransfer(loginUsername, toUsername, saving, transferFromSavingAmount);
                            logger.info("You successfully transferred $" + transferFromSavingAmount + " to  " + toUsername + " account");
                        } else {
                            logger.info("Invalid amount. Please try again");
                        }

                    } else {
                        logger.info("Invalid input. Please re-select");
                    }

                } catch (BankException e) {
                    logger.error(e);
                }
                displayCustomerMenu(loginUsername);
                break;
            case "6":

                customerService.displayPendingTransaction(loginUsername);
                logger.info("Enter transaction id that you what to accept:");
                int option = scanner.nextInt();
                boolean status1 = false;
                status1 = customerService.acceptPendingTransfer(option);
                if (status1 == true) {
                    logger.info("You successfully accepted the transaction with the id  " + option);
                } else {
                    logger.info("Unsuccessfully accepted the transaction");
                }
                displayCustomerMenu(loginUsername);
                break;

            default:
                if(!customerOption.equals("7") || !customerOption.equalsIgnoreCase("x")) {
                    logger.info("Invalid input. Please re-enter");
                    displayMenu();
                }
        }

    }

    public void displayEmployeeMenu() {

        try {
            logger.info("Select an option: ");
            logger.info("1) View bank account by customerID");
            logger.info("2) Display all pending accounts");
            logger.info("3) Approve account by customerID");
            logger.info("4) Reject account by customerID");
            logger.info("5) Display all the previous transactions bu customer ID");
            logger.info("6) Logout");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();
            switch (option) {
                case "1":
                    logger.info("Enter the customer ID: ");
                    int id = scanner.nextInt();
                    List<BankAccount> list = new ArrayList<>();
                    list = employeeFunction.displayCustomerBankAccountById(id);
                    logger.info("Customer with an id: " + id);
                    for (BankAccount a : list) {
                        logger.info(a.getAccountType() + " account balance: $" + a.getBalance());
                    }
                    displayEmployeeMenu();
                    break;
                case "2":
                    List<Customer> list1 = new ArrayList<>();
                    list1 = employeeFunction.displayAllPendingCustomer();
                    for (Customer customer : list1) {
                        logger.info("Customer id:" + customer.getCustomerId() + "|| Name:" + customer.getCustomerName() + "|| Contact:" + customer.getContact());
                    }
                    displayEmployeeMenu();
                case "3":
                    logger.info("Please enter customer id");
                    int customerId = scanner.nextInt();
                    boolean state = false;
                    state = employeeFunction.approveCustomerAccountById(customerId);
                    if (state == true) {
                        logger.info("The account with id: " + customerId + "  is approved");
                    } else {
                        logger.info("Unsuccessfully approve the account with id:  " + customerId);
                    }
                    displayEmployeeMenu();
                    break;
                case "4":
                    logger.info("Please enter customer id");
                    int customerIdReject = scanner.nextInt();
                    boolean state1 = false;
                    state1 = employeeFunction.rejectCustomerAccountById(customerIdReject);
                    if (state1 == true) {
                        logger.info("The account with id: " + customerIdReject + "  is rejected");
                    } else {
                        logger.info("Unsuccessfully reject the account with id:  " + customerIdReject);
                    }
                    displayEmployeeMenu();
                    break;
                case "5":
                    logger.info("Enter customer ID");
                    int customerId1 = scanner.nextInt();
                    employeeFunction.displayPreviousTransactionById(customerId1);
                    displayEmployeeMenu();
                    break;
                default:
                    if(!option.equals("6") || !option.equalsIgnoreCase("x")) {
                        logger.info("Invalid input. Please re-enter");
                        displayMenu();
                    }
            }
        } catch (BankException e) {
            logger.error(e);
        }

    }
}