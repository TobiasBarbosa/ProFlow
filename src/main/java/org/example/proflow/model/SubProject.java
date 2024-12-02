package org.example.proflow.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class SubProject extends Project{ //TODO lav om til subklasse

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private int projectId;        // Parent project ID
    private String assignedTo;    // Assigned employee/team
    private List<Task> tasks = new ArrayList<>(); // List of associated tasks

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    // Full constructor
    public SubProject(int id, String name, String description, LocalDate startDate, LocalDate endDate,
                      Status status, double budget, int projectId, String assignedTo) {
        super(id, name, description, startDate, endDate, status, budget); // Call parent constructor
        this.projectId = projectId;
        this.assignedTo = assignedTo;

        this.totalEstHours = calculateTotalEstHoursForSubProject(); // Use SubProject's method
        this.actualPrice = calculateActualPriceForSubProject(); // Use SubProject's method
    }

    // Constructor without ID
    public SubProject(String name, String description, LocalDate startDate, LocalDate endDate, Status status,
                      double budget, int projectId, String assignedTo) {
        super(name, description, startDate, endDate, status, budget); // Call parent constructor
        this.projectId = projectId;
        this.assignedTo = assignedTo;

        this.totalEstHours = calculateTotalEstHoursForSubProject(); // Use SubProject's method
        this.actualPrice = calculateActualPriceForSubProject();
    }

    // Constructor without projectId
    public SubProject(String name, String description, LocalDate startDate, LocalDate endDate, Status status, String assignedTo, double budget) {
        super(name, description, startDate, endDate, status, budget); // Call parent constructor
        this.assignedTo = assignedTo;

        this.totalEstHours = calculateTotalEstHoursForSubProject(); // Use SubProject's method
        this.actualPrice = calculateActualPriceForSubProject();
    }

    // Default constructor
    public SubProject() {
    }

    //***GETTER METHODS***----------------------------------------------------------------------------------------------
    public int getProjectId() {
        return projectId;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    //***SETTER METHODS***----------------------------------------------------------------------------------------------
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    //***UTILITY METHODS***---------------------------------------------------------------------------------------------
    public double calculateActualPriceForSubProject(){
        double actualPrice = 0;
        for (Task t : tasks){
            actualPrice += t.getTaskPrice();
        }
        return actualPrice;
    }

    public double calculateTotalEstHoursForSubProject(){
        double totalEstHours = 0;
        for (Task t : tasks){
            totalEstHours += t.getTotalEstHours();
        }
        return totalEstHours;
    }

    //***TO STRING METHOD***--------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "\nSubProject ID: " + getId() +
                "\nSubProject name: " + getName() +
                "\nDescription: " + getDescription()+
                "\nCreated date: " + getCreatedDate() +
                "\nStart date: " + getStartDate() +
                "\nEnd date=" + getEndDate() +
                "\nTotal task Duration (hour): " + actualPrice +
                "\nStatus: " + getStatus().getDisplayStatus() +
                "\nProject ID: " + projectId +
                "\nAssigned to: " + (assignedTo != null ? assignedTo : "Not assigned") +
                "\nBudget: " + getBudget();
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
