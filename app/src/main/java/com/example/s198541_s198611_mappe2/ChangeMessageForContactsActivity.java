package com.example.s198541_s198611_mappe2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ChangeMessageForContactsActivity extends AppCompatActivity{

    private DBHandler dbHandler;
    // chosen persons from ids we get from intent (chosen in ChooseContactsFragment/ChooseContactsActivity:
    private List<Person> chosenPersons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_message_for_contacts);

        dbHandler = new DBHandler(this);
        chosenPersons = new ArrayList<>();

        // Getting ids (for the persons chosen in ChooseContactsFragment) from the intent-data sent
        // from ChooseContactsActivity:

        ArrayList<Integer> chosenIds = getIntent().getIntegerArrayListExtra("IDS");

        if(chosenIds != null) {

            // Then filling chosenPersons (ArrayList) with the Person objects behind the ids:

            for(int i = 0; i < chosenIds.size(); i++) {
                Person p = dbHandler.getPerson(chosenIds.get(i));
                chosenPersons.add(p);
            }

            // Later (not necessarily here) we can go through chosenPersons (ArrayList) like this:
            if(!chosenPersons.isEmpty()) {
                for(int i = 0; i < chosenPersons.size(); i++) {
                    Person p = chosenPersons.get(i);

                    // Getting the name of the person in the log (remove later):
                    Log.d("NAVN: ", p.getName());
                }
            }

        }
    }

    public void OpenContact(View v)
    {
        /*  Insert method to open select contact activity */
        Intent i = new Intent(this, ChooseContactsActivity.class);
        startActivity(i);
    }

    // OnClick save-button:
    public void saveMessageForContacts(View v) {

    }

    // OnClick cancel-button:
    public void cancel(View v) {
        finish();
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
     /*   if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }
}
