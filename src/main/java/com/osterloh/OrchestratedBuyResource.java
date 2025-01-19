package com.osterloh;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("orchestrated-buy")
public class OrchestratedBuyResource {

    @Inject
    CreditService creditService;

    @Inject
    OrderService orderService;

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response saga() {
        Long id = 0L;

        buy(++id, 20);
        buy(++id, 30);
        buy(++id, 30);
        buy(++id, 25);

        return Response.ok().build();
    }

    private void buy(Long id, int value) {
        orderService.newOrder(id);

        try {
            creditService.newValueOrder(id, value);
            System.out.println("Order " + id + " registered in value of " + value + ". Available balance: " + creditService.getFullCredit());

        } catch ( IllegalStateException e) {
            orderService.cancelOrder(id);
            System.err.println("Order " + id + " cancelled in value of " + value);
        }
    }
}
