package org.example.proflow.controller;

import org.example.proflow.model.Task;
import org.example.proflow.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

//TODO TaskController: Rette endpoints og html sider
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
    @GetMapping("/{subProjectId}/add-task") //TODO lave tjek med isValid og IsProjectOwned
    public String addTask(@PathVariable("subProjectId") int subProjectId, Model model) {
        model.addAttribute("subProjectId", subProjectId);
        model.addAttribute("task", new Task());
        return "add_task";
    }

    @PostMapping("/{subProjectId}/save-task")
    public String saveTask(@PathVariable("subProjectId") int subProjectId,
                           @ModelAttribute("task") Task task) throws SQLException {
        task.setSubProjectId(subProjectId);
        taskService.addTask(task);
        return "redirect:/subproject";
    }

    //***READ TASK METHODS***-------------------------------------------------------------------------------------------
    @GetMapping("tasks") //TODO skal denne egentlig være en getAllTasksFromSubproject/getTasksFromSubProjectId og hvilken controller klasse skal den så ligge i?
    //TODO lave tjek med isValid og IsProjectOwned
    public String getAllTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "homepage";
    }

    @GetMapping("/task/{taskId}") //TODO lave tjek med isValid og IsProjectOwned
    public String getTaskById(@PathVariable("taskId") int taskId, Model model) throws SQLException {
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("name", task.getName());
        return "task"; //TODO Skal vi have en task html side eller hvad skal vi returnere her?
    }

    //***UPDATE TASK METHODS***-----------------------------------------------------------------------------------------
    @GetMapping("/task/edit/{taskId}") //TODO lave tjek med isValid og IsProjectOwned
    public String editTask(@PathVariable("taskId") int taskId, Model model) throws SQLException {
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
//        model.addAttribute("taskId", task.getId()); //TODO slet de her? 
//        model.addAttribute("name", task.getName());
//        model.addAttribute("description", task.getDescription());
//        model.addAttribute("location", task.getLocation());
//        model.addAttribute("startDate", task.getStartDate());
//        model.addAttribute("endDate", task.getEndDate());
//        //model.addAttribute("daysUntilDone", task.getDaysUntilDone());
//        //model.addAttribute("hourlyDuration", task.getHourlyDuration());
//        model.addAttribute("status", task.getStatus());
//        model.addAttribute("subProjectId", task.getSubProjectId());
//        model.addAttribute("assignedTo", task.getAssignedTo());
//        model.addAttribute("taskPrice", task.getTaskPrice());
        return "edit_task";
    }

    @PostMapping("/task/update") //TODO lave tjek med isValid og IsProjectOwned (men skal det gøres på både edit og update?)
    public String updateTask(@ModelAttribute Task task) throws SQLException {
        int taskId = task.getId();
        taskService.updateTask(task);
        return "redirect:/subproject"; //TODO skal vi redirect til task html side her hvis vi har en?
    }

    //***DELETE TASK METHODS***-----------------------------------------------------------------------------------------
    @PostMapping("/task/delete/{taskId}") //TODO lave tjek med isValid og IsProjectOwned
    public String deleteTask(@PathVariable("taskId") int taskId) throws SQLException {
        Task task = taskService.getTaskById(taskId);
        taskService.deleteTask(taskId);
        return "redirect:/subproject";
    }


    //***END***---------------------------------------------------------------------------------------------------------
}

