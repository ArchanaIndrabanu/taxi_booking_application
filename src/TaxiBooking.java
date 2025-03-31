// TaxiBooking.java
import java.util.*;

public class TaxiBooking {
    private List<Taxi> taxis;

    public TaxiBooking() {
        taxis = new ArrayList<>();
    }

    private int calculatePrice(char pickup, char drop) {
        int distance = Math.abs(drop - pickup) * 15;
        int price = 100;  // Minimum fare for first 5km
        if (distance > 5) {
            price += (distance - 5) * 10;  // Rs. 10 per additional km
        }
        return price;
    }

    private Taxi findNearestAvailableTaxi(char pickup, int pickupTime) {
        List<Taxi> availableTaxis = new ArrayList<>();
        
        // Find all available taxis (whose drop time is before pickup time)
        for (Taxi taxi : taxis) {
            if (taxi.getDropHr() <= pickupTime) {
                availableTaxis.add(taxi);
            }
        }

        if (availableTaxis.isEmpty()) {
            return null;
        }

        // Find closest taxi
        int minDistance = Integer.MAX_VALUE;
        Taxi selectedTaxi = null;
        int minFare = Integer.MAX_VALUE;

        for (Taxi taxi : availableTaxis) {
            int distance = Math.abs(taxi.getCurrentLocation() - pickup) * 15;
            if (distance < minDistance) {
                minDistance = distance;
                selectedTaxi = taxi;
                minFare = taxi.getFare();
            } else if (distance == minDistance && taxi.getFare() < minFare) {
                // If same distance, choose taxi with lower earnings
                selectedTaxi = taxi;
                minFare = taxi.getFare();
            }
        }
        return selectedTaxi;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter number of taxis:");
        int numTaxis = scanner.nextInt();
        for (int i = 0; i < numTaxis; i++) {
            taxis.add(new Taxi());
        }

        while (true) {
            System.out.println("\n1. Book a Taxi");
            System.out.println("2. Taxi Details");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Enter pickup location (A-F): ");
                char pickup = Character.toUpperCase(scanner.next().charAt(0));
                System.out.print("Enter drop location (A-F): ");
                char drop = Character.toUpperCase(scanner.next().charAt(0));
                System.out.print("Enter pickup time (in hours): ");
                int pickupTime = scanner.nextInt();

                if (pickup < 'A' || pickup > 'F' || drop < 'A' || drop > 'F') {
                    System.out.println("Invalid location! Please use A-F.");
                    continue;
                }

                Taxi selectedTaxi = findNearestAvailableTaxi(pickup, pickupTime);
                if (selectedTaxi == null) {
                    System.out.println("No taxis available!");
                    continue;
                }

                int travelTime = Math.abs(drop - pickup); // Hours between points
                int fare = calculatePrice(pickup, drop);

                // Update taxi details
                selectedTaxi.setCurrentLocation(drop);
                selectedTaxi.setPickupHr(pickupTime);
                selectedTaxi.setDropHr(pickupTime + travelTime);
                selectedTaxi.setFare(fare);

                System.out.println("Booking confirmed!");
                System.out.println("Fare: Rs. " + fare);
                System.out.println("Travel time: " + travelTime + " hour(s)");
                System.out.println("Drop-off time: " + (pickupTime + travelTime));

            } else if (choice == 2) {
                System.out.println("\nTaxi Details:");
                for (int i = 0; i < taxis.size(); i++) {
                    Taxi t = taxis.get(i);
                    System.out.printf("Taxi %d: Location: %c | Earnings: Rs. %d | Next Available: %d%n",
                        i + 1, t.getCurrentLocation(), t.getFare(), t.getDropHr());
                }
            } else if (choice == 3) {
                System.out.println("Thank you for using our service!");
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        new TaxiBooking().run();
    }
}