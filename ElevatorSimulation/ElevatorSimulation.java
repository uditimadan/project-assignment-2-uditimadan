import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.Random;

public class ElevatorSimulation {
    private Floor floor;
    private int tick;
    private int duration;
    private double passengerProbability;
    private List<Double> arrivalTimes = new ArrayList<>();
    private List<Double> conveyanceTimes = new ArrayList<>();

    public static void main(String[] args) {
        // Checking if a file path is provided as a command-line argument
        if (args.length < 1) {
            System.out.println("Usage: java ElevatorSimulation Elevator.properties");
            return;
        }

        String FilePath = args[0];

        // Load properties from the file
        Properties properties = propertiesReader(FilePath);

        //Initializes the simulation
        ElevatorSimulation simulation = initializeSimulation(properties);

        // Runs the simulation
        simulation.runSimulation();
        
        // After the simulation, reports the results
        simulation.reportResults();
    }

    //Method to read the property file
    private static Properties propertiesReader(String FilePath) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(FilePath)) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Error reading the properties file: " + e.getMessage());
            System.exit(1);
        }
        return properties;
    }

    //Setting the variables from the property file to initialize the elevator simulation
    private static ElevatorSimulation initializeSimulation(Properties properties) {
        String structures = properties.getProperty("structures", "linked");
        int floors = Integer.parseInt(properties.getProperty("floors", "30"));
        double passengers = Double.parseDouble(properties.getProperty("passengers", "0.05"));
        int elevators = Integer.parseInt(properties.getProperty("elevators", "2"));
        int elevatorCapacity = Integer.parseInt(properties.getProperty("elevatorCapacity", "10"));
        int duration = Integer.parseInt(properties.getProperty("duration", "500"));

        ElevatorSimulation simulation = new ElevatorSimulation();
        simulation.floor = new Floor(floors, elevators, elevatorCapacity);
        simulation.tick = 0;
        simulation.duration = duration;
        simulation.passengerProbability = passengers;

        return simulation;

    }
    
    // Running the actual simulation according to the requirement2
    private void runSimulation() {
        for (tick = 0; tick < duration; tick++) {
            // Elevator unload & load
            handleElevatorUnloadAndLoad();

            // Elevator travel
            handleElevatorTravel();

            // New passengers acc to the probability
            handleNewPassengers();

            // calculates the time between arrival and conveyance during this tick
            calculateArrivalConveyanceTimes();

            // Moving the elevator to next floor (if not at capacity)
            moveElevatorsToNextFloor();
            }
    }

    //Reporting back the results
    // Method to report simulation results
    public void reportResults() {
        // Calculate and print statistics here
        double averageTime = calculateAverageTime();
        double longestTime = calculateLongestTime();
        double shortestTime = calculateShortestTime();

        System.out.println("Average time between arrival and conveyance: " + averageTime);
        System.out.println("Longest time between arrival and conveyance: " + longestTime);
        System.out.println("Shortest time between arrival and conveyance: " + shortestTime);
    }

// Method to calculate the average time
private double calculateAverageTime() {
    if (conveyanceTimes.isEmpty()) {
        return 0.0; // handles the case when no passengers were conveyed
    }
    double sum = 0.0;
    for (double time : conveyanceTimes) {
        sum += time;
    }
    return sum / conveyanceTimes.size(); //Average calculation logic
}

//using the stream method which takes in the list of conveyanceTimes and converts it into a stream to perform the necessary operations
// Method to calculate the longest time
private double calculateLongestTime() {
    return conveyanceTimes.stream().max(Double::compare).orElse(0.0); //uses the comparator operation to find the max
}

// Method to calculate the shortest time
private double calculateShortestTime() {
    return conveyanceTimes.stream().min(Double::compare).orElse(0.0); //uses the comparator operation to find the min
}


// Method to calculate time between arrival and conveyance during each tick
private void calculateArrivalConveyanceTimes() {
    List<Elevator> elevators = floor.getElevators();
    for (Elevator elevator : elevators) {
        for (Passenger passenger : elevator.getConveyedPassengers()) {
            double arrivalTime = passenger.getArrivalTime();
            double conveyanceTime = tick - arrivalTime;
            
            // Store conveyance times for later analysis
            conveyanceTimes.add(conveyanceTime);
        }
    }
}

    
    //This method iterates through the list of elevators, checks if an elevator is not at capacity
    //and then calculates the next floor.
    private void moveElevatorsToNextFloor() {
        List<Elevator> elevators = floor.getElevators();
    
        for (Elevator elevator : elevators) {
            if (!elevator.isAtCapacity()) {
                int currentFloor = elevator.getCurrentFloor();
                int nextFloor = currentFloor + 1;
                if (nextFloor < floor.getNumFloors()) {
                    elevator.moveToFloor(nextFloor);
                }
            }
        }
    }
    // Method to handle elevator unload & load
    private void handleElevatorUnloadAndLoad() {
        List<Elevator> elevators = floor.getElevators();
        List<Queue<Passenger>> floorQueues = floor.getFloorQueues();

        for (Elevator elevator : elevators) {
            elevator.unloadPassengers();

            if (!elevator.isAtCapacity()) {
                int currentFloor = elevator.isAtCapacity() ? elevator.getCurrentFloor() : -1;
                for (int floor = 0; floor < 30; floor++) { //30 is the number of floors in my code
                    Queue<Passenger> queue = floorQueues.get(floor);
                    if (!queue.isEmpty()) {
                        elevator.loadPassenger(queue.poll()); // This method retrieves and removes the head of the queue
                        //removes the passenger at the front of the queue
                        if (currentFloor == -1) {
                            currentFloor = floor;
                            //moves to the next floor
                            elevator.moveToFloor(currentFloor);
                        }
                    }
                }
            }
        }
    }
// Method to handle elevator travel
private void handleElevatorTravel() {
    List<Elevator> elevators = floor.getElevators();

    for (Elevator elevator : elevators) {
        if (!elevator.isAtCapacity()) {
            int currentFloor = elevator.getCurrentFloor();
            int nextFloor;

            // Check if there are passengers in the elevator
            if (!elevator.getPassengers().isEmpty()) {
                // Determines the direction based on the destination of the first passenger
                int firstDestination = elevator.getPassengers().get(0).getDestinationFloor();
                nextFloor = (firstDestination > currentFloor) ? currentFloor + 1 : currentFloor - 1;
            } else {
                // If no passengers are there , move to the next floor based on the default upward direction
                nextFloor = currentFloor + 1;
            }

            // checking bounds to prevent going beyond the top or bottom floors
            if (nextFloor >= 0 && nextFloor < floor.getNumFloors()) {
                // checks for any pending requests in the current direction
                int direction = (nextFloor > currentFloor) ? 1 : -1;
                boolean hasPendingRequests = false;

                for (int floorIndex = currentFloor; floorIndex >= 0 && floorIndex < floor.getNumFloors(); floorIndex += direction) {
                    if (!floor.getFloorQueues().get(floorIndex).isEmpty()) {
                        hasPendingRequests = true;
                        break;
                    }
                }

                if (hasPendingRequests) {
                    elevator.moveToFloor(nextFloor);
                } else {
                    // If no pending requests, change direction and move to the nearest floor with a request
                    int newDirection = (nextFloor > currentFloor) ? 1 : -1;
                    int nearestFloorWithRequest = -1;

                    for (int floorIndex = currentFloor; floorIndex >= 0 && floorIndex < floor.getNumFloors(); floorIndex += newDirection) {
                        if (!floor.getFloorQueues().get(floorIndex).isEmpty()) {
                            nearestFloorWithRequest = floorIndex;
                            break;
                        }
                    }

                    if (nearestFloorWithRequest != -1) {
                        elevator.moveToFloor(nearestFloorWithRequest);
                    }
                }
            }
        }
    }
}


    // Method to handle new passengers
    private void handleNewPassengers() {
        if (Math.random() < passengerProbability) {
            Passenger passenger = generateRandomPassenger();
            floor.handleArrival(passenger);
        }
    }
    // Method to generate the random passengers
    private Passenger generateRandomPassenger() {
        Random rand = new Random();
        int numFloors = floor.getNumFloors();
        if (numFloors <= 0) {
            throw new IllegalArgumentException("Number of floors must be positive");
        }
        int startFloor = rand.nextInt(numFloors);
        int destinationFloor;
        do {
            destinationFloor = rand.nextInt(numFloors);
        } while (destinationFloor == startFloor);
        return new Passenger(tick, startFloor, destinationFloor, tick);
    }

}

