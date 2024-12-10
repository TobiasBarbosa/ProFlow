package org.example.proflow.serviceTest;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.repository.ProfileRepository;
import org.example.proflow.service.ProfileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
//TODO undersøge hvor metoder skal være public i serviceTest laget
//Svar: Alle dine testmetoder skal være public, da testmetoderne ellers ikke vil blive opdaget af test runneren. Så sørg for, at dine metoder er offentlige, hvilket du allerede har gjort i dine testmetoder.
public class ProfileServiceTest {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    //***OBJECT ATTRIBUTES***-------------------------------------------------------------------------------------------
    private Profile profile;

    //***TEST HELP METHODS***-------------------------------------------------------------------------------------------
    @Before //arrange
    public void setUp() {
        profile = new Profile();
        profile.setFirstName("test firstname");
        profile.setLastName("test lastname");
        profile.setEmail("test-email@test.com");
        profile.setPassword("testPassword");
    }

    @AfterEach
    public void tearDown(){
        profileRepository.deleteAllProfiles();
    }

    //***TEST PROFILE METHODS***----------------------------------------------------------------------------------------
    //***CREATE PROFILE TEST***----------------------------------------------------------------------------------------C
    @Test
    public void addProfileVerifyTest(){
        //Tester om addProfile kalder addProfile i repository
        //Act
        profileService.addProfile(profile);

        //Assert
        verify(profileRepository).addProfile(profile);
    }

    //***READ PROFILE TEST***------------------------------------------------------------------------------------------R
    @Test
    public void getProfileByIdTest() throws SQLException, ProfileException {
        when(profileRepository.getProfileById(profile.getId())).thenReturn(profile);

        //ACT
        Profile result = profileService.getProfileById(profile.getId());

        //ASSERT
        assertNotNull(result);
        assertEquals("test firstname", result.getFirstName());
        assertEquals("test lastname", result.getLastName());
        assertEquals("test-email@test.com", result.getEmail());
        assertEquals("testPassword", result.getPassword());
    }

    //***UPDATE PROFILE TEST***----------------------------------------------------------------------------------------U
    @Test
    public void updateProfileVerifyTest(){
        //Tester om updateProfile kalder updateProfile i repository
        //ACT
        profileService.updateProfile(profile);

        //ASSERT
        verify(profileRepository).updateProfile(profile);
    }

    //***DELETE PROFILE TEST***----------------------------------------------------------------------------------------D
    @Test
    public void deleteProfileVerifyTest() throws ProfileException {
        //Tester om updateProfile kalder updateProfile i repository
        //ACT
        profileService.deleteProfile(profile.getId());

        //ASSERT
        verify(profileRepository).deleteProfile(profile.getId());
    }

    //------------------------------------------------------------------------------------------------------------------
}
