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
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.example.talk2friends.databinding.ActivityCreateProfileBinding;
import android.widget.Button;
import android.widget.Spinner;
import java.util.Calendar;
public class CreateProfile extends AppCompatActivity {
    FirebaseDatabase root;
    DatabaseReference FirstNameReference;
    DatabaseReference LastNameReference;
    DatabaseReference birthDateReference;
    DatabaseReference AffiliationReference;
    DatabaseReference InterestsReference;
    DatabaseReference Interest1Reference;
    DatabaseReference Interest2Reference;
    DatabaseReference Interest3Reference;

    private Button BirthDateAnswer;
    private ActivityCreateProfileBinding binding;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        FirebaseApp.initializeApp(this);
        binding = ActivityCreateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        //topic
        FirstNameReference = root.getReference("firstName");
        LastNameReference = root.getReference("lastName");
        AffiliationReference = root.getReference("affiliation");
        InterestsReference = root.getReference("interest");
        birthDateReference = root.getReference("Birthdate");
        Interest1Reference = root.getReference("int1");
        Interest2Reference = root.getReference("int2");
        Interest3Reference = root.getReference("int3");
        binding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when clicked on crate button, the information inputted will be saved in firebase DB
                DatabaseReference newNameReference = FirstNameReference.push();
                DatabaseReference newLastReference = LastNameReference.push();
                DatabaseReference newBirthDateReference = birthDateReference.push();
                DatabaseReference newAffiliateReference = AffiliationReference.push();
                DatabaseReference newInterest1Reference = Interest1Reference.push();
                DatabaseReference newInterest2Reference = Interest2Reference.push();
                DatabaseReference newInterest3Reference = Interest3Reference.push();



                newNameReference.setValue(binding.FirstNameAsnwer.getText().toString());
                newLastReference.setValue(binding.LastNameAnswer.getText().toString());
                newAffiliateReference.setValue(binding.affiliationAnswer.getSelectedItem().toString());
                newInterest1Reference.setValue(binding.Interest1Answer.getSelectedItem().toString());
                newInterest2Reference.setValue(binding.interest2Answer.getSelectedItem().toString());
                newInterest3Reference.setValue(binding.interest3Answer.getSelectedItem().toString());
                newBirthDateReference.setValue(binding.BirthDateAnswer.getText().toString());


                Intent intent = new Intent(view.getContext(), PublicMeeting.class);
                startActivity(intent);

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