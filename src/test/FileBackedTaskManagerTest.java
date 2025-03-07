package test;

import manager.FileBackedTaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileBackedTaskManagerTest {
    private File tempFile;
    private FileBackedTaskManager manager;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("tasks", ".csv");
        manager = new FileBackedTaskManager(tempFile);
    }

    @AfterEach
    void tearDown() {
        tempFile.delete();
    }

    @Test
    void shouldSaveAndLoadEmptyFile() {
        FileBackedTaskManager loadedManager = new FileBackedTaskManager(tempFile);
        assertTrue(loadedManager.getAllTasks().isEmpty());
        assertTrue(loadedManager.getAllEpics().isEmpty());
        assertTrue(loadedManager.getAllSubtasks().isEmpty());
    }

    @Test
    void shouldSaveAndLoadMultipleTasks() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        manager.addTask(task1);
        manager.addTask(task2);

        FileBackedTaskManager loadedManager = new FileBackedTaskManager(tempFile);

        assertEquals(2, loadedManager.getAllTasks().size());
        assertEquals(task1.getName(), loadedManager.getAllTasks().get(0).getName());
        assertEquals(task2.getName(), loadedManager.getAllTasks().get(1).getName());
    }

    @Test
    void shouldSaveAndLoadEpicsAndSubtasks() {
        Epic epic = new Epic("Epic 1", "Epic description");
        manager.addEpic(epic);

        SubTask subTask = new SubTask("SubTask 1", "Subtask description", epic.getId());
        manager.addSubtask(subTask);

        FileBackedTaskManager loadedManager = new FileBackedTaskManager(tempFile);

        assertEquals(1, loadedManager.getAllEpics().size());
        assertEquals(1, loadedManager.getAllSubtasks().size());
        assertEquals(epic.getName(), loadedManager.getAllEpics().get(0).getName());
        assertEquals(subTask.getName(), loadedManager.getAllSubtasks().get(0).getName());
    }
}
