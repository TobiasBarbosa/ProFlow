package org.example.proflow.repositoryTest;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.repository.ProfileRepository;
import org.junit.jupiter.api.AfterEach;
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

    //***OBJECT(S) ATTRIBUTES***----------------------------------------------------------------------------------------
    private Profile profile;

    //***TEST HELP METHODS***-------------------------------------------------------------------------------------------
    @BeforeEach
    //Kører før hver test og sætter en profil op
    void setUp(){
        profile = new Profile();
        profile.setFirstName("test firstname");
        profile.setLastName("test lastname");
        profile.setEmail("test-email@test.com");
        profile.setPassword("testPassword");
    }

    @AfterEach
    // Kører efter hver test to clear the database
    void clearProfileDatabase() {
        profileRepository.deleteAllProfiles();
    }

    //***PROFILE TEST METHODS***----------------------------------------------------------------------------------------
    //***CREATE METHODS***---------------------------------------------------------------------------------------------C
    @Test
    void addProfileTest() throws ProfileException {
        // ARRANGE done in setUp

        //ACT
        profileRepository.addProfile(profile);
        Profile actualProfile = profileRepository.getProfileById(profile.getId());

        //ASSERT
        assertEquals(profile.getFirstName(), actualProfile.getFirstName());
        assertEquals(profile.getEmail(), profile.getEmail());
        assertEquals(profile.getId(), profile.getId());
    }

    //***READ METHODS***-----------------------------------------------------------------------------------------------R
    @Test
    void getAllProfilesTest() throws ProfileException{
        // ARRANGE
        Profile profile2 = new Profile();
        profile2.setFirstName("Test2 firstname");
        profile2.setLastName("Test2 lastname");
        profile2.setEmail("test2email@test.com");
        profile2.setPassword("test2Password");

        profileRepository.addProfile(profile);
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
    @Test
    public void deleteProjectTest() throws ProfileException{
        // ARRANGE
        profileRepository.addProfile(profile);

        //ASSERT: make sure project exist
        assertTrue(profile.getId() > 0); //

        // ACT: Delete the Project
        profileRepository.deleteProfile(profile.getId());

        // ASSERT: Verify the project no longer exists
        Profile deletedProfile = profileRepository.getProfileById(profile.getId());
        assertNull(deletedProfile); // Assert that the project has been deleted

    }

    //***END***---------------------------------------------------------------------------------------------------------
}
