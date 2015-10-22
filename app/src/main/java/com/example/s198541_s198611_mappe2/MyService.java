package com.example.s198541_s198611_mappe2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class MyService extends Service {

    private DBHandler dbHandler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "In MyService", Toast.LENGTH_SHORT).show();

        // Not run this if the app service (switchpreference) has been turned off:
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean serviceOn = sharedPrefs.getBoolean("turn_app_on_off", true);

        if(!serviceOn)
            return super.onStartCommand(intent, flags, startId);

        Calendar cal = Calendar.getInstance();
        int dayNow = cal.get(Calendar.DAY_OF_MONTH);
        int monthNow = cal.get(Calendar.MONTH) + 1; // because of index month different than day f.ex.

        dbHandler = new DBHandler(this);

        List<Person> personsWithBirthday = dbHandler.getAllPersonsWithBirthday(dayNow, monthNow);

        // just return if no one has birthday:
        if(personsWithBirthday.size() == 0)
            return super.onStartCommand(intent, flags, startId);

        // int array of ids for all the persons you're sending sms to (send this info to ResultOfNotificationActivity):
        int[] personsWithBirthdayIds = new int[personsWithBirthday.size()];

        // if there is someone who has birthday today:
        for(int i = 0; i < personsWithBirthday.size(); i++) {
            // send a sms to all of these:
            Person p = personsWithBirthday.get(i);
            String phone = p.getPhoneNumber();
            String message = p.getMessage();
            int id = p.get_ID();
            personsWithBirthdayIds[i] = id;

            SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage(phone, null, message, null, null);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent i = new Intent(this, ResultOfNotificationActivity.class);
        i.putExtra("PERSONIDS", personsWithBirthdayIds);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_message))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent).build();

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);

        return super.onStartCommand(intent, flags, startId);
    }
}