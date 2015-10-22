package com.example.s198541_s198611_mappe2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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

    // TODO: Legge inn koden for å sende SMS her??:
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "In MyService", Toast.LENGTH_SHORT).show();

        Calendar cal = Calendar.getInstance();
        int dayNow = cal.get(Calendar.DAY_OF_MONTH);
        int monthNow = cal.get(Calendar.MONTH);

        dbHandler = new DBHandler(this);

        List<Person> personsWithBirthday = dbHandler.getAllPersonsWithBirthday(dayNow, monthNow);

        // just return if no one has birthday:
        if(personsWithBirthday.size() == 0) {

            Log.d("BIRTH CHECK MYSERVICE:", "NO ONE HAS BIRTDAY");

            return super.onStartCommand(intent, flags, startId);
        }

        // if there is someone who has birthday today:
        for(int i = 0; i < personsWithBirthday.size(); i++) {
            // send a sms to all of these:
            String phone = personsWithBirthday.get(i).getPhoneNumber();
            String message = personsWithBirthday.get(i).getMessage();

            SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage(phone, null, message, null, null);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent i = new Intent(this, ResultOfNotificationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);

        /* I MAPPE 2??: GJØRE SLIK AT MAN FÅR EN NOTIFICATION PÅ MOBILEN OM AT
        EN VENN HAR BURSDAG OG NÅR MAN TRYKKER PÅ DENNE SÅ DUKKER DET OPP EN AKTIVITET
        HVOR MAN KAN VELGE Å SENDE EN SMS TIL VEDKOMMENDE ELLER IKKE?
        ER DETTE EN GOD MÅTE Å GJØRE DET PÅ?? ELLER AUTOMATISK SENDE SMS?? */

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