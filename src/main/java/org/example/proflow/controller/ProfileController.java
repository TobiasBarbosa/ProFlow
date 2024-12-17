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
@RequestMapping("")
public class ProfileController {
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private final ProfileService profileService;
    private final ProjectService projectService;
//    private HttpSession session; // why not assigned?

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public ProfileController(ProfileService profileService, ProjectService projectService) {
        this.profileService = profileService;
        this.projectService = projectService;
    }

    //***GetMapping***-----------------------------------------------------------------------------------------------
    @GetMapping("")
    public String homepage(){
        return "homepage";
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
            return "redirect:/dashboard";
        }
        //wrong credentials
        model.addAttribute("wrongCredentials", true);
        return "redirect:/"; //TODO: HTML skal returnere noget ala "Forkerte brugeroplysninger, prøv igen"
    }

//    @GetMapping("/dashboard")
//    public String showProfileDashboard(HttpSession session, Model model) throws ProfileException {
//        Profile profile = (Profile) session.getAttribute("profile");
//
//        if (profile == null) {
//            return "redirect:/login";
//        }
//
//        model.addAttribute("profile", profile);
//        List<Project> projects = profileService.getProjectsFromProfile(profile.getId());
//        model.addAttribute("projects", projects);
//        return "dashboard";
//    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //***CREATE PROFILE***---------------------------------------------------------------------------------------------C
    @GetMapping("/signup") // GetMapping henter data fra database
    public String addProfile(Model model) {
        Profile profile = new Profile();
        model.addAttribute("profile", profile);
        return "signup";
    }

    @PostMapping("/save-profile")
    public String saveProfile(@ModelAttribute Profile profile, HttpSession session) throws ProfileDataException {
        // Tilføjer profilen til databasen
        profileService.addProfile(profile);

        // Log den nyoprettede profil ind ved at bruge det eksisterende profile-objekt
        session.setAttribute("profile", profile);

        session.setMaxInactiveInterval(300);

        return "redirect:/dashboard";
    }


//    @PostMapping("/save-profile") //PostMapping tilføjer data til database
//    public String saveProfile(@ModelAttribute Profile profile) throws ProfileDataException {
//        profileService.addProfile(profile);
//        return "redirect:/dashboard";
//    }

    //***READ PROFILE***-----------------------------------------------------------------------------------------------R
//    ***PROFILE(PM)***
    @GetMapping("/dashboard") //dashboard til projektleder (viser alle projekter for en projektleder)
    public String dashboard(HttpSession session, Model model) throws ProfileException, SQLException {
        Profile profile = (Profile) session.getAttribute("profile");
        int profileId = profile.getId();
        if (!Validator.isValid(session, profileId)) {
            return "redirect:/";
        }

        List<Project> projectsFromProfile =profile.getProjects();
        model.addAttribute("projectsFromProfile", projectsFromProfile);
        model.addAttribute("profile", profile);
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
            return "redirect:/";
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
    public String updateProfile(@PathVariable("profileId") int profileId, @ModelAttribute Profile profile, Model model, HttpSession session)
            throws ProfileException, SQLException {
        if (!Validator.isValid(session, profileId)) {
            return "redirect:/";
        }
//        model.addAttribute("profile", profile);

        Profile loggedInProfile = profileService.getProfileById(profileId);

        // Behold det gamle password, hvis feltet er tomt
        if (profile.getPassword() == null || profile.getPassword().isEmpty()) {
            profile.setPassword(loggedInProfile.getPassword());
        }

        profile.setId(profileId);
        profileService.updateProfile(profile);
        return "redirect:/dashboard";
    }

    //***DELETE PROFILE***---------------------------------------------------------------------------------------------D
    @PostMapping("/remove/{profileId}")
    public String deleteProfile(@PathVariable int profileId, HttpSession session) throws ProfileException, SQLException {
        if (!Validator.isValid(session, profileId)) {
            return "redirect:/";
        }
        Profile profile = profileService.getProfileById(profileId);
        profileService.deleteProfile(profile.getId());
        return "redirect:/";
    }

    //***EXCEPTION HANDLING***------------------------------------------------------------------------------------------
    @ExceptionHandler(ProfileException.class)
    public String handleError(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage());
        return "error";
    }

    //***END***---------------------------------------------------------------------------------------------------------

}
