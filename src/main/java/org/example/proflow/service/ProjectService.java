package org.example.proflow.service;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.exception.ProjectException;
import org.example.proflow.model.Project;
import org.example.proflow.model.SubProject;
import org.example.proflow.repository.ProjectRepository;
import org.example.proflow.util.interfaces.ProjectRepositoryInterface;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProjectService {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final ProjectRepositoryInterface projectRepository;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public ProjectService(ProjectRepositoryInterface projectRepository) {
        this.projectRepository = projectRepository;
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    //***CREATE PROFILE***---------------------------------------------------------------------------------------------C
    public void addProject(Project project) throws SQLException {
        projectRepository.addProject(project);
    }

    //***READ PROFILE(S)***--------------------------------------------------------------------------------------------R
    public List<Project> getAllProjects() throws SQLException {
        return projectRepository.getAllProjects();
    }

    public Project getProjectById(int id) throws SQLException {
        return projectRepository.getProjectById(id);
    }

    public List<SubProject> getSubProjectsFromProject(int projectId) throws SQLException {
        return projectRepository.getSubProjectsFromProject(projectId);

    }

    //***UPDATE PROFILE***---------------------------------------------------------------------------------------------U
    public void updateProject(Project project) throws SQLException {
        projectRepository.updateProject(project);
    }

    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D
    public void deleteProject(int id) throws SQLException {
        projectRepository.deleteProject(id);
    }

    //**END***----------------------------------------------------------------------------------------------------------
}
