package org.example.proflow.model;

import org.example.proflow.repository.SubProjectRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class SubProject {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private int id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int daysUntilDone;
    private double totalTaskDurationHourly;
    private Status status;
    private int projectId;
    private String assignedTo;
    private double budget;

    //***ACCESS ATTRIBUTES***-------------------------------------------------------------------------------------------
    private SubProjectRepository subProjectRepository;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public SubProject(int id, String name, String description, LocalDate startDate, LocalDate endDate, double totalTaskDurationHourly, Status status, int projectId, String assignedTo, double budget) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        setEndDate(endDate);
        daysUntilDone = calculateDaysUntilDone(startDate,endDate);
        this.totalTaskDurationHourly = totalTaskDurationHourly;
        this.status = status;
        this.projectId = projectId;
        this.assignedTo = assignedTo;
        setBudget(budget);
    }

    // Constructor without `id` for new subprojects
    public SubProject(String name, String description, LocalDate startDate, LocalDate endDate, double totalTaskDurationHourly, Status status, int projectId, String assignedTo, double budget) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        daysUntilDone = calculateDaysUntilDone(startDate,endDate);
        this.totalTaskDurationHourly = totalTaskDurationHourly;
        this.status = status;
        this.projectId = projectId;
        this.assignedTo = assignedTo;
        setBudget(budget);
    }

    public SubProject(String name, String description, LocalDate startDate, LocalDate endDate, double totalTaskDurationHourly, Status status, String assignedTo, double budget) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        daysUntilDone = calculateDaysUntilDone(startDate,endDate);
        this.totalTaskDurationHourly = totalTaskDurationHourly;
        this.status = status;
        this.assignedTo = assignedTo;
        setBudget(budget);
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

    public double getTotalTaskDurationHourly() {
        return totalTaskDurationHourly;
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

    public double getBudget() {
        return budget;
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

    public void setTotalTaskDurationHourly(double totalTaskDurationHourly) {
        this.totalTaskDurationHourly = totalTaskDurationHourly;
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

    public void setBudget(double budget) {
        if (budget < 0){
            throw new IllegalArgumentException("Budget cannot be less than 0");
        } else {
            this.budget = budget;
        }
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

    public SubProject findSubProjectById(int id){
        SubProject subProject = null;
        for(SubProject s : subProjectRepository.getAllSubProjects()){
            if(s.getId() == id){
                subProject = s;
            } else {
                throw new IllegalArgumentException("No SubProject with this ID");
            }
        }
        return subProject;
    }

    //***TO STRING METHOD***--------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return  "\nSubProject ID: "              + id                        +
                "\nSubProject name: "            + name                      +
                "\nDescription: "                + description               +
                "\nStart date: "                 + startDate                 +
                "\nEnd date="                    + endDate                   +
                "\nDays until finished: "        + daysUntilDone             +
                "\nTotal task Duration (hour): " + totalTaskDurationHourly   +
                "\nStatus: "                     + status.getDisplayStatus() +
                "\nProject ID: "                 + projectId                 +
                "\nAssigned to: "                + (assignedTo != null ? assignedTo : "Not assigned") +
                "\nBudget: "                     + budget                    ;

    }


    //***END***---------------------------------------------------------------------------------------------------------
}
