package org.example.proflow.controller;

import jakarta.servlet.http.HttpSession;
import org.example.proflow.exception.ProjectException;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.SubProject;
import org.example.proflow.service.ProjectService;
import org.example.proflow.util.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

//TODO ProjectController: Rette endpoints og HTML sider
//TODO ProjectController: Rette exceptions til ProjectException

@Controller
@RequestMapping("dashboard")
public class ProjectController {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final ProjectService projectService;
//    private final ProfileService profileService;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
//        this.profileService = profileService;
    }

    //***CREATE PROJECT METHODS***-----------------------------------------------------------------------------------
    @GetMapping("/add-project/{profileId}")
    public String addProject(@PathVariable("profileId") int profileId, Model model, HttpSession session) {
        if (!Validator.isValid(session, profileId)) {
            return "redirect:/homepage";
        }
        model.addAttribute("profileId", profileId);
        model.addAttribute("project", new Project());
        return "add_project";
    }

    @PostMapping("/save-project")
    public String saveProject(@PathVariable("profileId") int profileId,
                              @ModelAttribute("projectId") Project project,
                              Model model, HttpSession session) throws ProjectException, SQLException {
        project.setProfileId(profileId);
        projectService.addProject(project);
        return "redirect:/dashboard";
    }

    //***READ PROJECT METHODS***-------------------------------------------------------------------------------------
    @GetMapping("/{projectId}/{name}")
    public String getProjectById(@PathVariable("projectId") int projectId, Model model, HttpSession session) throws ProjectException, SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())) { //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/homepage";
        }

        model.addAttribute("projectId", projectId);
        model.addAttribute("name", project.getName());
        return "dashboard";
    }

    @GetMapping("/project/subprojects") //shows all subprojects from a project
    public String getSubProjectsFromProject(Model model, @RequestParam int projectId, HttpSession session) throws SQLException {
        if (!Validator.isValid(session, projectId)) {
            return "redirect:/homepage";
        }
        List<SubProject> subProjectsFromProject = projectService.getSubProjectsFromProject(projectId);
        model.addAttribute("projectsFromProfile", subProjectsFromProject);
        return "project";
    }

    //***UPDATE PROJECT METHODS***-----------------------------------------------------------------------------------
    @GetMapping("/project/edit/{projectId}")
    public String editProject(@PathVariable("projectId") int projectId, Model model, HttpSession session) throws ProjectException, SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/homepage";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())) { //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/dashboard";
        }

        model.addAttribute("project", project);
//        model.addAttribute("projectId", project.getId());
//        model.addAttribute("name", project.getName());
//        model.addAttribute("description", project.getDescription());
//        model.addAttribute("startDate", project.getStartDate());
//        model.addAttribute("endDate", project.getEndDate());
//        //model.addAttribute("daysUntilDone", project.getDaysUntilDone());
//        //model.addAttribute("totalSubProjectDurationHourly", project.getTotalSubProjectDurationHourly());
//        model.addAttribute("status", project.getStatus());
//        model.addAttribute("budget", project.getBudget());
//        model.addAttribute("actualPrice", project.getActualPrice());
//        model.addAttribute("profileId", project.getProfileId());
        return "edit_project";
    }

    @PostMapping("/project/update") //TODO lave tjek med isValid og isProjectOwned (men skal det gøres på både edit og update?)
    public String updateProject(@ModelAttribute Project project) throws ProjectException, SQLException {
        projectService.updateProject(project);
        return "redirect:/dashboard";
    }

    //***DELETE PROJECT METHODS***-----------------------------------------------------------------------------------
    @PostMapping("/project/delete/{projectId}")
    public String deleteProject(@PathVariable("projectId") int projectId, HttpSession session) throws ProjectException, SQLException {

        projectService.deleteProject(projectId);
        return "redirect:/dashboard";
    }

    //***END***---------------------------------------------------------------------------------------------------------

}
