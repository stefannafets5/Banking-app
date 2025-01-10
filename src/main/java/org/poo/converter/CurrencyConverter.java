package org.poo.converter;

import org.poo.bank.ExchangeRate;
import org.poo.fileio.ObjectInput;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;


/**
 * The type Currency converter.
 */
public final class CurrencyConverter {
    private static CurrencyConverter instance;
    private final ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();

    private CurrencyConverter(final ObjectInput input) {
        for (int i = 0; i < input.getExchangeRates().length; i++) {
            this.exchangeRates.add(new ExchangeRate(input.getExchangeRates()[i].getFrom(),
                                                    input.getExchangeRates()[i].getTo(),
                                                    input.getExchangeRates()[i].getRate()));
        }
    }

    /**
     * Gets instance.
     *
     * @param input the input
     * @return the instance
     */
    public static CurrencyConverter getInstance(final ObjectInput input) {
        if (instance == null) {
            instance = new CurrencyConverter(input);
        }
        return instance;
    }

    /**
     * Reset instance.
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * Convert double.
     *
     * @param amount the amount
     * @param from   the from
     * @param to     the to
     * @return the double
     */
    public double convert(final double amount, final String from, final String to) {
        if (from.equals(to)) {
            return amount;
        }

        // direct conversion
        for (ExchangeRate rate : exchangeRates) {
            if (rate.getFrom().equals(from) && rate.getTo().equals(to)) {
                return amount * rate.getRate();
            }
        }

        // indirect conversion
        double result = indirectConversion(amount, from, to);
        if (result == -1) {
            System.out.println("Conversion between " + from + " and " + to + " is not possible");
        }
        return result;
    }

    private double indirectConversion(final double amount, final String from, final String to) {
        Map<String, Map<String, Double>> graph = buildExchangeGraph();

        Queue<String> queue = new LinkedList<>();
        Map<String, Double> visited = new HashMap<>();
        visited.put(from, 1.0);

        queue.add(from);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(to)) { // found currency
                return amount * visited.get(current);
            }

            if (graph.containsKey(current)) {
                // iterate trough every neighbour
                for (Map.Entry<String, Double> entry : graph.get(current).entrySet()) {
                    String neighbor = entry.getKey();
                    double rate = entry.getValue();
                    if (!visited.containsKey(neighbor)) {
                        visited.put(neighbor, visited.get(current) * rate);
                        queue.add(neighbor);
                    }
                }
            }
        }
        return -1; // no conversion rate found
    }

    private Map<String, Map<String, Double>> buildExchangeGraph() {
        Map<String, Map<String, Double>> graph = new HashMap<>();

        for (ExchangeRate rate : exchangeRates) {
            String from = rate.getFrom();
            String to = rate.getTo();
            double rateVal = rate.getRate();

            // normal conversion
            graph.computeIfAbsent(from, k -> new HashMap<>()).put(to, rateVal);
            // reverse conversion
            graph.computeIfAbsent(to, k -> new HashMap<>()).put(from, 1.0 / rateVal);
        }
        return graph;
    }
}
