package ru.mirea.chernyshev.resultactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private final int REQUEST_CODE = 143;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Log.d(MainActivity.class.getSimpleName(), "data is not NULL!");
            String universityName = data.getStringExtra("universityName");
            setUniversityTextView(universityName);
        }
    }

    private void setUniversityTextView(String universityName) {
        textViewResult.setText(universityName);
    }

    public void onBtnUniversityClick(View view) {
        Log.d(MainActivity.class.getSimpleName(), "Calling click method!");
        Intent intent = new Intent(this, DataActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }
}