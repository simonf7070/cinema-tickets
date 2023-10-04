package uk.gov.dwp.uc.pairtest;

import java.util.stream.Stream;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        validateAccount(accountId);
        validateTicketTypeRequests(ticketTypeRequests);

        TicketCalculator ticketCalculator = new TicketCalculatorImpl();
        var totalSeatCount = ticketCalculator.getNumberOfSeats(ticketTypeRequests);
        var totalAmountToPay = ticketCalculator.getTotalCost(ticketTypeRequests);

        SeatReservationService seatReservationService = new SeatReservationServiceImpl();
        seatReservationService.reserveSeat(accountId, totalSeatCount);

        TicketPaymentService ticketPaymentService = new TicketPaymentServiceImpl();
        ticketPaymentService.makePayment(accountId, totalAmountToPay);
    }

    private void validateTicketTypeRequests(TicketTypeRequest... ticketTypeRequests) {
        final Long maxNumbertOfTickets = 20L;

        long totalTicketCount = Stream.of(ticketTypeRequests)
            .mapToInt(x -> x.getNoOfTickets())
            .sum();

        if (totalTicketCount == 0L) {
            throw new InvalidPurchaseException("No tickets requested");
        }
        
        if (totalTicketCount > maxNumbertOfTickets) {
            throw new InvalidPurchaseException("Cannot request more than maximum number of tickets");
        }

        boolean includesAdultTicket = Stream.of(ticketTypeRequests)
            .anyMatch(t -> t.getTicketType() == Type.ADULT);

        if (!includesAdultTicket) {
            throw new InvalidPurchaseException("Cannot request child or infant tickets without an adult");
        }

        long adultCount = Stream.of(ticketTypeRequests)
            .filter(x -> x.getTicketType() == Type.ADULT)
            .mapToInt(x -> x.getNoOfTickets())
            .sum();

        long infantCount = Stream.of(ticketTypeRequests)
            .filter(x -> x.getTicketType() == Type.INFANT)
            .mapToInt(x -> x.getNoOfTickets())
            .sum();

        if (adultCount < infantCount) {
            throw new InvalidPurchaseException("Cannot request an Infant ticket without an accompanying Adult ticket");
        }
    }

    private void validateAccount(Long accountId) {
        if (accountId < 0L) {
            throw new InvalidPurchaseException("Invalid Account Id");
        }
    }
}
