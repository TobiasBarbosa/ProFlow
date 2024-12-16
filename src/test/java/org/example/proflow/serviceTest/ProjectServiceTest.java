package org.example.proflow.serviceTest;
import org.example.proflow.exception.ProjectException;
import org.example.proflow.model.Project;
import org.example.proflow.model.Status;
import org.example.proflow.service.ProjectService;
import org.example.proflow.util.interfaces.ProjectRepositoryInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    @Mock
    private ProjectRepositoryInterface projectRepository;

    @InjectMocks
    protected ProjectService projectService;

    //***OBJECT ATTRIBUTES***-------------------------------------------------------------------------------------------
    private Project project;

    //***TEST HELP METHODS***-------------------------------------------------------------------------------------------
    @Before
    public void setUp() {
        //arrange
        project = new Project();
        project.setName("test project name");
        project.setDescription("test project description");
        project.setCreatedDate(LocalDate.now());
        project.setStartDate(LocalDate.of(2024, 1, 1));
        project.setEndDate(LocalDate.of(2024, 12, 31));
        project.setTotalEstHours(10);
        project.setStatus(Status.ACTIVE);
        project.setBudget(2000);
        project.setActualPrice(1000);
    }

    @AfterEach
    public void tearDown(){
        projectRepository.clearProjectsForTesting();
    }

    //***CREATE PROJECT TEST***----------------------------------------------------------------------------------------C
    @Test
    public void addProjectVerifyTest() throws SQLException {
        //Tester om addProject kalder addProject i repository
        //Act
        projectService.addProject(project);

        //Assert
        verify(projectRepository).addProject(project);
    }

    //***READ PROJECT TEST***------------------------------------------------------------------------------------------R
    @Test
    public void getProjectByIdTest() throws SQLException, ProjectException {
        when(projectRepository.getProjectById(project.getId())).thenReturn(project);

        //ACT
        Project result = projectService.getProjectById(project.getId());

        //ASSERT
        assertNotNull(result);
        assertEquals("test project name", result.getName());
        assertEquals("test project description", result.getDescription());
        assertEquals(LocalDate.now(), result.getCreatedDate());
        assertEquals(LocalDate.of(2024, 1, 1), result.getStartDate());
        assertEquals(LocalDate.of(2024, 12, 31), result.getEndDate());
        assertEquals(10, result.getTotalEstHours());
        assertEquals(Status.ACTIVE, result.getStatus());
        assertEquals(2000, result.getBudget());
        assertEquals(1000, result.getActualPrice());

    }
    //***UPDATE PROJECT TEST***----------------------------------------------------------------------------------------U
    @Test
    public void updateProjectVerifyTest() throws SQLException {
        //Tester om updateProject kalder updateProject i repository
        //ACT
        projectService.updateProject(project);

        //ASSERT
        verify(projectRepository).updateProject(project);
    }

    //***DELETE PROJECT TEST***----------------------------------------------------------------------------------------D
    @Test
    public void deleteProjectVerifyTest() throws ProjectException, SQLException {
        //Tester om updateProject kalder updateProject i repository
        //ACT
        projectService.deleteProject(project.getId());

        //ASSERT
        verify(projectRepository).deleteProject(project.getId());
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
