package ru.mirea.chernyshev.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    private final String TAG = MainActivity.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FloatingActionButton fab;
    //QQ: Is it logical correct? Cause we using same name in two fragments, and for not creating
    //string constant twice we using shared preferences
    static final String HISTORY_FILE;
    static final String HISTORY_NO_FILE;
    private SharedPreferences preferences;

    static {
        HISTORY_FILE = "history_filename";
        HISTORY_NO_FILE = "history_no_file";
    }

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO: file creation?

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_history, container, false);
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        fab = fragmentView.findViewById(R.id.fabHistory);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryDialogFragment dialogFragment = new HistoryDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "HistoryDialog");
            }
        });
        //TODO: reading from file string by string inside RecycleView
        if (preferences.contains(HISTORY_FILE)) {
            FileInputStream fin = null;
            try {
                fin = getActivity().openFileInput(preferences.getString(HISTORY_FILE, HISTORY_NO_FILE));
                int streamAvailable = fin.available();
                //QQ: no need for checking negative value returned from available(), right?
                byte[] bytes = new byte[streamAvailable];
                if (streamAvailable != fin.read(bytes)) {
                    Log.d(TAG, "Not all available stream data read!");
                }
            } catch (IOException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    if (fin != null) {
                        fin.close();
                    }
                } catch (IOException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getActivity(), "No history file!", Toast.LENGTH_SHORT).show();
        }
        return fragmentView;
    }
}