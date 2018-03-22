package com.example.tvd.trm_billing.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;

import com.example.tvd.trm_billing.values.FunctionCalls;


import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.example.tvd.trm_billing.values.ConstantValues.ONLINE_UPDATE_FLAG_SUCCESS;


public class Databasehelper extends SQLiteOpenHelper {

    private String dwnfilepath;
    private String dwnfilename;
    private String dwnfileformat;
    private SQLiteDatabase myDataBase;
    static FunctionCalls fcall = new FunctionCalls();
    public final Context myContext;
    private static final String DATABASE_NAME = "mydb.db";
    /*	  static File dir = new File("data/data/com.transvivsion.subdiv/" + "databases");
            {
                if(!dir.exists())
                {
                       dir.mkdirs();
                }
            }*/
    static String path = fcall.filepath("databases");
    //public final static String DATABASE_PATH = ""+dir + File.separator;
    public final static String DATABASE_PATH = path + File.separator;
    public static final int DATABASE_VERSION = 1;
    //public static final int DATABASE_VERSION_old = 1;

    //Constructor
    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    //Create a empty database on the system
    public String createDatabase(String localfilepath, String localfilename, String localfileformat)
            throws IOException {
        dwnfilepath = localfilepath;
        dwnfilename = localfilename;
        dwnfileformat = localfileformat;

        boolean dbExist = checkDataBase();
        if (dbExist) {
            db_delete();
        }

        boolean dbExist1 = checkDataBase();
        if (!dbExist1) {
            try {
                this.close();
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error in copying database");
            }
        }
        return null;
    }


    //Check database already exist or not
    private boolean checkDataBase() {
        boolean checkDB = false;

        try {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        } catch (SQLiteException e) {
        }
        return checkDB;
    }

    //Copies your database from your local assets-folder to the
    //just created empty database in the system folder
    private void copyDataBase() throws IOException {

        String outFileName = DATABASE_PATH + DATABASE_NAME;

        String source = dwnfilepath + dwnfilename + dwnfileformat;
        String destination = dwnfilepath;
        String password = "12345";
        try {
            ZipFile zipFile = new ZipFile(source);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destination);
        } catch (ZipException e) {
            e.printStackTrace();
        }
        File hh = new File(DATABASE_PATH);
        if (!hh.exists()) {
            hh.mkdirs();
        }
        OutputStream myOutput = new FileOutputStream(outFileName);
        InputStream myInput = new FileInputStream(destination + "/" + dwnfilename + ".db");
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    //===========================//Converting Database to file format//=====================================//
    public void copyDBtoSD(String sdFilePath, String sdFilename, String format) throws IOException {
        this.getReadableDatabase();
        try {
            //zipPath is absolute path of zipped file
            ZipFile zipFile = new ZipFile(sdFilePath + sdFilename + format);
            //filePath is absolute path of file that want to be zip
            File fileToAdd = new File(DATABASE_PATH + DATABASE_NAME);
            File textfiletoAdd = new File(fcall.filepath("Textfile") + File.separator + fcall.inserttextfile());
            //create zip parameters such a password, encryption method, etc
            ZipParameters parameters = new ZipParameters();
            ZipParameters parameters1 = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameters.setPassword("12345");
            parameters.setFileNameInZip(sdFilename + ".db");
            parameters.setSourceExternalStream(true);
            zipFile.addFile(fileToAdd, parameters);
            if (textfiletoAdd.exists()) {
                parameters1.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
                parameters1.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                parameters1.setEncryptFiles(true);
                parameters1.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
                parameters1.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                parameters1.setPassword("12345");
                parameters1.setFileNameInZip(sdFilename + ".txt");
                parameters1.setSourceExternalStream(true);
                zipFile.addFile(textfiletoAdd, parameters1);
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    //delete database
    public void db_delete() {
        File file = new File(DATABASE_PATH + DATABASE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    //Open database
    public void openDatabase() throws SQLException {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void closeDataBase() throws SQLException {
        if (myDataBase == null)
            myDataBase.close();
        super.close();
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void copyzipdatabase(String sdFilePath, String sdFilename, String format) throws IOException {
        this.getReadableDatabase();
        try {
            //zipPath is absolute path of zipped file
            ZipFile zipFile = new ZipFile(sdFilePath + sdFilename + format);
            //filePath is absolute path of file that want to be zip
            File fileToAdd = new File(DATABASE_PATH + DATABASE_NAME);
            //create zip parameters such a password, encryption method, etc
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            parameters.setPassword("12345");
            parameters.setFileNameInZip(sdFilename + ".db");
            parameters.setSourceExternalStream(true);
            zipFile.addFile(fileToAdd, parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public Cursor getData() {
        Cursor c = null;
        c = myDataBase.query("MAST_CUST", null, null, null, null, null, null);
        return c;
    }

    public Cursor reprint_max(String val)
    {
        Cursor c1 = null;
        c1 = myDataBase.rawQuery("select * from COLLECTION_OUTPUT where REPRINT = " + "'" + val + "'" ,null);
        //c1 = myDataBase.rawQuery("select MAX(REPRINT) from COLLECTION_OUTPUT",null);
        return c1;
    }

    public  Cursor update_CancelReceipt(String cncl, String id)
    {
        Cursor c48 = null;
        //c48 = myDataBase.rawQuery("update COLLECTION_OUTPUT set CNCL_RECPT = '1' where " + "COLLECTION_OUTPUT.CONSUMER_ID = " + "'" + id + "'", null);
        //c48 = myDataBase.rawQuery("update COLLECTION_OUTPUT set CNCL_RECPT = 'Y' where _id = '1'",null);
        c48 = myDataBase.rawQuery("update COLLECTION_OUTPUT set CNCL_RECPT  = " + "'" + cncl + "'" + " where CONSUMER_ID = " + "'" + id + "'", null);
        //c48 = myDataBase("update MACHINE_ID from COLLECTION_OUTPUT where CONSUMER_ID= " + "'" + cid + "'",null);
        return c48;
    }


    public Cursor collection_input() {
        Cursor c1 = null;
        c1 = myDataBase.rawQuery("Select * from COLLECTION_INPUT", null);
        return c1;
    }

    public Cursor getRRNo(String value) {
        Cursor c1 = null;
        c1 = myDataBase.rawQuery("Select * from MAST_CUST where RRNO = " + "'" + value + "'", null);
        return c1;
    }

    public Cursor getAccountID(String value) {
        Cursor c1 = null;
        c1 = myDataBase.rawQuery("Select * from MAST_CUST where CONSNO = " + "'" + value + "'", null);
        return c1;
    }

    public Cursor getBilledRRNo(String value) {
        Cursor c1 = null;
        c1 = myDataBase.rawQuery("Select * from MAST_OUT where CONSNO = " + "'" + value + "'", null);
        return c1;
    }

    public Cursor login() {
        Cursor c2 = null;
        c2 = myDataBase.rawQuery("select USERNAME,PASSWORD from MAST_CUST ", null);
        return c2;
    }

    public Cursor statname() {
        Cursor c3 = null;
        c3 = myDataBase.rawQuery("select STATUS_NAME from BILLING_STATUS", null);
        return c3;
    }

    public Cursor statcode(String value) {
        Cursor c4 = null;
        c4 = myDataBase.rawQuery("select * from BILLING_STATUS where STATUS_NAME = " + "'" + value + "'", null);
        return c4;
    }

    public Cursor statuslabel(String value) {
        Cursor c4 = null;
        c4 = myDataBase.rawQuery("select * from BILLING_STATUS where STATUS_LABEL = " + "'" + value + "'", null);
        return c4;
    }

    public Cursor statuscode(String value) {
        Cursor c4 = null;
        c4 = myDataBase.rawQuery("select * from BILLING_STATUS where STATUS_CODE = " + "'" + value + "'", null);
        return c4;
    }

    public Cursor reportid(String value) {
        Cursor c5 = null;
        //c5 = myDataBase.rawQuery("select * from MAST_CUST,MAST_OUT,SUBDIV_DETAILS where MAST_CUST.RRNO = MAST_OUT.RRNO and MAST_OUT.RRNO = "+"'"+value+"'", null);
        c5 = myDataBase.rawQuery("select * from MAST_CUST,MAST_OUT,SUBDIV_DETAILS where MAST_CUST.CONSNO = MAST_OUT.CONSNO and MAST_OUT.CONSNO = " + "'" + value + "'", null);
        return c5;
    }

    public Cursor reportrrno(String value) {
        Cursor c5 = null;
        c5 = myDataBase.rawQuery("select * from MAST_CUST,MAST_OUT,SUBDIV_DETAILS where MAST_CUST.RRNO = MAST_OUT.RRNO and MAST_OUT.RRNO = " + "'" + value + "'", null);
        return c5;
    }

    public Cursor reportname(String value) {
        Cursor c5 = null;
        c5 = myDataBase.rawQuery("select * from MAST_CUST,MAST_OUT,SUBDIV_DETAILS where MAST_CUST.NAME = MAST_OUT.NAME and MAST_OUT.NAME = " + "'" + value + "'", null);
        return c5;
    }

    public Cursor reportdisplay() {
        Cursor c5 = null;
        c5 = myDataBase.rawQuery("select * from SUBDIV_DETAILS", null);
        return c5;
    }

    public Cursor checkreport() {
        Cursor c5 = null;
        c5 = myDataBase.rawQuery("select CONSNO from MAST_CUST", null);
        return c5;
    }

    public Cursor presentstatusbyid(String value) {
        Cursor c6 = null;
        c6 = myDataBase.rawQuery("select * from BILLING_STATUS where STATUS_CODE = (select PRES_STS from MAST_OUT where CONSNO = " + "'" + value + "')", null);
        return c6;
    }

    public Cursor presentstatusbyrrno(String value) {
        Cursor c6 = null;
        c6 = myDataBase.rawQuery("select * from BILLING_STATUS where STATUS_CODE = (select PRES_STS from MAST_OUT where CONSNO = " + "'" + value + "')", null);
        return c6;
    }

    public Cursor presentstatusbyname(String value) {
        Cursor c6 = null;
        c6 = myDataBase.rawQuery("select * from BILLING_STATUS where STATUS_CODE = (select PRES_STS from MAST_OUT where NAME = " + "'" + value + "')", null);
        return c6;
    }

    public Cursor previousstatusbyid(String value) {
        Cursor c7 = null;
        c7 = myDataBase.rawQuery("select * from BILLING_STATUS where STATUS_CODE = (select PREVSTAT from MAST_OUT where CONSNO = " + "'" + value + "')", null);
        return c7;
    }

    public Cursor previousstatusbyrrno(String value) {
        Cursor c7 = null;
        c7 = myDataBase.rawQuery("select * from BILLING_STATUS where STATUS_CODE = (select PREVSTAT from MAST_OUT where CONSNO = " + "'" + value + "')", null);
        return c7;
    }

    public Cursor previousstatusbyname(String value) {
        Cursor c7 = null;
        c7 = myDataBase.rawQuery("select * from BILLING_STATUS where STATUS_CODE = (select PREVSTAT from MAST_OUT where NAME = " + "'" + value + "')", null);
        return c7;
    }

    public Cursor collects1() {
        Cursor c8 = null;
        c8 = myDataBase.rawQuery("SELECT 'MR : ' || MRCODE || ' RDG_DATE : ' || SUBSTR(MIN(READDATE),1,2) || ' To ' || MAX(READDATE)COL1,''COL2 FROM MAST_CUST", null);
        return c8;
    }

    public Cursor collects2() {
        Cursor c9 = null;
        c9 = myDataBase.rawQuery("select 'TOTAL             'COL1, count(CONSNO)COL2 from MAST_CUST", null);
        return c9;
    }

    public Cursor collects3() {
        Cursor c10 = null;
        c10 = myDataBase.rawQuery("select 'BILLED           'COL1, count(CONSNO)COL2 from MAST_OUT", null);
        return c10;
    }

    public Cursor collects4() {
        Cursor c11 = null;
        c11 = myDataBase.rawQuery("select 'NOT BILLED       'COL1, ((SELECT count(CONSNO) from MAST_CUST)-(SELECT count(CONSNO) from MAST_OUT))COL2", null);
        return c11;
    }

    public Cursor collects5() {
        Cursor c12 = null;
        c12 = myDataBase.rawQuery("SELECT STATUS_NAME COL1, COUNT(CONSNO)COL2 FROM BILLING_STATUS LEFT OUTER JOIN MAST_OUT ON STATUS_CODE = PRES_STS GROUP BY STATUS_CODE", null);
        return c12;
    }

    public Cursor tariffwisecollections() {
        Cursor c13 = null;
        String tariff = "select TARIFF_NAME,COUNT(COLLECTION_OUTPUT.CONSUMER_ID) receipts,SUM(AMOUNT) AMOUNT from COLLECTION_OUTPUT,COLLECTION_INPUT where COLLECTION_OUTPUT.CONSUMER_ID = COLLECTION_INPUT.CONSUMER_ID GROUP BY TARIFF_NAME ORDER BY TARIFF_NAME ASC";
        c13 = myDataBase.rawQuery(tariff, null);
        return c13;
    }

    public Cursor cashcollectedbydate() {
        Cursor c13 = null;
        String tariff = "select (RECPT_DATE)RECPTDATE, COUNT(COLLECTION_OUTPUT.CONSUMER_ID)RECEIPTS, SUM(AMOUNT)AMOUNT from COLLECTION_OUTPUT where COLLECTION_OUTPUT.MODE_PAYMENT = '0' GROUP BY RECPT_DATE";
        c13 = myDataBase.rawQuery(tariff, null);
        return c13;
    }

    public Cursor chequecollected() {
        Cursor c13 = null;
        String tariff = "select (CHQ_NO)CHEQUE, COUNT(COLLECTION_OUTPUT.CONSUMER_ID)RECEIPTS, SUM(AMOUNT)AMOUNT from COLLECTION_OUTPUT,COLLECTION_INPUT where COLLECTION_OUTPUT.CONSUMER_ID = COLLECTION_INPUT.CONSUMER_ID and COLLECTION_OUTPUT.MODE_PAYMENT = '1' GROUP BY CHQ_NO";
        c13 = myDataBase.rawQuery(tariff, null);
        return c13;
    }

    public Cursor transactionidbydate(String date) {
        Cursor c13 = null;
        String tariff = "select (TRANSACTION_ID)TRANSACTIONID, COUNT(COLLECTION_OUTPUT.CONSUMER_ID)RECEIPTS, SUM(AMOUNT)AMOUNT from COLLECTION_OUTPUT where COLLECTION_OUTPUT.MODE_PAYMENT = '0' and COLLECTION_OUTPUT.RECPT_DATE = " + "'" + date + "'" + " GROUP BY TRANSACTION_ID";
        c13 = myDataBase.rawQuery(tariff, null);
        return c13;
    }

    public Cursor tariffwisebilling() {
        Cursor c13 = null;
        String tariff = "select (TARIFF_CONFIG_CURRENT.TARRIF)TARIFF_NAME, COUNT(MAST_OUT.CONSNO)BILLED, SUM(MAST_OUT.PAYABLE)PAYABLE from TARIFF_CONFIG_CURRENT,MAST_CUST,MAST_OUT where MAST_OUT.CONSNO = MAST_CUST.CONSNO and MAST_OUT.TARIFF = TARIFF_CONFIG_CURRENT.TARIFF_CODE and MAST_OUT.REBATE_FLAG = TARIFF_CONFIG_CURRENT.RUFLAG GROUP BY TARIFF_CONFIG_CURRENT.TARRIF ORDER BY TARIFF_CONFIG_CURRENT.TARRIF ASC";
        c13 = myDataBase.rawQuery(tariff, null);
        return c13;
    }

    public Cursor collections() {
        Cursor c14 = null;
        c14 = myDataBase.rawQuery("select * from Collections", null);
        return c14;
    }

    public Cursor collectiondetailsbytransactionid(String transactionid) {
        Cursor c14 = null;
        c14 = myDataBase.rawQuery("select * from COLLECTION_OUTPUT,COLLECTION_INPUT where COLLECTION_OUTPUT.CONSUMER_ID = COLLECTION_INPUT.CONSUMER_ID and TRANSACTION_ID = " + "'" + transactionid + "'", null);
        return c14;
    }

    public Cursor chequecollecteddetails(String chequeno) {
        Cursor c14 = null;
        c14 = myDataBase.rawQuery("select * from COLLECTION_OUTPUT,COLLECTION_INPUT where COLLECTION_OUTPUT.CONSUMER_ID = COLLECTION_INPUT.CONSUMER_ID and CHQ_NO = " + "'" + chequeno + "'", null);
        return c14;
    }

    public boolean checkinserteddata(String account_id) {
        Cursor data = null;
        data = myDataBase.rawQuery("SELECT * FROM MAST_OUT WHERE CONSNO ='"+account_id+"'", null);
        if (data.getCount() > 0) {
            data.close();
            return true;
        } else {
            data.close();
            return false;
        }
    }

    public Cursor summart1() {
        Cursor c15 = null;
        c15 = myDataBase.rawQuery("SELECT null MODE_PAYMENT, 'Printed on Date Time : ' || datetime(CURRENT_TIMESTAMP,'localtime')mode1 ,null Total_Receipts,null Net_Amount", null);
        return c15;
    }

    public Cursor summart2() {
        Cursor c16 = null;
        c16 = myDataBase.rawQuery("select null MODE_PAYMENT,(select distinct SUB_DIVISION from  SUBDIV_DETAILS) mode1,null Total_Receipts,null Net_Amount", null);
        return c16;
    }

    public Cursor summart3() {
        Cursor c17 = null;
        c17 = myDataBase.rawQuery("select null MODE_PAYMENT,'Start Time: ' || min(RECPT_DATE_TIME) mode1,'Stop  Time: ' || max(RECPT_DATE_TIME)Total_Receipts, null Net_Amount from COLLECTION_OUTPUT", null);
        return c17;
    }

    public Cursor summart4(String receipt_date) {
        Cursor c18 = null;
        c18 = myDataBase.rawQuery("select null MODE_PAYMENT, 'Start Recpt No : ' || min(RECEIPT_NO) mode1,'End   Recpt No : ' || max(RECEIPT_NO) Total_Receipts,null Net_Amount from COLLECTION_OUTPUT WHERE RECEIPT_DATE ="+"'"+receipt_date+"'", null);
        return c18;
    }

    public Cursor summart4() {
        Cursor c18 = null;
        c18 = myDataBase.rawQuery("select null MODE_PAYMENT, 'Start Recpt No : ' || min(RECEIPT_NO) mode1,'End   Recpt No : ' || max(RECEIPT_NO) Total_Receipts,null Net_Amount from COLLECTION_OUTPUT", null);
        return c18;
    }

    public Cursor summart5() {
        Cursor c19 = null;
        c19 = myDataBase.rawQuery("select * from Summart", null);
        return c19;
    }

    public Cursor summart6() {
        Cursor c20 = null;
        c20 = myDataBase.rawQuery("select null MODE_PAYMENT,'Total' mode1,'' Total_Receipts,sum(AMOUNT)Net_Amount from COLLECTION_OUTPUT", null);
        return c20;
    }

    public Cursor billstatus() {
        Cursor c21 = null;
        c21 = myDataBase.rawQuery("select * from BILLSTATUS", null);
        return c21;
    }

    public Cursor summaryreport() {
        Cursor c22 = null;
        c22 = myDataBase.rawQuery("select * from Summart3", null);
        return c22;
    }

    //Cursor to set data for spinner
    public Cursor spinnerData() {
        Cursor c = null;
        c = myDataBase.rawQuery("Select STATUS_NAME from BILLING_STATUS", null);
        return c;
    }

    public Cursor spinnerDataforBank() {
        Cursor c = null;
        c = myDataBase.rawQuery("select * from BANK_DETAILS", null);
        return c;
    }

    // Cursor to Select particular Item to select for spinner item
    public Cursor spinnerSelectedData(int index) {
        Cursor c = null;
        c = myDataBase.rawQuery("Select STATUS_LABEL from BILLING_STATUS where STATUS_CODE =" + "'" + index + "'", null);
        return c;
    }

    //Update the Billed Record
    public void updatebill(int billUpdate) {
        ContentValues cv = new ContentValues();
        cv.put("Billed_Record", billUpdate);
        myDataBase.update("SUBDIV_DETAILS", cv, "Billed_Record", null);
    }

    public void updateCollection_Flag() {
        ContentValues cv = new ContentValues();
        cv.put("COLLECTION_FLAG", "Y");
        myDataBase.update("SUBDIV_DETAILS", cv, "COLLECTION_FLAG", null);
    }

    public Cursor getBilledRecordData() {
        Cursor c = null;
        c = myDataBase.rawQuery("select * from mast_cust where rowid = (select billed_record from subdiv_details)", null);
        return c;
    }

    public Cursor updateDLrecord(String monthdiff) {
        Cursor data = null;
        data = myDataBase.rawQuery("update MAST_CUST set DLCOUNT = '"+monthdiff+"' where rowid = (select billed_record from subdiv_details)", null);
        return data;
    }

    public Cursor updateReadDate(String date) {
        Cursor data = null;
        data = myDataBase.rawQuery("update MAST_CUST set READDATE = '"+date+"' where rowid = (select billed_record from subdiv_details)", null);
        return data;
    }

    public Cursor getUnBilledRecordData() {
        Cursor c = null;
        c = myDataBase.rawQuery("select * from mast_cust where CONSNO not in (select CONSNO from mast_out) and rowid = (select billed_record from subdiv_details)", null);
        return c;
    }

    public Cursor getDLBilledRecordData() {
        Cursor c = null;
        c = myDataBase.rawQuery("select * from MAST_OUT where PRES_STS = '1' and rowid = (select billed_record from subdiv_details)", null);
        return c;
    }

    // Cursor to get Billed Record number
    public Cursor getBilledRecordNumber() {
        Cursor c = null;
        c = myDataBase.rawQuery("Select * from SUBDIV_DETAILS", null);
        return c;
    }

    public Cursor getTarrifData(String rRNO, String rRebate) {
        Cursor c = null;
        String query = "select * from TARIFF_CONFIG_CURRENT where TARIFF_CODE = (select TARIFF from MAST_CUST where CONSNO = " + "'" + rRNO + "') and RUFLAG = (select RREBATE from MAST_CUST where RREBATE = " + "'" + rRebate + "')";
        Log.d("Query", query);
        c = myDataBase.rawQuery("select * from TARIFF_CONFIG_CURRENT where TARIFF_CODE = (select TARIFF from MAST_CUST where CONSNO = " + "'" + rRNO + "') and RUFLAG = (select RREBATE from MAST_CUST where RREBATE = " + "'" + rRebate + "')", null);
        return c;
    }

    //Tariff = 10
    public Cursor getTarrifDataBJ(String rRebate, String Tariff) {
        Cursor c = null;
        c = myDataBase.rawQuery("select * from TARIFF_CONFIG_CURRENT where TARIFF_CODE = " + "'" + Tariff + "'and RUFLAG = " + "'" + rRebate + "'", null);
        Log.d("debug", "select * from TARIFF_CONFIG_CURRENT where TARIFF_CODE = " + "'" + Tariff + "'and RUFLAG = " + "'" + rRebate + "'");
        return c;
    }

    public Cursor billed() {
        Cursor c23 = null;
        c23 = myDataBase.rawQuery("select * from MAST_OUT", null);
        return c23;
    }

    public Cursor status(String status) {
        Cursor c24 = null;
        c24 = myDataBase.rawQuery("select * from MAST_OUT where PRES_STS = (select STATUS_CODE from BILLING_STATUS where STATUS_NAME = " + "'" + status + "')", null);
        return c24;
    }

    public Cursor notbilled() {
        Cursor c25 = null;
        c25 = myDataBase.rawQuery("SELECT * from MAST_CUST where CONSNO not in (SELECT CONSNO from MAST_OUT)", null);
        return c25;
    }

    public Cursor dlbilled() {
        Cursor c25 = null;
        c25 = myDataBase.rawQuery("select * from MAST_OUT where PRES_STS = '1'", null);
        return c25;
    }

    public Cursor totalbillingrecords() {
        Cursor c25 = null;
        c25 = myDataBase.rawQuery("select * from MAST_CUST", null);
        return c25;
    }

    public Cursor tariffcheckforbilling(String value) {
        Cursor c26 = null;
        c26 = myDataBase.rawQuery("select * from mast_out,tariff_config_current where mast_out.TARIFF = tariff_config_current.TARIFF_CODE and mast_out.REBATE_FLAG  = tariff_config_current.RUFLAG and TARIFF_CONFIG_CURRENT.TARRIF = " + "'" + value + "'", null);
        return c26;
    }

    public Cursor tariffcheck(String value) {
        Cursor c26 = null;
        c26 = myDataBase.rawQuery("select * from COLLECTION_OUTPUT,COLLECTION_INPUT where COLLECTION_OUTPUT.CONSUMER_ID = COLLECTION_INPUT.CONSUMER_ID and COLLECTION_INPUT.TARIFF_NAME = " + "'" + value + "' ORDER BY RECPT_NO", null);
        return c26;
    }

    public Cursor searchbyname() {
        Cursor c27 = null;
        c27 = myDataBase.rawQuery("select name from MAST_CUST", null);
        return c27;
    }

    public Cursor Billedsearchbyname() {
        Cursor c27 = null;
        c27 = myDataBase.rawQuery("select name from MAST_OUT", null);
        return c27;
    }

    public Cursor searchbyrrno() {
        Cursor c28 = null;
        c28 = myDataBase.rawQuery("select rrno from MAST_CUST", null);
        return c28;
    }

    public Cursor Billedsearchbyrrno() {
        Cursor c28 = null;
        c28 = myDataBase.rawQuery("select rrno from MAST_OUT", null);
        return c28;
    }

    public Cursor searchbyid() {
        Cursor c29 = null;
        c29 = myDataBase.rawQuery("select CONSNO from MAST_CUST", null);
        return c29;
    }

    public Cursor Billedsearchbyid() {
        Cursor c29 = null;
        c29 = myDataBase.rawQuery("select CONSNO from MAST_OUT", null);
        return c29;
    }

    public Cursor getID(String value) {
        Cursor c30 = null;
        c30 = myDataBase.rawQuery("select * from MAST_CUST where CONSNO = " + "'" + value + "'", null);
        return c30;
    }

    public Cursor getBilledID(String value) {
        Cursor c30 = null;
        c30 = myDataBase.rawQuery("select * from MAST_OUT where CONSNO = " + "'" + value + "'", null);
        return c30;
    }

    public Cursor getName(String value) {
        Cursor c31 = null;
        c31 = myDataBase.rawQuery("select * from MAST_CUST where NAME = " + "'" + value + "'", null);
        return c31;
    }

    public Cursor getBilledName(String value) {
        Cursor c31 = null;
        c31 = myDataBase.rawQuery("select * from MAST_OUT where NAME = " + "'" + value + "'", null);
        return c31;
    }

    public Cursor getBilledRRNO(String value) {
        Cursor c31 = null;
        c31 = myDataBase.rawQuery("select * from MAST_OUT where RRNO = " + "'" + value + "'", null);
        return c31;
    }

    public Cursor chqdetails() {
        Cursor c32 = null;
        c32 = myDataBase.rawQuery("select BANK_NAME,COUNT(CONSUMER_ID)CNT,SUM(AMOUNT) AMT from COLLECTION_OUTPUT where MODE_PAYMENT=1 GROUP BY BANK_NAME ORDER BY BANK_NAME ASC", null);
        return c32;
    }

    public Cursor chqbankdetails(String value) {
        Cursor c33 = null;
        c33 = myDataBase.rawQuery("select * from COLLECTION_OUTPUT,COLLECTION_INPUT where COLLECTION_INPUT.CONSUMER_ID = COLLECTION_OUTPUT.CONSUMER_ID and COLLECTION_OUTPUT.BANK_NAME = " + "'" + value + "'", null);
        return c33;
    }

    public Cursor chequedetails(String value) {
        Cursor c34 = null;
        c34 = myDataBase.rawQuery("select * from COLLECTION_OUTPUT,COLLECTION_INPUT where COLLECTION_INPUT.CONSUMER_ID = COLLECTION_OUTPUT.CONSUMER_ID and COLLECTION_OUTPUT.CHQ_NO = " + "'" + value + "'", null);
        return c34;
    }

    public Cursor cheque_details() {
        Cursor c35 = null;
        c35 = myDataBase.rawQuery("select CHQ_NO from COLLECTION_OUTPUT where MODE_PAYMENT = (select Mode_Code from Mode where Mode_Type = 'CHEQUE')", null);
        return c35;
    }

    public Cursor prvstatus(String value) {
        Cursor c36 = null;
        c36 = myDataBase.rawQuery("select * from MAST_CUST,BILLING_STATUS where BILLING_STATUS.STATUS_CODE = MAST_CUST.PREVSTAT and MAST_CUST.CONSNO = " + "'" + value + "'", null);
        return c36;
    }

    public void insertInTable(ContentValues cv1) {
        myDataBase.insert("MAST_OUT", null, cv1);
    }

    public void insertInCollectionTable(ContentValues cv1) {
        myDataBase.insert("COLLECTION_OUTPUT", null, cv1);
    }

    public void insertInRowEmptyTable(ContentValues cv1) {
        myDataBase.insert("MAST_ROW_EMPTY", null, cv1);
    }

    public Cursor getImageAddressString(String RRNO1) {
        Cursor c = null;
        c = myDataBase.rawQuery("Select IMGADD from MAST_OUT where CONSNO = " + "'" + RRNO1 + "'", null);
        return c;
    }

    public Cursor mode(String value) {
        Cursor c37 = null;
        c37 = myDataBase.rawQuery("select Mode_Code from Mode where Mode_Type = " + "'" + value + "'", null);
        return c37;
    }

    public Cursor bankcode(String value) {
        Cursor c38 = null;
        c38 = myDataBase.rawQuery("select BANK_CODE from BANK_DETAILS where BANK_NAME = " + "'" + value + "'", null);
        return c38;
    }

    public Cursor getpaymentdetailsbyid(String value) {
        Cursor c39 = null;
        c39 = myDataBase.rawQuery("select * from COLLECTION_OUTPUT where CONSUMER_ID = " + "'" + value + "'", null);
        return c39;
    }

    public Cursor getpaymentdetailsbyrrno(String value) {
        Cursor c40 = null;
        c40 = myDataBase.rawQuery("select * from COLLECTION_OUTPUT where RRNO = " + "'" + value + "'", null);
        return c40;
    }

    public Cursor getpaymentdetailsbyname(String value) {
        Cursor c41 = null;
        c41 = myDataBase.rawQuery("select * from COLLECTION_OUTPUT where NAME = " + "'" + value + "'", null);
        return c41;
    }

    public Cursor getmodetype(String value) {
        Cursor c42 = null;
        c42 = myDataBase.rawQuery("select Mode_Type from Mode where Mode_Code = " + "'" + value + "'", null);
        return c42;
    }

    public Cursor checkbillingbyid(String value) {
        Cursor c43 = null;
        c43 = myDataBase.rawQuery("select CONSNO from MAST_OUT where CONSNO = " + "'" + value + "'", null);
        return c43;
    }

    public Cursor deletebillingrow(String value) {
        Cursor c44 = null;
        c44 = myDataBase.rawQuery("delete from MAST_OUT where CONSNO = " + "'" + value + "'", null);
        return c44;
    }

    public Cursor checkbilled(String value) {
        Cursor c45 = null;
        c45 = myDataBase.rawQuery("select * from MAST_CUST,MAST_OUT where MAST_CUST.RRNO = MAST_OUT.RRNO and MAST_CUST.RRNO = " + "'" + value + "'", null);
        return c45;
    }

    public Cursor getforpaymentbyid(String value) {
        Cursor c46 = null;
        c46 = myDataBase.rawQuery("select * from COLLECTION_INPUT where CONSUMER_ID = " + "'" + value + "'", null);
        return c46;
    }

    public Cursor getforpaymentbyrrno(String value) {
        Cursor c46 = null;
        c46 = myDataBase.rawQuery("select * from COLLECTION_INPUT where RRNO = " + "'" + value + "'", null);
        return c46;
    }

    public Cursor getforpaymentbyname(String value) {
        Cursor c46 = null;
        c46 = myDataBase.rawQuery("select * from COLLECTION_INPUT where NAME = " + "'" + value + "'", null);
        return c46;
    }

    public Cursor getdetailsforpayment(String rrno, String consid) {
        Cursor c46 = null;
        c46 = myDataBase.rawQuery("select * from COLLECTION_INPUT where CONSUMER_ID = " + "'" + consid + "' and RRNO = " + "'" + rrno + "'", null);
        return c46;
    }

    public Cursor collection_output_by_receipt_date(String receipt_date) {
        Cursor data = null;
        data = myDataBase.rawQuery("SELECT * FROM COLLECTION_OUTPUT WHERE RECPT_DATE ="+"'"+receipt_date+"'", null);
        return data;
    }

    public Cursor getpaymentbyid() {
        Cursor c47 = null;
        c47 = myDataBase.rawQuery("select CONSUMER_ID from COLLECTION_INPUT", null);
        return c47;
    }

    public Cursor getbilledpaymentbyid() {
        Cursor c47 = null;
        c47 = myDataBase.rawQuery("select CONSUMER_ID from COLLECTION_OUTPUT", null);
        return c47;
    }

    public Cursor getpaymentbyrrno() {
        Cursor c47 = null;
        c47 = myDataBase.rawQuery("select RRNO from COLLECTION_INPUT", null);
        return c47;
    }

    public Cursor getforreceiptbyid(String value)
    {
        Cursor c46 = null;
        c46 = myDataBase.rawQuery("select * from COLLECTION_OUTPUT where CONSUMER_ID = " + "'" + value + "'", null);
        return c46;

    }
    public Cursor recptdatetime(String value)
    {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select RECPT_DATE_TIME from COLLECTION_OUTPUT where CONSUMER_ID = " + "'" + value + "'", null);
        return c48;
    }

    public Cursor getbilledpaymentbyrrno() {
        Cursor c47 = null;
        c47 = myDataBase.rawQuery("select RRNO from COLLECTION_OUTPUT", null);
        return c47;
    }

    public Cursor getpaymentbyname() {
        Cursor c47 = null;
        c47 = myDataBase.rawQuery("select NAME from COLLECTION_INPUT", null);
        return c47;
    }

    public Cursor getbilledpaymentbyname() {
        Cursor c47 = null;
        c47 = myDataBase.rawQuery("select NAME from COLLECTION_OUTPUT", null);
        return c47;
    }

    public Cursor addingrecptno() {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select MAX(RECPT_NO)RECPT_NO from COLLECTION_OUTPUT", null);
        return c48;
    }

    public void update_Recept(String recpt, String id) {
        Cursor data = null;
        data = myDataBase.rawQuery("update COLLECTION_OUTPUT SET RECPT_NO = '"+recpt+"' WHERE _id = '"+id+"'", null);
        data.moveToNext();
    }

    public Cursor addingrecptnototext(String rrno, String Consumerid) {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select RECPT_NO from COLLECTION_OUTPUT where RRNO = " + "'" + rrno + "' and CONSUMER_ID = " + "'" + Consumerid + "'", null);
        return c48;
    }

    public Cursor getconsumer_name(String cid)
    {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select NAME from COLLECTION_OUTPUT where CONSUMER_ID= " + "'" + cid + "'", null);
        return c48;
    }

    public Cursor getmax_reprint()
    {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select MAX(REPRINT)REPRINT from COLLECTION_OUTPUT", null);
        return  c48;
    }

    public Cursor get_paidamount(String cid)
    {
        Cursor c48 =null;
        c48 = myDataBase.rawQuery("select AMOUNT from COLLECTION_OUTPUT where CONSUMER_ID= " + "'" + cid + "'", null);
        return c48;
    }

    public Cursor get_collectionoutput_mode(String cid)
    {
        Cursor c48 =null;
        c48 = myDataBase.rawQuery("select MODE_PAYMENT from COLLECTION_OUTPUT where CONSUMER_ID= " + "'" + cid + "'", null);
        return c48;
    }

    public Cursor get_cashor_check(String mode)
    {
        Cursor c48 =null;
        c48 = myDataBase.rawQuery("select Mode_Type from Mode where Mode_Code= " + "'" + mode + "'", null);
        return c48;
    }
    public Cursor get_payable_amt(String cid)
    {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select PAYABLE_AMOUNT from COLLECTION_INPUT", null);
        return c48;
    }

    public Cursor get_check_details(String cid)
    {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select * from COLLECTION_OUTPUT where CONSUMER_ID = " + "'" + cid + "'", null);
        return c48;
    }

    public Cursor getreceiptno(String cid)
    {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select RECPT_NO from COLLECTION_OUTPUT where CONSUMER_ID= " + "'" + cid + "'", null);
        return c48;
    }

    public Cursor get_recpdate(String cid)
    {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select RECPT_DATE from COLLECTION_OUTPUT where CONSUMER_ID= " + "'" + cid + "'", null);
        return c48;
    }

    public  Cursor getmrcode(String cid)
    {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select MR_CODE from COLLECTION_OUTPUT where CONSUMER_ID= " + "'" + cid + "'", null);
        return c48;
    }

    public Cursor getmachineid(String cid)
    {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select MACHINE_ID from COLLECTION_OUTPUT where CONSUMER_ID= " + "'" + cid + "'", null);
        return c48;
    }

    public Cursor addingbillno() {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select count(RRNO)+1 BILL_NO from MAST_OUT", null);
        return c48;
    }

    public Cursor addingSSNO() {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select count(CONSNO)+1 SSNO from MAST_OUT", null);
        return c48;
    }

    public Cursor countbilled() {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select count(RRNO) BILLED from MAST_OUT", null);
        return c48;
    }

    public Cursor countfortransaction(String date) {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select count(RECPT_DATE)COUNT, TRANSACTION_ID from COLLECTION_OUTPUT where RECPT_DATE = " + "'" + date + "'", null);
        return c48;
    }

    public Cursor getdatesfromcollectionoutput() {
        Cursor c48 = null;
        c48 = myDataBase.rawQuery("select (RECPT_DATE_TIME)DATE from COLLECTION_OUTPUT where MODE_PAYMENT = '0'", null);
        return c48;
    }

    public Cursor report(String value) {
        Cursor c5 = null;
        c5 = myDataBase.rawQuery("select * from MAST_OUT,SUBDIV_DETAILS where MAST_OUT.CONSNO = " + "'" + value + "'", null);
        return c5;
    }

    public Cursor getbilledvalues() {
        return myDataBase.rawQuery("SELECT * FROM MAST_OUT, SUBDIV_DETAILS WHERE MAST_OUT.ONLINE_FLAG ='N'", null);
    }

    public void onlinebilledrecord(String id, Handler handler) {
        Cursor data = null;
        data = myDataBase.rawQuery("UPDATE MAST_OUT SET ONLINE_FLAG = 'Y' WHERE _id='"+id+"'", null);
        data.moveToNext();
        data.close();
        handler.sendEmptyMessage(ONLINE_UPDATE_FLAG_SUCCESS);
    }

    public Cursor reportbyid(String value) {
        Cursor c5 = null;
        c5 = myDataBase.rawQuery("select *, (MAST_OUT._id)ID from MAST_OUT,SUBDIV_DETAILS where MAST_OUT.CONSNO = " + "'" + value + "'", null);
        return c5;
    }

    public Cursor searchduplicate(String RRNO, String CONSNO) {
        Cursor data = null;
        data = myDataBase.rawQuery("SELECT * FROM MAST_OUT WHERE RRNO = '"+RRNO+"' AND CONSNO = '"+CONSNO+"'", null);
        return data;
    }

    public Cursor reportbyname(String value) {
        Cursor c5 = null;
        c5 = myDataBase.rawQuery("select * from MAST_OUT,SUBDIV_DETAILS where MAST_OUT.NAME = " + "'" + value + "'", null);
        return c5;
    }

    public Cursor subdivdetails() {
        Cursor c49 = null;
        c49 = myDataBase.rawQuery("select * from SUBDIV_DETAILS", null);
        return c49;
    }

    public void updateBillingStatus(String updtebillstatus) {
        ContentValues cv = new ContentValues();
        cv.put("BILLING_STATUS", updtebillstatus);
        myDataBase.update("SUBDIV_DETAILS", cv, null, null);
    }

    public void updateUpdateStatus(String updatevalue) {
        ContentValues cv = new ContentValues();
        cv.put("Upload_Status", updatevalue);
        myDataBase.update("SUBDIV_DETAILS", cv, null, null);
    }

    public void updateforCollection(String update) {
        ContentValues cv = new ContentValues();
        cv.put("COLLECTION_FLAG", update);
        myDataBase.update("SUBDIV_DETAILS", cv, null, null);
    }

    public void changename(String name) {
        ContentValues cv = new ContentValues();
        cv.put("BT_PRINTER", name);
        myDataBase.update("SUBDIV_DETAILS", cv, null, null);
    }

    public void changeprintertype(String Printertype, String logoprint, String barcode, String printerformat) {
        ContentValues cv = new ContentValues();
        cv.put("PRINTER_TYPE", Printertype);
        cv.put("LOGO_PRINT", logoprint);
        cv.put("BARCODE_PRINT", barcode);
        cv.put("PRINTER_FORMAT", printerformat);
        myDataBase.update("SUBDIV_DETAILS", cv, null, null);
        //		myDataBase.rawQuery("UPDATE SUBDIV_DETAILS SET PRINTER_TYPE = "+"'"+Printertype+"'," +
        //				"LOGO_PRINT = "+"'"+logoprint+"',BARCODE_PRINT = "+"'"+barcode+"'", null);

    }

    public void changeprinterformat(String format) {
        ContentValues cv = new ContentValues();
        cv.put("PRINTER_FORMAT", format);
        myDataBase.update("SUBDIV_DETAILS", cv, null, null);
    }

    public void changebtprinter(String format) {
        ContentValues cv = new ContentValues();
        cv.put("BT_PRINTER", format);
        myDataBase.update("SUBDIV_DETAILS", cv, null, null);
    }

    public Cursor billUnbillPieChart() {
        Cursor c = null;
        c = myDataBase.rawQuery("Select * from CollectionsBillUnBil", null);
        return c;
    }

    // android:screenOrientation="landscape"
    public Cursor billUnbillTotal() {
        Cursor c = null;
        c = myDataBase.rawQuery("Select * from BillUnBillTotal", null);
        return c;
    }

    public Cursor statusPieChart() {
        Cursor c = null;
        c = myDataBase.rawQuery("Select * from CollectionsStatusTotalLess", null);
        return c;
    }

    public Cursor statusTotalPieChart() {
        Cursor c = null;
        c = myDataBase.rawQuery("Select * from CollectionsStatusTotal", null);
        return c;
    }

    public Cursor tariffPieChart() {
        Cursor c = null;
        c = myDataBase.rawQuery("Select * from Pie_Tariff", null);
        return c;
    }

    public Cursor collection_output() {
        Cursor c50 = null;
        c50 = myDataBase.rawQuery("Select * from COLLECTION_OUTPUT", null);
        return c50;
    }

    public Cursor collecteddetails(String CustomerID) {
        Cursor c50 = null;
        c50 = myDataBase.rawQuery("Select * from COLLECTION_OUTPUT where CONSUMER_ID = " + "'" + CustomerID + "'", null);
        return c50;
    }

    public Cursor updatebill_printed(String printed, String RRNO) {
        Cursor c51 = null;
        c51 = myDataBase.rawQuery("update MAST_OUT set BILL_PRINTED = " + "'" + printed + "'" + " where MAST_OUT.RRNO = " + "'" + RRNO + "'", null);
        return c51;
    }

    public Cursor updatepaychq_printed(String printed, String Chequeno) {
        Cursor c51 = null;
        c51 = myDataBase.rawQuery("update COLLECTION_OUTPUT set PAYMENT_PRINTED = " + "'" + printed + "'" + " where COLLECTION_OUTPUT.CHQ_NO = " + "'" + Chequeno + "'", null);
        return c51;
    }

    public Cursor updatepaycash_printed(String printed, String Transactionid) {
        Cursor c51 = null;
        c51 = myDataBase.rawQuery("update COLLECTION_OUTPUT set PAYMENT_PRINTED = " + "'" + printed + "'" + " where COLLECTION_OUTPUT.TRANSACTION_ID = " + "'" + Transactionid + "'", null);
        return c51;
    }

    public Cursor fdrname() {
        Cursor c52 = null;
        c52 = myDataBase.rawQuery("select Feder_Name from DTC_Details", null);
        return c52;
    }

    public Cursor tccode(String fdrname) {
        Cursor c53 = null;
        c53 = myDataBase.rawQuery("select DTC_Code from DTC_Details where Feder_Name = " + "'" + fdrname + "'", null);
        return c53;
    }

    public Cursor fdr_details(String value) {
        Cursor c54 = null;
        c54 = myDataBase.rawQuery("select * from DTC_Details where Feder_Name = " + "'" + value + "'", null);
        return c54;
    }

    public Cursor dtc_details(String tccode, String fdrname) {
        Cursor c54 = null;
        c54 = myDataBase.rawQuery("select * from DTC_Details where DTC_Code = " + "'" + tccode + "' and Feder_Name = " + "'" + fdrname + "'", null);
        return c54;
    }

    public Cursor updateDtcCurrentReading(String Curreading, String tccode, String fdrname,
                                          String Consumption, String Longitude, String Latitude) {
        Cursor c55 = null;
        c55 = myDataBase.rawQuery("update DTC_Details set Current_Reading = " + "'" + Curreading + "',Consumption = " + "'" + Consumption + "'," +
                "Long = " + "'" + Longitude + "',Lat = " + "'" + Latitude + "' where DTC_Code = " + "'" + tccode + "' and " + "Feder_Name = " + "'" + fdrname + "'", null);
        return c55;
    }

    public Cursor readtariffconfig() {
        Cursor c56 = null;
        c56 = myDataBase.rawQuery("select * from TARIFF_CONFIG_CURRENT", null);
        return c56;
    }

    public Cursor counttobill() {
        Cursor c57 = null;
        c57 = myDataBase.rawQuery("SELECT COUNT(*)CUST,MRCODE FROM MAST_CUST", null);
        return c57;
    }

    public Cursor countbilledrecords() {
        Cursor data = null;
        data = myDataBase.rawQuery("SELECT COUNT(*)CUST FROM MAST_OUT", null);
        return data;
    }

    public Cursor countchequedetails() {
        Cursor c58 = null;
        c58 = myDataBase.rawQuery("select count(MODE_PAYMENT)CHEQUE from COLLECTION_OUTPUT where MODE_PAYMENT = '1'", null);
        return c58;
    }

    public Cursor countcashdetails() {
        Cursor c58 = null;
        c58 = myDataBase.rawQuery("select count(MODE_PAYMENT)CASH from COLLECTION_OUTPUT where MODE_PAYMENT = '0'", null);
        return c58;
    }

    public Cursor flECcount() {
        Cursor c58 = null;
        c58 = myDataBase.rawQuery("select * from TARIFF_CONFIG_CURRENT where TARIFF_CODE = '23'", null);
        return c58;
    }

    public Cursor getrowemptytable() {
        Cursor c59 = null;
        c59 = myDataBase.rawQuery("select * from MAST_ROW_EMPTY", null);
        return c59;
    }

    public Cursor getdisconndetails() {
        Cursor c59 = null;
        c59 = myDataBase.rawQuery("select * from Disconn_Details", null);
        return c59;
    }

    public Cursor getdisconndetailsbyrrno(String RRNO, String Consid) {
        Cursor c59 = null;
        c59 = myDataBase.rawQuery("select * from Disconn_Details where RRNO = '" + RRNO + "' and CONSNO = '" + Consid + "'", null);
        return c59;
    }

    public Cursor getdisconndetailsforname(String RRNO, String Consid) {
        Cursor c59 = null;
        c59 = myDataBase.rawQuery("select * from COLLECTION_INPUT where RRNO = '" + RRNO + "' and CONSUMER_ID = '" + Consid + "'", null);
        return c59;
    }

    public Cursor getdisconndetailsreport(String tarrif, String FromAmount, String ToAmount) {
        Cursor c59 = null;
        c59 = myDataBase.rawQuery("select * from Disconn_Details where TARRIF in(" + tarrif + ") and " +
                "ARREARS between " + FromAmount + " and " + ToAmount + " and MTR_SERIAL_NO = 'N'", null);
        fcall.logStatus("select * from Disconn_Details where TARRIF in(" + tarrif + ") and " +
                "ARREARS between " + FromAmount + " and " + ToAmount + " and MTR_SERIAL_NO = 'N'");
        return c59;
    }

    public Cursor updatedisconndetailsreport(String rrno, String consid) {
        Cursor c51 = null;
        c51 = myDataBase.rawQuery("update Disconn_Details set MTR_SERIAL_NO = 'Y' where " +
                "Disconn_Details.RRNO = " + "'" + rrno + "' and Disconn_Details.CONSNO = " + "'" + consid + "'", null);
        return c51;
    }

    public Cursor getdisconnvillagelist() {
        Cursor c59 = null;
        c59 = myDataBase.rawQuery("select distinct VILLAGE from Disconn_Details", null);
        return c59;
    }

    public Cursor getmaxdisconndetails() {
        Cursor c59 = null;
        c59 = myDataBase.rawQuery("select RRNO, CONSNO, NAME, MAX(ARREARS) MAXIMUM from Disconn_Details " +
                "where ARREARS = (select MAX(ARREARS) from Disconn_Details where MTR_SERIAL_NO = 'N')", null);
        return c59;
    }

    public Cursor getdisconnvillagedetaillist(String village, String tarrif, String FromAmount, String ToAmount) {
        Cursor c59 = null;
        c59 = myDataBase.rawQuery("select * from Disconn_Details where VILLAGE = '" + village + "' " +
                "and TARRIF in(" + tarrif + ") and ARREARS between " + FromAmount + " and " + ToAmount + " and " +
                "MTR_SERIAL_NO = 'N'", null);
        return c59;
    }

    public Cursor getdisconntccodelist() {
        Cursor c59 = null;
        c59 = myDataBase.rawQuery("select distinct TCCODE from Disconn_Details order by TCCODE ASC", null);
        return c59;
    }

    public Cursor getdisconntccodedetaillist(String tccode, String tarrif, String FromAmount, String ToAmount) {
        Cursor c59 = null;
        c59 = myDataBase.rawQuery("select * from Disconn_Details where TCCODE = '" + tccode + "' " +
                "and TARRIF in(" + tarrif + ") and ARREARS between " + FromAmount + " and " + ToAmount + " and " +
                "MTR_SERIAL_NO = 'N'", null);
        return c59;
    }

    public Cursor getreaddate() {
        Cursor c59 = null;
        c59 = myDataBase.rawQuery("select (substr(min(READDATE),1,2) || " + "_" + " || max(READDATE)) " +
                "READDATE1 from MAST_OUT", null);
        return c59;
    }

    public Cursor getmaxreaddate() {
        Cursor c59 = null;
        c59 = myDataBase.rawQuery("select max(READDATE) READDATE1 from MAST_CUST", null);
        return c59;
    }

    public void deletemastcust() {
        myDataBase.delete("MAST_CUST", null, null);
    }

    public void deletemastout() {
        myDataBase.delete("MAST_OUT", null, null);
    }

    public void deletebankdetails() {
        myDataBase.delete("BANK_DETAILS", null, null);
    }

    public void deletebillingstatus() {
        myDataBase.delete("BILLING_STATUS", null, null);
    }

    public void deletepaymentmodes() {
        myDataBase.delete("Mode", null, null);
    }

    public void deletesubdivdetails() {
        myDataBase.delete("SUBDIV_DETAILS", null, null);
    }

    public void deletetariffdetails() {
        myDataBase.delete("TARIFF_CONFIG_CURRENT", null, null);
    }

    public void deletecollectioninput() {
        myDataBase.delete("COLLECTION_INPUT", null, null);
    }

    public void deletecollectionoutput() {
        myDataBase.delete("COLLECTION_OUTPUT", null, null);
    }

    public void deletemastrowempty() {
        myDataBase.delete("MAST_ROW_EMPTY", null, null);
    }

    public void deletedisconnectiondetails() {
        myDataBase.delete("Disconn_Details", null, null);
    }

    public void insertInMastCust(ContentValues cv1) {
        myDataBase.insert("MAST_CUST", null, cv1);
    }

    public void insertInBankDetails(ContentValues cv1) {
        myDataBase.insert("BANK_DETAILS", null, cv1);
    }

    public void insertInBilllingStatus(ContentValues cv1) {
        myDataBase.insert("BILLING_STATUS", null, cv1);
    }

    public void insertInMode(ContentValues cv1) {
        myDataBase.insert("Mode", null, cv1);
    }

    public void insertInSubdivDetails(ContentValues cv1) {
        myDataBase.insert("SUBDIV_DETAILS", null, cv1);
    }

    public void insertInTariffDetails(ContentValues cv1) {
        myDataBase.insert("TARIFF_CONFIG_CURRENT", null, cv1);
    }

    public void insertInCollectionInput(ContentValues cv1) {
        myDataBase.insert("COLLECTION_INPUT", null, cv1);
    }

    public Cursor insertCollectionValues() {
        Cursor insert = null;
        insert = myDataBase.rawQuery("INSERT INTO COLLECTION_INPUT (RRNO,CONSUMER_ID,LF_NO,NAME,TARIFF_NAME,CHQ_DISSHONOUR_FLAG," +
                "CHQ_DISSHONOUR_DATE,SUB_DIVISION,MRCODE,PAYABLE_AMOUNT) SELECT MAST_CUST.RRNO,MAST_CUST.CONSNO,MAST_CUST.LEGFOL," +
                "MAST_CUST.NAME,MAST_CUST.TARIFF_NAME,'0','0',(SELECT SUBDIV_CODE FROM SUBDIV_DETAILS)DIVISION,MAST_CUST.MRCODE,'0' FROM MAST_CUST", null);
        return insert;
    }

    public void insertinDisconnection(ContentValues cv1) {
        myDataBase.insert("Disconn_Details", null, cv1);
    }

    public void insertInDISCONN_PAID(ContentValues cv1) {
        myDataBase.insert("DISCONN_PAID", null, cv1);
    }

    public void deleteDISCONN_PAIDdata() {
        myDataBase.delete("DISCONN_PAID", null, null);
    }

    public void insertInDISCONNECTED(ContentValues cv1) {
        myDataBase.insert("DISCONNECTED", null, cv1);
    }

    public void deleteDISCONNECTEDdata() {
        myDataBase.delete("DISCONNECTED", null, null);
    }

    public void insertACCTINFO(String MRCODE, String ACCT, String ORRNO, String NRRNO, String NAME, String ADDR, String TARIF, String CUSTCL,
                               String NUMCONN, String SLKW, String SLHP, String SD, String TVMTRFLG, String CONNLDHP, String CONNLDKW,
                               String RATELDHP, String RATELDKW, String DBTFLG, String TODFLG, String TAXEXEMPT, String VACANTFLG, String SEASONFLG,
                               String LT1TOLT2, String AVGPRVSEASON, String DLBILL, String CUSTINFO_Id) {
        ContentValues cv = new ContentValues();
        cv.put("MRCODE", MRCODE);
        cv.put("ACCT", ACCT);
        cv.put("ORRNO", ORRNO);
        cv.put("NRRNO", NRRNO);
        cv.put("NAME", NAME);
        cv.put("ADDR", ADDR);
        cv.put("TARIF", TARIF);
        cv.put("CUSTCL", CUSTCL);
        cv.put("NUMCONN", NUMCONN);
        cv.put("SLKW", SLKW);
        cv.put("SLHP", SLHP);
        cv.put("SD", SD);
        cv.put("TVMTRFLG", TVMTRFLG);
        cv.put("CONNLDHP", CONNLDHP);
        cv.put("CONNLDKW", CONNLDKW);
        cv.put("RATELDHP", RATELDHP);
        cv.put("RATELDKW", RATELDKW);
        cv.put("DBTFLG", DBTFLG);
        cv.put("TODFLG", TODFLG);
        cv.put("TAXEXEMPT", TAXEXEMPT);
        cv.put("VACANTFLG", VACANTFLG);
        cv.put("SEASONFLG", SEASONFLG);
        cv.put("LT1TOLT2", LT1TOLT2);
        cv.put("AVGPRVSEASON", AVGPRVSEASON);
        cv.put("DLBILL", DLBILL);
        cv.put("CUSTINFO_Id", CUSTINFO_Id);
        myDataBase.insert("ACCTINFO", null, cv);
    }

    public void insertAVERAGECONS(String AVGKWH, String AVGTODOFF, String AVGTODON, String AVGTODNM, String AVGMD, String AVGMDOFF, String AVGMDON,
                                  String AVGMDNM, String AVGKVAH, String AVGPF, String CUSTINFO_Id) {
        ContentValues cv = new ContentValues();
        cv.put("AVGKWH", AVGKWH);
        cv.put("AVGTODOFF", AVGTODOFF);
        cv.put("AVGTODON", AVGTODON);
        cv.put("AVGTODNM", AVGTODNM);
        cv.put("AVGMD", AVGMD);
        cv.put("AVGMDOFF", AVGMDOFF);
        cv.put("AVGMDON", AVGMDON);
        cv.put("AVGMDNM", AVGMDNM);
        cv.put("AVGKVAH", AVGKVAH);
        cv.put("AVGPF", AVGPF);
        cv.put("CUSTINFO_Id", CUSTINFO_Id);
        myDataBase.insert("AVERAGECONS", null, cv);
    }

    public void insertBILLSEQ(String MRCYC, String ROUTE, String RTSEQ, String CUSTINFO_Id) {
        ContentValues cv = new ContentValues();
        cv.put("MRCYC", MRCYC);
        cv.put("ROUTE", ROUTE);
        cv.put("RTSEQ", RTSEQ);
        cv.put("CUSTINFO_Id", CUSTINFO_Id);
        myDataBase.insert("BILLSEQ", null, cv);
    }

    public void insertCUSTINFO(String CUSTINFO_Id, String MTRXCHNGFLG, String BILLMSG, String REBATEAMT, String OTHER, String ARREARS,
                               String INTEREST, String CREADJ) {
        ContentValues cv = new ContentValues();
        cv.put("CUSTINFO_Id", CUSTINFO_Id);
        cv.put("MTRXCHNGFLG", MTRXCHNGFLG);
        cv.put("BILLMSG", BILLMSG);
        cv.put("REBATEAMT", REBATEAMT);
        cv.put("OTHER", OTHER);
        cv.put("ARREARS", ARREARS);
        cv.put("INTEREST", INTEREST);
        cv.put("CREADJ", CREADJ);
        myDataBase.insert("CUSTINFO", null, cv);
    }

    public void insertELFLG(String ELFLG_Name, String ELFLG_Text, String REBATE_Id) {
        ContentValues cv = new ContentValues();
        cv.put("NAME", ELFLG_Name);
        cv.put("ELFLAG_Text", ELFLG_Text);
        cv.put("REBATE_Id", "" + REBATE_Id);
        myDataBase.insert("ELFLG", null, cv);
    }

    public void insertENDDT(String NAME, String REBATE_Id) {
        ContentValues cv = new ContentValues();
        cv.put("NAME", NAME);
        cv.put("REBATE_Id", REBATE_Id);
        myDataBase.insert("ENDDT", null, cv);
    }

    public void insertMTRXCHANGE1(String OLDMTR, String RMDATE, String RMREADSTS, String RMMTRCON, String RMREADKWH, String RMREADTODOFF,
                                  String RMREADTODON, String RMREADTODNM, String RMREADMD, String RMREADMDOFF, String RMREADMDON, String RMREADMDNM,
                                  String RMREADKVAH, String RMREADPF, String NEWMTR, String INDATE, String INREADSTS, String INMTRCON,
                                  String INREADKWH, String INREADTODOFF, String INREADTODON, String INREADTODNM, String INREADMD, String INREADMDOFF,
                                  String INREADMDON, String INREADMDNM, String INREADKVAH, String INREADPF, String CUSTINFO_Id) {
        ContentValues cv = new ContentValues();
        cv.put("OLDMTR", OLDMTR);
        cv.put("RMDATE", RMDATE);
        cv.put("RMREADSTS", RMREADSTS);
        cv.put("RMMTRCON", RMMTRCON);
        cv.put("RMREADKWH", RMREADKWH);
        cv.put("RMREADTODOFF", RMREADTODOFF);
        cv.put("RMREADTODON", RMREADTODON);
        cv.put("RMREADTODNM", RMREADTODNM);
        cv.put("RMREADMD", RMREADMD);
        cv.put("RMREADMDOFF", RMREADMDOFF);
        cv.put("RMREADMDON", RMREADMDON);
        cv.put("RMREADMDNM", RMREADMDNM);
        cv.put("RMREADKVAH", RMREADKVAH);
        cv.put("RMREADPF", RMREADPF);
        cv.put("NEWMTR", NEWMTR);
        cv.put("INDATE", INDATE);
        cv.put("INREADSTS", INREADSTS);
        cv.put("INMTRCON", INMTRCON);
        cv.put("INREADKWH", INREADKWH);
        cv.put("INREADTODOFF", INREADTODOFF);
        cv.put("INREADTODON", INREADTODON);
        cv.put("INREADTODNM", INREADTODNM);
        cv.put("INREADMD", INREADMD);
        cv.put("INREADMDOFF", INREADMDOFF);
        cv.put("INREADMDON", INREADMDON);
        cv.put("INREADMDNM", INREADMDNM);
        cv.put("INREADKVAH", INREADKVAH);
        cv.put("INREADPF", INREADPF);
        cv.put("CUSTINFO_Id", CUSTINFO_Id);
        myDataBase.insert("MTRXCHANGE1", null, cv);
    }

    public void insertPENALTY(String APP_PF, String VALUE_PF, String PENALTYLIST_Id) {
        ContentValues cv = new ContentValues();
        cv.put("APP_PF", APP_PF);
        cv.put("VALUE_PF", VALUE_PF);
        cv.put("PENALTYLIST_Id", PENALTYLIST_Id);
        myDataBase.insert("PENALTY", null, cv);
    }

    public void insertPENALTYLIST(String PENALTYLIST_Id, String CUSTINFO_Id) {
        ContentValues cv = new ContentValues();
        cv.put("PENALTYLIST_Id", PENALTYLIST_Id);
        cv.put("CUSTINFO_Id", CUSTINFO_Id);
        myDataBase.insert("PENALTYLIST", null, cv);
    }

    public void insertPRVMRREADS(String MTR, String READDT, String MTRCON, String FULLSCALE, String READSTS, String READKWH, String READTODOFF,
                                 String READTODON, String READTODNM, String READMD, String READMDOFF, String READMDON, String READMDNM,
                                 String READKVAH, String READPF, String CUSTINFO_Id) {
        ContentValues cv = new ContentValues();
        cv.put("MTR", MTR);
        cv.put("READDT", READDT);
        cv.put("MTRCON", MTRCON);
        cv.put("FULLSCALE", FULLSCALE);
        cv.put("READSTS", READSTS);
        cv.put("READKWH", READKWH);
        cv.put("READTODOFF", READTODOFF);
        cv.put("READTODON", READTODON);
        cv.put("READTODNM", READTODNM);
        cv.put("READMD", READMD);
        cv.put("READMDOFF", READMDOFF);
        cv.put("READMDON", READMDON);
        cv.put("READMDNM", READMDNM);
        cv.put("READKVAH", READKVAH);
        cv.put("READPF", READPF);
        cv.put("CUSTINFO_Id", CUSTINFO_Id);
        myDataBase.insert("PRVMRREADS", null, cv);
    }

    public void insertREBATE(String REBATE_Id, String REBATELIST_Id) {
        ContentValues cv = new ContentValues();
        cv.put("REBATE_Id", REBATE_Id);
        cv.put("REBATELIST_Id", REBATELIST_Id);
        myDataBase.insert("REBATE", null, cv);
    }

    public void insertREBATELIST(String REBATELIST_Id, String CUSTINFO_Id) {
        ContentValues cv = new ContentValues();
        cv.put("REBATELIST_Id", REBATELIST_Id);
        cv.put("CUSTINFO_Id", CUSTINFO_Id);
        myDataBase.insert("REBATELIST", null, cv);
    }

    public void insertSTARTDT(String NAME, String STARTDT_Text, String REBATE_Id) {
        ContentValues cv = new ContentValues();
        cv.put("NAME", NAME);
        cv.put("STARTDT_Text", STARTDT_Text);
        cv.put("REBATE_Id", REBATE_Id);
        myDataBase.insert("STARTDT", null, cv);
    }

    public Cursor update_DLCount() {
        Cursor dl = null;
        dl = myDataBase.rawQuery("UPDATE MAST_CUST SET DLCOUNT = '0'", null);
        return dl;
    }

    public Cursor insertintoMAST_CUST() {
        Cursor c = null;
        c = myDataBase.rawQuery("insert into mast_cust(MONTH,READDATE,RRNO,NAME,ADD1,TARIFF,MF,PREVSTAT,AVGCON,LINEMIN,SANCHP,SANCKW,PRVRED," +
                "FR,IR,DLCOUNT,ARREARS,PF_FLAG,BILLFOR,MRCODE,LEGFOL,ODDEVEN,SSNO,CONSNO,REBATE_FLAG,RREBATE,EXTRA1,DATA1,EXTRA2,DATA2,PH_NO," +
                "DEPOSIT,MTRDIGIT,PFVAL,BMDVAL,ASDAMT,IODAMT,BILL_NO,INTEREST_AMT,CAP_FLAG,TOD_FLAG,TOD_PREVIOUS1,TOD_PREVIOUS3,INT_ON_DEP," +
                "SO_FEEDER_TC_POLE,TARIFF_NAME,PREV_READ_DATE,BILL_DAYS,MTR_SERIAL_NO,CHQ_DISSHONOUR_FLAG,CHQ_DISHONOUR_DATE,FDRNAME,TCCODE," +
                "MTR_FLAG,NEW_TARIFF_EFFECT_DATE,_Id) SELECT SUBSTR(PRVMRREADS.READDT,2,2) READDT,(substr('00'||PRVMRREADS.READDT, -8, 2) || '-' || " +
                "substr('00'||PRVMRREADS.READDT, -6, 2) || '-' || substr('0000'||PRVMRREADS.READDT, -4, 4)) DATE,ACCTINFO.ORRNO,ACCTINFO.NAME,ACCTINFO.ADDR," +
                "TARIFF_CODE.TARIFF,PRVMRREADS.MTRCON,PRVMRREADS.READSTS,AVERAGECONS.AVGKWH,000,ACCTINFO.SLHP,ACCTINFO.SLKW,PRVMRREADS.READKWH,000,000," +
                "ACCTINFO.DLBILL,CUSTINFO.ARREARS,(CASE WHEN ACCTINFO.TVMTRFLG = 'S' THEN '1' ELSE '0' END) as 'TVMTRFLG' ,1,ACCTINFO.MRCODE,1/1,000," +
                "BILLSEQ.ROUTE,ACCTINFO.ACCT,000,000,CUSTINFO.BILLMSG,CUSTINFO.OTHER,000,000,000,000,LENGTH(PRVMRREADS.FULLSCALE) FULLSCALE,AVERAGECONS.AVGPF," +
                "000,000,000,000,000,000,(CASE WHEN ACCTINFO.TODFLG = 'S' THEN '1' ELSE '0' END) as 'TODFLG',000,000,000,000,TARIFF_CODE.TARIFFNAME," +
                "substr('00'||PRVMRREADS.READDT, -8, 2) || '-' || (substr('00'||PRVMRREADS.READDT, -6, 2)-01) || '-' || substr('0000'||PRVMRREADS.READDT, -4, 4) " +
                "PRVREADDT,000,PRVMRREADS.MTR,000,000,000,000,'M','25-04-2017',ACCTINFO.CUSTINFO_Id FROM ACCTINFO,AVERAGECONS,BILLSEQ,CUSTINFO,MTRXCHANGE1," +
                "PENALTYLIST,PRVMRREADS,REBATELIST,REBATE,STARTDT,ELFLG,ENDDT,TARIFF_CODE WHERE ACCTINFO.CUSTINFO_Id = AVERAGECONS.CUSTINFO_Id " +
                "AND ACCTINFO.CUSTINFO_Id = BILLSEQ.CUSTINFO_Id AND ACCTINFO.CUSTINFO_Id = CUSTINFO.CUSTINFO_Id AND ACCTINFO.CUSTINFO_Id = MTRXCHANGE1.CUSTINFO_Id " +
                "AND ACCTINFO.CUSTINFO_Id = PENALTYLIST.CUSTINFO_Id AND ACCTINFO.CUSTINFO_Id = PRVMRREADS.CUSTINFO_Id AND ACCTINFO.CUSTINFO_Id = REBATELIST.CUSTINFO_Id " +
                "AND PENALTYLIST.CUSTINFO_Id = PENALTYLIST.PENALTYLIST_Id AND REBATELIST.CUSTINFO_Id = REBATE.REBATE_Id AND REBATE.REBATELIST_Id = STARTDT.REBATE_Id " +
                "AND STARTDT.REBATE_Id = ELFLG.REBATE_Id AND STARTDT.REBATE_Id = ENDDT.REBATE_Id AND ACCTINFO.TARIF=TARIFF_CODE.TARIFFCODE", null);
        return c;
    }

    public Cursor checkACCTINFO() {
        Cursor c = null;
        c = myDataBase.rawQuery("SELECT * FROM ACCTINFO", null);
        return c;
    }

    public Cursor reprint_last_collection() {
        Cursor data = null;
        data = myDataBase.rawQuery("SELECT * FROM COLLECTION_OUTPUT WHERE REPRINT = (SELECT MAX(REPRINT)REPRINT from COLLECTION_OUTPUT)", null);
        return data;
    }

    public Cursor getReprint_value() {
        Cursor data = null;
        data = myDataBase.rawQuery("SELECT MAX(REPRINT)REPRINT from COLLECTION_OUTPUT", null);
        return data;
    }

    public void insert_Unbilled_records(ContentValues cv) {
        myDataBase.insert("MAST_UNBILLED", null, cv);
    }

    public Cursor getMaxinserted() {
        Cursor data = null;
        data = myDataBase.rawQuery("SELECT MAX(_id)MAX FROM MAST_OUT", null);
        return data;
    }

    public Cursor getHT_report(String id) {
        Cursor data = null;
        data = myDataBase.rawQuery("SELECT * FROM MAST_OUT WHERE _id = '"+id+"'", null);
        return data;
    }

    public Cursor deleteHT_report(String id) {
        Cursor data = null;
        data = myDataBase.rawQuery("DELETE FROM MAST_OUT WHERE _id = '"+id+"'", null);
        return data;
    }

    public void update_Extra_Billing(String ph_no, String aad_no, String mail_id, String election, String water, String ration, String house) {
        Cursor data = null;
        data = myDataBase.rawQuery("UPDATE MAST_OUT SET PH_NO = '"+ph_no+"' AND AADHAAR = '"+aad_no+"' AND MAIL ='"+mail_id+"' " +
                "AND ELECTION ='"+election+"' AND RATION='"+ration+"' AND WATER='"+water+"' AND HOUSE_NO='"+house+"'", null);
        data.moveToNext();
        data.close();
    }

    public void insert_meter_asset_details(ContentValues cv) {
        myDataBase.insert("METER_ASSET", null, cv);
    }

    public Cursor meter_asset_details(String account_id) {
        return myDataBase.rawQuery("SELECT * FROM METER_ASSET WHERE ACCOUNT_ID = '"+account_id+"'", null);
    }

    public Cursor feder_details() {
        return myDataBase.rawQuery("select DISTINCT FEDER_NAME, FEDER_CODE FROM TC_DETAILS", null);
    }

    public String feder_code(String value) {
        Cursor data = myDataBase.rawQuery("select DISTINCT FEDER_CODE FROM TC_DETAILS WHERE FEDER_NAME = '"+value+"'", null);
        data.moveToNext();
        String code = data.getString(data.getColumnIndexOrThrow("FEDER_CODE"));
        data.close();
        return code;
    }

    public Cursor dtc_code_details(String value) {
        return myDataBase.rawQuery("select DTC_CODE FROM TC_DETAILS WHERE Feder_Code = '"+value+"'", null);
    }

    public boolean doesTableExist() {
        Cursor cursor = myDataBase.rawQuery("select DISTINCT FEDER_NAME, FEDER_CODE FROM TC_DETAILS", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}