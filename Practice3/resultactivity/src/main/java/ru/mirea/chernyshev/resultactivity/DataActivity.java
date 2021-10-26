package ru.mirea.chernyshev.resultactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class DataActivity extends AppCompatActivity {
    private EditText universityEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        universityEditText = (EditText) findViewById(R.id.universityEditText);
    }

    public void onBtnUniEnteredByClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("universityName", universityEditText.getText().toString());
        setResult(RESULT_OK, intent);
        Log.d(DataActivity.class.getSimpleName(), "Setting result OK!");
        finish();
    }
}