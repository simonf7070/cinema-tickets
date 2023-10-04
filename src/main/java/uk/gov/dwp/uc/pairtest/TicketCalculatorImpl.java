package uk.gov.dwp.uc.pairtest;

import java.util.stream.Stream;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

public class TicketCalculatorImpl implements TicketCalculator {

    private final int adultSeatPrice = 20;
    private final int childSeatPrice = 10;

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

        var childSeatCount = Stream.of(ticketTypeRequests)
            .filter(t -> t.getTicketType() == Type.CHILD)
            .mapToInt(t -> t.getNoOfTickets())
            .sum();

        int totalAdultCost = adultSeatCount * adultSeatPrice;
        int totalChildCost = childSeatCount * childSeatPrice;
        
        return totalAdultCost + totalChildCost;
    }
}
