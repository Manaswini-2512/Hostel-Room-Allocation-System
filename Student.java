package hostel;

public class Student {
    private String rollNo;
    private String name;
    private String gender;
    private String roomPreference;
    private int year;

    public Student(String rollNo, String name, String gender, String roomPreference, int year) {
        this.rollNo = rollNo;
        this.name = name;
        this.gender = gender;
        this.roomPreference = roomPreference;
        this.year = year;
    }

    public String getRollNo() { return rollNo; }
    public String getName() { return name; }
    public String getGender() { return gender; }
    public String getRoomPreference() { return roomPreference; }
    public int getYear() { return year; }

    @Override
    public String toString() {
        return name + " (" + rollNo + ", " + gender + ", Year " + year + ", Pref: " + roomPreference + ")";
    }

    // Two students are same if rollNo is same
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return this.rollNo != null && this.rollNo.equalsIgnoreCase(s.rollNo);
    }

    @Override
    public int hashCode() {
        return rollNo == null ? 0 : rollNo.toLowerCase().hashCode();
    }
}
