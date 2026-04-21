import enums.SeatType;
import enums.Status;
import models.Booking;
import models.Movie;
import models.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class App {

    // ==================== DATA STORAGE ====================
    static List<Movie>              movieList   = new ArrayList<>();
    static Map<Integer, List<Schedule>> scheduleMap = new LinkedHashMap<>();
    static List<Booking>            bookingList = new ArrayList<>();
    static int nextId = 1;

    // ==================== DATE & TIME FORMAT ====================
    static final DateTimeFormatter FMT_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    static final DateTimeFormatter FMT_DATE     = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static final DateTimeFormatter FMT_DISPLAY  = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

    static Scanner sc = new Scanner(System.in);


    public static class Schedule {
        private final LocalDateTime showTime;
        private final SeatType      seatType;
        private final int           capacity;
        private int                 bookedSeats;
        private final int           price;

        public Schedule(LocalDateTime showTime, SeatType seatType, int capacity) {
            this.showTime    = showTime;
            this.seatType    = seatType;
            this.capacity    = capacity;
            this.bookedSeats = 0;
            // Harga berbeda per tipe kursi (Level Upgrade)
            this.price = seatType == SeatType.VIP ? 100_000 : 50_000;
        }

        public boolean isAvailable()        { return bookedSeats < capacity; }
        public boolean isFull()             { return bookedSeats >= capacity; }
        public void book()                  { bookedSeats++; }
        public int getRemainingSeats()      { return capacity - bookedSeats; }
        public LocalDateTime getShowTime()  { return showTime; }
        public SeatType getSeatType()       { return seatType; }
        public int getPrice()               { return price; }
        public int getBookedSeats()         { return bookedSeats; }

        @Override
        public String toString() {
            return String.format(
                "Tayang: %-20s | Tipe: %-7s | Harga: Rp%,-9d | Sisa Kursi: %d/%d",
                showTime.format(FMT_DISPLAY), seatType, price,
                getRemainingSeats(), capacity
            );
        }
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {
        // Tampilkan waktu login saat aplikasi dijalankan
        System.out.println("============================================");
        System.out.println("     SELAMAT DATANG DI MOVIE BOOKING SYSTEM");
        System.out.println("============================================");
        System.out.println("Waktu Akses : " + LocalDateTime.now().format(FMT_DISPLAY));
        System.out.println("Tanggal     : " + LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")));

        while (true) {
            showMenu();
            int choice = getIntInput("Pilih menu: ");

            switch (choice) {
                case 1 -> addMovieFlow();
                case 2 -> addScheduleFlow();
                case 3 -> bookingFlow();
                case 4 -> showBookingFlow();
                case 0 -> {
                    System.out.println("\nTerima kasih! Sampai jumpa :)");
                    return;
                }
                default -> System.out.println("[!] Pilihan tidak tersedia.\n");
            }
        }
    }

    // ==================== MENU ====================
    static void showMenu() {
        System.out.println("\n============================================");
        System.out.println("              MOVIE BOOKING SYSTEM          ");
        System.out.println("============================================");
        System.out.println(" 1. Tambah Film");
        System.out.println(" 2. Tambah Jadwal");
        System.out.println(" 3. Booking Tiket");
        System.out.println(" 4. Lihat Semua Booking");
        System.out.println(" 0. Keluar");
        System.out.println("============================================");
    }

    // ==================== FLOW: TAMBAH FILM ====================
    static void addMovieFlow() {
        System.out.println("\n--- Tambah Film ---");

        System.out.print("Judul film     : ");
        String title = sc.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("[!] Judul tidak boleh kosong.");
            return;
        }

        System.out.print("Genre film     : ");
        String genre = sc.nextLine().trim();
        if (genre.isEmpty()) {
            System.out.println("[!] Genre tidak boleh kosong.");
            return;
        }

        System.out.print("Tanggal rilis (yyyy-MM-dd): ");
        String dateInput = sc.nextLine().trim();

        // ===== DATE & TIME API =====
        try {
            LocalDate releaseDate = LocalDate.parse(dateInput, FMT_DATE);
            Movie movie = new Movie(title, releaseDate, genre);

            movieList.add(movie);
            scheduleMap.put(nextId, new ArrayList<>());

            System.out.println("[OK] Film berhasil ditambahkan!");
            System.out.println("     ID Film : " + nextId);
            System.out.println("     " + movie);
            nextId++;

        } catch (DateTimeParseException e) {
            System.out.println("[!] Format tanggal salah. Contoh: 2026-06-15");
        }
    }

    // ==================== FLOW: TAMBAH JADWAL ====================
    static void addScheduleFlow() {
        System.out.println("\n--- Tambah Jadwal ---");
        if (!showMovieList()) return;

        int movieId = getIntInput("Masukkan ID film: ");
        if (!scheduleMap.containsKey(movieId)) {
            System.out.println("[!] ID film tidak ditemukan.");
            return;
        }

        System.out.print("Waktu tayang (yyyy-MM-dd HH:mm): ");
        String inputDate = sc.nextLine().trim();

        System.out.print("Tipe kursi (VIP / REGULAR)      : ");
        String typeInput = sc.nextLine().trim().toUpperCase();

        int capacity = getIntInput("Kapasitas kursi                 : ");
        if (capacity <= 0) {
            System.out.println("[!] Kapasitas harus lebih dari 0.");
            return;
        }

        // ===== DATE & TIME VALIDATION =====
        try {
            LocalDateTime showTime = LocalDateTime.parse(inputDate, FMT_DATETIME);

            // Tidak boleh jadwal di masa lalu
            if (showTime.isBefore(LocalDateTime.now())) {
                System.out.println("[!] Tidak bisa menambahkan jadwal di masa lalu.");
                System.out.println("    Waktu sekarang: " + LocalDateTime.now().format(FMT_DISPLAY));
                return;
            }

            SeatType seatType = SeatType.valueOf(typeInput);
            Schedule schedule  = new Schedule(showTime, seatType, capacity);
            scheduleMap.get(movieId).add(schedule);

            System.out.println("[OK] Jadwal berhasil ditambahkan!");
            System.out.println("     Film   : " + movieList.get(movieId - 1).getTitle());
            System.out.println("     " + schedule);

        } catch (DateTimeParseException e) {
            System.out.println("[!] Format waktu salah. Contoh: 2026-04-21 14:30");
        } catch (IllegalArgumentException e) {
            System.out.println("[!] Tipe kursi tidak valid. Gunakan VIP atau REGULAR.");
        }
    }

    // ==================== FLOW: BOOKING TIKET ====================
    static void bookingFlow() {
        System.out.println("\n--- Booking Tiket ---");
        if (!showMovieList()) return;

        System.out.print("Nama pelanggan : ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("[!] Nama tidak boleh kosong.");
            return;
        }

        int movieId = getIntInput("Masukkan ID film: ");
        if (!scheduleMap.containsKey(movieId)) {
            System.out.println("[!] ID film tidak ditemukan.");
            return;
        }

        Movie selectedMovie     = movieList.get(movieId - 1);
        List<Schedule> schedules = scheduleMap.get(movieId);

        if (schedules.isEmpty()) {
            System.out.println("[!] Belum ada jadwal untuk film ini. Tambahkan jadwal terlebih dahulu.");
            return;
        }

        // Tampilkan jadwal yang tersedia (belum lewat & belum full)
        System.out.println("\nJadwal Tersedia untuk \"" + selectedMovie.getTitle() + "\":");
        System.out.println("-".repeat(75));
        boolean adaJadwal = false;
        for (int i = 0; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            // ===== DATE & TIME CHECK =====
            if (s.getShowTime().isAfter(LocalDateTime.now()) && !s.isFull()) {
                System.out.printf(" %d. %s%n", i + 1, s);
                adaJadwal = true;
            }
        }

        if (!adaJadwal) {
            System.out.println("[!] Semua jadwal sudah lewat atau penuh.");
            return;
        }

        int scheduleIdx = getIntInput("Pilih nomor jadwal: ") - 1;
        if (scheduleIdx < 0 || scheduleIdx >= schedules.size()) {
            System.out.println("[!] Nomor jadwal tidak valid.");
            return;
        }

        Schedule chosen = schedules.get(scheduleIdx);

        // Level Upgrade: validasi ulang ketersediaan
        if (chosen.getShowTime().isBefore(LocalDateTime.now())) {
            System.out.println("[!] Jadwal sudah lewat.");
            return;
        }
        if (chosen.isFull()) {
            System.out.println("[!] Maaf, kursi sudah penuh.");
            return;
        }

        // Buat kode kursi otomatis
        String seatCode = chosen.getSeatType().name().charAt(0)
                          + String.format("%03d", chosen.getBookedSeats() + 1);

        // Buat Ticket (immutable object milik Fadhil)
        Ticket ticket = new Ticket(seatCode, chosen.getSeatType(), chosen.getPrice(), Status.BOOKED);

        // Tandai kursi terpesan
        chosen.book();

        // Buat Booking (menggunakan LocalDate.now() dari Booking class)
        Booking booking = new Booking(name, selectedMovie, ticket);
        bookingList.add(booking);

        // ===== STRUK BOOKING =====
        System.out.println("\n==================== STRUK BOOKING ====================");
        System.out.println("Status          : BOOKING BERHASIL ✓");
        System.out.println("Pelanggan       : " + name);
        System.out.println("Film            : " + selectedMovie.getTitle());
        System.out.println("Waktu Tayang    : " + chosen.getShowTime().format(FMT_DISPLAY));
        System.out.println("Tipe Kursi      : " + ticket.getType());
        System.out.println("Nomor Kursi     : " + ticket.getSeat());
        System.out.printf( "Harga           : Rp%,d%n", ticket.getPrice());
        System.out.println("Waktu Booking   : " + LocalDateTime.now().format(FMT_DISPLAY));
        System.out.println("Sisa Kursi      : " + chosen.getRemainingSeats() + " kursi tersisa");
        System.out.println("=======================================================");
    }

    // ==================== FLOW: LIHAT SEMUA BOOKING ====================
    static void showBookingFlow() {
        System.out.println("\n--- Daftar Semua Booking ---");

        if (bookingList.isEmpty()) {
            System.out.println("Belum ada booking.");
            return;
        }

        System.out.printf("%-4s %-20s %-22s %-8s %-8s %-12s%n",
                "No", "Pelanggan", "Film", "Kursi", "Tipe", "Tgl Booking");
        System.out.println("-".repeat(78));

        for (int i = 0; i < bookingList.size(); i++) {
            Booking b = bookingList.get(i);
            System.out.printf("%-4d %-20s %-22s %-8s %-8s %-12s%n",
                    i + 1,
                    b.getCustomerName(),
                    b.getMovie().getTitle(),
                    b.getTicket().getSeat(),
                    b.getTicket().getType(),
                    b.getBookingDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        System.out.println("-".repeat(78));
        System.out.println("Total booking: " + bookingList.size());
    }

    // ==================== UTILITY ====================
    /**
     * Menampilkan daftar film. Return false jika kosong.
     */
    static boolean showMovieList() {
        if (movieList.isEmpty()) {
            System.out.println("[!] Belum ada film. Tambahkan film terlebih dahulu.");
            return false;
        }
        System.out.println("\nDaftar Film:");
        System.out.println("-".repeat(50));
        int id = 1;
        for (Movie m : movieList) {
            System.out.printf(" %-3d %s%n", id, m);
            id++;
        }
        System.out.println("-".repeat(50));
        return true;
    }

    /**
     * Input integer dengan validasi loop.
     */
    static int getIntInput(String message) {
        while (true) {
            try {
                if (!message.isEmpty()) System.out.print(message);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Input harus berupa angka bulat.");
            }
        }
    }
}
