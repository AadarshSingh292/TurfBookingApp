package model;

public class Cricket extends Sport{
    public Cricket() {
        super("Cricket", 11, 100.0);  // ₹100 per person
    }

    @Override
    public String getSportDescription() {
        return " Cricket - Gentleman's game! Enjoy box cricket or full-pitch matches on our turf.";
    }

    @Override
    public String getEquipmentProvided() {
        return "Cricket Bat, Ball, Stumps, Pads, Gloves, Helmet";
    }

    @Override
    public String getTurfSize() {
        return "60m x 40m (Box Cricket) | Full Pitch Available";
    }

    @Override
    public String toString() {
        return String.format(
                " CRICKET\n" +
                        "   Description : %s\n" +
                        "   Equipment   : %s\n" +
                        "   Turf Size   : %s\n" +
                        "   Rate        : ₹%.0f per person",
                getSportDescription(), getEquipmentProvided(), getTurfSize(), getRatePerPerson()
        );
    }
}
