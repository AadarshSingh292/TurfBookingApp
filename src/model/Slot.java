package model;

public class Slot {
    private int slotId;
    private String startTime;
    private String endTime;
    private String sportType;    // "Football" or "Cricket"
    private boolean isBooked;
    private String bookedByUserId;
    private String turfName;

    public Slot(int slotId, String startTime, String endTime, String sportType, String turfName) {
        this.slotId = slotId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sportType = sportType;
        this.turfName = turfName;
        this.isBooked = false;
        this.bookedByUserId = null;
    }

    // Getters
    public int getSlotId() {
        return slotId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSportType() {
        return sportType;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public String getBookedByUserId() {
        return bookedByUserId;
    }

    public String getTurfName() {
        return turfName;
    }

    // Book the slot
    public boolean bookSlot(String userId) {
        if (!isBooked) {
            this.isBooked = true;
            this.bookedByUserId = userId;
            return true;
        }
        return false;
    }

    // Cancel the booking
    public void cancelSlot() {
        this.isBooked = false;
        this.bookedByUserId = null;
    }

    public String getTimeRange() {
        return startTime + " - " + endTime;
    }

    @Override
    public String toString() {
        String status = isBooked ? " BOOKED" : " AVAILABLE";
        return String.format("│ Slot %-2d │ %-7s - %-7s │ %-10s │ %-15s │ %-12s │",
                slotId, startTime, endTime, sportType, turfName, status);
    }
}
