package com.example.tvd.trm_billing.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.example.tvd.trm_billing.invoke.Apk_Notification;
import com.example.tvd.trm_billing.invoke.Billing_File_Notification;
import com.example.tvd.trm_billing.invoke.Generate_Bill_Notification;
import com.example.tvd.trm_billing.invoke.MR_Track_Notification;
import com.example.tvd.trm_billing.values.FunctionCalls;

public class MR_Service extends Service {
    FunctionCalls functionCalls;

    public MR_Service() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        functionCalls = new FunctionCalls();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        start_mr_track();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start_billing_file_Receiver();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        start_Version_Receiver();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                start_bills_updating();
                            }
                        }, 1000 * 30);
                    }
                }, 1000 * 30);
            }
        }, 1000 * 30);
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop_Version_Receiver();
        stop_billing_file_Receiver();
        stop_mr_track();
        stop_bills_updating();
    }

    private void start_Version_Receiver() {
        functionCalls.logStatus("Version_receiver Checking..");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Apk_Notification.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmRunning) {
            functionCalls.logStatus("Version_receiver Started..");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (60000 * 10), pendingIntent);
        } else functionCalls.logStatus("Version_receiver Already running..");
    }

    private void stop_Version_Receiver() {
        functionCalls.logStatus("Version_receiver Checking..");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Apk_Notification.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning) {
            functionCalls.logStatus("Version_receiver Stopping..");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            alarmManager.cancel(pendingIntent);
        } else functionCalls.logStatus("Version_receiver Not yet Started..");
    }

    private void start_billing_file_Receiver() {
        functionCalls.logStatus("Billing file Checking..");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Billing_File_Notification.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmRunning) {
            functionCalls.logStatus("Billing file Receiver Started..");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (60000 * 8), pendingIntent);
        } else functionCalls.logStatus("Billing file Receiver Already running..");
    }

    private void stop_billing_file_Receiver() {
        functionCalls.logStatus("Billing file Checking..");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Billing_File_Notification.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning) {
            functionCalls.logStatus("Billing file Receiver Stopping..");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            alarmManager.cancel(pendingIntent);
        } else functionCalls.logStatus("Billing file Receiver Not yet Started..");
    }

    private void start_mr_track() {
        functionCalls.logStatus("MR Tracking Checking...");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), MR_Track_Notification.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmRunning) {
            functionCalls.logStatus("MR Tracking Started..");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (60000 * 5), pendingIntent);
        } else functionCalls.logStatus("MR Tracking Already running..");
    }

    private void stop_mr_track() {
        functionCalls.logStatus("MR Tracking Checking...");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), MR_Track_Notification.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning) {
            functionCalls.logStatus("MR Tracking Stopping..");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            alarmManager.cancel(pendingIntent);
        } else functionCalls.logStatus("MR Tracking Not yet Started..");
    }

    private void start_bills_updating() {
        functionCalls.logStatus("Bills Updating Checking...");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Generate_Bill_Notification.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmRunning) {
            functionCalls.logStatus("Bills Updating Started..");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (60000 * 15), pendingIntent);
        } else functionCalls.logStatus("Bills Updating Already running..");
    }

    private void stop_bills_updating() {
        functionCalls.logStatus("Bills Updating Checking...");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Generate_Bill_Notification.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning) {
            functionCalls.logStatus("Bills Updating Stopping..");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            alarmManager.cancel(pendingIntent);
        } else functionCalls.logStatus("Bills Updating Not yet Started..");
    }
}
