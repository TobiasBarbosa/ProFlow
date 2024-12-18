package org.example.proflow.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class SubProject extends ProjectTask {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private double budget;
    private int projectId;        // Parent project ID
    private String assignedTo;    // Assigned employee/team
    private List<Task> tasks = new ArrayList<>(); // List of associated tasks

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    // Full constructor with all attributes
    public SubProject(int id, String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                      double totalEstHours, Status status, double actualPrice, double budget, int projectId, String assignedTo) {
        super(id, name, description, createdDate, startDate, endDate, totalEstHours, status, actualPrice);
        setBudget(budget);
        this.projectId = projectId;
        this.assignedTo = assignedTo;
    }

    //Constructor with no projectID, totalEstHours, actualPrice
    public SubProject(int id, String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                      Status status, double budget, int projectId, String assignedTo) {
        super(id, name, description, createdDate, startDate, endDate, status);
        setBudget(budget);
        this.projectId = projectId;
        this.assignedTo = assignedTo;
    }

    //Constructor with no ID
    public SubProject(String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                      double totalEstHours, Status status, double actualPrice, double budget, int projectId) {
        super(name, description, createdDate, startDate, endDate, totalEstHours, status, actualPrice);
        setBudget(budget);
        this.projectId = projectId;
    }

    //Constructor with no ID, projectID,
    public SubProject(String name, String description, LocalDate createdDate, LocalDate startDate, LocalDate endDate,
                      double totalEstHours, Status status, double actualPrice, double budget, String assignedTo) {
        super(name, description, createdDate, startDate, endDate, totalEstHours, status, actualPrice);
        this.budget = budget;
        this.assignedTo = assignedTo;
    }


    // Default constructor
    public SubProject() {
    }

    //***GETTER METHODS***----------------------------------------------------------------------------------------------
    public double getBudget() {
        return budget;
    }

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
    public void setBudget(double budget) {
        if (budget < 0) {
            throw new IllegalArgumentException("budget can not be less than 0");
        }
        this.budget = budget;
    }

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
    public double calculateActualPriceForSubProject() { //TODO rykkes til service
        double actualPrice = 0;
        for (Task t : tasks) {
            actualPrice += t.getActualPrice();
        }
        return actualPrice;
    }

    public double calculateTotalEstHoursForSubProject() { //TODO rykkes til service
        double totalEstHours = 0;
        for (Task t : tasks) {
            totalEstHours += t.getTotalEstHours();
        }
        return totalEstHours;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void remove(Task task) {
        tasks.remove(task);
    }

    //***TO STRING METHOD***--------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return super.toString() +
                "Budget: " + budget +
                "\nAssigned to: " + assignedTo +
                "\nProject ID: " + projectId;
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
