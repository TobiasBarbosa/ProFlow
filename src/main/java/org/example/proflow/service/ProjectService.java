package org.example.proflow.service;

import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProjectService {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final ProjectRepository projectRepository;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    //***CREATE PROFILE***---------------------------------------------------------------------------------------------C
    public void addProject(Project project ) throws SQLException{
        projectRepository.addProject(project);
    }

    //***READ PROFILE(S)***--------------------------------------------------------------------------------------------R
//    public List<Profile> getAllProjects(){
//        return projectRepository.getAllProjects();
//    }

    public Project getProjectById(int id) throws SQLException{
        return projectRepository.getProjectById(id);
    }

    //***UPDATE PROFILE***---------------------------------------------------------------------------------------------U
    public void updateProject(Project project) throws SQLException{
        projectRepository.updateProject(project);
    }

    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D
    public void deleteProject(int id) throws SQLException {
        projectRepository.deleteProject(id);
    }

    //**END***----------------------------------------------------------------------------------------------------------
}
