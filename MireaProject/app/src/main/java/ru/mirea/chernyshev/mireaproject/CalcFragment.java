package ru.mirea.chernyshev.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalcFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalcFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalcFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalcFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalcFragment newInstance(String param1, String param2) {
        CalcFragment fragment = new CalcFragment();
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
        View fragmentView = inflater.inflate(R.layout.fragment_calc, container, false);
        Button sumBtn = (Button) fragmentView.findViewById(R.id.btnGetSum);
        sumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText1 = (EditText) fragmentView.findViewById(R.id.editTextNum1);
                EditText editText2 = (EditText) fragmentView.findViewById(R.id.editTextNum2);
                int num1 = Integer.parseInt(editText1.getText().toString());
                int num2 = Integer.parseInt(editText2.getText().toString());
                int sum = num1 + num2;
                TextView textView = (TextView) fragmentView.findViewById(R.id.textView5);
                textView.setText("Sum of numbers is: " + sum);
            }
        });


        return fragmentView;
    }


    @Override
    public void onClick(View view) {
//                String str = view.findViewById(R.id.editTextNum1).toString();
//        Log.d(MainActivity.class.getSimpleName(), "here on Click called");
        TextView textView = view.findViewById(R.id.textViewSum);
        textView.setText("SETTING TEXT!");
    }

}