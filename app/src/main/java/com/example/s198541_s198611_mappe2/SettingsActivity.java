package com.example.s198541_s198611_mappe2;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    String defaultMessage;
    String timeOfDay;
    boolean serviceOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefsFragment()).commit();

        //TODO: På change_time: android:summary="@string/time_picker_dialog_summary"
    }

    // Store in SharedPreferences:
    @Override
    protected void onPause() {
        super.onPause();

        if(defaultMessage.equals("")) {
            defaultMessage = getString(R.string.our_default_message);
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putString("timeOfDay", timeOfDay)
                .putString("defaultMessage", defaultMessage)
                .putBoolean("serviceOn", serviceOn)
                .commit();
    }

    // Get values from SharedPreferences:
    @Override
    protected void onResume() {
        super.onResume();

        timeOfDay = (getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("timeOfDay", ""));
        defaultMessage = (getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("defaultMessage", ""));
        serviceOn = (getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("serviceOn", true));
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

    public static class PrefsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            Preference langPref = findPreference("change_language");
            langPref.setSummary(Locale.getDefault().getDisplayLanguage());
            langPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    if(Locale.getDefault().getDisplayLanguage().equals("English"))
                        setLocale("nb");
                    else
                        setLocale("en");

                    return true;
                }
            });
        }

        public void setLocale(String lang)
        {
            Locale myLocale = new Locale(lang);
            Resources res = getActivity().getBaseContext().getResources();
            Configuration config = res.getConfiguration();
            Locale.setDefault(myLocale);
            config.locale = myLocale;
            DisplayMetrics dm = res.getDisplayMetrics();
            res.updateConfiguration(config, dm);

            Intent refresh = new Intent(getActivity(), SettingsActivity.class);
            startActivity(refresh);
            getActivity().finish();
        }
    }
}