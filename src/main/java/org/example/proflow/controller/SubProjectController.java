package org.example.proflow.controller;

import jakarta.servlet.http.HttpSession;
import org.example.proflow.model.SubProject;
import org.example.proflow.service.SubProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.List;

//TODO SubProjectController: Rette HTML sider
//TODO SubProjectController: Rette navne/stier på endpoints

@Controller
public class SubProjectController {
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final SubProjectService subProjectService;
    private Model model;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public SubProjectController(SubProjectService subProjectService){
        this.subProjectService = subProjectService;
    }

    //***CREATE SUBPROJECT METHODS***-----------------------------------------------------------------------------------
   @GetMapping("/addsubproject")
    public String addSubProject(Model model) {
       SubProject subProject = new SubProject();

        //TODO addSubProject i controller

       return "homepage";
   }

   @PostMapping("/savesubproject")
    public String saveSubProject(@ModelAttribute SubProject subProject, HttpSession session, Model model){

        //TODO saveSubProject i controller

       return "subprojects";
   }

    //***READ SUBPROJECT METHODS***-------------------------------------------------------------------------------------
    @GetMapping("/subprojects")
    public String getAllSubProjects(Model model) {
        List<SubProject> subProjects = subProjectService.getAllSubProjects();
        model.addAttribute("SubProjects", subProjects);
        return "homepage";
    }


    @GetMapping("/subproject/{id}")
    public String getSubProjectById(@PathVariable("id") int subprojectId, Model model) throws SQLException { //TODO rette exception
        SubProject subProject = subProjectService.getSubProjectById(subprojectId);

        //TODO getSubProjectById i controller

        return "subprojects";
    }


    //***UPDATE SUBPROJECT METHODS***-----------------------------------------------------------------------------------
    @GetMapping("/subproject/edit/{id}")
    public String editSubProject(@PathVariable("id") int subProjectId, Model model) throws SQLException { //TODO rette exception
        SubProject subProject = subProjectService.getSubProjectById(subProjectId);

        //TODO editSubProject i controller

        return "editSubProject";
    }

    @PostMapping("/subproject/update")
    public String updateSubProject(@ModelAttribute SubProject subProject) throws SQLException { //TODO rette exception
        int subProjectId = subProjectService.getSubProjectById(subProjectId);
        subProjectService.updateSubProject(subProject);

        //TODO updateSubProject i controller

        return "redirect:/homepage/subproject/"+ subProjectId;
    }


    //***DELETE SUBPROJECT METHODS***-----------------------------------------------------------------------------------
    @PostMapping("/subproject/delete/{id}")
    public String deleteSubProject(@PathVariable("id") int subProjectId) throws SQLException { //TODO rette exception

        //TODO deleteSubProject i controller
        SubProject subProject = subProjectService.getSubProjectById(subProjectId);
        subProjectService.deleteSubProject(subProjectId);
        return "rediect:/homepage/userProfile";
    }



    //***END***---------------------------------------------------------------------------------------------------------

}
