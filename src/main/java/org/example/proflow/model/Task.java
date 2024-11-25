package org.example.proflow.model;

import java.time.LocalDate;

public class Task extends SubProject{
    //TODO hvad skal være det unikke i Task?
    //TODO skal Task have et projectID også?

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    String uniqueVariabel;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public Task(int id, String name, String description, LocalDate startDate, LocalDate endDate, Status status, int projectId, String assignedTo, String uniqueVariabel) {
        super                (id,
                              name,
                              description,
                              startDate,
                              endDate,
                              status,
                              projectId,
                              assignedTo);
        this.uniqueVariabel = uniqueVariabel;
    }

    public Task(String name, String description, LocalDate startDate, LocalDate endDate, Status status, int projectId, String assignedTo, String uniqueVariabel) {
        super                (name,
                              description,
                              startDate,
                              endDate,
                              status,
                              projectId,
                              assignedTo);
        this.uniqueVariabel = uniqueVariabel;
    }

    public Task() {
    }

    //***GETTER METHODS***----------------------------------------------------------------------------------------------
    public String getUniqueVariabel() {
        return uniqueVariabel;
    }

    //***SETTER METHODS***----------------------------------------------------------------------------------------------
    public void setUniqueVariabel(String uniqueVariabel) {
        this.uniqueVariabel = uniqueVariabel;
    }

    //***TO STRING METHOD***--------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return super.toString() + // Call the `toString` method from the parent `SubProject` class
               "\nUnique variabel: " + uniqueVariabel;
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
