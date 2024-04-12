package ru.itmo.general.network.responses;

public class SumOfPriceResponse extends Response {
    public SumOfPriceResponse(boolean success, String message, double sum) {
        super(success, message, sum);
    }

    public double getSum() {
        return (double) getData();
    }
}