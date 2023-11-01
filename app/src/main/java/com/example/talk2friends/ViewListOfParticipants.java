package com.example.talk2friends;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
public class ViewListOfParticipants extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseDatabase root;
    ListView particiantListView;
    private Button back;
    String meetingID;
    ArrayList<String> participantList = new ArrayList<>();
     ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_of_participants);
        particiantListView = findViewById(R.id.particiantListView); // Initialize the ListView
        root = FirebaseDatabase.getInstance();
        DatabaseReference reference = root.getReference("Meetings");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, participantList);
        particiantListView.setAdapter(arrayAdapter); // Set the adapter to the ListView
        // Passing the meeting ID to this activity so we can access the list of participants
        meetingID = getIntent().getStringExtra("meetingID");
        DatabaseReference meetingReference = FirebaseDatabase.getInstance()
                .getReference("meetings")
                .child(meetingID)
                .child("Participants");

        meetingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("ViewListOfParticipants", "here : " + meetingID);
                    String participant = snapshot.getValue(String.class);
                    participantList.add(participant);
                }
                arrayAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }
    public void buttonBackPressed(View view){
        Intent intent = new Intent(this, publicMeetingClass.class);
        startActivity(intent);
    }
}
