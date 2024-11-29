package org.example.proflow.model;

public enum Status {

    //***ENUMS***-------------------------------------------------------------------------------------------------------
    DONE("Done"),
    ALMOST_DONE("Almost Done"),
    DOING("Doing"),
    NOT_STARTED("Not started yet");

    //TODO PO spørgsmål - hvad vil PO have a dropdown valgmuligheder? (PO svar 29/11: aktiv og inaktiv)

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
