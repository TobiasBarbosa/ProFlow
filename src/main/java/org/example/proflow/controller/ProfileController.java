package org.example.proflow.controller;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.service.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.awt.color.ProfileDataException;

//TODO ProfileController: Rette HTML sider
//TODO ProfileController: Rette navne/stier på endpoints

@Controller
@RequestMapping("profile")
public class ProfileController {
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final ProfileService profileService;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    //***CREATE PROFILE***---------------------------------------------------------------------------------------------C
    @GetMapping("/addprofile") // GetMapping henter data fra database
    public String addProfile(Model model) {
        Profile profile = new Profile();
        model.addAttribute("profile", profile);
        return "signup";  // TODO tilføj navn på html page
    }

    @PostMapping("/saveprofile") //PostMapping tilføjer data til database
    public String saveProfile(@ModelAttribute Profile profile) throws ProfileDataException {
        profileService.addProfile(profile);
        return "redirect:/"; // TODO tilføj navn på html page
    }

    //***READ PROFILE***-----------------------------------------------------------------------------------------------R
    //TODO Ved ikke om vi skal bruge den her?
    @GetMapping("")
    public String getAllProfiles(Model model) throws ProfileException {
        List<Profile> profiles = profileService.getAllProfiles();
        model.addAttribute("profiles", profiles);
        return "allProfiles"; // TODO tilføj navn på html page
    }

    //***UPDATE PROFILE***---------------------------------------------------------------------------------------------U
    @GetMapping("/edit/{id}")
    public String editProfile(@PathVariable("id") int profileId, Model model) throws ProfileException {
        Profile profile = profileService.getProfileById(profileId);
        model.addAttribute("profile", profile);
        model.addAttribute("profileName", profile.getFirstName());
        model.addAttribute("profileLastName", profile.getLastName());
        model.addAttribute("profileEmail", profile.getEmail());
        model.addAttribute("profilePassword",profile.getPassword());
        return "editProfile"; //TODO tilføj nav på html page
    }

    @PostMapping("/update/{id}")
    public String updateProfile(@PathVariable("id") int profileId, @ModelAttribute Profile profile) throws ProfileException {
//        model.addAttribute("profile", profile);
        profile.setId(profileId);
        profileService.updateProfile(profile);
        return "redirect:/profile"; // TODO tilføj navn på html page
    }

    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D
    @PostMapping("/{name}/remove")
    public String deleteProfile(@PathVariable int profileId) throws ProfileException {
        Profile profile = profileService.getProfileById(profileId);
        profileService.deleteProfile(profile);
        return "redirect:/profile"; // TODO: Change to the correct HTML page if necessary
    }

    //***EXCEPTION HANDLING***------------------------------------------------------------------------------------------
    @ExceptionHandler(ProfileException.class)
    public String handleError(Model model, Exception exception) {
        model.addAttribute("message",exception.getMessage());
        return "errorPage";
    }

    //***END***---------------------------------------------------------------------------------------------------------

}
