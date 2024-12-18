package org.example.proflow.repository;

import org.example.proflow.config.DataBaseConnection;
import org.example.proflow.model.Project;
import org.example.proflow.model.Status;
import org.example.proflow.model.SubProject;
import org.example.proflow.util.interfaces.ProjectRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository implements ProjectRepositoryInterface {

    //***TO DO***-------------------------------------------------------------------------------------------------------
    //TODO fix duration in database & methods
    //TODO make method that ensures warning when actualPrice passes budget
    //TODO what do we do with duration?
    //TODO createdDate final?

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final DataBaseConnection dataBaseConnection;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public ProjectRepository(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    //***CREATE PROJECT***---------------------------------------------------------------------------------------------C
    public void addProject(Project project) throws SQLException {
        String insertProjectQuery = """
                    INSERT INTO Project (name, description, start_date, end_date, total_est_hours, status, budget, actual_price, profile_id)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertProjectQuery, Statement.RETURN_GENERATED_KEYS)) {

            //Database setter Id
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setDate(3, Date.valueOf(project.getStartDate()));
            ps.setDate(4, Date.valueOf(project.getEndDate()));
            ps.setDouble(5, project.getTotalEstHours());
            ps.setString(6, project.getStatus().getDisplayStatus());
            ps.setDouble(7, project.getBudget());
            ps.setObject(8, project.getActualPrice());
            ps.setInt(9, project.getProfileId());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    project.setId(generatedKeys.getInt(1)); // Set the generated ID to the project object
                }
            }
        }
    }

    //***READ PROJECT***-----------------------------------------------------------------------------------------------R
    public Project getProjectById(int id) throws SQLException {
        String query = "SELECT * FROM Project WHERE id = ?"; //Project ID?
        Project project = null;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    project = new Project();
                    project.setId(rs.getInt("id"));
                    project.setName(rs.getString("name"));
                    project.setDescription(rs.getString("description"));
                    project.setCreatedDate(rs.getDate("created_date").toLocalDate());
                    project.setStartDate(rs.getDate("start_date").toLocalDate());
                    project.setEndDate(rs.getDate("end_date").toLocalDate());
                    project.setTotalEstHours(rs.getDouble("total_est_hours"));
                    project.setStatus(Status.valueOf(rs.getString("status")));
                    project.setBudget(rs.getDouble("budget"));
                    project.setActualPrice(rs.getDouble("actual_price"));
                    project.setProfileId(rs.getInt("profile_id"));

                }
            }
            try {
                project.setSubProjects(getSubProjectsFromProject(project.getId()));
            } catch (NullPointerException n) {
                n.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    public List<Project> getAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM Project";

        try (Connection con = dataBaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setCreatedDate(rs.getDate("created_date").toLocalDate());
                project.setStartDate(rs.getDate("start_date").toLocalDate());
                project.setEndDate(rs.getDate("end_date").toLocalDate());
                project.setTotalEstHours(rs.getDouble("total_est_hours"));
                project.setStatus(Status.valueOf(rs.getString("status")));
                project.setBudget(rs.getDouble("budget"));
                project.setActualPrice(rs.getDouble("actual_price"));
                project.setProfileId(rs.getInt("profile_id"));
                projects.add(project);
            }
            // loop through all Projects and add the related subprojects.
            for (Project p : projects) {
                p.setSubProjects(getSubProjectsFromProject(p.getId()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    public List<Project> getProjectsFromProfile(int profileId) {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM Project WHERE profile_id = ?";

        try (Connection con = dataBaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setCreatedDate(rs.getDate("created_date").toLocalDate());
                project.setStartDate(rs.getDate("start_date").toLocalDate());
                project.setEndDate(rs.getDate("end_date").toLocalDate());
                project.setTotalEstHours(rs.getDouble("total_est_hours"));
                project.setStatus(Status.valueOf(rs.getString("status")));
                project.setBudget(rs.getDouble("budget"));
                project.setActualPrice(rs.getDouble("actual_price"));
                project.setProfileId(rs.getInt("profile_id"));
                projects.add(project);
            }
            // loop through all Projects and add the related subprojects.
            for (Project p : projects) {
                p.setSubProjects(getSubProjectsFromProject(p.getId()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }


    public List<SubProject> getSubProjectsFromProject(int projectId) throws SQLException {
        String query = "SELECT * FROM SubProject WHERE project_id = ?";
        List<SubProject> subProjectsFromProject = new ArrayList<>();
        SubProject subProject = null;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, projectId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    subProject = new SubProject();
                    subProject.setId(rs.getInt("id"));
                    subProject.setName(rs.getString("name"));
                    subProject.setDescription(rs.getString("description"));
                    subProject.setCreatedDate(rs.getDate("created_date").toLocalDate());
                    subProject.setStartDate(rs.getDate("start_date").toLocalDate());
                    subProject.setEndDate(rs.getDate("end_date").toLocalDate());
                    subProject.setStatus(Status.valueOf(rs.getString("status")));
                    subProject.setBudget(rs.getDouble("budget"));
                    subProject.setProjectId(rs.getInt("project_id"));
                    subProject.setAssignedTo(rs.getString("assigned_to"));
                    subProject.setTotalEstHours(rs.getDouble("total_est_hours"));
                    subProject.setActualPrice(rs.getDouble("actual_price"));
                    subProjectsFromProject.add(subProject);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subProjectsFromProject;
    }

    //***UPDATE PROJECT***---------------------------------------------------------------------------------------------U
    public void updateProject(Project project) throws SQLException {
        String updateProjectQuery = """
                    UPDATE Project 
                    SET name = ?, description = ?, start_date = ?, end_date = ?, status = ?, budget = ?
                    WHERE id = ?
                """;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(updateProjectQuery)) {

            //id can not change
            //createdDate can not change
            //totalEstHours can not change
            //actualPrice can not change
            //profileId can not change
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setDate(3, Date.valueOf(project.getStartDate()));
            ps.setDate(4, Date.valueOf(project.getEndDate()));
            ps.setString(5, project.getStatus().getDisplayStatus());
            ps.setDouble(6, project.getBudget());

            ps.setInt(7, project.getId()); // sets the id parameter (WHERE id = ? in sql script)

            ps.executeUpdate();
        }
    }

    //***DELETE PROJECT***---------------------------------------------------------------------------------------------D
    public void deleteProject(int projectId) throws SQLException {
        String deleteProjectQuery = "DELETE FROM Project WHERE id = ?";

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteProjectQuery)) {

            ps.setInt(1, projectId);
            ps.executeUpdate();
        }
    }

    //***TEST METHODS***------------------------------------------------------------------------------------------------
    //FOR TEST PURPOSES!!
    public void clearProjectsForTesting() {
        String query = "DELETE FROM Project";
        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear projects", e);
        }
    }

    //***END***---------------------------------------------------------------------------------------------------------
}






















