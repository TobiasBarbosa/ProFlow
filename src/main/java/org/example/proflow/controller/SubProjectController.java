package org.example.proflow.controller;

import jakarta.servlet.http.HttpSession;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.Status;
import org.example.proflow.model.SubProject;
import org.example.proflow.service.ProjectService;
import org.example.proflow.service.SubProjectService;
import org.example.proflow.util.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

//TODO SubProjectController: Rette HTML sider
//TODO SubProjectController: Rette navne/stier på endpoints
//TODO SubProjectController: Rette exceptions til subProjectException ..

@Controller
@RequestMapping("dashboard/{profileId}/{projectId}")
public class SubProjectController {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final ProjectService projectService;
    private final SubProjectService subProjectService;
    private Model model;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public SubProjectController(SubProjectService subProjectService, ProjectService projectService) {
        this.subProjectService = subProjectService;
        this.projectService = projectService;
    }

    //***CREATE SUBPROJECT METHODS***-----------------------------------------------------------------------------------
    @GetMapping("/addsubproject")
    public String addSubProject(@PathVariable("projectId") int projectId, Model model, HttpSession session) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if(!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/homepage";
        }
        model.addAttribute("projectId", projectId);
        model.addAttribute("subProject", new SubProject());
        return "homepage";
    }

    @PostMapping("/savesubproject")
    public String saveSubProject(@PathVariable("projectId") int projectId,
                                 @ModelAttribute("subProject") SubProject subProject, Model model) throws SQLException {
        subProject.setProjectId(projectId);
        subProjectService.addSubProject(subProject);
        return "subprojects";
    }


    //***READ SUBPROJECT METHODS***-------------------------------------------------------------------------------------
    @GetMapping("/subprojects")
    public String getAllSubProjects(Model model, HttpSession session, int projectId) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if(!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/homepage";
        }
        List<SubProject> subProjects = subProjectService.getAllSubProjects();
        model.addAttribute("SubProjects", subProjects);
        return "homepage";
    }

    @GetMapping("/subproject/{subprojectId}")
    public String getSubProjectById(@PathVariable("subprojectId") int subprojectId, Model model, HttpSession session, int projectId)
            throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if(!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/homepage";
        }
        SubProject subProject = subProjectService.getSubProjectById(subprojectId);
        model.addAttribute("subprojectId", subprojectId);
        model.addAttribute("name", subProject.getName());
        return "subprojects";
    }

    //***UPDATE SUBPROJECT METHODS***-----------------------------------------------------------------------------------
    @GetMapping("/subproject/edit/{id}") //TODO skal den bare hedde id her?
    public String editSubProject(@PathVariable("id") int subProjectId, Model model, HttpSession session, int projectId) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if(!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/homepage";
        }
        SubProject subProject = subProjectService.getSubProjectById(subProjectId);
        model.addAttribute("subProject", subProject);
        model.addAttribute("subProjectId", subProject.getId());
        model.addAttribute("name", subProject.getName());
        model.addAttribute("description", subProject.getDescription());
        model.addAttribute("startDate", subProject.getStartDate());
        model.addAttribute("endDate", subProject.getEndDate());
        //model.addAttribute("daysUntilDone", subProject.getDaysUntilDone());
        //model.addAttribute("totalTaskDurationHourly", subProject.getTotalTaskDurationHourly());
        model.addAttribute("status", subProject.getStatus());
        model.addAttribute("projectId", subProject.getProjectId());
        model.addAttribute("assignedTo", subProject.getAssignedTo());
        model.addAttribute("budget", subProject.getBudget());
        return "editSubProject";
    }


    @PostMapping("/subproject/update")
    public String updateSubProject(@ModelAttribute SubProject subProject, HttpSession session, int projectId) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if(!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/homepage";
        }
        int subProjectId = subProject.getId();
        subProjectService.updateSubProject(subProject);
        return "redirect:/homepage/subproject/" + subProjectId;
    }


    //***DELETE SUBPROJECT METHODS***-----------------------------------------------------------------------------------
    @PostMapping("/subproject/delete/{subProjectId}")
    public String deleteSubProject(@PathVariable("subProjectId") int subProjectId, int projectId, HttpSession session) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if(!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/homepage";
        }
        SubProject subProject = subProjectService.getSubProjectById(subProjectId);
        subProjectService.deleteSubProject(subProjectId);
        return "rediect:/homepage/userProfile";
    }

    //***END***---------------------------------------------------------------------------------------------------------

}
