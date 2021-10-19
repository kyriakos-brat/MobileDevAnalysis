package ru.mirea.chernyshev.dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @NonNull

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowDialog(View view) {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void onOkClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Иду дальше\"! ",
                Toast.LENGTH_SHORT).show();
    }

    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"На паузе\"! ",
                Toast.LENGTH_SHORT).show();
    }

    public void onNegativeClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Нет\"! ",
                Toast.LENGTH_SHORT).show();
    }

    public void onClickShowTimePickerDialog(View view) {
        DialogFragment dialogFragment = new MyTimeDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onClickShowDatePickerDialog(View view) {
        DialogFragment dialogFragment = new MyDateDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onClickShowProgressBar(View view) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        for (int i = 25; i < 101; ++i) {
            progressBar.incrementProgressBy(1);
        }
    }
}