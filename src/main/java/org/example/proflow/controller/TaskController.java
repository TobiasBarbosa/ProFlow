package org.example.proflow.controller;

import jakarta.servlet.http.HttpSession;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.Task;
import org.example.proflow.service.ProjectService;
import org.example.proflow.service.TaskService;
import org.example.proflow.util.Validator;
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
    private final ProjectService projectService;
    private Model model;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public TaskController(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    //***CREATE TASK METHODS***-----------------------------------------------------------------------------------------
    @GetMapping("/{subProjectId}/add-task")
    public String addTask(@PathVariable("subProjectId") int subProjectId, Model model, HttpSession session, int projectId) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId);

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())) {
            return "redirect:/dashboard";
        }
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
    @GetMapping("tasks") //TODO skal vi bruge getAllTasks?
    public String getAllTasks(Model model, HttpSession session, int projectId) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId);

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())) {
            return "redirect:/dashboard";
        }

        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "homepage";
    }

    @GetMapping("/task/{taskId}")
    public String getTaskById(@PathVariable("taskId") int taskId, Model model, HttpSession session, int projectId) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId);

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())) {
            return "redirect:/dashboard";
        }

        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("name", task.getName());
        return "task";
    }

    //***UPDATE TASK METHODS***-----------------------------------------------------------------------------------------
    @GetMapping("/task/edit/{taskId}")
    public String editTask(@PathVariable("taskId") int taskId, Model model, HttpSession session, int projectId) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId);

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())) {
            return "redirect:/dashboard";
        }

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

    @PostMapping("/task/update")
    public String updateTask(@ModelAttribute Task task) throws SQLException {
        int taskId = task.getId();
        taskService.updateTask(task);
        return "redirect:/task";
    }

    //***DELETE TASK METHODS***-----------------------------------------------------------------------------------------
    @PostMapping("/task/delete/{taskId}")  //TODO skal validator være her?
    public String deleteTask(@PathVariable("taskId") int taskId, HttpSession session, int projectId) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId);

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())) {
            return "redirect:/dashboard";
        }
        Task task = taskService.getTaskById(taskId);
        taskService.deleteTask(taskId);
        return "redirect:/subproject";
    }

    //***END***---------------------------------------------------------------------------------------------------------
}

