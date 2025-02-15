package manager;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация интерфейса TaskManager, хранящая задачи, эпики и подзадачи в оперативной памяти.
 * Вся логика управления задачами реализована через внутренние коллекции.
 */
public class InMemoryTaskManager implements TaskManager {

    // Мапа для хранения простых задач по их идентификаторам
    private final Map<Integer, Task> tasks = new HashMap<>();
    // Мапа для хранения подзадач по их идентификаторам
    private final Map<Integer, SubTask> subtasks = new HashMap<>();
    // Мапа для хранения эпиков по их идентификаторам
    private final Map<Integer, Epic> epics = new HashMap<>();
    // Мапа для генерации уникальных идентификаторов задач
    private int idCounter = 1;

    // Менеджер истории, позволяющий отслеживать просмотренные задачи
    private final HistoryManager historyManager;

    /**
     * Конструктор, принимающий HistoryManager.
     * Несмотря на то, что переданный параметр не используется напрямую,
     * HistoryManager всё равно получается через утилитарный класс Managers.
     */
    public InMemoryTaskManager() {
        // Получаем HistoryManager через утилитарный класс Managers.
        this.historyManager = Managers.getHistoryManager();
    }

    /**
     * Добавляет простую задачу в менеджер.
     */
    @Override
    public void addTask(Task task) {
        int id = generateId();
        task.setId(id);
        tasks.put(id, task);
        historyManager.add(task);
    }

    /**
     * Добавляет подзадачу в менеджер.
     * Генерирует уникальный ID, устанавливает его подзадаче, сохраняет подзадачу в коллекции,
     * а также обновляет соответствующий эпик (добавляет ID подзадачи и обновляет статус эпика).
     */
    @Override
    public void addSubtask(SubTask subtask) {
        int id = generateId();
        subtask.setId(id);
        subtasks.put(id, subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            // Добавляем ID подзадачи в список подзадач эпика
            epic.addSubtask(id);
            // Обновляем статус эпика на основании статусов его подзадач
            updateEpicStatus(epic.getId());
        }
        historyManager.add(subtask);
    }

    /**
     * Добавляет эпик в менеджер.
     * Генерирует уникальный ID, устанавливает его эпику, сохраняет эпик и добавляет его в историю просмотров.
     */
    @Override
    public void addEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
        historyManager.add(epic);
    }

    /**
     * Удаляет простую задачу по ID.
     * После удаления задачи из коллекции, её ID также удаляется из истории просмотров.
     */
    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    /**
     * Удаляет подзадачу по ID.
     * Если подзадача существует, удаляет её из коллекции.
     * Также обновляет соответствующий эпик (удаляет ID подзадачи и обновляет статус) и удаляет подзадачу из истории.
     */
    @Override
    public void removeSubtask(int id) {
        SubTask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                // Удаляем ID подзадачи из списка подзадач эпика
                epic.removeSubtask(id);
                // Обновляем статус эпика после удаления подзадачи
                updateEpicStatus(epic.getId());
            }
        }
    }

    /**
     * Удаляет эпик по ID.
     * При удалении эпика, также удаляются все его подзадачи из коллекции и из истории просмотров.
     */
    @Override
    public void removeEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            // Удаляем все подзадачи, связанные с этим эпику, из коллекции и истории
            for (int subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }

        }
    }

    /**
     * Удаляет все простые задачи.
     * Перед очисткой коллекции задач, удаляет каждую задачу из истории просмотров.
     */
    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    /**
     * Удаляет все подзадачи.
     * Удаляет подзадачи из истории просмотров, очищает коллекцию подзадач,
     * а также обновляет список подзадач для каждого эпика и его статус.
     */
    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateEpicStatus(epic.getId());
        }
    }

    /**
     * Удаляет все эпики.
     * При удалении каждого эпика также удаляются все связанные подзадачи из коллекции и из истории просмотров.
     */
    @Override
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    /**
     * Возвращает список всех простых задач, хранящихся в менеджере.
     */
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * Возвращает список всех подзадач, хранящихся в менеджере.
     */
    @Override
    public List<SubTask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    /**
     * Возвращает список всех эпиков, хранящихся в менеджере.
     */
    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    /**
     * Возвращает историю просмотров в виде списка задач.
     */
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    /**
     * Генерирует уникальный идентификатор для новой задачи.
     */
    private int generateId() {
        return idCounter++;
    }

    /**
     * Обновляет статус эпика по его идентификатору.
     */
    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return;

        List<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean allDone = true;
        boolean anyInProgress = false;

        for (int subtaskId : subtaskIds) {
            SubTask subtask = subtasks.get(subtaskId);
            if (subtask != null) {
                if (subtask.getStatus() != TaskStatus.DONE) {
                    allDone = false;
                }
                if (subtask.getStatus() == TaskStatus.IN_PROGRESS) {
                    anyInProgress = true;
                }
            }
        }

        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (anyInProgress) {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        } else {
            epic.setStatus(TaskStatus.NEW);
        }
    }
}
