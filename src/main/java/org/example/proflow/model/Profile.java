package org.example.proflow.model;

public class Profile {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    public Profile(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Profile(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Profile(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Profile() {
    }

    //***GETTER METHODS***----------------------------------------------------------------------------------------------
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    //***SETTER METHODS***----------------------------------------------------------------------------------------------
    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //***TO STRING METHOD***--------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return  "\nProfile ID: " + id        +
                "\nFirst name: " + firstName +
                "\nLast name: "  + lastName  +
                "\nEmail: "      + email     +
                "\nPassword: "   + password  ;
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
