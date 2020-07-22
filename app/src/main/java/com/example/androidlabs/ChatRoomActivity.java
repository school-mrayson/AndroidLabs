package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;


public class ChatRoomActivity extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "CHAT_ROOM_ACTIVITY";
    private ListView chatWindow;
    private Button sendBtn;
    private Button receiveBtn;
    private EditText messageET;
    private Message message;
    private ArrayList<Message> messages = new ArrayList<>();
    SQLiteDatabase db;
    private FrameLayout fl;
    private Boolean isPhone;

    public static final String ITEM_MESSAGE = "MESSAGE";
    public static final String ITEM_SENT = "ISSENT";
    public static final String ITEM_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        chatWindow = findViewById(R.id.chatListView);
        sendBtn = findViewById(R.id.sendBtn);
        receiveBtn = findViewById(R.id.receiveBtn);
        messageET = findViewById(R.id.messageET);
        fl = findViewById(R.id.fragmentLocation);

        if (fl == null){
            isPhone = true;
        }else{
            isPhone = false;
        }

        loadFromDatabase();

        ChatViewAdapter adapter = new ChatViewAdapter(this, messages);
        chatWindow.setAdapter(adapter);

        sendBtn.setOnClickListener(btn -> {
            String mes = messageET.getText().toString();
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.COL_TEXT, mes);
            newRowValues.put(MyOpener.COL_SENT, 1);

            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

            message = new Message(mes, true, newId);

            messages.add(message);
            messageET.setText("");
            adapter.notifyDataSetChanged();
        });

        receiveBtn.setOnClickListener(btn -> {
            String mes = messageET.getText().toString();
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.COL_TEXT, mes);
            newRowValues.put(MyOpener.COL_SENT, 0);

            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

            message = new Message(mes, false, newId);

            messages.add(message);
            messageET.setText("");
            adapter.notifyDataSetChanged();
        });

        chatWindow.setOnItemLongClickListener((parent, view, position, id) -> {
            alertDialogBuilder.setTitle("Do you want to delete this?");
            alertDialogBuilder.setMessage("The selected row is: " + position + "\nThe database id is: " + id);
            alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
                messages.remove(position);
                deleteContact(id);
                adapter.notifyDataSetChanged();
            });
            alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        });

        chatWindow.setOnItemClickListener((list, item, position, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString(ITEM_MESSAGE, messages.get(position).toString());
            dataToPass.putBoolean(ITEM_SENT, messages.get(position).isSent);
            dataToPass.putLong(ITEM_ID, id);

            if(!isPhone){
                DetailsFragment dFragment = new DetailsFragment();
                dFragment.setArguments(dataToPass);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, dFragment).commit();
            }
            else{
                Intent nextActivity = new Intent(this, DetailsPhone.class);
                nextActivity.putExtras(dataToPass);
                startActivity(nextActivity);
            }
        });
    }


    private void deleteContact(long id){
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(id)});
    }
    private void loadFromDatabase(){
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

        String[] columns = {MyOpener.COL_ID, MyOpener.COL_TEXT, MyOpener.COL_SENT};

        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null,null,null,null,null,null);

        int textColumnIndex = results.getColumnIndex(MyOpener.COL_TEXT);
        int sentColumnIndex = results.getColumnIndex(MyOpener.COL_SENT);
        int idColumnIndex = results.getColumnIndex(MyOpener.COL_ID);

        while(results.moveToNext()) {
            String text = results.getString(textColumnIndex);
            Boolean sent = (results.getInt(sentColumnIndex) == 1)? true:false;
            long id = results.getLong(idColumnIndex);
            messages.add(new Message(text, sent, id));
        }
        results.moveToPosition(-1);
        printCursor(results, db.getVersion());
    }

    private void printCursor(Cursor c, int version){
        Log.i(ACTIVITY_NAME, "Database Version: " + version);
        Log.i(ACTIVITY_NAME, "Number of columns in the cursor: " + c.getColumnCount());
        String names = "";
        String[] colNames = c.getColumnNames();
        for (String name: colNames){
            names = names + (name + " | ");
        }
        Log.i(ACTIVITY_NAME, "Names of the columns: " + names);
        Log.i(ACTIVITY_NAME, "Number of rows in the cursor: " + c.getCount());
        Log.i(ACTIVITY_NAME, "Results: ");
        while (c.moveToNext()) {
            String result = "|";
            for (int col = 0; col < c.getColumnCount(); col++) {
                result += " " + c.getString(col) + " | ";
            }
            Log.i(ACTIVITY_NAME, result);
        }
        c.moveToPosition(-1);
    }
}
