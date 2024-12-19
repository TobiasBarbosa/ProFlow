package org.example.proflow.controller;

import jakarta.servlet.http.HttpSession;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.SubProject;
import org.example.proflow.model.Task;
import org.example.proflow.service.ProjectService;
import org.example.proflow.service.SubProjectService;
import org.example.proflow.service.TaskService;
import org.example.proflow.util.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;



@Controller
@RequestMapping("dashboard/{projectId}")
public class SubProjectController {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final ProjectService projectService;
    private final SubProjectService subProjectService;
    private final TaskService taskService;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public SubProjectController(SubProjectService subProjectService, ProjectService projectService, TaskService taskService) {
        this.subProjectService = subProjectService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    //***CREATE SUBPROJECT METHODS***-----------------------------------------------------------------------------------
    @GetMapping("/add_subproject")
    public String addSubProject(@PathVariable("projectId") int projectId,@RequestParam("subprojectName") String subprojectName ,Model model, HttpSession session)
    throws SQLException {
        System.out.println("PROJECT ID: " + projectId);
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/dashboard";
        }

        SubProject subProject = new SubProject();
        subProject.setName(subprojectName);
        subProject.setProjectId(projectId);

        model.addAttribute("projectId", projectId);
        model.addAttribute("subProjectName", subprojectName);
        model.addAttribute("subProject", subProject);
        return "add_subproject";
    }

    @PostMapping("/save-subproject")
    public String saveSubProject(@PathVariable("projectId") int projectId,
                                 @ModelAttribute("subProject") SubProject subProject, Model model) throws SQLException {
        subProject.setProjectId(projectId);
        subProjectService.addSubProject(subProject);
        return "redirect:/dashboard/" + projectId;
    }


    //***READ SUBPROJECT METHODS***-------------------------------------------------------------------------------------
    @GetMapping("/subprojects")
    public String getAllSubProjects(Model model, HttpSession session, int projectId) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/dashboard";
        }
        List<SubProject> subProjects = subProjectService.getAllSubProjects();
        model.addAttribute("SubProjects", subProjects);
        return "project";
    }

    @GetMapping("/subproject/{subprojectId}")
    public String getSubProjectById(@PathVariable("projectId") int projectId,@PathVariable("subprojectId") int subprojectId, Model model, HttpSession session)
            throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/dashboard";
        }
        SubProject subProject = subProjectService.getSubProjectById(subprojectId);
        List<Task> tasks = subProject.getTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("subprojectId", subprojectId);
        model.addAttribute("name", subProject.getName());
        model.addAttribute("project", project);
        model.addAttribute("subProject", subProject);
        return "subproject";
    }


    //***UPDATE SUBPROJECT METHODS***-----------------------------------------------------------------------------------
    @GetMapping("/subproject/edit/{subProjectId}")
    public String editSubProject(@PathVariable("subProjectId") int subProjectId, @PathVariable("projectId") int projectId,Model model, HttpSession session) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/dashboard";
        }
        SubProject subProject = subProjectService.getSubProjectById(subProjectId);
        model.addAttribute("subProject", subProject);
        model.addAttribute("subProjectId", subProject.getId());
        return "edit_subproject";
    }



    @PostMapping("/subproject/update")
    public String updateSubProject(@PathVariable("projectId") int projectId,@ModelAttribute SubProject subProject, HttpSession session) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/dashboard";
        }
        SubProject existingSubProject = subProjectService.getSubProjectById(subProject.getId());

            if (existingSubProject == null) {
                throw new SQLException("SubProject with ID " + subProject.getId() + " not found.");
            }

                subProjectService.mergeSubProject(subProject, existingSubProject);

               subProjectService.updateSubProject(existingSubProject);


        return "redirect:/dashboard/" + projectId;
    }


    //***DELETE SUBPROJECT METHODS***-----------------------------------------------------------------------------------

    @PostMapping("/subproject/delete/{subProjectId}")
    public String deleteSubProject(@PathVariable("projectId") int projectId, @PathVariable("subProjectId") int subProjectId, Model model, HttpSession session) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  // Check if logged in
        SubProject subProject = subProjectService.getSubProjectById(subProjectId);
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId); // Fetch project from database
        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())) { // Check project ownership
            return "redirect:/dashboard";
        }

        // Check for associated tasks
        List<Task> tasks = subProject.getTasks();
        model.addAttribute("subProject", subProject);
        model.addAttribute("showDeleteConfirmation", true); // Show the confirmation dialog
        model.addAttribute("tasks", tasks);
        model.addAttribute("warning", "This subproject has associated tasks. Deleting it will also delete all tasks.");
        model.addAttribute("subProjectId", subProjectId);
        model.addAttribute("projectId", projectId);
        return "edit_subproject";
    }

    // Confirm deletion and delete all tasks and subproject
    @PostMapping("/subproject/confirm-delete/{subProjectId}")
    public String confirmDeleteSubProject(@PathVariable("projectId") int projectId, @PathVariable("subProjectId") int subProjectId, HttpSession session) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  // Check if logged in
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId); // Fetch project from database
        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())) { // Check project ownership
            return "redirect:/dashboard";
        }

        // Delete associated tasks
        taskService.deleteTasksBySubProjectId(subProjectId);

        // Delete the subproject
        subProjectService.deleteSubProject(subProjectId);

        return "redirect:/dashboard/" + projectId;
    }


    //***END***---------------------------------------------------------------------------------------------------------

}


