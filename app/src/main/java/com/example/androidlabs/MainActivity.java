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
    private SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
    private SharedPreferences.Editor spEditor = sp.edit();
    private EditText emailET = findViewById(R.id.emailET);
    private EditText passET = findViewById(R.id.passwordET);
    private Button login = findViewById(R.id.loginBtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        //load email from sp and send to email et
        emailET.setText(sp.getString("userEmail", ""));

        login.setOnClickListener(btn -> {
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile.putExtra("Email",emailET.getText().toString());
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        spEditor.putString("userEmail", emailET.getText().toString());
    }
}