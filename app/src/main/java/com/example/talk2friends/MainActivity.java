package com.example.talk2friends;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Auth auth = new Auth();
        User user = new User("jannatif@usc.edu");
        Invitation invitation = new Invitation(auth, user.getEmail(), this);
        String meetingId = "1234";
        invitation.sendInvitationEmail(meetingId);
    }
}