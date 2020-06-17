package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    private EditText emailET;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);
        emailET = findViewById(R.id.emailET);
        login = findViewById(R.id.loginBtn);
        sp = getPreferences(Context.MODE_PRIVATE);
        spEditor = sp.edit();

        login.setOnClickListener(btn -> {
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile.putExtra("Email",emailET.getText().toString());
            startActivity(goToProfile);
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        spEditor.putString("userEmail", emailET.getText().toString());
        spEditor.commit();
    }

    @Override
    protected void onResume(){
        super.onResume();
        emailET.setText(sp.getString("userEmail",""));
    }
}