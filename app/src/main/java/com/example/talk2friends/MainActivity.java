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
    public void onClickCreateProfile(View v){
//        Button createprofile = findViewById(R.id.createProfileButton);
//        Intent intent = new Intent(this, PublicMeeting.class);
//        startActivity(intent);
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);

    }

}
