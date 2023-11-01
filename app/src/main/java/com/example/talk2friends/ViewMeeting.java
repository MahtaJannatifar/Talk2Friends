package com.example.talk2friends;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.talk2friends.databinding.ActivityViewMeetingBinding;
import com.google.firebase.database.ValueEventListener;

public class ViewMeeting extends AppCompatActivity {

     Button rsvpButton;
     Button participantListButton;
     DatabaseReference referenceTopic;
     TextView TopicAnswer;
     TextView DateAnswer;
     TextView StartTimeAnswer;
     TextView EndTimeAsnwer;
     TextView LocationAnswer;
     FirebaseDatabase root;
    private DatabaseReference meetingReference;
    String meetingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);
        rsvpButton = findViewById(R.id.GoRSVP);
        participantListButton =  findViewById(R.id.participantListButton);
        TopicAnswer = findViewById(R.id.TopicAnswer);
        DateAnswer=findViewById(R.id.DateAnswer);
        StartTimeAnswer=findViewById(R.id.StartTimeAnswer);
        EndTimeAsnwer=findViewById(R.id.EndTimeAsnwer);
        LocationAnswer=findViewById(R.id.LocationAnswer);
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String startTime = intent.getStringExtra("startTime");
        String endTime = intent.getStringExtra("endTime");
        String location = intent.getStringExtra("location");
        meetingID = intent.getStringExtra("meetingID");
        String topicForMeeting = intent.getStringExtra("topic");
        TopicAnswer.setText(topicForMeeting);
        DateAnswer.setText(date);
        StartTimeAnswer.setText(startTime);
        EndTimeAsnwer.setText(endTime);
        LocationAnswer.setText(location);
    }
    public void onBackPressed(View view) {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onClickGoRSVP(View view){
        Intent intent = new Intent(this, RSVPorCancel.class);
        intent.putExtra("meetingID", meetingID);
        startActivity(intent);
    }
    public void onClickListOfParticipants(View view){
        Intent intent = new Intent(this, ViewListOfParticipants.class);
        intent.putExtra("meetingID", meetingID);
        startActivity(intent);
    }

}
