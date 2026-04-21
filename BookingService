package services;

import models.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookingService {
    private ArrayList<Movie> movies = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();

    public void addMovie(String title, String genre, LocalDate releaseDate) {
        Movie movie = new Movie(title, releaseDate, genre);
        movies.add(movie);
        System.out.println("Movie added successfully!\n");
    }

    public void showMovies() {
        if (movies.isEmpty()) {
            System.out.println("No movies available.\n");
            return;
        }

        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ". " + movies.get(i));
        }
        System.out.println();
    }

    public void bookTicket(String customerName, int movieIndex) {
        if (movieIndex < 0 || movieIndex >= movies.size()) {
            System.out.println("Invalid movie selection.\n");
            return;
        }

        Movie selectedMovie = movies.get(movieIndex);
        Ticket ticket = new Ticket("A1", enums.Type.VIP, 100000, enums.Status.BOOKED);
        Booking booking = new Booking(customerName, selectedMovie, ticket);
        bookings.add(booking);

        System.out.println("Booking success!\n");
    }

    public void showBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.\n");
            return;
        }

        for (Booking booking : bookings) {
            System.out.println(booking);
        }
        System.out.println();
    }
}
