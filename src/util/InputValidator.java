package util;
import java.util.regex.Pattern;

/**
 * Utility class for input validation.
 * Demonstrates utility/helper class pattern.
 */
public class InputValidator {

    // Regex patterns for validation
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[6-9]\\d{9}$");  // Indian phone number format

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-z ]{2,50}$");

    // Private constructor to prevent instantiation
    private InputValidator() {
        throw new AssertionError("Utility class - cannot be instantiated");
    }

    /**
     * Validate email format.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validate Indian phone number (10 digits starting with 6-9).
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Validate name (only letters and spaces, 2-50 chars).
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Validate age (must be between 10 and 80).
     */
    public static boolean isValidAge(int age) {
        return age >= 10 && age <= 80;
    }

    /**
     * Validate password (minimum 4 characters).
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 4;
    }

    /**
     * Validate number of players (1 to maxPlayers).
     */
    public static boolean isValidPlayerCount(int count, int maxPlayers) {
        return count >= 1 && count <= maxPlayers;
    }
}
