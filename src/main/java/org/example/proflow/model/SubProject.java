package org.example.proflow.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class SubProject {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private int id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int daysUntilDone;
    private Status status;
    private int projectId;
    private String assignedTo; // unique variable

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public SubProject(int id, String name, String description, LocalDate startDate, LocalDate endDate, Status status, int projectId, String assignedTo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        setEndDate(endDate);
        daysUntilDone = calculateDaysUntilDone(startDate,endDate);
        this.status = status;
        this.projectId = projectId;
        this.assignedTo = assignedTo;
    }

    // Constructor without `id` for new subprojects
    public SubProject(String name, String description, LocalDate startDate, LocalDate endDate, Status status, int projectId, String assignedTo) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        daysUntilDone = calculateDaysUntilDone(startDate,endDate);
        this.status = status;
        this.projectId = projectId;
        this.assignedTo = assignedTo;
    }

    public SubProject(String name, String description, LocalDate startDate, LocalDate endDate, Status status, String assignedTo) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        daysUntilDone = calculateDaysUntilDone(startDate,endDate);
        this.status = status;
        this.assignedTo = assignedTo;
    }

    // Default constructor
    public SubProject() {
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

    public int getProjectId() {
        return projectId;
    }

    public String getAssignedTo() {
        return assignedTo;
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

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
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
                "\nProject ID: "          + projectId                 +
                "\nAssigned to: "         + (assignedTo != null ? assignedTo : "Not assigned");
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
