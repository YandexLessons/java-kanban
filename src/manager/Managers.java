package manager;

import java.io.File;

/**
 * Утилитарный класс для создания экземпляров менеджеров.
 */
public class Managers {

    /**
     * Возвращает новый экземпляр TaskManager.
     */
    public static TaskManager getDefault() {
        return new FileBackedTaskManager(new File("tasks.csv"));
    }

    /**
     * Возвращает новый экземпляр HistoryManager.
     * Этот метод можно использовать, когда необходим только менеджер истории.
     */
    public static HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }


}
