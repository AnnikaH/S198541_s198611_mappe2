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

        Toast.makeText(context, "Updating..", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(context, SetService.class);
        context.startService(i);
    }
}