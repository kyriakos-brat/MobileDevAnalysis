package ru.mirea.chernyshev.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreferencesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreferencesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText editTextName;
    private TextView textViewGreet;
    private Button saveNameButton;
    private Button greetButton;

    final String GREET_NAME = "GREET_NAME";
    final String NO_NAME = "__NO_NAME";
    private SharedPreferences preferences;

    public PreferencesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment preferencesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreferencesFragment newInstance(String param1, String param2) {
        PreferencesFragment fragment = new PreferencesFragment();
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
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_preferences, container, false);
        editTextName = fragmentView.findViewById(R.id.editTextSaveName);
        textViewGreet = fragmentView.findViewById(R.id.textViewGreet);
        saveNameButton = fragmentView.findViewById(R.id.buttonSaveName);
        greetButton = fragmentView.findViewById(R.id.buttonGreet);
        saveNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                //
                editor.putString(GREET_NAME, editTextName.getText().toString());
                editor.apply();

                Toast.makeText(getContext(), "Name saved!", Toast.LENGTH_SHORT).show();
            }
        });
        greetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String greetString;
                if (preferences.contains(GREET_NAME)) {
                    String name = preferences.getString(GREET_NAME, NO_NAME);
                    greetString = "Greetings, " + name + "!";
                } else {
                    greetString = "Greetings!";
                }
                textViewGreet.setText(greetString);
            }
        });
        return fragmentView;
    }
}