public static class Schedule {
        private String time;
        private String type; // VIP atau Regular
        private int capacity;
        private int bookedSeats;
        private double price;

        public Schedule(String time, String type, int capacity) {
            this.time = time;
            this.type = type;
            this.capacity = capacity;
            this.bookedSeats = 0;
            // Level Upgrade: Harga berbeda
            this.price = type.equalsIgnoreCase("VIP") ? 100000 : 50000;
        }

        public boolean bookTicket(int count) {
            // Level Upgrade: Seat terbatas & Tidak bisa booking jika full
            if (bookedSeats + count <= capacity) {
                bookedSeats += count;
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format("[%s] Tipe: %s | Harga: Rp%,.0f | Sisa Kursi: %d/%d", 
                                  time, type, price, (capacity - bookedSeats), capacity);
        }
    }
}

public class MovieBookingSystem {
    private static ArrayList<Movie> movieList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- MOVIE BOOKING SYSTEM (ADVANCED) ---");
            System.out.println("1. Add Movie");
            System.out.println("2. Add Schedule");
            System.out.println("3. Book Ticket");
            System.out.println("4. Show Booking (List Movies & Schedules)");
            System.out.println("5. Exit");
            System.out.print("Pilih menu: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: addMovie(); break;
                case 2: addSchedule(); break;
                case 3: bookTicket(); break;
                case 4: showBooking(); break;
                case 5: return;
                default: System.out.println("Pilihan tidak valid.");
            }
        }
    }
