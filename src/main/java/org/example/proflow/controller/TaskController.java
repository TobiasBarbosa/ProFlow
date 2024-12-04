package org.example.proflow.controller;

import org.example.proflow.model.Status;
import org.example.proflow.model.Task;
import org.example.proflow.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

//TODO TaskController: Rette HTML sider
//TODO TaskController: Rette navne/stier på endpoints
//TODO TaskController: Rette exceptions til taskException

@Controller
@RequestMapping("dashboard/{profileId}/{projectId}")
public class TaskController {
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final TaskService taskService;
    private Model model;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //***CREATE TASK METHODS***-----------------------------------------------------------------------------------------
    @GetMapping("/{subProjectId}/addtask")
    public String addTask(@PathVariable("subProjectId") int subProjectId, Model model) {
        model.addAttribute("subProjectId", subProjectId);
        model.addAttribute("task", new Task());
        return "addTask";
    }

    @PostMapping("/{subProjectId}/savetask")
    public String saveTask(@PathVariable("subProjectId") int subProjectId,
                           @ModelAttribute("task") Task task) throws SQLException {
        task.setSubProjectId(subProjectId);
        taskService.addTask(task);
        return "redirect:";
    }

    //***READ TASK METHODS***-------------------------------------------------------------------------------------------
    @GetMapping("tasks")
    public String getAllTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "homepage";
    }

    @GetMapping("/task/{taskId}")
    public String getTaskById(@PathVariable("taskId") int taskId, Model model) throws SQLException {
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("name", task.getName());
        return "task";
    }

    //***UPDATE TASK METHODS***-----------------------------------------------------------------------------------------
    @GetMapping("/task/edit/{taskId}")
    public String editTask(@PathVariable("taskId") int taskId, Model model) throws SQLException {
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("taskId", task.getId());
        model.addAttribute("name", task.getName());
        model.addAttribute("description", task.getDescription());
        model.addAttribute("location", task.getLocation());
        model.addAttribute("startDate", task.getStartDate());
        model.addAttribute("endDate", task.getEndDate());
        //model.addAttribute("daysUntilDone", task.getDaysUntilDone());
        //model.addAttribute("hourlyDuration", task.getHourlyDuration());
        model.addAttribute("status", task.getStatus());
        model.addAttribute("subProjectId", task.getSubProjectId());
        model.addAttribute("assignedTo", task.getAssignedTo());
        model.addAttribute("taskPrice", task.getTaskPrice());
        return "editTask";
    }

    @PostMapping("/task/update")
    public String updateTask(@ModelAttribute Task task) throws SQLException {
        int taskId = task.getId();
        taskService.updateTask(task);
        return "redirect:/homepage/task/edit" + taskId;
    }

    //***DELETE TASK METHODS***-----------------------------------------------------------------------------------------
    @PostMapping("/task/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId) throws SQLException {
        Task task = taskService.getTaskById(taskId);
        taskService.deleteTask(taskId);
        return "redirect:/homepage/userProfile";
    }


    //***END***---------------------------------------------------------------------------------------------------------
}

