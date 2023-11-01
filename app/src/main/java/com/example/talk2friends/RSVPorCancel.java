package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RSVPorCancel extends AppCompatActivity {
    private FirebaseDatabase root;
    private Button RSVPButton;
    private EditText EmailForRSVP;
    private TextView ErrorMessageText;
    private Button CancelRSVP;
    private EditText EmailForCancelation;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_rsvpor_cancel);
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        RSVPButton = findViewById(R.id.RSVPButton);
        EmailForRSVP = findViewById(R.id.EmailForRSVP);
        CancelRSVP = findViewById(R.id.CancelRSVP);
        ErrorMessageText = findViewById(R.id.ErrorMessageText);
        ErrorMessageText.setText("If you want to cancel reservation, enter your email down below");
        EmailForCancelation = findViewById(R.id.EmailForCancelation);
        reference = FirebaseDatabase.getInstance().getReference().child("meetings");
        String meetingID = getIntent().getStringExtra("meetingID");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Check if meetingID is not null
                if (meetingID != null) {
                    DatabaseReference meetingReference = reference.child(meetingID);
                    RSVPButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String newEmail = EmailForRSVP.getText().toString().trim();
                            if (!newEmail.isEmpty()) {
                                DatabaseReference participantsReference = meetingReference.child("Participants");
                                // if the email already exists in the participants list don't add again
                                Query query = participantsReference.orderByValue().equalTo(newEmail);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (!dataSnapshot.exists()) {
                                            // Email doesn't exist, so add it
                                            participantsReference.push().setValue(newEmail);
                                            ErrorMessageText.setText("RSVP successful");
                                        } else {
                                            //if already exist show error
                                            ErrorMessageText.setText("Error: Email already exists, if you want to cancel your reservation, enter your email down below");
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        // Handle any error if necessary
                                    }
                                });
                            }
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //for cancelling RSVP
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Check if meetingID is not null
                if (meetingID != null) {
                    DatabaseReference meetingReference = reference.child(meetingID);
                    CancelRSVP.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String newEmail = EmailForCancelation.getText().toString().trim();
                            if (!newEmail.isEmpty()) {
                                DatabaseReference participantsReference = meetingReference.child("Participants");
                                // if the email already exists in the participants list then we can remove it if not show error
                                Query query = participantsReference.orderByValue().equalTo(newEmail);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            // Email exists, so we can remove it from the database
                                            for (DataSnapshot emailSnapshot : dataSnapshot.getChildren()) {
                                                emailSnapshot.getRef().removeValue();
                                            }
                                            ErrorMessageText.setText("RSVP canceled for email: " + newEmail);
                                        } else {
                                            ErrorMessageText.setText("Error: Email not found in the RSVP list, you can RSVP above");
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onBackPressed (View view){
        Intent intent = new Intent(this, PublicMeeting.class);
        startActivity(intent);
    }
}



