package org.example.proflow.controller;

import jakarta.servlet.http.HttpSession;
import org.example.proflow.exception.ProjectException;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.Status;
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
    private final Project project;
//    private final ProfileService profileService;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public ProjectController(ProjectService projectService, Project project) {
        this.projectService = projectService;
//        this.profileService = profileService;
        this.project = project;
    }

    //***CREATE PROJECT METHODS***-----------------------------------------------------------------------------------
    @GetMapping("/add_project/{profileId}")
    public String addProject(@PathVariable("profileId") int profileId,
                             @RequestParam("projectName") String projectName,
                             Model model,
                             HttpSession session) {
        if (!Validator.isValid(session, profileId)) {
            return "redirect:/";
        }

        Project project = new Project();
        project.setName(projectName);
        project.setProfileId(profileId);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("profileId", profileId);
        model.addAttribute("project", project);
        return "add_project";
    }

    @PostMapping("/save-project/{profileId}")
    public String saveProject(@PathVariable("profileId") int profileId,

                              @ModelAttribute("project") Project project) throws ProjectException, SQLException {

        project.setProfileId(profileId);
        projectService.addProject(project);

        return "redirect:/dashboard";
    }


    //***READ PROJECT METHODS***-------------------------------------------------------------------------------------
    @GetMapping("/{projectId}/{name}")
    public String getProjectById(@PathVariable("projectId") int projectId, Model model, HttpSession session) throws ProjectException, SQLException {
        Profile profile = (Profile) session.getAttribute("profile");  //Tjekker om den er logget ind
        if (!Validator.isValid(session, profile.getId())) {
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())) { //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/";
        }

        model.addAttribute("projectId", projectId);
        model.addAttribute("name", project.getName());
        return "dashboard";
    }

    @GetMapping("/project/subprojects") //shows all subprojects from a project
    public String getSubProjectsFromProject(Model model, @RequestParam int projectId, HttpSession session) throws SQLException {
        if (!Validator.isValid(session, projectId)) {
            return "redirect:/";
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
            return "redirect:/";
        }

        Project project = projectService.getProjectById(projectId); //Henter projektet fra databasen

        if (!Validator.isProjectOwned(profile.getId(), project.getProfileId())) { //Tjekker om profilens ID matcher ID'et tilhørende projeketets ID
            return "redirect:/dashboard";
        }

        model.addAttribute("project", project);
        model.addAttribute("projectId", project.getId());

        return "edit_project";
    }

    @PostMapping("/update/{projectId}")
    public String updateProject(@PathVariable("projectId") int projectId, @ModelAttribute Project project) throws ProjectException, SQLException {


        Project existingProject = projectService.getProjectById(projectId);

        if (existingProject == null) {
            throw new ProjectException("Project not found.");
        }

        projectService.mergeProject(project, existingProject);

        projectService.updateProject(existingProject);
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

