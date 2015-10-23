package com.example.s198541_s198611_mappe2;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BirthdayServiceMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_birthday_service_main);

        TextView textViewCustom = (TextView) findViewById(R.id.front_app_name);
        Typeface bebasNeueFont = Typeface.createFromAsset(getAssets(), "fonts/bebasneue.ttf");
        textViewCustom.setTypeface(bebasNeueFont);
    }

    // OnClick newMessageButton
    public void goToNewMessage(View view) {
        Intent i = new Intent(this, ChangeMessageForContactsActivity.class);
        startActivity(i);
    }

    // OnClick contactsButton
    public void goToContacts(View view) {
        Intent i = new Intent(this, ContactsActivity.class);
        startActivity(i);
    }

    // OnClick birthdaysButton
    public void goToBirthdayList(View view) {
        Intent i = new Intent(this, BirthdaysActivity.class);
        startActivity(i);
    }

    // OnClick settingsButton
    public void goToSettings(View view) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }
}