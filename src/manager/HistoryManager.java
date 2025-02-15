package manager;

import model.Task;
import java.util.List;

/**
 * Данный интерфейс управляет историей задач
 * Методы в нем, добаялвяют, удаляют ну и получаем списки задач который просмотрели
 */
public interface HistoryManager {

    /**
     * Добавляет задачу в историю просмотров.
     */
    void add(Task task);

    /**
     * Возвращает список всех задач из истории просмотров.
     */
    List<Task> getHistory();

    void remove(int id);

}
