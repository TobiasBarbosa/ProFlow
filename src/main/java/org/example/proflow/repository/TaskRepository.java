package org.example.proflow.repository;

import org.example.proflow.model.Status;
import org.example.proflow.model.Task;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private DataBaseConnection dataBaseConnection = new DataBaseConnection();

    //***METHODS***-----------------------------------------------------------------------------------------------------
    //***CREATE TASK***------------------------------------------------------------------------------------------------C
    public void addTask(Task task) throws SQLException {
        String insertTaskQuery = """
                    INSERT INTO Task (name, description, start_date, end_date, status, assigned_to, sub_project_id, price, duration, location)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertTaskQuery)) {

            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setDate(3, Date.valueOf(task.getStartDate()));
            ps.setDate(4, Date.valueOf(task.getEndDate()));
            ps.setString(5, task.getStatus().name());
            ps.setString(6, task.getAssignedTo());
            ps.setInt(7, task.getSubProjectId());
            ps.setDouble(8, task.getTaskPrice());
            ps.setDouble(9, task.getDaysUntilDone());
            ps.setString(10, task.getLocation());
            ps.executeUpdate();
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
                int subProjectId = rs.getInt("sub_project_id");
                task.setId(rs.getInt("id"));
                task.setName(rs.getString("name"));
                task.setDescription(rs.getString("description"));
                task.setStartDate(rs.getDate("start_date").toLocalDate());
                //task.setEndDate(rs.getDate("end_date").toLocalDate(), subProjectId);
                task.setEndDate(rs.getDate("end_date").toLocalDate());
                task.setStatus(Status.valueOf(rs.getString("status")));
                task.setAssignedTo(rs.getString("assigned_to"));
                task.setSubProjectId(rs.getInt("sub_project_id"));
                task.setTaskPrice(rs.getDouble("price"));
                task.setDaysUntilDone((int) rs.getDouble("duration")); //TODO change type to int in database
                task.setLocation(rs.getString("location"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }


    //GET TASKS BY ID
    public Task getTaskById(int id) throws SQLException {
        String query = "SELECT * FROM Task WHERE id = ?";
        Task task = null;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    task = new Task();
                    int subProjectId = rs.getInt("sub_project_id");
                    task.setId(rs.getInt("id"));
                    task.setName(rs.getString("name"));
                    task.setDescription(rs.getString("description"));
                    task.setStartDate(rs.getDate("start_date").toLocalDate());
                    // task.setEndDate(rs.getDate("end_date").toLocalDate(), subProjectId);
                    task.setEndDate(rs.getDate("end_date").toLocalDate());
                    task.setStatus(Status.valueOf(rs.getString("status")));
                    task.setAssignedTo(rs.getString("assigned_to"));
                    task.setSubProjectId(rs.getInt("sub_project_id"));
                    task.setTaskPrice(rs.getDouble("price")); // Not null column
                    //todo change type to int in database
                    task.setDaysUntilDone((int) rs.getDouble("duration")); // Not null column
                    task.setLocation(rs.getString("location"));
                }
            }
        }
        return task;
    }


    //***UPDATE TASK***------------------------------------------------------------------------------------------------U
    public void updateTask(Task task) throws SQLException {
        String updateTaskQuery = """
                    UPDATE Task 
                    SET name = ?, description = ?, start_date = ?, end_date = ?, status = ?, assigned_to = ?, sub_project_id = ?, price = ?, duration = ?, location = ? 
                    WHERE id = ?
                """;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(updateTaskQuery)) {

            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setDate(3, Date.valueOf(task.getStartDate()));
            ps.setDate(4, Date.valueOf(task.getEndDate()));
            ps.setString(5, task.getStatus().name());
            ps.setString(6, task.getAssignedTo());
            ps.setInt(7, task.getSubProjectId());
            ps.setDouble(8, task.getTaskPrice());
            ps.setDouble(9, task.getDaysUntilDone());
            ps.setString(10, task.getLocation());
            ps.setInt(11, task.getId());
            ps.executeUpdate();
        }
    }


    //***DELETE TASK***------------------------------------------------------------------------------------------------D
    public void deleteTask(int id) throws SQLException {
        String deleteTaskQuery = "DELETE FROM Task WHERE id = ?";

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteTaskQuery)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    //***END***---------------------------------------------------------------------------------------------------------
}

