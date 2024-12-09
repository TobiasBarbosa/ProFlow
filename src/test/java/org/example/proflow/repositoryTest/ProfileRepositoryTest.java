package org.example.proflow.repositoryTest;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
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

    //***TEST HELP METHODS***-------------------------------------------------------------------------------------------
    @BeforeEach
    void clearProfileDatabase() {
        profileRepository.deleteAllProfiles();
    }

    private Profile preSetProfile() throws ProfileException {
        Profile profile = new Profile();
        profile.setFirstName("test firstname");
        profile.setLastName("test lastname");
        profile.setEmail("test-email@test.com");
        profile.setPassword("testPassword");
        return profile;
    }

    //***PROFILE TEST METHODS***----------------------------------------------------------------------------------------
    //***CREATE METHODS***---------------------------------------------------------------------------------------------C
    @Test
    void addProfileTest() throws ProfileException {
        // ARRANGE
        Profile expectedProfile = preSetProfile();

        //ACT
        profileRepository.addProfile(expectedProfile);
        Profile actualProfile = profileRepository.getProfileById(expectedProfile.getId());

        //ASSERT
        assertEquals(expectedProfile.getFirstName(), actualProfile.getFirstName());
        assertEquals(expectedProfile.getEmail(), expectedProfile.getEmail());
        assertEquals(expectedProfile.getId(), expectedProfile.getId());
    }

    //***READ METHODS***-----------------------------------------------------------------------------------------------R
    @Test
    void getAllProfilesTest() throws ProfileException{
        // ARRANGE
        Profile profile1 = preSetProfile();

        Profile profile2 = new Profile();
        profile2.setFirstName("Test2 firstname");
        profile2.setLastName("Test2 lastname");
        profile2.setEmail("test2email@test.com");
        profile2.setPassword("test2Password");

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
        Profile profile = preSetProfile();
        profileRepository.addProfile(profile);

        //MODIFY CHANGES
        profile.setFirstName("test firstname update");
        profile.setLastName("test lastname update");
        profile.setEmail("testemailupdate@test.com");

        //ACT
        profileRepository.updateProfile(profile);
        Profile updatedProfile = profileRepository.getProfileById(profile.getId());

        //ASSERT
        assertEquals("test firstname update", updatedProfile.getFirstName());
        assertEquals("test lastname update", updatedProfile.getLastName());
        assertEquals("testemailupdate@test.com", updatedProfile.getEmail());
        assertEquals("testPassword", updatedProfile.getPassword());
    }

    //***DELETE PROFILE METHOD***--------------------------------------------------------------------------------------D
    @Rollback(false)  // Prevent rollback for this test so we can verify deletion
    @Test
    public void deleteProjectTest() throws ProfileException{
        // ARRANGE
        Profile profile = preSetProfile();
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
