package test;

import manager.Managers;
import manager.TaskManager;
import manager.HistoryManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки корректности работы утилитарного класса Managers.
 * Здесь проверяется, что методы для получения экземпляров менеджеров возвращают не-null объекты.
 */
class ManagersTest {

    /**
     * Тест проверяет, что метод getDefault() возвращает корректный экземпляр TaskManager.
     * Если метод возвращает null, значит, произошла ошибка в создании менеджера задач.
     */
    @Test
    void getDefault_ShouldReturnTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        // Проверяем, что полученный TaskManager не является null.
        assertNotNull(taskManager, "TaskManager не должен быть null");
    }

    /**
     * Тест проверяет, что метод getHistoryManager() возвращает корректный экземпляр HistoryManager.
     * Если метод возвращает null, значит, произошла ошибка в создании менеджера истории.
     */
    @Test
    void getHistoryManager_ShouldReturnHistoryManager() {
        HistoryManager historyManager = Managers.getHistoryManager();
        // Проверяем, что полученный HistoryManager не является null.
        assertNotNull(historyManager, "HistoryManager не должен быть null");
    }
}
