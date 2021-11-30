package ru.mirea.chernyshev.mireaproject;

import static ru.mirea.chernyshev.mireaproject.HistoryFragment.HISTORY_FILE;
import static ru.mirea.chernyshev.mireaproject.HistoryFragment.HISTORY_NO_FILE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HistoryDialogFragment extends DialogFragment {

    Button buttonSave;
    EditText editTextThought;
    private final String HISTORY_FILENAME = "history.txt";
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View dialogView = inflater.inflate(R.layout.dialog_history, container, false);
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (!preferences.contains(HISTORY_FILE)) {
            SharedPreferences.Editor editor= preferences.edit();
            editor.putString(HISTORY_FILE, HISTORY_FILENAME);
            editor.apply();
        }
        buttonSave = dialogView.findViewById(R.id.buttonSaveThought);
        editTextThought = dialogView.findViewById(R.id.editTextThought);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = preferences.getString(HISTORY_FILE, HISTORY_NO_FILE);
                FileOutputStream outputStream = null;
                try {
                    outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE | Context.MODE_APPEND);
                    String stringThought = editTextThought.getText().toString() + '\n';
                    outputStream.write(stringThought.getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                Toast.makeText(getActivity(), "Text saved!", Toast.LENGTH_SHORT).show();
            }
        });
        return dialogView;
    }
}
