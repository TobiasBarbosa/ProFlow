package org.example.proflow.repository;

import org.example.proflow.model.Status;
import org.example.proflow.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    public class DatabaseConnection {
        private static String db_url = System.getenv("DB_URL");
        private static String db_username = System.getenv("DB_USER");
        private static String db_password = System.getenv("DB_PASSWORD");

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(db_url, db_username, db_password);
        }
    }



    //ADD TASK

    public void addTask(Task task) throws SQLException {
        String insertTaskQuery = """
        INSERT INTO Tasks (name, description, start_date, end_date, status, assigned_to, subproject_id)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertTaskQuery)) {

            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setDate(3, Date.valueOf(task.getStartDate()));  // assuming startDate is LocalDate
            ps.setDate(4, Date.valueOf(task.getEndDate()));    // assuming endDate is LocalDate
            ps.setString(5, String.valueOf(task.getStatus()));
            ps.setString(6, task.getAssignedTo());
            ps.setInt(7, task.getSubProjectId());  // Assuming you have a subProjectId field in Tasks
            ps.executeUpdate();
        }
    }




    //GET ALL TASKS
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM Tasks";

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setName(rs.getString("name"));
                task.setDescription(rs.getString("description"));
                task.setStartDate(rs.getDate("start_date").toLocalDate());
                task.setEndDate(rs.getDate("end_date").toLocalDate());
                task.setStatus(Status.valueOf(rs.getString("status")));
                task.setAssignedTo(rs.getString("assigned_to"));
                task.setSubProjectId(rs.getInt("subproject_id"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }




    //GET TASKS BY ID
    public Task getTaskById(int id) throws SQLException {
        String query = "SELECT * FROM Tasks WHERE id = ?";
        Task task = null;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    task = new Task();
                    task.setId(rs.getInt("id"));
                    task.setName(rs.getString("name"));
                    task.setDescription(rs.getString("description"));
                    task.setStartDate(rs.getDate("start_date").toLocalDate());
                    task.setEndDate(rs.getDate("end_date").toLocalDate());
                    task.setStatus(Status.valueOf(rs.getString("status")));
                    task.setAssignedTo(rs.getString("assigned_to"));
                    task.setSubProjectId(rs.getInt("subproject_id"));
                }
            }
        }
        return task;
    }



    //UPDATE TASK
    public void updateTask(Task task) throws SQLException {
        String updateTaskQuery = """
        UPDATE Tasks
        SET name = ?, description = ?, start_date = ?, end_date = ?, status = ?, assigned_to = ?, subproject_id = ?
        WHERE id = ?
    """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(updateTaskQuery)) {

            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setDate(3, Date.valueOf(task.getStartDate()));  // assuming startDate is LocalDate
            ps.setDate(4, Date.valueOf(task.getEndDate()));    // assuming endDate is LocalDate
            ps.setString(5, String.valueOf(task.getStatus()));
            ps.setString(6, task.getAssignedTo());
            ps.setInt(7, task.getSubProjectId());
            ps.setInt(8, task.getId());
            ps.executeUpdate();
        }
    }




    //DELETE TASK
    public void deleteTask(int id) throws SQLException {
        String deleteTaskQuery = "DELETE FROM Tasks WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteTaskQuery)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

}



//ADD TASK

//    public void addTask(Task task) throws SQLException {
//        String insertTaskQuery = """
//        INSERT INTO Tasks (name, description, start_date, end_date, status, assigned_to, sub_project_id)
//        VALUES (?, ?, ?, ?, ?, ?, ?)
//    """;
//
//        try (Connection con = DatabaseConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(insertTaskQuery)) {
//
//            ps.setString(1, task.getName());
//            ps.setString(2, task.getDescription());
//            ps.setDate(3, Date.valueOf(task.getStartDate()));  // assuming startDate is LocalDate
//            ps.setDate(4, Date.valueOf(task.getEndDate()));    // assuming endDate is LocalDate
//            ps.setString(5, task.getStatus());
//            ps.setString(6, task.getAssignedTo());
//            ps.setInt(7, task.getSubProjectId());  // Assuming you have a subProjectId field in Tasks
//            ps.executeUpdate();
//        }
//    }




//GET ALL TASKS

//    public Task getTaskById(int id) throws SQLException {
//        String query = "SELECT * FROM Tasks WHERE id = ?";
//        Task task = null;
//
//        try (Connection con = DatabaseConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(query)) {
//
//            ps.setInt(1, id);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    task = new Task();
//                    task.setId(rs.getInt("id"));
//                    task.setName(rs.getString("name"));
//                    task.setDescription(rs.getString("description"));
//                    task.setStartDate(rs.getDate("start_date").toLocalDate());
//                    task.setEndDate(rs.getDate("end_date").toLocalDate());
//                    task.setStatus(rs.getString("status"));
//                    task.setAssignedTo(rs.getString("assigned_to"));
//                    task.setSubProjectId(rs.getInt("sub_project_id"));
//                }
//            }
//        }
//        return task;
//    }



//GET TASK BY ID

//    public Task getTaskById(int id) throws SQLException {
//        String query = "SELECT * FROM Tasks WHERE id = ?";
//        Task task = null;
//
//        try (Connection con = DatabaseConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(query)) {
//
//            ps.setInt(1, id);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    task = new Task();
//                    task.setId(rs.getInt("id"));
//                    task.setName(rs.getString("name"));
//                    task.setDescription(rs.getString("description"));
//                    task.setStartDate(rs.getDate("start_date").toLocalDate());
//                    task.setEndDate(rs.getDate("end_date").toLocalDate());
//                    task.setStatus(rs.getString("status"));
//                    task.setAssignedTo(rs.getString("assigned_to"));
//                    task.setSubProjectId(rs.getInt("sub_project_id"));
//                }
//            }
//        }
//        return task;
//    }










//UPDATE TASK

//    public void updateTask(Task task) throws SQLException {
//        String updateTaskQuery = """
//        UPDATE Tasks
//        SET name = ?, description = ?, start_date = ?, end_date = ?, status = ?, assigned_to = ?, sub_project_id = ?
//        WHERE id = ?
//    """;
//
//        try (Connection con = DatabaseConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(updateTaskQuery)) {
//
//            ps.setString(1, task.getName());
//            ps.setString(2, task.getDescription());
//            ps.setDate(3, Date.valueOf(task.getStartDate()));  // assuming startDate is LocalDate
//            ps.setDate(4, Date.valueOf(task.getEndDate()));    // assuming endDate is LocalDate
//            ps.setString(5, task.getStatus());
//            ps.setString(6, task.getAssignedTo());
//            ps.setInt(7, task.getSubProjectId());
//            ps.setInt(8, task.getId());
//            ps.executeUpdate();
//        }
//    }






//DELETE TASK
//    public void deleteTask(int id) throws SQLException {
//        String deleteTaskQuery = "DELETE FROM Tasks WHERE id = ?";
//
//        try (Connection con = DatabaseConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(deleteTaskQuery)) {
//
//            ps.setInt(1, id);
//            ps.executeUpdate();
//        }
//    }