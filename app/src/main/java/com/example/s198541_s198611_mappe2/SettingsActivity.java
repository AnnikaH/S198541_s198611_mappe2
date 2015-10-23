package com.example.s198541_s198611_mappe2;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefsFragment()).commit();
    }

    public static class PrefsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            final Preference switchPref = findPreference("turn_app_on_off");
            switchPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    // boolean checked = ((SwitchPreference) preference).isChecked();

                    // Sending broadcast (the broadcast checks if going to continue to service or not:
                    Intent i = new Intent();
                    i.setAction("com.example.s198541_s198611_mappe2.myBroadcast");
                    getActivity().sendBroadcast(i);

                    return true;
                }
            });

            // When the time for the sms is changed we need to change the switch to be off so the user
            // turns it on again and with that activates a new broadcast/updates the broadcast:
            Preference editPref = findPreference("change_time");
            editPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    ((SwitchPreference) switchPref).setChecked(false);
                    return true;
                }
            });

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