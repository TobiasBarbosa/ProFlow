package org.example.proflow.repository;

import org.example.proflow.model.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private DataBaseConnection dataBaseConnection = new DataBaseConnection();

    //***ACCESS ATTRIBUTES***-------------------------------------------------------------------------------------------
    SubProjectRepository subProjectRepository;

    //***CREATE PROJECT***---------------------------------------------------------------------------------------------C
    public void addProject(Project project) throws SQLException {
        String insertProjectQuery = """
                    INSERT INTO Project (name, description, start_date, end_date, status, profile_id, budget, duration, actual_price)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertProjectQuery)) {


            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setDate(3, Date.valueOf(project.getStartDate()));
            ps.setDate(4, Date.valueOf(project.getEndDate()));
            ps.setString(5, project.getStatus().name());
            ps.setInt(6, project.getProfileId());
            ps.setDouble(7, project.getBudget());
//            ps.setInt(8, project.getDuration());
            //ps.setInt(8, project.getDaysUntilDone());
            ps.setObject(9, project.getActualPrice()); // Use setObject for nullable columns
            ps.executeUpdate();
        }
    }


    //***READ PROJECT***-----------------------------------------------------------------------------------------------R
    public Project getProjectById(int id) throws SQLException {
        String query = "SELECT * FROM Project WHERE id = ?";
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
                    project.setStartDate(rs.getDate("start_date").toLocalDate());
                    project.setEndDate(rs.getDate("end_date").toLocalDate());
                    project.setStatus(Status.valueOf(rs.getString("status")));
                    project.setProfileId(rs.getInt("profile_id"));
                    project.setBudget(rs.getDouble("budget"));
                    //project.setDaysUntilDone(rs.getInt("duration"));
                    Double actualPrice = rs.getObject("actual_price", Double.class);
                    project.setActualPrice(actualPrice != null ? actualPrice : 0.0); // Default to 0.0 if null
                }
            }
        }
        return project;
    }


    public List<Project> getAllProjects() {
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
                project.setStartDate(rs.getDate("start_date").toLocalDate());
                project.setEndDate(rs.getDate("end_date").toLocalDate());
                project.setStatus(Status.valueOf(rs.getString("status")));
                project.setProfileId(rs.getInt("profile_id"));
                project.setBudget(rs.getDouble("budget"));
                //project.setDaysUntilDone(rs.getInt("duration"));

                // Handle null for actual_price explicitly
                Double actualPrice = rs.getObject("actual_price", Double.class);
                project.setActualPrice(actualPrice != null ? actualPrice : 0.0); // Default to 0.0 if null
                projects.add(project);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }


    //***UPDATE PROJECT***---------------------------------------------------------------------------------------------U
    public void updateProject(Project project) throws SQLException {
        String updateProjectQuery = """
                    UPDATE Project 
                    SET name = ?, description = ?, start_date = ?, end_date = ?, status = ?, profile_id = ?, budget = ?, duration = ?, actual_price = ? 
                    WHERE id = ?
                """;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(updateProjectQuery)) {

            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setDate(3, Date.valueOf(project.getStartDate()));
            ps.setDate(4, Date.valueOf(project.getEndDate()));
            ps.setString(5, project.getStatus().name());
            ps.setInt(6, project.getProfileId());
            ps.setDouble(7, project.getBudget());
            //ps.setInt(8, project.getDaysUntilDone());
            ps.setObject(9, project.getActualPrice());
            ps.setInt(10, project.getId());
            ps.executeUpdate();
        }
    }


    //***DELETE PROJECT***---------------------------------------------------------------------------------------------D
    public void deleteProject(int id) throws SQLException {
        String deleteProjectQuery = "DELETE FROM Project WHERE id = ?";

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteProjectQuery)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    //***OTHER METHODS***-----------------------------------------------------------------------------------------------
    public void getSubProjectsForProject() throws SQLException{
        for (Project project : getAllProjects()){
            List<SubProject> subProjects = new ArrayList<>();
            int projectId = project.getId();
            for (SubProject sp : subProjectRepository.getAllSubProjects()){
                if(projectId == sp.getProjectId()){
                    subProjects.add(sp);
                }
            }
            project.setSubProjects(subProjects);
        }
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
