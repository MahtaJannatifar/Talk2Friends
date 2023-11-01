package com.example.talk2friends;

public class User {
    private String userID;
    private String name;
    private String lastName;
    private String affiliation;
    private String birthdate;
    private String email;
    private String password;
    private String interest1;
    private String interest2;
    private String interest3;

    public User() {
        // Default constructor required for Firebase
    }
    public User(String userID,String email, String password, String firstNameAnswerString, String lastNameAnswerString, String birthDateAnswerString, String affiliationAnswerString, String interest1AnswerString, String interest2AnswerString, String interest3AnswerString) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = firstNameAnswerString;
        this.lastName = lastNameAnswerString;
        this.birthdate = birthDateAnswerString;
        this.affiliation = affiliationAnswerString;
        this.interest1 = interest1AnswerString;
        this.interest2 = interest2AnswerString;
        this.interest3 = interest3AnswerString;
    }

    public String getUserID(){
        return userID;
    }
    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getInterest1() {
        return interest1;
    }

    public String getInterest2() {
        return interest2;
    }

    public String getInterest3() {
        return interest3;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInterest1(String interest1) {
        this.interest1 = interest1;
    }

    public void setInterest2(String interest2) {
        this.interest2 = interest2;
    }

    public void setInterest3(String interest3) {
        this.interest3 = interest3;
    }
}

