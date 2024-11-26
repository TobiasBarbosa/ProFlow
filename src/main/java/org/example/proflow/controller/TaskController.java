package org.example.proflow.controller;

import jakarta.servlet.http.HttpSession;
import org.example.proflow.model.Status;
import org.example.proflow.model.Task;
import org.example.proflow.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

//TODO TaskController: Rette HTML sider
//TODO TaskController: Rette navne/stier på endpoints

@Controller
@RequestMapping("homepage")
public class TaskController {
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final TaskService taskService;
    private Model model;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public TaskController(TaskService taskService){
    this.taskService = taskService;
    }

    //***CREATE TASK METHODS***-----------------------------------------------------------------------------------------
    @GetMapping("/addTask")
    public String addTask(Model model){
        Task task = new Task();
        model.addAttribute("task", task);
        return "addTask";
    }

    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute Task task) throws Exception{ //TODO lav TaskException ændre til den
        taskService.addTask(task);
        return "redirect:/homepage/userProfile";
    }

    //***READ TASK METHODS***-------------------------------------------------------------------------------------------
    @GetMapping("tasks")
    public String getAllTasks(Model model){
        List<Task> tasks  = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "homepage";
    }

    @GetMapping("/task/{id}")
    public String getTaskById(@PathVariable("id") int taskId, Model model) throws Exception{
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("name", task.getName());
        return "task";
    }


    //***UPDATE TASK METHODS***-----------------------------------------------------------------------------------------
    @GetMapping("/task/edit/{id}")
    public String editTask(@PathVariable("id") int taskId, Model model) throws Exception{ //TODO taskException sættes ind
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("id", task.getId());
        model.addAttribute("name", task.getName());
        model.addAttribute("description", task.getDescription());
        model.addAttribute("startDate", task.getStartDate());
        model.addAttribute("endDate", task.getEndDate());
        model.addAttribute("daysUntilDone", task.getDaysUntilDone());
        model.addAttribute("status", task.getStatus());
        model.addAttribute("subProjectId", task.getSubProjectId());
        model.addAttribute("assignedTo", task.getAssignedTo());
        model.addAttribute("uniqueVariable", task.getUniqueVariable());
        return "editTask";
    }


    @PostMapping("/task/update/{id}")
    public String updateTask(@ModelAttribute Task task) throws Exception{
        int id = task.getId();
        taskService.updateTask(task);
        return "redirect:/homepage/task";
    }

    //***DELETE TASK METHODS***-----------------------------------------------------------------------------------------
    @PostMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable("id") int id) throws Exception{
        Task task = taskService.getTaskById(id);
        taskService.deleteTask(id);
        return "redirect:/homepage/userProfile";
    }


    //***END***---------------------------------------------------------------------------------------------------------
}

