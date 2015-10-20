package com.example.s198541_s198611_mappe2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// You get to this class when you have clicked on Contacts in the main menu and then the + to the
// right of the search field on top of the ContactsActivity

// Layout: form where you can fill in everything for each Person (incl. message)

public class NewContactActivity extends AppCompatActivity {

    DBHandler dbHandler;
    String storedDefaultMessage;

    // Get values from SharedPreferences:
    @Override
    protected void onResume() {
        super.onResume();

        storedDefaultMessage = (getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("defaultMessage", ""));

        if(storedDefaultMessage.equals(""))
            storedDefaultMessage = getString(R.string.our_default_message);

        EditText messageField = (EditText) findViewById(R.id.message);
        messageField.setText(storedDefaultMessage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        dbHandler = new DBHandler(this);

        //EditText editTextName = (EditText) findViewById(R.id.name);
//        int idText = editTextName.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//        TextView editText = (TextView) editTextName.findViewById(idText);
//        editText.setTextColor(Color.WHITE);
    }

    // OnClick add-button:
    public void addContact(View view) {
        EditText editTextName = (EditText) findViewById(R.id.name);
        EditText editTextPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        DatePicker datePickerBirthday = (DatePicker) findViewById(R.id.birthday);
        EditText editTextMessage = (EditText) findViewById(R.id.message);

        String name = editTextName.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        int year = datePickerBirthday.getYear();
        int month = datePickerBirthday.getMonth();
        int day = datePickerBirthday.getDayOfMonth();
        String birthday = year + "-" + month + "-" + day;
        String message = editTextMessage.getText().toString();

        // TODO: Validering av input

        Person person = new Person(name, phoneNumber, birthday, message);
        dbHandler.addPerson(person);

        Toast.makeText(this, name + " " + getString(R.string.person_added_message), Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, ContactsActivity.class);
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