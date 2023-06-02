package com.devhub.lms_javatest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScheduleActivity extends AppCompatActivity  {

    private CalendarView calendarView;
    private EditText editText;

    private EditText editTextStime;

    private EditText editTextEtime;
    private String stringDateSelected;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Button startButton = findViewById(R.id.button6);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action when button is clicked
                Intent intent = new Intent(ScheduleActivity.this, DailyScheduleActivity.class);
                startActivity(intent);
            }
        });


        //Calendar View and schedule store firebase
        calendarView = findViewById(R.id.calendarView);
        editText = findViewById(R.id.editTextName);
        editTextStime = findViewById(R.id.editTextStart);
        editTextEtime = findViewById(R.id.editTextEnd);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                stringDateSelected = Integer.toString(i) + Integer.toString(i1 + 1) + Integer.toString(i2);
                calendarClicked();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");




        //Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_schedule);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_dashboard:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_module:
                    startActivity(new Intent(getApplicationContext(), ModuleGalleryActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_schedule:

                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(), ExamActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });
    }

    private void calendarClicked() {
        databaseReference.child(stringDateSelected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    editText.setText(snapshot.child("event").getValue(String.class));
                    editTextStime.setText(snapshot.child("startTime").getValue(String.class));
                    editTextEtime.setText(snapshot.child("endTime").getValue(String.class));
                } else {
                    editText.setText("");
                    editTextStime.setText("");
                    editTextEtime.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void buttonSaveEvent(View view) {
        String eventText = editText.getText().toString();
        String startTimeText = editTextStime.getText().toString();
        String endTimeText = editTextEtime.getText().toString();

        DatabaseReference eventReference = databaseReference.child(stringDateSelected);
        eventReference.child("event").setValue(eventText);
        eventReference.child("startTime").setValue(startTimeText);
        eventReference.child("endTime").setValue(endTimeText);
    }

}