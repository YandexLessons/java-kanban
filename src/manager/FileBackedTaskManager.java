package manager;

import exceptions.TaskLoadException;
import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

import java.io.*;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        if (!file.exists()) return taskManager;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String header = reader.readLine(); // Пропускаем заголовок
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = taskManager.fromCsvString(line);
                if (task instanceof Epic) {
                    taskManager.addEpic((Epic) task);
                } else if (task instanceof SubTask) {
                    taskManager.addSubtask((SubTask) task);
                } else {
                    taskManager.addTask(task);
                }
            }
        } catch (IOException e) {
            throw new TaskLoadException("Ошибка при загрузке задач из файла: " + file.getName(), e);
        }

        return taskManager;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(SubTask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void removeSubtask(int id) {
        super.removeSubtask(id);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public List<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public List<SubTask> getAllSubtasks() {
        return super.getAllSubtasks();
    }

    @Override
    public List<Epic> getAllEpics() {
        return super.getAllEpics();
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("id,type,name,status,description,epicId\n");
            for (Task task : getAllTasks()) {
                writer.write(toCsvString(task) + "\n");
            }
            for (Epic epic : getAllEpics()) {
                writer.write(toCsvString(epic) + "\n");
            }
            for (SubTask subtask : getAllSubtasks()) {
                writer.write(toCsvString(subtask) + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении задач в файл", e);
        }
    }

    private String toCsvString(Task task) {
        String type = task.getClass().getSimpleName().toUpperCase(); // Упрощенный способ получения типа
        String epicId = (task instanceof SubTask) ? String.valueOf(((SubTask) task).getEpicId()) : "";

        return String.format("%d,%s,%s,%s,%s,%s",
                task.getId(), type, task.getName(), task.getStatus(), task.getDescription(), epicId);
    }

    private Task fromCsvString(String line) {
        String[] fields = line.split(",");
        int id = Integer.parseInt(fields[0]);
        String type = fields[1];
        String name = fields[2];
        TaskStatus status = TaskStatus.valueOf(fields[3]);
        String description = fields[4];

        if (type.equalsIgnoreCase("EPIC")) {
            Epic epic = new Epic(name, description);
            epic.setId(id);
            epic.setStatus(status);
            return epic;
        } else if (type.equalsIgnoreCase("SUBTASK")) {
            int epicId = Integer.parseInt(fields[5]);
            SubTask subtask = new SubTask(name, description, epicId);
            subtask.setId(id);
            subtask.setStatus(status);
            return subtask;
        } else {
            Task task = new Task(name, description);
            task.setId(id);
            task.setStatus(status);
            return task;
        }
    }
}
