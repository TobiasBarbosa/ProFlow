package org.example.proflow.repositoryTest;

import org.example.proflow.ProFlowApplication;
import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.Status;
import org.example.proflow.model.SubProject;
import org.example.proflow.repository.ProfileRepository;
import org.example.proflow.repository.ProjectRepository;
import org.example.proflow.util.interfaces.ProfileRepositoryInterface;
import org.example.proflow.util.interfaces.ProjectRepositoryInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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
    // Dependencies injected af SpringBoot
    @Autowired
    private ProjectRepositoryInterface projectRepository;
    @Autowired
    private ProfileRepositoryInterface profileRepository;

    //***OBJECT(S) ATTRIBUTES***----------------------------------------------------------------------------------------
    private Profile profile;
    private Project project;

    //***TEST HELP METHODS***-------------------------------------------------------------------------------------------
    @BeforeEach
    //Kører før hver test og sætter en profil og et project op
    void setUp(){
        profile = new Profile();
        profile.setFirstName("test profile firstname");
        profile.setLastName("test profile lastname");
        profile.setEmail("test-profile-email@test.com");
        profile.setPassword("testProfilePassword");

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
    }

    @AfterEach
    // Kører efter hver test to clear the database
    void clearDatabase(){
        projectRepository.deleteAllProjects();
        profileRepository.deleteAllProfiles();
    }

    //***INTEGRATION-TEST METHODS***------------------------------------------------------------------------------------
    //***CREATE PROJECT METHODS***-------------------------------------------------------------------------------------C
    @Test
    void addProjectTest() throws SQLException, ProfileException {
        // ARRANGE
        profileRepository.addProfile(profile);

        project.setProfileId(profile.getId()); // Link to the created Profile

        // ACT
        projectRepository.addProject(project);
        Project actualProject = projectRepository.getProjectById(project.getId());

        // ASSERT
        assertNotNull(actualProject, "Retrieved project should not be null");
        assertEquals(project.getName(), actualProject.getName());
        assertEquals(project.getDescription(), actualProject.getDescription());
        assertEquals(project.getId(), actualProject.getId());
    }

    //***READ PROJECT(S) METHODS***------------------------------------------------------------------------------------R
    @Test
    void getAllProjects() throws SQLException, ProfileException{
        //ARRANGE
        profileRepository.addProfile(profile);
        project.setProfileId(profile.getId());

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
        projectRepository.addProject(project);
        projectRepository.addProject(project2);

        //ACT
        List<Project> projects = projectRepository.getAllProjects();

        //ASSERT
        assertNotNull(projects, "The list of projects should not be null");
        assertTrue(projects.size() >= 2, "The number of projects should be at least 2");

    }

    //***UPDATE PROJECT***---------------------------------------------------------------------------------------------U
    @Test
    public void updateProject() throws SQLException, ProfileException {
        //ARRANGE
        profileRepository.addProfile(profile);

        project.setProfileId(profile.getId());
        projectRepository.addProject(project);

        //Modify update changes
        project.setName("Updated Project");
        project.setDescription("Updated Description");
        project.setEndDate(LocalDate.of(2024, 11, 30));
        project.setBudget(5500.0);

        //ACT
        projectRepository.updateProject(project);
        Project updatedProject = projectRepository.getProjectById(project.getId());

        //ASSERT
        assertEquals("Updated Project", updatedProject.getName());
        assertEquals("Updated Description", updatedProject.getDescription());
        assertEquals(LocalDate.of(2024, 11, 30), updatedProject.getEndDate());
        assertEquals(5500.0, updatedProject.getBudget());

    }

    //***DELETE PROJECT METHOD***--------------------------------------------------------------------------------------D
    @Test
    public void deleteProjectTest() throws SQLException, ProfileException{
        // ARRANGE
        profileRepository.addProfile(profile);

        project.setProfileId(profile.getId());
        projectRepository.addProject(project);

        //ASSERT
        assertTrue(project.getId() > 0); // ensure project exist

        // ACT: Delete the Project
        projectRepository.deleteProject(project.getId());

        // ASSERT: Verify the project no longer exists
        Project deletedProject = projectRepository.getProjectById(project.getId());
        assertNull(deletedProject); // Assert that the project has been deleted
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
