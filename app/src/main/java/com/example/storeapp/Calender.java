package com.example.storeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Calender extends AppCompatActivity {
    public static String Date;
    CalendarView calendar;
    static EditText dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        calendar = findViewById(R.id.calender1);
        dateView = findViewById(R.id.dateView1);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "-" + (month + 1) + "-" + year;
                dateView.setText(date);
                Date=dateView.getText().toString();
                Toast.makeText(Calender.this, "date:  "+Date, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
}