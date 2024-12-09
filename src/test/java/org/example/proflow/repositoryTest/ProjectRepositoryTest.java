package org.example.proflow.repositoryTest;

import org.example.proflow.ProFlowApplication;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.Status;
import org.example.proflow.repository.ProfileRepository;
import org.example.proflow.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // Når man tilføjer denne annotering på testklassen eller metoden, betyder det, at testen kører inden for en transaktion. Denne transaktion sikrer, at alle databaseoperationer i testen håndteres samlet, som om de er en del af en enkelt transaktion.
//@Rollback(true) // Ruller tilbage efter testen / skal bruges på databasen / ikke h2
@ActiveProfiles("h2")
public class ProjectRepositoryTest {

    //***ACCESS ATTRIBUTE(S)***-----------------------------------------------------------------------------------------
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProfileRepository profileRepository;

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------

    //***INTEGRATION-TEST METHODS***------------------------------------------------------------------------------------
    //***READ PROJECT METHODS***----------------------------------------------------------------------------------------
    @Test
    void addProjectTest() throws SQLException {
        // ARRANGE
        // Create and save a Profile to satisfy the foreign key constraint
        Profile profile = new Profile();
        profile.setId(1); // Set this to match the profile_id in the Project
        profile.setFirstName("Test name");
        profile.setLastName("Testsen name ");
        profile.setEmail("test@test.dk");
        profile.setPassword("test");
        profileRepository.addProfile(profile);


        Project expectedProject = new Project();
        expectedProject.setName("test");
        expectedProject.setDescription("test beskrivelse");
        expectedProject.setCreatedDate(LocalDate.now());
        expectedProject.setStartDate(LocalDate.of(2024, 1, 1));
        expectedProject.setEndDate(LocalDate.of(2024, 12, 31));
        expectedProject.setTotalEstHours(10);
        expectedProject.setStatus(Status.ACTIVE);
        expectedProject.setBudget(2000);
        expectedProject.setActualPrice(1000);
        expectedProject.setProfileId(1); // Link to the created Profile

        // ACT
        projectRepository.addProject(expectedProject);
        Project actualProject = projectRepository.getProjectById(expectedProject.getId());

        // ASSERT
        assertNotNull(actualProject, "Retrieved project should not be null");
        assertEquals(expectedProject.getName(), actualProject.getName());
        assertEquals(expectedProject.getDescription(), actualProject.getDescription());
        assertEquals(expectedProject.getId(), actualProject.getId());

    }

    @Test
    void getProjectById(){
    }

    @Test
    void getAllProjects() throws SQLException{
        // ARRANGE
        Profile profile = new Profile();
        profile.setFirstName("Test");
        profile.setLastName("User");
        profile.setEmail("test@example.com");
        profile.setPassword("password");
        profileRepository.addProfile(profile);

        Project project1 = new Project();
        project1.setName("Project One");
        project1.setDescription("First Test Project");
        project1.setCreatedDate(LocalDate.now());
        project1.setStartDate(LocalDate.of(2024, 1, 1));
        project1.setEndDate(LocalDate.of(2024, 6, 30));
        project1.setTotalEstHours(50);
        project1.setStatus(Status.ACTIVE);
        project1.setBudget(2000.0);
        project1.setActualPrice(1000.0);
        project1.setProfileId(profile.getId());

        Project project2 = new Project();
        project2.setName("Project Two");
        project2.setDescription("Second Test Project");
        project2.setCreatedDate(LocalDate.now());
        project2.setStartDate(LocalDate.of(2024, 7, 1));
        project2.setEndDate(LocalDate.of(2024, 12, 31));
        project2.setTotalEstHours(80);
        project2.setStatus(Status.INACTIVE);
        project2.setBudget(3000.0);
        project2.setActualPrice(1500.0);
        project2.setProfileId(profile.getId());

        // Add projects to the repository
        projectRepository.addProject(project1);
        projectRepository.addProject(project2);

        // ACT
        List<Project> projects = projectRepository.getAllProjects();

        // ASSERT
        assertNotNull(projects, "The list of projects should not be null");
        assertTrue(projects.size() >= 2, "The number of projects should be at least 2");

    }

//    private int id;
//    private String name;
//    private String description;
//    private LocalDate createdDate;
//    private LocalDate startDate;
//    private LocalDate endDate;
//    protected double totalEstHours;
//    private Status status;
//    private double budget;
//    protected double actualPrice;

    //***END***---------------------------------------------------------------------------------------------------------
}
