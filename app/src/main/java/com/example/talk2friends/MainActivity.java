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
}