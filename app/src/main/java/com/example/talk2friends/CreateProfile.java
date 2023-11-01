package com.example.talk2friends;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.content.Intent;
import android.view.View;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Calendar;


//Edit profile
//sign in

public class CreateProfile extends AppCompatActivity {
    private Button BirthDateAnswer;
    private DatePickerDialog datePickerDialog;
    private Button doneButton;
    private EditText firstNameAnswer;
    private EditText LastNameAnswer;
    private Button birthDateAnswer;
    private Spinner affiliationAnswer;
    private Spinner Interest1Answer;
    private Spinner Interest2Answer;
    private Spinner Interest3Answer;
    DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        FirebaseApp.initializeApp(this);
        doneButton = findViewById(R.id.doneButton);
        firstNameAnswer = findViewById(R.id.FirstNameAsnwer);
        LastNameAnswer = findViewById(R.id.LastNameAnswer);
        birthDateAnswer = findViewById(R.id.BirthDateAnswer);
        affiliationAnswer = findViewById(R.id.affiliationAnswer);
        Interest1Answer = findViewById(R.id.Interest1Answer);
        Interest2Answer = findViewById(R.id.interest2Answer);
        Interest3Answer = findViewById(R.id.interest3Answer);
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstNameAnswerString = String.valueOf(firstNameAnswer.getText());
                String LastNameAnswerString = LastNameAnswer.getText().toString();
                String birthDateAnswerString = birthDateAnswer.getText().toString();
                String affiliationAnswerString =  affiliationAnswer.getSelectedItem().toString();
                String Interest1AnswerString =  Interest1Answer.getSelectedItem().toString();
                String Interest2AnswerString = Interest2Answer.getSelectedItem().toString();
                String Interest3AnswerString = Interest3Answer.getSelectedItem().toString();
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");
                String password = intent.getStringExtra("password");
                DatabaseReference newUserRef = userRef.push();
                String UserID = (newUserRef).getKey();
                User user = new User(UserID,email, password,firstNameAnswerString, LastNameAnswerString, birthDateAnswerString, affiliationAnswerString, Interest1AnswerString,Interest2AnswerString,Interest3AnswerString);
                newUserRef.setValue(user);
                newUserRef.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateProfile.this, "user profile create", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent2= new Intent(CreateProfile.this, MainActivity.class);
                startActivity(intent2);
            }
        });
        initDatePicker();
        BirthDateAnswer = findViewById(R.id.BirthDateAnswer);
        BirthDateAnswer.setText(getTodaysDate());
        affiliationChoice();
        interest1();
        interest2();
        interest3();
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                BirthDateAnswer.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }


    public void affiliationChoice() {
        String[] data = {" ", "Native ", "International"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.affiliationAnswer);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                String selectedItem = data[position];
                // Do something with the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing when nothing is selected
            }
        });
    }
    public void interest1() {
        String[] data = {" ", "Yes","No"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.Interest1Answer);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                String selectedItem = data[position];
                // Do something with the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing when nothing is selected
            }
        });
    }
    public void interest2() {
        String[] data = {" ", "Yes ", "No"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.interest2Answer);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                String selectedItem = data[position];
                // Do something with the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing when nothing is selected
            }
        });
    }
    public void interest3() {
        String[] data = {" ", "Yes ", "No"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.interest3Answer);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                String selectedItem = data[position];
                // Do something with the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing when nothing is selected
            }
        });
    }
}