package org.example.proflow.repository;

import org.example.proflow.config.DataBaseConnection;
import org.example.proflow.model.Status;
import org.example.proflow.model.Task;
import org.example.proflow.util.interfaces.TaskRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository implements TaskRepositoryInterface {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final DataBaseConnection dataBaseConnection;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    // Constructor-based injection
    public TaskRepository(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    //***CREATE TASK***------------------------------------------------------------------------------------------------C
    public void addTask(Task task) throws SQLException {
        String insertTaskQuery = """
                    INSERT INTO Task (name, description, location, start_date, end_date, total_est_hours, status, sub_project_id, assigned_to, price)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertTaskQuery, Statement.RETURN_GENERATED_KEYS)) {

            //Id oprettes i database
            // Calculate days until done håndteres i class
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getLocation());
            ps.setDate(4, Date.valueOf(task.getStartDate()));
            ps.setDate(5, Date.valueOf(task.getEndDate()));
            ps.setDouble(6, task.getTotalEstHours());
            ps.setString(7, task.getStatus().name());
            ps.setInt(8, task.getSubProjectId());
            ps.setString(9, task.getAssignedTo());
            ps.setDouble(10, task.getActualPrice());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getInt(1)); // Set the generated ID to the project object
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //***READ TASK(S)***-----------------------------------------------------------------------------------------------R
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM Task";

        try (Connection con = dataBaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Task task = new Task();

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
                task.setActualPrice(rs.getDouble("price"));


                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }


    public Task getTaskById(int id) throws SQLException {
        String query = "SELECT * FROM Task WHERE id = ?";
        Task task = null;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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


                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return task;
    }

    //***UPDATE TASK***------------------------------------------------------------------------------------------------U
    public void updateTask(Task task) throws SQLException {
        String updateTaskQuery = """
                    UPDATE Task 
                    SET name = ?, description = ?, location = ?, start_date = ?, end_date = ?, total_est_hours = ?, status = ?, assigned_to = ?, price = ? 
                    WHERE id = ?
                """;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(updateTaskQuery)) {

            //id can not change
            //createdDate can not change
            //subProjectId can not change
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getLocation());
            ps.setDate(4, Date.valueOf(task.getStartDate()));
            ps.setDate(5, Date.valueOf(task.getEndDate()));
            ps.setDouble(6, task.getTotalEstHours());
            ps.setString(7, task.getStatus().name());
            ps.setString(8, task.getAssignedTo());
            ps.setDouble(9, task.getActualPrice());

            ps.setInt(10, task.getId()); // sets the id parameter (WHERE id = ? in sql script)

            ps.executeUpdate();
        }
    }

    //***DELETE TASK***------------------------------------------------------------------------------------------------D
    public void deleteTask(int taskId) throws SQLException {
        String deleteTaskQuery = "DELETE FROM Task WHERE id = ?";

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteTaskQuery)) {

            ps.setInt(1, taskId);
            ps.executeUpdate();
        }
    }

    public void deleteTasksBySubProjectId(int subProjectId) throws SQLException {
        String deleteQuery = "DELETE FROM Task WHERE sub_project_id = ?";
        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteQuery)) {
            ps.setInt(1, subProjectId);
            ps.executeUpdate();
        }
    }

    //FOR TEST PURPOSES!!
    public void clearTasksForTesting() {
        String query = "DELETE FROM Task";
        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear tasks", e);
        }
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
