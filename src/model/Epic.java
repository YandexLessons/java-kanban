package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();

    public Epic(int id, String name, String description) {
        super(name, description, id, TaskStatus.NEW);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtask(int subtaskId) {
        if (!subtaskIds.contains(subtaskId)) {
            subtaskIds.add(subtaskId);
        }
    }

    public void removeSubtask(int subtaskId) {
        subtaskIds.remove((Integer) subtaskId);
    }

    @Override
    public String toString() {
        return super.toString() + " Epic{subtaskIds=" + subtaskIds + "}";
    }
}
