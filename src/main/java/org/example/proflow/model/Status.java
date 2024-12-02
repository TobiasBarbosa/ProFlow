package org.example.proflow.model;

public enum Status {

    //***ENUMS***-------------------------------------------------------------------------------------------------------
    ACTIVE("Active"),
    INACTIVE("Inactive");

    //TODO PO spørgsmål - hvad vil PO have a dropdown valgmuligheder?

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
