package com.example.androidlabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatViewAdapter extends BaseAdapter {

    Context context;
    static LayoutInflater inflater = null;
    ArrayList<Message> messages;

    public ChatViewAdapter(Context context, ArrayList<Message> messages){
        this.context = context;
        this.messages = messages;

    }
    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return messages.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (messages.get(position).isSent()){
            View row = inflater.inflate(R.layout.activity_send, parent, false);
            TextView messageTv = row.findViewById(R.id.textView7);
            messageTv.setText(messages.get(position).toString());
            return row;
        }
        View row = inflater.inflate(R.layout.activity_receive, parent, false);
        TextView messageTv = row.findViewById(R.id.textView8);
        messageTv.setText(messages.get(position).toString());
        return row;
    }
}
