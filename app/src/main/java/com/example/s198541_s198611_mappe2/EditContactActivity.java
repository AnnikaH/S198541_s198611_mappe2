package com.example.s198541_s198611_mappe2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

// When clicking on/selecting a contact in the ContactsActivity

public class EditContactActivity extends AppCompatActivity implements WarningDialog.DialogClickListener {

    private DBHandler dbHandler;
    private String name;

    // WarningDialog-method:
    @Override
    public void onYesClick() {
        // Then the user wants to delete the person:

        int id;

        try {
            id = getIntent().getIntExtra("ID", -1);
            dbHandler.deletePerson(id);
            Toast.makeText(this, name + " " + getString(R.string.person_deleted_message), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.parse_id_error_message), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(this, ContactsActivity.class);
        startActivity(i);
        finish();
    }

    // WarningDialog-method:
    @Override
    public void onCancelClick() {
        // do nothing
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        dbHandler = new DBHandler(this);

        EditText editTextName = (EditText) findViewById(R.id.name);
        EditText editTextPhone = (EditText) findViewById(R.id.phoneNumber);
        DatePicker datePickerBirthday = (DatePicker) findViewById(R.id.birthday);
        EditText editTextMessage = (EditText) findViewById(R.id.message);

        name = getIntent().getStringExtra("NAME");
        editTextName.setText(name);
        editTextPhone.setText(getIntent().getStringExtra("PHONE"));
        editTextMessage.setText(getIntent().getStringExtra("MESSAGE"));

        String birthday = getIntent().getStringExtra("BIRTHDAY");
        String[] birthdayArray = birthday.split("-");

        int year = -1;
        int month = -1;
        int day = -1;

        try {
            year = Integer.parseInt(birthdayArray[0]);
            month = Integer.parseInt(birthdayArray[1]) - 1; // month indexed differently in DatePicker (want the database-date to be correct)
            day = Integer.parseInt(birthdayArray[2]);
        } catch (NumberFormatException nfe) {
            Toast.makeText(this, getString(R.string.parse_id_date_picker_error_message), Toast.LENGTH_SHORT).show();
        }

        if(year != -1 && month != -1 && day != -1)
            datePickerBirthday.updateDate(year, month, day);
    }

    // OnClick save-button:
    public void saveContactChanges(View view) {
        EditText editTextName = (EditText) findViewById(R.id.name);
        EditText editTextPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        DatePicker datePickerBirthday = (DatePicker) findViewById(R.id.birthday);
        EditText editTextMessage = (EditText) findViewById(R.id.message);

        String namePerson = editTextName.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        int year = datePickerBirthday.getYear();
        int month = datePickerBirthday.getMonth() + 1;  // month indexed differently in DatePicker
        int day = datePickerBirthday.getDayOfMonth();
        String birthday = year + "-" + month + "-" + day;
        String message = editTextMessage.getText().toString();

        // Validation of input:

        if(!namePerson.matches("[a-zæøåA-ZÆØÅ0-9_ ]+")) {
            editTextName.setError(getString(R.string.name_error_message));
            return;
        }

        if(!phoneNumber.matches("[0-9]{8,}")) {
            editTextPhoneNumber.setError(getString(R.string.phone_error_message));
            return;
        }

        if(!message.matches(".+")) {
            editTextMessage.setError(getString(R.string.message_error_message));
            return;
        }

        int id;
        Person p;

        try {
            id = getIntent().getIntExtra("ID", -1);
            p = dbHandler.getPerson(id);
            p.setName(namePerson);
            p.setPhoneNumber(phoneNumber);
            p.setBirthday(birthday);
            p.setMessage(message);
            dbHandler.updatePerson(p);
            Toast.makeText(this, name + getString(R.string.person_edited_message), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.parse_id_edited_error_message), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(this, ContactsActivity.class);
        startActivity(i);
        finish();
    }

    // OnClick delete-button:
    public void deleteContact(View view) {
        // Pop-up with warning first:
        String message = getString(R.string.delete_warning_message);
        WarningDialog dialog = WarningDialog.newInstance(message);
        dialog.show(getFragmentManager(), "DELETE");
        // Waiting for the user to make a choice: Yes or Cancel
    }

    // OnClick cancel-button:
    public void cancel(View view) {
        Intent i = new Intent(this, ContactsActivity.class);
        startActivity(i);
        finish();
    }
}
