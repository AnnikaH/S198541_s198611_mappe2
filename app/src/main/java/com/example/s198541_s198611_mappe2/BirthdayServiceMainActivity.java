package com.example.s198541_s198611_mappe2;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class BirthdayServiceMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_service_main);

        TextView textViewCustom = (TextView) findViewById(R.id.front_app_name);
        Typeface bebasNeueFont = Typeface.createFromAsset(getAssets(), "fonts/bebasneue.ttf");
        textViewCustom.setTypeface(bebasNeueFont);

        Switch serviceSwitch = (Switch) findViewById(R.id.startStopServiceSwitch);

        serviceSwitch.setChecked(false);
        serviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Intent i = new Intent();
                    i.setAction("com.example.s198541_s198611_mappe2.myBroadcast");
                    sendBroadcast(i);
                } else {
                    // TODO: turn off the sms service

                }

            }
        });

        // If need to check the current state before we display the screen
        /*if(serviceSwitch.isChecked()){
            switchStatus.setText("Switch is currently ON");
        }
        else {
            switchStatus.setText("Switch is currently OFF");
        }*/
    }

    // OnClick newMessageButton
    public void goToNewMessage(View view) {
        Intent i = new Intent(this, NewMessageActivity.class);
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

    // OnClick changeLanguageButton
    public void changeLanguage(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_birthday_service_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}