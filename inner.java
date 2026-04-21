public class OuterClass {
    // Movie inner class
    public static class Movie {
        private String title;
        private String director;
        private int releaseYear;

        public Movie(String title, String director, int releaseYear) {
            this.title = title;
            this.director = director;
            this.releaseYear = releaseYear;
        }

        // Getters
        public String getTitle() { return title; }
        public String getDirector() { return director; }
        public int getReleaseYear() { return releaseYear; }
    }

    // Other methods for OuterClass...
}
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

