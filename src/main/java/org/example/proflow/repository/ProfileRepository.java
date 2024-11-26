package org.example.proflow.repository;

import org.example.proflow.model.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProfileRepository {


    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
//    private String db_url = System.getenv("DB_URL");
//    private String db_username = System.getenv("DB_USER");
//    private String db_password = System.getenv("DB_PASSWORD");

    //***METHODS***-----------------------------------------------------------------------------------------------------

    public class DatabaseConnection {
        private static String db_url = System.getenv("DB_URL");
        private static String db_username = System.getenv("DB_USER");
        private static String db_password = System.getenv("DB_PASSWORD");

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(db_url, db_username, db_password);
        }
    }

    //***CREATE PROFILE***---------------------------------------------------------------------------------------------C

    //chat version (extracted connection)
    public void addProfile(Profile profile)  {
        String insertProfileQuery = """
        INSERT INTO Profile (name, lastName, email, password) 
        VALUES (?, ?, ?, ?)
    """;

        try (Connection con = DatabaseConnection.getConnection()) {
            // Check for duplicate email
//            for (Profile p : getAllProfiles()) {
//                if (profile.getEmail().equalsIgnoreCase(p.getEmail())) {
//                    throw new ProfileException("Profile with this email already exists");
//                }
//            }

            try (PreparedStatement ps = con.prepareStatement(insertProfileQuery)) {
                ps.setString(1, profile.getFirstName());
                ps.setString(2, profile.getLastName());
                ps.setString(3, profile.getEmail());
                ps.setString(4, profile.getPassword());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
           // throw new ProfileException("Error while adding profile: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }



    //***READ PROFILE(S)***--------------------------------------------------------------------------------------------R

    //chat version
    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<>();
        String query = "SELECT * FROM Profile";

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Profile profile = new Profile();
                profile.setId(rs.getInt("id"));
                profile.setFirstName(rs.getString("name"));
                profile.setLastName(rs.getString("lastName"));
                profile.setEmail(rs.getString("email"));
                profile.setPassword(rs.getString("password"));
                profiles.add(profile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profiles;
    }


    //***UPDATE***-----------------------------------------------------------------------------------------------------U

    //chat version
    public void updateProfile(Profile profile)  {
        String updateProfileQuery = """
        UPDATE Profile SET name = ?, lastName = ?, email = ?, password = ? WHERE id = ?
    """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(updateProfileQuery)) {

            ps.setString(1, profile.getFirstName());
            ps.setString(2, profile.getLastName());
            ps.setString(3, profile.getEmail());
            ps.setString(4, profile.getPassword());
            ps.setInt(5, profile.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D

//chat version
    public void deleteProfile(int id)  {
        String deleteProfileQuery = "DELETE FROM Profile WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteProfileQuery)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


