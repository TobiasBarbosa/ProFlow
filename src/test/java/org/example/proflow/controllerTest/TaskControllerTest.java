package org.example.proflow.controllerTest;

import org.example.proflow.controller.TaskController;
import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.model.SubProject;
import org.example.proflow.model.Task;
import org.example.proflow.service.ProjectService;
import org.example.proflow.service.TaskService;
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
@WebMvcTest(TaskController.class)
@RunWith(SpringRunner.class)

public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    private Profile profile;
    private Project project;
    private SubProject subProject;
    private Task task;

    @BeforeEach
    public void setUp(){
        profile = new Profile();
        profile.setFirstName("Test firstname");
        profile.setLastName("Test lastname");
        profile.setEmail("test@email.com");
        profile.setPassword("testpassword");

        project = new Project();

        subProject = new SubProject();

        task = new Task();

    }

    @AfterEach
    public void tearDown(){

    }

}
