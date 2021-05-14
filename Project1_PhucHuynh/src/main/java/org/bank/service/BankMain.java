package org.bank.service;

import io.javalin.Javalin;
import org.apache.log4j.Logger;
import org.bank.exeption.BankException;
import org.bank.model.BankAccount;
import org.bank.model.Customer;
import org.bank.model.Employee;
import org.bank.model.Transaction;
import org.bank.service.implementation.CustomerServiceImplementation;
import org.bank.service.implementation.EmployeeFunctionImplementation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * This is a main class where the program starts
 */
public class BankMain {
    private static Logger logger = Logger.getLogger(BankMain.class);

    public static void main(String[] args) throws BankException {
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation();
        CustomerService customerService = new CustomerServiceImplementation();
        Javalin app = Javalin.create(config -> config.enableCorsForAllOrigins()).
                start(8000);


        /**
         * ====================================Customer===View==========================================================
         */

        //apply for a new customer account
        app.post("/customer", ctx -> {
            try {
                Customer customer = ctx.bodyAsClass(Customer.class);
                employeeFunction.registerForNewAccount(customer);
                ctx.json("Thank you for choosing Billy Banking. Your account is now pending to be approved");
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });


        //validate the customer account in login
        app.get("/customer/:username/:password", ctx -> {
            logger.info("hello");
            try {
                boolean validation = false;
                //ctx.body();
                String username = ctx.pathParam("username");
                String password = ctx.pathParam("password");
                //logger.info(username);
                validation = customerService.validateAccount(username, password);
                ctx.json(validation);
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });

        // apply for bank accounts
        app.post("/customer/create-bankaccount", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                String username = jsonObject.getString("username");
                String accountType = jsonObject.getString("accountType");
                double amount = Double.parseDouble(jsonObject.getString("balance"));

                customerService.applyForBankAccount(username, accountType, amount);
                ctx.json("successful");

            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });

        //view banking info
        app.get("/customer/:username", ctx -> {
            try {
                String username = ctx.pathParam("username");

                List<BankAccount> list = customerService.viewBankAccount(username);
                ctx.json(list);
            } catch (BankException e) {
                ctx.json(e.getMessage());
                logger.error(e.getMessage());
            }
        });


        //deposit
        app.post("/customer/bankaccount-deposit", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                String username = jsonObject.getString("username");
                String accountType = jsonObject.getString("accountType");
                double amount = Double.parseDouble(jsonObject.getString("balance"));

                customerService.deposit(username, accountType, amount);
                ctx.json("successful");
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });


        //Withdraw
        app.post("/customer/bankaccount-withdraw", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                String username = jsonObject.getString("username");
                String accountType = jsonObject.getString("accountType");
                double amount = Double.parseDouble(jsonObject.getString("balance"));
                customerService.withdraw(username, accountType, amount);
                ctx.json("successful");
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });

        //Make a transfer
        app.post("/customer/bankaccount-transfer", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                String fromUsername = jsonObject.getString("username");
                String toUsername = jsonObject.getString("toUser");
                String accountType = jsonObject.getString("accountType");
                double amount = Double.parseDouble(jsonObject.getString("balance"));
                customerService.makeTransfer(fromUsername, toUsername, accountType, amount);
                ctx.json("successful");
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });

        //Retrieve all of pending transaction
        app.get("/customer/bankaccount/get-pending-transaction/:username", ctx -> {
            try {
                String username = ctx.pathParam("username");

                List<Transaction> list = customerService.displayPendingTransaction(username);
                ctx.json(list);
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });


        // Accept the pending transactions by the id
        app.get("/customer/bankaccount/accept-pending-transaction/:id", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                boolean status = customerService.acceptPendingTransfer(id);
                ctx.json(status);
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });
        //Display all of previous transaction
        app.get("/customer/bankaccount/display/previous-transaction/:username", ctx -> {
            try {
                String username = ctx.pathParam("username");
                List<Transaction> list = customerService.displayPreviousTransactionByUsername(username);
                ctx.json(list);
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });


        /**
         * ====================================Employee===View==========================================================
         */

        //apply for a new customer account
        app.post("/employee", ctx -> {
            Employee employee = ctx.bodyAsClass(Employee.class);
            employeeFunction.registerForEmployeeAccount(employee);
            ctx.json("Congrats! You have successfully registered YAY!!!!");
        });

        //display bankinfo by the customer id
        app.get("/employee/customer/:id", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                List<BankAccount> list = employeeFunction.displayCustomerBankAccountById(id);
                ctx.json(list);
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });

        //display all of pending customers
        app.get("/employee/customer-pending", ctx -> {
            try {
                List<Customer> list = employeeFunction.displayAllPendingCustomer();
                ctx.json(list);
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });

        //approve the customer by id
        app.get("/employee/approve-customer-pending/:id/:employeeUsername", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                String employeeUsername = ctx.pathParam("employeeUsername");
                boolean status = employeeFunction.approveCustomerAccountById(id, employeeUsername);
                ctx.json(status);
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });

        //reject the customer by id

        app.get("/employee/reject-customer-pending/:id", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                boolean status = employeeFunction.rejectCustomerAccountById(id);
                ctx.json(status);
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });
        //display all transaction by customer id
        app.get("/employee/bankaccount/transaction/:id", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                List<Transaction> list = employeeFunction.displayPreviousTransactionById(id);
                ctx.json(list);
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });

        //display all the transaction by account id
        app.get("/employee/bankaccount/transaction-by-accountid/:id", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                List<Transaction> list = employeeFunction.displayPreviousTransactionByAccountId(id);
                ctx.json(list);
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });

        // display all the transaction by transaction id
        app.get("/employee/bankaccount/transaction-by-transactionid/:id", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                List<Transaction> list = employeeFunction.displayPreviousTransactionByTransactionId(id);
                ctx.json(list);
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });
        // display all the transaction by date
        app.get("/employee/bankaccount/transaction-by-date/:date", ctx -> {
            try {
                String tempDate = ctx.pathParam("date");
                String date = tempDate + "%";
                List<Transaction> list = employeeFunction.displayPreviousTransactionByDate(date);
                ctx.json(list);
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });


        //validate employee account

        app.get("/employee/:username/:password", ctx -> {
            try {
                boolean validation;
                String username = ctx.pathParam("username");
                String password = ctx.pathParam("password");
                validation = employeeFunction.validateEmployeeAccount(username, password);
                ctx.json(validation);
            } catch (BankException e) {
                ctx.json(e.getMessage());
            }
        });

        //Get the Approver that approve specific customer
        app.get("/employee/display/approver/:id", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Customer customer = employeeFunction.getCustomerApprover(id);
                ctx.json(customer);
            } catch (BankException e) {
                ctx.json(e);
            }
        });


/**
 * ========================================END=========================================================================
 */



    }

}










