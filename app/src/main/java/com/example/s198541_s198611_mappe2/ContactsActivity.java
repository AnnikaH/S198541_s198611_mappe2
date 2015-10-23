package com.example.s198541_s198611_mappe2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

// Activity where you can see all the contacts and add new ones (a +-field up in the corner to the
// right of the search field

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) {
                return;
            }

            ContactsFragment contactsFragment = new ContactsFragment();
            contactsFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, contactsFragment).commit();
        }
    }

    // Add-button clicked:
    public void goToAddNewContact(View view) {
        Intent i = new Intent(this, NewContactActivity.class);
        startActivity(i);
        finish();
    }
}