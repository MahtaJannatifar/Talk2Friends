package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import com.example.talk2friends.databinding.ActivitySignUpBinding;

import android.view.View;
import android.widget.Button;


public class SignUp extends AppCompatActivity {

    FirebaseDatabase root;
    DatabaseReference EmailReference;
    DatabaseReference PasswordReference;
    DatabaseReference VerificationCodeReference;
    private Button SignUp;
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseApp.initializeApp(this);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        //topic
        EmailReference = root.getReference("email");
        PasswordReference = root.getReference("password");
        VerificationCodeReference = root.getReference("verificationCode");

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the button is clicked, the information inputted will be saved in Firebase DB
                DatabaseReference newEmailReference = EmailReference.push();
                DatabaseReference newPasswordReference = PasswordReference.push();
                DatabaseReference newVerificationReference = VerificationCodeReference.push();

                String email = binding.signUpEmail.getText().toString();
                newEmailReference.setValue(email);
                newPasswordReference.setValue(binding.signUpPassword.getText().toString());
                newVerificationReference.setValue(100);

                Intent intent = new Intent(view.getContext(), AuthPage.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

    }
}