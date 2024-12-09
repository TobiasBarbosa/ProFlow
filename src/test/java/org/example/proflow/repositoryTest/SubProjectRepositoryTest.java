package org.example.proflow.repositoryTest;

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

    //***SUBPROJECT METHODS***------------------------------------------------------------------------------------------
    @Test
   void addSubProject() throws SQLException{
        //Create Profile
        Profile profile = new Profile();
        profile.setId(1); // Set this to match the profile_id in the Project
        profile.setFirstName("Test name");
        profile.setLastName("Testsen name ");
        profile.setEmail("test@test.dk");
        profile.setPassword("test");
        profileRepository.addProfile(profile);

        //Create Project
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

        // Add and retrieve the SubProject
        SubProject expectedSubProject = new SubProject();
        expectedSubProject.setName("Test name");
        expectedSubProject.setDescription("Tests description ");
        expectedSubProject.setCreatedDate(LocalDate.now());
        expectedSubProject.setStartDate(LocalDate.of(2024, 1, 1));
        expectedSubProject.setEndDate(LocalDate.of(2024, 12, 31));
        expectedSubProject.setTotalEstHours(10);
        expectedSubProject.setStatus(Status.ACTIVE);
        expectedSubProject.setBudget(2000);
        expectedSubProject.setActualPrice(1000);
        expectedSubProject.setProjectId(project.getId());
        expectedSubProject.setAssignedTo("test employee");

        subProjectRepository.addSubProject(expectedSubProject);
        SubProject actualSubProject = subProjectRepository.getSubProjectById(expectedSubProject.getProjectId());

        assertEquals(expectedSubProject.getName(), actualSubProject.getName());
        assertEquals(expectedSubProject.getDescription(), actualSubProject.getDescription());
        assertEquals(expectedSubProject.getId(), actualSubProject.getId());
    }

   @Test
    void getAllSubProjects() throws SQLException {
       //ARRANGE
       //Create Profile
       Profile profile = new Profile();
       profile.setId(1); // Set this to match the profile_id in the Project
       profile.setFirstName("Test name");
       profile.setLastName("Testsen name ");
       profile.setEmail("test@test.dk");
       profile.setPassword("test");
       profileRepository.addProfile(profile);

       //Create Project
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

       //Create first subproject to test
       SubProject subProject1 = new SubProject();
       subProject1.setName("Test name 1");
       subProject1.setDescription("Tests description 1");
       subProject1.setCreatedDate(LocalDate.now());
       subProject1.setStartDate(LocalDate.of(2024, 1, 1));
       subProject1.setEndDate(LocalDate.of(2024, 12, 30));
       subProject1.setTotalEstHours(15);
       subProject1.setStatus(Status.ACTIVE);
       subProject1.setBudget(2000);
       subProject1.setActualPrice(1000);
       subProject1.setProjectId(project.getId());
       subProject1.setAssignedTo("test employee");

       //Create second subproject to test
       SubProject subProject2 = new SubProject();
       subProject2.setName("Test name 2");
       subProject2.setDescription("Tests description 2");
       subProject2.setCreatedDate(LocalDate.now());
       subProject2.setStartDate(LocalDate.of(2024, 1, 10));
       subProject2.setEndDate(LocalDate.of(2024, 12, 31));
       subProject2.setTotalEstHours(10);
       subProject2.setStatus(Status.ACTIVE);
       subProject2.setBudget(2000);
       subProject2.setActualPrice(1000);
       subProject2.setProjectId(project.getId());
       subProject2.setAssignedTo("test employee");

       //Add created subprojects
       subProjectRepository.addSubProject(subProject1);
       subProjectRepository.addSubProject(subProject2);

       //ACT
       List<SubProject> subProjects = subProjectRepository.getAllSubProjects();

       //ASSERT
       assertNotNull(subProjects, "The list of subprojects should not be null");
       assertTrue(subProjects.size() >= 2, "The number of subprojects should be at least two");

   }

   @Test
    void updateSubProjectTest() throws SQLException {
        //ARRANGE
       //Create Profile
       Profile profile = new Profile();
       profile.setId(1); // Set this to match the profile_id in the Project
       profile.setFirstName("Test name");
       profile.setLastName("Testsen name ");
       profile.setEmail("test@test.dk");
       profile.setPassword("test");
       profileRepository.addProfile(profile);

       //Create Project
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

       //Create subproject to test the update method
       SubProject subProject = new SubProject();
       subProject.setName("Test subproject name");
       subProject.setDescription("Tests subproject description");
       subProject.setCreatedDate(LocalDate.now());
       subProject.setStartDate(LocalDate.of(2024, 1, 1));
       subProject.setEndDate(LocalDate.of(2024, 12, 30));
       subProject.setTotalEstHours(12);
       subProject.setStatus(Status.ACTIVE);
       subProject.setBudget(2000);
       subProject.setActualPrice(1000);
       subProject.setProjectId(project.getId());
       subProject.setAssignedTo("test employee");

       subProjectRepository.addSubProject(subProject);

       //Changes to test if task is updated
       subProject.setName("new name");
       subProject.setStartDate(LocalDate.of(2024, 2, 4));
       subProject.setAssignedTo("new employee");

       //ACT
       subProjectRepository.updateSubProject(subProject);
       SubProject updatedSubProject = subProjectRepository.getSubProjectById(subProject.getId());

       //ASSERT
       assertEquals("new name", updatedSubProject.getName());
       assertEquals(LocalDate.of(2024, 2, 4), updatedSubProject.getStartDate());
       assertEquals("new employee", updatedSubProject.getAssignedTo());
   }
    @Rollback(false)
    @Test
    void deleteSubProjectTest() throws SQLException{
        //ARRANGE
        //Create Profile
        Profile profile = new Profile();
        profile.setId(1); // Set this to match the profile_id in the Project
        profile.setFirstName("Test name");
        profile.setLastName("Testsen name ");
        profile.setEmail("test@test.dk");
        profile.setPassword("test");
        profileRepository.addProfile(profile);

        //Create Project
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

        //Create subproject to test delete method
        SubProject subProject = new SubProject();
        subProject.setName("Test subproject name");
        subProject.setDescription("Tests subproject description");
        subProject.setCreatedDate(LocalDate.now());
        subProject.setStartDate(LocalDate.of(2024, 1, 1));
        subProject.setEndDate(LocalDate.of(2024, 12, 30));
        subProject.setTotalEstHours(12);
        subProject.setStatus(Status.ACTIVE);
        subProject.setBudget(2000);
        subProject.setActualPrice(1000);
        subProject.setProjectId(project.getId());
        subProject.setAssignedTo("test employee");
        subProjectRepository.addSubProject(subProject);

        //ACT
        assertTrue(subProject.getId() > 0); //Tjekker om der er et subproject
        subProjectRepository.deleteSubProject(subProject.getId());

        //ASSERT
        SubProject deletedeSubProject = subProjectRepository.getSubProjectById(subProject.getId());
        assertNull(deletedeSubProject); //Asserts that the project has been deleted
    }


}
