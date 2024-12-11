package org.example.proflow.controllerTest;

import org.example.proflow.controller.SubProjectController;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.SubProject;
import org.example.proflow.service.ProfileService;
import org.example.proflow.service.ProjectService;
import org.example.proflow.service.SubProjectService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(SubProjectController.class)
//@RunWith(SpringRunner.class)
//
////TODO Test for subprojectcontroller virker ikke
//
//public class SubProjectControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private ProfileService profileService;
//
//    @MockitoBean
//    private SubProjectService subProjectService;
//
//    @MockitoBean
//    private ProjectService projectService;
//
//    @BeforeEach
//    public void setUp(){
//
//    }
//
//    @AfterEach
//    public void tearDown(){
//
//    }
//
//    @Test
//    public void deleteSubProjectValidProfileIdTest() throws Exception {
//        Profile validProfile = new Profile();
//        validProfile.setId(1);
//        validProfile.setFirstName("Test firstname");
//        validProfile.setLastName("Test lastname");
//        validProfile.setEmail("test@email.com");
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("profile", validProfile);
//
//        Project validProject = new Project();
//        validProject.setProfileId(1);
//        session.setAttribute("project", validProject);
//
//        SubProject validSubProject = new SubProject();
//        validSubProject.setId(1);
//        validSubProject.setName("SubProject Test");
//        session.setAttribute("subproject", validSubProject);
//
//        mockMvc.perform(get("/dashboard/1/1/subproject/delete/1", 1, 1, 1)
//                        .session(session))
//                .andExpect(status().isOk())
//                .andExpect(view().name("dashboard"));
//    }
//
//    @Test
//    //Test at mam bliver redirected til dashboard hvis man ikke har session
//    public void deleteSubProjectInvalidProfileIdTest() throws Exception {
//        mockMvc.perform(post("/dashboard/1/1/subproject/delete/1"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/homepage"));
//    }
//
//    @Test
//    public void getSubProjectByIdIsValidTest() throws Exception {
//        // Mocking valid session
//        Profile validProfile = new Profile();
//        validProfile.setId(1);
//        validProfile.setFirstName("Test firstname");
//        validProfile.setLastName("Test lastname");
//        validProfile.setEmail("test@email.com");
//
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("profile", validProfile);
//
//        Project validProject = new Project();
//        validProject.setProfileId(1);
//        session.setAttribute("project", validProject);
//
//        SubProject validSubProject = new SubProject();
//        validSubProject.setId(1);
//        validSubProject.setName("SubProject Test");
//        session.setAttribute("subproject", validSubProject);
//
//        mockMvc.perform(get("/dashboard/1/1/subproject/1").session(session))
//                .andExpect(status().isOk())
//                .andExpect(view().name("project"))
//                .andExpect(model().attribute("subprojectId", 1))
//                .andExpect(model().attribute("name", "SubProject Test"));
//    }
//
//}
