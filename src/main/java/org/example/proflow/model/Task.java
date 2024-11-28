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
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private int daysUntilDone;
    private double hourlyDuration;
    private Status status;
    private int subProjectId;
    private String assignedTo;
    private double taskPrice;

    //***ACCESS ATTRIBUTES***-------------------------------------------------------------------------------------------
    private SubProject subProject; // access to class

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public Task(int id, String name, String description, String location, LocalDate startDate, LocalDate endDate, double hourlyDuration, Status status, int subProjectId, String assignedTo, double taskPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        //setEndDate(endDate,subProjectId);
        this.endDate = endDate;
        daysUntilDone = calculateDaysUntilDone(startDate, endDate);
        setHourlyDuration(hourlyDuration);
        this.status = status;
        this.subProjectId = subProjectId;
        this.assignedTo = assignedTo;
        setTaskPrice(taskPrice);
    }

    public Task(String name, String description, String location, LocalDate startDate, LocalDate endDate, double hourlyDuration, Status status, int subProjectId, String assignedTo, double taskPrice) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
//        setEndDate(endDate, subProjectId);
        this.endDate = endDate;
        daysUntilDone = calculateDaysUntilDone(startDate, endDate);
        setHourlyDuration(hourlyDuration);
        this.status = status;
        this.subProjectId = subProjectId;
        this.assignedTo = assignedTo;
        setTaskPrice(taskPrice);
    }

    public Task(String name, String description, String location, LocalDate startDate, LocalDate endDate, double hourlyDuration, Status status, String assignedTo, double taskPrice) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
//        setEndDate(endDate,subProjectId);
        this.endDate = endDate;
        daysUntilDone = calculateDaysUntilDone(startDate, endDate);
        setHourlyDuration(hourlyDuration);
        this.status = status;
        this.assignedTo = assignedTo;
        setTaskPrice(taskPrice);
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

    public String getLocation() {
        return location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getHourlyDuration() {
        return hourlyDuration;
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

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
//TODO FIX the calculation;

    //    public void setEndDate(LocalDate endDate, int subProjectId) {
//        if(endDate.isBefore(startDate)){
//            throw new IllegalArgumentException("End date cannot be before start date.");
//        } if(endDate.isAfter(subProject.findSubProjectById(subProjectId).getEndDate())){
//            throw new IllegalArgumentException("End date cannot be after SubProject end date");
//        } else {
//            this.endDate = endDate;
//        }
//    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setHourlyDuration(double hourlyDuration) {
        if (hourlyDuration < 0) {
            throw new IllegalArgumentException("Hourly duration cannot be less than 0");
        } else {
            this.hourlyDuration = hourlyDuration;
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

    public void setTaskPrice(double taskPrice) {
        if (taskPrice < 0) {
            throw new IllegalArgumentException("Task price cannot be less than 0");
        } else {
            this.taskPrice = taskPrice;
        }
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
                "\nStart date: " + startDate +
                "\nEnd date=" + endDate +
                "\nDays until finished: " + daysUntilDone +
                "\nDuration in hours: " + hourlyDuration +
                "\nStatus: " + status.getDisplayStatus() +
                "\nSubproject ID: " + subProjectId +
                "\nAssigned to: " + (assignedTo != null ? assignedTo : "Not assigned") +
                "\nTask price: " + taskPrice;
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
