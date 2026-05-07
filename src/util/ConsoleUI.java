package util;

public class ConsoleUI {
    // Private constructor to prevent instantiation
    private ConsoleUI() {
        throw new AssertionError("Utility class - cannot be instantiated");
    }

    /**
     * Display the application banner/logo.
     */
    public static void showBanner() {
        System.out.println("TURF BOOKING SYSTEM ");
        System.out.println("!!! Play your game !!!");
    }

    /**
     * Display the main menu (before login).
     */
    public static void showAuthMenu() {
        System.out.println("WELCOME MENU:");
        System.out.println("1. Login");
        System.out.println("2. Register(New User)");
        System.out.println("3. Exit");
    }

    /**
     * Display the main menu (after login).
     */
    public static void showMainMenu(String userName) {
        System.out.println("1. Book a Turf");
        System.out.println("2.View My Bookings");
        System.out.println("3.View my Profile");
        System.out.println("4. Logout");
        System.out.println("5. Exit");
    }

    /**
     * Display sport selection menu.
     */
    public static void showSportMenu() {
        System.out.println("!!! SELECT YOUR GAME !!!:");
        System.out.println("1. Football");
        System.out.println("2. Cricket");
        System.out.println("3. Back to main menu");
    }

    /**
     * Display payment method menu.
     */
    public static void showPaymentMenu() {
        System.out.println("SELECT PAYMENT METHOD ");
        System.out.println("1. UPI");
        System.out.println("2. Debit Card");
        System.out.println("3. Credit Card");
        System.out.println("4. Net Banking");
        System.out.println("5. Cash on Arrival");
        System.out.println("6. Cancel Payment");
    }

    /**
     * Display a section divider with title.
     */
    public static void showSectionHeader(String title) {
        int totalWidth = 50;
        int padding = (totalWidth - title.length() - 2) / 2;
        String padStr = "═".repeat(Math.max(padding, 1));
        System.out.println("\n  " + padStr + " " + title + " " + padStr);
    }

    /**
     * Display a simple divider line.
     */
    public static void showDivider() {
        System.out.println("  ────────────────────────────────────────────────");
    }

    /**
     * Display a success message.
     */
    public static void showSuccess(String message) {
        System.out.println("\n " + message);
    }

    /**
     * Display an error message.
     */
    public static void showError(String message) {
        System.out.println("\n " + message);
    }

    /**
     * Display a warning message.
     */
    public static void showWarning(String message) {
        System.out.println("\n  " + message);
    }

    /**
     * Display an info message.
     */
    public static void showInfo(String message) {
        System.out.println("\n  " + message);
    }

    /**
     * Display the goodbye message.
     */
    public static void showGoodbye() {
        System.out.println("THANKS !!! FOR BOOKING TURF");
        System.out.println("See you on the field");
    }
}
