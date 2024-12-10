package org.example.proflow.serviceTest;
import org.example.proflow.exception.SubProjectException;
import org.example.proflow.model.Status;
import org.example.proflow.model.SubProject;
import org.example.proflow.repository.SubProjectRepository;
import org.example.proflow.service.SubProjectService;
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
public class SubProjectServiceTest {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    @Mock
    private SubProjectRepository subProjectRepository;

    @InjectMocks
    protected SubProjectService subProjectService;

    //***OBJECT ATTRIBUTES***-------------------------------------------------------------------------------------------
    private SubProject subProject;

    //***TEST HELP METHODS***-------------------------------------------------------------------------------------------
    //arrange
    @Before
    public void setUp() {
        subProject = new SubProject();
        subProject.setName("test subproject name");
        subProject.setDescription("test subproject description");
        subProject.setCreatedDate(LocalDate.now());
        subProject.setStartDate(LocalDate.of(2024, 1, 1));
        subProject.setEndDate(LocalDate.of(2024, 12, 31));
        subProject.setTotalEstHours(10);
        subProject.setStatus(Status.ACTIVE);
        subProject.setBudget(2000);
        subProject.setActualPrice(1000);
        subProject.setAssignedTo("test employee");
    }

    @AfterEach
    public void tearDown(){
        subProjectRepository.deleteAllSubProjects();
    }

    //***TEST SUBPROJECT METHODS***-------------------------------------------------------------------------------------
    //***CREATE SUBPROJECT TEST***-------------------------------------------------------------------------------------C
    @Test
    public void addSubProjectVerifyTest() throws SQLException {
        //Tester om addSubProject kalder addSubProject i repository
        //Act
        subProjectService.addSubProject(subProject);

        //Assert
        verify(subProjectRepository).addSubProject(subProject);
    }

    //***READ SUBPROJECT TEST***---------------------------------------------------------------------------------------R
    @Test
    public void getSubProjectByIdTest() throws SQLException, SubProjectException {
        when(subProjectRepository.getSubProjectById(subProject.getId())).thenReturn(subProject);

        //ACT
        SubProject result = subProjectService.getSubProjectById(subProject.getId());

        //ASSERT
        assertNotNull(result);
        assertEquals("test subproject name", result.getName());
        assertEquals("test subproject description", result.getDescription());
        assertEquals(LocalDate.now(), result.getCreatedDate());
        assertEquals(LocalDate.of(2024, 1, 1), result.getStartDate());
        assertEquals(LocalDate.of(2024, 12, 31), result.getEndDate());
        assertEquals(10, result.getTotalEstHours());
        assertEquals(Status.ACTIVE, result.getStatus());
        assertEquals(2000, result.getBudget());
        assertEquals(1000, result.getActualPrice());
        assertEquals("test employee", result.getAssignedTo());

    }

    //***UPDATE SUBPROJECT TEST***-------------------------------------------------------------------------------------U
    @Test
    public void updateSubProjectVerifyTest() throws SQLException {
        //Tester om updateSubProject kalder updateSubProject i repository
        //ACT
        subProjectService.updateSubProject(subProject);

        //ASSERT
        verify(subProjectRepository).updateSubProject(subProject);
    }

    //***DELETE SUBPROJECT TEST***-------------------------------------------------------------------------------------D
    @Test
    public void deleteSubProjectVerifyTest() throws SubProjectException, SQLException {
        //Tester om updateSubProject kalder updateSubProject i repository
        //ACT
        subProjectService.deleteSubProject(subProject.getId());

        //ASSERT
        verify(subProjectRepository).deleteSubProject(subProject.getId());
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
