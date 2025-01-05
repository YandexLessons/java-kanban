import manager.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        // Создание задач
        Task task1 = new Task("Task 1", "Descs 1", manager.generateId(), TaskStatus.NEW);
        Task task2 = new Task("Tasks 2", "Descs 2", manager.generateId(), TaskStatus.NEW);
        manager.addTask(task1);
        manager.addTask(task2);

        // Создание эпиков и подзадач
        Epic epic1 = new Epic(manager.generateId(), "Epic 1", "Epic Description 1");
        SubTask subtask1 = new SubTask("Sub 1", "Desc 1", manager.generateId(), TaskStatus.NEW, epic1.getId());
        manager.addEpic(epic1);
        manager.addSubtask(subtask1);

        // Проверка работы
        System.out.println("All tasks: " + manager.getAllTasks());
        System.out.println("All epics: " + manager.getAllEpics());
        System.out.println("All subtasks: " + manager.getAllSubtasks());
    }
}
