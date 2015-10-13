package com.example.s198541_s198611_mappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "In BroadcastReceiver", Toast.LENGTH_SHORT).show();

        // Starting up the service:
        Intent i = new Intent(context, SetService.class);
        context.startService(i);
    }
}