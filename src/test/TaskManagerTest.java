package test;

import manager.TaskManager;
import manager.Managers;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для проверки работы TaskManager.
 */
class TaskManagerTest {

    // Экземпляр TaskManager для тестирования
    private TaskManager manager;

    /**
     * Метод выполняется перед каждым тестом и создаёт новый экземпляр TaskManager.
     */
    @BeforeEach
    void setUp() {
        manager = Managers.getDefault();
    }

    /**
     * Проверяет, что задача корректно добавляется в TaskManager.
     */
    @Test
    void addTask_ShouldAddTaskToManager() {
        Task task = new Task("Task1", "Description1");
        manager.addTask(task);
        assertEquals(1, manager.getAllTasks().size(), "Количество задач должно быть 1");
        assertEquals("Task1", manager.getAllTasks().get(0).getName(), "Название задачи не совпадает");
    }

    /**
     * Проверяет, что удаление задачи корректно удаляет её из TaskManager.
     */
    @Test
    void removeTask_ShouldRemoveTaskFromManager() {
        Task task = new Task("Task1", "Description1");
        manager.addTask(task);
        manager.removeTask(task.getId());
        assertTrue(manager.getAllTasks().isEmpty(), "Список задач должен быть пустым после удаления");
    }

    /**
     * Проверяет, что изменение статуса задачи корректно обновляется в TaskManager.
     */
    @Test
    void updateTaskStatus_ShouldUpdateTaskStatus() {
        Task task = new Task("Task1", "Description1");
        manager.addTask(task);
        task.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, manager.getAllTasks().get(0).getStatus(), "Статус задачи должен быть IN_PROGRESS");
    }
}

