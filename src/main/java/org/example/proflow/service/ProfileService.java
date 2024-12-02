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
    public ProfileService(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }
    //***METHODS***-----------------------------------------------------------------------------------------------------
    //***CREATE PROFILE***---------------------------------------------------------------------------------------------C
    public void addProfile(Profile profile ){
        profileRepository.addProfile(profile);
    }

    //***READ PROFILE(S)***--------------------------------------------------------------------------------------------R
    public List<Profile> getAllProfiles(){
        return profileRepository.getAllProfiles();
    }

    public Profile getProfileById(int id) throws ProfileException, SQLException {
        return profileRepository.getProfileById(id);
    }

    public List<Project> getProjectsFromProfile(int profileId) throws ProfileException {
        return profileRepository.getProjectsFromProfile(profileId);
    }

    //***UPDATE PROFILE***---------------------------------------------------------------------------------------------U
    public void updateProfile(Profile profile){
        profileRepository.updateProfile(profile);
    }

    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D
    public void deleteProfile(int id) throws ProfileException {
        profileRepository.deleteProfile(id);
    }
    //**END***----------------------------------------------------------------------------------------------------------
}
