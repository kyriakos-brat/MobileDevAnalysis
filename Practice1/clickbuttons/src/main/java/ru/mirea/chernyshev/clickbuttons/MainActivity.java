package ru.mirea.chernyshev.clickbuttons;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvOut;
    private Button buttonOk;
    private Button buttonCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        tvOut = (TextView) findViewById(R.id.tvOut);
        buttonOk = (Button) findViewById(R.id.btnOk);
        buttonCancel = (Button) findViewById(R.id.btnCancel);
        //
        OnClickListener oclBtnOk = new OnClickListener() {
            @Override
            public void onClick(View view) {
                tvOut.setText("Нажата кнопка OK");
            }
        };
        //
        buttonOk.setOnClickListener(oclBtnOk);
    }

    public void onMyButtonClick(View view)
    {
        //
        Toast.makeText(this, "Еще один способ!", Toast.LENGTH_SHORT).show();
    }
}