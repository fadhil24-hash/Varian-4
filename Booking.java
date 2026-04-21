package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Booking {
    private String    customerName;
    private Movie     movie;
    private Ticket    ticket;
    private LocalDate bookingDate;  // Date & Time API

    public Booking(String customerName, Movie movie, Ticket ticket) {
        this.customerName = customerName.trim();
        this.movie        = movie;
        this.ticket       = ticket;
        this.bookingDate  = LocalDate.now(); // otomatis isi tanggal saat booking
    }

    public String    getCustomerName() { return customerName; }
    public Movie     getMovie()        { return movie; }
    public Ticket    getTicket()       { return ticket; }
    public LocalDate getBookingDate()  { return bookingDate; } // ← tambahan getter

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "Customer: " + customerName +
                " | Film: "    + movie.getTitle() +
                " | Kursi: "   + ticket.getSeat() +
                " | Tgl: "     + bookingDate.format(formatter);
    }
}
