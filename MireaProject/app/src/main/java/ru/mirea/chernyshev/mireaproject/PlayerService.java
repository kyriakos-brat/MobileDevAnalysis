package ru.mirea.chernyshev.mireaproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer;
    public static final String PLAYER_URI_FILE = "PLAYER_URI_FILE";
    public static final String PLAYER_RES_FILE = "PLAYER_RES_FILE";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if (!bundle.isEmpty()) {
            int bundleInt = bundle.getInt(PLAYER_RES_FILE, -1);
            if (bundleInt != -1) {
                mediaPlayer = MediaPlayer.create(this, bundleInt);
            } else {
                //QQ: working?
                Uri fileUri = bundle.getParcelable(PLAYER_URI_FILE);
                mediaPlayer = MediaPlayer.create(this, fileUri);
            }
        }
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }
}