package com.example.s198541_s198611_mappe2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // TODO: Legge inn koden for å sende SMS her??:
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "In MyService", Toast.LENGTH_SHORT).show();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent i = new Intent(this, ResultOfNotificationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);

        /* ! EN NOTIFICATION: IKONET DUKKER OPP ØVERST PÅ MOBILEN OG NÅR MAN DRAR
        NED DENNE KOMMER "Tittel" OG "Tekst" OPP HER + NÅR MAN TRYKKER PÅ DEN SÅ STARTES
        Resultat.java! : */

        /* I MAPPE 2??: GJØRE SLIK AT MAN FÅR EN NOTIFICATION PÅ MOBILEN OM AT
        EN VENN HAR BURSDAG OG NÅR MAN TRYKKER PÅ DENNE SÅ DUKKER DET OPP EN AKTIVITET
        HVOR MAN KAN VELGE Å SENDE EN SMS TIL VEDKOMMENDE ELLER IKKE?
        ER DETTE EN GOD MÅTE Å GJØRE DET PÅ?? ELLER AUTOMATISK SENDE SMS?? */

        Notification notification = new Notification.Builder(this)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_message))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent).build();
        // TODO: Change icon (two lines up)

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);

        return super.onStartCommand(intent, flags, startId);
    }
}