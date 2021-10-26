package ru.mirea.chernyshev.practice3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TimeReceiver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_receiver);

        String receivedTimeString = "Receieved time: ";
        receivedTimeString += (String) getIntent().getSerializableExtra("dateTime");

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(receivedTimeString);


    }
}