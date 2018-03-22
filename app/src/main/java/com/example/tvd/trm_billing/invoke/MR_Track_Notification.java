package com.example.tvd.trm_billing.invoke;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.tvd.trm_billing.services.ClassGPS;
import com.example.tvd.trm_billing.values.FunctionCalls;

import static android.content.Context.MODE_PRIVATE;
import static com.example.tvd.trm_billing.values.ConstantValues.BILLING_FILE_MR;
import static com.example.tvd.trm_billing.values.ConstantValues.DEVICE_ID;
import static com.example.tvd.trm_billing.values.ConstantValues.MR_TRACKING_UPDATE;
import static com.example.tvd.trm_billing.values.ConstantValues.MR_TRACKING_UPDATE_FAIL;
import static com.example.tvd.trm_billing.values.ConstantValues.PREFS_NAME;

public class MR_Track_Notification extends BroadcastReceiver {
    ClassGPS classGPS;
    SendingData sendingData;
    String mr_gpslat="", mr_gpslong="";
    FunctionCalls functionCalls;
    SharedPreferences settings;

    private Handler handler = null;
    {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MR_TRACKING_UPDATE:
                        functionCalls.logStatus("MR Tracking Successfully...");
                        break;

                    case MR_TRACKING_UPDATE_FAIL:
                        functionCalls.logStatus("MR Tracking Failed...");
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        classGPS = new ClassGPS(context);
        sendingData = new SendingData();
        functionCalls = new FunctionCalls();
        settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        GPSlocation();

        functionCalls.logStatus("MR Tracking Current Time: "+functionCalls.currentRecpttime());

        if (functionCalls.checkInternetConnection(context)) {
            if (!TextUtils.isEmpty(settings.getString(BILLING_FILE_MR, ""))) {
                functionCalls.logStatus("Billing MR: "+settings.getString(BILLING_FILE_MR, ""));
                SendingData.MR_Track mrTrack = sendingData.new MR_Track(handler);
                mrTrack.execute(settings.getString(BILLING_FILE_MR, ""), settings.getString(DEVICE_ID, ""), mr_gpslong, mr_gpslat, "b");
            } else functionCalls.logStatus("No Billing MR...");
        } else functionCalls.logStatus("No Internet Connection...");
    }

    private void GPSlocation() {
        if (classGPS.canGetLocation()) {
            double latitude = classGPS.getLatitude();
            double longitude = classGPS.getLongitude();
            mr_gpslat = ""+latitude;
            mr_gpslong = ""+longitude;
        }
    }
}
