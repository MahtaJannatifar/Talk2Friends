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

    public User(String userID, String email, String password, String firstNameAnswerString, String lastNameAnswerString, String birthDateAnswerString, String affiliationAnswerString, String interest1AnswerString, String interest2AnswerString, String interest3AnswerString) {
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

    public String getUserID() {
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
}

//
//public class User {
//    // We need the following things for a user to have:
//    // name
//    // id
//    // email
//    // age
//    // interest list
//    // isNative
//    // EditUser
//    // FriendsList
//    private String name;
//    private String userId;
//    private String email;
//    private int age;
//    private String[] interests;
//    private Boolean isNative;
//    private String[] friends;
//
//    // A user is created using only their USC emails
//    public User(String email) {
//        this.email = email;
//    }
//    // Constructor with optional fields for changing user profile
//    public User(String name, String userId, String email, int age, String[] interests, Boolean isNative, String[] friends) {
//        this(email); // Call the constructor
//        this.name = name;
//        this.age = age;
//        this.userId = userId;
//        this.interests = interests;
//        this.isNative = isNative;
//        this.friends = friends;
//    }
//    public String getName(){
//        return name;
//    }
//    public void setName(String name){
//        this.name = name;
//    }
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//
//    public String getPassword() {
//        return password;
//    }
//
//    public String getInterest1() {
//        return interest1;
//    }
//
//    public String getInterest2() {
//        return interest2;
//    }
//
//    public String getInterest3() {
//        return interest3;
//    }
//
//    // Setters
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public void setAffiliation(String affiliation) {
//        this.affiliation = affiliation;
//    }
//
//    public void setBirthdate(String birthdate) {
//        this.birthdate = birthdate;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setInterest1(String interest1) {
//        this.interest1 = interest1;
//    }
//
//    public void setInterest2(String interest2) {
//        this.interest2 = interest2;
//    }
//
//    public void setInterest3(String interest3) {
//        this.interest3 = interest3;
//    }
//}
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//    public String[] getInterests() {
//        return interests;
//    }
//
//    public void setInterests(String[] interests) {
//        this.interests = interests;
//    }
//
//    public Boolean getIsNative() {
//        return isNative;
//    }
//
//    public void setIsNative(Boolean isNative) {
//        this.isNative = isNative;
//    }
//
//    public String[] getFriends() {
//        return friends;
//    }
//
//    public void setFriends(String[] friends) {
//        this.friends = friends;
//    }
//    // optional fields to change any of them the user wants to change
//    public void editUser(Optional<String> name, Optional<Integer> age, Optional<Boolean> isNative, Optional<String[]> friends, Optional<String[]> interests) {
//        name.ifPresent(this::setName);
//        age.ifPresent(this::setAge);
//        isNative.ifPresent(this::setIsNative);
//        // user can add/remove their friends
//        friends.ifPresent(this::setFriends);
//        // user can change their interests
//        interests.ifPresent(this::setInterests);
//    }
//}
