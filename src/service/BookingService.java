package service;
import model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class BookingService {
    private List<Slot> allSlots;
    private List<Booking> allBookings;
    private int bookingIdCounter;

    // Available turfs
    private static final String[] TURF_NAMES = {
            "Green Arena", "Star Sports Turf", "Champions Ground"
    };

    // Time slots
    private static final String[][] TIME_SLOTS = {
            {"06:00 AM", "07:00 AM"},
            {"07:00 AM", "08:00 AM"},
            {"08:00 AM", "09:00 AM"},
            {"09:00 AM", "10:00 AM"},
            {"04:00 PM", "05:00 PM"},
            {"05:00 PM", "06:00 PM"},
            {"06:00 PM", "07:00 PM"},
            {"07:00 PM", "08:00 PM"},
            {"08:00 PM", "09:00 PM"},
            {"09:00 PM", "10:00 PM"}
    };

    public BookingService() {
        this.allSlots = new ArrayList<>();
        this.allBookings = new ArrayList<>();
        this.bookingIdCounter = 5000;
        initializeSlots();
    }

    /**
     * Initialize all available slots for both sports across all turfs.
     */
    private void initializeSlots() {
        int slotId = 1;
        for (String turfName : TURF_NAMES) {
            // Football slots
            for (String[] timeSlot : TIME_SLOTS) {
                allSlots.add(new Slot(slotId++, timeSlot[0], timeSlot[1], "Football", turfName));
            }
            // Cricket slots
            for (String[] timeSlot : TIME_SLOTS) {
                allSlots.add(new Slot(slotId++, timeSlot[0], timeSlot[1], "Cricket", turfName));
            }
        }
    }

    /**
     * Get available (unbooked) slots for a specific sport.
     */
    public List<Slot> getAvailableSlots(String sportType) {
        return allSlots.stream()
                .filter(slot -> slot.getSportType().equalsIgnoreCase(sportType) && !slot.isBooked())
                .collect(Collectors.toList());
    }

    /**
     * Get available slots for a sport on a specific turf.
     */
    public List<Slot> getAvailableSlotsByTurf(String sportType, String turfName) {
        return allSlots.stream()
                .filter(slot -> slot.getSportType().equalsIgnoreCase(sportType)
                        && slot.getTurfName().equalsIgnoreCase(turfName)
                        && !slot.isBooked())
                .collect(Collectors.toList());
    }

    /**
     * Get all unique turf names that have available slots for a sport.
     */
    public List<String> getAvailableTurfs(String sportType) {
        return allSlots.stream()
                .filter(slot -> slot.getSportType().equalsIgnoreCase(sportType) && !slot.isBooked())
                .map(Slot::getTurfName)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Create a new booking.
     * @return the created Booking, or null if slot is already booked
     */
    public Booking createBooking(User user, Sport sport, Slot slot, int numberOfPlayers) {
        if (slot.isBooked()) {
            System.out.println("\n  ⚠️  Sorry! This slot has already been booked.");
            return null;
        }

        // Cap players at 5
        if (numberOfPlayers > Booking.getMaxPlayers()) {
            System.out.println("\n    Maximum " + Booking.getMaxPlayers() + " players allowed per booking.");
            numberOfPlayers = Booking.getMaxPlayers();
            System.out.println("    Number of players set to " + numberOfPlayers);
        }

        if (numberOfPlayers < 1) {
            System.out.println("\n    Minimum 1 player required for booking.");
            return null;
        }

        String bookingId = "BKG" + (++bookingIdCounter);
        Booking booking = new Booking(bookingId, user, sport, slot, numberOfPlayers);
        slot.bookSlot(user.getUserId());
        allBookings.add(booking);

        return booking;
    }

    /**
     * Get all bookings for a specific user.
     */
    public List<Booking> getUserBookings(String userId) {
        return allBookings.stream()
                .filter(booking -> booking.getUser().getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    /**
     * Get a booking by its ID.
     */
    public Booking getBookingById(String bookingId) {
        return allBookings.stream()
                .filter(booking -> booking.getBookingId().equals(bookingId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get a slot by its ID.
     */
    public Slot getSlotById(int slotId) {
        return allSlots.stream()
                .filter(slot -> slot.getSlotId() == slotId)
                .findFirst()
                .orElse(null);
    }

    /**
     * Get all turf names.
     */
    public String[] getTurfNames() {
        return TURF_NAMES;
    }

    /**
     * Get total number of bookings.
     */
    public int getTotalBookings() {
        return allBookings.size();
    }
}
