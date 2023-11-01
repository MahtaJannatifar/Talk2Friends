package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.example.talk2friends.databinding.ActivityCreateProfileBinding;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.signUpButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Create a new user account with email and password
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registration successful
                                    FirebaseUser firebaseUser = task.getResult().getUser();
                                    String userId = firebaseUser.getUid();

                                    // Redirect to the profile creation activity and pass email and password as extras
                                    Intent intent = new Intent(SignUp.this, CreateProfile.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("password", password);
                                    startActivity(intent);
                                } else {
                                    // Registration failed
                                    Exception exception = task.getException();
                                    if (exception != null) {
                                        String errorMessage = exception.getMessage();
                                        // Handle the error and display an error message
                                    }
                                }
                            }
                        });
            }
        });
    }
}

//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import android.widget.Toast;
//
//public class SignUp extends AppCompatActivity {
//    private EditText nameEditText;
//    private EditText lastNameEditText;
//    private EditText emailEditText;
//    private EditText passwordEditText;
//    private Button registerButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);
//        emailEditText = findViewById(R.id.emailEditText);
//        passwordEditText = findViewById(R.id.passwordEditText);
//        registerButton = findViewById(R.id.signUpButton);
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = emailEditText.getText().toString();
//                String password = passwordEditText.getText().toString();
//                Intent intent = new Intent(SignUp.this, CreateProfile.class);
//                intent.putExtra("email", email);
//                intent.putExtra("password", password);
//                startActivity(intent);
////                String name = " ";
////                String lastName = " ";
////                String birthDate=" ";
////                String affiliation=" ";
////                String int1=" ";
////                String int2=" ";
////                String int3 = " ";
////                String email = emailEditText.getText().toString();
////                String password = passwordEditText.getText().toString();
////                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
////                firebaseAuth.createUserWithEmailAndPassword(email, password)
////                        .addOnCompleteListener(task -> {
////                            if (task.isSuccessful()) {
////                                // Registration successful
////                                FirebaseUser firebaseUser = task.getResult().getUser();
////                                String userId = firebaseUser.getUid();
////                                User user = new User(userId,email, password,name, lastName, birthDate, affiliation, int1,int2,int3);
////                                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
////                                usersRef.child(userId).setValue(user);
////                                Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();
////                                Intent intent = new Intent(SignUp.this,CreateProfile.class);
////                                intent.putExtra("email", email);
////                                intent.putExtra("password", password);
////                                startActivity(intent);
////                            } else {
////                                // Registration failed
////                                Exception exception = task.getException();
////                                if (exception != null) {
////                                    String errorMessage = exception.getMessage();
////                                    Toast.makeText(SignUp.this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
////                                }
////                            }
////                        });
//            }
//        });
//    }
//}
