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
        setContentView(R.layout.activity_main_grid);

        Button button1 = findViewById(R.id.button2);
        CheckBox check = findViewById(R.id.checkBox2);
        Switch switchA = findViewById(R.id.switch1);

        button1.setOnClickListener(btn -> Toast.makeText(this, this.getString(R.string.toast_message), Toast.LENGTH_LONG).show());

        check.setOnCheckedChangeListener( (box, isChecked) -> {
            if (box.isChecked()){
                Snackbar.make(check, this.getString(R.string.switch_on), Snackbar.LENGTH_LONG).setAction(this.getString(R.string.Undo), v -> check.setChecked(false)).show();
            }else{
                Snackbar.make(check, this.getString(R.string.switch_off), Snackbar.LENGTH_LONG).show();
            }
        });

        switchA.setOnCheckedChangeListener( (s, isChecked) -> {
            if (s.isChecked()){
                Snackbar.make(switchA, this.getString(R.string.switch_on), Snackbar.LENGTH_LONG).setAction(this.getString(R.string.Undo), v -> switchA.setChecked(false)).show();
            }else{
                Snackbar.make(switchA, this.getString(R.string.switch_off), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}