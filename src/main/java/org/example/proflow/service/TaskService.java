package org.example.proflow.service;

import org.example.proflow.model.Profile;
import org.example.proflow.model.Task;
import org.example.proflow.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TaskService {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final TaskRepository taskRepository;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    //***CREATE TASK***------------------------------------------------------------------------------------------------C
    public void addTask(Task task) throws SQLException{
        taskRepository.addTask(task);
    }

    //***READ TASK(S)***-----------------------------------------------------------------------------------------------R
    public List<Task> getAllTasks(){
        return taskRepository.getAllTasks();
    }

    public Task getTaskById(int id) throws SQLException{
        return taskRepository.getTaskById(id);
    }

    //***UPDATE PROFILE***---------------------------------------------------------------------------------------------U
    public void updateTask(Task task) throws SQLException{
        taskRepository.updateTask(task);
    }

    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D
    public void deleteTask(int id) throws SQLException{
        taskRepository.deleteTask(id);
    }

    //**END***----------------------------------------------------------------------------------------------------------
}
