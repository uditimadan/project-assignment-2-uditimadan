# project-assignment-2-uditimadan
Assignment02-- Using the data structures we studied so far to create a simulation of one or more elevators in a Elevator Simulation

This assignment helped me use my knowledge on the different data structures like lists and queues in a more practical working project space. In this Elevator Simulation assignment, different data structures are used to model the elevators, floors, and passengers. My code has 4 class files; ElevatorSimulation.java-main class with run simulation, results and helper methods; Floor.java class- containing the number of floors, list of floor containing a queue of passengers on each floor, list of elevators; Elevator.java - class taking in account for the capacity, load and unload getters; Passenger.java - class takes in account for the arrival and conveyance times

Below is the example of different data structures used in my code:

1. **ArrayList and LinkedList:** In the Floor.java class, ArrayList and LinkedList are used to model floor queues for passengers and elevators, respectively.
private List<Queue<Passenger>> floorQueues; // ArrayList of Queues for each floor
private List<Elevator> elevators; // ArrayList of Elevators on the floor

2. **List:** Lists are used to store elevators and queues for each floor in the Floor.java class.
private List<Queue<Passenger>> floorQueues; // List of queues for each floor
private List<Elevator> elevators; // List of elevators on the floor
    
3. **Queues:** Each floor can have a queue of passengers waiting for an elevator. When a passenger arrives at a floor and requests transportation, they are added to the queue for that floor.
private Queue<Passenger> passengerQueue = new LinkedList<>();
private List<Queue<Passenger>> floorQueues; //List of queues, where each queue corresponds to a floor in the building, and each queue holds passengers waiting on that particular floor
    

**Requirement 1 : Reading the property file**

The Elevator.properties file is read through the file input method in the main method in ElevationSimulation.java class. My properties file was a little different from the default values as I wanted to try out different probability of the passengers with 2 elevators and 30 floors.

<img width="520" alt="properties" src="https://github.com/uditimadan/project-assignment-2-uditimadan/assets/123421939/49f96218-d8f4-4891-8a3f-f528edacdee7">


**Requirement 2 : Running the Simulation**

1. **Elevator unload & load:** The `handleElevatorUnloadAndLoad` method in the ElevatorSimulation code is responsible for managing the process of unloading passengers from the elevator and loading new passengers waiting on the floors. It ensures that elevator unloads passengers whose destination is the current floor and loads new passengers from the floor queues, considering the elevator's capacity. The `loadPassenger` and `unloadPassengers` methods in the Elevator class handle the specific loading and unloading operations, respectively.
    
 **Unload Passengers:** For each elevator, the method calls **`elevator.unloadPassengers()`**. This method inside the **`Elevator`** class removes passengers from the elevator whose destination floor matches the current floor of the elevator.
    
**Load Passengers:** The method then checks if the elevator is not at capacity using `!elevator.isAtCapacity()`. If there is still space in the elevator: It iterates through each floor, starting from floor 0, using a for loop. For each floor, it checks if there is a passenger waiting in the queue for that floor using `!queue.isEmpty()`. If there is a passenger, it loads that passenger into the elevator using `elevator.loadPassenger(queue.poll())`, which removes the passenger from the floor's queue. If **`currentFloor`** is still -1, it sets **`currentFloor`** to the current floor where the passenger is picked up, and the elevator moves to that floor using **`elevator.moveToFloor(currentFloor)`**.
    

2. **Elevator travel:** The `handleElevatorTravel` method in the ElevatorSimulation code manages the movement of elevators during the simulation. I tried to implement the logic of realistic elevator movement, considering factors such as current passenger destinations, pending requests, and optimal travel directions. It provides a simulation similar to the behavior of an elevator system in a building, making decisions based on the immediate conditions and passenger needs.

Initially this method retrieves the list of elevators from the floor. It iterates through each elevator in the list and then checks if the elevator is not at its capacity, meaning it can accommodate more passengers. The method retrieves the current floor of the elevator. It calculates the next floor based on whether there are passengers in the elevator or not. If there are passengers, it determines the direction based on the destination floor of the first passenger. If there are no passengers, it defaults to moving upward. It checks if the calculated next floor is within the valid range (not going beyond the top or bottom floors) and checks for pending requests (passengers waiting) in the current direction. If there are pending requests, the elevator moves to the next floor. If there are no pending requests, it changes direction and looks for the nearest floor with a request. It determines the new direction based on whether the next floor is greater or smaller than the current floor. It iterates through floors in the new direction to find the nearest floor with a request. If a nearest floor with a request is found, the elevator moves to that floor.
    

3. **New passengers:** The basic logic for handling new passengers in the `handleNewPassengers` method is based on a probability check which utilizes the `Math.random()` method, and returns a random double value between 0.0 (inclusive) and 1.0 (exclusive). The method checks if this random value is less than the specified passenger probability. If true, it generates a random passenger using the `generateRandomPassenger` method and adds them to the floor by calling `floor.handleArrival(passenger)`. The `generateRandomPassenger` method, in turn, generates a passenger with random attributes. It uses the `Random` class to determine the start floor, destination floor, and arrival time of the passenger. The destination floor is ensured to be different from the start floor, then generated passenger is returned.

**Requirement 3 : Reporting the Results**
The `reportResults` method is responsible for presenting the results of the elevator simulation. It calculates and prints the average time between passenger arrival and conveyance, the longest time, and the shortest time. 

1. **`calculateAverageTime`:** This method calculates the average time between passenger arrival and conveyance. It checks if the `conveyanceTimes` list is empty to handle the case when no passengers were conveyed during the simulation. If the list is not empty, it uses a loop to sum up all the conveyance times and then divides the sum by the number of conveyance times to get the average.

2. **`calculateLongestTime`:** This method calculates the longest time between passenger arrival and conveyance. It uses the  Stream method to find the maximum value in the `conveyanceTimes` list. The `stream().max(Double::compare)` operation compares the elements and returns the maximum value. If the list is empty, it defaults to 0.0.
   
3. **`calculateShortestTime`:**This method calculates the shortest time between passenger arrival and conveyance. Similar to `calculateLongestTime`, it uses the Stream method to find the minimum value in the `conveyanceTimes` list. The `stream().min(Double::compare)` operation compares the elements and returns the minimum value. If the list is empty, it defaults to 0.0.

Some sample outputs:

<img width="720" alt="Screen Shot 2023-11-26 at 3 31 17 PM" src="https://github.com/uditimadan/project-assignment-2-uditimadan/assets/123421939/74fcc19d-fd15-4642-b1e9-0e33b642981c">


