package com.example.s198541_s198611_mappe2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChooseContactsActivity extends AppCompatActivity{

    private ChooseContactsFragment chooseContactsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contacts);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            chooseContactsFragment = new ChooseContactsFragment();
            chooseContactsFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, chooseContactsFragment).commit();
        }
    }

    // OK-button clicked (the user is happy with the contacts chosen):
    public void returnWithChosenContacts(View view) {
        Intent intent = new Intent(this, ChangeMessageForContactsActivity.class);

        ArrayList<Long> idsLong = chooseContactsFragment.getCheckedIds();
        ArrayList<Integer> ids = new ArrayList<>();

        // adding Long ids to IntegerArrayList to put in Extra:
        for(int i = 0; i < idsLong.size(); i++) {
            long idLong = idsLong.get(i);   // casting from Long to long
            int idInt = (int) idLong;       // casting from long to int
            ids.add(idInt);                 // possible to add int to ArrayList<Integer> (implicit casting)
        }

        intent.putIntegerArrayListExtra("IDS", ids);
        startActivity(intent);
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
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}