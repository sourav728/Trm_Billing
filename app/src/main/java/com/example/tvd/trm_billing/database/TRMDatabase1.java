package com.example.tvd.trm_billing.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tvd.trm_billing.values.FunctionCalls;

import java.io.File;

public class TRMDatabase1 {
    MyHelper mh ;
    SQLiteDatabase sdb ;
    FunctionCalls functionCalls = new FunctionCalls();
    String dbpath ="", dbfolder="databases", db_name="backup.db";
    File databasefile = null;

    public TRMDatabase1(Context context) {
        try {
            databasefile = functionCalls.trm_backup1_filestorepath(dbfolder, db_name);
            functionCalls.logStatus("TRM Backup 1 Not Exists");
            dbpath = functionCalls.trm_backup1_filepath(dbfolder) + File.separator + db_name;
            mh = new MyHelper(context, dbpath, null, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() {
        sdb = mh.getWritableDatabase();
    }

    public void close() {
        sdb.close();
    }

    public class MyHelper extends SQLiteOpenHelper {
        public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("Create table MAST_OUT (MONTH TEXT, READDATE TEXT, RRNO TEXT, NAME TEXT, ADD1 TEXT, " +
                    "TARIFF TEXT, MF TEXT, PREVSTAT TEXT, AVGCON TEXT, LINEMIN TEXT, SANCHP TEXT, " +
                    "SANCKW TEXT, PRVRED TEXT, FR TEXT, IR TEXT, DLCOUNT TEXT, ARREARS TEXT, PF_FLAG TEXT, " +
                    "BILLFOR TEXT, MRCODE TEXT, LEGFOL TEXT, ODDEVEN TEXT, SSNO TEXT, CONSNO TEXT, " +
                    "REBATE_FLAG TEXT, RREBATE TEXT, EXTRA1 TEXT, DATA1 TEXT, EXTRA2 TEXT, DATA2 TEXT, " +
                    "PH_NO TEXT, DEPOSIT TEXT, MTRDIGIT TEXT, ASDAMT TEXT, IODAMT TEXT, PFVAL TEXT, " +
                    "BMDVAL TEXT, BILL_NO TEXT, INTEREST_AMT TEXT, CAP_FLAG TEXT, TOD_FLAG TEXT, " +
                    "TOD_PREVIOUS1 TEXT, TOD_PREVIOUS3 TEXT, INT_ON_DEP TEXT, SO_FEEDER_TC_POLE TEXT, " +
                    "TARIFF_NAME TEXT, PREV_READ_DATE TEXT, BILL_DAYS TEXT, MTR_SERIAL_NO TEXT, " +
                    "CHQ_DISSHONOUR_FLAG TEXT, CHQ_DISHONOUR_DATE TEXT, FDRNAME TEXT, TCCODE TEXT, " +
                    "MTR_FLAG TEXT, PRES_RDG TEXT, PRES_STS TEXT, UNITS TEXT, FIX TEXT, ENGCHG TEXT, " +
                    "REBATE_AMOUNT TEXT, TAX_AMOUNT TEXT, BMD_PENALTY TEXT, PF_PENALTY TEXT, PAYABLE TEXT, " +
                    "BILLDATE TEXT, BILLTIME TEXT, TOD_CURRENT1 TEXT, TOD_CURRENT3 TEXT, GOK_SUBSIDY TEXT, " +
                    "DEM_REVENUE TEXT, GPS_LAT TEXT, GPS_LONG TEXT, ONLINE_FLAG TEXT, BATTERY_CHARGE TEXT, " +
                    "SIGNAL_STRENGTH TEXT, IMGADD TEXT, PAYABLE_REAL TEXT, PAYABLE_PROFIT TEXT, " +
                    "PAYABLE_LOSS TEXT, BILL_PRINTED TEXT, FSLAB1 TEXT, FSLAB2 TEXT, FSLAB3 TEXT, FSLAB4 TEXT, FSLAB5 TEXT, " +
                    "ESLAB1 TEXT, ESLAB2 TEXT, ESLAB3 TEXT, ESLAB4 TEXT, ESLAB5 TEXT, ESLAB6 TEXT, CHARITABLE_RBT_AMT TEXT, " +
                    "SOLAR_RBT_AMT TEXT, FL_RBT_AMT TEXT, HANDICAP_RBT_AMT TEXT, PL_RBT_AMT TEXT, IPSET_RBT_AMT TEXT, REBATEFROMCCB_AMT TEXT, " +
                    "TOD_CHARGES TEXT, PF_PENALITY_AMT TEXT, EXLOAD_MDPENALITY TEXT, CURR_BILL_AMOUNT TEXT, ROUNDING_AMOUNT TEXT, " +
                    "DUE_DATE TEXT, DISCONN_DATE TEXT, _id INTEGER PRIMARY KEY, CREADJ TEXT, PREADKVAH TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    public void insert_billed_backup1(ContentValues cv) {
        sdb.insert("MAST_OUT", null, cv);
    }
}
