package org.example.proflow.modelTest;

import org.example.proflow.model.Project;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    //***ACCESS ATTRIBUTES***-------------------------------------------------------------------------------------------
    private final Project project = new Project();

    //***PROJECT TEST METHODS***----------------------------------------------------------------------------------------
    //***JUNIT TEST***--------------------------------------------------------------------------------------------------
//    @Test
//    void setEndDate() {
//        // ARRANGE
//        LocalDate expectedStartDate = LocalDate.of(2024, 1, 1);
//        LocalDate expectedEndDate = LocalDate.of(2024, 12, 31);
//        LocalDate invalidEndDate = LocalDate.of(2023, 12, 31);
//        String expectedExceptionMessage = "End date cannot be before start date.";
//
//        // ACT
//        project.setStartDate(expectedStartDate);
//        project.setEndDate(expectedEndDate);
//
//        LocalDate actualEndDate = project.getEndDate(); // Retrieve the actual end date
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            project.setEndDate(invalidEndDate);
//        });
//
//        // ASSERT
//        // 1: Tjekker at endDate ikke er null
//        assertNotNull(actualEndDate, "End date should not be null.");
//
//        //2: Tjekker at actualEndDate matcher expectedEndDate
//        assertEquals(expectedEndDate, actualEndDate, "End date should match the expected value.");
//
//        //3: Tjekker at en exception bliver kastet for en invalid endDate
//        assertEquals(expectedExceptionMessage, exception.getMessage());
//    }
//
//    @Test void calculateDaysUntilDone(){
//        // ARRANGE
//        LocalDate startDate = LocalDate.of(2024, 1, 1);
//        LocalDate endDate = LocalDate.of(2024, 1, 2);
//
//        // ACT
//        int expectedDaysUntilDone = project.calculateDaysUntilDone(startDate,endDate);
//        int actualDaysUntilDone = 1;
//
//        // ASSERT
//        assertEquals(expectedDaysUntilDone,actualDaysUntilDone);
//    }

    //***END***---------------------------------------------------------------------------------------------------------
}
