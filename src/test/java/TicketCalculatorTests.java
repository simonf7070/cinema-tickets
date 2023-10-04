import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import uk.gov.dwp.uc.pairtest.TicketCalculator;
import uk.gov.dwp.uc.pairtest.TicketCalculatorImpl;

public class TicketCalculatorTests {
    @Test
    public void getNumberOfSeats_should_be_zero_when_none_requested() {
        TicketCalculator ticketCalculator = new TicketCalculatorImpl();
        
        assertEquals(0, ticketCalculator.getNumberOfSeats());
    }
}
