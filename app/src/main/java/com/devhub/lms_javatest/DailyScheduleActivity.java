package com.devhub.lms_javatest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DailyScheduleActivity extends AppCompatActivity  {

    private ListView listView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);

        // Navigate button
        Button startButton = findViewById(R.id.module01back);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action when button is clicked
                Intent intent = new Intent(DailyScheduleActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });


        //listview of data
        listView = findViewById(R.id.listView);
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");

        // Retrieve data from Firebase and populate the ListView
        retrieveData();
    }

    private void retrieveData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> eventDataList = new ArrayList<>();

                for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                    String event = dateSnapshot.child("event").getValue(String.class);
                    String startTime = dateSnapshot.child("startTime").getValue(String.class);
                    String endTime = dateSnapshot.child("endTime").getValue(String.class);

                    String eventData = "Event: " + event + "\nStart Time: " + startTime + "\nEnd Time: " + endTime;
                    eventDataList.add(eventData);
                }

                // Create an ArrayAdapter to populate the ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DailyScheduleActivity.this,
                        android.R.layout.simple_list_item_1, eventDataList);

                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });

    }

}
