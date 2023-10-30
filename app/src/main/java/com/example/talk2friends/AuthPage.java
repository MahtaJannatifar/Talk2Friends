package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.example.talk2friends.databinding.ActivityAuthPageBinding;

public class AuthPage extends AppCompatActivity {
    FirebaseDatabase root;
    DatabaseReference CodeReference;
    private Button verButton;
    private int verCode;
    private String userEmail;
    private ActivityAuthPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        binding = ActivityAuthPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        root = FirebaseDatabase.getInstance();
        //topic
        CodeReference = root.getReference("verificationCode");

        // Get email of the user from the intent
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("email");

        // SYNC verCode to the current key in the firebase directory matching user email
        CodeReference.child(userEmail).child("verificationCode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    verCode = dataSnapshot.getValue(Integer.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        binding.verButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText verCodeInput = findViewById(R.id.verCodeEntered);
                int inputCode = Integer.parseInt(verCodeInput.getText().toString());

                if (inputCode == verCode) {
                    // If they match, then push to firebase directory key "verified" and set it to true
                    CodeReference.child(userEmail).child("verified").setValue(true);
                    Toast.makeText(AuthPage.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                } else {
                    // else set it to false.
                    CodeReference.child(userEmail).child("verified").setValue(false);
                    Toast.makeText(AuthPage.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
