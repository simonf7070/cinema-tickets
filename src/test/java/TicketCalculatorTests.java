import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import uk.gov.dwp.uc.pairtest.TicketCalculator;
import uk.gov.dwp.uc.pairtest.TicketCalculatorImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

public class TicketCalculatorTests {
    @Test
    public void getNumberOfSeats_should_be_zero_when_none_requested() {
        TicketCalculator ticketCalculator = new TicketCalculatorImpl();
        
        assertEquals(0, ticketCalculator.getNumberOfSeats());
    }

    @Test
    public void getNumberOfSeats_should_be_zero_when_zero_adult_requested() {
        TicketCalculator ticketCalculator = new TicketCalculatorImpl();
        var tickets = new TicketTypeRequest(Type.ADULT, 0);

        assertEquals(0, ticketCalculator.getNumberOfSeats(tickets));
    }      
   
}
