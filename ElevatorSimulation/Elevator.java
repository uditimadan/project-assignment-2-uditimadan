import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Elevator {
    private int currentFloor;
    private int capacity;
    private List<Passenger> passengers;

    public Elevator(int capacity) {
        this.currentFloor = 0; // starts at the ground floor
        this.capacity = capacity;
        this.passengers = new ArrayList<>();
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void loadPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public void unloadPassengers() {
        passengers.removeIf(passenger -> passenger.getDestinationFloor() == currentFloor);
    }

    public void moveToFloor(int floor) {
        currentFloor = floor;
    }

    public boolean isAtCapacity() {
        return passengers.size() >= capacity;
    }

    public Passenger[] getConveyedPassengers() {
        List<Passenger> conveyedPassengers = new ArrayList<>();

        // iterate through passengers to find those whose destination is the current floor
        for (Passenger passenger : passengers) {
            if (passenger.getDestinationFloor() == currentFloor) {
                conveyedPassengers.add(passenger);
            }
        }

        // converts the list to an array
        return conveyedPassengers.toArray(new Passenger[0]);
    }

    public Hashtable<Object, Object> getFloorQueues() {
        return null;
    }
}
