package model;

public abstract class Sport {
    private String sportName;
    private int maxPlayersPerTeam;
    private double ratePerPerson;

    public Sport(String sportName, int maxPlayersPerTeam, double ratePerPerson) {
        this.sportName = sportName;
        this.maxPlayersPerTeam = maxPlayersPerTeam;
        this.ratePerPerson = ratePerPerson;
    }

    // Abstract methods - must be implemented by subclasses
    public abstract String getSportDescription();
    public abstract String getEquipmentProvided();
    public abstract String getTurfSize();

    // Concrete methods
    public String getSportName() {
        return sportName;
    }

    public int getMaxPlayersPerTeam() {
        return maxPlayersPerTeam;
    }

    public double getRatePerPerson() {
        return ratePerPerson;
    }

    public double calculateTotalCost(int numberOfPlayers) {
        return ratePerPerson * numberOfPlayers;
    }

    @Override
    public String toString() {
        return String.format("%s (Rate: ₹%.0f/person)", sportName, ratePerPerson);
    }
}
