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
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

// Is going to do something regularly: send SMS once a day
// This class is going to set AlarmManager to run MyService (class) regularly

public class SetService extends Service {

    private DBHandler dbHandler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /* Når jeg klikker på knappen skal jeg. Hvert 10. sekund vil det komme opp
    I MinService uansett hvor man befinner seg på mobilen.
    Det er det som skal til for at vi skal kunne gå i en database og sjekke om
    noen har bursdag. */
    // NB!!:
    /* HVIS MAN SKAL KUNNE SKRU AV SERVICEN:
    * GJØR SAMME KODE som i onStartCommand, Lager en pending intent og så tar man
    * alarm.cancel(sender med PendingIntenten) - kan lage en ekstra knapp og se at dette
    * fungerer! : */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar cal = Calendar.getInstance();

        dbHandler = new DBHandler(this);

        // Get time for sending sms (set in Settings):
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String time = sharedPrefs.getString("change_time", "");

        Log.d("TIME: ", time);

        // Denne intenten som skal kjøres periodisk:
        Intent i = new Intent(this, MyService.class);
        // TODO: LEGGE KODEN FOR Å SENDE SMS I MyService?



        /* PendingIntent er en intent vi lager som vi vil at andre skal kjøre som oss
        på ett eller annet tidspunkt/med jevne mellomrom (det som må til for å sende
        en SMS en gang i døgnet) */
        PendingIntent pIntent = PendingIntent.getService(this, 0, i, 0);
        // i er intenten vi opprettet

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60 * 1000, pIntent);
        // hvis skal repeteres hvert 10. sekund setter vi ved 10 * 1000
        // hvis skal repeteres en gang i minuttet: 60 * 1000 ?

        // TODO: ENDRE TIL Å GJENTAS PÅ DET ANGITTE KLOKKESLETTET HVER DAG SOM BRUKER HAR SATT + KJØRE NÅR STARTER OPP MOBILEN

        return super.onStartCommand(intent, flags, startId);
    }
}