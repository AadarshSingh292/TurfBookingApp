package service;
import model.User;
import java.util.HashMap;
import java.util.Map;
public class AuthService {
    // In-memory user storage (simulates database)
    private Map<String, User> usersByEmail;
    private int userIdCounter;

    public AuthService() {
        this.usersByEmail = new HashMap<>();
        this.userIdCounter = 1000;
    }

    /**
     * Register a new user with validation.
     * @return the registered User object, or null if registration fails
     */
    public User register(String name, String email, String phone, int age, String password) {
        // Check if email already exists
        if (usersByEmail.containsKey(email.toLowerCase())) {
            System.out.println("\n    Email already registered! Please login instead.");
            return null;
        }

        // Create new user
        String userId = "USR" + (++userIdCounter);
        User newUser = new User(userId, name, email.toLowerCase(), phone, age, password);
        usersByEmail.put(email.toLowerCase(), newUser);

        System.out.println("\n   Registration successful! Welcome, " + name + "!");
        System.out.println("   Your User ID: " + userId);

        return newUser;
    }

    /**
     * Login an existing user.
     * @return the User object if credentials are valid, null otherwise
     */
    public User login(String email, String password) {
        User user = usersByEmail.get(email.toLowerCase());

        if (user == null) {
            System.out.println("\n   No account found with this email. Please register first.");
            return null;
        }

        if (!user.validatePassword(password)) {
            System.out.println("\n  Incorrect password! Please try again.");
            return null;
        }

        System.out.println("\n   Login successful! Welcome back, " + user.getName() + "!");
        return user;
    }

    /**
     * Check if an email is already registered.
     */
    public boolean isEmailRegistered(String email) {
        return usersByEmail.containsKey(email.toLowerCase());
    }

    /**
     * Get total number of registered users.
     */
    public int getTotalUsers() {
        return usersByEmail.size();
    }
}
