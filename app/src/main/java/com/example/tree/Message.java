package com.example.tree;

import java.sql.Timestamp;
import java.util.Date;

public class Message {
    private long time;


    Date d = new Date();


   // private Timestamp timeStamp;
    private String message;
    private String userName;
    private String chat_id;
    private String media_url;
    private String sender_id;
    public Message() {

    }

    public Message(long time, String message, String userName, String chat_id, String media_url, String sender_id) {
        this.time = d.getTime();
       // this.timeStamp = new Timestamp(time);
        this.message = message;
        this.userName = userName;
        this.chat_id = chat_id;
        this.media_url = media_url;
        this.sender_id = sender_id;
    }


    public Message(String userName ,String message) {
        this.time = d.getTime();
      //  this.timeStamp = new Timestamp(time);
        this.message = message;
        this.userName = userName;
    }

    public Date getD() {
        return d;
    }

    public void setD(Date d) {
        this.d = d;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }





    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
