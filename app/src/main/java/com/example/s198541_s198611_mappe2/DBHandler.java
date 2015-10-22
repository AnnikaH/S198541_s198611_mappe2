package com.example.s198541_s198611_mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    static String TABLE_PERSONS = "Person";
    static String KEY_ID = "_id";
    static String KEY_NAME = "Name";
    static String KEY_PH_NO = "PhoneNumber";
    static String KEY_BIRTHDAY = "Birthday";
    static String KEY_MESSAGE = "Message";
    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "birthdayServiceDatabase.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String LAG_TABELL = "CREATE TABLE " + TABLE_PERSONS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " + KEY_PH_NO + " TEXT, " + KEY_BIRTHDAY + " TEXT, " + KEY_MESSAGE +
                " TEXT)";
        Log.d("SQL", LAG_TABELL);
        db.execSQL(LAG_TABELL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
        onCreate(db);
    }

    public void addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, person.getName());
        values.put(KEY_PH_NO, person.getPhoneNumber());
        values.put(KEY_BIRTHDAY, person.getBirthday());
        values.put(KEY_MESSAGE, person.getMessage());
        db.insert(TABLE_PERSONS, null, values);
        db.close();
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PERSONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.set_ID(cursor.getInt(0));
                person.setName(cursor.getString(1));
                person.setPhoneNumber(cursor.getString(2));
                person.setBirthday(cursor.getString(3));
                person.setMessage(cursor.getString(4));
                persons.add(person);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return persons;
    }

    public List<Person> getAllPersonsWithBirthday(int day, int month) {
        List<Person> persons = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PERSONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.set_ID(cursor.getInt(0));
                person.setName(cursor.getString(1));
                person.setPhoneNumber(cursor.getString(2));
                person.setBirthday(cursor.getString(3));
                person.setMessage(cursor.getString(4));

                // Checking birthday (if match input parameters):

                String birthday = person.getBirthday();
                String[] parts = birthday.split("-");
                int birthDay = Integer.parseInt(parts[2]);
                int birthMonth = Integer.parseInt(parts[1]);

                if(birthMonth == month && birthDay == day) {
                    persons.add(person);
                }
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return persons;
    }

    public List<Person> getAllPersonsFromName(String namePart) {
        List<Person> persons = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PERSONS + " WHERE " + KEY_NAME + " LIKE " + namePart + "%";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.set_ID(cursor.getInt(0));
                person.setName(cursor.getString(1));
                person.setPhoneNumber(cursor.getString(2));
                person.setBirthday(cursor.getString(3));
                person.setMessage(cursor.getString(4));
                persons.add(person);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return persons;
    }

    public List<Person> getAllPersonsByBirthday() {
        List<Person> persons = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PERSONS + " ORDER BY " + KEY_BIRTHDAY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.set_ID(cursor.getInt(0));
                person.setName(cursor.getString(1));
                person.setPhoneNumber(cursor.getString(2));
                person.setBirthday(cursor.getString(3));
                person.setMessage(cursor.getString(4));
                persons.add(person);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return persons;
    }

    public int getNumberOfPersons() {
        String sql = "SELECT * FROM " + TABLE_PERSONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int updatePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, person.getName());
        values.put(KEY_PH_NO, person.getPhoneNumber());
        values.put(KEY_BIRTHDAY, person.getBirthday() + "");
        int changed = db.update(TABLE_PERSONS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(person.get_ID()) });
        db.close();
        return changed;
    }

    public void deletePerson(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PERSONS, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void deletePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PERSONS, KEY_ID + " = ?", new String[] { String.valueOf(person.get_ID()) });
        db.close();
    }

    public Person getPerson(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PERSONS,
                new String[] { KEY_ID, KEY_NAME, KEY_PH_NO, KEY_BIRTHDAY, KEY_MESSAGE }, KEY_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if(cursor != null) {
            if(cursor.moveToFirst()) {  // finner bare den fÃ¸rste forekomsten (ok siden skal vÃ¦re unik)
                Person person = new Person(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4));

                cursor.close();
                db.close();

                return person;
            }
        }

        db.close();
        return null;
    }

    public Person getPerson(String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PERSONS,
                new String[] { KEY_ID, KEY_NAME, KEY_PH_NO, KEY_BIRTHDAY, KEY_MESSAGE }, KEY_PH_NO + " = ?",
                new String[] { phoneNumber }, null, null, null, null);

        if(cursor != null) {
            if(cursor.moveToFirst()) {
                Person person = new Person(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4));

                cursor.close();
                db.close();

                return person;
            }
        }

        db.close();
        return null;
    }
}