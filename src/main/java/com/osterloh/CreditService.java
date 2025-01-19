package com.osterloh;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class CreditService {

    private int fullCredit;
    private Map<Long, Integer> valueOrder = new HashMap<>();

    public CreditService() {
        this.fullCredit = 100;
    }

    public void newValueOrder(Long orderId, int value) {
        if (value > fullCredit) {
            throw new IllegalStateException("Insufficientbalance!");
        }

        fullCredit = fullCredit - value;
        valueOrder.put(orderId, value);
    }

    public void cancelOrder(Long id) {
        fullCredit = fullCredit + valueOrder.get(id);
        valueOrder.remove(id);
    }

    public int getFullCredit() {
        return fullCredit;
    }
}
