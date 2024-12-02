package org.example.proflow.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class Task {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private int id;
    private String name;
    private String description;
    private String location;
    private final LocalDate createdDate; // final makes sure that the date will not change once the object is created
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalEstHours;
    private Status status;
    private int subProjectId;
    private String assignedTo;
    private double taskPrice;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public Task(int id, String name, String description, String location, LocalDate startDate, LocalDate endDate, double totalEstHours, Status status, int subProjectId, String assignedTo, double taskPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.createdDate = LocalDate.now(); // Set only once during object creation
        setStartDate(startDate);
        setEndDate(endDate);
        calculateDaysUntilDone(startDate, endDate);
        setTotalEstHours(totalEstHours);
        this.status = status;
        this.subProjectId = subProjectId;
        this.assignedTo = assignedTo;
        setTaskPrice(taskPrice);
    }

    public Task(String name, String description, String location, LocalDate startDate, LocalDate endDate, double totalEstHours, Status status, int subProjectId, String assignedTo, double taskPrice) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.createdDate = LocalDate.now(); // Set only once during object creation
        this.startDate = startDate;
        setEndDate(endDate);
        calculateDaysUntilDone(startDate, endDate);
        setTotalEstHours(totalEstHours);
        this.status = status;
        this.subProjectId = subProjectId;
        this.assignedTo = assignedTo;
        setTaskPrice(taskPrice);
    }

    public Task(String name, String description, String location, LocalDate startDate, LocalDate endDate, double totalEstHours, Status status, String assignedTo, double taskPrice) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.createdDate = LocalDate.now(); // Set only once during object creation
        this.startDate = startDate;
//        setEndDate(endDate,subProjectId);
        this.endDate = endDate;
        calculateDaysUntilDone(startDate, endDate);
        setTotalEstHours(totalEstHours);
        this.status = status;
        this.assignedTo = assignedTo;
        setTaskPrice(taskPrice);
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

    //TODO do we need a setter for createdDate? kan man ikke når attribut er final...

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
    private int calculateDaysUntilDone(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        // Calculate the difference in days
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return (int) days; // Cast to int and returnn
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
                "\nDays until finished: " + calculateDaysUntilDone(startDate,endDate) + // what to do here?
                "\nDuration in hours: " + totalEstHours +
                "\nStatus: " + status.getDisplayStatus() +
                "\nSubproject ID: " + subProjectId +
                "\nAssigned to: " + (assignedTo != null ? assignedTo : "Not assigned") +
                "\nTask price: " + taskPrice;
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
