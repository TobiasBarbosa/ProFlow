package org.example.proflow.controller;

import jakarta.servlet.http.HttpSession;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.SubProject;
import org.example.proflow.model.Task;
import org.example.proflow.service.ProjectService;
import org.example.proflow.service.SubProjectService;
import org.example.proflow.util.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

//TODO SubProjectController: Rette exceptions til subProjectException

@Controller
@RequestMapping("dashboard/{projectId}")
public class SubProjectController {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final ProjectService projectService;
    private final SubProjectService subProjectService;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public SubProjectController(SubProjectService subProjectService, ProjectService projectService) {
        this.subProjectService = subProjectService;
        this.projectService = projectService;
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
        System.out.println("this is the sub project to save"+ subProject);
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
        System.out.println("These are tasks for sub project "+ tasks);
        model.addAttribute("tasks", tasks);
        model.addAttribute("subprojectId", subprojectId);
        model.addAttribute("name", subProject.getName());
            model.addAttribute("project", project);
            model.addAttribute("subProject", subProject);
        //    return "view_subproject";
        return "subproject";
    }

    @GetMapping("/subproject/tasks") //shows all tasks from one subproject
    public String getTasksFromSubProject(Model model, @RequestParam int subProjectId, HttpSession session) throws SQLException {
        if (!Validator.isValid(session, subProjectId)) {
            return "redirect:/";
        }
        List<Task> tasksFromSubProject = subProjectService.getTasksFromSubProject(subProjectId);
        model.addAttribute("tasksFromSubProject", tasksFromSubProject);
        return "task";
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

    //@GetMapping("/subproject/edit/{subProjectId}")
    //public String editSubProject(@PathVariable("subProjectId") int subProjectId,
    //                             @PathVariable("projectId") int projectId,  // Retrieve projectId from the path
    //                             Model model, HttpSession session) throws SQLException {
    //    Profile profile = (Profile) session.getAttribute("profile");  // Check if the user is logged in
    //    if (!Validator.isValid(session, profile.getId())) {
    //        return "redirect:/";
    //    }
    //
    //    Project project = projectService.getProjectById(projectId); // Fetch the project from the database
    //
    //    if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())) { // Validate ownership
    //        return "redirect:/dashboard";
    //    }
    //
    //    SubProject subProject = subProjectService.getSubProjectById(subProjectId); // Fetch the subproject
    //    model.addAttribute("subProject", subProject);
    //    model.addAttribute("subProjectId", subProject.getId());
    //    return "edit_subproject";
    //}


    @PostMapping("/subproject/update") //TODO skal der laves tjek både på edit og update?
    public String updateSubProject(@ModelAttribute SubProject subProject, HttpSession session, int projectId) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/dashboard";
        }
        int subProjectId = subProject.getId();
        subProjectService.updateSubProject(subProject);
        return "redirect:/subproject";
    }


    //***DELETE SUBPROJECT METHODS***-----------------------------------------------------------------------------------
    @PostMapping("/subproject/delete/{subProjectId}")
    public String deleteSubProject(@PathVariable("subProjectId") int subProjectId, @PathVariable("projectId") int projectId, HttpSession session)
    throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/dashboard_stub";
        }
        SubProject subProject = subProjectService.getSubProjectById(subProjectId);
        subProjectService.deleteSubProject(subProjectId);
        return "redirect:/dashboard_stub";
    }


    //***END***---------------------------------------------------------------------------------------------------------

}


