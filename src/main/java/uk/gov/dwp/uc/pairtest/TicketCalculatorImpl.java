package uk.gov.dwp.uc.pairtest;

import java.util.stream.Stream;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public class TicketCalculatorImpl implements TicketCalculator {

    @Override
    public int getNumberOfSeats(TicketTypeRequest... ticketTypeRequests) {
        return Stream.of(ticketTypeRequests)
            .mapToInt(t -> t.getNoOfTickets())
            .sum();
    }
}
