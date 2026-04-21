import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Movie {
    private String title;
    private LocalDate releaseDate;
    private String genre;

    public Movie(String title, LocalDate releaseDate, String genre) {
        this.title = title.trim();
        this.releaseDate = releaseDate;
        this.genre = genre.trim();
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "Movie: " + title +
                " | Genre: " + genre +
                " | Release: " + releaseDate.format(formatter);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Movie movie = (Movie) obj;
        return Objects.equals(title, movie.title);
    }
    
      public static class Schedule {
        private String studio;
        private String showTime;
        private LocalDate showDate;

        public Schedule(String studio, String showTime, LocalDate showDate) {
            this.studio = studio.trim();
            this.showTime = showTime.trim();
            this.showDate = showDate;
        }

        public String getStudio() {
            return studio;
        }

        public String getShowTime() {
            return showTime;
        }

        public LocalDate getShowDate() {
            return showDate;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy");

            return "Studio: " + studio +
                    " | Time: " + showTime +
                    " | Date: " + showDate.format(formatter);
        }
    }
}
