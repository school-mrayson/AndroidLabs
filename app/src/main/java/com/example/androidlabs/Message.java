package com.example.androidlabs;

public class Message {
    protected String text;
    protected boolean isSent;
    protected long id;

    public Message(String text, boolean isSent, long id){
        this.text = text;
        this.isSent = isSent;
        this.id = id;
    }

    public boolean isSent(){
        return isSent;
    }
    public long getId(){
        return id;
    }

    public String toString(){return text;}
}
