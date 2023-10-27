package com.example.talk2friends;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.widget.ListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.example.talk2friends.databinding.ActivityPublicMeetingBinding;
import java.util.ArrayList;
public class PublicMeeting extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private FirebaseDatabase root;
    ListView MeetingList;
    private ActivityPublicMeetingBinding binding;
    DatabaseReference reference;
    DatabaseReference reference2;

    ArrayList<String> topicList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_meeting);
        MeetingList = findViewById(R.id.MeetingList);
        MeetingList.setClickable(true);
        root = FirebaseDatabase.getInstance();
        reference = root.getReference("topic"); // our key in DB
        reference2 = root.getReference("topicOnline");
        listView = (ListView) findViewById(R. id.MeetingList) ;
        final ArrayAdapter<String>arrayAdapter =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,topicList);
        listView. setAdapter (arrayAdapter);
        //adding in person meetings to the list
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
        //adding online meetings to the list
        reference2.addChildEventListener(new ChildEventListener() {
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = topicList.get(position);
                Intent intent = new Intent(PublicMeeting.this, ViewMeeting.class);
                intent.putExtra("selectedItem", selectedItem);
                startActivity(intent);
            }
        });
    }
    public void onClickCreateMeeting(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onClickViewProfile(View v){
        Intent intent = new Intent(this, CreateProfile.class);
        startActivity(intent);
    }
//    public void onClickSeeFriendsList(){
//
//    }
}
