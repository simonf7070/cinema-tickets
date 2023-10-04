package uk.gov.dwp.uc.pairtest;

import java.util.stream.Stream;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

public class TicketCalculatorImpl implements TicketCalculator {

    private final int adultSeatPrice = 20;

    @Override
    public int getNumberOfSeats(TicketTypeRequest... ticketTypeRequests) {
        return Stream.of(ticketTypeRequests)
            .filter(t -> t.getTicketType() != Type.INFANT)
            .mapToInt(t -> t.getNoOfTickets())
            .sum();
    }

    @Override
    public int getTotalCost(TicketTypeRequest... ticketTypeRequests) {
        var adultSeatCount = Stream.of(ticketTypeRequests)
            .filter(t -> t.getTicketType() == Type.ADULT)
            .mapToInt(t -> t.getNoOfTickets())
            .sum();

        return adultSeatCount * adultSeatPrice;
    }
}
