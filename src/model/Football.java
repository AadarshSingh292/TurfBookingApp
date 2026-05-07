package model;

public class Football extends Sport {
    public Football() {
        super("Football", 11, 100.0);  // ₹100 per person
    }

    @Override
    public String getSportDescription() {
        return " Football - The beautiful game! Play 5-a-side or full team matches on our premium turf.";
    }

    @Override
    public String getEquipmentProvided() {
        return "Football, Goal Posts, Bibs/Jerseys, Cones";
    }

    @Override
    public String getTurfSize() {
        return "100m x 64m (Full Size) | 40m x 20m (5-a-side)";
    }

    @Override
    public String toString() {
        return String.format(
                " FOOTBALL\n" +
                        "   Description : %s\n" +
                        "   Equipment   : %s\n" +
                        "   Turf Size   : %s\n" +
                        "   Rate        : ₹%.0f per person",
                getSportDescription(), getEquipmentProvided(), getTurfSize(), getRatePerPerson()
        );
    }
}

