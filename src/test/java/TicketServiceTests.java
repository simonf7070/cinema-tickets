import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Before;
import org.junit.Test;

import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
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
        var tickets = new TicketTypeRequest(Type.ADULT, 1);

        assertDoesNotThrow(() -> ticketService.purchaseTickets(validAccountId, tickets));
    }

    @Test
    public void no_tickets_requested_throws_exception() {
        var exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(validAccountId));
        assertEquals("No tickets requested", exception.getMessage());
    }
    
    @Test
    public void zero_quantity_of_tickets_requested_throws_exception() {
        var zeroTickets = new TicketTypeRequest(Type.ADULT, 0);

        var exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(validAccountId, zeroTickets));
        assertEquals("No tickets requested", exception.getMessage());
    }

    @Test
    public void cannot_request_child_ticket_without_an_adult_throws_exception() {
        var tickets = new TicketTypeRequest(Type.CHILD, 1);

        var exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(validAccountId, tickets));
        assertEquals("Cannot request child or infant tickets without an adult", exception.getMessage());
    }
    
    @Test
    public void cannot_request_infant_ticket_without_an_adult_throws_exception() {
        var tickets = new TicketTypeRequest(Type.INFANT, 1);

        var exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(validAccountId, tickets));
        assertEquals("Cannot request child or infant tickets without an adult", exception.getMessage());
    }

    @Test
    public void each_infant_must_be_accompanied_by_an_adult_throws_exception() {
        var twoInfantTickets = new TicketTypeRequest(Type.INFANT, 2);
        var oneAdultTicket = new TicketTypeRequest(Type.ADULT, 1);

        var exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(validAccountId, twoInfantTickets, oneAdultTicket));
        assertEquals("Cannot request an Infant ticket without an accompanying Adult ticket", exception.getMessage());
    }

    @Test
    public void cannot_request_more_than_max_number_of_tickets_throws_exception() {
        var exceedMaxNumberOfTickets = new TicketTypeRequest(Type.ADULT, 21);

        var exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(validAccountId, exceedMaxNumberOfTickets));
        assertEquals("Cannot request more than maximum number of tickets", exception.getMessage());
    }
}
