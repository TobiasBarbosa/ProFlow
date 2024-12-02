package org.example.proflow.controller;

import jakarta.servlet.http.HttpSession;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.service.ProfileService;
import org.example.proflow.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.example.proflow.util.Validator;

import java.sql.SQLException;
import java.util.List;

//TODO ProjectController: Rette HTML sider
//TODO ProjectController: Rette navne/stier på endpoints
//TODO ProjectController: Rette exceptions til ProjectException
//TODO projectId ? hedder rigtigt id i vores Project klasse

@Controller
@RequestMapping("dashboard/{profileId}")
public class ProjectController {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final ProjectService projectService;
    private Model model; //TODO slet?
    private final ProfileService profileService;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public ProjectController(ProjectService projectService, ProfileService profileService) {
        this.projectService = projectService;
        this.profileService = profileService;
    }

    //***CREATE PROJECT METHODS***-----------------------------------------------------------------------------------
    @GetMapping("/addproject")
    public String addProject(@PathVariable("profileId") int profileId, Model model, HttpSession session) {
        if(!Validator.isValid(session, profileId)) {
            return "redirect:/homepage";
        }
        model.addAttribute("profileId", profileId);
        model.addAttribute("project", new Project());
        return "homepage";
    }

    @PostMapping("/saveproject")
    public String saveProject(@PathVariable("profileId") int profileId,
                              @ModelAttribute("projectId") Project project,
                              Model model, HttpSession session) throws SQLException { //TODO ændre exception
        project.setProfileId(profileId);
        projectService.addProject(project);
        return "projects";
    }

    //***READ PROJECT METHODS***-------------------------------------------------------------------------------------
    @GetMapping("")
    public String homepage(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "homepage";
    }

    @GetMapping("/projects")
    public String getAllProjects(Model model) throws SQLException{
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("Projects", projects);
        return "homepage";
    }

    @GetMapping("/{projectId}/{name}")
    public String getProjectById(@PathVariable("projectId") int projectId, Model model, HttpSession session) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if(!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/homepage";
        }

        model.addAttribute("projectId", projectId);
        model.addAttribute("name", project.getName());
        return "projects";
    }

    //***UPDATE PROJECT METHODS***-----------------------------------------------------------------------------------
    @GetMapping("/project/edit/{projectId}")
    public String editProject(@PathVariable("projectId") int projectId, Model model, HttpSession session) throws SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if(!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if(!Validator.isProjectOwned(profile.getId(), project.getProfileId())){ //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/homepage";
        }

        model.addAttribute("project", project);
        model.addAttribute("projectId", project.getId());
        model.addAttribute("name", project.getName());
        model.addAttribute("description", project.getDescription());
        model.addAttribute("startDate", project.getStartDate());
        model.addAttribute("endDate", project.getEndDate());
        //model.addAttribute("daysUntilDone", project.getDaysUntilDone());
        //model.addAttribute("totalSubProjectDurationHourly", project.getTotalSubProjectDurationHourly());
        model.addAttribute("status", project.getStatus());
        model.addAttribute("budget", project.getBudget());
        model.addAttribute("actualPrice", project.getActualPrice());
        model.addAttribute("profileId", project.getProfileId());
        return "editproject";
    }


    @PostMapping("/project/update")
    public String updateProject(@ModelAttribute Project project) throws SQLException {
        int projectId = project.getId();
        projectService.updateProject(project);
        return "redirect:/homepage/project/" + projectId; //TODO ProjectId?
    }


    //***DELETE PROJECT METHODS***-----------------------------------------------------------------------------------
    @PostMapping("/project/delete/{projectId}")
    public String deleteProject(@PathVariable("projectId") int projectId, HttpSession session) throws SQLException {

        projectService.deleteProject(projectId);
        return "rediect:/homepage/userProfile";
    }

    //***END***---------------------------------------------------------------------------------------------------------

}
