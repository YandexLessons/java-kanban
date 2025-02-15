package manager;

import model.Epic;
import model.SubTask;
import model.Task;
import java.util.List;

/**
 * Интерфейс TaskManager описывает базовые операции для управления задачами.
 * Он включает методы для работы с простыми задачами, эпиками и подзадачами,
 * а также метод для получения истории просмотров задач.
 */
public interface TaskManager {


    // Добавляет простую задачу в систему.
    void addTask(Task task);

    /**
     * Добавляет подзадачу в систему.
     * При этом подзадача связывается с определённым эпиком.
     */
    void addSubtask(SubTask subtask);

    /**
     * Добавляет эпик в систему.
     * Эпик может содержать список связанных подзадач.
     */
    void addEpic(Epic epic);

    // Удаляет простую задачу по её идентификатору.
    void removeTask(int id);


    // Удаляет подзадачу по её идентификатору.
    void removeSubtask(int id);

    /**
     * Удаляет эпик по его идентификатору.
     * При этом могут быть удалены также все подзадачи, связанные с данным эпиком.
     */
    void removeEpic(int id);

    /**
     * Удаляет все простые задачи из системы.
     */
    void removeAllTasks();

    /**
     * Удаляет все подзадачи из системы.
     */
    void removeAllSubtasks();

    /**
     * Удаляет все эпики из системы.
     */
    void removeAllEpics();

    /**
     * Возвращает список всех простых задач, хранящихся в системе.
     */
    List<Task> getAllTasks();

    /**
     * Возвращает список всех подзадач, хранящихся в системе.
     */
    List<SubTask> getAllSubtasks();

    /**
     * Возвращает список всех эпиков, хранящихся в системе.
     */
    List<Epic> getAllEpics();

    List<Task> getHistory();
}
