package model;

import java.util.Objects;

/**
 * Класс Task представляет базовую задачу в системе.
 * Он содержит поля для идентификатора, имени, описания и статуса задачи.
 * Этот класс является базовым для других типов задач (например, SubTask и Epic).
 */
public class Task {

    // Уникальный идентификатор задачи
    private int id;
    // Название задачи
    private String name;
    // Описание задачи
    private String description;
    // Текущий статус задачи (например, NEW, IN_PROGRESS, DONE)
    private TaskStatus status;

    /**
     * Конструктор для создания новой задачи.
     * При создании задачи статус по умолчанию устанавливается как NEW.
     */
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    /**
     * Возвращает название задачи.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает новое название задачи.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает описание задачи.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Устанавливает новое описание задачи.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Возвращает идентификатор задачи.
     */
    public int getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор задачи.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Возвращает статус задачи.
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Устанавливает новый статус задачи.
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    /**
     * Возвращает строковое представление задачи.
     */
    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    /**
     * Переопределенный метод equals для сравнения задач.
     * Задачи считаются равными, если равны их id, имя, описание и статус.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Если объекты совпадают, возвращаем true
        if (o == null || getClass() != o.getClass()) return false;  // Если типы различаются, возвращаем false
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                status == task.status;
    }

    /**
     * Переопределенный метод hashCode для генерации хэш-кода задачи.
     * Используются все поля: имя, описание, идентификатор и статус.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }
}
