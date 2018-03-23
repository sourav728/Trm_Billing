package com.example.tvd.trm_billing.values;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.example.tvd.trm_billing.database.Databasehelper;
import com.example.tvd.trm_billing.database.TRMDatabase1;
import com.example.tvd.trm_billing.database.TRMDatabase2;
import com.example.tvd.trm_billing.database.UploadDatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MastoutReading {
    private FunctionCalls functionCalls = new FunctionCalls();

	public void insertintomastoutandtextfile(Context context, String consid, Activity activity, SharedPreferences shared) {
		FunctionCalls fcall = new FunctionCalls();
		Databasehelper dbh = new Databasehelper(context);
		dbh.openDatabase();
		UploadDatabase upload = new UploadDatabase(context);
		upload.open();
        TRMDatabase1 trmDatabase1 = new TRMDatabase1(context);
        trmDatabase1.open();
        TRMDatabase2 trmDatabase2 = new TRMDatabase2(context);
        trmDatabase2.open();
		Cursor c = dbh.reportbyid(consid);
		c.moveToNext();
		String MONTH = c.getString(c.getColumnIndex("MONTH"));
		String READDATE = c.getString(c.getColumnIndex("READDATE"));
		String RRNO = c.getString(c.getColumnIndex("RRNO"));
		String NAME = c.getString(c.getColumnIndex("NAME"));
		String ADD1 = c.getString(c.getColumnIndex("ADD1"));
		String TARIFF = c.getString(c.getColumnIndex("TARIFF"));
		String MF = c.getString(c.getColumnIndex("MF"));
		String PREVSTAT = c.getString(c.getColumnIndex("PREVSTAT"));
		String AVGCON = c.getString(c.getColumnIndex("AVGCON"));
		String LINEMIN = c.getString(c.getColumnIndex("LINEMIN"));
		String SANCHP = c.getString(c.getColumnIndex("SANCHP"));
		String SANCKW = c.getString(c.getColumnIndex("SANCKW"));
		String PRVRED = c.getString(c.getColumnIndex("PRVRED"));
		String FR = c.getString(c.getColumnIndex("FR"));
		String IR = c.getString(c.getColumnIndex("IR"));
		String DLCOUNT = c.getString(c.getColumnIndex("DLCOUNT"));
		String ARREARS = c.getString(c.getColumnIndex("ARREARS"));
		String PF_FLAG = c.getString(c.getColumnIndex("PF_FLAG"));
		String BILLFOR = c.getString(c.getColumnIndex("BILLFOR"));
		String MRCODE = c.getString(c.getColumnIndex("MRCODE"));
		String LEGFOL = c.getString(c.getColumnIndex("LEGFOL"));
		String ODDEVEN = c.getString(c.getColumnIndex("ODDEVEN"));
		String SSNO = c.getString(c.getColumnIndex("SSNO"));
		String CONSNO = c.getString(c.getColumnIndex("CONSNO"));
		String REBATE_FLAG = c.getString(c.getColumnIndex("REBATE_FLAG"));
		String RREBATE = c.getString(c.getColumnIndex("RREBATE"));
		String EXTRA1 = c.getString(c.getColumnIndex("EXTRA1"));
		String DATA1 = c.getString(c.getColumnIndex("DATA1"));
		String EXTRA2 = c.getString(c.getColumnIndex("EXTRA2"));
		String DATA2 = c.getString(c.getColumnIndex("DATA2"));
		String PH_NO;
		try {
			PH_NO = c.getString(c.getColumnIndex("PH_NO"));
		} catch (NullPointerException e) {
			e.printStackTrace();
			PH_NO = "";
		}
		String DEPOSIT = c.getString(c.getColumnIndex("DEPOSIT"));
		String MTRDIGIT = c.getString(c.getColumnIndex("MTRDIGIT"));
		String ASDAMT = c.getString(c.getColumnIndex("ASDAMT"));
		String IODAMT = c.getString(c.getColumnIndex("IODAMT"));
		String PFVAL = c.getString(c.getColumnIndex("PFVAL"));
		String BMDVAL = c.getString(c.getColumnIndex("BMDVAL"));
		String BILL_NO;
		try {
			BILL_NO = c.getString(c.getColumnIndex("BILL_NO"));
		} catch (NullPointerException e) {
			e.printStackTrace();
			BILL_NO = "";
		}
		String INTEREST_AMT = c.getString(c.getColumnIndex("INTEREST_AMT"));
		String CAP_FLAG = c.getString(c.getColumnIndex("CAP_FLAG"));
		String TOD_FLAG = c.getString(c.getColumnIndex("TOD_FLAG"));
		String TOD_PREVIOUS1 = c.getString(c.getColumnIndex("TOD_PREVIOUS1"));
		String TOD_PREVIOUS3 = c.getString(c.getColumnIndex("TOD_PREVIOUS3"));
		String INT_ON_DEP = c.getString(c.getColumnIndex("INT_ON_DEP"));
		String SO_FEEDER_TC_POLE = c.getString(c.getColumnIndex("SO_FEEDER_TC_POLE"));
		String TARIFF_NAME = c.getString(c.getColumnIndex("TARIFF_NAME"));
		String PREV_READ_DATE = c.getString(c.getColumnIndex("PREV_READ_DATE"));
		String BILL_DAYS = c.getString(c.getColumnIndex("BILL_DAYS"));
		String MTR_SERIAL_NO = c.getString(c.getColumnIndex("MTR_SERIAL_NO"));
		String FDRNAME = c.getString(c.getColumnIndex("FDRNAME"));
		String TCCODE = c.getString(c.getColumnIndex("TCCODE"));
		String MTR_FLAG = c.getString(c.getColumnIndex("MTR_FLAG"));
		String PRES_RDG = c.getString(c.getColumnIndex("PRES_RDG"));
		String PRES_STS = c.getString(c.getColumnIndex("PRES_STS"));
		String UNITS = c.getString(c.getColumnIndex("UNITS"));
		String FIX = c.getString(c.getColumnIndex("FIX"));
		String ENGCHG = c.getString(c.getColumnIndex("ENGCHG"));
		String REBATE_AMOUNT = c.getString(c.getColumnIndex("REBATE_AMOUNT"));
		String TAX_AMOUNT = c.getString(c.getColumnIndex("TAX_AMOUNT"));
		String BMD_PENALTY = c.getString(c.getColumnIndex("BMD_PENALTY"));
		String PF_PENALTY = c.getString(c.getColumnIndex("PF_PENALTY"));
		String PAYABLE = c.getString(c.getColumnIndex("PAYABLE"));
		String BILLDATE = c.getString(c.getColumnIndex("BILLDATE"));
		String BILLTIME = c.getString(c.getColumnIndex("BILLTIME"));
		String TOD_CURRENT1 = c.getString(c.getColumnIndex("TOD_CURRENT1"));
		String TOD_CURRENT3 = c.getString(c.getColumnIndex("TOD_CURRENT3"));
		String GOK_SUBSIDY = c.getString(c.getColumnIndex("GOK_SUBSIDY"));
		String DEM_REVENUE = c.getString(c.getColumnIndex("DEM_REVENUE"));
		String GPS_LAT = c.getString(c.getColumnIndex("GPS_LAT"));
		String GPS_LONG = c.getString(c.getColumnIndex("GPS_LONG"));
		String IMGADD;
		try {
			IMGADD = c.getString(c.getColumnIndex("IMGADD"));
		} catch (NullPointerException e) {
			e.printStackTrace();
			IMGADD = "";
		}
		String PAYABLE_REAL = c.getString(c.getColumnIndex("PAYABLE_REAL"));
		String PAYABLE_PROFIT = c.getString(c.getColumnIndex("PAYABLE_PROFIT"));
		String PAYABLE_LOSS = c.getString(c.getColumnIndex("PAYABLE_LOSS"));
		String BILL_PRINTED = c.getString(c.getColumnIndex("BILL_PRINTED"));
		String FSLAB1 = c.getString(c.getColumnIndex("FSLAB1"));
		String FSLAB2 = c.getString(c.getColumnIndex("FSLAB2"));
		String FSLAB3 = c.getString(c.getColumnIndex("FSLAB3"));
		String FSLAB4 = c.getString(c.getColumnIndex("FSLAB4"));
		String FSLAB5 = c.getString(c.getColumnIndex("FSLAB5"));
		String ESLAB1 = c.getString(c.getColumnIndex("ESLAB1"));
		String ESLAB2 = c.getString(c.getColumnIndex("ESLAB2"));
		String ESLAB3 = c.getString(c.getColumnIndex("ESLAB3"));
		String ESLAB4 = c.getString(c.getColumnIndex("ESLAB4"));
		String ESLAB5 = c.getString(c.getColumnIndex("ESLAB5"));
		String ESLAB6 = c.getString(c.getColumnIndex("ESLAB6"));
		String CHARITABLE_RBT_AMT = c.getString(c.getColumnIndex("CHARITABLE_RBT_AMT"));
		String SOLAR_RBT_AMT = c.getString(c.getColumnIndex("SOLAR_RBT_AMT"));
		String FL_RBT_AMT = c.getString(c.getColumnIndex("FL_RBT_AMT"));
		String HANDICAP_RBT_AMT = c.getString(c.getColumnIndex("HANDICAP_RBT_AMT"));
		String PL_RBT_AMT = c.getString(c.getColumnIndex("PL_RBT_AMT"));
		String IPSET_RBT_AMT = c.getString(c.getColumnIndex("IPSET_RBT_AMT"));
		String REBATEFROMCCB_AMT = c.getString(c.getColumnIndex("REBATEFROMCCB_AMT"));
		String TOD_CHARGES = c.getString(c.getColumnIndex("TOD_CHARGES"));
		String PF_PENALITY_AMT = c.getString(c.getColumnIndex("PF_PENALITY_AMT"));
		String EXLOAD_MDPENALITY = c.getString(c.getColumnIndex("EXLOAD_MDPENALITY"));
		String CURR_BILL_AMOUNT = c.getString(c.getColumnIndex("CURR_BILL_AMOUNT"));
		String ROUNDING_AMOUNT = c.getString(c.getColumnIndex("ROUNDING_AMOUNT"));
		String DUE_DATE = c.getString(c.getColumnIndex("DUE_DATE"));
		String DISCONN_DATE = c.getString(c.getColumnIndex("DISCONN_DATE"));
        String CREADJ = c.getString(c.getColumnIndex("CREADJ"));
        String PREADKVAH = c.getString(c.getColumnIndex("PREADKVAH"));

        ContentValues cv = new ContentValues();
        cv.put("MONTH", MONTH);
        cv.put("READDATE", READDATE);
        cv.put("RRNO", RRNO);
        cv.put("NAME", NAME);
        cv.put("ADD1", ADD1);
        cv.put("TARIFF", TARIFF);
        cv.put("MF", MF);
        cv.put("PREVSTAT", PREVSTAT);
        cv.put("AVGCON", AVGCON);
        cv.put("LINEMIN", LINEMIN);
        cv.put("SANCHP", SANCHP);
        cv.put("SANCKW", SANCKW);
        cv.put("PRVRED", PRVRED);
        cv.put("FR", FR);
        cv.put("IR", IR);
        cv.put("DLCOUNT", DLCOUNT);
        cv.put("ARREARS", ARREARS);
        cv.put("PF_FLAG", PF_FLAG);
        cv.put("BILLFOR", BILLFOR);
        cv.put("MRCODE", MRCODE);
        cv.put("LEGFOL", LEGFOL);
        cv.put("ODDEVEN", ODDEVEN);
        cv.put("SSNO", SSNO);
        cv.put("CONSNO", CONSNO);
        cv.put("REBATE_FLAG", REBATE_FLAG);
        cv.put("RREBATE", RREBATE);
        cv.put("EXTRA1", EXTRA1);
        cv.put("DATA1", DATA1);
        cv.put("EXTRA2", EXTRA2);
        cv.put("DATA2", DATA2);
        cv.put("PH_NO", PH_NO);
        cv.put("DEPOSIT", DEPOSIT);
        cv.put("MTRDIGIT", MTRDIGIT);
        cv.put("ASDAMT", ASDAMT);
        cv.put("IODAMT", IODAMT);
        cv.put("PFVAL", PFVAL);
        cv.put("BMDVAL", BMDVAL);
        cv.put("BILL_NO", BILL_NO);
        cv.put("INTEREST_AMT", INTEREST_AMT);
        cv.put("CAP_FLAG", CAP_FLAG);
        cv.put("TOD_FLAG", TOD_FLAG);
        cv.put("TOD_PREVIOUS1", TOD_PREVIOUS1);
        cv.put("TOD_PREVIOUS3", TOD_PREVIOUS3);
        cv.put("INT_ON_DEP", INT_ON_DEP);
        cv.put("SO_FEEDER_TC_POLE", SO_FEEDER_TC_POLE);
        cv.put("TARIFF_NAME", TARIFF_NAME);
        cv.put("PREV_READ_DATE", PREV_READ_DATE);
        cv.put("BILL_DAYS", BILL_DAYS);
        cv.put("MTR_SERIAL_NO", MTR_SERIAL_NO);
        cv.put("FDRNAME", FDRNAME);
        cv.put("TCCODE", TCCODE);
        cv.put("MTR_FLAG", MTR_FLAG);
        cv.put("PRES_RDG", PRES_RDG);
        cv.put("PRES_STS", PRES_STS);
        cv.put("UNITS", UNITS);
        cv.put("FIX", FIX);
        cv.put("ENGCHG", ENGCHG);
        cv.put("REBATE_AMOUNT", REBATE_AMOUNT);
        cv.put("TAX_AMOUNT", TAX_AMOUNT);
        cv.put("BMD_PENALTY", BMD_PENALTY);
        cv.put("PF_PENALTY", PF_PENALTY);
        cv.put("PAYABLE", PAYABLE);
        cv.put("BILLDATE", BILLDATE);
        cv.put("BILLTIME", BILLTIME);
        cv.put("TOD_CURRENT1", TOD_CURRENT1);
        cv.put("TOD_CURRENT3", TOD_CURRENT3);
        cv.put("GOK_SUBSIDY", GOK_SUBSIDY);
        cv.put("DEM_REVENUE", DEM_REVENUE);
        cv.put("GPS_LAT", GPS_LAT);
        cv.put("GPS_LONG", GPS_LONG);
        cv.put("IMGADD", IMGADD);
        cv.put("PAYABLE_REAL", PAYABLE_REAL);
        cv.put("PAYABLE_PROFIT", PAYABLE_PROFIT);
        cv.put("PAYABLE_LOSS", PAYABLE_LOSS);
        cv.put("BILL_PRINTED", BILL_PRINTED);
        cv.put("FSLAB1", FSLAB1);
        cv.put("FSLAB2", FSLAB2);
        cv.put("FSLAB3", FSLAB3);
        cv.put("FSLAB4", FSLAB4);
        cv.put("FSLAB5", FSLAB5);
        cv.put("ESLAB1", ESLAB1);
        cv.put("ESLAB2", ESLAB2);
        cv.put("ESLAB3", ESLAB3);
        cv.put("ESLAB4", ESLAB4);
        cv.put("ESLAB5", ESLAB5);
        cv.put("ESLAB6", ESLAB6);
        cv.put("CHARITABLE_RBT_AMT", CHARITABLE_RBT_AMT);
        cv.put("SOLAR_RBT_AMT", SOLAR_RBT_AMT);
        cv.put("FL_RBT_AMT", FL_RBT_AMT);
        cv.put("HANDICAP_RBT_AMT", HANDICAP_RBT_AMT);
        cv.put("PL_RBT_AMT", PL_RBT_AMT);
        cv.put("IPSET_RBT_AMT", IPSET_RBT_AMT);
        cv.put("REBATEFROMCCB_AMT", REBATEFROMCCB_AMT);
        cv.put("TOD_CHARGES", TOD_CHARGES);
        cv.put("PF_PENALITY_AMT", PF_PENALITY_AMT);
        cv.put("EXLOAD_MDPENALITY", EXLOAD_MDPENALITY);
        cv.put("CURR_BILL_AMOUNT", CURR_BILL_AMOUNT);
        cv.put("ROUNDING_AMOUNT", ROUNDING_AMOUNT);
        cv.put("DUE_DATE", DUE_DATE);
        cv.put("DISCONN_DATE", DISCONN_DATE);
        cv.put("CREADJ", CREADJ);
        cv.put("PREADKVAH", PREADKVAH);
        upload.insertInuploadMastOut(cv);
        trmDatabase1.insert_billed_backup1(cv);
        trmDatabase2.insert_billed_backup2(cv);

        Cursor subdiv_data = dbh.subdivdetails();
        subdiv_data.moveToNext();
        String SUBDIVCODE = subdiv_data.getString(subdiv_data.getColumnIndex("SUBDIV_CODE"));

        ContentValues collection_values = new ContentValues();
        collection_values.put("RRNO", RRNO);
        collection_values.put("CONSUMER_ID", CONSNO);
        collection_values.put("LF_NO", LEGFOL);
        collection_values.put("NAME", NAME);
        collection_values.put("TARIFF_NAME", TARIFF_NAME);
        collection_values.put("CHQ_DISSHONOUR_FLAG", " ");
        collection_values.put("CHQ_DISSHONOUR_DATE", " ");
        collection_values.put("SUB_DIVISION", SUBDIVCODE);
        collection_values.put("MRCODE", MRCODE);
        collection_values.put("PAYABLE_AMOUNT", PAYABLE);
        dbh.insertInCollectionInput(collection_values);

        String path = fcall.filepath("Textfile");
        String filename = "TextReport.txt";
        File log = new File(path + File.separator + filename);
        try {
            if (!log.exists()) {
                log.createNewFile();
            }
            PrintWriter out = new PrintWriter(new FileWriter(log, true));
            out.append("insert into MAST_OUT(MONTH,READDATE,RRNO,NAME,ADD1,TARIFF,MF,PREVSTAT,AVGCON,LINEMIN,"
                    + "SANCHP,SANCKW,PRVRED,FR,IR,DLCOUNT,ARREARS,PF_FLAG,BILLFOR,MRCODE,LEGFOL,ODDEVEN,SSNO,CONSNO,"
                    + "REBATE_FLAG,RREBATE,EXTRA1,DATA1,EXTRA2,DATA2,DEPOSIT,MTRDIGIT,ASDAMT,IODAMT,PFVAL,BMDVAL,"
                    + "INTEREST_AMT,CAP_FLAG,TOD_FLAG,TOD_PREVIOUS1,TOD_PREVIOUS3,INT_ON_DEP,"
                    + "SO_FEEDER_TC_POLE,TARIFF_NAME,PREV_READ_DATE,BILL_DAYS,MTR_SERIAL_NO,"
                    + "FDRNAME,TCCODE,MTR_FLAG,PRES_RDG,PRES_STS,UNITS,FIX,ENGCHG,REBATE_AMOUNT,TAX_AMOUNT,"
                    + "BMD_PENALTY,PF_PENALTY,PAYABLE,BILLDATE,BILLTIME,TOD_CURRENT1,TOD_CURRENT3,GOK_SUBSIDY,"
                    + "DEM_REVENUE,GPS_LAT,GPS_LONG,PAYABLE_REAL,PAYABLE_PROFIT,PAYABLE_LOSS,BILL_PRINTED,FSLAB1,FSLAB2,FSLAB3,FSLAB4,FSLAB5,"
                    + "ESLAB1,ESLAB2,ESLAB3,ESLAB4,ESLAB5,ESLAB6,CHARITABLE_RBT_AMT,SOLAR_RBT_AMT,FL_RBT_AMT,HANDICAP_RBT_AMT,PL_RBT_AMT,"
                    + "IPSET_RBT_AMT,REBATEFROMCCB_AMT,TOD_CHARGES,PF_PENALITY_AMT,EXLOAD_MDPENALITY,CURR_BILL_AMOUNT,ROUNDING_AMOUNT,DUE_DATE,DISCONN_DATE,"
                    + "CREADJ, PREADKVAH)"
                    + "values("
                    + "'" + MONTH + "'" + ","
                    + "'" + READDATE + "'" + ","
                    + "'" + RRNO + "'" + ","
                    + "'" + NAME + "'" + ","
                    + "'" + ADD1 + "'" + ","
                    + "'" + TARIFF + "'" + ","
                    + "'" + MF + "'" + ","
                    + "'" + PREVSTAT + "'" + ","
                    + "'" + AVGCON + "'" + ","
                    + "'" + LINEMIN + "'" + ","
                    + "'" + SANCHP + "'" + ","
                    + "'" + SANCKW + "'" + ","
                    + "'" + PRVRED + "'" + ","
                    + "'" + FR + "'" + ","
                    + "'" + IR + "'" + ","
                    + "'" + DLCOUNT + "'" + ","
                    + "'" + ARREARS + "'" + ","
                    + "'" + PF_FLAG + "'" + ","
                    + "'" + BILLFOR + "'" + ","
                    + "'" + MRCODE + "'" + ","
                    + "'" + LEGFOL + "'" + ","
                    + "'" + ODDEVEN + "'" + ","
                    + "'" + SSNO + "'" + ","
                    + "'" + CONSNO + "'" + ","
                    + "'" + REBATE_FLAG + "'" + ","
                    + "'" + RREBATE + "'" + ","
                    + "'" + EXTRA1 + "'" + ","
                    + "'" + DATA1 + "'" + ","
                    + "'" + EXTRA2 + "'" + ","
                    + "'" + DATA2 + "'" + ","
                    + "'" + PH_NO + "'" + ","
                    + "'" + DEPOSIT + "'" + ","
                    + "'" + MTRDIGIT + "'" + ","
                    + "'" + ASDAMT + "'" + ","
                    + "'" + IODAMT + "'" + ","
                    + "'" + PFVAL + "'" + ","
                    + "'" + BMDVAL + "'" + ","
                    + "'" + BILL_NO + "'" + ","
                    + "'" + INTEREST_AMT + "'" + ","
                    + "'" + CAP_FLAG + "'" + ","
                    + "'" + TOD_FLAG + "'" + ","
                    + "'" + TOD_PREVIOUS1 + "'" + ","
                    + "'" + TOD_PREVIOUS3 + "'" + ","
                    + "'" + INT_ON_DEP + "'" + ","
                    + "'" + SO_FEEDER_TC_POLE + "'" + ","
                    + "'" + TARIFF_NAME + "'" + ","
                    + "'" + PREV_READ_DATE + "'" + ","
                    + "'" + BILL_DAYS + "'" + ","
                    + "'" + MTR_SERIAL_NO + "'" + ","
                    + "'" + FDRNAME + "'" + ","
                    + "'" + TCCODE + "'" + ","
                    + "'" + MTR_FLAG + "'" + ","
                    + "'" + PRES_RDG + "'" + ","
                    + "'" + PRES_STS + "'" + ","
                    + "'" + UNITS + "'" + ","
                    + "'" + FIX + "'" + ","
                    + "'" + ENGCHG + "'" + ","
                    + "'" + REBATE_AMOUNT + "'" + ","
                    + "'" + TAX_AMOUNT + "'" + ","
                    + "'" + BMD_PENALTY + "'" + ","
                    + "'" + PF_PENALTY + "'" + ","
                    + "'" + PAYABLE + "'" + ","
                    + "'" + BILLDATE + "'" + ","
                    + "'" + BILLTIME + "'" + ","
                    + "'" + TOD_CURRENT1 + "'" + ","
                    + "'" + TOD_CURRENT3 + "'" + ","
                    + "'" + GOK_SUBSIDY + "'" + ","
                    + "'" + DEM_REVENUE + "'" + ","
                    + "'" + GPS_LAT + "'" + ","
                    + "'" + GPS_LONG + "'" + ","
                    + "'" + IMGADD + "'" + ","
                    + "'" + PAYABLE_REAL + "'" + ","
                    + "'" + PAYABLE_PROFIT + "'" + ","
                    + "'" + PAYABLE_LOSS + "'" + ","
                    + "'" + BILL_PRINTED + "'" + ","
                    + "'" + FSLAB1 + "'" + ","
                    + "'" + FSLAB2 + "'" + ","
                    + "'" + FSLAB3 + "'" + ","
                    + "'" + FSLAB4 + "'" + ","
                    + "'" + FSLAB5 + "'" + ","
                    + "'" + ESLAB1 + "'" + ","
                    + "'" + ESLAB2 + "'" + ","
                    + "'" + ESLAB3 + "'" + ","
                    + "'" + ESLAB4 + "'" + ","
                    + "'" + ESLAB5 + "'" + ","
                    + "'" + ESLAB6 + "'" + ","
                    + "'" + CHARITABLE_RBT_AMT + "'" + ","
                    + "'" + SOLAR_RBT_AMT + "'" + ","
                    + "'" + FL_RBT_AMT + "'" + ","
                    + "'" + HANDICAP_RBT_AMT + "'" + ","
                    + "'" + PL_RBT_AMT + "'" + ","
                    + "'" + IPSET_RBT_AMT + "'" + ","
                    + "'" + REBATEFROMCCB_AMT + "'" + ","
                    + "'" + TOD_CHARGES + "'" + ","
                    + "'" + PF_PENALITY_AMT + "'" + ","
                    + "'" + EXLOAD_MDPENALITY + "'" + ","
                    + "'" + CURR_BILL_AMOUNT + "'" + ","
                    + "'" + ROUNDING_AMOUNT + "'" + ","
                    + "'" + DUE_DATE + "'" + ","
                    + "'" + DISCONN_DATE + "'" + ","
                    + "'" + CREADJ + "'" + ","
                    + "'" + PREADKVAH + "'" + ")"
            );
            out.append("\r\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		dbh.closeDataBase();
		upload.close();
    }
	
}
