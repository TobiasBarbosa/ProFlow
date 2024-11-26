package org.example.proflow.model;

import java.time.LocalDate;

public class Task extends SubProject{
    //TODO hvad skal være det unikke i Task?
    //TODO skal Task have et projectID også?

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private String uniqueVariabel;
    private int subProjectId;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public Task(int id, String name, String description, LocalDate startDate, LocalDate endDate, Status status, int projectId, String assignedTo, String uniqueVariabel, int subProjectId) {
        super                (id,
                              name,
                              description,
                              startDate,
                              endDate,
                              status,
                              projectId,
                              assignedTo);
        this.uniqueVariabel = uniqueVariabel;
        this.subProjectId = subProjectId;
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

    public int getSubProjectId() {
        return subProjectId;
    }

    //***SETTER METHODS***----------------------------------------------------------------------------------------------
    public void setUniqueVariabel(String uniqueVariabel) {
        this.uniqueVariabel = uniqueVariabel;
    }


    public void setSubProjectId(int subProjectId) {
        this.subProjectId = subProjectId;
    }

    //***TO STRING METHOD***--------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return super.toString() + // Call the `toString` method from the parent `SubProject` class
               "\nUnique variabel: " + uniqueVariabel;
    }


    //***END***---------------------------------------------------------------------------------------------------------
}
