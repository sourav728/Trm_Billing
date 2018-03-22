package com.example.tvd.trm_billing.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import com.example.tvd.trm_billing.database.Databasehelper;
import com.example.tvd.trm_billing.invoke.SendingData;
import com.example.tvd.trm_billing.values.FunctionCalls;

import java.io.File;

import static com.example.tvd.trm_billing.values.ConstantValues.DOWNLOAD_FILE_NAME;
import static com.example.tvd.trm_billing.values.ConstantValues.ONLINE_BILL_PASS_FAILURE;
import static com.example.tvd.trm_billing.values.ConstantValues.ONLINE_BILL_PASS_SUCCESS;
import static com.example.tvd.trm_billing.values.ConstantValues.ONLINE_UPDATE_FLAG_SUCCESS;
import static com.example.tvd.trm_billing.values.ConstantValues.PREFS_NAME;

public class Generate_Bill_Service extends Service {
    Databasehelper billeddb;
    FunctionCalls functionCalls;
    String updatedid="";
    SharedPreferences sPref;
    SendingData sendingData;

    private Handler handler = null;
    {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ONLINE_BILL_PASS_SUCCESS:
                        billeddb.onlinebilledrecord(updatedid, handler);
                        break;

                    case ONLINE_BILL_PASS_FAILURE:
                        getbilleddata(getApplicationContext());
                        break;

                    case ONLINE_UPDATE_FLAG_SUCCESS:
                        if (functionCalls.checkInternetConnection(Generate_Bill_Service.this))
                            getbilleddata(getApplicationContext());
                        else {
                            functionCalls.logStatus("Service No Internet Connection...");
                            Generate_Bill_Service.this.stopSelf();
                        }
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        functionCalls = new FunctionCalls();
        sendingData = new SendingData();
        sPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        billeddb = new Databasehelper(this);
        billeddb.openDatabase();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (functionCalls.checkInternetConnection(this))
            getbilleddata(getApplicationContext());
        else {
            functionCalls.logStatus("Service No Internet Connection...");
            this.stopSelf();
        }
        return Service.START_STICKY;
    }

    private void getbilleddata(Context context) {
        File dbfile = new File(functionCalls.filepath("databases") + File.separator + "mydb.db");
        if (dbfile.exists()) {
            Cursor data = billeddb.getbilledvalues();
            if (data.getCount() > 0) {
                data.moveToNext();
                String MONTH = functionCalls.getCursorValue(data, "MONTH");
                String READDATE = functionCalls.getCursorValue(data, "READDATE");
                String RRNO = functionCalls.getCursorValue(data, "RRNO");
                String NAME = functionCalls.getCursorValue(data, "NAME");
                String ADD1 = functionCalls.getCursorValue(data, "ADD1");
                String TARIFF = functionCalls.getCursorValue(data, "TARIFF");
                String MF = functionCalls.getCursorValue(data, "MF");
                String PREVSTAT = functionCalls.getCursorValue(data, "PREVSTAT");
                String AVGCON = functionCalls.getCursorValue(data, "AVGCON");
                String LINEMIN = functionCalls.getCursorValue(data, "LINEMIN");
                String SANCHP = functionCalls.getCursorValue(data, "SANCHP");
                String SANCKW = functionCalls.getCursorValue(data, "SANCKW");
                String PRVRED = functionCalls.getCursorValue(data, "PRVRED");
                String FR = functionCalls.getCursorValue(data, "FR");
                String IR = functionCalls.getCursorValue(data, "IR");
                String DLCOUNT = functionCalls.getCursorValue(data, "DLCOUNT");
                String ARREARS = functionCalls.getCursorValue(data, "ARREARS");
                String PF_FLAG = functionCalls.getCursorValue(data, "PF_FLAG");
                String BILLFOR = functionCalls.getCursorValue(data, "BILLFOR");
                String MRCODE = functionCalls.getCursorValue(data, "MRCODE");
                String LEGFOL = functionCalls.getCursorValue(data, "LEGFOL");
                String ODDEVEN = functionCalls.getCursorValue(data, "ODDEVEN");
                String SSNO = functionCalls.getCursorValue(data, "SSNO");
                String CONSNO = functionCalls.getCursorValue(data, "CONSNO");
                String REBATE_FLAG = functionCalls.getCursorValue(data, "REBATE_FLAG");
                String RREBATE = functionCalls.getCursorValue(data, "RREBATE");
                String EXTRA1 = functionCalls.getCursorValue(data, "EXTRA1");
                String DATA1 = functionCalls.getCursorValue(data, "DATA1");
                String EXTRA2 = functionCalls.getCursorValue(data, "EXTRA2");
                String DATA2 = functionCalls.getCursorValue(data, "DATA2");
                String PH_NO = functionCalls.getCursorValue(data, "PH_NO");
                String DEPOSIT = functionCalls.getCursorValue(data, "DEPOSIT");
                String MTRDIGIT = functionCalls.getCursorValue(data, "MTRDIGIT");
                String ASDAMT = functionCalls.getCursorValue(data, "ASDAMT");
                String IODAMT = functionCalls.getCursorValue(data, "IODAMT");
                String PFVAL = functionCalls.getCursorValue(data, "PFVAL");
                String BMDVAL = functionCalls.getCursorValue(data, "BMDVAL");
                String BILL_NO = functionCalls.getCursorValue(data, "BILL_NO");
                String INTEREST_AMT = functionCalls.getCursorValue(data, "INTEREST_AMT");
                String CAP_FLAG = functionCalls.getCursorValue(data, "CAP_FLAG");
                String TOD_FLAG = functionCalls.getCursorValue(data, "TOD_FLAG");
                String TOD_PREVIOUS1 = functionCalls.getCursorValue(data, "TOD_PREVIOUS1");
                String TOD_PREVIOUS3 = functionCalls.getCursorValue(data, "TOD_PREVIOUS3");
                String INT_ON_DEP = functionCalls.getCursorValue(data, "INT_ON_DEP");
                String SO_FEEDER_TC_POLE = functionCalls.getCursorValue(data, "SO_FEEDER_TC_POLE");
                String TARIFF_NAME = functionCalls.getCursorValue(data, "TARIFF_NAME");
                String PREV_READ_DATE = functionCalls.getCursorValue(data, "PREV_READ_DATE");
                String BILL_DAYS = functionCalls.getCursorValue(data, "BILL_DAYS");
                String MTR_SERIAL_NO = functionCalls.getCursorValue(data, "MTR_SERIAL_NO");
                String FDRNAME = functionCalls.getCursorValue(data, "FDRNAME");
                String TCCODE = functionCalls.getCursorValue(data, "TCCODE");
                String MTR_FLAG = functionCalls.getCursorValue(data, "MTR_FLAG");
                String PRES_RDG = functionCalls.getCursorValue(data, "PRES_RDG");
                String PRES_STS = functionCalls.getCursorValue(data, "PRES_STS");
                String UNITS = functionCalls.getCursorValue(data, "UNITS");
                String FIX = functionCalls.getCursorValue(data, "FIX");
                String ENGCHG = functionCalls.getCursorValue(data, "ENGCHG");
                String REBATE_AMOUNT = functionCalls.getCursorValue(data, "REBATE_AMOUNT");
                String TAX_AMOUNT = functionCalls.getCursorValue(data, "TAX_AMOUNT");
                String BMD_PENALTY = functionCalls.getCursorValue(data, "BMD_PENALTY");
                String PF_PENALTY = functionCalls.getCursorValue(data, "PF_PENALTY");
                String PAYABLE = functionCalls.getCursorValue(data, "PAYABLE");
                String BILLDATE = functionCalls.getCursorValue(data, "BILLDATE");
                String BILLTIME = functionCalls.getCursorValue(data, "BILLTIME");
                String TOD_CURRENT1 = functionCalls.getCursorValue(data, "TOD_CURRENT1");
                String TOD_CURRENT3 = functionCalls.getCursorValue(data, "TOD_CURRENT3");
                String GOK_SUBSIDY = functionCalls.getCursorValue(data, "GOK_SUBSIDY");
                String DEM_REVENUE = functionCalls.getCursorValue(data, "DEM_REVENUE");
                String GPS_LAT = functionCalls.getCursorValue(data, "GPS_LAT");
                String GPS_LONG = functionCalls.getCursorValue(data, "GPS_LONG");
                String IMGADD = functionCalls.getCursorValue(data, "IMGADD");
                String PAYABLE_REAL = functionCalls.getCursorValue(data, "PAYABLE_REAL");
                String PAYABLE_PROFIT = functionCalls.getCursorValue(data, "PAYABLE_PROFIT");
                String PAYABLE_LOSS = functionCalls.getCursorValue(data, "PAYABLE_LOSS");
                String BILL_PRINTED = functionCalls.getCursorValue(data, "BILL_PRINTED");
                String FSLAB1 = functionCalls.getCursorValue(data, "FSLAB1");
                String FSLAB2 = functionCalls.getCursorValue(data, "FSLAB2");
                String FSLAB3 = functionCalls.getCursorValue(data, "FSLAB3");
                String FSLAB4 = functionCalls.getCursorValue(data, "FSLAB4");
                String FSLAB5 = functionCalls.getCursorValue(data, "FSLAB5");
                String ESLAB1 = functionCalls.getCursorValue(data, "ESLAB1");
                String ESLAB2 = functionCalls.getCursorValue(data, "ESLAB2");
                String ESLAB3 = functionCalls.getCursorValue(data, "ESLAB3");
                String ESLAB4 = functionCalls.getCursorValue(data, "ESLAB4");
                String ESLAB5 = functionCalls.getCursorValue(data, "ESLAB5");
                String ESLAB6 = functionCalls.getCursorValue(data, "ESLAB6");
                String CHARITABLE_RBT_AMT = functionCalls.getCursorValue(data, "CHARITABLE_RBT_AMT");
                String SOLAR_RBT_AMT = functionCalls.getCursorValue(data, "SOLAR_RBT_AMT");
                String FL_RBT_AMT = functionCalls.getCursorValue(data, "FL_RBT_AMT");
                String HANDICAP_RBT_AMT = functionCalls.getCursorValue(data, "HANDICAP_RBT_AMT");
                String PL_RBT_AMT = functionCalls.getCursorValue(data, "PL_RBT_AMT");
                String IPSET_RBT_AMT = functionCalls.getCursorValue(data, "IPSET_RBT_AMT");
                String REBATEFROMCCB_AMT = functionCalls.getCursorValue(data, "REBATEFROMCCB_AMT");
                String TOD_CHARGES = functionCalls.getCursorValue(data, "TOD_CHARGES");
                String PF_PENALITY_AMT = functionCalls.getCursorValue(data, "PF_PENALITY_AMT");
                String EXLOAD_MDPENALITY = functionCalls.getCursorValue(data, "EXLOAD_MDPENALITY");
                String CURR_BILL_AMOUNT = functionCalls.getCursorValue(data, "CURR_BILL_AMOUNT");
                String ROUNDING_AMOUNT = functionCalls.getCursorValue(data, "ROUNDING_AMOUNT");
                String DUE_DATE = functionCalls.getCursorValue(data, "DUE_DATE");
                String DISCONN_DATE = functionCalls.getCursorValue(data, "DISCONN_DATE");
                String CREADJ = functionCalls.getCursorValue(data, "CREADJ");
                String PREADKVAH = functionCalls.getCursorValue(data, "PREADKVAH");
                updatedid = functionCalls.getCursorValue(data, "_id");
                String ONLINE_FLAG = functionCalls.getCursorValue(data, "ONLINE_FLAG");
                String BATTERY_CHARGE = functionCalls.getCursorValue(data, "BATTERY_CHARGE");
                String SIGNAL_STRENGTH = functionCalls.getCursorValue(data, "SIGNAL_STRENGTH");

                String Filename = sPref.getString(DOWNLOAD_FILE_NAME, "");
                if (!TextUtils.isEmpty(Filename)) {
                    if (functionCalls.isInternetOn(context)) {
                        SendingData.UpdateBilledRecords updateBilledRecords = sendingData.new UpdateBilledRecords(handler);
                        updateBilledRecords.execute(MONTH, READDATE, RRNO, NAME, ADD1, TARIFF, MF, PREVSTAT, AVGCON, LINEMIN, SANCHP, SANCKW,
                                PRVRED, FR, IR, DLCOUNT, ARREARS, PF_FLAG, BILLFOR, MRCODE, LEGFOL, ODDEVEN, SSNO, CONSNO, PH_NO, REBATE_FLAG, RREBATE,
                                EXTRA1, DATA1, EXTRA2, DATA2, DEPOSIT, MTRDIGIT, ASDAMT, IODAMT, PFVAL, BMDVAL, BILL_NO, INTEREST_AMT, CAP_FLAG,
                                TOD_FLAG, TOD_PREVIOUS1, TOD_PREVIOUS3, INT_ON_DEP, SO_FEEDER_TC_POLE, TARIFF_NAME, PREV_READ_DATE, BILL_DAYS,
                                MTR_SERIAL_NO, "0", FDRNAME, TCCODE, MTR_FLAG, PRES_RDG, PRES_STS, UNITS, FIX, ENGCHG, REBATE_AMOUNT,
                                TAX_AMOUNT, BMD_PENALTY, PF_PENALTY, PAYABLE, BILLDATE, BILLTIME, TOD_CURRENT1, TOD_CURRENT3, GOK_SUBSIDY, DEM_REVENUE,
                                GPS_LAT, GPS_LONG, ONLINE_FLAG, BATTERY_CHARGE, SIGNAL_STRENGTH, IMGADD, PAYABLE_REAL, PAYABLE_PROFIT, PAYABLE_LOSS,
                                BILL_PRINTED, FSLAB1, FSLAB2, FSLAB3, FSLAB4, FSLAB5, ESLAB1, ESLAB2, ESLAB3, ESLAB4, ESLAB5, ESLAB6,
                                CHARITABLE_RBT_AMT, SOLAR_RBT_AMT, FL_RBT_AMT, HANDICAP_RBT_AMT, PL_RBT_AMT, IPSET_RBT_AMT, REBATEFROMCCB_AMT,
                                TOD_CHARGES, PF_PENALITY_AMT, EXLOAD_MDPENALITY, CURR_BILL_AMOUNT, ROUNDING_AMOUNT, DUE_DATE, DISCONN_DATE, CREADJ,
                                PREADKVAH, Filename, updatedid, "0");
                    }
                }
            } else this.stopSelf();
        } else this.stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
