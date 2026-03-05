package hostel;

public class DoubleRoom extends Room {
    public DoubleRoom(String id) {
        super(id, 2);   // Double room capacity = 2
    }

    @Override
    public String getType() {
        return "Double";
    }
}
