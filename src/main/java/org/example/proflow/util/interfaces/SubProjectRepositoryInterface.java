package org.example.proflow.util.interfaces;

import org.example.proflow.model.SubProject;
import org.example.proflow.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface SubProjectRepositoryInterface {

    // Create a new subproject
    void addSubProject(SubProject subProject) throws SQLException;

    // Get all subprojects
    List<SubProject> getAllSubProjects();

    // Get a subproject by its ID
    SubProject getSubProjectById(int id) throws SQLException;

    // Get tasks related to a subproject
    List<Task> getTasksFromSubProject(int subProjectId) throws SQLException;

    // Update an existing subproject
    void updateSubProject(SubProject subProject) throws SQLException;

    // Delete a subproject by its ID
    void deleteSubProject(int id) throws SQLException;

    // Delete all subprojects (for testing purposes)
    void clearSubProjectsForTesting();
}
