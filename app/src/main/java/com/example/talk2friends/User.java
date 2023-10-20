package com.example.talk2friends;

import java.util.Optional;

public class User {
    // We need the following things for a user to have:
    // name
    // id
    // email
    // age
    // interest list
    // isNative
    // EditUser
    // FriendsList
    private String name;
    private String userId;
    private String email;
    private int age;
    private String[] interests;
    private Boolean isNative;
    private String[] friends;

    // A user is created using only their USC emails
    public User(String email) {
        this.email = email;
    }
    // Constructor with optional fields for changing user profile
    public User(String name, String userId, String email, int age, String[] interests, Boolean isNative, String[] friends) {
        this(email); // Call the constructor
        this.name = name;
        this.age = age;
        this.userId = userId;
        this.interests = interests;
        this.isNative = isNative;
        this.friends = friends;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public String[] getInterests() {
        return interests;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    public Boolean getIsNative() {
        return isNative;
    }

    public void setIsNative(Boolean isNative) {
        this.isNative = isNative;
    }

    public String[] getFriends() {
        return friends;
    }

    public void setFriends(String[] friends) {
        this.friends = friends;
    }
    // optional fields to change any of them the user wants to change
    public void editUser(Optional<String> name, Optional<Integer> age, Optional<Boolean> isNative, Optional<String[]> friends, Optional<String[]> interests) {
        name.ifPresent(this::setName);
        age.ifPresent(this::setAge);
        isNative.ifPresent(this::setIsNative);
        // user can add/remove their friends
        friends.ifPresent(this::setFriends);
        // user can change their interests
        interests.ifPresent(this::setInterests);
    }
}
