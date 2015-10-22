package com.example.s198541_s198611_mappe2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class BirthdayServiceMainActivity extends AppCompatActivity {

    private MyBroadcastReceiver myBroadcastReceiver;

    // Get values from SharedPreferences (turn_app_on_off):
    @Override
    protected void onResume() {
        super.onResume();

        // Sending broadcast (the broadcast checks if going to continue to service or not:
        Intent i = new Intent();
        i.setAction("com.example.s198541_s198611_mappe2.myBroadcast");
        sendBroadcast(i);

        /* Get switch status (on or off) (is placed in Settings) - only send broadcast if is turned on
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean serviceOn = sharedPrefs.getBoolean("turn_app_on_off", true);

        if(serviceOn) {
            Log.d("RECEIVER: ", "ON");
            Intent i = new Intent();
            i.setAction("com.example.s198541_s198611_mappe2.myBroadcast");
            sendBroadcast(i);
        }
        else {

            Log.d("RECEIVER: ", "ELSE");
            //unregisterReceiver
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_birthday_service_main);

        TextView textViewCustom = (TextView) findViewById(R.id.front_app_name);
        Typeface bebasNeueFont = Typeface.createFromAsset(getAssets(), "fonts/bebasneue.ttf");
        textViewCustom.setTypeface(bebasNeueFont);

        /*Switch serviceSwitch = (Switch) findViewById(R.id.startStopServiceSwitch);

        serviceSwitch.setChecked(false);

        serviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Intent i = new Intent();
                    i.setAction("com.example.s198541_s198611_mappe2.myBroadcast");
                    sendBroadcast(i);
                } else {
                    // Turn off the sms service

                }

            }
        });*/
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
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}