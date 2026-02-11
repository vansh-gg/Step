import java.util.*;

public class ParkingLot {

    enum Status { EMPTY, OCCUPIED, DELETED }

    class Spot {
        String licensePlate;
        long entryTime;
        Status status;

        Spot() {
            status = Status.EMPTY;
        }
    }

    private Spot[] table;
    private int capacity;
    private int size = 0;
    private int totalProbes = 0;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        table = new Spot[capacity];
        for (int i = 0; i < capacity; i++)
            table[i] = new Spot();
    }

    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % capacity;
    }

    // 🚗 Park Vehicle
    public boolean parkVehicle(String plate) {

        if (size == capacity) {
            System.out.println("Parking FULL!");
            return false;
        }

        int index = hash(plate);
        int startIndex = index;
        int probes = 0;

        while (table[index].status == Status.OCCUPIED) {
            index = (index + 1) % capacity;
            probes++;

            if (index == startIndex) {
                System.out.println("Parking FULL!");
                return false;
            }
        }

        table[index].licensePlate = plate;
        table[index].entryTime = System.currentTimeMillis();
        table[index].status = Status.OCCUPIED;

        size++;
        totalProbes += probes;

        System.out.println("Parked " + plate +
                " at spot " + index +
                " (probes: " + probes + ")");
        return true;
    }

    // 🚘 Exit Vehicle
    public boolean exitVehicle(String plate) {

        int index = hash(plate);
        int startIndex = index;

        while (table[index].status != Status.EMPTY) {

            if (table[index].status == Status.OCCUPIED &&
                    table[index].licensePlate.equals(plate)) {

                long duration =
                        (System.currentTimeMillis() - table[index].entryTime) / 1000;

                table[index].status = Status.DELETED;
                size--;

                System.out.println("Exited " + plate +
                        " | Duration: " + duration + " sec");
                return true;
            }

            index = (index + 1) % capacity;

            if (index == startIndex)
                break;
        }

        System.out.println("Vehicle not found.");
        return false;
    }

    // 🔍 Search Vehicle
    public boolean findVehicle(String plate) {

        int index = hash(plate);
        int startIndex = index;

        while (table[index].status != Status.EMPTY) {

            if (table[index].status == Status.OCCUPIED &&
                    table[index].licensePlate.equals(plate)) {

                System.out.println("Found at spot " + index);
                return true;
            }

            index = (index + 1) % capacity;

            if (index == startIndex)
                break;
        }

        System.out.println("Not found.");
        return false;
    }

    // 📊 Statistics
    public void getStatistics() {

        double occupancy = (size * 100.0) / capacity;
        double avgProbes = (size == 0) ? 0 : (totalProbes * 1.0 / size);

        System.out.println("\n--- Parking Stats ---");
        System.out.println("Occupancy: " + occupancy + "%");
        System.out.println("Avg Probes: " + avgProbes);
    }

    public static void main(String[] args) {

        ParkingLot lot = new ParkingLot(5);

        lot.parkVehicle("ABC123");
        lot.parkVehicle("XYZ789");
        lot.parkVehicle("DEF456");

        lot.findVehicle("XYZ789");

        lot.exitVehicle("XYZ789");

        lot.findVehicle("XYZ789");

        lot.getStatistics();
    }
}
