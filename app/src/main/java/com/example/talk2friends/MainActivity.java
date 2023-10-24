package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
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