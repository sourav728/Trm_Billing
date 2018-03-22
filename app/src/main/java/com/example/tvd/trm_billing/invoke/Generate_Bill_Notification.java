package com.example.tvd.trm_billing.invoke;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.tvd.trm_billing.services.Generate_Bill_Service;
import com.example.tvd.trm_billing.values.FunctionCalls;

import java.io.File;


public class Generate_Bill_Notification extends BroadcastReceiver {
    FunctionCalls functionCalls;
    File dbfile;

    @Override
    public void onReceive(Context context, Intent intent) {
        functionCalls = new FunctionCalls();

        functionCalls.logStatus("Billing Updating Current Time: "+functionCalls.currentRecpttime());

        if (functionCalls.checkInternetConnection(context)) {
            dbfile = new File(functionCalls.filepath("databases") + File.separator + "mydb.db");
            if (dbfile.exists()) {
                if (!isMyServiceRunning(context, Generate_Bill_Service.class)) {
                    Intent billing_service = new Intent(context, Generate_Bill_Service.class);
                    context.startService(billing_service);
                } else functionCalls.logStatus("Generate Bill Service running...");
            } else functionCalls.logStatus("Billing Database file not found...");
        } else functionCalls.logStatus("No Internet Connection...");
    }

    private boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}