package org.example.proflow.serviceTest;

import org.example.proflow.exception.ProfileException;
import org.example.proflow.model.Status;
import org.example.proflow.model.Task;
import org.example.proflow.repository.TaskRepository;
import org.example.proflow.service.TaskService;
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
public class TaskServiceTest {

    //***TO DO***-------------------------------------------------------------------------------------------------------
    //TODO: skriv i rapport: hvorfor vi ikke har lavet integrationstest i serviceklasserne?

    //***ACCESS ATTRIBUTES***-------------------------------------------------------------------------------------------
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    //***OBJECT ATTRIBUTES***-------------------------------------------------------------------------------------------
    private Task task;

    //***TEST HELP METHODS***-------------------------------------------------------------------------------------------
    @Before
    public void setUp(){
        task = new Task();
        task.setName("test task name");
        task.setDescription("test task description");
        task.setLocation("test task location");
        task.setCreatedDate(LocalDate.now());
        task.setStartDate(LocalDate.of(2024,1,1));
        task.setEndDate(LocalDate.of(2024,12,31));
        task.setTotalEstHours(100.0);
        task.setStatus(Status.ACTIVE);
        task.setAssignedTo("test task assigned to");
        task.setTaskPrice(100.0);
        task.setSubProjectId(1);
    }

    @AfterEach
    public void tearDown(){
        taskRepository.deleteAllTasks();
    }

    //***TEST TASK METHODS***-------------------------------------------------------------------------------------------
    //***CREATE TASK TEST***-------------------------------------------------------------------------------------------C
    @Test
    public void addTaskVerifyTest() throws SQLException{
        //Tester om addProfile kalder addProfile i repository
        //Act
        taskService.addTask(task);

        //Assert
        verify(taskRepository).addTask(task);
    }

    //***READ TASK TEST***---------------------------------------------------------------------------------------------R
    @Test
    public void getTaskByIdTest() throws SQLException, ProfileException {
        when(taskRepository.getTaskById(task.getId())).thenReturn(task);

        //ACT
        Task result = taskService.getTaskById(task.getId());

        //ASSERT
        assertNotNull(result);
        assertEquals(task.getName(), result.getName());
        assertEquals(task.getDescription(), result.getDescription());
        assertEquals(task.getLocation(), result.getLocation());
        assertEquals(task.getAssignedTo(), result.getAssignedTo());
    }

    //***UPDATE TASK TEST***-------------------------------------------------------------------------------------------U
    @Test
    public void updateTaskVerifyTest() throws SQLException{
        //Tester om updateProfile kalder updateProfile i repository
        //ACT
        taskService.updateTask(task);

        //ASSERT
        verify(taskRepository).updateTask(task);
    }

    //***DELETE TASK METHODS***----------------------------------------------------------------------------------------D
    @Test
    public void deleteTaskVerifyTest() throws SQLException {
        //Tester om updateProfile kalder updateProfile i repository
        //ACT
        taskService.deleteTask(task.getId());

        //ASSERT
        verify(taskRepository).deleteTask(task.getId());
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
