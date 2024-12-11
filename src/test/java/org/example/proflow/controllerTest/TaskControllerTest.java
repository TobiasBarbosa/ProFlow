package org.example.proflow.controllerTest;

import org.example.proflow.controller.TaskController;
import org.example.proflow.exception.ProfileException;
import org.example.proflow.service.ProfileService;
import org.example.proflow.service.ProjectService;

import org.junit.Test;
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

import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(TaskController.class)
//@RunWith(SpringRunner.class)
//
//public class TaskControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private ProfileService profileService;
//
//    @MockitoBean
//    private ProjectService projectService;
//    @BeforeEach
//    public void setUp() throws SQLException, ProfileException{
//
//    }
//
//    @AfterEach
//    public void tearDown() throws SQLException, ProfileException {
//
//    }
//
//    @Test
//    //Test at mam bliver redirected til homepage hvis man ikke har session
//    public void testAddTaskInvalidProfileId() throws Exception {
//        mockMvc.perform(get("/dashboard/1/1/1/add-task"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/homepage"));
//    }
//
//
//}
