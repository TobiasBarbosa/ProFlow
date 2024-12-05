package org.example.proflow.controllerTest;

import org.example.proflow.controller.ProfileController;
import org.example.proflow.model.Profile;
import org.example.proflow.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfileController.class)

public class ProfileControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ProfileService profileService;
//
//    private Profile profile;
//
//    @BeforeEach
//    public void setUp() {
//        profile = new Profile(1, "Josefine", "Røes", "joro0003@stud.kea.dk", "1234abcd");
//    }


}
