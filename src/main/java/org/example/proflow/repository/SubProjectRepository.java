package org.example.proflow.repository;

import org.example.proflow.model.Status;
import org.example.proflow.model.SubProject;
import org.example.proflow.model.Task;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubProjectRepository {

    //***EXAM QUESTIONS***----------------------------------------------------------------------------------------------
    // Kunne man have lavet en mere effektiv kode, eller mindre kode. eks.vis jdbc template??

    //***TO DO***-------------------------------------------------------------------------------------------------------
    //TODO createdDate final?

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private DataBaseConnection dataBaseConnection = new DataBaseConnection();

    //***ACCESS ATTRIBUTES***-------------------------------------------------------------------------------------------
    TaskRepository taskRepository;

    //***METHODS***-----------------------------------------------------------------------------------------------------
    //***CREATE SUBPROJECT***------------------------------------------------------------------------------------------C
    public void addSubProject(SubProject subProject) throws SQLException {
        String insertSubProjectQuery = """
                    INSERT INTO SubProject (name, description, created_date, start_date, end_date, status, budget, project_id, assigned_to, total_est_hours, actual_price)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertSubProjectQuery)) {

            //Database setter SubProjectId
            //How do we get projectId?
            ps.setString(1, subProject.getName());
            ps.setString(2, subProject.getDescription());
            ps.setDate(3, Date.valueOf(subProject.getCreatedDate()));
            ps.setDate(4, Date.valueOf(subProject.getStartDate()));
            ps.setDate(5, Date.valueOf(subProject.getEndDate()));
            ps.setString(6, subProject.getStatus().name());
            ps.setDouble(7, subProject.getBudget());
            ps.setInt(8, subProject.getProjectId()); //TODO hvordan henter vi projectId?
            ps.setString(9, subProject.getAssignedTo());
            ps.setDouble(10, subProject.getTotalEstHours());
            ps.setDouble(11, subProject.getActualPrice());
            //TODO hvordan håndterer vi calculateDaysUntilDone?
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
                subProject.setCreatedDate(rs.getDate("created_date").toLocalDate());
                subProject.setStartDate(rs.getDate("start_date").toLocalDate());
                subProject.setEndDate(rs.getDate("end_date").toLocalDate());
                subProject.setStatus(Status.valueOf(rs.getString("status")));
                subProject.setBudget(rs.getDouble("budget"));
                subProject.setProjectId(rs.getInt("project_id"));
                subProject.setAssignedTo(rs.getString("assigned_to"));
                subProject.setTotalEstHours(rs.getDouble("total_est_hours"));
                subProject.setActualPrice(rs.getDouble("actual_price"));
                //TODO hvordan håndterer vi calculateDaysUntilDone?
                //subProject.setDaysUntilDone(rs.getInt("duration"));
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
                    subProject.setCreatedDate(rs.getDate("created_date").toLocalDate());
                    subProject.setStartDate(rs.getDate("start_date").toLocalDate());
                    subProject.setEndDate(rs.getDate("end_date").toLocalDate());
                    subProject.setStatus(Status.valueOf(rs.getString("status")));
                    subProject.setBudget(rs.getDouble("budget"));
                    subProject.setProjectId(rs.getInt("project_id"));
                    subProject.setAssignedTo(rs.getString("assigned_to"));
                    subProject.setTotalEstHours(rs.getDouble("total_est_hours"));
                    subProject.setActualPrice(rs.getDouble("actual_price"));
                    //TODO hvordan håndterer vi calculateDaysUntilDone?
                    //subProject.setBudget(rs.getObject("price") != null ? rs.getDouble("price") : null); // TODO lav om
                    //subProject.setDaysUntilDone(rs.getInt("duration"));
                }
            }
        }
        return subProject;
    }

    //***UPDATE SUBPROJECT***------------------------------------------------------------------------------------------U
    public void updateSubProject(SubProject subProject) throws SQLException {
        String updateSubProjectQuery = """
                    UPDATE SubProject 
                    SET name = ?, description = ?, start_date = ?, end_date = ?, status = ?, budget = ?, assigned_to = ?
                    WHERE id = ?
                """;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(updateSubProjectQuery)) {

            //id is already set
            //createdDate can not change
            //projectId can not change
            //subProjectId can not change
            //totalEstHours can not change
            //actualPrice can not change
            ps.setString(1, subProject.getName());
            ps.setString(2, subProject.getDescription());
            ps.setDate(3, Date.valueOf(subProject.getStartDate()));
            ps.setDate(4, Date.valueOf(subProject.getEndDate()));
            ps.setString(5, subProject.getStatus().name());
            ps.setDouble(6, subProject.getBudget());
            ps.setString(7, subProject.getAssignedTo());
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

    //***OTHER METHODS***-----------------------------------------------------------------------------------------------
    public void getTasksForSubProject() throws SQLException{

        for (SubProject subProject : getAllSubProjects()){
            List<Task> tasks = new ArrayList<>();
            int subProjectId = subProject.getId();
            for (Task t : taskRepository.getAllTasks()){
                if(subProjectId == t.getSubProjectId()){
                    tasks.add(t);
                }
            }
            subProject.setTasks(tasks);
        }
    }

    //***END***---------------------------------------------------------------------------------------------------------
}


