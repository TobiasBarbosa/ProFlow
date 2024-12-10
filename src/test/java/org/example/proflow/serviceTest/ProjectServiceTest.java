package org.example.proflow.serviceTest;

import org.example.proflow.model.Profile;
import org.example.proflow.model.Project;
import org.example.proflow.repository.ProfileRepository;
import org.example.proflow.repository.ProjectRepository;
import org.example.proflow.service.ProfileService;
import org.example.proflow.service.ProjectService;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    protected ProjectService projectService;

    private Project project;

    @Before //arrange
    public void setUp() {
        project = new Project();
//        project.setFirstName("test firstname");
//        project.setLastName("test lastname");
//        project.setEmail("test-email@test.com");
//        project.setPassword("testPassword");
    }

    @AfterEach
    public void tearDown(){
        projectRepository.deleteAllProjects();
    }


}
