import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Before;
import org.junit.Test;

import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketServiceTests {
    final Long invalidAccountId = -1L;
    final Long validAccountId = 0L;

    private TicketService ticketService;

    @Before
    public void setup() {
        ticketService = new TicketServiceImpl();
    }

    @Test
    public void invalid_AccountId_should_throw_exception() {
        var exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(invalidAccountId));
        assertEquals("Invalid Account Id", exception.getMessage());
    }
        
    @Test
    public void valid_AccountId_should_not_throw_exception() {
        var tickets = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);

        assertDoesNotThrow(() -> ticketService.purchaseTickets(validAccountId, tickets));
    }

    @Test
    public void no_tickets_requested_throws_exception() {
        var exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(validAccountId));
        assertEquals("No tickets requested", exception.getMessage());
    }

    @Test
    public void cannot_request_child_ticket_without_an_adult() {
        var tickets = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);

        var exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(validAccountId, tickets));
        assertEquals("Cannot request child or infant tickets without an adult", exception.getMessage());
    }
}
