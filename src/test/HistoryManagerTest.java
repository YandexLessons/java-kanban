package test;

import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для проверки работы HistoryManager.
 */
class HistoryManagerTest {

    // Экземпляр HistoryManager для тестирования
    private HistoryManager historyManager;

    /**
     * Метод выполняется перед каждым тестом и создаёт новый экземпляр HistoryManager.
     */
    @BeforeEach
    void setUp() {
        historyManager = Managers.getHistoryManager();
    }

    /**
     * Проверяет, что задача корректно добавляется в историю.
     */
    @Test
    void addTask_ShouldAddTaskToHistory() {
        Task task = new Task("Task1", "Description1");
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size(), "История должна содержать 1 задачу");
        assertEquals("Task1", historyManager.getHistory().get(0).getName(), "Имя задачи в истории должно совпадать");
    }

}