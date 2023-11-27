import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Floor {
    private int numFloors;
    private List<Queue<Passenger>> floorQueues;
    private List<Elevator> elevators;

    // Constructor to set the number of floors
    public Floor(int numFloors, int numElevators, int elevatorCapacity) {
        this.numFloors = numFloors;  // Added this line to set the numFloors field

        floorQueues = new ArrayList<>();
        elevators = new ArrayList<>();

        for (int i = 0; i < numFloors; i++) {
            floorQueues.add(new LinkedList<>());
        }

        for (int i = 0; i < numElevators; i++) {
            elevators.add(new Elevator(elevatorCapacity));
        }
    }

    public List<Queue<Passenger>> getFloorQueues() {
        return floorQueues;
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public void handleArrival(Passenger passenger) {
        floorQueues.get(passenger.getStartFloor()).add(passenger);
    }

    public int getNumFloors() {
        return numFloors;
    }

}


