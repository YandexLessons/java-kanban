package model;

/**
 * Класс SubTask представляет собой подзадачу, связанную с определённым эпиком.
 * Он наследуется от базового класса Task и добавляет поле для хранения идентификатора эпика,
 * к которому относится данная подзадача.
 */
public class SubTask extends Task {

    // Идентификатор эпика, к которому относится данная подзадача.
    private int epicId;

    /**
     * Конструктор для создания подзадачи.
     */
    public SubTask(String name, String description, int epicId) {
        super(name, description);  // Вызов конструктора базового класса Task
        this.epicId = epicId;
    }

    /**
     * Возвращает идентификатор эпика, к которому относится данная подзадача.
     */
    public int getEpicId() {
        return epicId;
    }

    /**
     * Устанавливает идентификатор эпика для данной подзадачи.
     */
    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    /**
     * Возвращает строковое представление подзадачи.
     * Здесь выводится только информация об идентификаторе эпика.
     */
    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epicId +
                '}';
    }
}
