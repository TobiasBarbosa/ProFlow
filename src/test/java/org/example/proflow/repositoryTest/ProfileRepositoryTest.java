package org.example.proflow.repositoryTest;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
// Når man tilføjer denne annotering på testklassen eller metoden, betyder det, at testen kører inden for en transaktion. Denne transaktion sikrer, at alle databaseoperationer i testen håndteres samlet, som om de er en del af en enkelt transaktion.
//@Rollback(true) // Ruller tilbage efter testen / skal bruges på databasen / ikke h2
@ActiveProfiles("h2")
public class ProfileRepositoryTest {

    //***ACCESS ATTRIBUTES***-------------------------------------------------------------------------------------------
    @Autowired
    private ProfileRepository profileRepository;

    //***PROFILE METHODS***---------------------------------------------------------------------------------------------
    //***CREATE METHODS***---------------------------------------------------------------------------------------------C
    @Test
    void addProfileTest() throws ProfileException {
        // ARRANGE
        Profile expectedProfile = new Profile();
        //expectedProfile.setId(1); // Set this to match the profile_id in the Project
        expectedProfile.setFirstName("Test name");
        expectedProfile.setLastName("Testsen name ");
        expectedProfile.setEmail("test@test.dk");
        expectedProfile.setPassword("test");

        //ACT
        profileRepository.addProfile(expectedProfile);
        expectedProfile.setId(expectedProfile.getId());
        Profile actualProfile = profileRepository.getProfileById(expectedProfile.getId());

        //ASSERT
        assertEquals(expectedProfile.getFirstName(), actualProfile.getFirstName());
        assertEquals(expectedProfile.getEmail(), expectedProfile.getEmail());
        assertEquals(expectedProfile.getId(), expectedProfile.getId());
    }

    //***READ METHODS***-----------------------------------------------------------------------------------------------R
    @Test
    void getAllProfilesTest(){
        // ARRANGE

        Profile profile1 = new Profile();
        profile1.setFirstName("Test name");
        profile1.setLastName("Testsen name ");
        profile1.setEmail("test@test.dk");
        profile1.setPassword("test");

        Profile profile2 = new Profile();
        profile2.setFirstName("Test2 name");
        profile2.setLastName("Testsen2 name ");
        profile2.setEmail("test2@test.dk");
        profile2.setPassword("test2");

        profileRepository.addProfile(profile1);
        profileRepository.addProfile(profile2);

        //ACT
        List<Profile> profiles = profileRepository.getAllProfiles();

        //ASSERT
        assertNotNull(profiles, "The list of projects should not be null");
        assertTrue(profiles.size() >= 2, "The number of projects should be at least 2");
    }

    //***UPDATE PROFILE METHOD***--------------------------------------------------------------------------------------U
    @Test
    void updateProfileTest() throws ProfileException{
        //ARRANGE
        Profile profile = new Profile();
        profile.setFirstName("Test");
        profile.setLastName("User");
        profile.setEmail("test@example.com");
        profile.setPassword("password");
        profileRepository.addProfile(profile);

        //MODIFY CHANGES
        profile.setFirstName("Test update");
        profile.setLastName("User update");
        profile.setEmail("testupdate@example.com");

        //ACT
        profileRepository.updateProfile(profile);
        Profile updatedProfile = profileRepository.getProfileById(profile.getId());

        //ASSERT
        assertEquals("Test update", updatedProfile.getFirstName());
        assertEquals("User update", updatedProfile.getLastName());
        assertEquals("testupdate@example.com", updatedProfile.getEmail());
        assertEquals("password", updatedProfile.getPassword());
    }

    //***DELETE PROFILE METHOD***--------------------------------------------------------------------------------------D
    @Rollback(false)  // Prevent rollback for this test so we can verify deletion
    @Test
    public void deleteProjectTest() throws ProfileException{
        // ARRANGE
        Profile profile = new Profile();
        profile.setFirstName("Test");
        profile.setLastName("User");
        profile.setEmail("test@example.com");
        profile.setPassword("password");
        profileRepository.addProfile(profile);

        //ASSERT
        assertTrue(profile.getId() > 0); // ensure project exist

        // ACT: Delete the Project
        profileRepository.deleteProfile(profile.getId());

        // ASSERT: Verify the project no longer exists
        Profile deletedProfile = profileRepository.getProfileById(profile.getId());
        assertNull(deletedProfile); // Assert that the project has been deleted

    }

    //***END***---------------------------------------------------------------------------------------------------------
}
