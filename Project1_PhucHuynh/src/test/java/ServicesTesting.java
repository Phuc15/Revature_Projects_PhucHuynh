import org.bank.daos.BankAccountDAO;
import org.bank.daos.CustomerDAO;
import org.bank.daos.EmployeeDAO;
import org.bank.exeption.BankException;
import org.bank.model.BankAccount;
import org.bank.model.Customer;
import org.bank.model.Employee;
import org.bank.model.Transaction;
import org.bank.service.CustomerService;
import org.bank.service.EmployeeFunction;
import org.bank.service.implementation.CustomerServiceImplementation;
import org.bank.service.implementation.EmployeeFunctionImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class ServicesTesting {

   @Test
   public void testValidateAccountshouldPass() throws BankException {
       CustomerDAO customerDAO = mock(CustomerDAO.class);
       BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
       when(customerDAO.validateAccount("phuch", "phucsonmy")).thenReturn(true);
       CustomerService customerService = new CustomerServiceImplementation(bankAccountDAO, customerDAO);
       Assertions.assertEquals(true, customerService.validateAccount("phuch", "phucsonmy"));
   }

    @Test
    public void testValidateAccountshouldFail() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        when(customerDAO.validateAccount("phuch", "phucsonmy")).thenReturn(true);
        CustomerService customerService = new CustomerServiceImplementation(bankAccountDAO, customerDAO);
        Assertions.assertEquals(false, customerService.validateAccount("kate", "phucsonmy"));
    }

    @Test
    public void testDisplayPendingTrasaction() throws BankException {
        List<Transaction>  list = new ArrayList<>();
        Transaction transaction  = new Transaction();
        transaction.setTransactionId(12);
        transaction.setPendingTransaction(100);
        list.add(transaction);
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        when(bankAccountDAO.displayPendingTransaction("phuch")).thenReturn(list);
        CustomerService customerService = new CustomerServiceImplementation(bankAccountDAO, customerDAO);
        Assertions.assertEquals(list, customerService.displayPendingTransaction("phuch"));
    }

    @Test
    public void testAcceptPendingTransactionShouldPass() throws BankException {

        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        when(bankAccountDAO.acceptPendingTransfer(1)).thenReturn(true);
        CustomerService customerService = new CustomerServiceImplementation(bankAccountDAO, customerDAO);
        Assertions.assertEquals(true, customerService.acceptPendingTransfer(1));
    }

    @Test
    public void testAcceptPendingTransactionShouldFail() throws BankException {

        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        when(bankAccountDAO.acceptPendingTransfer(2)).thenReturn(true);
        CustomerService customerService = new CustomerServiceImplementation(bankAccountDAO, customerDAO);
        Assertions.assertEquals(false, customerService.acceptPendingTransfer(1));
    }
    @Test
    public void  testDeposit() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        CustomerService customerService = new CustomerServiceImplementation(bankAccountDAO, customerDAO);
        customerService.deposit("phuch", "Saving", 123);
        verify(bankAccountDAO).deposit("phuch", "Saving",123 );
    }

    @Test
    public void  testWithdraw() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        CustomerService customerService = new CustomerServiceImplementation(bankAccountDAO, customerDAO);
        customerService.withdraw("phuch", "Saving", 123);
        verify(bankAccountDAO).withdraw("phuch", "Saving",123 );
    }

    @Test
    public void testViewBankAccount() throws BankException {
        List<BankAccount>  list = new ArrayList<>();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountId(12);
        bankAccount.setBalance(123);
        list.add(bankAccount);
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        when(bankAccountDAO.viewBankAccount("phuch")).thenReturn(list);
        CustomerService customerService = new CustomerServiceImplementation(bankAccountDAO, customerDAO);
        Assertions.assertEquals(list, customerService.viewBankAccount("phuch"));
    }

    @Test
    public void  testapplyForBankAccount() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        CustomerService customerService = new CustomerServiceImplementation(bankAccountDAO, customerDAO);
        customerService.applyForBankAccount("phuch", "Saving", 123);
        verify(bankAccountDAO).applyForBankAccount("phuch", "Saving",123 );
    }

    @Test
    public void  testRegisterForNewAccount() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        Customer customer = new Customer("phuch", "phucsonmy", "phuc", "425954900", "Pending");
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        employeeFunction.registerForNewAccount(customer);
        verify(customerDAO).registerForNewAccount(customer);
    }

    @Test
    public void testDisplayCustomerBankAccountById() throws BankException {
        List<BankAccount>  list = new ArrayList<>();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountId(12);
        bankAccount.setBalance(123);
        list.add(bankAccount);
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        when(bankAccountDAO.displayCustomerBankAccountById(1)).thenReturn(list);
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        Assertions.assertEquals(list,employeeFunction.displayCustomerBankAccountById(1));
    }

    @Test
    public void testDisplayAllPendingCustomer() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        Customer customer = new Customer("phuch", "phucsonmy", "phuc", "425954900", "Pending");
        List<Customer> list = new ArrayList<>();
        list.add(customer);
        when(customerDAO.displayAllPendingCustomer()).thenReturn(list);
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        Assertions.assertEquals(list, employeeFunction.displayAllPendingCustomer());
    }

    @Test
    public void approveCustomerAccountById() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        when(customerDAO.approveCustomerAccountById(12, "phuch")).thenReturn(true);
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        Assertions.assertEquals(true, employeeFunction.approveCustomerAccountById(12, "phuch") );
    }

    @Test
    public void approveCustomerAccountByIdShouldFail() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        when(customerDAO.approveCustomerAccountById(12, "phuch")).thenReturn(true);
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        Assertions.assertEquals(false, employeeFunction.approveCustomerAccountById(12, "phuc") );
    }
    @Test
    public void rejectCustomerAccountById() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        when(customerDAO.rejectCustomerAccountById(12)).thenReturn(true);
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        Assertions.assertEquals(true, employeeFunction.rejectCustomerAccountById(12) );
    }

    @Test
    public void rejectCustomerAccountByIdShouldFail() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        when(customerDAO.rejectCustomerAccountById(12)).thenReturn(true);
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        Assertions.assertEquals(false, employeeFunction.rejectCustomerAccountById(15) );
    }

    @Test
    public void testDisplayPreviousTransactionById() throws BankException {
        List<Transaction>  list = new ArrayList<>();
        Transaction transaction  = new Transaction();
        transaction.setTransactionId(12);
        transaction.setPendingTransaction(100);
        list.add(transaction);
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        when(bankAccountDAO.displayPreviousTransactionById(1)).thenReturn(list);
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        Assertions.assertEquals(list, employeeFunction.displayPreviousTransactionById(1));
    }

    @Test
    public void testDisplayPreviousTransactionByTransactionId() throws BankException {
        List<Transaction>  list = new ArrayList<>();
        Transaction transaction  = new Transaction();
        transaction.setTransactionId(12);
        transaction.setPendingTransaction(100);
        list.add(transaction);
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        when(bankAccountDAO.displayPreviousTransactionById(1)).thenReturn(list);
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        Assertions.assertEquals(list, employeeFunction.displayPreviousTransactionById(1));
    }

    @Test
    public void testDisplayPreviousTransactionByDate() throws BankException {
        List<Transaction>  list = new ArrayList<>();
        Transaction transaction  = new Transaction();
        transaction.setTransactionId(12);
        transaction.setPendingTransaction(100);
        list.add(transaction);
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        when(bankAccountDAO.displayPreviousTransactionByDate("2021-05-12")).thenReturn(list);
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        Assertions.assertEquals(list, employeeFunction.displayPreviousTransactionByDate("2021-05-12"));
    }

    @Test
    public void testRegisterForEmployeeAccount() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        Employee employee = new Employee("phuch", "phucsonmy", "Phuc");
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        employeeFunction.registerForEmployeeAccount(employee);
        verify(employeeDAO).registerForEmployeeAccount(employee);
    }

    @Test
    public void testGetCustomerApprover() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        Customer customer = new Customer("phuch", "phucsonmy", "phuc", "425954900", "Pending");
        when(customerDAO.getCustomerApprover(12)).thenReturn(customer);
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        Assertions.assertEquals(customer, employeeFunction.getCustomerApprover(12));
    }

    @Test
    public void testValidateEmployeeAccount() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        when(employeeDAO.validateEmployeeAccount("phuch","phucsonmy")).thenReturn(true);
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        Assertions.assertEquals(true, employeeFunction.validateEmployeeAccount("phuch","phucsonmy"));
    }

    @Test
    public void testValidateEmployeeAccountShouldFail() throws BankException {
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        BankAccountDAO bankAccountDAO = mock(BankAccountDAO.class);
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        when(employeeDAO.validateEmployeeAccount("phuch","phucsonmy")).thenReturn(true);
        EmployeeFunction employeeFunction = new EmployeeFunctionImplementation(bankAccountDAO, customerDAO, employeeDAO);
        Assertions.assertEquals(false, employeeFunction.validateEmployeeAccount("phuc","phucsonmy"));
    }











}
