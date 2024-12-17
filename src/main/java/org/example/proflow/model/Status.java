package org.example.proflow.model;

public enum Status {

    //***EXAM QUESTIONS***----------------------------------------------------------------------------------------------
    //Hvorfor har vi valgt enum fremfor en boolean (især når der kun er to konstanter)?

    //***TO DO***-------------------------------------------------------------------------------------------------------
    //TODO PO spørgsmål - hvad vil PO have a dropdown valgmuligheder?

    //***ENUMS***-------------------------------------------------------------------------------------------------------
    ACTIVE("Active"),
    INACTIVE("Inactive");

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    final String displayStatus;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    Status(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    //***GETTER METHODS***----------------------------------------------------------------------------------------------
    public String getDisplayStatus() {
        return displayStatus;
    }


    //***END***---------------------------------------------------------------------------------------------------------
}
