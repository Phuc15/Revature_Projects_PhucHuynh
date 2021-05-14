import org.bank.daos.BankAccountDAO;
import org.bank.daos.implementation.BankAccountDAOImpl;
import org.bank.exeption.BankException;
import org.bank.model.BankAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BankAccountDAOTesting {
    BankAccountDAO bankAccountDAO = new BankAccountDAOImpl();

    @Test
    public void acceptPendingTransferShouldFail() throws BankException {
        Assertions.assertNotEquals(true, bankAccountDAO.acceptPendingTransfer(16));
    }

   //This case should pass if there is pending transaction
   /* @Test
    public void acceptPendingTransferShouldPass() throws BankException {
        Assertions.assertEquals(true, bankAccountDAO.acceptPendingTransfer(16));
    }*/

}
