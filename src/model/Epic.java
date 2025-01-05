package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{
    private final List<SubTask> subtaskIds;

    public Epic (String name, String description, int id, TaskStatus status, List<SubTask> subtaskIds) {
        super(name, description, id, status);
        this.subtaskIds = new ArrayList<>();
    }

    public void subTaskAdd(SubTask subTask) {
        subtaskIds.add(subTask);
        updateStatus();
    }

    public void subTaskDell(SubTask subTask) {
        subtaskIds.remove(subTask);
    }

    public void subTaskClear() {
        subtaskIds.clear();
    }

    public List<SubTask> getSubtaskIds() {
        return subtaskIds;
    }

    public void updateStatus() {
        if(subtaskIds.isEmpty()) {
            setStatus(TaskStatus.NEW);
            return;
        }

        boolean statusDone = false;
        boolean statusInProgress = false;

        for (SubTask subTask : subtaskIds) {
            if (subTask.getStatus() == TaskStatus.DONE) {
                statusDone = true;
            }
            if (subTask.getStatus() == TaskStatus.IN_PROGRESS) {
                statusInProgress = true;
            }
        }

        if (statusDone) {
            setStatus(TaskStatus.DONE);
        } else if (statusInProgress) {
            setStatus(TaskStatus.IN_PROGRESS);
        } else {
            setStatus(TaskStatus.NEW);
        }
    }
}
