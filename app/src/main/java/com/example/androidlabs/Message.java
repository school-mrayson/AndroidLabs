package com.example.androidlabs;

public class Message {
    private String text;
    private boolean isSent;

    public Message(String text, boolean isSent){
        this.text = text;
        this.isSent = isSent;
    }

    public boolean isSent(){
        return isSent;
    }

    public String toString(){return text;}
}
