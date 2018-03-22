package com.example.tvd.trm_billing;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tvd.trm_billing.database.Databasehelper;
import com.example.tvd.trm_billing.database.UploadDatabase;
import com.example.tvd.trm_billing.fragment.HomeFragment;
import com.example.tvd.trm_billing.services.BluetoothService;
import com.example.tvd.trm_billing.services.MR_Service;
import com.example.tvd.trm_billing.values.FunctionCalls;
import com.example.tvd.trm_billing.values.GetSetValues;

import org.apache.commons.lang3.StringUtils;

import static com.example.tvd.trm_billing.values.ConstantValues.BLUETOOTH_RESULT;
import static com.example.tvd.trm_billing.values.ConstantValues.COLLECTION;
import static com.example.tvd.trm_billing.values.ConstantValues.DISCONNECTED;
import static com.example.tvd.trm_billing.values.ConstantValues.GETSET;
import static com.example.tvd.trm_billing.values.ConstantValues.PREFS_NAME;
import static com.example.tvd.trm_billing.values.ConstantValues.RESULT;
import static com.example.tvd.trm_billing.values.ConstantValues.TURNED_OFF;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static AppCompatActivity thisActivity;
    UploadDatabase uploaddb;
    Databasehelper dbh;
    FunctionCalls functionCalls;
    String deviceID="";
    GetSetValues getSetValues;
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        replaceFragment(R.id.nav_home);
        thisActivity = this;


        functionCalls = new FunctionCalls();
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();
        editor.apply();

       /* cbkdb = new CollectionBackupDB(getActivity().getApplicationContext());
        cbkdb.open();*/

        uploaddb = new UploadDatabase(thisActivity.getApplicationContext());
        uploaddb.open();

        dbh = new Databasehelper(thisActivity);
        dbh.openDatabase();

        Intent intent = getIntent();
        getSetValues = (GetSetValues) intent.getSerializableExtra(GETSET);
       /* if (StringUtils.startsWithIgnoreCase(getSetValues.getCollection_started(), "Yes")) {
            Cursor clear_collection_receipts = cbkdb.clear_Collection_Output();
            clear_collection_receipts.moveToNext();
        }*/

        if (StringUtils.startsWithIgnoreCase(settings.getString(COLLECTION, ""), "Success")) {
            dbh.updateforCollection("Y");
        }
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        deviceID = tm.getDeviceId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.hambergercolor));
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onDestroy() {
        if (!Build.MANUFACTURER.matches("alps")) {
            if (isMyServiceRunning(BluetoothService.class)) {
                functionCalls.logStatus("Service Stopped");
                Intent bluetoothservice = new Intent(thisActivity, BluetoothService.class);
                stopService(bluetoothservice);
            } else functionCalls.logStatus("Service Not Started");
            unregisterReceiver(mReceiver);
        }
        super.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter(BLUETOOTH_RESULT));
        if (!isMyServiceRunning(BluetoothService.class)) {
            functionCalls.logStatus("Service not running");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    functionCalls.logStatus("Service Started");
                    Intent bluetoothservice = new Intent(thisActivity, BluetoothService.class);
                   startService(bluetoothservice);
                }
            }, 500);
        } else functionCalls.logStatus("Service running");
        if (!isMyServiceRunning(MR_Service.class)) {
            functionCalls.logStatus("MR Service not running");
            Intent service = new Intent(thisActivity, MR_Service.class);
            startService(service);
        } else functionCalls.logStatus("MR Service Running in background");
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        onNavigationItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        replaceFragment(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void replaceFragment(int id)
    {
        Fragment fragment = null;
        if (id == R.id.nav_home) {
            fragment = new HomeFragment();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        if (fragment!= null)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }

    }
    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            FunctionCalls fcall = new FunctionCalls();
            String status = intent.getStringExtra("message");
            switch (status) {
                case RESULT:
                    String printer = intent.getStringExtra("printer");
                    fcall.showtoast(thisActivity, printer+" Bluetooth Printer Connected");
                    fcall.logStatus("Handler Printer Broadcast receiving Connected from service");
                    break;

                case DISCONNECTED:
                    fcall.showtoast(thisActivity, "Bluetooth Printer Disconnected");
                    fcall.logStatus("Handler Printer Broadcast receiving Disconnected from service");
                    break;

                case TURNED_OFF:
                    fcall.showtoast(thisActivity, "Please Turn On the printer and proceed...");
                    fcall.logStatus("Handler Printer Broadcast receiving TurnedOff from service");
                    break;
            }
        }
    };
    public GetSetValues gettersetterValues() {
        return this.getSetValues;
    }
}
