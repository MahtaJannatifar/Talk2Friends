package com.example.talk2friends;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class ViewMeeting extends AppCompatActivity {

    private Button rsvpButton;
    private Button participantListButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);
        rsvpButton = findViewById(R.id.GoRSVP);
        participantListButton =  findViewById(R.id.participantListButton);
    }

    public void onBackPressed(View view) {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void onClickGoRSVP(View view){
        Intent intent = new Intent(this, RSVPorCancel.class);
        startActivity(intent);
    }
    public void onClickListOfParticipants(View view){
        Intent intent = new Intent(this, ViewListOfParticipants.class);
        startActivity(intent);
    }

}
