package org.example.proflow.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Project extends ProjectTask{

    //***TO DO***-------------------------------------------------------------------------------------------------------
    //TODO slet unødvendige constructors
    //TODO lav createdDate final
    //TODO lav default value 0 - på dem som skal have det?
    //TODO calculateDaysUntilDone() - ryk til service?
    //TODO calculateTotalEstHours() - ryk til service?
    //TODO calculateActualPrice() - ryk til service?

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private double budget;                // Budget for the project
    private int profileId;                // Profile ID associated with the project

    private List<SubProject> subProjects = new ArrayList<>(); // List of associated sub-projects

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    // Full constructor with all attributes
    public Project(int id, String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                   double totalEstHours, Status status, double actualPrice, double budget, int profileId) {
        super(id, name, description, createdDate, startDate, endDate, totalEstHours, status, actualPrice);
        setBudget(budget);
        this.profileId = profileId;
    }

    //Constructor with no ProfileID, totalEstHrs, actualPrice
    public Project(int id, String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                   Status status, double budget) {
        super(id, name, description, createdDate, startDate, endDate, status);
        setBudget(budget);
    }

    //Constructor with no ID
    public Project(String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate, double totalEstHours, Status status, double actualPrice, double budget, int profileId) {
        super(name, description, createdDate, startDate, endDate, totalEstHours, status, actualPrice);
        setBudget(budget);
        this.profileId = profileId;
    }

    //Constructor with no ID, profileID
    public Project(String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate, double totalEstHours, Status status, double actualPrice, double budget) {
        super(name, description, createdDate, startDate, endDate, totalEstHours, status, actualPrice);
        setBudget(budget);
    }

    //Constructor default
    public Project() {
    }

    //***GETTER METHODS***----------------------------------------------------------------------------------------------
    public double getBudget() {
        return budget;
    }

    public int getProfileId() {
        return profileId;
    }

    public List<SubProject> getSubProjects() {
        return subProjects;
    }

    //***SETTER METHODS***----------------------------------------------------------------------------------------------
    public void setBudget(double budget) {
        if (budget < 0) {
            throw new IllegalArgumentException("budget can not be less than 0");
        }
        this.budget = budget;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public void setSubProjects(List<SubProject> subProjects) {
        this.subProjects = subProjects;
    }

    //***UTILITY METHODS***---------------------------------------------------------------------------------------------
    public double calculateActualPriceForProject() { //TODO move to service?
        double actualPrice = 0;
        for (SubProject sp : subProjects) {
            actualPrice += sp.getActualPrice();
        }
        return actualPrice;
    }

    public double calculateTotalEstHoursForProject() { //TODO move to service?
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
        return super.toString() +
                "Budget: " + budget +
                "Profile ID: " + profileId;
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
