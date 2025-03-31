// Taxi.java (unchanged)
public class Taxi {
    private char currentLocation;
    private int fare;
    private int pickupHr;
    private int dropHr;

    public Taxi() {
        this.currentLocation = 'A';
        this.fare = 0;
        this.pickupHr = 0;
        this.dropHr = 0;
    }

    public char getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(char location) { this.currentLocation = location; }
    public int getFare() { return fare; }
    public void setFare(int fare) { this.fare += fare; }  // Changed to += to accumulate earnings
    public int getPickupHr() { return pickupHr; }
    public void setPickupHr(int pickupHr) { this.pickupHr = pickupHr; }
    public int getDropHr() { return dropHr; }
    public void setDropHr(int dropHr) { this.dropHr = dropHr; }
}