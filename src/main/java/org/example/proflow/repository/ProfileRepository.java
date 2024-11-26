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
//    public void addProfile(Profile profile) {
//        String insertProfileQuery = """
//        INSERT INTO Profile (name, lastName, email, password)
//        VALUES (?, ?, ?, ?)
//    """;
//
//
//
//        try (Connection con = DriverManager.getConnection(db_url, db_username, db_password)) {
//            PreparedStatement profileStatement = con.prepareStatement(insertProfileQuery);
//
//            // Check for duplicate email
////            for (Profile p : getAllProfiles()) {
////                if (profile.getEmail().equalsIgnoreCase(p.getEmail())) {
////                    throw new ProfileException("Profile with this email already exists");
////                }
////            }
//
//            profileStatement.setString(1, profile.getFirstName());
//            profileStatement.setString(2, profile.getLastName());
//            profileStatement.setString(3, profile.getEmail());
//            profileStatement.setString(4, profile.getPassword());
//            profileStatement.executeUpdate();
//
//        } catch (SQLException ex) {
//            // throw new ProfileException("Error while adding profile: " + e.getMessage());
//            throw new RuntimeException(ex);
//        }
//        }

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
//    @Override
//    public Profile getProfileByEmailAndPassword(String profileEmail, String profilePassword) throws ProfileException {
//        for (Profile profile: getAllProfiles()) {
//            if (profile.getProfileEmail().equalsIgnoreCase(profileEmail) && profile.getProfilePassword().equalsIgnoreCase(profilePassword))
//                return profile;
//        }
//        return null;
//    }
//
//    @Override
//    public Profile getProfileById(int profileId) throws ProfileException {
//        for (Profile profile: getAllProfiles()) {
//            if (profile.getProfileId() == (profileId))
//                return profile;
//        }
//        return null;
//    }

//    @Override
//    public List<Profile> getAllProfiles() throws ProfileException {
//        List<Profile> profiles = new ArrayList<>();
//
//        try (Connection con = DriverManager.getConnection(db_url, db_username, db_password)) {
//            String query = "SELECT * FROM Profile";
//            Statement stmt = con.createStatement(); //Opretter et statement til at udføre SQL-forespørgsler
//            ResultSet rs = stmt.executeQuery(query); //Udfører en SELECT-forespørgsel og returnerer resultaterne som et ResultSet.
//
//            while (rs.next()) {
//                String profileName = rs.getString("profileName");
//                String profileLastName = rs.getString("profileLastName");
//                String profileEmail = rs.getString("profileEmail");
//                String profilePassword = rs.getString("profilePassword");
//                int profileId = rs.getInt("profileId");
//
//                Profile profileObj = new Profile(profileName,profileLastName,profileEmail,profilePassword, profileId);
//                profiles.add(profileObj);
//            }
//        } catch (SQLException ex) {
//            //throw new RuntimeException(ex);
//            throw new ProfileException("Something wrong with getAllProfiles()"); //TODO ret error message så det passer til users behov
//        }
//
//        return profiles;
//    }


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
//    @Override
//    public void updateProfile(Profile profile) throws ProfileException{
//        String updateQuery = """
//        UPDATE Profile
//        SET profileName = ?, profileLastName = ?, profileEmail = ?, profilePassword = ?
//        WHERE profileId = ?
//    """;
//
//        try (Connection con = DriverManager.getConnection(db_url, db_username, db_password);
//             PreparedStatement profileStatement = con.prepareStatement(updateQuery)) {
//
//            // Set parameter values
//            profileStatement.setString(1, profile.getProfileName());
//            profileStatement.setString(2, profile.getProfileLastName());
//            profileStatement.setString(3, profile.getProfileEmail());
//
//            // Handle possible null values for password
//            if (profile.getProfilePassword() != null) {
//                profileStatement.setString(4, profile.getProfilePassword());
//            } else {
//                profileStatement.setNull(4, java.sql.Types.VARCHAR);
//            }
//
//            // Set profile ID
//            profileStatement.setInt(5, profile.getProfileId());
//
//            // Execute the update
//            int rowsUpdated = profileStatement.executeUpdate();
//            if (rowsUpdated == 0) {
//                System.out.println("Update failed: No profile found with ID " + profile.getProfileId());
//            } else {
//                System.out.println("Profile updated successfully for ID " + profile.getProfileId());
//            }
//
//        } catch (SQLException e) {
//            //e.printStackTrace();
//            throw new ProfileException("Something wrong with updateProfile()"); //TODO ret error message så det passer til users behov
//        }
//    }

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
  //  @Override
//    public void deleteProfile(Profile profile) throws ProfileException{
//        String deleteQuery = "DELETE FROM Profile WHERE profileId = ?";
//
//        try (Connection con = DriverManager.getConnection(db_url, db_username, db_password)) {
//            PreparedStatement prepstmt = con.prepareStatement(deleteQuery);
//            prepstmt.setInt(1, profile.getProfileId());  // Assuming profile has a getProfileId() method
//            prepstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            //e.printStackTrace();
//            throw new ProfileException("Something wrong with deleteProfile()"); //TODO ret error message så det passer til users behov
//        }
//    }

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


