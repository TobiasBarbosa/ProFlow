package org.example.proflow.repository;

import org.example.proflow.model.Status;
import org.example.proflow.model.SubProject;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubProjectRepository {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private DataBaseConnection dataBaseConnection = new DataBaseConnection();

    //***METHODS***-----------------------------------------------------------------------------------------------------
    //***CREATE SUBPROJECT***------------------------------------------------------------------------------------------C
    public void addSubProject(SubProject subProject) throws SQLException {
        String insertSubProjectQuery = """
        INSERT INTO SubProject (name, description, start_date, end_date, status, project_id)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertSubProjectQuery)) {

            ps.setString(1, subProject.getName());
            ps.setString(2, subProject.getDescription());
            ps.setDate(3, Date.valueOf(subProject.getStartDate()));
            ps.setDate(4, Date.valueOf(subProject.getEndDate()));
            ps.setString(5, String.valueOf(subProject.getStatus()));
            ps.setInt(6, subProject.getProjectId());
            ps.executeUpdate();
        }
    }

    //***READ SUBPROJECT(S)***-----------------------------------------------------------------------------------------R
    public List<SubProject> getAllSubProjects() {
        List<SubProject> subProjects = new ArrayList<>();
        String query = "SELECT * FROM SubProject";

        try (Connection con = dataBaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                SubProject subProject = new SubProject();
                subProject.setId(rs.getInt("id"));
                subProject.setName(rs.getString("name"));
                subProject.setDescription(rs.getString("description"));
                subProject.setStartDate(rs.getDate("start_date").toLocalDate());
                subProject.setEndDate(rs.getDate("end_date").toLocalDate());
                subProject.setStatus(Status.valueOf(rs.getString("status")));
                subProject.setProjectId(rs.getInt("project_id"));
                subProjects.add(subProject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subProjects;
    }

    //GET SUB PROJECT BY ID
    public SubProject getSubProjectById(int id) throws SQLException {
        String query = "SELECT * FROM SubProject WHERE id = ?";
        SubProject subProject = null;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    subProject = new SubProject();
                    subProject.setId(rs.getInt("id"));
                    subProject.setName(rs.getString("name"));
                    subProject.setDescription(rs.getString("description"));
                    subProject.setStartDate(rs.getDate("start_date").toLocalDate());
                    subProject.setEndDate(rs.getDate("end_date").toLocalDate());
                    subProject.setStatus(Status.valueOf(rs.getString("status")));
                    subProject.setProjectId(rs.getInt("project_id"));
                }
            }
        }
        return subProject;
    }

    //***UPDATE SUBPROJECT***------------------------------------------------------------------------------------------U
    public void updateSubProject(SubProject subProject) throws SQLException {
        String updateSubProjectQuery = """
        UPDATE SubProject
        SET name = ?, description = ?, start_date = ?, end_date = ?, status = ?, project_id = ?
        WHERE id = ?
    """;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(updateSubProjectQuery)) {

            ps.setString(1, subProject.getName());
            ps.setString(2, subProject.getDescription());
            ps.setDate(3, Date.valueOf(subProject.getStartDate()));
            ps.setDate(4, Date.valueOf(subProject.getEndDate()));
            ps.setString(5, String.valueOf(subProject.getStatus()));
            ps.setInt(6, subProject.getProjectId());
            ps.setInt(7, subProject.getId());
            ps.executeUpdate();
        }
    }

    //***DELETE SUBPROJECT***------------------------------------------------------------------------------------------D
    public void deleteSubProject(int id) throws SQLException {
        String deleteSubProjectQuery = "DELETE FROM SubProject WHERE id = ?";

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteSubProjectQuery)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    //***END***---------------------------------------------------------------------------------------------------------
}


