package hostel;

/**
 * Generic room class for arbitrary capacity (e.g., 3-bed, 4-bed, 6-bed).
 */
public class MultiRoom extends Room {

    public MultiRoom(String id, int capacity) {
        super(id, capacity);
    }

    @Override
    public String getType() {
        return getCapacity() + "-Bed";
    }
}
