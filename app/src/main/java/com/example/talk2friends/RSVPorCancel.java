package com.example.talk2friends;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import java.util.Calendar;
import android.widget.ArrayAdapter;
import java.util.Arrays;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.view.KeyEvent;
import com.example.talk2friends.databinding.ActivityRsvporCancelBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.os.Bundle;
public class RSVPorCancel extends AppCompatActivity {
    private ActivityRsvporCancelBinding binding;
    private FirebaseDatabase root;
    private DatabaseReference EmailReference;
    private Button RSVPButton;
    private EditText EmailForRSVP;
    private TextView ErrorMessageText;
    private Button CancelRSVP;
    private EditText EmailForCancelation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        binding = ActivityRsvporCancelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        RSVPButton = findViewById(R.id.RSVPButton);
        EmailForRSVP = findViewById(R.id.EmailForRSVP);
        EmailReference = root.getReference("RSVPEmails");
        ErrorMessageText = findViewById(R.id.ErrorMessageText);
        ErrorMessageText.setText("If you want to cancel reservation, enter you email down below");
        CancelRSVP = findViewById(R.id.CancelRSVP);
        EmailForCancelation = findViewById (R.id.EmailForCancelation);

        binding.RSVPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String newEmail = binding.EmailForRSVP.getText().toString().trim();
                if (!newEmail.isEmpty()) {
                    EmailReference.orderByValue().equalTo(newEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Email already exists in the database
                                ErrorMessageText.setText("Error: Email already exists, if you want to cancel your reservation, enter your email down below");
                            } else {
                                // Email doesn't exist, so add it
                                ErrorMessageText.setText("If you want to cancel reservation, enter you email down below");
                                DatabaseReference newEmailReference = EmailReference.push();
                                newEmailReference.setValue(newEmail);
                                ErrorMessageText.setText("");
                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        binding.CancelRSVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailToRemove = binding.EmailForCancelation.getText().toString().trim();
                if (!emailToRemove.isEmpty()) {
                    // Query the database to find the email entry
                    EmailReference.orderByValue().equalTo(emailToRemove).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot emailSnapshot : dataSnapshot.getChildren()) {
                                    // Get the key of the email to be removed
                                    String emailKey = emailSnapshot.getKey();
                                    if (emailKey != null) {
                                        // Remove the specific email entry by its key
                                        EmailReference.child(emailKey).removeValue();
                                    }
                                    ErrorMessageText.setText("Email removed");
                                    EmailForCancelation.setText("");
                                }
                            } else {
                                // Handle the case where the email doesn't exist in the database
                                ErrorMessageText.setText("Email not found in the database");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle the error here if necessary
                        }
                    });
                }
            }
        });
    }
    public void onBackPressed(View view){
        Intent intent = new Intent(this, PublicMeeting.class);
        startActivity(intent);
    }
}