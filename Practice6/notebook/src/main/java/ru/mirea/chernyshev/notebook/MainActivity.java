package ru.mirea.chernyshev.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private EditText tvFileName;
    private EditText tvText;
    private TextView tvLast;
    private Button saveButton;
    //for last file loading
    final String LAST_FILE_NAME = "last_file_name";
    private final String NO_LAST_FILE = "no_last_file";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFileName = findViewById(R.id.editTextFileName);
        tvText = findViewById(R.id.editText);
        tvLast = findViewById(R.id.textViewLast);
        saveButton = findViewById(R.id.buttonSave);
        //
        preferences = getPreferences(MODE_PRIVATE);

        //QQ: what if user reinstall app? Should we use this some global preferences instead?
        //I think the local file will be deleted with app, right?
        String lastFilename = preferences.getString(LAST_FILE_NAME, NO_LAST_FILE);
        if (!lastFilename.equals(NO_LAST_FILE)) {
            //TODO: reading from file + internalFileStorage module was missed
        } else {
            tvLast.setText("Last file not found!");
        }
    }

    public void onSaveButtonClick(View view) {
        String filename = tvFileName.getText().toString() + ".txt";
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(tvText.getText().toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally { //QQ: correct way instead of calling this inside try-block?
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        //
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LAST_FILE_NAME, filename);
        editor.apply();
        Toast.makeText(this, "File with name " + filename + " saved successfully!", Toast.LENGTH_SHORT).show();
    }
}