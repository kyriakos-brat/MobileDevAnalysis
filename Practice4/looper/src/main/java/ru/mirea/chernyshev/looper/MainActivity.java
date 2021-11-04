package ru.mirea.chernyshev.looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    MyLooper myLooper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLooper = new MyLooper();
        myLooper.start();
    }

    public void onClick(View view) {
        int chernyshevAge = 22;
        String chernyshevOccupation = "C++ developer";
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("Age", Integer.toString(chernyshevAge));
        bundle.putString("Occupation", chernyshevOccupation);
        msg.setData(bundle);
        if (myLooper != null) {
            try {
                TimeUnit.SECONDS.sleep(chernyshevAge);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myLooper.handler.sendMessage(msg);
        }
    }
}