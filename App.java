import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import services.MovieService;
import services.BookingService;
import enums.SeatType;

public class App {

    static Scanner sc = new Scanner(System.in);
    static MovieService movieService = new MovieService();
    static BookingService bookingService = new BookingService();

    public static void main(String[] args) {

        while (true) {
            showMenu();
            int choice = getIntInput("Pilih menu: ");

            switch (choice) {
                case 1:
                    addMovieFlow();
                    break;
                case 2:
                    addScheduleFlow();
                    break;
                case 3:
                    bookingFlow();
                    break;
                case 4:
                    movieService.showMovies();
                    break;
                case 0:
                    System.out.println("Keluar dari sistem...");
                    return;
                default:
                    System.out.println("Pilihan tidak tersedia.\n");
            }
        }
    }

    // ================= MENU =================
    static void showMenu() {
        System.out.println("\n=== MOVIE BOOKING SYSTEM ===");
        System.out.println("1. Tambah Film");
        System.out.println("2. Tambah Jadwal");
        System.out.println("3. Booking Tiket");
        System.out.println("4. Lihat Film");
        System.out.println("0. Exit");
    }

    // ================= FLOW =================
    static void addMovieFlow() {
        System.out.print("Judul film: ");
        String title = sc.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Judul tidak boleh kosong.");
            return;
        }

        movieService.addMovie(title);
        System.out.println("Film berhasil ditambahkan.");
    }

    static void addScheduleFlow() {
        int movieId = getIntInput("Masukkan ID film: ");

        System.out.print("Masukkan tanggal (format: yyyy-MM-dd HH:mm): ");
        String inputDate = sc.nextLine();

        try {
            LocalDateTime dateTime = parseDateTime(inputDate);

            // 🔥 VALIDASI WAKTU (WAJIB)
            if (dateTime.isBefore(LocalDateTime.now())) {
                System.out.println("Tidak bisa menambahkan jadwal di masa lalu.");
                return;
            }

            movieService.addSchedule(movieId, dateTime);
            System.out.println("Jadwal berhasil ditambahkan.");

        } catch (Exception e) {
            System.out.println("Format salah. Contoh: 2026-04-21 14:30");
        }
    }

    static void bookingFlow() {
        System.out.print("Nama: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Nama tidak boleh kosong.");
            return;
        }

        int movieId = getIntInput("Masukkan ID film: ");

        System.out.print("Tipe kursi (VIP / REGULAR): ");
        String typeInput = sc.nextLine();

        try {
            SeatType seatType = SeatType.valueOf(typeInput.toUpperCase());

            bookingService.bookTicket(name, movieId, seatType);

        } catch (IllegalArgumentException e) {
            System.out.println("Tipe kursi tidak valid.");
        }
    }

    // ================= UTIL =================
    static LocalDateTime parseDateTime(String input) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(input, formatter);
    }

    static int getIntInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(sc.nextLine());
                return value;
            } catch (Exception e) {
                System.out.println("Input harus angka.");
            }
        }
    }
}
