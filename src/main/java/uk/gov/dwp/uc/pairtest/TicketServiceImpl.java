package uk.gov.dwp.uc.pairtest;

import java.util.stream.Stream;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        if (accountId < 0L) {
            throw new InvalidPurchaseException("Invalid Account Id");
        }

        if (ticketTypeRequests.length == 0) {
            throw new InvalidPurchaseException("No tickets requested");
        }

        boolean includesAdultTicket = Stream.of(ticketTypeRequests)
            .anyMatch(t -> t.getTicketType() == Type.ADULT);

        if (!includesAdultTicket) {
            throw new InvalidPurchaseException("Cannot request child or infant tickets without an adult");
        }
    }
}
