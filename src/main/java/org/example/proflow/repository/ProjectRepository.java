package org.example.proflow.repository;

import org.example.proflow.model.Project;
import org.example.proflow.model.Status;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ProjectRepository {

    public class DatabaseConnection {
        private static String db_url = System.getenv("DB_URL");
        private static String db_username = System.getenv("DB_USER");
        private static String db_password = System.getenv("DB_PASSWORD");

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(db_url, db_username, db_password);
        }
    }

    public void addProject(Project project) throws SQLException {
        String insertProjectQuery = """
        INSERT INTO Project (name, description, start_date, end_date, status, profile_id)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertProjectQuery)) {

            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setDate(3, Date.valueOf(project.getStartDate()));
            ps.setDate(4, Date.valueOf(project.getEndDate()));
            ps.setString(5, String.valueOf(project.getStatus()));
            ps.setInt(6, project.getProfileId());
            ps.executeUpdate();
        }
    }

    //***READ PROJECT***----------------------------------------------------------------------------------------------R
    public Project getProjectById(int id) throws SQLException {
        String query = "SELECT * FROM Project WHERE id = ?";
        Project project = null;

        try (Connection con = DatabaseConnection.getConnection();
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
                }
            }
        }
        return project;
    }

    //***UPDATE PROJECT***--------------------------------------------------------------------------------------------U
    public void updateProject(Project project) throws SQLException {
        String updateProjectQuery = """
        UPDATE Project SET name = ?, description = ?, start_date = ?, end_date = ?, status = ?, profile_id = ?
        WHERE id = ?
    """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(updateProjectQuery)) {

            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setDate(3, Date.valueOf(project.getStartDate()));
            ps.setDate(4, Date.valueOf(project.getEndDate()));
            ps.setString(5, String.valueOf(project.getStatus()));
            ps.setInt(6, project.getProfileId());
            ps.setInt(7, project.getId());
            ps.executeUpdate();
        }
    }

    //***DELETE PROJECT***---------------------------------------------------------------------------------------------D
    public void deleteProject(int id) throws SQLException {
        String deleteProjectQuery = "DELETE FROM Project WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteProjectQuery)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
