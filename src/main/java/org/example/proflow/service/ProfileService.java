package org.example.proflow.service;

import org.example.proflow.model.Profile;
import org.example.proflow.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    ProfileRepository profileRepository;

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

    //***UPDATE PROFILE***---------------------------------------------------------------------------------------------U
    public void updateProfile(Profile profile){
        profileRepository.updateProfile(profile);
    }

    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D
    public void deleteProfile(int id)  {
        profileRepository.deleteProfile(id);
    }
    //**END***----------------------------------------------------------------------------------------------------------
}
