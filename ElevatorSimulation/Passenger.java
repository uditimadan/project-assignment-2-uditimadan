
public class Passenger {

    private int arrivalTime; 
    private int startFloor;
    private int destinationFloor;
    private int conveyanceTime; 

    public Passenger(int arrivalTime, int startFloor, int destinationFloor, int conveyanceTime) {
        this.arrivalTime = arrivalTime;
        this.startFloor = startFloor;
        this.destinationFloor = destinationFloor;
        this.conveyanceTime = conveyanceTime;
    }

    //getter methods

    public int getStartFloor() {
        return startFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getConveyanceTime() {
        return conveyanceTime;
    }
}

