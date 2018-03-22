package com.example.tvd.trm_billing.invoke;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.example.tvd.trm_billing.R;
import com.example.tvd.trm_billing.activities.SplashActivity;
import com.example.tvd.trm_billing.values.FunctionCalls;
import com.example.tvd.trm_billing.values.GetSetValues;

import java.io.File;
import java.text.ParseException;

import static android.content.Context.MODE_PRIVATE;
import static com.example.tvd.trm_billing.values.ConstantValues.BILLING_DB_FILE_DOWNLOAD;
import static com.example.tvd.trm_billing.values.ConstantValues.BILLING_FILE_MR;
import static com.example.tvd.trm_billing.values.ConstantValues.DEVICE_ID;
import static com.example.tvd.trm_billing.values.ConstantValues.NOTIFY_BILLING_DATE;
import static com.example.tvd.trm_billing.values.ConstantValues.PREFS_NAME;


public class Billing_File_Notification extends BroadcastReceiver {
    FunctionCalls functionCalls;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    String selected_date="";
    SendingData sendingData;
    Context Billing_context;
    GetSetValues getSetValues;

    private static Handler handler = null;
    {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case BILLING_DB_FILE_DOWNLOAD:
                        notification(getContext());
                        break;
                }
            }
        };
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Billing_context = context;
        functionCalls = new FunctionCalls();
        sendingData = new SendingData();
        getSetValues = new GetSetValues();

        settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();
        editor.apply();

        try {
            selected_date = functionCalls.dateSet();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        editor.putString(NOTIFY_BILLING_DATE, selected_date);
        editor.commit();

        functionCalls.logStatus("Billing File Notification Current Time: "+functionCalls.currentRecpttime());

        if (functionCalls.checkInternetConnection(context)) {
            File dbfile = new File(functionCalls.filepath("databases") + File.separator + "mydb.db");
            if (!dbfile.exists()) {
                SendingData.Download_Billing_datafile downloading = sendingData.new Download_Billing_datafile(handler);
                downloading.execute(settings.getString(BILLING_FILE_MR, ""), settings.getString(DEVICE_ID, ""), selected_date);
            }
        } else functionCalls.logStatus("No Internet Connection...");
    }

    private Context getContext() {
        return this.Billing_context;
    }

    private void notification(Context context) {
        Intent in = new Intent(context, SplashActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, in, 0);
        //build notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.notification)
                        .setContentTitle("Smart Billing")
                        .setContentText("New Billing file is available to download...")
                        .setDefaults(Notification.DEFAULT_ALL) // must requires VIBRATE permission
                        .setPriority(NotificationCompat.PRIORITY_HIGH) //must give priority to High, Max which will considered as heads-up notification
                        .setContentIntent(pi)
                        .setAutoCancel(true);
        // Gets an instance of the NotificationManager service
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //to post your notification to the notification bar with a id. If a notification with same id already exists, it will get replaced with updated information.
        notificationManager.notify(0, builder.build());
    }
}
