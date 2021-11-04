package ru.mirea.chernyshev.looper;

import android.annotation.SuppressLint;
;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {
    private String TAG = MyLooper.class.getSimpleName();
    private int number = 0;
    Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        Log.d(TAG, "run");
        Looper.prepare();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, number + ": " + "My age: " + msg.getData().getString("Age"));
                Log.d(TAG, "My occupation: " + msg.getData().getString("Occupation"));
                number++;
            }
        };
        Looper.loop();
    }
}
