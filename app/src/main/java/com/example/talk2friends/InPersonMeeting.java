package com.example.talk2friends;
import androidx.appcompat.app.AppCompatActivity;
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
import android.content.Intent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.example.talk2friends.databinding.ActivityInPersonMeetingBinding;

public class InPersonMeeting extends AppCompatActivity {

    private Spinner locationAnswer;

    //firebase
    FirebaseDatabase root;
    DatabaseReference topicReference;
    DatabaseReference dateReference;
    DatabaseReference startTimeReference;
    DatabaseReference EndTimeReference;
    DatabaseReference locationReference;

    private ActivityInPersonMeetingBinding binding;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Spinner startTime;
    private Spinner endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        binding = ActivityInPersonMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        //topic
        topicReference = root.getReference("topic");
        dateReference = root.getReference("date");
        startTimeReference = root.getReference("startTime");
        EndTimeReference = root.getReference("endTime");
        locationReference = root.getReference("location");
        // Set a click listener for a button
        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when clicked on crate button, the information inputted will be saved in firebase DB
                DatabaseReference newTopicReference = topicReference.push();
                DatabaseReference newDateReference = dateReference.push();
                DatabaseReference newStartTimeReference = startTimeReference.push();
                DatabaseReference newEndTimeReference = EndTimeReference.push();
                DatabaseReference newLocationReference = locationReference.push();

                // Save the user message to the Firebase database under the generated keys
                newTopicReference.setValue(binding.topicAnswer2.getText().toString());
                newDateReference.setValue(binding.datePickerButton2.getText().toString());
                newStartTimeReference.setValue(binding.startTimeSpinner2.getSelectedItem().toString());
                newEndTimeReference.setValue(binding.endTimeSpinner2.getSelectedItem().toString());
                newLocationReference.setValue(binding.locationAnswer.getSelectedItem().toString());


                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);

            }
        });
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton2);
        dateButton.setText(getTodaysDate());
        startTime = findViewById(R.id.startTimeSpinner2);
        endTime = findViewById(R.id.endTimeSpinner2);
        chooseStartTime(); // Initialize the start time Spinner
        EditText editText = findViewById(R.id.topicAnswer2);
        locationAnswer = findViewById(R.id.locationAnswer);
        locationOptions();
        editText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    private void chooseStartTime() {
        // Create an array of time options with 15-minute intervals
        String[] timeOptions = generateTimeOptionsForStart();
        // Create an ArrayAdapter for the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapter for the Spinner
        startTime.setAdapter(adapter);
        Spinner start = findViewById(R.id.startTimeSpinner2);
        final String[] selectedStartTime = new String[1];
        Spinner end = findViewById(R.id.endTimeSpinner2);
        final String[] selectedEndTime = new String[1];
        start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item's text from the Spinner
                selectedStartTime[0] = (String) parent.getItemAtPosition(position);
                // Update the showStartTime TextView with the selected start time
//                showtime.setText(selectedStartTime[0]);
                compareStartAndEnd(selectedStartTime[0], selectedEndTime[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle when nothing is selected (if needed)
            }
        });
        ArrayAdapter<String> adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeOptions);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endTime.setAdapter(adapt);
        end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item's text from the Spinner
                selectedEndTime[0] = (String) parent.getItemAtPosition(position);
                // Update the showStartTime TextView with the selected start time
//                showEnd.setText(selectedEndTime[0]);
                compareStartAndEnd(selectedStartTime[0], selectedEndTime[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle when nothing is selected (if needed)
            }
        });
    }

    private int convertTimeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    // Function to compare start and end times as integers
    private void compareStartAndEnd(String startTime, String endTime) {
        if (startTime != null && endTime != null) {
            int startMinutes = convertTimeToMinutes(startTime);
            int endMinutes = convertTimeToMinutes(endTime);
            if (startMinutes > endMinutes) {
                // Start time is later than end time
                // Handle the case where the start time is later than the end time
                TextView showtime = findViewById(R.id.showStartTime2);
                showtime.setText("start is bigger or equal to  end");

            } else if (startMinutes < endMinutes) {
                TextView showtime = findViewById(R.id.showStartTime2);
                showtime.setText("");
            }
        }
    }

    private String[] generateTimeOptionsForStart() {
        // Create an array of time options with 15-minute intervals for the start time
        String[] timeOptions = new String[49]; // 12-hour day from 8 AM to 8 PM
        for (int i = 0; i < timeOptions.length; i++) {
            int hour = (i + 32) / 4; // Start at 8 AM (i.e., 8:00, 8:15, 8:30, ...)
            int minute = (i % 4) * 15;
            timeOptions[i] = String.format("%02d:%02d", hour, minute);
        }
        return timeOptions;
    }

    private String[] generateTimeOptionsForEnd(String selectedStartTime) {
        // Create an array of time options for the end time based on the selected start time
        String[] timeOptions = new String[49]; // 12-hour day from 8 AM to 8 PM
        int startIndex = Arrays.asList(generateTimeOptionsForStart()).indexOf(selectedStartTime);
        for (int i = startIndex; i < timeOptions.length; i++) {
            int hour = (i + 32) / 4; // Start at the selected start hour
            int minute = (i % 4) * 15;
            timeOptions[i - startIndex] = String.format("%02d:%02d", hour, minute);
        }
        return timeOptions;
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.DAY_OF_MONTH, 1);
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";
        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void locationOptions() {
        String[] data = {" ", "RTH cafe", "Leavy Library", "Dohny  Library", "RTC"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.locationAnswer);
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

    public void onBackPressed(View view) {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
//    public void onClickCreateButton(View view){
//        Button createB = findViewById(R.id.createButton);
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }

//    public void create(@NonNull View view, Bundle savedInstanceState) {
//        root = FirebaseDatabase.getInstance();
//        reference = root.getReference("ourKey");
//        binding.createButton.setOnClickListener(new View.OnClickListener() {
//            public void onClickCreateButton(View view) {
//                /* when next button is clicked, save the user message to DB */
//                /* first, call Firebase to the (key, value) we want to edit */
//
//                /* then, save the value */
//                reference.setValue(binding.topicAnswer2.getText().toString());
//            }
//        });
//    }

}



