package org.example.proflow.repositoryTest;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    //***END***---------------------------------------------------------------------------------------------------------
}
