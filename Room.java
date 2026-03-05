package hostel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Room {
    protected String roomId;
    protected int capacity;
    protected List<Student> occupants = new ArrayList<>();

    public Room(String roomId, int capacity) {
        this.roomId = roomId;
        this.capacity = capacity;
    }

    public String getRoomId() { return roomId; }
    public int getCapacity() { return capacity; }

    // available if we have space
    public boolean isAvailable() {
        return occupants.size() < capacity;
    }

    // try to occupy - returns true if placed
    public boolean occupy(Student s) {
        if (!isAvailable()) return false;
        occupants.add(s);
        return true;
    }

    // remove a student (if needed)
    public void vacateStudent(Student s) {
        occupants.remove(s);
    }

    public List<Student> getOccupants() {
        return occupants;
    }

    public String getOccupantNames() {
        return occupants.stream().map(Student::getName).collect(Collectors.joining(", "));
    }

    public abstract String getType();
}
