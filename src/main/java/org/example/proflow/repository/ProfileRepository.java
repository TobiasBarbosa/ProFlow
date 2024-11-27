package org.example.proflow.repository;

import org.example.proflow.model.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//TODO: add exceptions
@Repository
public class ProfileRepository {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private DataBaseConnection dataBaseConnection = new DataBaseConnection();

    //***METHODS***-----------------------------------------------------------------------------------------------------
    //***CREATE PROFILE***---------------------------------------------------------------------------------------------C
    public void addProfile(Profile profile)  {
        String insertProfileQuery = """
        INSERT INTO Profile (name, lastName, email, password) 
        VALUES (?, ?, ?, ?)
    """;

        try (Connection con = dataBaseConnection.getConnection()) {
            //TODO:
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
            throw new RuntimeException(e);
        }
    }

    //***READ PROFILE(S)***--------------------------------------------------------------------------------------------R
    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<>();
        String query = "SELECT * FROM Profile";

        try (Connection con = dataBaseConnection.getConnection();
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

    //TODO getProfileById
    public Profile getProfileById(int id) throws SQLException {
        String query = "SELECT * FROM Profile WHERE id = ?";
        Profile profile = null;

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id); // Bind the id parameter

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // If a result is found
                    profile = new Profile();
                    profile.setId(rs.getInt("id"));
                    profile.setFirstName(rs.getString("name"));
                    profile.setLastName(rs.getString("lastName"));
                    profile.setEmail(rs.getString("email"));
                    profile.setPassword(rs.getString("password"));
                }
            }
        }
        return profile; // Return the Profile object or null if not found
    }


    //***UPDATE***-----------------------------------------------------------------------------------------------------U
    public void updateProfile(Profile profile)  {
        String updateProfileQuery = """
        UPDATE Profile SET name = ?, lastName = ?, email = ?, password = ? WHERE id = ?
    """;

        try (Connection con = dataBaseConnection.getConnection();
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
    public void deleteProfile(int id)  {
        String deleteProfileQuery = "DELETE FROM Profile WHERE id = ?";

        try (Connection con = dataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteProfileQuery)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //***END***---------------------------------------------------------------------------------------------------------
}


