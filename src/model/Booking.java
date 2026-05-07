package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {
    private String bookingId;
    private User user;
    private Sport sport;
    private Slot slot;
    private int numberOfPlayers;
    private double totalAmount;
    private boolean isPaid;
    private LocalDateTime bookingTime;

    private static final int MAX_PLAYERS = 5;

    public Booking(String bookingId, User user, Sport sport, Slot slot, int numberOfPlayers) {
        this.bookingId = bookingId;
        this.user = user;
        this.sport = sport;
        this.slot = slot;
        this.numberOfPlayers = Math.min(numberOfPlayers, MAX_PLAYERS); // Cap at 5
        this.totalAmount = sport.calculateTotalCost(this.numberOfPlayers);
        this.isPaid = false;
        this.bookingTime = LocalDateTime.now();
    }

    // Getters
    public String getBookingId() {
        return bookingId;
    }

    public User getUser() {
        return user;
    }

    public Sport getSport() {
        return sport;
    }

    public Slot getSlot() {
        return slot;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public static int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    // Mark booking as paid
    public void markAsPaid() {
        this.isPaid = true;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String paymentStatus = isPaid ? " PAID" : " UNPAID";

        return String.format(
                "\n╔═══════════════════════════════════════════════════╗\n" +
                        "║              BOOKING CONFIRMATION                 ║\n" +
                        "╠═══════════════════════════════════════════════════╣\n" +
                        "║  Booking ID    : %-30s  ║\n" +
                        "║  Booked By     : %-30s  ║\n" +
                        "║  Email         : %-30s  ║\n" +
                        "║  Phone         : %-30s  ║\n" +
                        "║  Sport         : %-30s  ║\n" +
                        "║  Turf          : %-30s  ║\n" +
                        "║  Time Slot     : %-30s  ║\n" +
                        "║  No. of Players: %-30d  ║\n" +
                        "║  Rate/Person   : ₹%-29.0f  ║\n" +
                        "║  Total Amount  : ₹%-29.0f  ║\n" +
                        "║  Payment       : %-30s  ║\n" +
                        "║  Booked At     : %-30s  ║\n" +
                        "╚═══════════════════════════════════════════════════╝",
                bookingId, user.getName(), user.getEmail(), user.getPhoneNumber(),
                sport.getSportName(), slot.getTurfName(), slot.getTimeRange(),
                numberOfPlayers, sport.getRatePerPerson(), totalAmount,
                paymentStatus, bookingTime.format(formatter)
        );
    }
}
