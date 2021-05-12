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
        Javalin app = Javalin.create(config->config.enableCorsForAllOrigins()).
                start(8000);




        /**
         * ====================================Customer===View==========================================================
         */

        //apply for a new customer account
        app.post("/customer", ctx -> {
            try {
                Customer customer = ctx.bodyAsClass(Customer.class);
                employeeFunction.registerForNewAccount(customer);
                ctx.result("Thank you for choosing Billy Banking. Your account is now pending to be approved");
            }catch (BankException e) {
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
                validation = customerService.validateAccount(username,password);
                ctx.json(validation);
            }catch (BankException e){
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
                double amount = jsonObject.getDouble("amount");
                customerService.applyForBankAccount(username, accountType, amount);

            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });

        //view banking info
        app.get("/customer/:username", ctx -> {
            try {

                Customer customer = ctx.bodyAsClass(Customer.class);
                List<BankAccount> list = customerService.viewBankAccount(customer.getUsername());
                ctx.json(list);
            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });



        //deposit
        app.post("/customer/bankaccount-deposit", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                String username = jsonObject.getString("username");
                String accountType = jsonObject.getString("accountType");
                double amount = jsonObject.getDouble("amount");
                customerService.deposit(username, accountType, amount);
            }catch (BankException e) {
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
                double amount = jsonObject.getDouble("amount");
                customerService.deposit(username, accountType, amount);
            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });

        //Make a transfer
        app.post("/customer/bankaccount-transfer", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                String fromUsername = jsonObject.getString("username");
                String toUsername = jsonObject.getString("toUsername");
                String accountType = jsonObject.getString("accountType");
                double amount = jsonObject.getDouble("amount");
                customerService.makeTransfer(fromUsername, toUsername, accountType, amount);
            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });

        //Retrieve all of pending transaction
        app.get("/customer/bankaccount/get-pending-transaction", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                String username = jsonObject.getString("username");
                List<Transaction> list = customerService.displayPendingTransaction(username);
                ctx.json(list);
            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });


        // Accept the pending transactions by the id
        app.post("/customer/bankaccount/accept-pending-transaction", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                int id  = jsonObject.getInt("pendingTransactionId");
                customerService.acceptPendingTransfer(id);
            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });


        //Display all of previous transaction
        app.get("/customer/bankaccount/display/previous-transaction", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                String username = jsonObject.getString("username");
                List<Transaction> list = customerService.displayPreviousTransactionByUsername(username);
                ctx.json(list);
            }catch (BankException e){
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
            ctx.result("Congrats! You have successfully registered YAY!!!!");
        });

        //display bankinfo by the customer id
        app.get("/employee/customer/bankaccount", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                int id  = jsonObject.getInt("id");
               List<BankAccount> list = employeeFunction.displayCustomerBankAccountById(id);
               ctx.json(list);
            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });

        //display all of pending customers
        app.get("/employee/customer-pending", ctx -> {
            try {
                List<Customer> list = employeeFunction.displayAllPendingCustomer();
                ctx.json(list);
            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });

        //approve the customer by id
        app.get("/employee/approve-customer-pending", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                int id  = jsonObject.getInt("id");
                String employeeUsername = jsonObject.getString("employeeUsername");
                boolean status = employeeFunction.approveCustomerAccountById(id, employeeUsername);
                ctx.json(status);
            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });

        //reject the customer by id

        app.get("/employee/reject-customer-pending", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                int id  = jsonObject.getInt("id");
                boolean status = employeeFunction.rejectCustomerAccountById(id);
                ctx.json(status);
            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });
        //display all transaction by customer id
        app.get("/employee/bankaccount/transaction/:id", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                int id  = jsonObject.getInt("customerId");
               List<Transaction>  list = employeeFunction.displayPreviousTransactionById(id);
                ctx.json(list);
            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });

        //display all the transaction by account id
        app.get("/employee/bankaccount/transaction-by-accountid", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                int accountId  = jsonObject.getInt("accountId");
                List<Transaction> list = employeeFunction.displayPreviousTransactionByAccountId(accountId);
                ctx.json(list);
            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });

        // display all the transaction by date
        app.get("/employee/bankaccount/transaction-by-transactionid", ctx -> {
            try {
                ctx.body();
                JSONObject jsonObject = new JSONObject(ctx.body());
                int transactionId  = jsonObject.getInt("transactionId");
                List<Transaction> list = employeeFunction.displayPreviousTransactionByTransactionId(transactionId);
                ctx.json(list);
            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });

        //validate employee account

        app.get("/employee", ctx -> {
            try {
                boolean validation = false;
                Employee employee = ctx.bodyAsClass(Employee.class);
                validation = employeeFunction.validateEmployeeAccount(employee.getEmployeeUsername(), employee.getEmployeePassword());
                ctx.json(validation);
            }catch (BankException e){
                ctx.json(e.getMessage());
            }
        });

        //Get the Approver that approve specific customer
        app.get("/employee/approver", ctx -> {
            ctx.body();
            JSONObject jsonObject = new JSONObject(ctx.body());

            int customerId  = jsonObject.getInt("customerId");
           Customer customer = employeeFunction.getCustomerApprover(customerId);
            ctx.json(customer);
        });








































//        BankDisplayMenu bankDriver = new BankDisplayMenu();
//        logger.info("------------------------------------------------------");
//        logger.info("Welcome to Billy Bank!!!! ");
//        logger.info("-------------------------------------------------------");
//        bankDriver.displayMenu();

        //List<Transaction> list = new ArrayList<>();
        //list = employeeFunction.displayPreviousTransactionByTransactionId(1);
        //list = employeeFunction.displayPreviousTransactionByAccountId(26);
        //list = employeeFunction.displayPreviousTransactionByDate("2021-05-07%");
        //System.out.println(list);
        //employeeFunction.registerForEmployeeAccount(new Employee("phuch", "phucsonmy", "Phuc"));
        /*boolean a = employeeFunction.validateEmployeeAccount("phuc", "phucsonmy");
        System.out.println(a);
        boolean a = employeeFunction.approveCustomerAccountById(22, "Phuch");*/

    }

}










