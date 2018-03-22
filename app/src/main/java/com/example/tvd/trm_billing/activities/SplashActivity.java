package com.example.tvd.trm_billing.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.trm_billing.MainActivity;
import com.example.tvd.trm_billing.R;
import com.example.tvd.trm_billing.database.CollectionBackupDB;
import com.example.tvd.trm_billing.database.Databasehelper;
import com.example.tvd.trm_billing.database.UploadDatabase;
import com.example.tvd.trm_billing.fragment.HomeFragment;
import com.example.tvd.trm_billing.values.FunctionCalls;
import com.example.tvd.trm_billing.values.GetSetValues;

import java.io.File;
import java.text.ParseException;
import java.util.Date;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.tvd.trm_billing.values.ConstantValues.FTP_HOST;
import static com.example.tvd.trm_billing.values.ConstantValues.FTP_PASS;
import static com.example.tvd.trm_billing.values.ConstantValues.FTP_PORT;
import static com.example.tvd.trm_billing.values.ConstantValues.FTP_USER;
import static com.example.tvd.trm_billing.values.ConstantValues.GETSET;

public class SplashActivity extends Activity {
    private final String NAMESPACE = "http://tempuri.org/";
    private final String URL = "http://106.51.57.253:8086/Service.asmx";
    private final String DOWNLOADUPLOADURL = "http://202.38.181.250:999/Android_Upload_Download.asmx";
    private final String SOAP_ACTION = "http://tempuri.org/";

    public static final int DLG_DATE = 2;
    public static final int RequestPermissionCode = 1;

    Databasehelper dbh;
    CollectionBackupDB cbkdb;
    UploadDatabase uploaddb;

    //	Settings st;
    FunctionCalls fcall;
    BluetoothAdapter deviceadapter;

    GetSetValues getSetValues;

    private static final int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_splash);

        fcall = new FunctionCalls();
        getSetValues = new GetSetValues();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkPermissionsMandAbove();
            }
        }, 1000);
    }

    private void checkforservicedetails() {
        Cursor c = cbkdb.getSERVICE();
        if (c.getCount() > 0) {
            cbkdb.updateSERVICE(NAMESPACE, SOAP_ACTION, URL, DOWNLOADUPLOADURL);
        }
        else {
            cbkdb.insertInSERVICE(NAMESPACE, SOAP_ACTION, URL, DOWNLOADUPLOADURL);
        }
    }

    private void checkforFTPdetails() {
        Cursor c = cbkdb.getFTP_SERVER();
        if (c.getCount() > 0) {
            cbkdb.updateFTP_SERVER(FTP_HOST, FTP_USER, FTP_PASS, ""+FTP_PORT);
        }
        else {
            cbkdb.insertInFTP_SERVER(FTP_HOST, FTP_USER, FTP_PASS, ""+FTP_PORT);
        }
    }

    private void checkforlogin() {
        Cursor c = cbkdb.getLOGIN_DETAILS();
        if (c.getCount() > 0) {
            c.moveToNext();
            Log.d("Splash Activity", "login data available");
            String date = c.getString(c.getColumnIndex("DATE1"));
            try {
                Log.d("Splash Activity", "Checking dates");
                Date logindate = fcall.selectiondate(date);
                Date currentdate = fcall.selectiondate(fcall.dateSet());
                if (logindate.equals(currentdate)) {
                    Log.d("Splash Activity", "Login date matching");
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            //This method will be executed once the timer is over
                            //Start your app main activity
                            Log.d("Main_Checking", "Checking_Mydb_file");
                            try {
                                File MYDB = fcall.filestorepath("databases", "mydb.db");
                                if (MYDB.exists()) {
                                    Log.d("Main_Database", "Exists");
                                    Log.d("Main", "Moving to Tabact activity");
                                    movetotabact();
                                }
                                else {
                                    Log.d("Main_Database", "Does not Exists");
                                    Log.d("Main", "Moving to Main activity");
                                    movetoMain();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, SPLASH_TIME_OUT);
                }
                else {
                    if (currentdate.after(logindate)) {
                        Log.d("Splash Activity", "Login date is more");
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                //This method will be executed once the timer is over
                                //Start your app main activity
                                movetoMain();
                            }
                        }, SPLASH_TIME_OUT);
                    }
                    else {
                        if (currentdate.before(logindate)) {
                            Log.d("Splash Activity", "Login date is below");
                            showdialog(DLG_DATE);
                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            Log.d("Splash Activity", "login data not available");
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    //This method will be executed once the timer is over
                    //Start your app main activity
                    movetoMain();
                }
            }, SPLASH_TIME_OUT);
        }
    }

    private void movetoMain() {
        Intent in = new Intent(SplashActivity.this, MainActivity.class);
        in.putExtra(GETSET, getSetValues);
        startActivity(in);
        finish();
    }

    private void movetotabact() {
       /* Intent in = new Intent(SplashActivity.this, Tabact.class);
        in.putExtra(GETSET, getSetValues);
        startActivity(in);*/
        Intent intent = new Intent(SplashActivity.this, HomeFragment.class);
        intent.putExtra(GETSET, getSetValues);
        startActivity(intent);

    }

    @SuppressLint("InflateParams")
    protected void showdialog(int id) {
        Dialog d = null;
        switch (id) {
            case DLG_DATE:
                AlertDialog.Builder date = new AlertDialog.Builder(SplashActivity.this,
                        AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                date.setTitle("DATE");
                RelativeLayout rl = (RelativeLayout) getLayoutInflater().inflate(R.layout.dialog, null);
                TextView tvdate = (TextView) rl.findViewById(R.id.textView1);
                tvdate.setText("Please make the device date for current date before login");
                date.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });
                date.setView(rl);
                d = date.create();
                d.show();
                break;
        }
    }

    private void startup() {
        cbkdb = new CollectionBackupDB(this);
        cbkdb.open();

        try {
            uploaddb = new UploadDatabase(this);
            uploaddb.open();
        } catch (SQLException e) {
            e.printStackTrace();
            fcall.deletefile(""+fcall.filestorepath("CollectionData", "upload.db"));
            fcall.logStatus("Upload file deleted");
            uploaddb = new UploadDatabase(this);
            uploaddb.open();
        }

        if (!Build.MANUFACTURER.matches("alps")) {
            deviceadapter = BluetoothAdapter.getDefaultAdapter();
            deviceadapter.enable();
        }

        checkforservicedetails();

        checkforFTPdetails();

        checkforlogin();
    }

    @TargetApi(23)
    public void checkPermissionsMandAbove() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 23) {
            if (checkPermission()) {
                startup();
            } else {
                requestPermission();
            }
        } else {
            startup();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(SplashActivity.this, new String[]
                {
                        READ_PHONE_STATE,
                        WRITE_EXTERNAL_STORAGE,
                        ACCESS_FINE_LOCATION,
                        WRITE_CONTACTS
                }, RequestPermissionCode);
    }

    private boolean checkPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int FourthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_CONTACTS);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FourthPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean ReadPhoneStatePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadStoragePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadLocationPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadLogsPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    if (ReadPhoneStatePermission && ReadStoragePermission && ReadLocationPermission && ReadLogsPermission) {
                        startup();
                    } else {
                        Toast.makeText(SplashActivity.this, "Required All Permissions to granted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
        }
    }
}




