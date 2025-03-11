package org.example.proflow.repository;

import org.example.proflow.config.DataBaseConnection;
import org.example.proflow.model.Project;
import org.example.proflow.model.ProjectTask;
import org.example.proflow.model.SubProject;
import org.example.proflow.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ProjectTaskRepository {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final DataBaseConnection dataBaseConnection;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    @Autowired
    public ProjectTaskRepository(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    //***CREATE PROJECT***---------------------------------------------------------------------------------------------C
    public void addProjectTask(ProjectTask projectTask) throws SQLException {
        String insertQuery;
        PreparedStatement ps;

        // Determine the type of ProjectTask (Project, SubProject, or Task)
        if (projectTask instanceof Project) {
            insertQuery = """
                    INSERT INTO Project (name, description, start_date, end_date, total_est_hours, status, budget, actual_price, profile_id)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

            // Create the PreparedStatement for Project
            try (Connection con = dataBaseConnection.getConnection()) {
                ps = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

                // Set parameters for Project
                ps.setString(1, projectTask.getName());
                ps.setString(2, projectTask.getDescription());
                ps.setDate(3, Date.valueOf(projectTask.getStartDate()));
                ps.setDate(4, Date.valueOf(projectTask.getEndDate()));
                ps.setDouble(5, projectTask.getTotalEstHours());
                ps.setString(6, projectTask.getStatus().name()); // Assuming Status is an enum
                ps.setDouble(7, ((Project) projectTask).getBudget());
                ps.setObject(8, ((Project) projectTask).getActualPrice()); // Handle nulls with setObject
                ps.setInt(9, ((Project) projectTask).getProfileId());

                ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        projectTask.setId(generatedKeys.getInt(1)); // Set the generated ID
                    }
                }
            }
        } else if (projectTask instanceof SubProject) {
            insertQuery = """
                    INSERT INTO SubProject (name, description, start_date, end_date, status, budget, project_id, assigned_to, total_est_hours, actual_price)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

            // Create the PreparedStatement for SubProject
            try (Connection con = dataBaseConnection.getConnection()) {
                ps = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

                // Set parameters for SubProject
                ps.setString(1, projectTask.getName());
                ps.setString(2, projectTask.getDescription());
                ps.setDate(3, Date.valueOf(projectTask.getStartDate()));
                ps.setDate(4, Date.valueOf(projectTask.getEndDate()));
                ps.setString(5, projectTask.getStatus().name()); // Assuming Status is an enum
                ps.setDouble(6, ((SubProject) projectTask).getBudget());
                ps.setInt(7, ((SubProject) projectTask).getProjectId());
                ps.setString(8, ((SubProject) projectTask).getAssignedTo());
                ps.setDouble(9, projectTask.getTotalEstHours());
                ps.setDouble(10, ((SubProject) projectTask).getActualPrice());

                ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        projectTask.setId(generatedKeys.getInt(1)); // Set the generated ID
                    }
                }
            }
        } else if (projectTask instanceof Task) {
            insertQuery = """
                    INSERT INTO Task (name, description, location, start_date, end_date, total_est_hours, status, sub_project_id, assigned_to, price)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

            // Create the PreparedStatement for Task
            try (Connection con = dataBaseConnection.getConnection()) {
                ps = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

                // Set parameters for Task
                ps.setString(1, projectTask.getName());
                ps.setString(2, projectTask.getDescription());
                ps.setString(3, ((Task) projectTask).getLocation());
                ps.setDate(4, Date.valueOf(projectTask.getStartDate()));
                ps.setDate(5, Date.valueOf(projectTask.getEndDate()));
                ps.setDouble(6, projectTask.getTotalEstHours());
                ps.setString(7, projectTask.getStatus().name()); // Assuming Status is an enum
                ps.setInt(8, ((Task) projectTask).getSubProjectId());
                ps.setString(9, ((Task) projectTask).getAssignedTo());
                ps.setDouble(10, projectTask.getActualPrice());

                ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        projectTask.setId(generatedKeys.getInt(1)); // Set the generated ID
                    }
                }
            }
        } else {
            throw new SQLException("Unsupported ProjectTask type");
        }
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
