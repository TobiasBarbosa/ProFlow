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
    public String addSubProject(@PathVariable("projectId") int projectId, Model model, HttpSession session)
    throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/dashboard";
        }

        model.addAttribute("projectId", projectId);
        model.addAttribute("subProject", new SubProject());
        return "add_subproject";
    }

    @PostMapping("/save-subproject")
    public String saveSubProject(@PathVariable("projectId") int projectId,
                                 @ModelAttribute("subProject") SubProject subProject, Model model) throws SQLException {
        subProject.setProjectId(projectId);
        subProjectService.addSubProject(subProject);
        return "redirect:/project";
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
    public String getSubProjectById(@PathVariable("subprojectId") int subprojectId, Model model, HttpSession session, int projectId)
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
        model.addAttribute("subprojectId", subprojectId);
        model.addAttribute("name", subProject.getName());
        return "project";
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
    public String editSubProject(@PathVariable("subProjectId") int subProjectId, Model model, HttpSession session, int projectId) throws SQLException {
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
