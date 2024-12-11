package org.example.proflow.controllerTest;

import org.example.proflow.controller.ProfileController;
import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.service.ProfileService;
import org.example.proflow.service.ProjectService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.sql.SQLException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfileController.class)
@RunWith(SpringRunner.class)

public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    @MockitoBean
    private ProjectService projectService;

    private Profile profile;
    @BeforeEach
    public void setUp() throws SQLException, ProfileException {
        profile = new Profile();
        profile.setFirstName("Test firstname");
        profile.setLastName("Test lastname");
        profile.setEmail("test@email.com");
        profile.setPassword("testpassword");
    }

    @AfterEach
    public void tearDown(){

    }

    @Test
    public void showLoginTest() throws Exception{
        mockMvc.perform(get("/homepage/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testAddProfile() throws Exception {
        mockMvc.perform(get("/homepage/add-profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("profile"));
    }


}
