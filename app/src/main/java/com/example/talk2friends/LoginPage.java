package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("Users");
    }

    public void onLoginClick(View view) {
        EditText emailInput = findViewById(R.id.uEmail);
        EditText passwordInput = findViewById(R.id.uPass);

        String email = emailInput.getText().toString();
        String enteredPassword = passwordInput.getText().toString();

        // Look up password for user associated with that email in Firebase database
        usersRef.child(email.replace(".", ",")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String storedPassword = dataSnapshot.child("password").getValue(String.class);
                    if (enteredPassword.equals(storedPassword)) {
                        // Passwords match, create auth intent with "email" extra string

                        // TODO: MAHTHA EMAIL SYSTEM SEND AND PUSH VERIFICATION CODE!!s
                        Intent intent = new Intent(LoginPage.this, AuthPage.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    } else {
                        // Wrong password, try again
                        Toast.makeText(LoginPage.this, "Incorrect password, try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginPage.this, "User not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginPage.this, "Database error.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
