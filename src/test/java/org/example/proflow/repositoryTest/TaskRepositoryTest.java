package org.example.proflow.repositoryTest;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.*;
import org.example.proflow.util.interfaces.ProfileRepositoryInterface;
import org.example.proflow.util.interfaces.ProjectRepositoryInterface;
import org.example.proflow.util.interfaces.SubProjectRepositoryInterface;
import org.example.proflow.util.interfaces.TaskRepositoryInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //forklar lige hvorfor
//@Rollback(true) // Ruller tilbage efter testen / skal bruges på databasen / ikke h2
@ActiveProfiles("h2") // skal vi teste flere profiler?
//TODO @clear-metoder i tearDown skal skiftes ud med @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:h2.sql")
//@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:h2.sql")
public class TaskRepositoryTest {

    //***ACCESS ATTRIBUTES***-------------------------------------------------------------------------------------------
    @Autowired //  bruges @Autowired-annotationen til automatisk at injicere afhængigheder i din applikation. Dette er en nøglefunktion inden for Spring Framework, som hjælper med at håndtere afhængighedsinjektion (DI) på en nem og effektiv måde.
    private TaskRepositoryInterface taskRepository;
    @Autowired
    private SubProjectRepositoryInterface subProjectRepository;
    @Autowired
    private ProjectRepositoryInterface projectRepository;
    @Autowired
    private ProfileRepositoryInterface profileRepository;

    //***OBJECT(S) ATTRIBUTES***----------------------------------------------------------------------------------------
    private Profile profile;
    private Project project;
    private SubProject subProject;
    private Task task;

    //***TEST HELP METHODS***-------------------------------------------------------------------------------------------
    @BeforeEach
    //Kører før hver test og sætter en profil, et project, et subproject og en task op
    void setUp(){
        //arrange
        profile = new Profile();
        profile.setFirstName("test profile firstname");
        profile.setLastName("test profile lastname");
        profile.setEmail("test-profile-email@test.com");
        profile.setPassword("testProfilePassword12@");

        project = new Project();
        project.setName("test project name");
        project.setDescription("test project description");
        project.setCreatedDate(LocalDate.now());
        project.setStartDate(LocalDate.of(2024, 1, 1));
        project.setEndDate(LocalDate.of(2024, 12, 31));
        project.setTotalEstHours(10);
        project.setStatus(Status.ACTIVE);
        project.setBudget(2000);
        project.setActualPrice(1000);

        subProject = new SubProject();
        subProject.setName("test subproject name");
        subProject.setDescription("tests subproject description ");
        subProject.setCreatedDate(LocalDate.now());
        subProject.setStartDate(LocalDate.of(2024, 1, 1));
        subProject.setEndDate(LocalDate.of(2024, 12, 31));
        subProject.setTotalEstHours(10);
        subProject.setStatus(Status.ACTIVE);
        subProject.setBudget(2000);
        subProject.setActualPrice(1000);
        subProject.setAssignedTo("test employee");

        task = new Task();
        task.setName("test task name");
        task.setDescription("tests task description ");
        task.setLocation("test location");
        task.setCreatedDate(LocalDate.now());
        task.setStartDate(LocalDate.of(2024, 1, 1));
        task.setEndDate(LocalDate.of(2024, 12, 31));
        task.setTotalEstHours(10);
        task.setStatus(Status.ACTIVE);
        task.setAssignedTo("test employee");
        task.setActualPrice(1000);
    }

    @AfterEach
    void tearDown() {
        // Kører efter hver test to clear the database
        taskRepository.clearTasksForTesting();
        subProjectRepository.clearSubProjectsForTesting();
        projectRepository.clearProjectsForTesting();
        profileRepository.clearProfilesForTesting();
    }

    //***CREATE TASK***------------------------------------------------------------------------------------------------C
    @Test
    void addTaskTest() throws SQLException, ProfileException {
        //ARRANGE
        //Profile
        profileRepository.addProfile(profile);
        //Project
        project.setProfileId(profile.getId()); // Reference the profile you just added
        projectRepository.addProject(project);
        //SubProject
        subProject.setProjectId(project.getId());
        subProjectRepository.addSubProject(subProject);
        //Task
        task.setSubProjectId(subProject.getProjectId());

        //ACT
        taskRepository.addTask(task);
        Task actualTask = taskRepository.getTaskById(task.getId());

        //ASSERT
        assertNotNull(actualTask, "Retrieved task should not be null");
        assertEquals(task.getName(), actualTask.getName());
        assertEquals(task.getDescription(), actualTask.getDescription());
        assertEquals(task.getId(), actualTask.getId());

    }

    //***READ TASK***--------------------------------------------------------------------------------------------------R
    @Test
    void getAllTasksTest() throws SQLException, ProfileException {
        //ARRANGE
        profileRepository.addProfile(profile);

        project.setProfileId(profile.getId()); // Reference the profile you just added
        projectRepository.addProject(project);

        subProject.setProjectId(project.getId());
        subProjectRepository.addSubProject(subProject);

        task.setSubProjectId(subProject.getId());

        //create second task to test
        Task task2 = new Task();
        task2.setName("test2 task name");
        task2.setDescription("test2 task description");
        task2.setLocation("test2 task location");
        task2.setCreatedDate(LocalDate.now());
        task2.setStartDate(LocalDate.of(2024, 1, 1));
        task2.setEndDate(LocalDate.of(2024, 12, 31));
        task2.setTotalEstHours(10);
        task2.setStatus(Status.ACTIVE);
        task2.setAssignedTo("Test2 task assigned to");
        task2.setActualPrice(500);
        task2.setSubProjectId(subProject.getId());

        //Add created tasks
        taskRepository.addTask(task);
        taskRepository.addTask(task2);

        //ACT
        List<Task> tasks = taskRepository.getAllTasks();

        //ASSERT
        assertNotNull(tasks, "The list of tasks should not be null");
        assertTrue(tasks.size() >= 2, "The number of tasks should be at least two");
    }

    //***UPDATE TASK***------------------------------------------------------------------------------------------------U
    @Test
    void updateTaskTest() throws SQLException, ProfileException {
        //ARRANGE
        profileRepository.addProfile(profile);

        project.setProfileId(profile.getId()); // Reference the profile you just added
        projectRepository.addProject(project);

        subProject.setProjectId(project.getId());
        subProjectRepository.addSubProject(subProject);

        task.setSubProjectId(subProject.getId());
        taskRepository.addTask(task);

        //Changes to test if task is updated
        task.setName("updated task name");
        task.setDescription("updated task description");
        task.setLocation("updated task location");
        task.setActualPrice(700.0);

        //ACT
        taskRepository.updateTask(task);
        Task updatedTask = taskRepository.getTaskById(task.getId()); // vi assigner til ny variabel for at sikre at data vi sammenligner kommer fra db

        //ASSERT
        assertEquals("updated task name", updatedTask.getName());
        assertEquals("updated task description", updatedTask.getDescription());
        assertEquals("updated task location", updatedTask.getLocation());
        assertEquals(700, updatedTask.getActualPrice());

    }

    //***DELETE TASK***------------------------------------------------------------------------------------------------D
    @Test
    void deleteTaskTest() throws SQLException, ProfileException{
        //ARRANGE
        profileRepository.addProfile(profile);

        project.setProfileId(profile.getId()); // Reference the profile you just added
        projectRepository.addProject(project);

        subProject.setProjectId(project.getId());
        subProjectRepository.addSubProject(subProject);

        task.setSubProjectId(subProject.getId());
        taskRepository.addTask(task);

        //ACT
        assertTrue(task.getId() > 0); //Tjekker om der er en task
        taskRepository.deleteTask(task.getId());

        //ASSERT
        Task deletedTask = taskRepository.getTaskById(task.getId());
        assertNull(deletedTask); //Asserts that the project has been deleted
    }

    //***END***---------------------------------------------------------------------------------------------------------
}