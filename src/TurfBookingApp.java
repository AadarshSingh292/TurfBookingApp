
import model.*;
import service.*;
import util.*;

import java.util.List;
import java.util.Scanner;
public class TurfBookingApp {


    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService = new AuthService();
    private static BookingService bookingService = new BookingService();
    private static PaymentService paymentService = new PaymentService();
    private static User currentUser = null;

    public static void main(String[] args) {
        ConsoleUI.showBanner();
        runAuthLoop();
        scanner.close();
    }

    // ==================== AUTHENTICATION FLOW ====================

    /**
     * Main authentication loop - Login / Register / Exit.
     */
    private static void runAuthLoop() {
        while (true) {
            ConsoleUI.showAuthMenu();
            System.out.print("\n Enter your choice: ");
            int choice = readInt();

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleRegistration();
                    break;
                case 3:
                    ConsoleUI.showGoodbye();
                    return;
                default:
                    ConsoleUI.showError("Invalid choice! Please select 1, 2, or 3.");
            }
        }
    }

    /**
     * Handle user login.
     */
    private static void handleLogin() {
        ConsoleUI.showSectionHeader("LOGIN");

        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();

        System.out.print(" Enter Password: ");
        String password = scanner.nextLine().trim();

        User user = authService.login(email, password);
        if (user != null) {
            currentUser = user;
            runMainMenu();
        }
    }

    /**
     * Handle new user registration.
     */
    private static void handleRegistration() {
        ConsoleUI.showSectionHeader("REGISTRATION");

        // Get and validate name
        String name;
        while (true) {
            System.out.print("Enter Full Name: ");
            name = scanner.nextLine().trim();
            if (InputValidator.isValidName(name)) break;
            ConsoleUI.showError("Invalid name! Use only letters and spaces (2-50 chars).");
        }

        // Get and validate email
        String email;
        while (true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine().trim();
            if (InputValidator.isValidEmail(email)) break;
            ConsoleUI.showError("Invalid email format! Example: user@example.com");
        }

        // Get and validate phone
        String phone;
        while (true) {
            System.out.print("Enter Phone Number (10 digits): ");
            phone = scanner.nextLine().trim();
            if (InputValidator.isValidPhone(phone)) break;
            ConsoleUI.showError("Invalid phone! Enter 10-digit Indian number (starting with 6-9).");
        }

        // Get and validate age
        int age;
        while (true) {
            System.out.print("Enter Age: ");
            age = readInt();
            if (InputValidator.isValidAge(age)) break;
            ConsoleUI.showError("Invalid age! Must be between 10 and 80.");
        }

        // Get and validate password
        String password;
        while (true) {
            System.out.print("Create Password (min 4 chars): ");
            password = scanner.nextLine().trim();
            if (InputValidator.isValidPassword(password)) break;
            ConsoleUI.showError("Password too short! Minimum 4 characters required.");
        }

        // Confirm password
        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine().trim();
        if (!password.equals(confirmPassword)) {
            ConsoleUI.showError("Passwords do not match! Registration cancelled.");
            return;
        }

        User user = authService.register(name, email, phone, age, password);
        if (user != null) {
            currentUser = user;
            runMainMenu();
        }
    }

    // ==================== MAIN MENU FLOW ====================

    /**
     * Main menu loop after successful login.
     */
    private static void runMainMenu() {
        while (true) {
            ConsoleUI.showMainMenu(currentUser.getName());
            System.out.print("\n Enter your choice: ");
            int choice = readInt();

            switch (choice) {
                case 1:
                    handleBookTurf();
                    break;
                case 2:
                    handleViewBookings();
                    break;
                case 3:
                    handleViewProfile();
                    break;
                case 4:
                    ConsoleUI.showSuccess("Logged out successfully!");
                    currentUser = null;
                    return;
                case 5:
                    ConsoleUI.showGoodbye();
                    System.exit(0);
                default:
                    ConsoleUI.showError("Invalid choice! Please select 1-5.");
            }
        }
    }

    // ==================== BOOKING FLOW ====================

    /**
     * Handle the complete turf booking flow:
     * Select Sport → Select Turf → Select Slot → Enter Players → Payment
     */
    private static void handleBookTurf() {
        // STEP 1: Select Sport
        ConsoleUI.showSportMenu();
        System.out.print("\n Select sport: ");
        int sportChoice = readInt();

        Sport selectedSport;
        String sportType;

        switch (sportChoice) {
            case 1:
                selectedSport = new Football();   // Polymorphism: Sport reference to Football
                sportType = "Football";
                break;
            case 2:
                selectedSport = new Cricket();     // Polymorphism: Sport reference to Cricket
                sportType = "Cricket";
                break;
            case 3:
                return;
            default:
                ConsoleUI.showError("Invalid sport selection!");
                return;
        }

        // Display sport details (Polymorphism in action)
        ConsoleUI.showSectionHeader(sportType.toUpperCase() + " DETAILS");
        System.out.println("\n  " + selectedSport.getSportDescription());
        System.out.println("  Equipment: " + selectedSport.getEquipmentProvided());
        System.out.println("  Turf Size: " + selectedSport.getTurfSize());
        System.out.println("  Rate: ₹" + String.format("%.0f", selectedSport.getRatePerPerson()) + " per person");

        // STEP 2: Select Turf
        ConsoleUI.showSectionHeader("SELECT TURF");
        List<String> availableTurfs = bookingService.getAvailableTurfs(sportType);

        if (availableTurfs.isEmpty()) {
            ConsoleUI.showWarning("No turfs available for " + sportType + " at the moment.");
            return;
        }

        System.out.println("\n  Available Turfs for " + sportType + ":");
        ConsoleUI.showDivider();
        for (int i = 0; i < availableTurfs.size(); i++) {
            System.out.println("  " + (i + 1) + ". Turf Available  " + availableTurfs.get(i));
        }
        System.out.println("  " + (availableTurfs.size() + 1) + ".Back");
        ConsoleUI.showDivider();

        System.out.print("\n  Select turf: ");
        int turfChoice = readInt();

        if (turfChoice < 1 || turfChoice > availableTurfs.size()) {
            if (turfChoice == availableTurfs.size() + 1) return;
            ConsoleUI.showError("Invalid turf selection!");
            return;
        }

        String selectedTurf = availableTurfs.get(turfChoice - 1);

        // STEP 3: Select Time Slot
        ConsoleUI.showSectionHeader("AVAILABLE TIME SLOTS");
        List<Slot> availableSlots = bookingService.getAvailableSlotsByTurf(sportType, selectedTurf);

        if (availableSlots.isEmpty()) {
            ConsoleUI.showWarning("No available slots on " + selectedTurf + " for " + sportType + ".");
            return;
        }

        System.out.println("\n " + selectedTurf + " - " + sportType + " Slots:\n");
        System.out.println("  ┌──────┬─────────────────────────┬──────────────┐");
        System.out.println("  │  #   │     Time Slot           │    Status    │");
        System.out.println("  ├──────┼─────────────────────────┼──────────────┤");
        for (int i = 0; i < availableSlots.size(); i++) {
            Slot slot = availableSlots.get(i);
            System.out.printf("  │  %-3d │  %-7s - %-7s      │ AVAILABLE │%n",
                    (i + 1), slot.getStartTime(), slot.getEndTime());
        }
        System.out.println("  └──────┴─────────────────────────┴──────────────┘");

        System.out.print("\n  Select time slot (1-" + availableSlots.size() + "): ");
        int slotChoice = readInt();

        if (slotChoice < 1 || slotChoice > availableSlots.size()) {
            ConsoleUI.showError("Invalid slot selection!");
            return;
        }

        Slot selectedSlot = availableSlots.get(slotChoice - 1);

        // STEP 4: Enter Number of Players
        ConsoleUI.showSectionHeader("NUMBER OF PLAYERS");
        System.out.println("\n Maximum " + Booking.getMaxPlayers() + " players allowed per booking.");
        System.out.println("  Rate: ₹" + String.format("%.0f", selectedSport.getRatePerPerson()) + " per person.");

        int numPlayers;
        while (true) {
            System.out.print("\n Enter number of players (1-" + Booking.getMaxPlayers() + "): ");
            numPlayers = readInt();
            if (InputValidator.isValidPlayerCount(numPlayers, Booking.getMaxPlayers())) break;
            ConsoleUI.showError("Invalid! Enter 1 to " + Booking.getMaxPlayers() + " players.");
        }

        // Show booking summary before payment
        double totalCost = selectedSport.calculateTotalCost(numPlayers);
        ConsoleUI.showSectionHeader("BOOKING SUMMARY");
        System.out.println(
                "\n  ┌─────────────────────────────────────────┐\n" +
                        "  │         BOOKING SUMMARY                  │\n" +
                        "  ├─────────────────────────────────────────┤\n" +
                        String.format("  │  Sport       : %-24s │%n", sportType) +
                        String.format("  │  Turf        : %-24s │%n", selectedTurf) +
                        String.format("  │  Time        : %-24s │%n", selectedSlot.getTimeRange()) +
                        String.format("  │  Players     : %-24d │%n", numPlayers) +
                        String.format("  │  Rate/Person : ₹%-23.0f │%n", selectedSport.getRatePerPerson()) +
                        String.format("  │  Total Cost  : ₹%-23.0f │%n", totalCost) +
                        "  └─────────────────────────────────────────┘"
        );

        System.out.print("\n Confirm booking? (Y/N): ");
        String confirm = scanner.nextLine().trim().toUpperCase();

        if (!confirm.equals("Y")) {
            ConsoleUI.showInfo("Booking cancelled.");
            return;
        }

        // STEP 5: Create Booking
        Booking booking = bookingService.createBooking(currentUser, selectedSport, selectedSlot, numPlayers);
        if (booking == null) {
            ConsoleUI.showError("Failed to create booking. Please try again.");
            return;
        }

        // STEP 6: Payment
        handlePayment(booking);
    }

    // ==================== PAYMENT FLOW ====================

    /**
     * Handle payment for a booking.
     */
    private static void handlePayment(Booking booking) {
        ConsoleUI.showSectionHeader("PAYMENT");
        System.out.println("\n Amount to pay: ₹" + String.format("%.0f", booking.getTotalAmount()));

        ConsoleUI.showPaymentMenu();
        System.out.print("\n Select payment method: ");
        int payChoice = readInt();

        if (payChoice < 1 || payChoice > 6) {
            ConsoleUI.showError("Invalid payment method!");
            return;
        }

        if (payChoice == 6) {
            ConsoleUI.showInfo("Payment cancelled. Your booking is saved but unpaid.");
            ConsoleUI.showWarning("Please complete payment from 'View My Bookings' to confirm your slot.");
            return;
        }

        String paymentMethod = PaymentService.PAYMENT_METHODS[payChoice - 1];

        Payment payment = paymentService.processPayment(booking, paymentMethod);
        if (payment != null) {
            // Show payment receipt
            System.out.println(payment);

            // Show booking confirmation
            System.out.println(booking);

            System.out.println("\n Your turf has been booked successfully!");
            System.out.println(" Arrive 10 minutes before your slot time.");
            System.out.println(" Enjoy your game!\n");
        }
    }

    // ==================== VIEW BOOKINGS ====================

    /**
     * Display all bookings for the current user.
     */
    private static void handleViewBookings() {
        ConsoleUI.showSectionHeader("MY BOOKINGS");

        List<Booking> myBookings = bookingService.getUserBookings(currentUser.getUserId());

        if (myBookings.isEmpty()) {
            ConsoleUI.showInfo("You haven't made any bookings yet.");
            ConsoleUI.showInfo("Go to 'Book a Turf' to make your first booking!");
            return;
        }

        System.out.println("\n Total Bookings: " + myBookings.size());
        ConsoleUI.showDivider();

        for (int i = 0; i < myBookings.size(); i++) {
            System.out.println("\n  --- Booking " + (i + 1) + " ---");
            System.out.println(myBookings.get(i));
        }

        // Check for unpaid bookings
        boolean hasUnpaid = false;
        for (Booking b : myBookings) {
            if (!b.isPaid()) {
                hasUnpaid = true;
                break;
            }
        }

        if (hasUnpaid) {
            System.out.print("\n Do you want to pay for an unpaid booking? (Y/N): ");
            String pay = scanner.nextLine().trim().toUpperCase();
            if (pay.equals("Y")) {
                System.out.print("  Enter Booking ID (e.g., BKG5001): ");
                String bookingId = scanner.nextLine().trim().toUpperCase();
                Booking unpaidBooking = bookingService.getBookingById(bookingId);
                if (unpaidBooking != null && !unpaidBooking.isPaid()
                        && unpaidBooking.getUser().getUserId().equals(currentUser.getUserId())) {
                    handlePayment(unpaidBooking);
                } else {
                    ConsoleUI.showError("Invalid Booking ID or booking is already paid.");
                }
            }
        }
    }

    // ==================== VIEW PROFILE ====================

    /**
     * Display the current user's profile.
     */
    private static void handleViewProfile() {
        ConsoleUI.showSectionHeader("MY PROFILE");
        System.out.println("\n" + currentUser);

        int totalBookings = bookingService.getUserBookings(currentUser.getUserId()).size();
        System.out.println("\n Total Bookings: " + totalBookings);
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Safely read an integer from console input.
     */
    private static int readInt() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
