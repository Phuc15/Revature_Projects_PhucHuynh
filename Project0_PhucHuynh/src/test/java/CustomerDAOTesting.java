import org.bank.daos.CustomerDAO;
import org.bank.daos.implementation.CustomerDAOImpl;
import org.bank.exeption.BankException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerDAOTesting {
    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Test
    public void rejectCustomerAccountByIdShouldFail() {
        Assertions.assertEquals(false, customerDAO.rejectCustomerAccountById(-10));
    }


    // This should pass if there is pending customers are waiting to get approved
   /* @Test
    public void rejectCustomerAccountByIdShouldPass() {
        Assertions.assertEquals(true, customerDAO.rejectCustomerAccountById(10));
    }*/

    @Test
    public void validateAccountShouldPass() throws BankException {
        Assertions.assertEquals(true, customerDAO.validateAccount("phuch", "phucsonmy"));
    }
    //No one id pending to be approve right now
    /*@Test
    public void approveCustomerAccountByIdShoudlPass(){
        Assertions.assertEquals(true, customerDAO.approveCustomerAccountById(12));
    }*/
    @Test
    public void approveCustomerAccountByIdShoudlFail(){
        Assertions.assertEquals(false, customerDAO.approveCustomerAccountById(-10));
    }
}


