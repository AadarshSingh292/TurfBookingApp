package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {
    private String paymentId;
    private String bookingId;
    private String userId;
    private double amount;
    private String paymentMethod;
    private boolean isSuccessful;
    private LocalDateTime paymentTime;

    public Payment(String paymentId, String bookingId, String userId, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.isSuccessful = false;
        this.paymentTime = LocalDateTime.now();
    }

    // Getters
    public String getPaymentId() {
        return paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    // Process payment
    public void processPayment() {
        this.isSuccessful = true;
        this.paymentTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String status = isSuccessful ? " SUCCESS" : " FAILED";

        return String.format(
                "\n╔═══════════════════════════════════════════╗\n" +
                        "║           PAYMENT RECEIPT                 ║\n" +
                        "╠═══════════════════════════════════════════╣\n" +
                        "║  Payment ID : %-26s  ║\n" +
                        "║  Booking ID : %-26s  ║\n" +
                        "║  Amount     : ₹%-25.0f  ║\n" +
                        "║  Method     : %-26s  ║\n" +
                        "║  Status     : %-26s  ║\n" +
                        "║  Time       : %-26s  ║\n" +
                        "╚═══════════════════════════════════════════╝",
                paymentId, bookingId, amount, paymentMethod, status,
                paymentTime.format(formatter)
        );
    }
}
