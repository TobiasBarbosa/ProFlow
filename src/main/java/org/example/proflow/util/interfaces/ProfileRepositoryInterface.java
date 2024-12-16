package org.example.proflow.util.interfaces;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;

import java.util.List;

public interface ProfileRepositoryInterface {

    // Create a new profile
    void addProfile(Profile profile);

    // Read a profile by its ID
    Profile getProfileById(int id) throws ProfileException;

    // Read a profile by its email and password
    Profile getProfileByEmailAndPassword(String profileEmail, String profilePassword) throws ProfileException;

    // Read all profiles
    List<Profile> getAllProfiles() throws ProfileException;

    // Get all projects associated with a profile by profile ID
    List<Project> getProjectsFromProfile(int profileId) throws ProfileException;

    // Update an existing profile
    void updateProfile(Profile profile);

    // Delete a profile by its ID
    void deleteProfile(int id);

    // Delete all profiles (for testing purposes only)
    void clearProfilesForTesting();
}
