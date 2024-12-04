package org.example.proflow.service;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProfileService {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final ProfileRepository profileRepository;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    public boolean login(String profileEmail, String profilePassword) throws ProfileException {
        Profile profile = profileRepository.getProfileByEmailAndPassword(profileEmail, profilePassword);
        if (profile != null)
            return profile.getPassword().equals(profilePassword);
        return false;
    }

    //***CREATE PROFILE***---------------------------------------------------------------------------------------------C
    public void addProfile(Profile profile) {
        profileRepository.addProfile(profile);
    }

    //***READ PROFILE(S)***--------------------------------------------------------------------------------------------R
    public List<Profile> getAllProfiles() {
        return profileRepository.getAllProfiles();
    }

    public Profile getProfileById(int id) throws ProfileException, SQLException {
        return profileRepository.getProfileById(id);
    }

    public List<Project> getProjectsFromProfile(int profileId) throws ProfileException {
        return profileRepository.getProjectsFromProfile(profileId);
    }

    public Profile getProfileByEmailAndPassword(String profileEmail, String profilePassword) throws ProfileException {
        return profileRepository.getProfileByEmailAndPassword(profileEmail, profilePassword);
    }

    //***UPDATE PROFILE***---------------------------------------------------------------------------------------------U
    public void updateProfile(Profile profile) {
        profileRepository.updateProfile(profile);
    }

    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D
    public void deleteProfile(int id) throws ProfileException {
        profileRepository.deleteProfile(id);
    }
    //**END***----------------------------------------------------------------------------------------------------------
}
