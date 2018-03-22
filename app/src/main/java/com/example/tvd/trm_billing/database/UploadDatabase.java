package com.example.tvd.trm_billing.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tvd.trm_billing.values.FunctionCalls;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.IOException;

public class UploadDatabase {
	MyHelper mh ;
	SQLiteDatabase sdb ;
	FunctionCalls fcall = new FunctionCalls();
	String dbname;
	File databasefile = null;
	
	public UploadDatabase(Context context) {
		try {
			databasefile = fcall.filestorepath("CollectionData", "upload.db");
			dbname = fcall.filepath("CollectionData") + File.separator + "upload.db";
			mh = new MyHelper(context, dbname, null, 5);
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
		public MyHelper(Context context, String name, CursorFactory factory, int version) {
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
			db.execSQL("Create table COLLECTION_OUTPUT(_id integer primary key, " +
					"RRNO TEXT, CONSUMER_ID TEXT, LF_NO TEXT, NAME TEXT, AMOUNT TEXT, MODE_PAYMENT TEXT, " +
					"RECPT_NO TEXT, BANK_CODE TEXT, BANK_NAME TEXT, CHQ_NO TEXT, CHQ_DATE TEXT, TRANSACTION_ID TEXT, " +
					"MACHINE_ID TEXT, RECPT_DATE_TIME TEXT, RECPT_DATE TEXT,MR_CODE TEXT, ONLINE_FLAG TEXT, GPS_LAT TEXT, GPS_LONG TEXT, " +
					"BATTERY_CHARGE TEXT, SIGNAL_STRENGTH TEXT, PAYMENT_PRINTED TEXT, CNCL_RECPT TEXT, REPRINT TEXT);");
			db.execSQL("Create table DTC_Details(_id integer primary key, DTC_Code TEXT, DTC_Name TEXT, " +
					"Feder_Code TEXT, Feder_Name TEXT, Section_Code TEXT, Section_Name TEXT, Prev_Reading INTEGER, " +
					"Current_Reading INTEGER, MF INTEGER, Consumption INTEGER, Long TEXT, Lat TEXT, Meter_No TEXT, Meter_Make TEXT);");
			db.execSQL("CREATE TABLE DISCONN_PAID(_id integer primary key, RRNO TEXT, CONSUMER_ID TEXT, " +
					"LEGFOL TEXT, RECPT_NO TEXT, RECPT_DATE TEXT, AMOUNT TEXT, DISS_DATE_TIME TEXT, GPS_LAT TEXT, GPS_LONG TEXT);");
			db.execSQL("CREATE TABLE DISCONNECTED(_id integer primary key, RRNO TEXT, CONSUMER_ID TEXT, LEGFOL TEXT, PRESENT_READING TEXT, " +
                    "DISS_TYPE TEXT, DISS_DATE_TIME TEXT, GPS_LAT TEXT, GPS_LONG TEXT, IMAGE_FILE TEXT);");
            db.execSQL("Create table Disconn_Details(READDATE TEXT, RRNO TEXT, LEGFOL TEXT, CONSNO TEXT, NAME TEXT, ADD1 TEXT, TARRIF TEXT, " +
                    "ARREARS INTEGER, MRCODE TEXT, PRVRED TEXT, SSNO TEXT, MOBILE_NO TEXT, MTR_SERIAL_NO TEXT, TARIFF_NAME TEXT, PAYABLE TEXT, " +
                    "SO TEXT, DUEDATE TEXT, VILLAGE TEXT, SUBDIV_CODE TEXT, TCCODE TEXT, POLE_NO TEXT, FDRNAME TEXT, TCNAME TEXT, " +
                    "Long TEXT, Lat TEXT, _id INTEGER PRIMARY KEY);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            fcall.logStatus("OldVersion: "+oldVersion+" NewVersion: "+newVersion);
            if (newVersion > oldVersion) {
                db.execSQL("DROP TABLE Disconn_Details");
                db.execSQL("Create table Disconn_Details(READDATE TEXT, RRNO TEXT, LEGFOL TEXT, CONSNO TEXT, NAME TEXT, ADD1 TEXT, " +
                        "TARRIF TEXT, ARREARS INTEGER, MRCODE TEXT, PRVRED TEXT, SSNO TEXT, MOBILE_NO TEXT, MTR_SERIAL_NO TEXT, " +
                        "TARIFF_NAME TEXT, PAYABLE TEXT, SO TEXT, DUEDATE TEXT, VILLAGE TEXT, SUBDIV_CODE TEXT, TCCODE TEXT, POLE_NO TEXT, " +
                        "FDRNAME TEXT, TCNAME TEXT, Long TEXT, Lat TEXT, _id INTEGER PRIMARY KEY);");
            }
		}
	}
	
	//===========================//Converting Database to file format//=====================================//	    
	public void copyDBtoSD(String sdFilePath, String sdFilename, String format) throws IOException {
		mh.getReadableDatabase();
		try {
            //zipPath is absolute path of zipped file
            ZipFile zipFile = new ZipFile(sdFilePath + sdFilename + format);
            //filePath is absolute path of file that want to be zip
            File fileToAdd = new File(dbname);
            File textfiletoAdd = new File(fcall.filepath("Textfile") + File.separator + fcall.inserttextfile());
            //create zip parameters such a password, encryption method, etc
            ZipParameters parameters = new ZipParameters();
            ZipParameters parameters1 = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            parameters.setPassword("12345");
            parameters.setFileNameInZip(sdFilename +".db");
            parameters.setSourceExternalStream(true);
            zipFile.addFile(fileToAdd, parameters);
            parameters1.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters1.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters1.setEncryptFiles(true);
            parameters1.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            parameters1.setPassword("12345");
            parameters1.setFileNameInZip(sdFilename +".txt");
            parameters1.setSourceExternalStream(true);
            zipFile.addFile(textfiletoAdd, parameters1);
		} catch (ZipException e) {
            e.printStackTrace();
 		}
	}	
	
	public void insertInuploadMastOut(ContentValues cv1) {
		sdb.insert("MAST_OUT", null, cv1);
	}
	
	public void deleteuploadbilleddata() {
		sdb.delete("MAST_OUT", null, null);
	}
	
	public void insertInuploadCollectionOut(ContentValues cv1) {
		sdb.insert("COLLECTION_OUTPUT", null, cv1);
	}
	
	public void deleteuploadcollectiondata() {
		sdb.delete("COLLECTION_OUTPUT", null, null);
	}
	
	public void insertInuploadDtcdetails(ContentValues cv1) {
		sdb.insert("DTC_Details", null, cv1);
	}
	
	public void deleteuploadDtcdetails() {
		sdb.delete("DTC_Details", null, null);
	}
	
	public Cursor getMAST_OUTDetails() {
		Cursor c = null;
		c = sdb.rawQuery("select * from MAST_OUT", null);
		return c;
	}
	
	public Cursor getCOLLECTION_OUTDetails() {
		Cursor c = null;
		c = sdb.rawQuery("select * from COLLECTION_OUTPUT", null);
		return c;
	}
	
	public Cursor getreaddate() {
		Cursor c = null;
		c = sdb.rawQuery("select (substr(min(READDATE),1,2) || '_' || max(READDATE)) " +
				"READDATE1 from MAST_OUT", null);
		return c;
	}
	
	public void insertInDISCONN_PAID(ContentValues cv1) {
		sdb.insert("DISCONN_PAID", null, cv1);
	}
	
	public void deleteDISCONN_PAIDdata() {
		sdb.delete("DISCONN_PAID", null, null);
	}
	
	public void insertInDISCONNECTED(ContentValues cv1) {
		sdb.insert("DISCONNECTED", null, cv1);
	}
	
	public void deleteDISCONNECTEDdata() {
		sdb.delete("DISCONNECTED", null, null);
	}
	
	public Cursor getDISCONN_PAIDdata() {
		Cursor c = null;
		c = sdb.rawQuery("SELECT * FROM DISCONN_PAID", null);
		return c;
	}
	
	public Cursor getDISCONN_PAIDdatabyrrnoconsid(String rrno, String consid) {
		Cursor c = null;
		c = sdb.rawQuery("SELECT * FROM DISCONN_PAID WHERE RRNO = "+"'"+rrno+"'"+" " +
				"and CONSUMER_ID = "+"'"+consid+"'", null);
		return c;
	}
	
	public Cursor getDISCONNECTEDdata() {
		Cursor c = null;
		c = sdb.rawQuery("SELECT * FROM DISCONNECTED", null);
		return c;
	}
	
	public Cursor getDISCONNECTEDdatabyrrnoconsid(String rrno, String consid) {
		Cursor c = null;
		c = sdb.rawQuery("SELECT * FROM DISCONNECTED WHERE RRNO = "+"'"+rrno+"'"+" " +
				"and CONSUMER_ID = "+"'"+consid+"'", null);
		return c;
	}

    public Cursor searchduplicate(String RRNO, String CONSNO) {
        Cursor data = null;
        data = sdb.rawQuery("SELECT * FROM MAST_OUT WHERE RRNO = '"+RRNO+"' AND CONSNO = '"+CONSNO+"'", null);
        return data;
    }

    public void insertinDisconnection(ContentValues cv1) {
        sdb.insert("Disconn_Details", null, cv1);
    }

    public Cursor getdisconndetails() {
        Cursor data = null;
        data = sdb.rawQuery("select * from Disconn_Details WHERE MTR_SERIAL_NO = 'N'", null);
        return data;
    }

    public Cursor updatedisconndetailsreport(String rrno, String consid) {
        Cursor data = null;
        data = sdb.rawQuery("update Disconn_Details set MTR_SERIAL_NO = 'Y' where " +
                "Disconn_Details.RRNO = " + "'" + rrno + "' and Disconn_Details.CONSNO = " + "'" + consid + "'", null);
        return data;
    }

    public Cursor getdisconndetailsreport(String tarrif, String FromAmount, String ToAmount) {
        Cursor data = null;
        data = sdb.rawQuery("select * from Disconn_Details where TARRIF in(" + tarrif + ") and " +
                "ARREARS between " + FromAmount + " and " + ToAmount + " and MTR_SERIAL_NO = 'N'", null);
        fcall.logStatus("select * from Disconn_Details where TARRIF in(" + tarrif + ") and " +
                "ARREARS between " + FromAmount + " and " + ToAmount + " and MTR_SERIAL_NO = 'N'");
        return data;
    }

    public void deletedisconnectiondetails() {
        sdb.delete("Disconn_Details", null, null);
    }

    public Cursor getmaxdisconndetails() {
        Cursor c59 = null;
        c59 = sdb.rawQuery("select RRNO, CONSNO, NAME, MAX(ARREARS) MAXIMUM from Disconn_Details " +
                "where ARREARS = (select MAX(ARREARS) from Disconn_Details where MTR_SERIAL_NO = 'N')", null);
        return c59;
    }

    public Cursor getdisconnvillagedetaillist(String village, String tarrif, String FromAmount, String ToAmount) {
        Cursor c59 = null;
        c59 = sdb.rawQuery("select * from Disconn_Details where VILLAGE = '" + village + "' " +
                "and TARRIF in(" + tarrif + ") and ARREARS between " + FromAmount + " and " + ToAmount + " and " +
                "MTR_SERIAL_NO = 'N'", null);
        return c59;
    }

    public Cursor getdisconntccodedetaillist(String tccode, String tarrif, String FromAmount, String ToAmount) {
        Cursor c59 = null;
        c59 = sdb.rawQuery("select * from Disconn_Details where TCCODE = '" + tccode + "' " +
                "and TARRIF in(" + tarrif + ") and ARREARS between " + FromAmount + " and " + ToAmount + " and " +
                "MTR_SERIAL_NO = 'N'", null);
        return c59;
    }

    public Cursor getdisconnvillagelist() {
        Cursor c59 = null;
        c59 = sdb.rawQuery("select distinct VILLAGE from Disconn_Details", null);
        return c59;
    }

    public Cursor getdisconntccodelist() {
        Cursor c59 = null;
        c59 = sdb.rawQuery("select distinct TCCODE from Disconn_Details order by TCCODE ASC", null);
        return c59;
    }

}
