import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketServiceTests {
    
    @Test
    public void Invalid_AccountId_should_throw_exception() {
        final var invalidAccountId = -1L;
        TicketService ticketService = new TicketServiceImpl();        

        var exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(invalidAccountId));
        assertEquals("Invalid Account Id", exception.getMessage());
    }
}
