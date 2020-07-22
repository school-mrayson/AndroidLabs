package com.example.androidlabs;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailsFragment extends Fragment {
    private AppCompatActivity parentActivity;

    private Bundle dataFromActivity;
    private long id;
    private String message;
    private boolean isSent;

    private TextView messageText;
    private TextView idText;
    private CheckBox isSentBox;
    private Button hideBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.ITEM_ID);
        message = dataFromActivity.getString(ChatRoomActivity.ITEM_MESSAGE);
        isSent = dataFromActivity.getBoolean(ChatRoomActivity.ITEM_SENT);

        View result = inflater.inflate(R.layout.fragment_details, container, false);

        messageText = result.findViewById(R.id.messageText);
        messageText.setText(message);

        idText = result.findViewById(R.id.messageID2);
        idText.setText(Long.toString(id));

        isSentBox = result.findViewById(R.id.isSentCheck);
        isSentBox.setChecked(isSent);

        hideBtn = result.findViewById(R.id.hideBtn);
        hideBtn.setOnClickListener(btn -> {
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });


        return result;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        parentActivity = (AppCompatActivity)context;
    }
}