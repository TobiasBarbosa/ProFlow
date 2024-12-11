package org.example.proflow.controllerTest;

import org.example.proflow.controller.ProjectController;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.service.ProjectService;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
@RunWith(SpringRunner.class)

public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    private Profile profile;
    private Project project;

    @BeforeEach
    public void setUp(){
        profile = new Profile();
        profile.setFirstName("Test firstname");
        profile.setLastName("Test lastname");
        profile.setEmail("test@email.com");
        profile.setPassword("testpassword");

        project = new Project();

    }

    @AfterEach
    public void tearDown(){

    }



}
