package hostel;

import java.util.ArrayList;

public class Hostel {

    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Student> waiting = new ArrayList<>();

    // Default constructor (keeps previous sample rooms for compatibility)
    public Hostel() {
        rooms.add(new SingleRoom("S101"));
        rooms.add(new SingleRoom("S102"));
        rooms.add(new SingleRoom("S103"));
        rooms.add(new DoubleRoom("D201"));
        rooms.add(new DoubleRoom("D202"));
    }

    /**
     * New constructor: create a hostel with `roomCount` rooms,
     * each having `capacity` occupants, and room id prefix `prefix` (like "SV" or "SM").
     *
     * Example: new Hostel("SV", 30, 6) -> SV001...SV030 each 6-bed rooms
     */
    public Hostel(String prefix, int roomCount, int capacity) {
        for (int i = 1; i <= roomCount; i++) {
            String id = String.format("%s%03d", prefix, i); // e.g., SV001
            rooms.add(new MultiRoom(id, capacity));
        }
    }

    // returns false if roll already exists (in this hostel)
    public boolean addStudent(Student s) {
        if (hasRoll(s.getRollNo())) return false;
        students.add(s);
        return true;
    }

    // public check (used by manager) — checks students, waiting and occupants
    public boolean hasRoll(String roll) {
        if (roll == null) return false;
        for (Student st : students) {
            if (roll.equalsIgnoreCase(st.getRollNo())) return true;
        }
        for (Student st : waiting) {
            if (roll.equalsIgnoreCase(st.getRollNo())) return true;
        }
        for (Room r : rooms) {
            for (Student occ : r.getOccupants()) {
                if (roll.equalsIgnoreCase(occ.getRollNo())) return true;
            }
        }
        return false;
    }

    // allocateRoom assumes student is unique within this hostel and already added via addStudent
    public Room allocateRoom(Student s) {
        // 1) If the student is already allocated (by roll), return that room
        for (Room r : rooms) {
            for (Student occ : r.getOccupants()) {
                if (occ.getRollNo().equalsIgnoreCase(s.getRollNo())) {
                    return r;
                }
            }
        }

        // 2) Find preferred room with available capacity
        for (Room r : rooms) {
            if (r.isAvailable() && r.getType().equalsIgnoreCase(s.getRoomPreference())) {
                boolean placed = r.occupy(s);
                if (placed) return r;
            }
        }

        // 3) No room available: add to waiting list (if not already present)
        if (!waiting.contains(s)) waiting.add(s);
        return null;
    }

    public ArrayList<Room> getRooms() { return rooms; }
    public ArrayList<Student> getWaitingList() { return waiting; }

    // optional helper to show hostel occupancy in UI later
    public String getStatus() {
        StringBuilder sb = new StringBuilder();
        for (Room r : rooms) {
            sb.append(r.getRoomId())
              .append(" [").append(r.getType()).append("] ")
              .append(r.isAvailable() ? "Vacant(" + (r.getCapacity() - r.getOccupants().size()) + ")" : "Occupied")
              .append(" - Occupants: ").append(r.getOccupantNames())
              .append("\n");
        }
        return sb.toString();
    }
}
