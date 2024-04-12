package ru.itmo.general.network.responses;

public class AddIfMinResponse extends Response {
    public AddIfMinResponse(boolean success, String message, Double minPrice) {
        super(success, message, minPrice);
    }
    public Double getMinPrice(){
        return (Double) getData();
    }
}