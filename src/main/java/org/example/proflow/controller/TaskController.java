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

//TODO TaskController: Rette exceptions til taskException

@Controller
@RequestMapping("dashboard/{projectId}/{subprojectId}")
public class TaskController {
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final TaskService taskService;
    private final ProjectService projectService;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public TaskController(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    //***CREATE TASK METHODS***-----------------------------------------------------------------------------------------
    @GetMapping("/add-task")
    public String addTask(@PathVariable("subprojectId") int subProjectId, Model model, HttpSession session, @PathVariable int projectId) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId);

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())) {
            return "redirect:/dashboard";
        }
        model.addAttribute("subProjectId", subProjectId);
        model.addAttribute("task", new Task());
        return "add_task";
    }

    @PostMapping("/save-task")
    public String saveTask(@PathVariable("subprojectId") int subProjectId,
                           @ModelAttribute("task") Task task) throws SQLException {
        task.setSubProjectId(subProjectId);
        taskService.addTask(task);
        return "redirect:/dashboard/{projectId}/subproject/" + subProjectId;
    }

    //***READ TASK METHODS***-------------------------------------------------------------------------------------------
    @GetMapping("tasks")
    public String getAllTasks(Model model, HttpSession session, int projectId) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId);

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())) {
            return "redirect:/dashboard";
        }

        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "task";
    }




    @GetMapping("/{taskId}")
    public String getTaskById(@PathVariable("taskId") int taskId,
                              @PathVariable("projectId") int projectId,
                              @PathVariable("subprojectId") int subprojectId,
                              Model model, HttpSession session) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId);

        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())) {
            return "redirect:/dashboard";
        }

        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("name", task.getName());
        return "task"; // Ensure this maps to the task template you created
    }


    //***UPDATE TASK METHODS***-----------------------------------------------------------------------------------------
    @GetMapping("/task/edit/{taskId}")
    public String editTask(@PathVariable("taskId") int taskId,
                           @PathVariable("projectId") int projectId,
                           @PathVariable("subprojectId") int subprojectId,
                           Model model, HttpSession session) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId);

        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())) {
            return "redirect:/dashboard";
        }

        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("projectId", projectId);
        model.addAttribute("subprojectId", subprojectId); // Add this line
        return "edit_task";
    }


    @PostMapping("/task/update")
    public String updateTask(@ModelAttribute Task task,
                             @RequestParam int projectId,
                             @RequestParam int subprojectId) throws SQLException {

        Task existingTask = taskService.getTaskById(task.getId());

        if (existingTask == null) {
            // Handle the case where the task doesn't exist
            return "redirect:/error";
        }

        // Merge the updated fields from sourceTask into the existingTask
        taskService.mergeTask(task, existingTask);

        // Save the updated task back to the database
        taskService.updateTask(existingTask);

        taskService.updateTask(task);
        return "redirect:/dashboard/" + projectId + "/" + subprojectId+"/"+ task.getId(); // Redirect back to the correct path
    }


    @PostMapping("/task/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId,
                             @PathVariable("projectId") int projectId,
                             @PathVariable("subprojectId") int subprojectId,
                             HttpSession session) throws SQLException {
        int subProjectId = subprojectId;
        Profile profile = (Profile) session.getAttribute("profile");

        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId);

        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())) {
            return "redirect:/dashboard";
        }

        taskService.deleteTask(taskId);

        // Redirect back to the correct subproject dashboard
        return "redirect:/dashboard/{projectId}/subproject/" + subProjectId;
    }


    //***END***---------------------------------------------------------------------------------------------------------
}

