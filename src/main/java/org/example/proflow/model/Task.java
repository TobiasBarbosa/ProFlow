package org.example.proflow.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class Task extends ProjectTask{

    //^^^EXAM QUESTION^^^-----------------------------------------------------------------------------------------------
    //@Component - Hvad gør den?

    //***TO DO***-------------------------------------------------------------------------------------------------------
    //TODO lav createdDate attribute final!
    //TODO slet unødvendige constructors
    //TODO lav implementer interface?
    //TODO calculateDaysUntilDone() - ryk til service?

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private String location;        // Location where the task is performed.
    private String assignedTo;      // Person to whom the task is assigned.
    private int subProjectId;       // ID of the subproject this task is associated with.

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    // FULL constructor with all fields
    public Task(int id, String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                double totalEstHours, Status status, double actualPrice, String location, int subProjectId, String assignedTo) {
        super(id, name, description, createdDate, startDate, endDate, totalEstHours, status, actualPrice);
        this.location = location;
        this.assignedTo = assignedTo;
        this.subProjectId = subProjectId;
    }

    //Constructor with no projectID, totalEstHours, actualPrice
    public Task(int id, String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                Status status, String location, String assignedTo, int subProjectId) {
        super(id, name, description, createdDate, startDate, endDate, status);
        this.location = location;
        this.assignedTo = assignedTo;
        this.subProjectId = subProjectId;
    }

    //Constructor with no ID
    public Task(String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                double totalEstHours, Status status, double actualPrice, String location, String assignedTo, int subProjectId) {
        super(name, description, createdDate, startDate, endDate, totalEstHours, status, actualPrice);
        this.location = location;
        this.assignedTo = assignedTo;
        this.subProjectId = subProjectId;
    }

    //Constructor with no ID, projectID
    public Task(String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                      double totalEstHours, Status status, double actualPrice, String location, String assignedTo) {
        super(name, description, createdDate, startDate, endDate, totalEstHours, status, actualPrice);
        this.location = location;
        this.assignedTo = assignedTo;
    }

    public Task() {
    }

    //***GETTER METHODS***----------------------------------------------------------------------------------------------
    public String getLocation() {
        return location;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public int getSubProjectId() {
        return subProjectId;
    }

    //***SETTER METHODS***----------------------------------------------------------------------------------------------
    public void setLocation(String location) {
        this.location = location;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setSubProjectId(int subProjectId) {
        this.subProjectId = subProjectId;
    }

    //***TO STRING METHOD***--------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return super.toString() +
                "Location: " + location +
                "\nAssigned to: " + assignedTo +
                "\nSubproject ID: " + subProjectId;
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
