package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);
    }

    public void displayToastMsg(View view) {
        Toast toast = Toast.makeText(this, getResources().getString(R.string.toast_message), Toast.LENGTH_LONG);
        toast.show();
    }
}