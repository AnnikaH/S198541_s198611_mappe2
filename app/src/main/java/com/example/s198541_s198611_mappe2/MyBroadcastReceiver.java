package com.example.s198541_s198611_mappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        /*if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // runs when the phone starts up

        }*/

        Toast.makeText(context, "In BroadcastReceiver", Toast.LENGTH_SHORT).show();

        // Checking if want to use service (send SMS) or not:

        // Get switch status (on or off) (is placed in Settings) - only send broadcast if is turned on
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean serviceOn = sharedPrefs.getBoolean("turn_app_on_off", true);

        if(serviceOn) {
            // Starting up the service:
            Intent i = new Intent(context, SetService.class);
            context.startService(i);
        }

        // else do nothing
    }
}