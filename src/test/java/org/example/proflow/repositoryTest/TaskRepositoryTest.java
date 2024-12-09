package org.example.proflow.repositoryTest;

import org.example.proflow.exception.TaskException;
import org.example.proflow.model.*;
import org.example.proflow.repository.ProfileRepository;
import org.example.proflow.repository.ProjectRepository;
import org.example.proflow.repository.SubProjectRepository;
import org.example.proflow.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
//@Rollback(true)
@ActiveProfiles("h2")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private SubProjectRepository subProjectRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Test
    void addTaskTest() throws SQLException {
        //ARRANGE
        // Create and save a Project to satisfy the foreign key constraint
        // Insert a profile entry
        Profile profile = new Profile();
        profile.setId(1); // Set this to match the profile_id in the Project
        profile.setFirstName("Test name");
        profile.setLastName("Testsen name ");
        profile.setEmail("test@test.dk");
        profile.setPassword("test");
        profileRepository.addProfile(profile);


        SubProject subProject = new SubProject();
        //subProject.setId(1); // Set this to match the profile_id in the Project
        subProject.setName("Test name");
        subProject.setDescription("Tests description ");
        subProject.setCreatedDate(LocalDate.now());
        subProject.setStartDate(LocalDate.of(2024, 1, 1));
        subProject.setEndDate(LocalDate.of(2024, 12, 31));
        subProject.setTotalEstHours(10);
        subProject.setStatus(Status.ACTIVE);
        subProject.setBudget(2000);
        subProject.setActualPrice(1000);
        subProject.setProjectId(1);
        subProject.setAssignedTo("test employee");
        subProjectRepository.addSubProject(subProject);

        Task expectedTask = new Task();
        expectedTask.setName("test");
        expectedTask.setDescription("test beskrivelse");
        expectedTask.setLocation("test location");
        expectedTask.setCreatedDate(LocalDate.now());
        expectedTask.setStartDate(LocalDate.of(2024, 1, 1));
        expectedTask.setEndDate(LocalDate.of(2024, 12, 31));
        expectedTask.setTotalEstHours(10);
        expectedTask.setStatus(Status.ACTIVE);
        expectedTask.setSubProjectId(1);
        expectedTask.setAssignedTo("Test employee");
        expectedTask.setTaskPrice(500); //link to the created project

        //ACT
        taskRepository.addTask(expectedTask);
        Task actualTask = taskRepository.getTaskById(expectedTask.getId());

        //ASSERT
        assertNotNull(actualTask, "Retrieved task should not be null");
        assertEquals(expectedTask.getName(), actualTask.getName());
        assertEquals(expectedTask.getDescription(), actualTask.getDescription());
        assertEquals(expectedTask.getId(), actualTask.getId());



    }





}
