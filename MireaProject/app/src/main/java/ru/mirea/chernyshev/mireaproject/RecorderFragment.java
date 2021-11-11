package ru.mirea.chernyshev.mireaproject;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecorderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecorderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PERMISSION = 100;
    private String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};
    private boolean isWork = false;

    private Button startRecButton;
    private Button stopRecButton;
    private Button listenRecButton;
    private MediaRecorder mediaRecorder;
    private File audioFile;

    public RecorderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecorderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecorderFragment newInstance(String param1, String param2) {
        RecorderFragment fragment = new RecorderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        isWork = hasPermissions(getContext(), PERMISSIONS);
        if (!isWork) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_PERMISSION);
        }
        //
        mediaRecorder = new MediaRecorder();
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (REQUEST_CODE_PERMISSION == requestCode) {
            if (permissions.length > 0) {
                isWork = PackageManager.PERMISSION_GRANTED == grantResults[0];
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_recorder, container, false);
        startRecButton = (Button) fragmentView.findViewById(R.id.buttonStartRec);
        stopRecButton = (Button) fragmentView.findViewById(R.id.buttonStopRec);
        listenRecButton = (Button) fragmentView.findViewById(R.id.buttonListenRec);

        stopRecButton.setEnabled(false);
        listenRecButton.setEnabled(false);

        startRecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startRecButton.setEnabled(false);
                    stopRecButton.setEnabled(true);
                    stopRecButton.requestFocus();
                    startRecording();
                } catch (IOException e) {
                    Log.e(TAG, "Caught IO exception: " + e.getMessage());
                }
            }
        });
        stopRecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecButton.setEnabled(true);
                stopRecButton.setEnabled(false);
                listenRecButton.setEnabled(true);
                startRecButton.requestFocus();
                stopRecording();
                processAudioFile();
            }
        });
        listenRecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PlayerService.class);
                intent.putExtra(PlayerService.PLAYER_URI_FILE, Uri.fromFile(audioFile));
                getActivity().startService(intent);
            }
        });
        //TODO: bit lazy to create stop listen button
        return fragmentView;
    }

    private void startRecording() throws IOException {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d(TAG, "SD-card success");
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            if (null == audioFile) {
                audioFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                        "mireaRec.3gp");
            }
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(getContext(), "Recording started!", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            //QQ: why not checking boolean var like isLive instead? What possible faults on
            //calling methods below to non-recording mediaRecorder?
            Log.d(TAG, "stopRecording");
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            Toast.makeText(getContext(), "You are not recording now.", Toast.LENGTH_SHORT).show();
        }
    }

    private void processAudioFile() {
        Log.d(TAG, "processAudioFile");
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio_" + audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());
        ContentResolver contentResolver = getActivity().getContentResolver();
        Log.d(TAG, "audioFile: " + audioFile.canRead());

        Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(baseUri, values);

        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
    }



}