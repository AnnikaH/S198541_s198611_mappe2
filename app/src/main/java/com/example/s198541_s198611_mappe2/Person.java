package com.example.s198541_s198611_mappe2;

// Database-class Person:

public class Person {
    private int _id;
    private String Name;
    private String PhoneNumber;
    private String Birthday;
    private String Message;

    public Person() {

    }

    public Person(String name, String phoneNumber, String birthday, String message) {
        Name = name;
        PhoneNumber = phoneNumber;
        Birthday = birthday;
        Message = message;
    }

    public Person(int id, String name, String phoneNumber, String birthday, String message) {
        _id = id;
        Name = name;
        PhoneNumber = phoneNumber;
        Birthday = birthday;
        Message = message;
    }

    public void set_ID(int id) {
        _id = id;
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
        return _id;
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
