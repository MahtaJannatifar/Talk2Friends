package com.example.talk2friends;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.widget.ListView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.AdapterView;
public class PublicMeeting extends AppCompatActivity {
     ListView MeetingList;
     ArrayAdapter<String> arrayAdapter;
     ArrayList<String> topicList = new ArrayList<>();
     DatabaseReference reference;
     FirebaseDatabase root;
    private ListView listView;

    private Map<String, publicMeetingClass> meetingDetailsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_public_meeting);
        MeetingList = findViewById(R.id.MeetingList);
        MeetingList.setClickable(true);
        // Initialize the class-level arrayAdapter
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, topicList);
        MeetingList.setAdapter(arrayAdapter);
        listView = (ListView) findViewById(R. id.MeetingList) ;
        listView. setAdapter(arrayAdapter);
        reference = FirebaseDatabase.getInstance().getReference().child("meetings");
        // Change the reference path to your database structure
        reference.addValueEventListener(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    publicMeetingClass details = snapshot.getValue(publicMeetingClass.class);
                    if (details != null) {
                        String topic = details.getTopic();
                        meetingDetailsMap.put(topic, details);
                    }
                }

                // Update the ListView
                topicList.clear();
                topicList.addAll(meetingDetailsMap.keySet());
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ErrorFireBase", "Firebase Database Error: " + error.getMessage());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTopic = topicList.get(position);
                publicMeetingClass selectedMeeting = meetingDetailsMap.get(selectedTopic);
                // access to all details of the selected meeting
                String date = selectedMeeting.getDate();
                String startTime = selectedMeeting.getStartTime();
                String endTime = selectedMeeting.getEndTime();
                String location = selectedMeeting.getLocation();
                String MeetingID = selectedMeeting.getId();
                String topicForMeeting = selectedMeeting.getTopic();
                Intent intent = new Intent(PublicMeeting.this, ViewMeeting.class);

                // Add the meeting details as extras to the intent
                intent.putExtra("date", date);
                intent.putExtra("startTime", startTime);
                intent.putExtra("endTime", endTime);
                intent.putExtra("location", location);
                intent.putExtra("meetingID", MeetingID);
                intent.putExtra("topic",topicForMeeting);
                // Start the ViewMeeting activity
                startActivity(intent);
            }
        });
    }
    public void onClickCreateMeeting (View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onClickViewProfile (View v){
        Intent intent = new Intent(this, CreateProfile.class);
        startActivity(intent);
    }
}