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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        // Insert a profile entry
        Profile profile = new Profile();
        profile.setId(1); // Set this to match the profile_id in the Project
        profile.setFirstName("Test name");
        profile.setLastName("Testsen name ");
        profile.setEmail("test@test.dk");
        profile.setPassword("test");
        profileRepository.addProfile(profile);

        // Continue with your existing code
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
}
