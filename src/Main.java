import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // Создание задач
        Task task1 = new Task("Task1", "Desc1");
        Task task2 = new Task("Task2", "Desc2");
        manager.addTask(task1);
        manager.addTask(task2);

        // Создание эпиков и подзадач
        Epic epic1 = new Epic("Epic 1", "Epic Description 1");
        Epic epic2 = new Epic("Epic 2", "Epic Description 2");
        manager.addEpic(epic1);
        manager.addEpic(epic2);

        SubTask subtask1 = new SubTask("Sub1", "Subdesc1", epic1.getId());
        SubTask subtask2 = new SubTask("Sub2", "Subdesc2", epic1.getId());
        SubTask subtask3 = new SubTask("Sub3", "Subdesc3", epic2.getId());
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);

        // Печать всех объектов
        System.out.println("All tasks: " + manager.getAllTasks());
        System.out.println("All epics: " + manager.getAllEpics());
        System.out.println("All subtasks: " + manager.getAllSubtasks());

        // Изменение статусов
        task1.setStatus(TaskStatus.IN_PROGRESS);
        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.IN_PROGRESS);
        

        // Печать обновленных объектов
        System.out.println("Updated tasks: " + manager.getAllTasks());
        System.out.println("Updated epics: " + manager.getAllEpics());
        System.out.println("Updated subtasks: " + manager.getAllSubtasks());

        // Удаление задачи и эпика
        manager.removeTask(task1.getId());
        manager.removeEpic(epic2.getId());
        manager.removeAllSubtasks();

        // Печать после удаления
        System.out.println("After deletion - tasks: " + manager.getAllTasks());
        System.out.println("After deletion - epics: " + manager.getAllEpics());
        System.out.println("After deletion - subtasks: " + manager.getAllSubtasks());

    }
}

