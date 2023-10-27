package com.example.talk2friends;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;


import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.InPersonButton);
        button.setOnClickListener(this::onClickInPerson);
        Button button2 = findViewById(R.id.onlineMeetingButton);
        button2.setOnClickListener(this::onClickOnline);
        Button button3 = findViewById(R.id.createProfileButton);
        button3.setOnClickListener(this::onClickCreateProfile);
    }
    public void onClickInPerson(View v){
        Button InPersonButton = findViewById(R.id.InPersonButton);
        Intent intent = new Intent(this, InPersonMeeting.class);
        startActivity(intent);
    }
    public void onClickOnline(View v){
        Button onlineButton = findViewById(R.id.onlineMeetingButton);
        Intent intent = new Intent(this, OnlineMeeting.class);
        startActivity(intent);
    }
    public void onClickViewMeeting(View v){
        Button viewMeeting = findViewById(R.id.ViewMeetingButton);
        Intent intent = new Intent(this, ViewMeeting.class);
        startActivity(intent);

        Auth auth = new Auth();
        // TODO: please change this email with the user email
        User user = new User("jannatif@usc.edu");
        Invitation invitation = new Invitation(auth, user.getEmail(), this);
        // to access and verify verification code
        String code = invitation.getVerificationCode();
        String meetingId = "1234";
        // TODO: right now meetingID is doing nothing in the sendInvitationEmail function, so this
        //  can be used to verify users for sign up too, it will email them the code
        //  make the UI to accept the verification code & compare it
        invitation.sendInvitationEmail(meetingId);

    }
    public void onClickCreateProfile(View v){
        Button createprofile = findViewById(R.id.createProfileButton);
        Intent intent = new Intent(this, CreateProfile.class);
        startActivity(intent);
    }

}

//create functions to navigate to online meeting or in person meeting pages

/*
clicked on create new meetings:
when click on create new meetings:
page pop up that you can choose in person or remote meeting
if in person a list of locations pop up and choose location
time of the meeting
choose topic for the meeting
choose date of the meeting
if remote create a zoom link
*/