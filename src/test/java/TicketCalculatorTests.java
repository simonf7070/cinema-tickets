import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import uk.gov.dwp.uc.pairtest.TicketCalculator;
import uk.gov.dwp.uc.pairtest.TicketCalculatorImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

public class TicketCalculatorTests {
    private TicketCalculator ticketCalculator;

    @Before
    public void setup() {
        ticketCalculator = new TicketCalculatorImpl();
    }

    @Test
    public void getNumberOfSeats_should_be_zero_when_none_requested() {
        assertEquals(0, ticketCalculator.getNumberOfSeats());
    }

    @Test
    public void getNumberOfSeats_should_be_zero_when_zero_adult_requested() {
        var tickets = new TicketTypeRequest(Type.ADULT, 0);

        assertEquals(0, ticketCalculator.getNumberOfSeats(tickets));
    }  

    @Test
    public void getNumberOfSeats_should_be_one_when_one_adult_requested() {
        var tickets = new TicketTypeRequest(Type.ADULT, 1);
        
        assertEquals(1, ticketCalculator.getNumberOfSeats(tickets));
    }

    @Test
    public void getNumberOfSeats_should_be_two_when_two_adult_requested() {
        TicketTypeRequest[] tickets = {
            new TicketTypeRequest(Type.ADULT, 1),
            new TicketTypeRequest(Type.ADULT, 1)
        };
        
        assertEquals(2, ticketCalculator.getNumberOfSeats(tickets));
    }
    
    @Test
    public void getNumberOfSeats_should_be_two_when_one_adult_and_one_child_requested() {
        TicketTypeRequest[] tickets = {
            new TicketTypeRequest(Type.ADULT, 1),
            new TicketTypeRequest(Type.CHILD, 1)
        };
        
        assertEquals(2, ticketCalculator.getNumberOfSeats(tickets));
    }
        
    @Test
    public void getNumberOfSeats_should_be_one_when_one_adult_and_one_infant_requested() {
        TicketTypeRequest[] tickets = {
            new TicketTypeRequest(Type.ADULT, 1),
            new TicketTypeRequest(Type.INFANT, 1)
        };
        
        assertEquals(1, ticketCalculator.getNumberOfSeats(tickets));
    }
            
    @Test
    public void getNumberOfSeats_should_be_correct_when_a_mixture_requested() {
        TicketTypeRequest[] tickets = {
            new TicketTypeRequest(Type.ADULT, 3),
            new TicketTypeRequest(Type.CHILD, 6),
            new TicketTypeRequest(Type.INFANT, 2)
        };
        
        assertEquals(9, ticketCalculator.getNumberOfSeats(tickets));
    }

    @Test
    public void getTotalCost_should_be_zero_when_no_tickets_requested() {
        assertEquals(0, ticketCalculator.getTotalCost());
    }

    @Test
    public void getTotalCost_should_be_zero_when_tickets_with_quantity_zero_requested() {
        TicketTypeRequest[] tickets = {
            new TicketTypeRequest(Type.ADULT, 0)
        };
        
        assertEquals(0, ticketCalculator.getTotalCost(tickets));
    }
    
    @Test
    public void getTotalCost_should_be_40_when_two_adult_tickets_requested() {
        TicketTypeRequest[] tickets = {
            new TicketTypeRequest(Type.ADULT, 2)
        };
        
        assertEquals(40, ticketCalculator.getTotalCost(tickets));
    }

    @Test
    public void getTotalCost_should_be_10_when_one_child_ticket_requested() {
        TicketTypeRequest[] tickets = {
            new TicketTypeRequest(Type.CHILD, 1)
        };
        
        assertEquals(10, ticketCalculator.getTotalCost(tickets));
    }

    @Test
    public void getTotalCost_should_be_zero_when_one_infant_ticket_requested() {
        TicketTypeRequest[] tickets = {
            new TicketTypeRequest(Type.INFANT, 1)
        };
        
        assertEquals(0, ticketCalculator.getTotalCost(tickets));
    }
}
