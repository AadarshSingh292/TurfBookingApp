package service;
import model.Booking;
import model.Payment;
import java.util.ArrayList;
import java.util.List;
public class PaymentService {

    private List<Payment> allPayments;
    private int paymentIdCounter;

    // Payment methods available
    public static final String[] PAYMENT_METHODS = {
            "UPI (Google Pay / PhonePe / Paytm)",
            "Credit Card",
            "Debit Card",
            "Net Banking",
            "Cash on Arrival"
    };

    public PaymentService() {
        this.allPayments = new ArrayList<>();
        this.paymentIdCounter = 9000;
    }

    /**
     * Process payment for a booking.
     * @return the Payment object if successful, null otherwise
     */
    public Payment processPayment(Booking booking, String paymentMethod) {
        if (booking == null) {
            System.out.println("\n  Invalid booking! Cannot process payment.");
            return null;
        }

        if (booking.isPaid()) {
            System.out.println("\n   This booking has already been paid for.");
            return null;
        }

        String paymentId = "PAY" + (++paymentIdCounter);
        Payment payment = new Payment(
                paymentId,
                booking.getBookingId(),
                booking.getUser().getUserId(),
                booking.getTotalAmount(),
                paymentMethod
        );

        // Simulate payment processing
        System.out.println("\n  Processing payment...");
        simulatePaymentProcessing();

        payment.processPayment();
        booking.markAsPaid();
        allPayments.add(payment);

        System.out.println("  Payment of ₹" + String.format("%.0f", booking.getTotalAmount()) + " received successfully!");

        return payment;
    }

    /**
     * Simulate payment processing delay.
     */
    private void simulatePaymentProcessing() {
        try {
            String[] steps = {
                    "   Verifying payment details...",
                    "   Connecting to payment gateway...",
                    "   Authenticating transaction...",
                    "   Confirming payment..."
            };
            for (String step : steps) {
                System.out.println(step);
                Thread.sleep(600);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Get payment history for a user.
     */
    public List<Payment> getUserPayments(String userId) {
        List<Payment> userPayments = new ArrayList<>();
        for (Payment payment : allPayments) {
            if (payment.getUserId().equals(userId)) {
                userPayments.add(payment);
            }
        }
        return userPayments;
    }

    /**
     * Get total revenue collected.
     */
    public double getTotalRevenue() {
        double total = 0;
        for (Payment payment : allPayments) {
            if (payment.isSuccessful()) {
                total += payment.getAmount();
            }
        }
        return total;
    }
}
