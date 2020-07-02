package com.example.androidlabs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    private ImageButton profilePicBtn;
    private EditText emailET;
    private Button chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent fromMain = getIntent();

        emailET = findViewById(R.id.emailET2);
        profilePicBtn = findViewById(R.id.profileImageBtn);
        chat = findViewById(R.id.chatButton);

        emailET.setText(fromMain.getStringExtra("Email"));

        profilePicBtn.setOnClickListener(btn -> {
            dispatchTakePictureIntent();
        });

        chat.setOnClickListener(btn -> {
            Intent goToChatRoom = new Intent(this, ChatRoomActivity.class);
            startActivity(goToChatRoom);
        });
        Log.e(ACTIVITY_NAME, "In function:" + " onCreate");
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");
            profilePicBtn.setImageBitmap(imageBitmap);
        }
        Log.e(ACTIVITY_NAME, "In function:" + " onActivityResult");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function:" + " onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function:" + " onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function:" + " onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function:" + " onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function:" + " onDestroy");
    }
}