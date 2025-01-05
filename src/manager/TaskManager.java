package manager;


import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, SubTask> subtasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private int idCounter = 1;

    public int generateId() {
        return idCounter++;
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addSubtask(SubTask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtask(subtask.getId());
            updateEpicStatus(epic.getId());
        }
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return;

        List<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean allDone = true;
        boolean anyInProgress = false;

        for (int subtaskId : subtaskIds) {
            SubTask subtask = subtasks.get(subtaskId);
            if (subtask != null) {
                if (subtask.getStatus() != TaskStatus.DONE) {
                    allDone = false;
                }
                if (subtask.getStatus() == TaskStatus.IN_PROGRESS) {
                    anyInProgress = true;
                }
            }
        }

        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (anyInProgress) {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        } else {
            epic.setStatus(TaskStatus.NEW);
        }
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void removeSubtask(int id) {
        SubTask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(id);
                updateEpicStatus(epic.getId());
            }
        }
    }

    public void removeEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<SubTask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }
}
