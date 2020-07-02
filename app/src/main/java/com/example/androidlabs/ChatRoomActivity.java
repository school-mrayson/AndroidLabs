package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class ChatRoomActivity extends AppCompatActivity {
    private ListView chatWindow;
    private Button sendBtn;
    private Button receiveBtn;
    private EditText messageET;
    private Message message;
    private ArrayList<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        chatWindow = findViewById(R.id.chatListView);
        sendBtn = findViewById(R.id.sendBtn);
        receiveBtn = findViewById(R.id.receiveBtn);
        messageET = findViewById(R.id.messageET);

        ChatViewAdapter adapter = new ChatViewAdapter(this, messages);
        chatWindow.setAdapter(adapter);

        sendBtn.setOnClickListener(btn -> {
            message = new Message (messageET.getText().toString(), true);
            messages.add(message);
            messageET.setText("");
            adapter.notifyDataSetChanged();
        });

        receiveBtn.setOnClickListener(btn -> {
            message = new Message (messageET.getText().toString(), false);
            messages.add(message);
            messageET.setText("");
            adapter.notifyDataSetChanged();
        });

        chatWindow.setOnItemLongClickListener((parent, view, position, id) -> {
            alertDialogBuilder.setTitle("Do you want to delete this?");
            alertDialogBuilder.setMessage("The selected row is: " + position + "\nThe database id is: " + id);
            alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
                messages.remove(position);
                adapter.notifyDataSetChanged();
            });
            alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        });


    }
}
