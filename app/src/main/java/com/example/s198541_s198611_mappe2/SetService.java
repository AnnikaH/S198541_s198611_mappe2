package com.example.s198541_s198611_mappe2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

// Is going to do something regularly: send SMS once a day
// This class is going to set AlarmManager to run MyService (class) regularly

public class SetService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Get if sending sms is turned on or off (set in Settings):
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean serviceOn = sharedPrefs.getBoolean("turn_app_on_off", true);

        if(!serviceOn) {
            // Then we want to cancel the sms service:
            Intent i = new Intent(this, MyService.class);
            PendingIntent pIntent = PendingIntent.getService(this, 0, i, 0);
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pIntent);

            Log.d("ALARM: ", "CANCELED");

            return super.onStartCommand(intent, flags, startId);
        }

        // Get time for sending sms (set in Settings):
        String time = sharedPrefs.getString("change_time", "");
        String[] parts = time.split(":");
        int chosenHour = Integer.parseInt(parts[0]);
        int chosenMinute = Integer.parseInt(parts[1]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, chosenHour);
        cal.set(Calendar.MINUTE, chosenMinute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // Intent to the class/code that will run periodically:
        Intent i = new Intent(this, MyService.class);
        PendingIntent pIntent = PendingIntent.getService(this, 0, i, 0);

        // Run code in MyService once each day from the time set by the user:
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);

        return super.onStartCommand(intent, flags, startId);
    }
}