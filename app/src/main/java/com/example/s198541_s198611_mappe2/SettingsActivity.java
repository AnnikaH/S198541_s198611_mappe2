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