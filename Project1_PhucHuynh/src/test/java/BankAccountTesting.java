import org.bank.model.BankAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BankAccountTesting {

    BankAccount bankAccount = new BankAccount();

    @Test
    public void BankAccountConstructorShouldPass(){
        bankAccount = new BankAccount("Saving", 12, 1);
        Assertions.assertEquals("Saving", bankAccount.getAccountType());
        Assertions.assertEquals(12, bankAccount.getBalance());
        Assertions.assertEquals(1, bankAccount.getAccountId());
    }

    @Test
    public void BankAccountConstructorShouldFail(){
        bankAccount = new BankAccount("Saving", 12, 1);
        Assertions.assertNotEquals("Checking", bankAccount.getAccountType());
        Assertions.assertNotEquals(110, bankAccount.getBalance());
        Assertions.assertNotEquals(13, bankAccount.getAccountId());
    }
}
