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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    private ProjectRepository projectRepository;
    @Autowired
    private ProfileRepository profileRepository;


    //TODO how to move create profile, project and subproject out
    @Test
    void addTaskTest() throws SQLException {
        //ARRANGE
        //Create profile
        Profile profile = new Profile();
        profile.setId(1); // Set this to match the profile_id in the Project
        profile.setFirstName("Test name");
        profile.setLastName("Testsen name ");
        profile.setEmail("test@test.dk");
        profile.setPassword("test");
        profileRepository.addProfile(profile);

        //Create project
        Project project = new Project();
        project.setName("test");
        project.setDescription("test beskrivelse");
        project.setCreatedDate(LocalDate.now());
        project.setStartDate(LocalDate.of(2024, 1, 1));
        project.setEndDate(LocalDate.of(2024, 12, 31));
        project.setTotalEstHours(10);
        project.setStatus(Status.ACTIVE);
        project.setBudget(2000);
        project.setActualPrice(1000);
        project.setProfileId(1); // Reference the profile you just added
        projectRepository.addProject(project);

        //Create subproject
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

        //Create expected task
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
        expectedTask.setTaskPrice(500);

        //ACT
        taskRepository.addTask(expectedTask);
        Task actualTask = taskRepository.getTaskById(expectedTask.getId());

        //ASSERT
        assertNotNull(actualTask, "Retrieved task should not be null");
        assertEquals(expectedTask.getName(), actualTask.getName());
        assertEquals(expectedTask.getDescription(), actualTask.getDescription());
        assertEquals(expectedTask.getId(), actualTask.getId());

    }

    @Test
    void getAllTasksTest() throws SQLException {
        //ARRANGE
        //create profile
        Profile profile = new Profile();
        profile.setFirstName("Test");
        profile.setLastName("User");
        profile.setEmail("test@example.com");
        profile.setPassword("password");
        profileRepository.addProfile(profile);

        //create project
        Project project = new Project();
        project.setName("test");
        project.setDescription("test beskrivelse");
        project.setCreatedDate(LocalDate.now());
        project.setStartDate(LocalDate.of(2024, 1, 1));
        project.setEndDate(LocalDate.of(2024, 12, 31));
        project.setTotalEstHours(10);
        project.setStatus(Status.ACTIVE);
        project.setBudget(2000);
        project.setActualPrice(1000);
        project.setProfileId(1); // Reference the profile you just added
        projectRepository.addProject(project);

        //create subproject
        SubProject subProject = new SubProject();
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

        //create first task to test
        Task task1 = new Task();
        task1.setName("test task 1");
        task1.setDescription("first test task");
        task1.setLocation("test location");
        task1.setCreatedDate(LocalDate.now());
        task1.setStartDate(LocalDate.of(2024, 1, 1));
        task1.setEndDate(LocalDate.of(2024, 12, 31));
        task1.setTotalEstHours(10);
        task1.setStatus(Status.ACTIVE);
        task1.setSubProjectId(1);
        task1.setAssignedTo("Test employee");
        task1.setTaskPrice(500);

        //create second task to test
        Task task2 = new Task();
        task2.setName("test task 2");
        task2.setDescription("second test task");
        task2.setLocation("test location");
        task2.setCreatedDate(LocalDate.now());
        task2.setStartDate(LocalDate.of(2024, 1, 1));
        task2.setEndDate(LocalDate.of(2024, 12, 31));
        task2.setTotalEstHours(10);
        task2.setStatus(Status.ACTIVE);
        task2.setSubProjectId(1);
        task2.setAssignedTo("Test employee");
        task2.setTaskPrice(500);

        //Add created tasks
        taskRepository.addTask(task1);
        taskRepository.addTask(task2);

        //ACT
        List<Task> tasks = taskRepository.getAllTasks();

        //ASSERT
        assertNotNull(tasks, "The list of tasks should not be null");
        assertTrue(tasks.size() >= 2, "The number of tasks should be at least two");
    }

    @Test
    void updateTaskTest() throws SQLException {
        //ARRANGE
        //Create profile
        Profile profile = new Profile();
        profile.setFirstName("Test");
        profile.setLastName("User");
        profile.setEmail("test@example.com");
        profile.setPassword("password");
        profileRepository.addProfile(profile);

        //create project
        Project project = new Project();
        project.setName("test");
        project.setDescription("test beskrivelse");
        project.setCreatedDate(LocalDate.now());
        project.setStartDate(LocalDate.of(2024, 1, 1));
        project.setEndDate(LocalDate.of(2024, 12, 31));
        project.setTotalEstHours(10);
        project.setStatus(Status.ACTIVE);
        project.setBudget(2000);
        project.setActualPrice(1000);
        project.setProfileId(1); // Reference the profile you just added
        projectRepository.addProject(project);

        //Create subproject
        SubProject subProject = new SubProject();
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

        //Create task to test the update method
        Task task = new Task();
        task.setName("test task");
        task.setDescription("description test task");
        task.setLocation("test location");
        task.setCreatedDate(LocalDate.now());
        task.setStartDate(LocalDate.of(2024, 1, 1));
        task.setEndDate(LocalDate.of(2024, 12, 31));
        task.setTotalEstHours(10);
        task.setStatus(Status.ACTIVE);
        task.setSubProjectId(1);
        task.setAssignedTo("Test employee");
        task.setTaskPrice(500);

        taskRepository.addTask(task);

        //Changes to test if task is updated
        task.setName("new name test task");
        task.setDescription("new description");
        task.setLocation("new location");
        task.setTaskPrice(700.0);

        //ACT
        taskRepository.updateTask(task);
        Task updatedTask = taskRepository.getTaskById(task.getId());

        //ASSERT
        assertEquals("new name test task", updatedTask.getName());
        assertEquals("new description", updatedTask.getDescription());
        assertEquals("new location", updatedTask.getLocation());
        assertEquals(700, updatedTask.getTaskPrice());


    }

    @Rollback(false)
    @Test
    void deleteTaskTest() throws SQLException{
        //ARRANGE
        //Create profile
        Profile profile = new Profile();
        profile.setFirstName("Test");
        profile.setLastName("User");
        profile.setEmail("test@example.com");
        profile.setPassword("password");
        profileRepository.addProfile(profile);

        //create project
        Project project = new Project();
        project.setName("test");
        project.setDescription("test beskrivelse");
        project.setCreatedDate(LocalDate.now());
        project.setStartDate(LocalDate.of(2024, 1, 1));
        project.setEndDate(LocalDate.of(2024, 12, 31));
        project.setTotalEstHours(10);
        project.setStatus(Status.ACTIVE);
        project.setBudget(2000);
        project.setActualPrice(1000);
        project.setProfileId(1); // Reference the profile you just added
        projectRepository.addProject(project);

        //Create subproject
        SubProject subProject = new SubProject();
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

        //Create task to test the delete method
        Task task = new Task();
        task.setName("test task");
        task.setDescription("description test task");
        task.setLocation("test location");
        task.setCreatedDate(LocalDate.now());
        task.setStartDate(LocalDate.of(2024, 1, 1));
        task.setEndDate(LocalDate.of(2024, 12, 31));
        task.setTotalEstHours(10);
        task.setStatus(Status.ACTIVE);
        task.setSubProjectId(1);
        task.setAssignedTo("Test employee");
        task.setTaskPrice(500);
        taskRepository.addTask(task);

        //ACT
        assertTrue(task.getId() > 0); //Tjekker om der er en task
        taskRepository.deleteTask(task.getId());

        //ASSERT
        Task deletedTask = taskRepository.getTaskById(task.getId());
        assertNull(deletedTask); //Asserts that the project has been deleted

    }





}