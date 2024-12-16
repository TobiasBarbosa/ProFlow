package org.example.proflow.controllerTest;

import org.example.proflow.controller.ProjectController;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.service.ProjectService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
@RunWith(SpringRunner.class)

public class ProjectControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
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
//    //Test at mam bliver redirected til homepage hvis man ikke har session
//    public void testAddProjectInvalidProfileId() throws Exception {
//        mockMvc.perform(get("/dashboard/add-project/1"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/homepage"));
//    }

//    @Test //TODO fejler pga manglede forbindelse til thymeleaf men vi skal lige overveje om vi skla kunne setId
//    public void testAddProjectValidProfileId() throws Exception {
//        Profile validProfile = new Profile();
//        validProfile.setId(1);
//        validProfile.setFirstName("Test firstname");
//        validProfile.setLastName("Test lastname");
//        validProfile.setEmail("test@email.com");
//
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("profile", validProfile);
//
//        mockMvc.perform(get("/dashboard/add-project/{profileId}", 1)
//                .session(session))
//                .andExpect(status().isOk())
//                .andExpect(view().name("add_project"))
//                .andExpect(model().attributeExists("profileId"))
//                .andExpect(model().attributeExists("project"));
//    }



}
