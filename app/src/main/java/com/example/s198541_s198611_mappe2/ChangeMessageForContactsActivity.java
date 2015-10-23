package com.example.s198541_s198611_mappe2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView textViewChosenContacts = (TextView) findViewById(R.id.text_view_for_showing_chosen_contacts);

        if(chosenIds != null) {
            // Then filling chosenPersons (ArrayList) with the Person objects behind the ids:
            // And at the same time making a string with all the contacts' names (to show to the user):

            String outString = "";

            for(int i = 0; i < chosenIds.size(); i++) {
                Person p = dbHandler.getPerson(chosenIds.get(i));
                chosenPersons.add(p);

                if(i == 0)
                    outString += p.getName();
                else {
                    outString +=  ", " + p.getName();
                }
            }

            textViewChosenContacts.setText(outString);
        }
    }

    public void OpenContact(View v)
    {
        /*  Insert method to open select contact activity */
        Intent i = new Intent(this, ChooseContactsActivity.class);
        startActivity(i);
        finish();
    }

    // OnClick save-button:
    public void saveMessageForContacts(View v) {
        EditText edit = (EditText) findViewById(R.id.edit_message);
        String newMessage = edit.getText().toString();

        if(!newMessage.matches(".+")) {
            edit.setError(getString(R.string.message_error_message));
            return;
        }

        if(chosenPersons.size() == 0) {
            TextView textView = (TextView) findViewById(R.id.text_view_for_showing_chosen_contacts);
            textView.setError(getString(R.string.no_contacts_chosen_error_message));
            Toast.makeText(this, getString(R.string.no_contacts_chosen_error_message), Toast.LENGTH_SHORT).show();
            return;
        }

        for(int i = 0; i < chosenPersons.size(); i++) {
            Person p = chosenPersons.get(i);
            p.setMessage(newMessage);
            dbHandler.updatePerson(p);
        }

        Toast.makeText(this, getString(R.string.message_changed), Toast.LENGTH_SHORT).show();
        finish();
    }

    // OnClick cancel-button:
    public void cancel(View v) {
        finish();
    }
}