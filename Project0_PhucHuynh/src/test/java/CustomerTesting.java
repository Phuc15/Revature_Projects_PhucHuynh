import org.bank.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerTesting {
    @Test
    public void CustomerConstructorShouldPass(){
       Customer customer = new Customer("phuch", "phucsonmy", "phuc", "425-954-9600", "Pending");
       Assertions.assertEquals("phuch", customer.getUsername());
       Assertions.assertEquals("phucsonmy", customer.getPassword());
        Assertions.assertEquals("phuc", customer.getCustomerName());
       Assertions.assertEquals("425-954-9600", customer.getContact());
       Assertions.assertEquals("Pending", customer.getStatus());
    }
}
