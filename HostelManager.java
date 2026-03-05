package hostel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class HostelManager {

    private ArrayList<HostelBranch> branches = new ArrayList<>();

    public void addBranch(HostelBranch b) {
        branches.add(b);
    }

    public List<HostelBranch> getBranches() {
        return branches;
    }

    // Check globally across all hostels if a roll already exists
    public boolean isRollExistsGlobally(String roll) {
        if (roll == null) return false;
        for (HostelBranch hb : branches) {
            if (hb.getHostel().hasRoll(roll)) return true;
        }
        return false;
    }

    // Try to add student to a specific hostel branch
    public boolean addStudentToBranch(String branchName, Student s) {
        if (isRollExistsGlobally(s.getRollNo())) return false;
        HostelBranch b = getBranchByName(branchName);
        if (b == null) return false;
        return b.getHostel().addStudent(s);
    }

    public Room allocateInBranch(String branchName, Student s) {
        HostelBranch b = getBranchByName(branchName);
        if (b == null) return null;
        return b.getHostel().allocateRoom(s);
    }

    public HostelBranch getBranchByName(String name) {
        for (HostelBranch b : branches) {
            if (b.getName().equalsIgnoreCase(name))
                return b;
        }
        return null;
    }

    // NEW: find which branch contains the student (by roll) and return the branch; null if not found
    public HostelBranch findBranchByRoll(String roll) {
        if (roll == null) return null;
        for (HostelBranch b : branches) {
            if (b.getHostel().hasRoll(roll)) return b;
        }
        return null;
    }

    // NEW: find the Room object (and branch) that contains student by roll (returns pair-like Map with branch & room)
    public Map<String, Object> findStudentLocationByRoll(String roll) {
        Map<String, Object> res = new HashMap<>();
        if (roll == null) return res;
        for (HostelBranch b : branches) {
            Hostel h = b.getHostel();
            for (Room r : h.getRooms()) {
                for (Student occ : r.getOccupants()) {
                    if (occ.getRollNo().equalsIgnoreCase(roll)) {
                        res.put("branch", b);
                        res.put("room", r);
                        res.put("student", occ);
                        return res;
                    }
                }
            }
            // check waiting list
            for (Student w : h.getWaitingList()) {
                if (w.getRollNo().equalsIgnoreCase(roll)) {
                    res.put("branch", b);
                    res.put("waiting", true);
                    res.put("student", w);
                    return res;
                }
            }
        }
        return res;
    }

    // NEW: search by name (may return multiple matches across branches)
    public List<Map<String, Object>> findStudentByName(String nameQuery) {
        List<Map<String, Object>> results = new ArrayList<>();
        if (nameQuery == null || nameQuery.trim().isEmpty()) return results;
        String q = nameQuery.trim().toLowerCase();
        for (HostelBranch b : branches) {
            Hostel h = b.getHostel();
            for (Room r : h.getRooms()) {
                for (Student occ : r.getOccupants()) {
                    if (occ.getName().toLowerCase().contains(q)) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("branch", b);
                        map.put("room", r);
                        map.put("student", occ);
                        results.add(map);
                    }
                }
            }
            for (Student w : h.getWaitingList()) {
                if (w.getName().toLowerCase().contains(q)) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("branch", b);
                    map.put("waiting", true);
                    map.put("student", w);
                    results.add(map);
                }
            }
        }
        return results;
    }
}
