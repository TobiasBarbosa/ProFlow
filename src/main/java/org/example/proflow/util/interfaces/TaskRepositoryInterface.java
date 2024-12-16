package org.example.proflow.util.interfaces;

import org.example.proflow.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskRepositoryInterface {
    // Create a new task
    void addTask(Task task) throws SQLException;

    // Get all tasks
    List<Task> getAllTasks();

    // Get a task by its ID
    Task getTaskById(int id) throws SQLException;

    // Update an existing task
    void updateTask(Task task) throws SQLException;

    // Delete a task by its ID
    void deleteTask(int taskId) throws SQLException;

    void clearTasksForTesting();
}
