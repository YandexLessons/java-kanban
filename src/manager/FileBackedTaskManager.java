package manager;

import model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
        loadFromFile(); // Загружаем данные при запуске
    }

    // Метод для сохранения всех задач в файл
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

    // Метод для загрузки задач из файла
    private void loadFromFile() {
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String header = reader.readLine(); // Пропускаем заголовок
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = fromCsvString(line);
                if (task instanceof Epic) {
                    super.addEpic((Epic) task);
                } else if (task instanceof SubTask) {
                    super.addSubtask((SubTask) task);
                } else {
                    super.addTask(task);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке задач из файла", e);
        }
    }

    // Конвертация задачи в строку CSV
    private String toCsvString(Task task) {
        String type = task instanceof Epic ? "EPIC" : task instanceof SubTask ? "SUBTASK" : "TASK";
        String epicId = task instanceof SubTask ? String.valueOf(((SubTask) task).getEpicId()) : "";
        return String.format("%d,%s,%s,%s,%s,%s",
                task.getId(), type, task.getName(), task.getStatus(), task.getDescription(), epicId);
    }

    // Конвертация строки CSV в задачу
    private Task fromCsvString(String line) {
        String[] fields = line.split(",");
        int id = Integer.parseInt(fields[0]);
        String type = fields[1];
        String name = fields[2];
        TaskStatus status = TaskStatus.valueOf(fields[3]);
        String description = fields[4];

        if (type.equals("EPIC")) {
            Epic epic = new Epic(name, description);
            epic.setId(id);
            epic.setStatus(status);
            return epic;
        } else if (type.equals("SUBTASK")) {
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

    // Переопределяем методы добавления задач, чтобы они сразу сохранялись в файл
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
}
