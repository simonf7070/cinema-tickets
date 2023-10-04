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
 
}
