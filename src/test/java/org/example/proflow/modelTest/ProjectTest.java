package org.example.proflow.modelTest;

import org.example.proflow.model.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    //***EXAM QUESTIONS***----------------------------------------------------------------------------------------------
    //Hvorfor bruge assertions?
    //Hvad er JUnit test? Og hvad differentierer dem fra andre test kategorier?
    //Exceptions rapport

    //***TO DO***-------------------------------------------------------------------------------------------------------
    //TODO test calculateActualPriceForProject()   - bemærk at den looper igennem sæt fra database...
    //TODO test calculateTotalEstHoursForProject() - bemærk at den looper igennem sæt fra database...

    //***ACCESS ATTRIBUTES***-------------------------------------------------------------------------------------------
    private final Project project = new Project();

    //***PROJECT TEST METHODS***----------------------------------------------------------------------------------------
    //***JUNIT TEST(S)***-----------------------------------------------------------------------------------------------
    @Test
    void setEndDate() {
        // ARRANGE
        LocalDate expectedStartDate = LocalDate.of(2024, 1, 1);
        LocalDate expectedEndDate = LocalDate.of(2024, 12, 31);
        LocalDate invalidEndDate = LocalDate.of(2023, 12, 31);
        String expectedExceptionMessage = "End date cannot be before start date.";

        // ACT
        project.setStartDate(expectedStartDate);
        project.setEndDate(expectedEndDate);

        LocalDate actualEndDate = project.getEndDate(); // Retrieve the actual end date

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            project.setEndDate(invalidEndDate);
        });

        // ASSERT
        // 1: Tjekker at endDate ikke er null
        assertNotNull(actualEndDate, "End date should not be null.");

        //2: Tjekker at actualEndDate matcher expectedEndDate
        assertEquals(expectedEndDate, actualEndDate, "End date should match the expected value.");

        //3: Tjekker at en exception bliver kastet for en invalid endDate
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    void setBudget(){
        // ARRANGE
        double expectedBudget = 99;
        double invalidBudget = -99;
        String expectedExceptionMessage = "budget can not be less than 0";

        // ACT
        project.setBudget(expectedBudget);
        double actualBudget = project.getBudget();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            project.setBudget(invalidBudget);
        });

        //ASSERT
        //1: Tjekker at budget ikke er null
        assertNotNull(actualBudget, "Budget should not be null");

        //2: Tjekker at actual og expected budget stemmer overens
        assertEquals(actualBudget, expectedBudget);

        //3: Tjekker at den kaster en fejlmeddelelse hvis budget er under 0
        Assertions.assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    void calculateDaysUntilDone(){
        // ARRANGE
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 2);
        LocalDate invalidDate = null;
        String expectedExceptionMessage = "Start and end dates must not be null.";

        // ACT
        int expectedDaysUntilDone = project.calculateDaysUntilDone(startDate,endDate);
        int actualDaysUntilDone = 1;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            project.calculateDaysUntilDone(startDate, invalidDate);
        });

        // ASSERT
        //1: tester at metode beregner rigtigt
        assertEquals(expectedDaysUntilDone,actualDaysUntilDone);

        //2: tester at den spytter den rigtige exception ud når (en) date er null
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
