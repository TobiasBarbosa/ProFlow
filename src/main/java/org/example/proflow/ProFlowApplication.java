package org.example.proflow;

import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.SubProject;
import org.example.proflow.model.Task;
import org.example.proflow.model.Status;
import org.example.proflow.repository.ProfileRepository;
import org.example.proflow.repository.ProjectRepository;
import org.example.proflow.repository.SubProjectRepository;
import org.example.proflow.repository.TaskRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ProFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProFlowApplication.class, args);

        var profiles = new ProfileRepository();
        var projects = new ProjectRepository();
        var subProjects = new SubProjectRepository();
        var tasks = new TaskRepository();


        List<Profile> allProfiles = new ArrayList<Profile>();
        ArrayList<Project> allProjects = new ArrayList<Project>();
        ArrayList<SubProject> allSubProjects = new ArrayList<SubProject>();
        ArrayList<Task> allTasks = new ArrayList<Task>();

        allProfiles = profiles.getAllProfiles();





        System.out.println("Welcome to ProFlow!");
        System.out.println(allProfiles);







    }

}





// to test add profile works
//            Profile profile = new Profile();
//            profile.setFirstName("John");
//            profile.setLastName("Doe");
//            profile.setEmail("john.doe@example.com");
//            profile.setPassword("password123");
//            profiles.addProfile(profile);
//            System.out.println("Profile added successfully.");



// To test update profile
// Fetch the first profile from the database to update
//
//            if (allProfiles.isEmpty()) {
//                System.out.println("No profiles available to update.");
//                return;
//            }
//            Profile profileToUpdate = allProfiles.get(0);
//            profileToUpdate.setFirstName("Jane");
//            profileToUpdate.setLastName("Smith");
//            profileToUpdate.setEmail("jane.smith@example.com");
//            profileToUpdate.setPassword("newpassword456");
//           profiles.updateProfile(profileToUpdate);
//            System.out.println("Profile updated successfully.");


//Delete Profile

// Fetch the first profile from the database to delete

//            if (allProfiles.isEmpty()) {
//                System.out.println("No profiles available to delete.");
//                return;
//            }
//
//            Profile profileToDelete = allProfiles.get(allProfiles.size() - 1);
//            profiles.deleteProfile(profileToDelete.getId());
//
//            System.out.println("Profile deleted successfully.");





// TEST UPDATE PROJECT METHOD

//            int projectId = 1; // Assuming project with ID 1 exists
//        Project project = null;
//        try {
//            project = projects.getProjectById(projectId);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        if (project != null) {
//                project.setName("Updated Pro Flow Website");
//                project.setDescription("Updated project description.");
//                project.setEndDate(LocalDate.of(2024, 12, 31));
//                project.setStatus(Status.ALMOST_DONE);
//
//            try {
//                projects.updateProject(project);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//            System.out.println("Project updated successfully.");
//            } else {
//                System.out.println("Project with ID " + projectId + " not found.");
//            }






// test add project

//            Project project = new Project();
//            project.setName("Pro Flow Website");
//            project.setDescription("Developing a project management web application.");
//            project.setStartDate(LocalDate.of(2024, 1, 1));
//            project.setEndDate(LocalDate.of(2024, 6, 30));
//            project.setStatus(Status.DOING);
//            project.setProfileId(1); // Assuming profile with ID 1 exists
//
//        try {
//            projects.addProject(project);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("Project added successfully.");


// TEST GET PROJECT BY ID

//            int projectId = 1; // Assuming project with ID 1 exists
//        Project project = null;
//        try {
//            project = projects.getProjectById(projectId);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        if (project != null) {
//                System.out.println("Project Found:");
//                System.out.println("ID: " + project.getId());
//                System.out.println("Name: " + project.getName());
//                System.out.println("Description: " + project.getDescription());
//                System.out.println("Start Date: " + project.getStartDate());
//                System.out.println("End Date: " + project.getEndDate());
//                System.out.println("Status: " + project.getStatus());
//                System.out.println("Profile ID: " + project.getProfileId());
//            } else {
//                System.out.println("Project with ID " + projectId + " not found.");
//            }