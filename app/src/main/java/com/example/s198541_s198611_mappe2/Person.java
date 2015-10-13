package com.example.s198541_s198611_mappe2;

import android.util.Log;

import java.sql.Date;

// Database-class Person:

public class Person {
    private int _ID;
    private String Name;
    private String PhoneNumber;
    private String Birthday;
    private String Message;

    public Person() {

    }

    public Person(String name, String phoneNumber, String birthday) {
        Name = name;
        PhoneNumber = phoneNumber;

        Log.d("BIRTHDAY CONSTRUCTOR: ", "" + birthday);

        Birthday = birthday;

        Log.d("BIRTDAY CONSTR AFTER: ", "" + Birthday);
    }

    public Person(int _id, String name, String phoneNumber, String birthday) {
        _ID = _id;
        Name = name;
        PhoneNumber = phoneNumber;
        Birthday = birthday;
    }

    public Person(String name, String phoneNumber, String birthday, String message) {
        Name = name;
        PhoneNumber = phoneNumber;
        Birthday = birthday;
        Message = message;
    }

    public Person(int _id, String name, String phoneNumber, String birthday, String message) {
        _ID = _id;
        Name = name;
        PhoneNumber = phoneNumber;

        Log.d("BIRTHDAY CONSTRUCTOR: ", "" + birthday);

        Birthday = birthday;
        Message = message;

        Log.d("BIRTDAY CONSTR AFTER: ", "" + Birthday);
    }

    public void set_ID(int _id) {
        _ID = _id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int get_ID() {
        return _ID;
    }

    public String getName() {
        return Name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getBirthday() {
        return Birthday;
    }

    public String getMessage() {
        return Message;
    }
}
