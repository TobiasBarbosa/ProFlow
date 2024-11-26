package org.example.proflow.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class Task {
    //TODO hvad skal være det unikke i Task?
    //TODO skal Task have et projectID også?

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private int id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int daysUntilDone;
    private Status status;
    private int subProjectId;
    private String assignedTo;
    private String uniqueVariable;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public Task(int id, String name, String description, LocalDate startDate, LocalDate endDate, Status status, int subProjectId, String assignedTo, String uniqueVariable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        setEndDate(endDate);
        daysUntilDone = calculateDaysUntilDone(startDate,endDate);
        this.status = status;
        this.subProjectId = subProjectId;
        this.assignedTo = assignedTo;
        this.uniqueVariable = uniqueVariable;
    }

    public Task(String name, String description, LocalDate startDate, LocalDate endDate, Status status, int subProjectId, String assignedTo, String uniqueVariable) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        setEndDate(endDate);
        daysUntilDone = calculateDaysUntilDone(startDate,endDate);
        this.status = status;
        this.subProjectId = subProjectId;
        this.assignedTo = assignedTo;
        this.uniqueVariable = uniqueVariable;
    }

    public Task(String name, String description, LocalDate startDate, LocalDate endDate, Status status, String assignedTo, String uniqueVariable) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        setEndDate(endDate);
        daysUntilDone = calculateDaysUntilDone(startDate,endDate);
        this.status = status;
        this.assignedTo = assignedTo;
        this.uniqueVariable = uniqueVariable;
    }

    public Task() {
    }

    //***GETTER METHODS***----------------------------------------------------------------------------------------------
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getDaysUntilDone() {
        return daysUntilDone;
    }

    public Status getStatus() {
        return status;
    }

    public int getSubProjectId() {
        return subProjectId;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getUniqueVariable() {
        return uniqueVariable;
    }

    //***SETTER METHODS***----------------------------------------------------------------------------------------------
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        if(endDate.isBefore(startDate)){
            throw new IllegalArgumentException("End date cannot be before start date.");
        } else {
            this.endDate = endDate;
        }
    }

    public void setDaysUntilDone(int daysUntilDone) {
        this.daysUntilDone = daysUntilDone;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSubProjectId(int subProjectId) {
        this.subProjectId = subProjectId;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setUniqueVariable(String uniqueVariable) {
        this.uniqueVariable = uniqueVariable;
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    private int calculateDaysUntilDone(LocalDate startDate, LocalDate endDate){
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        // Calculate the difference in days
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return (int) days; // Cast to int and return
    }

    //***TO STRING METHOD***--------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return  "\nProject ID: "          + id                        +
                "\nProject name: "        + name                      +
                "\nDescription: "         + description               +
                "\nStart date: "          + startDate                 +
                "\nEnd date="             + endDate                   +
                "\nDays until finished: " + daysUntilDone             +
                "\nStatus: "              + status.getDisplayStatus() +
                "\nProject ID: "          + subProjectId              +
                "\nAssigned to: "         + (assignedTo != null ? assignedTo : "Not assigned") +
                "\nUnique variabel: "     + uniqueVariable            ;
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
