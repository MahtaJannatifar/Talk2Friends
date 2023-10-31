package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class InviteActivity extends AppCompatActivity {

    private String inviteeEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
    }

    public void sendInvite(View view){
        Auth auth = new Auth();
        EditText emailEditText = (EditText) findViewById(R.id.emailText);
        inviteeEmail = emailEditText.getText().toString();
        Invitation invitation = new Invitation(auth, inviteeEmail, this);
        String code = invitation.getVerificationCode();
        String meetingId = "";
        invitation.sendInvitationEmail(meetingId,new Invitation.EmailCallback() {
            @Override
            public void onEmailSent(boolean isSuccessful) {
                if (isSuccessful) {
                    Toast.makeText(InviteActivity.this, "Email sent successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InviteActivity.this, "Failed to send the email!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void goBack(View view){
        Intent intent = new Intent(this, ViewMeeting.class);
        startActivity(intent);
    }
}