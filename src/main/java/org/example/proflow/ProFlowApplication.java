package org.example.proflow;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.SubProject;
import org.example.proflow.model.Task;
import org.example.proflow.repository.ProfileRepository;
import org.example.proflow.repository.ProjectRepository;
import org.example.proflow.repository.SubProjectRepository;
import org.example.proflow.repository.TaskRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.util.List;


@SpringBootApplication
public class ProFlowApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(ProFlowApplication.class, args);


//        ProfileRepository profileRepository = new ProfileRepository();
//        List<Profile> allProfiles = profileRepository.getAllProfiles();
//        for (Profile profile : allProfiles) {
//            System.out.println(profile);
//            System.out.println(profile.getProjects());
//        }

//        try {
//            Profile profile = profileRepository.getProfileById(1);
//            System.out.println(profile);
//            System.out.println(profile.getProjects());
//        } catch (ProfileException e) {
//            throw new RuntimeException(e);
//        }




        SubProjectRepository subProjectRepository = new SubProjectRepository();
        List<SubProject> allSubProjects = subProjectRepository.getAllSubProjects();
        //SubProject subProject = subProjectRepository.getSubProjectById(3);

//        ProjectRepository projectRepository = new ProjectRepository();
//        List<Project> allProjects = projectRepository.getAllProjects();
//        Project project = projectRepository.getProjectById(1);
//
//        System.out.println(project);
//        System.out.println(project.getSubProjects());

//        for (Project project : allProjects) {
//            System.out.println(project);
//            System.out.println("this project has sub projects: "+project.getSubProjects().size());
//            System.out.println(project.getSubProjects());
//        }

//        System.out.println(subProject);
//        System.out.println(subProject.getTasks());

        for (SubProject subProject : allSubProjects) {

            System.out.println(subProject.getName());
            System.out.println("SubProject ID: "+ subProject.getId());
            System.out.println(subProject.getDescription());
            System.out.println("This subProject has : ");
            System.out.println(subProject.getTasks());
            System.out.println("Tasks");
        }

//        TaskRepository taskRepository = new TaskRepository();
//        List<Task> alltasks = taskRepository.getAllTasks();
//
//        for (Task task : alltasks) {
//            System.out.println(task);
//        }
    }

}



