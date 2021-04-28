import org.bank.exeption.BankException;
import org.bank.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerTesting {

    @Test
    public void CustomerConstructorShouldPass() {
        Customer customer = new Customer("phuch", "phucsonmy", "phuc", "425-954-9600", "Pending");
        Assertions.assertEquals("phuch", customer.getUsername());
        Assertions.assertEquals("phucsonmy", customer.getPassword());
        Assertions.assertEquals("phuc", customer.getCustomerName());
        Assertions.assertEquals("425-954-9600", customer.getContact());
        Assertions.assertEquals("Pending", customer.getStatus());
    }
    @Test
    public void CustomerConstructorShouldFail() {
        Customer customer = new Customer("phuc", "phucsonmy", "phuc", "425-954-9600", "Pending");
        Assertions.assertNotEquals("phuch", customer.getUsername());
        Assertions.assertNotEquals("phucs", customer.getPassword());
        Assertions.assertNotEquals("phc", customer.getCustomerName());
        Assertions.assertNotEquals("4259549600", customer.getContact());
        Assertions.assertNotEquals("Pend", customer.getStatus());
    }

}
