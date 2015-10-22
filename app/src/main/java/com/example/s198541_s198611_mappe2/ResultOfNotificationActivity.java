package com.example.s198541_s198611_mappe2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultOfNotificationActivity extends AppCompatActivity {

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_of_notification);

        dbHandler = new DBHandler(this);

        LinearLayout layout = (LinearLayout) findViewById(R.id.sms_information_layout);

        // Getting the ids for all the persons that has gotten a sms:
        int[] ids = getIntent().getIntArrayExtra("PERSONIDS");

        for(int i = 0; i < ids.length; i++) {
            Person p = dbHandler.getPerson(ids[i]);

            if(p != null) {
                TextView text = new TextView(getBaseContext());
                text.setText(p.getName() + ", " + p.getPhoneNumber() + ", " + p.getBirthday() + ": " + p.getMessage());
                layout.addView(text);
            }
            else {
                TextView text = new TextView(getBaseContext());
                text.setText(getString(R.string.find_person_error));
                layout.addView(text);
            }
        }
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
      /*  if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}