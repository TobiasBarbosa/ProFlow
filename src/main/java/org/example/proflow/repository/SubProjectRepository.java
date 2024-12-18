package org.example.proflow.repository;

import org.example.proflow.config.DataBaseConnection;
import org.example.proflow.model.Status;
import org.example.proflow.model.SubProject;
import org.example.proflow.model.Task;
import org.example.proflow.util.interfaces.SubProjectRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubProjectRepository implements SubProjectRepositoryInterface {


    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final DataBaseConnection dataBaseConnection;
    //private TaskRepository taskRepository = new TaskRepository();

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    // Constructor-based injection
    public SubProjectRepository(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    //***CREATE SUBPROJECT***------------------------------------------------------------------------------------------C
    public void addSubProject(SubProject subProject) throws SQLException {
        String insertSubProjectQuery = """
                    INSERT INTO SubProject (name, description, start_date, end_date, status, budget, project_id, assigned_to, total_est_hours, actual_price)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertSubProjectQuery, Statement.RETURN_GENERATED_KEYS)) {

            //Database setter SubProjectId

            ps.setString(1, subProject.getName());
            ps.setString(2, subProject.getDescription());
            ps.setDate(3, Date.valueOf(subProject.getStartDate()));
            ps.setDate(4, Date.valueOf(subProject.getEndDate()));
            ps.setString(5, subProject.getStatus().name());
            ps.setDouble(6, subProject.getBudget());
            ps.setInt(7, subProject.getProjectId());
            ps.setString(8, subProject.getAssignedTo());
            ps.setDouble(9, subProject.getTotalEstHours());
            ps.setDouble(10, subProject.getActualPrice());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subProject.setId(generatedKeys.getInt(1)); // Set the generated ID to the project object
                }
            }

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

                subProjects.add(subProject);
            }
            for (SubProject subProject : subProjects) {
                subProject.setTasks(getTasksFromSubProject(subProject.getId()));
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

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (subProject != null) { //Tjekker at subprojeket ikke er null, kunne også have været en try catch
         List<Task> tasksForSubproject = getTasksFromSubProject(subProject.getId());
         subProject.setTasks(tasksForSubproject);
        }

        return subProject;
    }

    public List<Task> getTasksFromSubProject(int subProjectId) throws SQLException {
        String query = "SELECT * FROM Task WHERE sub_project_id = ?";
        List<Task> tasksFromSubProject = new ArrayList<>();
        Task task = null;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, subProjectId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    task = new Task();
                    task.setId(rs.getInt("id"));
                    task.setName(rs.getString("name"));
                    task.setDescription(rs.getString("description"));
                    task.setLocation(rs.getString("location"));
                    task.setCreatedDate(rs.getDate("created_date").toLocalDate());
                    task.setStartDate(rs.getDate("start_date").toLocalDate());
                    task.setEndDate(rs.getDate("end_date").toLocalDate());
                    task.setTotalEstHours(rs.getDouble("total_est_hours"));
                    task.setStatus(Status.valueOf(rs.getString("status")));
                    task.setSubProjectId(rs.getInt("sub_project_id"));
                    task.setAssignedTo(rs.getString("assigned_to"));
                    task.setActualPrice(rs.getDouble("price")); // Not null column
                    tasksFromSubProject.add(task);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasksFromSubProject;
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

            ps.setInt(8, subProject.getId()); // sets the id parameter (WHERE id = ? in sql script)

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

    //FOR TEST PURPOSES!!
    public void clearSubProjectsForTesting() { //TODO slettes når vi bruger nye testmetoder
        String query = "DELETE FROM SubProject";
        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear subprojects", e);
        }
    }



}

//***END***---------------------------------------------------------------------------------------------------------



