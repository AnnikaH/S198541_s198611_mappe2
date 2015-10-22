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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_of_notification);

        DBHandler dbHandler = new DBHandler(this);

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
}