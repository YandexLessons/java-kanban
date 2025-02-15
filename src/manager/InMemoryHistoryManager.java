package manager;

import model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Реализация интерфейса HistoryManager, который в свою очередь хранит и работает с историей задач
 */
public class InMemoryHistoryManager implements HistoryManager {

    private final HashMap<Integer, Node> nodeHashMap = new HashMap<>();
    private Node head;
    private Node tail;

    /**
     * Добавляет задачу в историю просмотров.
     * Если задача уже присутствует в истории, она удаляется и добавляется заново.
     */
    @Override
    public void add(Task task) {
        if (nodeHashMap.containsKey(task.getId())) {
            remove(task.getId());
        }

        Node newNode = new Node(task, tail, null);
        if (tail != null) {
            tail.next = newNode;
            newNode.prev = tail;
        } else {
            head = newNode;
        }
        tail = newNode;

        nodeHashMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        Node node = nodeHashMap.remove(id);
        if (node == null) return;

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            head = node.prev;
        }
    }

    /**
     * Возвращает список задач, представляющий историю просмотров.
     * Порядок в списке соответствует порядку их просмотра.
    */
    @Override
    public List<Task> getHistory() {

        List<Task> history = new ArrayList<>();
        for (Node node = head; node != null; node = node.next){
            history.add(node.getTask());
        }
        return history;
    }

}
