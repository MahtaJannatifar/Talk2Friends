package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.talk2friends.databinding.ActivityViewMeetingBinding;

public class ViewListOfParticipants extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private FirebaseDatabase root;
    ListView participantListView;
    private ActivityViewMeetingBinding binding;
    DatabaseReference reference;
    ArrayList<String> topicList = new ArrayList<>();
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_of_participants);
        participantListView = findViewById(R.id.particiantListView);
        participantListView.setClickable(true);
        root = FirebaseDatabase.getInstance();
        reference = root.getReference("RSVPEmails"); // our key in DB
        listView = (ListView) findViewById(R. id.particiantListView) ;
        final ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,topicList);
        listView. setAdapter (arrayAdapter);
        back  = findViewById(R.id.buttonBack3);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(String.class);
                topicList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void buttonBackPressed(View view){
        Intent intent = new Intent(this, ViewMeeting.class);
        startActivity(intent);
    }
    public void inviteParticipant(View view){
        Intent intent = new Intent(this, InviteActivity.class);
        startActivity(intent);

    }

}