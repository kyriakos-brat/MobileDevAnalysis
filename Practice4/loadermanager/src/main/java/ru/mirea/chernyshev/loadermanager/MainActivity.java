package ru.mirea.chernyshev.loadermanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {
    public final String TAG = this.getClass().getSimpleName();
    private int LoaderID = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();
        bundle.putString(MyLoader.ARG_WORD, "mirea");
        getSupportLoaderManager().initLoader(LoaderID, bundle, this);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(TAG, "onLoaderReset");
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        if (LoaderID == i) {
            Toast.makeText(this, "onCreateLoader: " + i, Toast.LENGTH_SHORT).show();
            return new MyLoader(this, bundle);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        if (LoaderID == loader.getId()) {
            Log.d(TAG, "onLoadFinished " + s);
            Toast.makeText(this, "onLoadFinished: " + s, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NewApi")
    public void onShuffleClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String text = editText.getEditableText().toString();
        List<String> letters = Arrays.asList(text.split(""));
        Collections.shuffle(letters);
        Toast.makeText(this, "Shuffled text: " + String.join("", letters), Toast.LENGTH_LONG).show();
    }
}