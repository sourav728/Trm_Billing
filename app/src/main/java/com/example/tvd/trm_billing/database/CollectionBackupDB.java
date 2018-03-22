package com.example.tvd.trm_billing.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tvd.trm_billing.values.FunctionCalls;

import java.io.File;

public class CollectionBackupDB {
	MyHelper mh ;
	SQLiteDatabase sdb ;
	
	public CollectionBackupDB(Context context){
		FunctionCalls fcall = new FunctionCalls();
		String dbname = fcall.filepath("CollectionData") + File.separator + "collectionbackup.db";
		mh = new MyHelper(context, dbname, null, 2);
	}
	
	public void open() {
		sdb = mh.getWritableDatabase();
	}
	
	public void close() {
		sdb.close();
	}
	
	public class MyHelper extends SQLiteOpenHelper {
		public MyHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("Create table COLLECTION_OUTPUT(_id integer primary key, " +
					"RRNO TEXT, CONSUMER_ID TEXT, LF_NO TEXT, " +
					"NAME TEXT, AMOUNT TEXT, MODE_PAYMENT TEXT, " +
					"RECPT_NO TEXT, BANK_CODE TEXT, BANK_NAME TEXT, " +
					"CHQ_NO TEXT, CHQ_DATE TEXT, TRANSACTION_ID TEXT, " +
					"MACHINE_ID TEXT, RECPT_DATE_TIME TEXT, RECPT_DATE TEXT, " +
					"MR_CODE TEXT, ONLINE_FLAG TEXT, GPS_LAT TEXT, GPS_LONG TEXT, " +
					"BATTERY_CHARGE TEXT, SIGNAL_STRENGTH TEXT, PAYMENT_PRINTED TEXT, CNCL_RECPT TEXT, REPRINT TEXT);");
			db.execSQL("Create table RECEIPT_NO(_id integer primary key, RECPT_NO TEXT);");
			db.execSQL("Create table RECPT_DATE_TIME(_id integer primary key, TIME TEXT);");
			db.execSQL("Create table FTP_SERVER(_id integer primary key, FTP_HOST TEXT," +
					"FTP_USER TEXT, FTP_PASS TEXT, FTP_PORT TEXT);");
			db.execSQL("Create table BILLING_DATE(_id integer primary key, DATE TEXT, MONTH TEXT);");
			db.execSQL("Create table COLLECTION_DATE(_id integer primary key, DATE TEXT);");
			db.execSQL("Create table DISCONN_DATE(_id integer primary key, DATE TEXT);");
			db.execSQL("Create table DISCONN_DETAILS(_id integer primary key, FROM_DATE TEXT, TO_DATE TEXT, " +
					"DISCONN_SELECTED TEXT, DISCONN_MR TEXT);");
			db.execSQL("Create table LOGIN_DETAILS(_id integer primary key, SUBDIV_CODE TEXT, SUB_DIVISION TEXT," +
					"DEVICE_ID TEXT, MR_CODE TEXT, USER_TYPE TEXT, DATE1 TEXT);");
			db.execSQL("Create table UPLOAD(_id integer primary key, LOG TEXT);");
			db.execSQL("Create table SERVICE(_id integer primary key, NAMESPACE TEXT, SOAP_ACTION TEXT, " +
					"SERVICE_URL TEXT, DOWNLOAD_UPLOAD_URL TEXT);");
            /*db.execSQL("Create table COLLECTION_DATA(_id integer primary key, MR_CODE TEXT, MR_NAME TEXT, " +
                    "SUBDIV_CODE TEXT, COLL_FLAG TEXT, START_TIME TEXT, END_TIME TEXT, COLL_LIMIT TEXT, COLL_DATE TEXT);");*/
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /*db.execSQL("Create table COLLECTION_DATA(_id integer primary key, MR_CODE TEXT, MR_NAME TEXT, " +
                    "SUBDIV_CODE TEXT, COLL_FLAG TEXT, START_TIME TEXT, END_TIME TEXT, COLL_LIMIT TEXT, COLL_DATE TEXT);");*/
            if (newVersion > oldVersion) {
                db.execSQL("ALTER TABLE COLLECTION_OUTPUT ADD COLUMN CNCL_RECPT TEXT");
                db.execSQL("ALTER TABLE COLLECTION_OUTPUT ADD COLUMN REPRINT TEXT");
            }
		}
	}

    public void insert_collection_date(String date) {
        ContentValues cv = new ContentValues();
        cv.put("DATE", date);
        Cursor data = getCollection_date();
        if (data.getCount() > 0)
            sdb.update("COLLECTION_DATE", cv, null, null);
        else sdb.insert("COLLECTION_DATE", null, cv);
    }

    public Cursor getCollection_date() {
        Cursor data = null;
        data = sdb.rawQuery("SELECT * FROM COLLECTION_DATE", null);
        return data;
    }
	
	public void insertInCollectionTable(ContentValues cv1) {
		sdb.insert("COLLECTION_OUTPUT", null, cv1);
	}
	
	public void insertInRecptTime(ContentValues cv1) {
		sdb.insert("RECPT_DATE_TIME", null, cv1);
	}
	
	public void insertInDisconndetails(String fromdate, String todate, String selected, String mrcode) {
		ContentValues cv = new ContentValues();
		cv.put("FROM_DATE", fromdate);
		cv.put("TO_DATE", todate);
		cv.put("DISCONN_SELECTED", selected);
		cv.put("DISCONN_MR", mrcode);
		sdb.insert("DISCONN_DETAILS", null, cv);
	}
	
	public void insertInDisconndate(String currentdate) {
		ContentValues cv = new ContentValues();
		cv.put("DATE", currentdate);
		sdb.insert("DISCONN_DATE", null, cv);
	}
	
	public void updateRecpttime(String recpttime) {
		ContentValues cv = new ContentValues();
		cv.put("TIME", recpttime);
		sdb.update("RECPT_DATE_TIME", cv, null, null);
	}
	
	public void updateDisconndetails(String fromdate, String todate, String selected, String mrcode) {
		ContentValues cv = new ContentValues();
		cv.put("FROM_DATE", fromdate);
		cv.put("TO_DATE", todate);
		cv.put("DISCONN_SELECTED", selected);
		cv.put("DISCONN_MR", mrcode);
		sdb.update("DISCONN_DETAILS", cv, null, null);
	}
	
	public Cursor recpttime() {
		Cursor c1 = null;
		c1 = sdb.rawQuery("select * from RECPT_DATE_TIME", null);
		return c1;
	}
	
	public Cursor addingrecptno(){
		Cursor c1 = null;
		c1 = sdb.rawQuery("select RECPT_NO from COLLECTION_OUTPUT", null);
		return c1;
	}
	
	public Cursor Collection_Output(){
		Cursor c1 = null;
		c1 = sdb.rawQuery("select * from COLLECTION_OUTPUT", null);
		return c1;
	}

	public Cursor clear_Collection_Output() {
        Cursor data = null;
        data = sdb.rawQuery("DELETE FROM COLLECTION_OUTPUT", null);
        return data;
    }
	
	public Cursor emptyCollectionOutput() {
		Cursor c1 = null;
		c1 = sdb.rawQuery("delete * from COLLECTION_OUTPUT", null);
		return c1;
	}
	
	public Cursor getDisconndetails() {
		Cursor c1 = null;
		c1 = sdb.rawQuery("select * from DISCONN_DETAILS", null);
		return c1;
	}
	
	public Cursor getDisconndate() {
		Cursor c1 = null;
		c1 = sdb.rawQuery("select DATE from DISCONN_DATE", null);
		return c1;
	}
	
	public void insertIndisconndate(String currentdate) {
		ContentValues cv = new ContentValues();
		cv.put("DATE", currentdate);
		sdb.insert("DISCONN_DATE", null, cv);
	}
	
	public void updatedisconndate(String currentdate) {
		ContentValues cv = new ContentValues();
		cv.put("DATE", currentdate);
		sdb.update("DISCONN_DATE", cv, null, null);
	}
	
	public void insertInBillingdate(String currentdate) {
		ContentValues cv = new ContentValues();
		cv.put("DATE", currentdate);
		sdb.insert("BILLING_DATE", null, cv);
	}
	
	public void updateBillingdate(String currentdate) {
		ContentValues cv = new ContentValues();
		cv.put("DATE", currentdate);
		sdb.update("BILLING_DATE", cv, null, null);
	}
	
	public Cursor getBillingdate() {
		Cursor c1 = null;
		c1 = sdb.rawQuery("select * from BILLING_DATE", null);
		return c1;
	}
	
	public void insertInLOGIN_DETAILS(String subdivcode, String subdiv, String IMEI, String mrcode, String user, 
			String currentdate) {
		ContentValues cv = new ContentValues();
		cv.put("SUBDIV_CODE", subdivcode);
		cv.put("SUB_DIVISION", subdiv);
		cv.put("DEVICE_ID", IMEI);
		cv.put("MR_CODE", mrcode);
		cv.put("USER_TYPE", user);
		cv.put("DATE1", currentdate);
		sdb.insert("LOGIN_DETAILS", null, cv);
	}
	
	public void updateLOGIN_DETAILS(String subdivcode, String subdiv) {
		ContentValues cv = new ContentValues();
		cv.put("SUBDIV_CODE", subdivcode);
		cv.put("SUB_DIVISION", subdiv);
		sdb.update("LOGIN_DETAILS", cv, null, null);
	}
	
	public void updateDATEinLOGIN_DETAILS(String currentdate) {
		ContentValues cv = new ContentValues();
		cv.put("DATE1", currentdate);
		sdb.update("LOGIN_DETAILS", cv, null, null);
	}
	
	public void deleteLOGIN_DETAILS() {
		sdb.delete("LOGIN_DETAILS", null, null);
	}
	
	public Cursor getLOGIN_DETAILS() {
		Cursor c1 = null;
		c1 = sdb.rawQuery("select * from LOGIN_DETAILS", null);
		return c1;
	}
	
	public void insertInSERVICE(String namespace, String soapaction, String serviceurl, String downloaduploadurl) {
		ContentValues cv = new ContentValues();
		cv.put("NAMESPACE", namespace);
		cv.put("SOAP_ACTION", soapaction);
		cv.put("SERVICE_URL", serviceurl);
		cv.put("DOWNLOAD_UPLOAD_URL", downloaduploadurl);
		sdb.insert("SERVICE", null, cv);
	}
	
	public void updateSERVICE(String namespace, String soapaction, String serviceurl, String downloaduploadurl) {
		ContentValues cv = new ContentValues();
		cv.put("NAMESPACE", namespace);
		cv.put("SOAP_ACTION", soapaction);
		cv.put("SERVICE_URL", serviceurl);
		cv.put("DOWNLOAD_UPLOAD_URL", downloaduploadurl);
		sdb.update("SERVICE", cv, null, null);
	}
	
	public Cursor getSERVICE() {
		Cursor c1 = null;
		c1 = sdb.rawQuery("select * from SERVICE", null);
		return c1;
	}
	
	public void insertInFTP_SERVER(String ftphost, String ftpuser, String ftppass, String ftpport) {
		ContentValues cv = new ContentValues();
		cv.put("FTP_HOST", ftphost);
		cv.put("FTP_USER", ftpuser);
		cv.put("FTP_PASS", ftppass);
		cv.put("FTP_PORT", ftpport);
		sdb.insert("FTP_SERVER", null, cv);
	}
	
	public void updateFTP_SERVER(String ftphost, String ftpuser, String ftppass, String ftpport) {
		ContentValues cv = new ContentValues();
		cv.put("FTP_HOST", ftphost);
		cv.put("FTP_USER", ftpuser);
		cv.put("FTP_PASS", ftppass);
		cv.put("FTP_PORT", ftpport);
		sdb.update("FTP_SERVER", cv, null, null);
	}
	
	public Cursor getFTP_SERVER() {
		Cursor c1 = null;
		c1 = sdb.rawQuery("select * from FTP_SERVER", null);
		return c1;
	}
	
	public void insertInRECEIPT_NO(String receiptno) {
		ContentValues cv = new ContentValues();
		cv.put("RECPT_NO", receiptno);
		sdb.insert("RECEIPT_NO", null, cv);
	}
	
	public void updateRECEIPT_NO(String receiptno) {
		ContentValues cv = new ContentValues();
		cv.put("RECPT_NO", receiptno);
		sdb.update("RECEIPT_NO", cv, null, null);
	}
	
	public Cursor getRECEIPT_NO() {
		Cursor c1 = null;
		c1 = sdb.rawQuery("select * from RECEIPT_NO", null);
		return c1;
	}

	public void insert_Collection_Data(String MR_CODE, String MR_NAME, String SUBDIV_CODE, String COLL_FLAG, String START_TIME,
                                       String END_TIME, String COLL_LIMIT, String COLL_DATE) {
        ContentValues cv = new ContentValues();
        cv.put("MR_CODE", MR_CODE);
        cv.put("MR_NAME", MR_NAME);
        cv.put("SUBDIV_CODE", SUBDIV_CODE);
        cv.put("COLL_FLAG", COLL_FLAG);
        cv.put("START_TIME", START_TIME);
        cv.put("END_TIME", END_TIME);
        cv.put("COLL_LIMIT", COLL_LIMIT);
        cv.put("COLL_DATE", COLL_DATE);
        sdb.insert("COLLECTION_DATA", null, cv);
    }

    public Cursor getCollection_Data() {
        Cursor data = null;
        data = sdb.rawQuery("SELECT * FROM COLLECTION_DATA", null);
        return data;
    }
	
}
