package com.example.tvd.trm_billing.invoke;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.example.tvd.trm_billing.R;
import com.example.tvd.trm_billing.activities.SplashActivity;
import com.example.tvd.trm_billing.values.FunctionCalls;
import com.example.tvd.trm_billing.values.GetSetValues;

import static android.content.Context.MODE_PRIVATE;
import static com.example.tvd.trm_billing.values.ConstantValues.BILLING_FILE_MR;
import static com.example.tvd.trm_billing.values.ConstantValues.DEVICE_ID;
import static com.example.tvd.trm_billing.values.ConstantValues.LOGIN_SUCCESS;
import static com.example.tvd.trm_billing.values.ConstantValues.MR_PASSWORD;
import static com.example.tvd.trm_billing.values.ConstantValues.PREFS_NAME;


public class Apk_Notification extends BroadcastReceiver {
    FunctionCalls functionCalls;
    GetSetValues getSetValues;
    SendingData sendingData;
    String curr_version="";
    Context Notification_context;
    SharedPreferences settings;

    private static Handler handler = null;
    {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case LOGIN_SUCCESS:
                        functionCalls.logStatus("Server Version: "+getSetValues.getApp_version());
                        if (functionCalls.compare(curr_version, getSetValues.getApp_version()))
                            notification(getContext().getApplicationContext());
                        break;

                }
            }
        };
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification_context = context;
        functionCalls = new FunctionCalls();
        getSetValues = new GetSetValues();
        sendingData = new SendingData();
        settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        functionCalls.logStatus("Apk Notification Current Time: "+functionCalls.currentRecpttime());

        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            curr_version = pInfo.versionName;
            functionCalls.logStatus("Current Version: "+curr_version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (functionCalls.checkInternetConnection(context)) {
            functionCalls.logStatus("Checking for newer version of Smart Billing application...");
            SendingData.MR_Login mrLogin = sendingData.new MR_Login(handler, getSetValues);
            mrLogin.execute(settings.getString(BILLING_FILE_MR, ""), settings.getString(DEVICE_ID, ""), settings.getString(MR_PASSWORD, ""));
        } else functionCalls.logStatus("No Internet Connection...");
    }

    private Context getContext() {
        return this.Notification_context;
    }

    private void notification(Context context) {
        Intent in = new Intent(context, SplashActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, in, 0);
        //build notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.notification)
                        .setContentTitle("Smart Billing")
                        .setContentText("New version of Smart Billing is available to download")
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
