package org.example.proflow.controller;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.service.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.color.ProfileDataException;
import java.sql.SQLException;
import java.util.List;

//TODO ProfileController: Rette HTML sider
//TODO ProfileController: Rette navne/stier på endpoints
//TODO ProfileController: Rette exceptions til ProfileException

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
        return "signup";
    }

    @PostMapping("/saveprofile") //PostMapping tilføjer data til database
    public String saveProfile(@ModelAttribute Profile profile) throws ProfileDataException {
        profileService.addProfile(profile);
        return "redirect:/";
    }

    //***READ PROFILE***-----------------------------------------------------------------------------------------------R
    @GetMapping("/admin") //homepage til projektleder (viser alle projekter for en projektleder)
    public String homepage(Model model, @RequestParam int profileId) throws ProfileException {
        List<Project> projectsFromProfile = profileService.getProjectsFromProfile(profileId);
        model.addAttribute("projectsFromProfile", projectsFromProfile);
        return "homepage";
    }

    @GetMapping("")
    public String getAllProfiles(Model model) throws ProfileException {
        List<Profile> profiles = profileService.getAllProfiles();
        model.addAttribute("profiles", profiles);
        return "allProfiles";
    }

    //***UPDATE PROFILE***---------------------------------------------------------------------------------------------U
    @GetMapping("/edit/{profileId}")
    public String editProfile(@PathVariable("profileId") int profileId, Model model) throws ProfileException, SQLException {
        Profile profile = profileService.getProfileById(profileId);
        model.addAttribute("profile", profile);
        model.addAttribute("profileName", profile.getFirstName());
        model.addAttribute("profileLastName", profile.getLastName());
        model.addAttribute("profileEmail", profile.getEmail());
        model.addAttribute("profilePassword",profile.getPassword());
        return "editProfile";
    }

    @PostMapping("/update/{id}")
    public String updateProfile(@PathVariable("id") int profileId, @ModelAttribute Profile profile, Model model) throws ProfileException {
        model.addAttribute("profile", profile);
        profile.setId(profileId);
        profileService.updateProfile(profile);
        return "redirect:/profile";
    }

    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D
    @PostMapping("/{name}/remove")
    public String deleteProfile(@PathVariable int profileId) throws ProfileException {
        Profile profile = profileService.getProfileById(profileId);
        profileService.deleteProfile(profile.getId());
        return "redirect:/profile";
    }

    //***EXCEPTION HANDLING***------------------------------------------------------------------------------------------
    @ExceptionHandler(ProfileException.class)
    public String handleError(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage());
        return "errorPage";
    }

    //***END***---------------------------------------------------------------------------------------------------------

}
