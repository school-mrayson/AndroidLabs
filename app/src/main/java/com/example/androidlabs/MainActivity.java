package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        Button button1 = findViewById(R.id.button2);
        CheckBox check = findViewById(R.id.checkBox2);
        Switch switchA = findViewById(R.id.switch1);

        button1.setOnClickListener(btn -> Toast.makeText(this, "Here is more information", Toast.LENGTH_LONG).show());

        check.setOnCheckedChangeListener( (box, isChecked) -> {
            if (box.isChecked()){
                Snackbar.make(check, "Switch is now on", Snackbar.LENGTH_LONG).setAction("Undo", v -> check.setChecked(false)).show();
            }else{
                Snackbar.make(check, "Switch is now off", Snackbar.LENGTH_LONG).show();
            }
        });

        switchA.setOnCheckedChangeListener( (s, isChecked) -> {
            if (s.isChecked()){
                Snackbar.make(switchA, "Switch is now on", Snackbar.LENGTH_LONG).setAction("Undo", v -> switchA.setChecked(false)).show();
            }else{
                Snackbar.make(switchA, "Switch is now off", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}