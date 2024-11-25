package org.example.proflow.model;

import java.time.LocalDate;

public class SubProject extends Project{

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private String assignedTo;
    // private int projectId; projectId er unikt here men vi behøver ikke at oprette en variabel da vi bare tager den fra superklassen

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public SubProject(int id, String name, String description, LocalDate startDate, LocalDate endDate, Status status, int projectId, String assignedTo) {
        super            (id,
                          name,
                          description,
                          startDate,
                          endDate,
                          status,
                          projectId);
        this.assignedTo = assignedTo;
    }

    // Constructor without `id` for new subprojects
    public SubProject(String name, String description, LocalDate startDate, LocalDate endDate, Status status, int projectId, String assignedTo) {
        super            (name,
                          description,
                          startDate,
                          endDate,
                          status,
                          projectId);
        this.assignedTo = assignedTo;
    }

    // Default constructor
    public SubProject() {
    }

    //***GETTER METHODS***----------------------------------------------------------------------------------------------
    public String getAssignedTo() {
        return assignedTo;
    }

    //***SETTER METHODS***----------------------------------------------------------------------------------------------
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    //***TO STRING METHOD***--------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return  super.toString() + // Call the `toString` method from the parent `Project` class
                "\nAssigned to: " + (assignedTo != null ? assignedTo : "Not assigned");
        //TODO hvordan håndterer vi navngivet på parentID?
    }


    //***END***---------------------------------------------------------------------------------------------------------
}
