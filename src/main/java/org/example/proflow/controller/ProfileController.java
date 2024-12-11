package org.example.proflow.controller;

import jakarta.servlet.http.HttpSession;
import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.service.ProfileService;
import org.example.proflow.service.ProjectService;
import org.example.proflow.util.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.color.ProfileDataException;
import java.sql.SQLException;
import java.util.List;

//TODO ProfileController: Rette endpoints og HTML sider
//TODO ProfileController: Rette exceptions til ProfileException

@Controller
@RequestMapping("homepage")
public class ProfileController {
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final ProfileService profileService;
    private final ProjectService projectService;
    private HttpSession session; // why not assigned?

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public ProfileController(ProfileService profileService, ProjectService projectService) {
        this.profileService = profileService;
        this.projectService = projectService;
    }

    //***LOGIN METHODS***-----------------------------------------------------------------------------------------------
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("profileEmail") String profileEmail,
                        @RequestParam("profilePassword") String profilePassword,
                        HttpSession session, Model model) throws ProfileException {

        if (profileService.login(profileEmail, profilePassword)) {
            Profile profileToCheck = profileService.getProfileByEmailAndPassword(profileEmail, profilePassword);
            session.setAttribute("profile", profileToCheck);
            session.setMaxInactiveInterval(300);
            return "redirect:/homepage/userProfile";
        }
        //wrong credentials
        model.addAttribute("wrongCredentials", true);
        return "login"; //TODO: HTML skal returnere noget ala "Forkerte brugeroplysninger, prøv igen"
    }

    @GetMapping("/profile")
    public String showProfileDashboard(HttpSession session, Model model) throws ProfileException {
        Profile profile = (Profile) session.getAttribute("profile");

        if (profile == null) {
            return "redirect:/login";
        }

        model.addAttribute("profile", profile);
        List<Project> projects = profileService.getProjectsFromProfile(profile.getId());
        model.addAttribute("projects", projects);
        return "profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/homepage";
    }

    //***CREATE PROFILE***---------------------------------------------------------------------------------------------C
    @GetMapping("/add-profile") // GetMapping henter data fra database
    public String addProfile(Model model) {
        Profile profile = new Profile();
        model.addAttribute("profile", profile);
        return "signup"; //TODO har vi denne eller skal den bare hedde signup?
    }

    @PostMapping("/save-profile") //PostMapping tilføjer data til database
    public String saveProfile(@ModelAttribute Profile profile) throws ProfileDataException {
        profileService.addProfile(profile);
        return "redirect:/dashboard";
    }

    //***READ PROFILE***-----------------------------------------------------------------------------------------------R
    //***PROFILE(PM)***
    @GetMapping("/dashboard") //dashboard til projektleder (viser alle projekter for en projektleder)
    public String dashboard(Model model, @RequestParam int profileId) throws ProfileException {
        if (!Validator.isValid(session, profileId)) {
            return "redirect:/homepage";
        }
        List<Project> projectsFromProfile = profileService.getProjectsFromProfile(profileId);
        model.addAttribute("projectsFromProfile", projectsFromProfile);
        return "dashboard";
    }

    //***PROFILE ADMIN)***
    @GetMapping("/admin-dashboard")
    public String getAllProfiles(Model model) throws ProfileException { //admin kan se alle profiler
        List<Profile> profiles = profileService.getAllProfiles();
        model.addAttribute("profiles", profiles);
        return "admin_dashboard";
    }

    @GetMapping("/admin-dashboard/profiles")
    //TODO argumentere for hvorfor den her ligger her i profileController, mens getAllSubProjects ligger i taskSubProjects og getAllTasks ligger i taskController
    public String getAllProjects(Model model) throws SQLException { //til admin til at se liste over alle projekter
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("Projects", projects);
        return "admin_dashboard";
    }

    //***UPDATE PROFILE***---------------------------------------------------------------------------------------------U
    @GetMapping("/edit/{profileId}")
    public String editProfile(@PathVariable("profileId") int profileId, Model model, HttpSession session)
            throws ProfileException, SQLException {
        if (!Validator.isValid(session, profileId)) {
            return "redirect:/homepage";
        }
        Profile profile = profileService.getProfileById(profileId);
        model.addAttribute("profile", profile);
//        model.addAttribute("profileName", profile.getFirstName());
//        model.addAttribute("profileLastName", profile.getLastName());
//        model.addAttribute("profileEmail", profile.getEmail());
//        model.addAttribute("profilePassword",profile.getPassword());
        return "edit_profile";
    }

    @PostMapping("/update/{profileId}")
    public String updateProfile(@PathVariable("profileId") int profileId, @ModelAttribute Profile profile, Model model)
            throws ProfileException {
        if (!Validator.isValid(session, profileId)) {
            return "redirect:/homepage";
        }
        model.addAttribute("profile", profile);
        profileService.updateProfile(profile);
        return "redirect:/profile";
    }

    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D
    @PostMapping("/remove/{profileId}")
    public String deleteProfile(@PathVariable int profileId) throws ProfileException, SQLException {
        if (!Validator.isValid(session, profileId)) {
            return "redirect:/homepage";
        }
        Profile profile = profileService.getProfileById(profileId);
        profileService.deleteProfile(profile.getId());
        return "redirect:/profile";
    }

    //***EXCEPTION HANDLING***------------------------------------------------------------------------------------------
    @ExceptionHandler(ProfileException.class)
    public String handleError(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage());
        return "error";
    }

    //***END***---------------------------------------------------------------------------------------------------------

}
