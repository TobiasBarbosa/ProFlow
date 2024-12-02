package org.example.proflow.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class Task {

    //^^^EXAM QUESTION^^^-----------------------------------------------------------------------------------------------
    //Arveforhold: Hvorfor nedarver den her klasse ikke SubProject // eller bliver en subklasse?
    //CreatedDate - Hvorfor har vi valgt at lave createdDate final - problematikker?
    //Duplikering af kode af caculateDaysUntilDone...kunne man have genbrugt Projects metode (som er den samme)???

    //***TO DO***-------------------------------------------------------------------------------------------------------
    //TODO lav createdDate attribute final

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private int id;                      // Task ID, unique identifier for the task.
    private String name;                 // Name of the task.
    private String description;          // Description of the task.
    private String location;             // Location where the task is performed.
    private LocalDate createdDate; // final ensures this date cannot be changed once assigned.
    private LocalDate startDate;         // The start date of the task.
    private LocalDate endDate;           // The end date of the task.
    private double totalEstHours;        // Estimated hours to complete the task.
    private Status status;               // The current status of the task.
    private int subProjectId;            // ID of the subproject this task is associated with.
    private String assignedTo;           // Person to whom the task is assigned.
    private double taskPrice;            // Price for completing the task.

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    // FULL constructor with all fields
    public Task(int id, String name, String description, String location,LocalDate createdDate, LocalDate startDate, LocalDate endDate, double totalEstHours, Status status, int subProjectId, String assignedTo, double taskPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        setCreatedDate(createdDate); // Set date once at object creation
        setStartDate(startDate);
        setEndDate(endDate);
        calculateDaysUntilDone(startDate, endDate); // Calculate days from start to end date.
        setTotalEstHours(totalEstHours);
        this.status = status;
        this.subProjectId = subProjectId;
        this.assignedTo = assignedTo;
        setTaskPrice(taskPrice);
    }

    // Constructor without the task ID, but all other fields
    public Task(String name, String description, String location, LocalDate createdDate, LocalDate startDate, LocalDate endDate, double totalEstHours, Status status, int subProjectId, String assignedTo, double taskPrice) {
        this.name = name;
        this.description = description;
        this.location = location;
        setCreatedDate(createdDate); // Set only once during object creation
        setStartDate(startDate);
        setEndDate(endDate);
        calculateDaysUntilDone(startDate, endDate);
        setTotalEstHours(totalEstHours);
        this.status = status;
        this.subProjectId = subProjectId;
        this.assignedTo = assignedTo;
        setTaskPrice(taskPrice);
    }

    // Constructor without subProjectId
    public Task(String name, String description, String location, LocalDate createdDate, LocalDate startDate, LocalDate endDate, double totalEstHours, Status status, String assignedTo, double taskPrice) {
        this.name = name;
        this.description = description;
        this.location = location;
        setCreatedDate(createdDate); // Set only once during object creation
        setStartDate(startDate);
        setEndDate(endDate);
        calculateDaysUntilDone(startDate, endDate);
        setTotalEstHours(totalEstHours);
        this.status = status;
        this.assignedTo = assignedTo;
        setTaskPrice(taskPrice);
    }

    public Task(){
    }

    //TODO how to make default constructor when attributes are final?

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

    public String getLocation() {
        return location;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getTotalEstHours() {
        return totalEstHours;
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

    public double getTaskPrice() {
        return taskPrice;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCreatedDate(LocalDate createdDate) {
        if(endDate == null){
            throw new IllegalArgumentException("Created date cannot be null");
        }
        this.createdDate = createdDate;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null.");
        }
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null.");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
        this.endDate = endDate;
    }

    public void setTotalEstHours(double totalEstHours) {
        if (totalEstHours < 0) {
            throw new IllegalArgumentException("Hourly duration cannot be less than 0");
        }
        this.totalEstHours = totalEstHours;
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

    public void setTaskPrice(double taskPrice) {
        if (taskPrice < 0) {
            throw new IllegalArgumentException("Task price cannot be less than 0");
        }
        this.taskPrice = taskPrice;
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    public int calculateDaysUntilDone(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    //***TO STRING METHOD***--------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "\nTask ID: " + id +
                "\nTask name: " + name +
                "\nDescription: " + description +
                "\nLocation: " + location +
                "\nCreated date: " + createdDate +
                "\nStart date: " + startDate +
                "\nEnd date=" + endDate +
                "\nDays until finished: " + calculateDaysUntilDone(startDate,endDate) +
                "\nDuration in hours: " + totalEstHours +
                "\nStatus: " + status.getDisplayStatus() +
                "\nSubproject ID: " + subProjectId +
                "\nAssigned to: " + (assignedTo != null ? assignedTo : "Not assigned") +
                "\nTask price: " + taskPrice;
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
