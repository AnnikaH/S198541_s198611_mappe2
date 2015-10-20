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

    //implements SearchView.OnQueryTextListener

    //DBHandler db;

    // SearchView search_view;
    //ListView list_view;

    //ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        /*db = new DBHandler(this);

        Log.d("Legg inn: ", "legger til kontakter");

        String dateString = "1989-06-07";
        //Date date = Date.valueOf(dateString);

        // Oppretter en kontakt uten noen melding for Ã¥ teste:
        db.addPerson(new Person("Pernille", "41234567", dateString));*/


//        List<Person> persons = db.getAllPersons();
//        String[] personNameArray = new String[persons.size()];
//
//        int count = 0;
//
//        for(Person pers : persons) {
//            String log = "Id: " + pers.get_ID() + ", Name: " +
//                    pers.getName() + ", PhoneNumber: " + pers.getPhoneNumber() +
//                    ", Birthday: " + pers.getBirthday() + ", Message is null for now";
//
//            personNameArray[count++] = pers.getName() + " " + pers.getBirthday();
//
//            Log.d("Persons: ", log);
//        }


        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) {
                return;
            }

            ContactsFragment contactsFragment = new ContactsFragment();
            contactsFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, contactsFragment).commit();
        }



        //list_view = (ListView) findViewById(R.id.list_view);

        //adapter = new ArrayAdapter<>(this, R.layout.list, R.id.name, personNameArray);
        //list_view.setAdapter(adapter);

//        search_view = (SearchView) findViewById(R.id.search_view);
//        search_view.setOnQueryTextListener(this);
    }

//    @Override
//    public boolean onQueryTextChange(String newText) {
//        adapter.getFilter().filter(newText);
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }

    // Add-button clicked:
    public void goToAddNewContact(View view) {
        Intent i = new Intent(this, NewContactActivity.class);
        startActivity(i);
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
