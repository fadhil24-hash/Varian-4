package models;

import enums.Status;
import enums.Type;

public final class Ticket {
    private final String seat;
    private final Type type;
    private final int price;
    private final Status status;

    public Ticket(String seat, Type type, int price, Status status) {
        this.seat = seat;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public String getSeat() {
        return seat;
    }

    public Type getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Seat: " + seat +
                " | Type: " + type +
                " | Price: " + price +
                " | Status: " + status;
    }
}
