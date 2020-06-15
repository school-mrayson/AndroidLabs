package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
    private SharedPreferences.Editor spEditor = sp.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);


    }

    @Override
    protected void onPause() {
        super.onPause();
        spEditor.putString("userEmail", )
    }
}