package org.example.proflow.util.interfaces;

import org.example.proflow.model.Project;
import org.example.proflow.model.SubProject;

import java.sql.SQLException;
import java.util.List;

public interface ProjectRepositoryInterface {

    // Create a new project
    void addProject(Project project) throws SQLException;

    // Read a project by its ID
    Project getProjectById(int id) throws SQLException;

    // Read all projects
    List<Project> getAllProjects() throws SQLException;

    // Read all projects associated with a specific profile ID
    List<Project> getProjectsFromProfile(int profileId);

    // Read all subprojects associated with a specific project ID
    List<SubProject> getSubProjectsFromProject(int projectId) throws SQLException;

    // Update an existing project
    void updateProject(Project project) throws SQLException;

    // Delete a project by its ID
    void deleteProject(int projectId) throws SQLException;

    // Delete all projects (for testing purposes only)
    void deleteAllProjects();
}
