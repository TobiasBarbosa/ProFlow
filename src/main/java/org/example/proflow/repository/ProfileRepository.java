package org.example.proflow.repository;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.Status;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO: add exceptions
@Repository
public class ProfileRepository {

    //***PROFILE METHODS***---------------------------------------------------------------------------------------------
    //***CREATE PROFILE***---------------------------------------------------------------------------------------------C
    public void addProfile(Profile profile) {
        String insertProfileQuery = """
                    INSERT INTO Profile (name, lastName, email, password) 
                    VALUES (?, ?, ?, ?)
                """;

        try (Connection con = DataBaseConnection.getConnection()) {
            //TODO:
            // Check for duplicate email
//            for (Profile p : getAllProfiles()) {
//                if (profile.getEmail().equalsIgnoreCase(p.getEmail())) {
//                    throw new ProfileException("Profile with this email already exists");
//                }
//            }

            try (PreparedStatement ps = con.prepareStatement(insertProfileQuery, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, profile.getFirstName());
                ps.setString(2, profile.getLastName());
                ps.setString(3, profile.getEmail());
                ps.setString(4, profile.getPassword());
                ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        profile.setId(generatedKeys.getInt(1)); // Set the generated ID to the project object
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //***READ PROFILE(S)***--------------------------------------------------------------------------------------------R
    public List<Profile> getAllProfiles() throws ProfileException{
        List<Profile> profiles = new ArrayList<>();
        String query = "SELECT * FROM Profile";

        try (Connection con = DataBaseConnection.getConnection();
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
            {
                try {
                    for (Profile profile : profiles)
                profile.setProjects(getProjectsFromProfile(profile.getId()));
                } catch (NullPointerException n){
                    n.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profiles;
    }


    public Profile getProfileById(int id) throws ProfileException {
        String query = "SELECT * FROM Profile WHERE id = ?";
        Profile profile = null;

        try (Connection con = DataBaseConnection.getConnection();
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

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }  //TODO måske refaktorere?
        try {
            profile.setProjects(getProjectsFromProfile(id));
        } catch (NullPointerException n){
            n.printStackTrace();
        }
        return profile; // Return the Profile object or null if not found
    }


    public Profile getProfileByEmailAndPassword(String profileEmail, String profilePassword) throws ProfileException {
        try {
            for (Profile profile : getAllProfiles()) {
                if (profile.getEmail().equalsIgnoreCase(profileEmail) && profile.getPassword().equals(profilePassword))
                    return profile;
            }
        } catch (ProfileException p){
            p.printStackTrace();
        }
        return null;
    }

    public List<Project> getProjectsFromProfile(int profileId) throws ProfileException {
        String query = "SELECT * FROM Project WHERE profile_id = ?";
        List<Project> projectsFromProfile = new ArrayList<>();
        Project project = null;

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, profileId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    project = new Project();
                    project.setId(rs.getInt("id"));
                    project.setName(rs.getString("name"));
                    project.setDescription(rs.getString("description"));
                    project.setCreatedDate(rs.getDate("created_date").toLocalDate());
                    project.setStartDate(rs.getDate("start_date").toLocalDate());
                    project.setEndDate(rs.getDate("end_date").toLocalDate());
                    project.setTotalEstHours(rs.getDouble("total_est_hours"));
                    project.setStatus(Status.valueOf(rs.getString("status")));
                    project.setBudget(rs.getDouble("budget"));
                    project.setActualPrice(rs.getDouble("actual_price"));
                    project.setProfileId(rs.getInt("profile_id"));

                }
                projectsFromProfile.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projectsFromProfile;
    }


    //***UPDATE***-----------------------------------------------------------------------------------------------------U
    public void updateProfile(Profile profile) {
        String updateProfileQuery = """
                    UPDATE Profile SET name = ?, lastName = ?, email = ?, password = ? WHERE id = ?
                """;

        try (Connection con = DataBaseConnection.getConnection();
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
    public void deleteProfile(int id) {
        String deleteProfileQuery = "DELETE FROM Profile WHERE id = ?";

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteProfileQuery)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //***FOR TEST PURPOSES ONLY!!***------------------------------------------------------------------------------------
    public void deleteAllProfiles() {
        String query = "DELETE FROM Profile";
        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear profiles", e);
        }
    }

    //***END***---------------------------------------------------------------------------------------------------------
}


