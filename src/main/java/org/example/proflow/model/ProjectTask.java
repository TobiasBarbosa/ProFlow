package org.example.proflow.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class ProjectTask {

    //TODO automatiser calculate metode for totalEstHours
    //TODO automatiser calculate metode for actualPrice

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private int id;
    private String name;
    private String description;
    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private double totalEstHours;
    private double actualPrice;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    //Full constructor
    public ProjectTask(int id, String name, String description, LocalDate createdDate, LocalDate startDate,
                       LocalDate endDate, double totalEstHours, Status status, double actualPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        setCreatedDate(createdDate);
        setStartDate(startDate);
        setEndDate(endDate);
        calculateDaysUntilDone(startDate,endDate);
        this.status = status;
        this.totalEstHours = totalEstHours; // skal laves om til calculateTotalHours?
        this.actualPrice = actualPrice;     // skal laves om til calculateActualPrice?
    }

    //Constructor with no totalEstHours, actualPrice
    public ProjectTask(int id, String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        setCreatedDate(createdDate);
        setStartDate(startDate);
        setEndDate(endDate);
        calculateDaysUntilDone(startDate,endDate);
        this.status = status;
    }

    //Constructor with no ID, ProfileID, totalEstHours, actualPrice
    public ProjectTask(String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                       double totalEstHours, Status status, double actualPrice) {
        this.name = name;
        this.description = description;
        setCreatedDate(createdDate);
        setStartDate(startDate);
        setEndDate(endDate);
        calculateDaysUntilDone(startDate,endDate);
        this.status = status;
        this.totalEstHours = totalEstHours;
        this.actualPrice = actualPrice;
    }

    //Constructor default
    public ProjectTask(){
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

    public double getActualPrice() {
        return actualPrice;
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

    public void setCreatedDate(LocalDate createdDate) {
        if (createdDate == null) {
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

//    public void setEndDate(LocalDate endDate) {
//        if (endDate == null) {
//            throw new IllegalArgumentException("End date cannot be null.");
//        }
//        if (endDate.isBefore(startDate)) {
//            throw new IllegalArgumentException("End date cannot be before start date.");
//        }
//        this.endDate = endDate;
//    }

    public void setEndDate(LocalDate endDate) {

        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }
        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
        this.endDate = endDate;
    }

    public void setTotalEstHours(double totalEstHours) {
        this.totalEstHours = totalEstHours; // TODO set default value 0?
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice; //TODO set default value 0?
    }

    //***UTILITY METHODS***---------------------------------------------------------------------------------------------
    public int calculateDaysUntilDone(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates must not be null.");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    //***TO STRING METHOD***--------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return  "\n ID: " + id +
                "\nName: " + name +
                "\nDescription: " + description +
                "\nCreated date: " + createdDate +
                "\nStart date: " + startDate +
                "\nEnd date=" + endDate +
                "\nTotal Est Hours" + totalEstHours +
                "\nStatus: " + status +
                "\nActual Price: " + actualPrice ;
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
