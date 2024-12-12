package org.example.proflow.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class Project {

    //^^^EXAM QUESTIONS^^^----------------------------------------------------------------------------------------------
    //Hvorfor er den her klasse ikke abstract?

    //***TO DO***-------------------------------------------------------------------------------------------------------
    //TODO slet unødvendige constructors
    //TODO lav createdDate final
    //TODO lav default value 0 - på dem som skal have det?
    //TODO calculateDaysUntilDone() - ryk til service?
    //TODO calculateTotalEstHours() - ryk til service?
    //TODO calculateActualPrice() - ryk til service?

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private int id;                       // Project ID
    private String name;                  // Project name
    private String description;           // Project description
    private LocalDate createdDate;        // Date the project was created
    private LocalDate startDate;          // Start date of the project
    private LocalDate endDate;            // End date of the project
    protected double totalEstHours;       // Total estimated hours for the project (protected so SubProject kan tilgå variable i underklasse)
    private Status status;                // Project status
    private double budget;                // Budget for the project
    protected double actualPrice;         // Actual price spent (protected so SubProject kan tilgå variable i underklasse)

    //***UNIQUE ATTRIBUTES***-------------------------------------------------------------------------------------------
    private int profileId;                // Profile ID associated with the project
    private List<SubProject> subProjects = new ArrayList<>(); // List of associated sub-projects

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    // Full constructor with all attributes
    public Project(int id, String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                   Status status, double budget, int profileId) {
        this.id = id;
        this.name = name;
        this.description = description;
        setCreatedDate(createdDate);
        setStartDate(startDate);
        setEndDate(endDate);
        this.status = status;
        setBudget(budget);
        this.profileId = profileId;

        calculateDaysUntilDone(startDate, endDate);
        this.totalEstHours = calculateTotalEstHoursForProject();
        this.actualPrice = calculateActualPriceForProject();
    }

    public Project(int id, String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                   Status status, double budget) {
        this.id = id;
        this.name = name;
        this.description = description;
        setCreatedDate(createdDate);
        setStartDate(startDate);
        setEndDate(endDate);
        this.status = status;
        setBudget(budget);
    }

    public Project(String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                   Status status, double budget, int profileId) {
        this.name = name;
        this.description = description;
        setCreatedDate(createdDate);
        setStartDate(startDate);
        setEndDate(endDate);
        calculateDaysUntilDone(startDate, endDate);
        totalEstHours = calculateTotalEstHoursForProject();
        this.status = status;
        setBudget(budget);
        actualPrice = calculateActualPriceForProject();
        this.profileId = profileId;
    }

    public Project(String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                   Status status, double budget) {
        this.name = name;
        this.description = description;
        setCreatedDate(createdDate);
        setStartDate(startDate);
        setEndDate(endDate);
        calculateDaysUntilDone(startDate, endDate);
        totalEstHours = calculateTotalEstHoursForProject();
        this.status = status;
        setBudget(budget);
        actualPrice = calculateActualPriceForProject();
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

    public double getBudget() {
        return budget;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public int getProfileId() {
        return profileId;
    }

    public List<SubProject> getSubProjects() {
        return subProjects;
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
        this.totalEstHours = totalEstHours;
    } // TODO set default value 0?

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setBudget(double budget) {
        if (budget < 0) {
            throw new IllegalArgumentException("budget can not be less than 0");
        }
        this.budget = budget;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    } //TODO set default value 0?

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public void setSubProjects(List<SubProject> subProjects) {
        this.subProjects = subProjects;
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

    public double calculateActualPriceForProject() {
        double actualPrice = 0;
        for (SubProject sp : subProjects) {
            actualPrice += sp.getActualPrice();
        }
        return actualPrice;
    }

    public double calculateTotalEstHoursForProject() {
        double totalEstHours = 0;
        for (SubProject sp : subProjects) {
            totalEstHours += sp.getTotalEstHours();
        }
        return totalEstHours;
    }

    public void addSubProjects(SubProject subProject){
        subProjects.add(subProject);
    }

    public void removeSubProjects(SubProject subProject){
        subProjects.remove(subProject);
    }

    //***TO STRING METHOD***--------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "\nProject ID: " + id +
                "\nProject name: " + name +
                "\nDescription: " + description +
                "\nCreated date: " + createdDate +
                "\nStart date: " + startDate +
                "\nEnd date=" + endDate +
                "\nTotal Est Hours (SubProject): " + totalEstHours +
                "\nStatus: " + status +
                "\nBudget: " + budget +
                "\nActual Price: " + actualPrice +
                "\nProfile ID: " + profileId;
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
