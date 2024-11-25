package org.example.proflow.model;

import java.time.LocalDate;

public class Project {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private int id;
    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int daysUntilDone;
    private Status status;
    private int profileId;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public Project(int id, String projectName, String description, LocalDate startDate, LocalDate endDate, int daysUntilDone, Status status, int profileId) {
        this.id = id;
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysUntilDone = daysUntilDone;
        this.status = status;
        this.profileId = profileId;
    }

    public Project(String projectName, String description, LocalDate startDate, LocalDate endDate, int daysUntilDone, Status status, int profileId) {
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysUntilDone = daysUntilDone;
        this.status = status;
        this.profileId = profileId;
    }


}
