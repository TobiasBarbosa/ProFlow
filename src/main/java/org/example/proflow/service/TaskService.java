package org.example.proflow.service;

import org.example.proflow.model.Task;
import org.example.proflow.repository.TaskRepository;
import org.example.proflow.util.interfaces.TaskRepositoryInterface;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TaskService {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final TaskRepositoryInterface taskRepository;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public TaskService(TaskRepositoryInterface taskRepository) {
        this.taskRepository = taskRepository;
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    //***CREATE TASK***------------------------------------------------------------------------------------------------C
    public void addTask(Task task) throws SQLException {
        taskRepository.addTask(task);
    }

    //***READ TASK(S)***-----------------------------------------------------------------------------------------------R
    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public Task getTaskById(int id) throws SQLException {
        return taskRepository.getTaskById(id);
    }

    //***UPDATE PROFILE***---------------------------------------------------------------------------------------------U
    public void updateTask(Task task) throws SQLException {
        taskRepository.updateTask(task);
    }

    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D
    public void deleteTask(int id) throws SQLException {
        taskRepository.deleteTask(id);
    }

    public void deleteTasksBySubProjectId(int subProjectId) throws SQLException {
        taskRepository.deleteTasksBySubProjectId(subProjectId);
    }


    public void mergeTask(Task source, Task target) {
        if (source.getName() != null) {
            target.setName(source.getName());
        }
        if (source.getDescription() != null) {
            target.setDescription(source.getDescription());
        }
        if (source.getStartDate() != null) {
            target.setStartDate(source.getStartDate());
        }
        if (source.getEndDate() != null) {
            target.setEndDate(source.getEndDate());
        }
        if (source.getStatus() != null) {
            target.setStatus(source.getStatus());
        }
        if (source.getAssignedTo() != null) {
            target.setAssignedTo(source.getAssignedTo());
        }
        if (source.getLocation() != null) {
            target.setLocation(source.getLocation());
        }
        if (source.getTotalEstHours() > 0) {
            target.setTotalEstHours(source.getTotalEstHours());
        }
        if (source.getActualPrice() > 0) {
            target.setActualPrice(source.getActualPrice());
        }
    }


    //**END***----------------------------------------------------------------------------------------------------------
}
