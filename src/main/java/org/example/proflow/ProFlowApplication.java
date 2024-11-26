package org.example.proflow;


import org.example.proflow.model.*;
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
        List<SubProject> allSubProjects = new ArrayList<SubProject>();
        ArrayList<Task> allTasks = new ArrayList<Task>();

        allProfiles = profiles.getAllProfiles();



    }

}




//test delete project

//int projectId = 4; // Assuming project with ID 1 exists
//
//        try {
//                projects.deleteProject(projectId);
//        } catch (SQLException e) {
//        throw new RuntimeException(e);
//        }
//
//                System.out.println("Project deleted successfully.");


//TEST ADD SUBPROJECT

//            SubProject subProject = new SubProject();
//            subProject.setName("Frontend Development");
//            subProject.setDescription("Building the user interface for the Pro Flow application.");
//            subProject.setStartDate(LocalDate.of(2024, 2, 1));
//            subProject.setEndDate(LocalDate.of(2024, 5, 31));
//            subProject.setStatus(Status.DONE);
//            subProject.setProjectId(1); // Assuming Project ID 1 exists
//
//        try {
//            subProjects.addSubProject(subProject);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("SubProject added successfully.");


//GET ALL SUBPROJECTS

//
//allSubProjects = subProjects.getAllSubProjects();
//
//            System.out.println("List of SubProjects:");
//            for (SubProject sp : allSubProjects) {
//        System.out.println("ID: " + sp.getId());
//        System.out.println("Name: " + sp.getName());
//        System.out.println("Description: " + sp.getDescription());
//        System.out.println("Start Date: " + sp.getStartDate());
//        System.out.println("End Date: " + sp.getEndDate());
//        System.out.println("Status: " + sp.getStatus());
//        System.out.println("Project ID: " + sp.getProjectId());
//        System.out.println("-------------------");
//            }



//GET SUBPROJECT BY ID

//            int subProjectId = 1; // Assuming SubProject with ID 1 exists
//        SubProject subProject = null;
//        try {
//            subProject = subProjects.getSubProjectById(subProjectId);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        if (subProject != null) {
//                System.out.println("SubProject Found:");
//                System.out.println("ID: " + subProject.getId());
//                System.out.println("Name: " + subProject.getName());
//                System.out.println("Description: " + subProject.getDescription());
//                System.out.println("Start Date: " + subProject.getStartDate());
//                System.out.println("End Date: " + subProject.getEndDate());
//                System.out.println("Status: " + subProject.getStatus());
//                System.out.println("Project ID: " + subProject.getProjectId());
//            } else {
//                System.out.println("SubProject with ID " + subProjectId + " not found.");
//            }


//UPDATE SUB PROJECT

//            int subProjectId = 2; // Assuming SubProject with ID 1 exists
//        SubProject subProject = null;
//        try {
//            subProject = subProjects.getSubProjectById(subProjectId);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        if (subProject != null) {
//                subProject.setName("Updated Frontend Development");
//                subProject.setDescription("Updated description for frontend.");
//                subProject.setEndDate(LocalDate.of(2024, 6, 30));
//                subProject.setStatus(Status.DONE);
//
//            try {
//                subProjects.updateSubProject(subProject);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//            System.out.println("SubProject updated successfully.");
//            } else {
//                System.out.println("SubProject with ID " + subProjectId + " not found.");
//            }



//DELETE SUB PROJECT

//            int subProjectId = 5; // Assuming SubProject with ID 1 exists
//
//        try {
//            subProjects.deleteSubProject(subProjectId);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("SubProject deleted successfully.");

//// Method to test add task
//Task task1 = new Task();
//        task1.setName("Design Logo");
//        task1.setDescription("Create a logo");
//        task1.setStartDate(LocalDate.of(2024, 1, 1));
//        task1.setEndDate(LocalDate.of(2024, 1, 5));
//        task1.setStatus(Status.DONE);
//        task1.setAssignedTo("John Doe");
//        task1.setSubProjectId(1);
//
//Task task2 = new Task();
//        task2.setName("Setup Database");
//        task2.setDescription("Initialize DB");
//        task2.setStartDate(LocalDate.of(2024, 1, 3));
//        task2.setEndDate(LocalDate.of(2024, 1, 10));
//        task2.setStatus(Status.DONE);
//        task2.setAssignedTo("Jane Doe");
//        task2.setSubProjectId(1);
//
//        try {
//                tasks.addTask(task1);
//        } catch (SQLException e) {
//        throw new RuntimeException(e);
//        }
//                try {
//                tasks.addTask(task2);
//        } catch (SQLException e) {
//        throw new RuntimeException(e);
//        }
//
//
//                System.out.println("Welcome to ProFlow!");
//        System.out.println(allProfiles);