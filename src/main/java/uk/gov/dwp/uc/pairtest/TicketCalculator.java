package uk.gov.dwp.uc.pairtest;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public interface TicketCalculator {
    int getNumberOfSeats(TicketTypeRequest... ticketTypeRequests);
    int getTotalCost(TicketTypeRequest... ticketTypeRequests);
}
