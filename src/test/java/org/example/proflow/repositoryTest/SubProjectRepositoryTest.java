package org.example.proflow.repositoryTest;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.Status;
import org.example.proflow.model.SubProject;
import org.example.proflow.repository.ProfileRepository;
import org.example.proflow.repository.ProjectRepository;
import org.example.proflow.repository.SubProjectRepository;
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
// Når man tilføjer denne annotering på testklassen eller metoden, betyder det, at testen kører inden for en transaktion. Denne transaktion sikrer, at alle databaseoperationer i testen håndteres samlet, som om de er en del af en enkelt transaktion.
//@Rollback(true) // Ruller tilbage efter testen / skal bruges på databasen / ikke h2
@ActiveProfiles("h2")
public class SubProjectRepositoryTest {

    //***ACCESS ATTRIBUTES***-------------------------------------------------------------------------------------------
    @Autowired
    private SubProjectRepository subProjectRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    ProfileRepository profileRepository;

    //***TEST HELP METHODS***-------------------------------------------------------------------------------------------
    private Profile preSetProfile() throws ProfileException {
        Profile profile = new Profile();
        profile.setFirstName("test profile firstname");
        profile.setLastName("test profile lastname");
        profile.setEmail("test-profile-email@test.com");
        profile.setPassword("testProfilePassword");
        return profile;
    }

    private Project preSetProject() {
        Project project = new Project();
        project.setName("test project name");
        project.setDescription("test project description");
        project.setCreatedDate(LocalDate.now());
        project.setStartDate(LocalDate.of(2024, 1, 1));
        project.setEndDate(LocalDate.of(2024, 12, 31));
        project.setTotalEstHours(10);
        project.setStatus(Status.ACTIVE);
        project.setBudget(2000);
        project.setActualPrice(1000);
        return project;
    }
    private SubProject preSetSubProject(){
    SubProject subProject = new SubProject();
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
        return subProject;
    }

    //***SUBPROJECT METHODS***------------------------------------------------------------------------------------------
    //***CREATE SUBPROJECT***------------------------------------------------------------------------------------------C
    @Test
    void addSubProject() throws SQLException, ProfileException{
        //Create Profile
        Profile profile = preSetProfile();
        profileRepository.addProfile(profile);

        //Create Project
        Project project = preSetProject();
        project.setProfileId(profile.getId()); // Reference the profile you just added
        projectRepository.addProject(project);

        // Add and retrieve the SubProject
        SubProject expectedSubProject = preSetSubProject();
        expectedSubProject.setProjectId(project.getId());

        subProjectRepository.addSubProject(expectedSubProject);
        SubProject actualSubProject = subProjectRepository.getSubProjectById(expectedSubProject.getProjectId());

        assertEquals(expectedSubProject.getName(), actualSubProject.getName());
        assertEquals(expectedSubProject.getDescription(), actualSubProject.getDescription());
        assertEquals(expectedSubProject.getId(), actualSubProject.getId());
    }

    //***READ SUBPROJECTS***-------------------------------------------------------------------------------------------R
   @Test
    void getAllSubProjects() throws SQLException, ProfileException {
       //ARRANGE
       //Create Profile
       Profile profile = preSetProfile();
       profileRepository.addProfile(profile);

       //Create Project
       Project project = preSetProject();
       project.setProfileId(profile.getId()); // Reference the profile you just added
       projectRepository.addProject(project);

       //Create first subproject to test
       SubProject subProject1 = preSetSubProject();
       subProject1.setProjectId(project.getId());

       //Create second subproject to test
       SubProject subProject2 = new SubProject();
       subProject2.setName("test2 subproject name");
       subProject2.setDescription("test2 subproject description");
       subProject2.setCreatedDate(LocalDate.now());
       subProject2.setStartDate(LocalDate.of(2024, 1, 10));
       subProject2.setEndDate(LocalDate.of(2024, 12, 31));
       subProject2.setTotalEstHours(10);
       subProject2.setStatus(Status.ACTIVE);
       subProject2.setBudget(2000);
       subProject2.setActualPrice(1000);
       subProject2.setAssignedTo("test employee");
       subProject2.setProjectId(project.getId());

       //Add created subprojects
       subProjectRepository.addSubProject(subProject1);
       subProjectRepository.addSubProject(subProject2);

       //ACT
       List<SubProject> subProjects = subProjectRepository.getAllSubProjects();

       //ASSERT
       assertNotNull(subProjects, "The list of subprojects should not be null");
       assertTrue(subProjects.size() >= 2, "The number of subprojects should be at least two");

   }

   //***UPDATE SUBPROJECT***-------------------------------------------------------------------------------------------U
   @Test
    void updateSubProjectTest() throws SQLException, ProfileException {
        //ARRANGE
       //Create Profile
       Profile profile = preSetProfile();
       profileRepository.addProfile(profile);

       //Create Project
       Project project = preSetProject();
       project.setProfileId(profile.getId()); // Reference the profile you just added
       projectRepository.addProject(project);

       //Create subproject to test the update method
       SubProject subProject = preSetSubProject();
       subProject.setProjectId(project.getId());
       subProjectRepository.addSubProject(subProject);

       //Changes to test if task is updated
       subProject.setName("updated name");
       subProject.setStartDate(LocalDate.of(2024, 2, 4));
       subProject.setAssignedTo("updated assigned to");

       //ACT
       subProjectRepository.updateSubProject(subProject);
       SubProject updatedSubProject = subProjectRepository.getSubProjectById(subProject.getId());

       //ASSERT
       assertEquals("updated name", updatedSubProject.getName());
       assertEquals(LocalDate.of(2024, 2, 4), updatedSubProject.getStartDate());
       assertEquals("updated assigned to", updatedSubProject.getAssignedTo());
   }

   //***DELETE SUBPROJECT***-------------------------------------------------------------------------------------------D
    @Rollback(false)
    @Test
    void deleteSubProjectTest() throws SQLException, ProfileException{
        //ARRANGE
        //Create Profile
        Profile profile = preSetProfile();
        profileRepository.addProfile(profile);

        //Create Project
        Project project = preSetProject();
        project.setProfileId(profile.getId()); // Reference the profile you just added
        projectRepository.addProject(project);

        //Create subproject to test delete method
        SubProject subProject = preSetSubProject();
        subProject.setProjectId(project.getId());
        subProjectRepository.addSubProject(subProject);

        //ACT
        assertTrue(subProject.getId() > 0); //Tjekker om der er et subproject
        subProjectRepository.deleteSubProject(subProject.getId());

        //ASSERT
        SubProject deletedeSubProject = subProjectRepository.getSubProjectById(subProject.getId());
        assertNull(deletedeSubProject); //Asserts that the project has been deleted
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
