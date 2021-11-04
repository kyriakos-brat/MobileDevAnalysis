package ru.mirea.chernyshev.data_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView textViewInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewInfo = (TextView) findViewById(R.id.textView);
        final Runnable runn1 = new Runnable() {
            @Override
            public void run() {
                textViewInfo.setText("runn1");
            }
        };
        final Runnable runn2 = new Runnable() {
            @Override
            public void run() {
                textViewInfo.setText("runn2");
            }
        };
        final Runnable runn3 = new Runnable() {
            @Override
            public void run() {
                textViewInfo.setText("runn3");
            }
        };
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);
                    TimeUnit.SECONDS.sleep(1);
                    textViewInfo.postDelayed(runn3, 2000);
                    textViewInfo.post(runn2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}