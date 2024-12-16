package org.example.proflow.serviceTest;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.service.ProfileService;
import org.example.proflow.util.interfaces.ProfileRepositoryInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    @Mock
    private ProfileRepositoryInterface profileRepository;

    @InjectMocks
    private ProfileService profileService;

    //***OBJECT ATTRIBUTES***-------------------------------------------------------------------------------------------
    private Profile profile;

    //***TEST HELP METHODS***-------------------------------------------------------------------------------------------
    @Before
    public void setUp() {
        //arrange
        profile = new Profile();
        profile.setFirstName("test firstname");
        profile.setLastName("test lastname");
        profile.setEmail("test-email@test.com");
        profile.setPassword("testPassword");
    }

    @AfterEach
    public void tearDown(){
        profileRepository.clearProfilesForTesting();
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
        //Vi fortæller Mockito, at når profileRepository.getProfileById(profile.getId()) bliver kaldt, skal den returnere en bestemt værdi.
        //thenReturn(profile) betyder, at når den overordnede metode getProfileById kaldes med det specifikke profile.getId(), vil mock-objektet returnere den profile som er oprettet i setUp().

        //ACT
        Profile result = profileService.getProfileById(profile.getId());
        //Her kalder vi den faktiske metode på profileService-objektet for at hente en profil ved at bruge profile.getId(). Resultatet af kaldet bliver gemt i result.

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
