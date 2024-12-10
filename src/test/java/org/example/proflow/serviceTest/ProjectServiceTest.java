package org.example.proflow.serviceTest;

import org.example.proflow.model.Project;
import org.example.proflow.repository.ProfileRepository;
import org.example.proflow.repository.ProjectRepository;
import org.example.proflow.service.ProfileService;
import org.example.proflow.service.ProjectService;
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


}
