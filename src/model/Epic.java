package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс Epic представляет собой тип задачи, который может содержать несколько подзадач.
 * Он наследуется от класса Task и расширяет его, добавляя возможность хранить идентификаторы подзадач.
 */
public class Epic extends Task {

    // Список идентификаторов подзадач, связанных с этим эпиком.
    private List<Integer> subtaskIds = new ArrayList<>();

    /**
     * Конструктор для создания эпика с указанным именем и описанием.
     */
    public Epic(String name, String description) {
        super(name, description);
    }

    /**
     * Возвращает список идентификаторов подзадач, связанных с этим эпиком.
     */
    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    /**
     * Добавляет идентификатор подзадачи в список, если он там ещё не содержится.
     * Это позволяет связать подзадачу с этим эпику.
     */
    public void addSubtask(int subtaskId) {
        if (!subtaskIds.contains(subtaskId)) {
            subtaskIds.add(subtaskId);
        }
    }

    /**
     * Удаляет идентификатор подзадачи из списка.
     * Это используется, когда подзадача удаляется из менеджера задач.
     */
    public void removeSubtask(int subtaskId) {
        // Приводим subtaskId к объекту Integer, чтобы корректно работал метод remove(Object)
        subtaskIds.remove((Integer) subtaskId);
    }

    /**
     * Возвращает строковое представление эпика, включая информацию о подзадачах.
     */
    @Override
    public String toString() {
        return super.toString() + " Epic{subtaskIds=" + subtaskIds + "}";
    }
}
