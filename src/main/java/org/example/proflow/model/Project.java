package org.example.proflow.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class Project {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private int id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int daysUntilDone;
    private Status status;
    private int profileId;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public Project(int id, String name, String description, LocalDate startDate, LocalDate endDate, Status status, int profileId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        setEndDate(endDate);
        daysUntilDone = calculateDaysUntilDone(startDate,endDate);
        this.status = status;
        this.profileId = profileId;
    }

    public Project(String name, String description, LocalDate startDate, LocalDate endDate, Status status, int profileId) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        setEndDate(endDate);
        daysUntilDone = calculateDaysUntilDone(startDate,endDate);
        this.status = status;
        this.profileId = profileId;
    }

    public Project(String name, String description, LocalDate startDate, LocalDate endDate, Status status) {
        this.name = name;
        this.description = description;
        this.startDate=startDate;
        setEndDate(endDate);
        daysUntilDone = calculateDaysUntilDone(startDate,endDate);
        this.status = status;
    }

    public Project() {
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

    public int getProfileId() {
        return profileId;
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
    } // skal den her bruges eller er calculateDaysUntilDone en set metode?

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    private int calculateDaysUntilDone(LocalDate startDate, LocalDate endDate){
        // TODO kan datoer være null?
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        // Calculate the difference in days
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return (int) days; // Cast to int and return
    }

    //TODO vi skal kalkulere timer
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
                "\nProfile ID: "          + profileId                 ;
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
