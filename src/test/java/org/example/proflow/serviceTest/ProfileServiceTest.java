package org.example.proflow.serviceTest;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.repository.ProfileRepository;
import org.example.proflow.service.ProfileService;
import org.junit.Before;
import org.junit.Test;
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
public class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    private Profile profile;

    @Before //arrange
    public void setUp() {
        profile = new Profile("FirstNameTest", "LastNameTest", "test@example.com", "testpassword");
    }

    @Test
    public void getProfileByIdTest() throws SQLException, ProfileException {
        when(profileRepository.getProfileById(1)).thenReturn(profile);

        //act
        Profile result = profileService.getProfileById(1);

        //assert
        assertNotNull(result);
        assertEquals("FirstNameTest", result.getFirstName());
        assertEquals("LastNameTest", result.getLastName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("testpassword", result.getPassword());
    }


//    @Test //TODO fra pdf, men det giver ikke mening fordi vi ikke har saveProfile i rep?
//    public void testAddProfileCallsSaveProfile(){
//        //Act
//        profileService.addProfile(profile);
//
//        //Assert
//        verify(profileRepository).saveProfile(profile);
//
//    }


}
