package com.example.tree;

import java.util.Date;

public class UIDGenerator {

    public static String generateUID(){
        String UID;
        Date today = new Date();
        String month = Integer.toString(today.getMonth());
        String day = Integer.toString(today.getDay());
        String year = Integer.toString(today.getYear());
        String hour = Integer.toString(today.getHours());
        String minute = Integer.toString(today.getMinutes());
        String second = Integer.toString(today.getSeconds());
        int rand = (int)(Math.random() * 9999 + 1000 - 1000);
        String end = Integer.toString(rand);
        UID = month + day + year + hour + minute + second + end;
        return UID;
    }


}
