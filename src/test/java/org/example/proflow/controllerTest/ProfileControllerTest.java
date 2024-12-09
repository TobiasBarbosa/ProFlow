package org.example.proflow.controllerTest;

import org.example.proflow.controller.ProfileController;
import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Profile;
import org.example.proflow.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(ProfileController.class)
//
//public class ProfileControllerTest {
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
//        profile = new Profile(1, "Test FirstName", "Test LastName", "test@mail.dk", "testpassword");
//    }
//
//    @Test
//    public void getProfileByIdTest() throws Exception { //skal vi have den her, vi har jo ikke rigtig getProfileById i controller?
//        when(profileService.getProfileById(1)).thenReturn(profile);
//
//        mockMvc.perform(get("/")) //sætte endpoint ind
//                .andExpect(status().isOk())
//                .andExpect(view().name("")) //sætte HTML side ind
//                .andExpect(model().attributeExists("profile"))
//                .andExpect(model().attribute("profile", profile));
//
//    }
//
//}
