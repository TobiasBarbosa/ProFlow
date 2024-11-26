package org.example.proflow.controller;

import jakarta.servlet.http.HttpSession;
import org.example.proflow.model.Project;
import org.example.proflow.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.List;

//TODO ProjectController: Rette HTML sider
//TODO ProjectController: Rette navne/stier på endpoints

@Controller
public class ProjectController {
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final ProjectService projectService;
    private Model model;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    //***CREATE PROJECT METHODS***-----------------------------------------------------------------------------------
    @GetMapping("/addproject")
    public String addProject(Model model) {
        Project Project = new Project();

        //TODO addProject i controller

        return "homepage";
    }

    @PostMapping("/saveproject")
    public String saveProject(@ModelAttribute Project Project, HttpSession session, Model model){

        //TODO saveProject i controller

        return "projects";
    }

    //***READ PROJECT METHODS***-------------------------------------------------------------------------------------
    @GetMapping("/projects")
    public String getAllProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("Projects", projects);
        return "homepage";
    }


    @GetMapping("/project/{id}")
    public String getProjectById(@PathVariable("id") int projectId, Model model) throws SQLException { //TODO ændre exception
        Project project = projectService.getProjectById(projectId);

        //TODO getProjectById i controller

        return "projects";
    }

    //***UPDATE PROJECT METHODS***-----------------------------------------------------------------------------------
    @GetMapping("/project/edit/{id}")
    public String editProject(@PathVariable("id") int projectId, Model model) throws SQLException { //TODO ændre exception
        Project project = projectService.getProjectById(projectId);

        //TODO editProject i controller

        return "editproject";
    }

    @PostMapping("/project/update")
    public String updateProject(@ModelAttribute Project project) throws SQLException { //TODO ændre exception
        int projectId = projectService.getProjectById();
        projectService.updateProject(project);

        //TODO updateProject i controller

        return "redirect:/homepage/project/"+ projectId;
    }


    //***DELETE PROJECT METHODS***-----------------------------------------------------------------------------------
    @PostMapping("/project/delete/{id}")
    public String deleteProject(@PathVariable("id") int projectId) throws SQLException { //TODO ændre exception

        //TODO deleteProject i controller

        Project project = projectService.getProjectById(projectId);
        projectService.deleteProject(projectId);
        return "rediect:/homepage/userProfile";
    }

    //***END***---------------------------------------------------------------------------------------------------------

}
