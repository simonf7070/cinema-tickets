package uk.gov.dwp.uc.pairtest;

import java.util.stream.Stream;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

public class TicketCalculatorImpl implements TicketCalculator {

    @Override
    public int getNumberOfSeats(TicketTypeRequest... ticketTypeRequests) {
        return Stream.of(ticketTypeRequests)
            .filter(t -> t.getTicketType() != Type.INFANT)
            .mapToInt(t -> t.getNoOfTickets())
            .sum();
    }
}
