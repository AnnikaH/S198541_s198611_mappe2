package com.example.s198541_s198611_mappe2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// You get to this class when you have clicked on Contacts in the main menu and then the + to the
// right of the search field on top of the ContactsActivity

// Layout: form where you can fill in everything for each Person (incl. message)

public class NewContactActivity extends AppCompatActivity {

    DBHandler dbHandler;
    String storedDefaultMessage = "";

    // Store values (landscape-portrait)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        EditText messageField = (EditText) findViewById(R.id.message);
        storedDefaultMessage = messageField.getText().toString();
        outState.putString("MESSAGE", storedDefaultMessage);
        super.onSaveInstanceState(outState);
    }

    // Get stored values (landscape-portrait)
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        storedDefaultMessage = savedInstanceState.getString("MESSAGE");
    }

    // Get values from SharedPreferences (change_default_message):
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        storedDefaultMessage = sharedPrefs.getString("change_default_message", "");

        if(storedDefaultMessage.equals("")) {
            storedDefaultMessage = getString(R.string.our_default_message);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        dbHandler = new DBHandler(this);

        if(storedDefaultMessage.equals("")) {
            storedDefaultMessage = getString(R.string.our_default_message);
            EditText messageField = (EditText) findViewById(R.id.message);
            messageField.setText(storedDefaultMessage);
        }
    }

    // OnClick add-button:
    public void addContact(View view) {
        EditText editTextName = (EditText) findViewById(R.id.name);
        EditText editTextPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        DatePicker datePickerBirthday = (DatePicker) findViewById(R.id.birthday);
        EditText editTextMessage = (EditText) findViewById(R.id.message);

        String name = editTextName.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        int year = datePickerBirthday.getYear();
        int month = datePickerBirthday.getMonth() + 1;  // month indexed differently in DatePicker
        int day = datePickerBirthday.getDayOfMonth();
        String birthday = year + "-" + month + "-" + day;
        String message = editTextMessage.getText().toString();

        // Validation of input:

        if(!name.matches("[a-zæøåA-ZÆØÅ0-9_ ]+")) {
            editTextName.setError(getString(R.string.name_error_message));
            return;
        }

        if(!phoneNumber.matches("[0-9]{8,}")) {
            editTextPhoneNumber.setError(getString(R.string.phone_error_message));
            return;
        }

        if(!message.matches(".+")) {
            editTextMessage.setError(getString(R.string.message_error_message));
            return;
        }

        Person person = new Person(name, phoneNumber, birthday, message);
        dbHandler.addPerson(person);

        Toast.makeText(this, name + " " + getString(R.string.person_added_message), Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, ContactsActivity.class);
        startActivity(i);
        finish();
    }

    // OnClick cancel-button:
    public void cancel(View view) {
        Intent i = new Intent(this, ContactsActivity.class);
        startActivity(i);
        finish();
    }
}