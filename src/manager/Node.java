package manager;

import model.Task;

public class Node {
   private Task task;
   protected Node next;
   protected Node prev;

    public Node(Task task, Node next, Node prev) {
        this.task = task;
        this.next = next;
        this.prev = prev;
    }

    public Task getTask() {
        return task;
    }

}
