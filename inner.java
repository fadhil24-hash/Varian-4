import enums.SeatType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
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
        DateTimeFormatter FMT_DISPLAY = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        return String.format(
            "Tayang: %-20s | Tipe: %-7s | Harga: Rp%,-9d | Sisa Kursi: %d/%d",
            showTime.format(FMT_DISPLAY), seatType, price,
            getRemainingSeats(), capacity
        );
    }
}
