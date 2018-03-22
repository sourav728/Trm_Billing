package com.example.tvd.trm_billing;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.analogics.thermalAPI.Bluetooth_Printer_3inch_prof_ThermalAPI;
import com.analogics.thermalprinter.AnalogicsThermalPrinter;
import com.analogics.utils.AnalogicsUtil;
import com.example.tvd.trm_billing.activities.ConsumerBilling;
import com.example.tvd.trm_billing.database.Databasehelper;
import com.example.tvd.trm_billing.hardware.printer;
import com.example.tvd.trm_billing.invoke.Datainvoke;
import com.example.tvd.trm_billing.invoke.SendingData;
import com.example.tvd.trm_billing.services.BluetoothService;
import com.example.tvd.trm_billing.values.FunctionCalls;
import com.example.tvd.trm_billing.values.GetSetValues;
import com.lvrenyang.io.Pos;
import com.ngx.BluetoothPrinter;
import com.ngx.Enums.NGXBarcodeCommands;
import com.ngx.PrinterWidth;


import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;



import static android.text.Layout.Alignment.ALIGN_CENTER;
import static android.text.Layout.Alignment.ALIGN_NORMAL;
import static com.example.tvd.trm_billing.values.ConstantValues.BILLING_DOWNLOADED;
import static com.example.tvd.trm_billing.values.ConstantValues.DOWNLOAD_FILE_NAME;
import static com.example.tvd.trm_billing.values.ConstantValues.PREFS_NAME;


public class Reportdisp extends Activity {
	public final static String STORETXT = "report.txt";
	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://192.168.100.28:8085/Photo_Billing.asmx";
	private final String SOAP_ACTION = "http://tempuri.org/";

    private static final int PRINT_DLG = 0;
    private static final int EXIT_DLG = 1;
    private static final int PAIR_DLG = 2;
    private static final int UPDATE_DLG = 3;

	TextView tvsubd, tvrrno, tvlegf, tvtariff, tvname, tvaddr, tvconsno, tvbillno, tvmtr, tvlinem,
			tvload, tvmf, tvkw, tvdate1, tvdate2, tvcur, tvprv, tvfr, tvir, tvmnth1, tvmnth2, tvstat1, tvstat2,
			tvconm, tvavgcon, tvfc, tvec, tvgkpay, tvrebate, tvbillamnt, tvarr, tvintr, tvintrtv, tvtaxe,
			tvtax, tvcredit, tvmrcode, tvtel, tvintd, tvasd, tviod, tvpay, tvdue, tvcity, tvprint, tvprinttm,
			tvhelp, tvmachine, tvver, tvdivcode, tvfslab1, tvfslab2, tvfslab3, tvfrate1, tvfrate2, tvfrate3, tveslab1,
			tveslab2, tveslab3, tveslab4, tverate1, tverate2, tverate3, tverate4, tvtfs1, tvtfs2, tvtfs3, tvtes1, tvtes2,
			tvtes3, tvtes4, tvst1, tvst2, tvst3, tvst4, tvst5, tvst6, tvst7, tveq1, tveq2, tveq3, tveq4, tveq5, tveq6,
			tveq7, tvsearch, tvpf, tvpfpen, tvbmd, tvbmdpen, tvpfvaltext, tvpfpentext, tvbmdtext, tvbmdpentext,
			tvTodonPeak, tvTodoffpeak, tvonpeaktext, tvoffpeaktext, tvonpeaksemi, tvoffpeaksemi, tvrep_others1,
			tvrep_textothers1, tvrep_others1semi, tvrep_others2, tvrep_textothers2, tvrep_others2semi, tvrep_textpd,
			tvrep_pd, tvrep_textpdpen, tvrep_pdpen, tvrep_duplicate;

	String rep_rrno, rep_consname, rep_address, tarifn, mfs, linm, readdate, prevdate, frs, irs, legfol, rep_consid, billnum,
			mtrs, prevrdg, rep_subdiv, rep_presentrdg, prstat1, prstat2, conms, credits, mrcodes, telph, gkpays, rebates,
			billamnts, arrs, intrs, taxs, intds, rep_readdate, rep_prevdate, rep_subdiv_fec = "",
			asds, iods, rep_payable, fix, ecs, custid, custrrno, custname, billamnts3, rep_display, rep_report,
			duedate, place, printdt, printtime, mnth, helpline, machineid, verid, divcodes, avgcons, rep_bluprinter = "",
			eslab1, eslab2, eslab3, eslab4, erate1, erate2, erate3, erate4, totales1, totales2, totales3, totales4,
			fslab1, fslab2, fslab3, frate1, frate2, frate3, totalfs1, totalfs2, totalfs3, load, kw, rRebate, totalfc,
			totalec, rep_custid, rep_custrrno, rep_custname, rep_slabs, rep_printer, rep_preprint, rep_intrate,
			rep_taxrate, billing_status, billprinted, rep_tariff, rep_pfvalue, rep_pfpenalty, rep_bmdpenalty,
			rep_bmdvalue, rep_dlcount1, rep_rebateflag, rep_todonpeak, rep_todoffpeak, rep_todflag, rep_prvtodonpeak,
			rep_prvtodoffpeak, rep_printerformat, subdivname, rep_pfflag, rep_extra1, rep_extra2, rep_extradata,
			rep_data1, rep_data2, rep_data01, rep_data02, rep_billdays, rep_status, rep_statusbilling, rep_barcodevalue = "",
			rep_updateonline, rep_ssno, rep_phno, rep_interestamt, rep_tccode, rep_pres_sts, rep_gpslat, rep_gpslong,
			rep_image = "", rep_month, rep_payreal, rep_payprofit, rep_payloss, rep_billeddata, rep_deviceconnected,
			rep_address_1 = "", rep_address_2 = "", rep_creadj = "", rep_cur_bill ="", rep_mrname="", rep_dl_fslab1="",
            rep_dl_fslab2="", rep_dl_fslab3="", rep_dl_days_count="", rep_aadhaar_no="0", rep_mail_id="0", rep_election_no="0",
            rep_ration_no="0", rep_water_conn_no="0", rep_house_no="0";

	String str = "=", str2 = " ", d1, g1 = ":", customname, customverid, customaddr1, customaddr2, add1, extdate, dateext, splchar = "";

	boolean es1, es2, es3, es4, er1, er2, er3, er4, fs1, fs2, fs3, fr1, fr2, fr3, te1, te2, te3, te4, tf1, tf2, tf3;
	boolean eval1 = false, eval2 = false, eval3 = false, eval4 = false, fval1 = false, fval2 = false, fval3 = false,
			Internet = false, ngxconnected = false, analogics = false, goojprt = false, bill_removed=false;

	double gkpays1, rebates1, billamnts1, arrs1, intrs1, taxs1, intds1, asds1, iods1, pays1, fix1, ecs1, pays2, credits1;

	double lgcons, rep_pfvalue1, rep_pfpenalty1, rep_bmdpenalty1, rep_bmdvalue1, rep_todvalue, data1,
			data2, rep_dlcount;

	int repUnits, lgmf, todonpeak, todoffpeak;

	AutoCompleteTextView actvsearch;
	ArrayList<String> rrnoarray, idarray, namearray, addresslist;
	ArrayAdapter<String> rrnoadap, idadap, nameadap;

	Databasehelper dbh;
	Cursor c, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, extra_billing;
	ListView lv;
	ArrayList<String> Details;
	ClassCalculation cc;
	//Payment pay;
	FunctionCalls fcall;
	static Context con;
	GetSetValues getset;
	Datainvoke invoke;
	ProgressDialog printing;
    SharedPreferences settings;
    Bluetooth_Printer_3inch_prof_ThermalAPI api;

	static double totalEC1, totalFC1;
	double[] ArrFrate = new double[10];
	double[] ArrFslab = new double[10];
	double[] ArrdlFslab = new double[10];
	double[] ArrErate = new double[10];
	double[] ArrEslab = new double[10];
	double[] ArrEc = new double[10];
	double[] ArrFc = new double[10];

	BluetoothPrinter mBtp;
	AnalogicsThermalPrinter conn = BluetoothService.conn;

//	com.lvrenyang.io.Canvas mCanvas = BluetoothService.mCanvas;
    Pos mPos = BluetoothService.mPos;
	ExecutorService es = BluetoothService.es;
	Bitmap barcode;
	float yaxis = 0;
	printer m_printer;

	private String mConnectedDeviceName = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report);

		//Initializing Textviews
		inittext();
		rep_rrno = "";
		billing_status = "Billing Deleted";
		initcalculationtext();
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		getset = new GetSetValues();
		cc = new ClassCalculation(con);
        api = new Bluetooth_Printer_3inch_prof_ThermalAPI();

		//Get the values from previous activity
		dbh = new Databasehelper(this);
		dbh.openDatabase();

		if (Build.MANUFACTURER.matches("alps")) {
			m_printer = new printer();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					m_printer.Open();
					byte btGrayLevel = (byte) 0;
					m_printer.SetGrayLevel(btGrayLevel);
				}
			}, 2500);
		}

		Cursor print = dbh.subdivdetails();
		if (print.getCount() > 0) {
			print.moveToNext();
			rep_bluprinter = print.getString(print.getColumnIndex("BT_PRINTER"));
			if (rep_bluprinter.equals("NGX")) {
				mBtp = BluetoothService.getngxprinter();
			}
		}

		Intent disp = getIntent();
		if (disp.getExtras() != null) {
			Bundle bnd = disp.getExtras();
			if (bnd.getString("RRNO") != null) {
				if (bnd.getString("billing") != null) {
					rep_display = bnd.getString("billing");
					custrrno = bnd.getString("RRNO");
					if (bnd.getString("STATUS") != null) {
						rep_statusbilling = "Yes";
						rep_status = bnd.getString("STATUS");
						rep_report = rep_status;
						fulldetailsbyrrno();
					} else {
						rep_statusbilling = "No";
						rep_report = "Billing";
						fulldetailsbyrrno();
					}
				} else {
					custrrno = bnd.getString("RRNO");
					rep_display = "";
					rep_report = "Justview";
					fulldetailsbyrrno();
				}
			}
		} else {
			rep_statusbilling = "No";
			rep_report = "Billsearch";
			actionbaricon();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.buttonmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.item1:
				if (!rep_consid.equals("")) {
                    if (rep_display.equals("billing")) {
                        if (rep_phno.equals("N") || rep_aadhaar_no.equals("0") || rep_mail_id.equals("0")) {
                            showdialog(UPDATE_DLG);
                        } else {
                            updating_billing_record();
                            showdialog(PRINT_DLG);
                            cc.resetAllValues();
                        }
                    } else {
                        showdialog(PRINT_DLG);
                        cc.resetAllValues();
                    }
				} else {
					Toast.makeText(Reportdisp.this, "Take Readings for Printing Report", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.item2:
				cc.resetAllValues();
				onexit();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void updating_billing_record() {
        MastoutReading mastout = new MastoutReading();
        mastout.insertintomastoutandtextfile(Reportdisp.this, rep_consid, Reportdisp.this, settings);
        String filename = settings.getString(DOWNLOAD_FILE_NAME, "");
        if (!TextUtils.isEmpty(filename)) {
            if (fcall.isInternetOn(Reportdisp.this)) {
                Cursor billed = dbh.countbilledrecords();
                billed.moveToNext();
                String billed_count = billed.getString(billed.getColumnIndex("CUST"));
                if (StringUtils.startsWithIgnoreCase(settings.getString(BILLING_DOWNLOADED, ""), "Yes")) {
                    SendingData sendingData = new SendingData();
                    SendingData.Update_Billed_record_details recordDetails = sendingData.new Update_Billed_record_details();
                    recordDetails.execute(mrcodes, billed_count, fcall.updatedate(settings.getString(DOWNLOAD_FILE_NAME, "").substring(2, 4)));
                }
            }
        }
    }

	//Getting array values for calculation from ClassCalculation Activity
	public void getcalculationarrayvalues() {
		if (rep_tariff.equals("10")) {
			if (repUnits > 40) {
				c3 = dbh.getTarrifDataBJ("1", "20");
			} else {
				c3 = dbh.getTarrifDataBJ("1", "10");
			}
		} else {
			if (rep_tariff.equals("23")) {
				if (rRebate.equals("0")) {
					c3 = dbh.getTarrifDataBJ("0", "20");
				} else {
					if (rRebate.equals("1")) {
						c3 = dbh.getTarrifDataBJ("1", "20");
					}
				}
			} else {
				if (rep_tariff.equals("60") || rep_tariff.equals("61") || rep_tariff.equals("70")) {
					c3 = dbh.getTarrifDataBJ("0", rep_tariff);
				} else {
					if (rep_tariff.equals("70")) {
						c3 = dbh.getTarrifDataBJ("0", rep_tariff);
					} else {
						if (rep_tariff.equals("40")) {
							c3 = dbh.getTarrifDataBJ("0", rep_tariff);
						} else c3 = dbh.getTarrifData(custrrno, rRebate);
					}
				}
			}
		}
		if (rep_rebateflag.equals("4")) {
			if (rep_tariff.equals("30")) {
				if (rRebate.equals("0")) {
					c3 = dbh.getTarrifDataBJ("3", rep_tariff);
				}
				if (rRebate.equals("1")) {
					c3 = dbh.getTarrifDataBJ("4", rep_tariff);
				}
			}
			if (rep_tariff.equals("50")) {
				if (rRebate.equals("0")) {
					c3 = dbh.getTarrifDataBJ("3", rep_tariff);
				}
				if (rRebate.equals("1")) {
					c3 = dbh.getTarrifDataBJ("4", rep_tariff);
				}
			}
			if (rep_tariff.equals("51")) {
				if (rRebate.equals("0")) {
					c3 = dbh.getTarrifDataBJ("3", rep_tariff);
				}
				if (rRebate.equals("1")) {
					c3 = dbh.getTarrifDataBJ("4", rep_tariff);
				}
			}
			if (rep_tariff.equals("52")) {
				if (rRebate.equals("0")) {
					c3 = dbh.getTarrifDataBJ("3", rep_tariff);
				}
				if (rRebate.equals("1")) {
					c3 = dbh.getTarrifDataBJ("4", rep_tariff);
				}
			}
			if (rep_tariff.equals("53")) {
				if (rRebate.equals("0")) {
					c3 = dbh.getTarrifDataBJ("3", rep_tariff);
				}
				if (rRebate.equals("1")) {
					c3 = dbh.getTarrifDataBJ("4", rep_tariff);
				}
			}
		}
		if (!rep_rebateflag.equals("4")) {
			if (rep_tariff.equals("50") || rep_tariff.equals("51") || rep_tariff.equals("52") || rep_tariff.equals("53")) {
				c3 = dbh.getTarrifDataBJ("0", rep_tariff);
			}
		}
		cc.FcCalculation(c3, rep_rebateflag);
		cc.EcCalculation(c3);
		ArrErate = cc.erateForTextViews();
		ArrEslab = cc.eslabForTextViews();
		ArrEc = cc.ecForTextViews();
		ArrFrate = cc.frateForTextViews();
		ArrFslab = cc.fslabForTextViews();
        ArrdlFslab = cc.dl_fslabForTextViews();
		ArrFc = cc.fcForTextViews();
		rep_todvalue = cc.todCalculation(c3);
		if (rep_rebateflag.equals("5")) {
			double totalec = cc.totalEC();
			totalEC1 = totalec - gkpays1;
		} else {
			totalEC1 = cc.totalEC();
		}
		totalFC1 = cc.totalFC();
		rep_taxrate = cc.taxrate(c3);
		rep_intrate = cc.intrrate(c3);
	}

	//Initializing values
	public void inittext() {
		tvsubd = (TextView) findViewById(R.id.textView1);
		tvrrno = (TextView) findViewById(R.id.textView22);
		tvlegf = (TextView) findViewById(R.id.textView23);
		tvtariff = (TextView) findViewById(R.id.textView24);
		tvname = (TextView) findViewById(R.id.textView25);
		tvaddr = (TextView) findViewById(R.id.textView26);
		tvconsno = (TextView) findViewById(R.id.textView27);
		tvbillno = (TextView) findViewById(R.id.textView28);
		tvmtr = (TextView) findViewById(R.id.textView29);
		tvlinem = (TextView) findViewById(R.id.textView30);
		tvload = (TextView) findViewById(R.id.textView31);
		tvmf = (TextView) findViewById(R.id.textView36);
		tvkw = (TextView) findViewById(R.id.textView37);
		tvdate1 = (TextView) findViewById(R.id.textView40);
		tvdate2 = (TextView) findViewById(R.id.textView42);
		tvcur = (TextView) findViewById(R.id.textView52);

		tvprv = (TextView) findViewById(R.id.textView53);
		tvfr = (TextView) findViewById(R.id.textView54);
		tvir = (TextView) findViewById(R.id.textView61);
		tvmnth1 = (TextView) findViewById(R.id.textView55);
		tvmnth2 = (TextView) findViewById(R.id.textView56);
		tvstat1 = (TextView) findViewById(R.id.textView57);
		tvstat2 = (TextView) findViewById(R.id.textView58);

		tvconm = (TextView) findViewById(R.id.textView64);
		tvavgcon = (TextView) findViewById(R.id.textView67);
		tvgkpay = (TextView) findViewById(R.id.textView104);
		tvrebate = (TextView) findViewById(R.id.textView110);
		tvbillamnt = (TextView) findViewById(R.id.textView113);

		tvarr = (TextView) findViewById(R.id.textView116);
		tvintr = (TextView) findViewById(R.id.textView119);
		tvintrtv = (TextView) findViewById(R.id.textView120);
		tvtaxe = (TextView) findViewById(R.id.textView123);
		tvtax = (TextView) findViewById(R.id.textView124);
		tvcredit = (TextView) findViewById(R.id.textView127);
		tvmrcode = (TextView) findViewById(R.id.textView159);

		tvtel = (TextView) findViewById(R.id.textView160);
		tvintd = (TextView) findViewById(R.id.textView131);
		tvasd = (TextView) findViewById(R.id.textView134);
		tviod = (TextView) findViewById(R.id.textView137);
		tvpay = (TextView) findViewById(R.id.textView140);

		tvdue = (TextView) findViewById(R.id.textView143);
		tvcity = (TextView) findViewById(R.id.textView146);
		tvprint = (TextView) findViewById(R.id.textView149);
		tvhelp = (TextView) findViewById(R.id.textView153);
		tvmachine = (TextView) findViewById(R.id.textView156);
		tvver = (TextView) findViewById(R.id.textView157);
		tvfc = (TextView) findViewById(R.id.textView75);
		tvec = (TextView) findViewById(R.id.textView101);

		tvdivcode = (TextView) findViewById(R.id.textView98);
		tvsearch = (TextView) findViewById(R.id.textView175);
		tvpfvaltext = (TextView) findViewById(R.id.textView176);
		tvpf = (TextView) findViewById(R.id.textView177);
		tvpfpentext = (TextView) findViewById(R.id.textView178);

		tvpfpen = (TextView) findViewById(R.id.textView179);
		tvbmdtext = (TextView) findViewById(R.id.textView180);
		tvbmd = (TextView) findViewById(R.id.textView181);
		tvbmdpentext = (TextView) findViewById(R.id.textView182);
		tvbmdpen = (TextView) findViewById(R.id.textView183);
		tvonpeaktext = (TextView) findViewById(R.id.textView184);
		tvonpeaksemi = (TextView) findViewById(R.id.textView185);

		tvTodonPeak = (TextView) findViewById(R.id.textView186);
		tvoffpeaktext = (TextView) findViewById(R.id.textView188);
		tvoffpeaksemi = (TextView) findViewById(R.id.textView189);
		tvTodoffpeak = (TextView) findViewById(R.id.textView187);
		tvrep_textothers1 = (TextView) findViewById(R.id.textView190);
		tvrep_others1semi = (TextView) findViewById(R.id.textView191);

		tvrep_others1 = (TextView) findViewById(R.id.textView192);
		tvrep_textothers2 = (TextView) findViewById(R.id.textView193);
		tvrep_others2semi = (TextView) findViewById(R.id.textView194);
		tvrep_others2 = (TextView) findViewById(R.id.textView195);
		tvrep_textpd = (TextView) findViewById(R.id.textView196);
		tvrep_pd = (TextView) findViewById(R.id.textView197);
		tvrep_textpdpen = (TextView) findViewById(R.id.textView198);
		tvrep_pdpen = (TextView) findViewById(R.id.textView199);
		tvrep_duplicate = (TextView) findViewById(R.id.textView200);
		actvsearch = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

		idarray = new ArrayList<>();
		rrnoarray = new ArrayList<>();
		namearray = new ArrayList<>();
		addresslist = new ArrayList<>();

		fcall = new FunctionCalls();
		invoke = new Datainvoke();
	}

	//Initializing calculation textviews		
	public void initcalculationtext() {
		//Initializing eslabs
		tveslab1 = (TextView) findViewById(R.id.textView76);
		tveslab2 = (TextView) findViewById(R.id.textView81);
		tveslab3 = (TextView) findViewById(R.id.textView86);
		tveslab4 = (TextView) findViewById(R.id.textView91);

		//Initializing erates
		tverate1 = (TextView) findViewById(R.id.textView78);
		tverate2 = (TextView) findViewById(R.id.textView83);
		tverate3 = (TextView) findViewById(R.id.textView88);
		tverate4 = (TextView) findViewById(R.id.textView93);

		//Initializing estotals
		tvtes1 = (TextView) findViewById(R.id.textView80);
		tvtes2 = (TextView) findViewById(R.id.textView85);
		tvtes3 = (TextView) findViewById(R.id.textView90);
		tvtes4 = (TextView) findViewById(R.id.textView95);

		//Initializing fslabs
		tvfslab1 = (TextView) findViewById(R.id.textView68);
		tvfslab2 = (TextView) findViewById(R.id.textView162);
		tvfslab3 = (TextView) findViewById(R.id.textView167);

		//Initializing frates
		tvfrate1 = (TextView) findViewById(R.id.textView71);
		tvfrate2 = (TextView) findViewById(R.id.textView164);
		tvfrate3 = (TextView) findViewById(R.id.textView169);

		//Initializing fstotals
		tvtfs1 = (TextView) findViewById(R.id.textView73);
		tvtfs2 = (TextView) findViewById(R.id.textView166);
		tvtfs3 = (TextView) findViewById(R.id.textView171);

		//Initializing star & equals signs
		tvst1 = (TextView) findViewById(R.id.textView70);
		tvst2 = (TextView) findViewById(R.id.textView163);
		tvst3 = (TextView) findViewById(R.id.textView168);
		tvst4 = (TextView) findViewById(R.id.textView77);
		tvst5 = (TextView) findViewById(R.id.textView82);
		tvst6 = (TextView) findViewById(R.id.textView87);
		tvst7 = (TextView) findViewById(R.id.textView92);

		tveq1 = (TextView) findViewById(R.id.textView72);
		tveq2 = (TextView) findViewById(R.id.textView165);
		tveq3 = (TextView) findViewById(R.id.textView170);
		tveq4 = (TextView) findViewById(R.id.textView79);
		tveq5 = (TextView) findViewById(R.id.textView84);
		tveq6 = (TextView) findViewById(R.id.textView89);
		tveq7 = (TextView) findViewById(R.id.textView94);
	}

	//Getting the values from cursor
	public void getvalue() throws ParseException {
		rep_rrno = c.getString(c.getColumnIndex("RRNO"));
		legfol = c.getString(c.getColumnIndex("LEGFOL"));
		tarifn = c.getString(c.getColumnIndex("TARIFF_NAME"));
		rep_tariff = c.getString(c.getColumnIndex("TARIFF"));
		rep_consname = c.getString(c.getColumnIndex("NAME"));
		rep_address = c.getString(c.getColumnIndex("ADD1"));
		rep_consid = c.getString(c.getColumnIndex("CONSNO"));
//		billnum = c.getString(c.getColumnIndex("BILL_NO"));
		mtrs = c.getString(c.getColumnIndex("MTR_SERIAL_NO"));

		linm = c.getString(c.getColumnIndex("LINEMIN"));
		lgmf = Integer.parseInt(c.getString(c.getColumnIndex("MF")));
		mfs = "" + lgmf;
		rep_readdate = c.getString(c.getColumnIndex("READDATE"));
		rep_prevdate = c.getString(c.getColumnIndex("PREV_READ_DATE"));
		prevrdg = c.getString(c.getColumnIndex("PRVRED"));
		frs = c.getString(c.getColumnIndex("FR"));
		irs = c.getString(c.getColumnIndex("IR"));
		rep_presentrdg = c.getString(c.getColumnIndex("PRES_RDG"));
		conms = c.getString(c.getColumnIndex("UNITS"));

		repUnits = Integer.parseInt(conms);
		avgcons = c.getString(c.getColumnIndex("AVGCON"));
		gkpays1 = Double.parseDouble(c.getString(c.getColumnIndex("GOK_SUBSIDY")));
		rebates1 = Double.parseDouble(c.getString(c.getColumnIndex("REBATE_AMOUNT")));
		billamnts3 = c.getString(c.getColumnIndex("DEM_REVENUE"));
		billamnts1 = Double.parseDouble(billamnts3);
		arrs1 = Double.parseDouble(c.getString(c.getColumnIndex("ARREARS")));

		Double credj = Double.parseDouble(c.getString(c.getColumnIndex("CREADJ")));
		arrs1 = arrs1 + credj;
		intrs1 = Double.parseDouble(c.getString(c.getColumnIndex("INTEREST_AMT")));
		taxs1 = Double.parseDouble(c.getString(c.getColumnIndex("TAX_AMOUNT")));
		credits1 = Double.parseDouble(c.getString(c.getColumnIndex("DEPOSIT")));
		mrcodes = c.getString(c.getColumnIndex("MRCODE"));
		telph = c.getString(c.getColumnIndex("MOBILE_NO"));

		intds1 = Double.parseDouble(c.getString(c.getColumnIndex("INT_ON_DEP")));
		asds1 = Double.parseDouble(c.getString(c.getColumnIndex("ASDAMT")));
		iods1 = Double.parseDouble(c.getString(c.getColumnIndex("IODAMT")));
		pays1 = Double.parseDouble(c.getString(c.getColumnIndex("PAYABLE")));
		pays1 = pays1 + credj;
        /*if (rep_tariff.equals("31"))
            pays1 = billamnts1 + arrs1 - credits1 - gkpays1;*/
		place = c.getString(c.getColumnIndex("ADD1"));
		printdt = c.getString(c.getColumnIndex("BILLDATE"));
		printtime = c.getString(c.getColumnIndex("BILLTIME"));
		helpline = c.getString(c.getColumnIndex("HELPLINE_NO"));
		verid = c.getString(c.getColumnIndex("DB_VERSION"));
		rep_subdiv = c.getString(c.getColumnIndex("SUB_DIVISION"));
		rep_mrname = c.getString(c.getColumnIndex("MRNAME"));
		divcodes = c.getString(c.getColumnIndex("SUBDIV_CODE"));
		fix1 = Double.parseDouble(c.getString(c.getColumnIndex("FIX")));
		ecs1 = Double.parseDouble(c.getString(c.getColumnIndex("ENGCHG")));
		load = c.getString(c.getColumnIndex("SANCHP"));
		kw = c.getString(c.getColumnIndex("SANCKW"));
		rRebate = c.getString(c.getColumnIndex("RREBATE"));
		rep_printer = c.getString(c.getColumnIndex("PRINTER_TYPE"));
		rep_slabs = c.getString(c.getColumnIndex("SLABS_PRINT"));
		rep_preprint = c.getString(c.getColumnIndex("PRE_PRINT"));
		rep_printerformat = c.getString(c.getColumnIndex("PRINTER_FORMAT"));
		rep_pfvalue1 = Double.parseDouble(c.getString(c.getColumnIndex("PFVAL")));
		rep_pfpenalty1 = Double.parseDouble(c.getString(c.getColumnIndex("PF_PENALTY")));
		rep_bmdpenalty1 = Double.parseDouble(c.getString(c.getColumnIndex("BMD_PENALTY")));
		rep_bmdvalue = c.getString(c.getColumnIndex("BMDVAL"));
		rep_dlcount1 = c.getString(c.getColumnIndex("DLCOUNT"));
		rep_dlcount = Double.parseDouble(rep_dlcount1);
		rep_rebateflag = c.getString(c.getColumnIndex("REBATE_FLAG"));
		rep_todonpeak = c.getString(c.getColumnIndex("TOD_CURRENT1"));
		rep_todoffpeak = c.getString(c.getColumnIndex("TOD_CURRENT3"));
		todonpeak = Integer.parseInt(rep_todonpeak);
		todoffpeak = Integer.parseInt(rep_todoffpeak);
		rep_todflag = c.getString(c.getColumnIndex("TOD_FLAG"));
		rep_prvtodonpeak = c.getString(c.getColumnIndex("TOD_PREVIOUS1"));
		rep_prvtodoffpeak = c.getString(c.getColumnIndex("TOD_PREVIOUS3"));
		rep_pfflag = c.getString(c.getColumnIndex("PF_FLAG"));
		rep_data01 = c.getString(c.getColumnIndex("DATA1"));
		data1 = Double.parseDouble(rep_data01);
		rep_data02 = c.getString(c.getColumnIndex("DATA2"));
		data2 = Double.parseDouble(rep_data02);
		rep_extra1 = c.getString(c.getColumnIndex("EXTRA1"));
		rep_extra2 = c.getString(c.getColumnIndex("EXTRA2"));
		rep_billdays = c.getString(c.getColumnIndex("BILL_DAYS"));
		rep_ssno = c.getString(c.getColumnIndex("SSNO"));
		rep_phno = c.getString(c.getColumnIndex("PH_NO"));
		rep_interestamt = c.getString(c.getColumnIndex("INTEREST_AMT"));
		rep_tccode = c.getString(c.getColumnIndex("TCCODE"));
		rep_pres_sts = c.getString(c.getColumnIndex("PRES_STS"));
		rep_gpslat = c.getString(c.getColumnIndex("GPS_LAT"));
		rep_gpslong = c.getString(c.getColumnIndex("GPS_LONG"));
		rep_image = c.getString(c.getColumnIndex("IMGADD"));
		rep_month = c.getString(c.getColumnIndex("MONTH"));
		rep_payreal = c.getString(c.getColumnIndex("PAYABLE_REAL"));
		rep_payprofit = c.getString(c.getColumnIndex("PAYABLE_PROFIT"));
		rep_payloss = c.getString(c.getColumnIndex("PAYABLE_LOSS"));
		rep_creadj = c.getString(c.getColumnIndex("CREADJ"));
        rep_aadhaar_no = c.getString(c.getColumnIndexOrThrow("AADHAAR"));
        rep_mail_id = c.getString(c.getColumnIndexOrThrow("MAIL"));

		//Converting dates to date format
		readdate = fcall.changedateformat(rep_readdate, "/");
		prevdate = fcall.changedateformat(rep_prevdate, "/");

		//Getting duedate value
        duedate = fcall.changedateformat((c.getString(c.getColumnIndexOrThrow("DUE_DATE"))), "/");

		//Getting date
		dateext = getDate(fcall.changedateformat(printdt, "-"));

		DecimalFormat num = new DecimalFormat("##.00");
		Cursor fec = dbh.subdivdetails();
		fec.moveToNext();
		try {
			rep_subdiv_fec = num.format(Double.parseDouble(fec.getString(fec.getColumnIndex("FEC"))));
			if (rep_subdiv_fec.substring(0, 1).equals("."))
				rep_subdiv_fec = "0" + num.format(Double.parseDouble(fec.getString(fec.getColumnIndex("FEC"))));
		} catch (NullPointerException e) {
			e.printStackTrace();
			rep_subdiv_fec = num.format(0);
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
			rep_subdiv_fec = num.format(0);
		}

        dl_calculate_days();
	}

	//Converting Calculation values for doubles to String
	public void converttostring() {
		DecimalFormat num = new DecimalFormat("##.00");

		double data = data1 + data2;
		String data3 = num.format(data);
		if (data3.equals(".00")) {
			rep_extradata = "0.00";
		} else {
			rep_extradata = data3;
		}
		if (!data3.equals(".00")) {
			rep_extradata = String.format("%.2f", +gkpays1);
		}

		//Converting GOK PAYABLE
		String gkpays2 = num.format(gkpays1);
		if (gkpays2.equals(".00")) {
			gkpays = "0.00";
		} else {
			gkpays = gkpays2;
		}
		if (!gkpays2.equals(".00")) {
			gkpays = String.format("%.2f", +gkpays1);
		}

		//Converting REBATE
		String rebates2 = num.format(rebates1);
		if (rebates2.equals(".00")) {
			rebates = "0.00";
		} else {
			rebates = rebates2;
		}
		if (!rebates2.equals(".00")) {
			rebates = String.format("%.2f", +rebates1);
		}

		//Converting BILLAMOUNT
		String billamnts2 = num.format(billamnts1);
		if (billamnts2.equals(".00")) {
			billamnts = "0.00";
		} else {
			billamnts = billamnts2;
		}
		if (!billamnts2.equals(".00")) {
			billamnts = String.format("%.2f", +billamnts1);
		}

		//Converting ARREARS
		String arrs2 = num.format(arrs1);
		if (arrs2.equals(".00")) {
			arrs = "0.00";
		} else {
			arrs = arrs2;
		}
		if (!arrs2.equals(".00")) {
			arrs = String.format("%.2f", +arrs1);
		}

		//Converting INTEREST AMOUNT
		String intrs2 = num.format(intrs1);
		if (intrs2.equals(".00")) {
			intrs = "0.00";
		} else {
			intrs = intrs2;
		}
		if (!intrs2.equals(".00")) {
			intrs = String.format("%.2f", +intrs1);
		}

		//Converting TAX ON EC
		/*String taxs2 = num.format(taxs1);
		if (taxs2.equals(".00")) {
			taxs = "0.00";
		} else {
			taxs = taxs2;
		}
		if (!taxs2.equals(".00")) {
//			taxs = String.format("%.2f", +taxs1);
			taxs = fcall.decimalroundoff(taxs1);
		}*/
        taxs = fcall.decimalroundoff(taxs1);

		//Converting INTEREST ON DEPOSIT
		String intds2 = num.format(intds1);
		if (intds2.equals(".00")) {
			intds = "0.00";
		} else {
			intds = intds2;
		}
		if (!intds2.equals(".00")) {
			intds = String.format("%.2f", +intds1);
		}

		//Converting ASD
		String asds2 = num.format(asds1);
		if (asds2.equals(".00")) {
			asds = "0.00";
		} else {
			asds = asds2;
		}
		if (!asds2.equals(".00")) {
			asds = String.format("%.2f", +asds1);
		}

		//Converting IOD/TOD
		String iods2 = num.format(iods1);
		if (iods2.equals(".00")) {
			iods = "0.00";
		} else {
			iods = iods2;
		}
		if (!iods2.equals(".00")) {
			iods = String.format("%.2f", +iods1);
		}

		//Converting PAYABLE AMOUNT
		String pays2 = num.format(pays1);
		if (pays2.equals(".00")) {
			rep_payable = "0.00";
		} else {
			rep_payable = pays2;
		}
		if (!pays2.equals(".00")) {
			pays2 = String.format("%.2f", +pays1);
		}

		String fix2 = num.format(fix1);
		if (fix2.equals(".00")) {
			fix = "0.00";
		} else {
			fix = fix2;
		}
		if (!fix2.equals(".00")) {
			fix2 = String.format("%.2f", +fix1);
		}

		String ecs2 = num.format(ecs1);
		if (ecs2.equals(".00")) {
			ecs = "0.00";
		} else {
			ecs = ecs2;
		}
		if (!ecs2.equals(".00")) {
			ecs2 = String.format("%.2f", +ecs1);
		}

		String credits2 = num.format(credits1);
		if (credits2.equals(".00")) {
			credits = "0.00";
		} else {
			credits = credits2;
		}
		if (!credits2.equals(".00")) {
			credits2 = String.format("%.2f", +credits1);
		}

		rep_pfvalue = String.format("%.2f", rep_pfvalue1);
		rep_pfpenalty = String.format("%.2f", rep_pfpenalty1);

		rep_bmdpenalty = String.format("%.2f", rep_bmdpenalty1);

		rep_data1 = fcall.decimalroundoff(data1);
		rep_data2 = fcall.decimalroundoff(data2);
	}

	private void dl_calculate_days() {
        DecimalFormat num = new DecimalFormat("##.00");
        double billdays = Double.parseDouble(rep_billdays);
        double dlrate = billdays / 30;
        rep_dl_days_count = num.format(dlrate);
    }

	//Getting all Calculation values
	public void getcalculationvalue() {
		DecimalFormat num = new DecimalFormat("##.00");

		//Getting eslab values
        if (rep_dlcount != 0) {
            eslab1 = num.format(ArrEslab[1]);
            eslab2 = num.format(ArrEslab[2]);
            eslab3 = num.format(ArrEslab[3]);
            eslab4 = num.format(ArrEslab[4]);
        } else {
            eslab1 = "" + ArrEslab[1];
            eslab2 = "" + ArrEslab[2];
            eslab3 = "" + ArrEslab[3];
            eslab4 = "" + ArrEslab[4];
        }

		//Getting erate values
		String ert1 = "" + num.format(ArrErate[1]);
		if (ert1.equals(".00")) {
			erate1 = "0.00";
			if (ert1.startsWith(".", 0)) {
				String ert2 = "0" + ert1;
				erate1 = ert2;
			}
		} else {
			erate1 = ert1;
		}
		String ert2 = "" + num.format(ArrErate[2]);
		if (ert2.equals(".00")) {
			erate2 = "0.00";
		} else {
			erate2 = ert2;
		}
		String ert3 = "" + num.format(ArrErate[3]);
		if (ert3.equals(".00")) {
			erate3 = "0.00";
		} else {
			erate3 = ert3;
		}
		String ert4 = "" + num.format(ArrErate[4]);
		if (ert4.equals(".00")) {
			erate4 = "0.00";
		} else {
			erate4 = ert4;
		}

		//Getting totales values
		String tes1 = "" + num.format(ArrEc[1]);
		if (tes1.equals(".00")) {
			totales1 = "0.00";
		} else {
			totales1 = tes1;
		}
		String tes2 = "" + num.format(ArrEc[2]);
		if (tes2.equals(".00")) {
			totales2 = "0.00";
		} else {
			totales2 = tes2;
		}
		String tes3 = "" + num.format(ArrEc[3]);
		if (tes3.equals(".00")) {
			totales3 = "0.00";
		} else {
			totales3 = tes3;
		}
		String tes4 = "" + num.format(ArrEc[4]);
		if (tes4.equals(".00")) {
			totales4 = "0.00";
		} else {
			totales4 = tes4;
		}

		//TotalEC value
		totalec = String.format("%.2f", +totalEC1);

		//Getting fslab values
		fslab1 = "" + ArrFslab[1];
		fslab2 = "" + ArrFslab[2];
		fslab3 = "" + ArrFslab[3];
        rep_dl_fslab1 = ""+ArrdlFslab[1];
        rep_dl_fslab2 = ""+ArrdlFslab[2];
        rep_dl_fslab3 = ""+ArrdlFslab[3];

		//Getting frate values
		String frate7 = "" + num.format(ArrFrate[1]);
		if (frate7.equals(".00")) {
			frate1 = "0.00";
		} else {
			frate1 = frate7;
		}

		String frate8 = "" + num.format(ArrFrate[2]);
		if (frate8.equals(".00")) {
			frate2 = "0.00";
		} else {
			frate2 = frate8;
		}

		String frate9 = "" + num.format(ArrFrate[3]);
		if (frate9.equals(".00")) {
			frate3 = "0.00";
		} else {
			frate3 = frate9;
		}

		//Getting totalfs values
		String totalfs7 = "" + num.format(ArrFc[1]);
		if (totalfs7.equals(".00")) {
			totalfs1 = "0.00";
		} else {
			totalfs1 = totalfs7;
		}

		String totalfs8 = "" + num.format(ArrFc[2]);
		if (totalfs8.equals(".00")) {
			totalfs2 = "0.00";
		} else {
			totalfs2 = totalfs8;
		}

		String totalfs9 = "" + num.format(ArrFc[3]);
		if (totalfs9.equals(".00")) {
			totalfs3 = "0.00";
		} else {
			totalfs3 = totalfs9;
		}

		//TotalFC value
		totalfc = String.format("%.2f", +totalFC1);
	}

	//Setting the values for the textview
	public void setvalue() {

		tvsubd.setText(rep_subdiv);
		tvrrno.setText(rep_rrno);
		tvlegf.setText(legfol);
		tvtariff.setText(tarifn);
		tvname.setText(rep_consname);
		tvaddr.setText(rep_address);
		tvconsno.setText(rep_consid);
		tvbillno.setText(billnum);
		tvmtr.setText(mtrs);
		tvlinem.setText(linm);
		tvmf.setText(mfs);
		tvdate2.setText(prevdate);
		tvdate1.setText(readdate);
		tvcur.setText(rep_presentrdg);
		tvprv.setText(prevrdg);
		tvfr.setText(frs);
		tvir.setText(irs);
		tvconm.setText(conms);
		tvavgcon.setText(avgcons);
		tvgkpay.setText(gkpays);
		double discount = rebates1 + gkpays1;
//		tvrebate.setText(rebates);
		tvrebate.setText(fcall.decimalroundoff(discount));
		tvbillamnt.setText(billamnts);
		tvarr.setText(arrs);
		tvintr.setText(rep_intrate);
		tvintrtv.setText(intrs);
		tvtaxe.setText(rep_taxrate);
		tvtax.setText(taxs);
		tvcredit.setText(credits);
		tvmrcode.setText(mrcodes);
		tvtel.setText(telph);
		tvintd.setText(intds);
		tvasd.setText(asds);
		tviod.setText(iods);
		tvpay.setText(rep_payable);
		tvdue.setText(duedate);
		tvcity.setText(place);
		tvprint.setText(printtime);
		tvhelp.setText(helpline);
		tvmachine.setText(machineid);
		tvver.setText(verid);
		tvfc.setText(totalfc);
		tvec.setText(totalec);
		if (StringUtils.startsWithIgnoreCase(rep_report, "Billed") || rep_display.equals("normalbilled")) {
			tvrep_duplicate.setVisibility(View.VISIBLE);
		} else {
			tvrep_duplicate.setVisibility(View.GONE);
		}
		tvdivcode.setText(divcodes);
		tvstat1.setText(prstat1);
		tvstat2.setText(prstat2);
		tvmnth1.setText(month(readdate));
		tvmnth2.setText(month(prevdate));
		tvload.setText(load);
		tvkw.setText(kw);
		if (data1 > 0) {
			tvrep_textothers1.setText(rep_extra1);
			tvrep_others1.setText(rep_data1);
		} else {
			tvrep_textothers1.setVisibility(View.GONE);
			tvrep_others1semi.setVisibility(View.GONE);
			tvrep_others1.setVisibility(View.GONE);
		}
		if (data2 > 0) {
			tvrep_textothers2.setText(rep_extra2);
			tvrep_others2.setText(rep_data2);
		} else {
			tvrep_textothers2.setVisibility(View.GONE);
			tvrep_others2semi.setVisibility(View.GONE);
			tvrep_others2.setVisibility(View.GONE);
		}
		if (rep_pfflag.equals("2") || rep_pfflag.equals("1")) {
			tvpfvaltext.setVisibility(View.VISIBLE);
			tvpf.setVisibility(View.VISIBLE);
			tvpfpentext.setVisibility(View.VISIBLE);
			tvpfpen.setVisibility(View.VISIBLE);
			tvpf.setText(rep_pfvalue);
			tvpfpen.setText(rep_pfpenalty);
			tvbmdtext.setVisibility(View.VISIBLE);
			tvbmd.setVisibility(View.VISIBLE);
			tvbmdpentext.setVisibility(View.VISIBLE);
			tvbmdpen.setVisibility(View.VISIBLE);
			tvbmd.setText(rep_bmdvalue);
			tvbmdpen.setText(rep_bmdpenalty);
		} else {
			if (rep_pfflag.equals("0")) {
				if (Double.parseDouble(rep_bmdvalue) > 0) {
					tvpfvaltext.setVisibility(View.GONE);
					tvpf.setVisibility(View.GONE);
					tvpfpentext.setVisibility(View.GONE);
					tvpfpen.setVisibility(View.GONE);
					tvbmdtext.setVisibility(View.VISIBLE);
					tvbmd.setVisibility(View.VISIBLE);
					tvbmdpentext.setVisibility(View.VISIBLE);
					tvbmdpen.setVisibility(View.VISIBLE);
				} else {
					tvpfvaltext.setVisibility(View.GONE);
					tvpf.setVisibility(View.GONE);
					tvpfpentext.setVisibility(View.GONE);
					tvpfpen.setVisibility(View.GONE);
					tvbmdtext.setVisibility(View.GONE);
					tvbmd.setVisibility(View.GONE);
					tvbmdpentext.setVisibility(View.GONE);
					tvbmdpen.setVisibility(View.GONE);
				}
			} else {
				tvpfvaltext.setVisibility(View.GONE);
				tvpf.setVisibility(View.GONE);
				tvpfpentext.setVisibility(View.GONE);
				tvpfpen.setVisibility(View.GONE);
				tvbmdtext.setVisibility(View.GONE);
				tvbmd.setVisibility(View.GONE);
				tvbmdpentext.setVisibility(View.GONE);
				tvbmdpen.setVisibility(View.GONE);
			}
		}
		if (rep_tariff.equals("70")) {
			tvrep_textpd.setVisibility(View.VISIBLE);
			tvrep_pd.setVisibility(View.VISIBLE);
			tvrep_textpdpen.setVisibility(View.VISIBLE);
			tvrep_pdpen.setVisibility(View.VISIBLE);
			tvrep_pd.setText(rep_bmdvalue);
			tvrep_pdpen.setText(rep_bmdpenalty);
		} else {
			tvrep_textpd.setVisibility(View.GONE);
			tvrep_pd.setVisibility(View.GONE);
			tvrep_textpdpen.setVisibility(View.GONE);
			tvrep_pdpen.setVisibility(View.GONE);
		}
		if (rep_todflag.equals("1")) {
			tvonpeaktext.setVisibility(View.VISIBLE);
			tvonpeaksemi.setVisibility(View.VISIBLE);
			tvTodonPeak.setVisibility(View.VISIBLE);
			tvoffpeaktext.setVisibility(View.VISIBLE);
			tvoffpeaksemi.setVisibility(View.VISIBLE);
			tvTodoffpeak.setVisibility(View.VISIBLE);
			tvTodonPeak.setText(rep_todonpeak);
			tvTodoffpeak.setText(rep_todoffpeak);
		} else {
			tvonpeaktext.setVisibility(View.INVISIBLE);
			tvonpeaksemi.setVisibility(View.INVISIBLE);
			tvTodonPeak.setVisibility(View.INVISIBLE);
			tvoffpeaktext.setVisibility(View.INVISIBLE);
			tvoffpeaksemi.setVisibility(View.INVISIBLE);
			tvTodoffpeak.setVisibility(View.INVISIBLE);
		}

		calculationpart();
	}

	private void actionbaricon() {
		rep_display = "PaymentReprint";
		tvsearch.setVisibility(View.VISIBLE);
		View v = getLayoutInflater().inflate(R.layout.actionbar, null);
		ImageView iv = (ImageView) v.findViewById(R.id.imageView1);
		iv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				View v_b = v;
				PopupMenu popup = new PopupMenu(Reportdisp.this, v);

				popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {
							case R.id.item1:
								actvsearch.setVisibility(View.VISIBLE);
								c7 = dbh.Billedsearchbyid();
								idadap = new ArrayAdapter<String>(Reportdisp.this, android.R.layout.simple_dropdown_item_1line, idarray);
								actvsearch.setAdapter(idadap);
								idarray.clear();
								idadap.notifyDataSetChanged();
								if (c7.getCount() > 0) {
									tvsearch.setText("Search Report by ID");
									actvsearch.setHint("Enter Customer Id");
									while (c7.moveToNext()) {
										rep_custid = c7.getString(c7.getColumnIndex("CONSNO"));
										idarray.add(rep_custid);
									}
									c7.close();
									Collections.sort(idarray);
									idadap.notifyDataSetChanged();
									actvsearch.setThreshold(1);
									actvsearch.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
											custid = (String) arg0.getItemAtPosition(arg2);
											fulldetailsbyid();
											hidekeyboard();
										}
									});
								} else {
									Toast.makeText(Reportdisp.this, "No Records found in Table", Toast.LENGTH_SHORT).show();
									actvsearch.setVisibility(View.GONE);
								}
								return true;

							case R.id.item2:
								actvsearch.setVisibility(View.VISIBLE);
								c7 = dbh.Billedsearchbyrrno();
								rrnoadap = new ArrayAdapter<String>(Reportdisp.this, android.R.layout.simple_dropdown_item_1line, rrnoarray);
								actvsearch.setAdapter(rrnoadap);
								rrnoarray.clear();
								rrnoadap.notifyDataSetChanged();
								if (c7.getCount() > 0) {
									tvsearch.setText("Search Report by RRNO");
									actvsearch.setHint("Enter Customer RRNO");
									while (c7.moveToNext()) {
										rep_custrrno = c7.getString(c7.getColumnIndex("RRNO"));
										rrnoarray.add(rep_custrrno);
									}
									c7.close();
									Collections.sort(rrnoarray);
									rrnoadap.notifyDataSetChanged();
									actvsearch.setThreshold(1);
									actvsearch.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
											custrrno = (String) arg0.getItemAtPosition(arg2);
											fulldetailsbyrrno();
											hidekeyboard();
										}
									});
								} else {
									Toast.makeText(Reportdisp.this, "No Records found in Table", Toast.LENGTH_SHORT).show();
									actvsearch.setVisibility(View.GONE);
								}
								return true;

							case R.id.item3:
								actvsearch.setVisibility(View.VISIBLE);
								c7 = dbh.Billedsearchbyname();
								nameadap = new ArrayAdapter<String>(Reportdisp.this, android.R.layout.simple_dropdown_item_1line, namearray);
								actvsearch.setAdapter(nameadap);
								namearray.clear();
								nameadap.notifyDataSetChanged();
								if (c7.getCount() > 0) {
									tvsearch.setText("Search Report by Name");
									actvsearch.setHint("Enter Customer Name");
									while (c7.moveToNext()) {
										rep_custname = c7.getString(c7.getColumnIndex("NAME"));
										namearray.add(rep_custname);
									}
									c7.close();
									Collections.sort(namearray);
									nameadap.notifyDataSetChanged();
									actvsearch.setThreshold(1);
									actvsearch.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
											custname = (String) arg0.getItemAtPosition(arg2);
											fulldetailsbyname();
											hidekeyboard();
										}
									});
								} else {
									Toast.makeText(Reportdisp.this, "No Records found in Table", Toast.LENGTH_SHORT).show();
									actvsearch.setVisibility(View.GONE);
								}
								return true;

							case R.id.item4:
								actvsearch.setVisibility(View.GONE);
								settextviewtonull();
								return true;

							case R.id.item5:
								c10 = dbh.billed();
								if (c10.getCount() > 0) {
									while (c10.moveToNext()) {
										billprinted = c10.getString(c10.getColumnIndex("BILL_PRINTED"));
										if (billprinted.equals("N")) {
											Intent billsnotprinted = new Intent(Reportdisp.this, BillsnotPrinted.class);
											startActivity(billsnotprinted);
										}
									}
				       /* 		if (!billprinted.equals("N")) {
				        			Toast.makeText(Reportdisp.this, "All Bills are Printed", Toast.LENGTH_SHORT).show();
								}	*/
								} else {
									Toast.makeText(Reportdisp.this, "No Values in billed Records", Toast.LENGTH_SHORT).show();
								}
								return true;
						}
						return false;
					}
				});
				getMenuInflater().inflate(R.menu.actions, popup.getMenu());
				popup.show();
			}
		});

		// Add the custom View to your ActionBar
		final ActionBar actionBar = getActionBar();
		actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL | Gravity.RIGHT));
		actionBar.setDisplayShowCustomEnabled(true);
		// Remove the "up" affordance
		actionBar.setDisplayShowHomeEnabled(true);
	}

	//Making boolean values for calculation values
	public void booleanvalues() {
		es1 = (eslab1.equals("0") || eslab1.equals("0.0") || eslab1.equals(".00"));
		es2 = (eslab2.equals("0") || eslab2.equals("0.0") || eslab2.equals(".00"));
		es3 = (eslab3.equals("0") || eslab3.equals("0.0") || eslab3.equals(".00"));
		es4 = (eslab4.equals("0") || eslab4.equals("0.0") || eslab4.equals(".00"));
		er1 = (erate1.equals("0") || erate1.equals("0.00") || erate1.equals("0.0"));
		er2 = (erate2.equals("0") || erate2.equals("0.00") || erate2.equals("0.0"));
		er3 = (erate3.equals("0") || erate3.equals("0.00") || erate3.equals("0.0"));
		er4 = (erate4.equals("0") || erate4.equals("0.00") || erate4.equals("0.0"));
		fs1 = (fslab1.equals("0") || fslab1.equals("0.0"));
		fs2 = (fslab2.equals("0") || fslab2.equals("0.0"));
		fs3 = (fslab3.equals("0") || fslab3.equals("0.0"));
		fr1 = (frate1.equals("0") || frate1.equals("0.00") || frate1.equals("0.0"));
		fr2 = (frate2.equals("0") || frate2.equals("0.00") || frate2.equals("0.0"));
		fr3 = (frate3.equals("0") || frate3.equals("0.00") || frate3.equals("0.0"));
	}

	//Setting calculation textviews
	public void calculationpart() {
		if (es1) {
			eval1 = true;
			tveslab1.setText("");
			tverate1.setText("");
		} else {
			tveslab1.setText(eslab1);
			tverate1.setText(erate1);
		}

		if (es2) {
			eval2 = true;
			tveslab2.setText("");
			tverate2.setText("");
		} else {
			tveslab2.setText(eslab2);
			tverate2.setText(erate2);
		}

		if (es3) {
			eval3 = true;
			tveslab3.setText("");
			tverate3.setText("");
		} else {
			tveslab3.setText(eslab3);
			tverate3.setText(erate3);
		}

		if (es4) {
			eval4 = true;
			tveslab4.setText("");
			tverate4.setText("");
		} else {
			tveslab4.setText(eslab4);
			tverate4.setText(erate4);
		}

		if (fs1) {
			fval1 = true;
			tvfslab1.setText("");
			tvfrate1.setText("");
		} else {
			tvfslab1.setText(fslab1);
			tvfrate1.setText(frate1);
		}

		if (fs2) {
			fval2 = true;
			tvfslab2.setText("");
			tvfrate2.setText("");
		} else {
			tvfslab2.setText(fslab2);
			tvfrate2.setText(frate2);
		}

		if (fs3) {
			fval3 = true;
			tvfslab3.setText("");
			tvfrate3.setText("");
		} else {
			tvfslab3.setText(fslab3);
			tvfrate3.setText(frate3);
		}

		if (eval1) {
			tvst4.setText("");
			tveq4.setText("");
			tvtes1.setText("");
		} else {
			tvtes1.setText(totales1);
		}

		if (eval2) {
			tvst5.setText("");
			tveq5.setText("");
			tvtes2.setText("");
		} else {
			tvtes2.setText(totales2);
		}

		if (eval3) {
			tvst6.setText("");
			tveq6.setText("");
			tvtes3.setText("");
		} else {
			tvtes3.setText(totales3);
		}

		if (eval4) {
			tvst7.setText("");
			tveq7.setText("");
			tvtes4.setText("");
		} else {
			tvtes4.setText(totales4);
		}

		if (fval1) {
			tvst1.setText("");
			tveq1.setText("");
			tvtfs1.setText("");
		} else {
			tvtfs1.setText(totalfs1);
		}

		if (fval2) {
			tvst2.setText("");
			tveq2.setText("");
			tvtfs2.setText("");
		} else {
			tvtfs2.setText(totalfs2);
		}

		if (fval3) {
			tvst3.setText("");
			tveq3.setText("");
			tvtfs3.setText("");
		} else {
			tvtfs3.setText(totalfs3);
		}
	}

	//Convert to dateformat for printdate
	public String getDate(String datevalue) {
		String a1 = datevalue.substring(0, 2);
		String a2 = datevalue.substring(3, 5);
		String a3 = datevalue.substring(6);
		extdate = (a1 + "/" + a2 + "/" + a3);
		return extdate;
	}

	//Sending values for calculation
	public void setvaluesforcalculation() {
		cc.setCalMF(lgmf);
		cc.setPrvRead(prevrdg);
		cc.setCurReading(rep_presentrdg);
		cc.setconsumtion(conms);
		cc.setSanctionKw(kw);
		cc.setSantionHp(load);
		cc.setStatusLabel(prstat1);
		cc.setdl_count(rep_dlcount);
		cc.settariff(rep_tariff);
		cc.setRrebate(rRebate);
		cc.setTod_onPeakValue(todonpeak);
		cc.setTod_ofPeakValue(todoffpeak);
		cc.setdata1(data1);
		cc.setdata2(data2);
		if (rep_tariff.equals("70")) {
			cc.setweeks(rep_billdays);
		}
	}

	//Finding the month name
	public String month(String s) {
		String a2 = fcall.changedateformat(s, "/").substring(3, 5);
		int m1 = Integer.parseInt(a2);
		String[] months = new String[]{"JAN", "FEB", "MAR",
				"APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
		mnth = months[m1 - 1];
		return mnth;
	}

	//Substring for address field
	public void address(String s) {

		if (s.length() > 16) {
			customaddr1 = s.substring(0, 16);
		} else {
			customaddr1 = s;
		}

		if (rep_address.length() > 16) {
			add1 = s.substring(16);
		} else {
			add1 = "Hi";
		}
		if (add1.length() > 16) {
			customaddr2 = add1.substring(0, 16);
		} else {
			customaddr2 = add1;
		}
	}

	//Converting all date to dateformat
	public String convertdate(String s) {
		String a1 = s.substring(0, 2);
		String a2 = s.substring(3, 5);
		String a3 = s.substring(6);
		String a4 = a1 + "/" + a2 + "/" + a3;
		return a4;
	}

	public void printanalogics() {
		/*Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/DroidSansMono.ttf");
		String address = BluetoothService.printer_address;
		StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fcall.line(38));
		stringBuilder.append(rep_subdiv + "\n");
		stringBuilder.append("(" + divcodes + ")");
		analogicsprint(address, stringBuilder.toString(), 25, tf, Layout.Alignment.ALIGN_CENTER);
		stringBuilder.setLength(0);
		stringBuilder.append(fcall.space("RRNO", 8) + ":" + " " + rep_rrno);
		analogicsprint(address, stringBuilder.toString(), 30, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		stringBuilder.append(fcall.space("Account ID", 16) + ":" + " " + rep_consid);
        analogicsprint(address, stringBuilder.toString(), 30, tf, ALIGN_NORMAL);
        stringBuilder.setLength(0);
		stringBuilder.append(fcall.space("Mtr.Rdr.Code", 16) + ":" + " " + mrcodes + "\n");
		analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		stringBuilder.append(customname + "\n");
		stringBuilder.append(rep_address_1 + "\n");
		stringBuilder.append(rep_address_2);
		analogicsprint(address, stringBuilder.toString(), 20, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		stringBuilder.append(fcall.space("Tariff", 16) + ":" + " " + tarifn + "\n");
		stringBuilder.append(fcall.space("Sanct Load", 16) + ":");
		stringBuilder.append("HP:" + fcall.rightalign(load, 5));
		stringBuilder.append(String.format("%1s", fcall.leftalign(" ", 1)));
		stringBuilder.append("KW:" + fcall.rightalign(kw, 5) + "\n");
		stringBuilder.append(fcall.space("Billing Period", 15) + ":" + prevdate + "-" + readdate);
		analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		stringBuilder.append(fcall.space("Reading Date", 12) + ":" + " " + readdate);
		analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		stringBuilder.append(fcall.space("BillNo", 10) + ":" + " " + rep_consid + "-" + readdate + "\n");
		stringBuilder.append(fcall.space("Meter SlNo.", 16) + ":" + " " + mtrs);
		analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		stringBuilder.append(fcall.space("Pres Rdg", 11) + ":" + " " + rep_presentrdg + "\n");
		stringBuilder.append(fcall.space("Prev Rdg", 11) + ":" + " " + prevrdg);
		analogicsprint(address, stringBuilder.toString(), 35, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		stringBuilder.append(fcall.space("Constant", 16) + ":" + " " + mfs + "\n");
		stringBuilder.append(fcall.space("Consumption", 16) + ":" + " " + conms + "\n");
		stringBuilder.append(fcall.space("Average", 16) + ":" + " " + avgcons + "\n");
		if (rep_pfflag.equals("2") || rep_pfflag.equals("1")) {
			stringBuilder.append(fcall.space("Recorded MD", 16) + ":" + " " + rep_bmdvalue + "\n");
			stringBuilder.append(fcall.space("Power Factor", 16) + ":" + " " + rep_pfvalue);
		} else if (rep_pfflag.equals("0")) {
			if (Double.parseDouble(rep_bmdvalue) > 0) {
				stringBuilder.append(fcall.space("Recorded MD", 16) + ":" + " " + rep_bmdvalue + "\n");
			} else {
				stringBuilder.append(" " + "\n");
			}
		}
		stringBuilder.append(" ");
		analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		if (rep_slabs.equals("Y") || rep_slabs.equals("y")) {
			if (!fs1) {
				stringBuilder.append(String.format("%8s", fcall.rightalign(fslab1, 7)));
				stringBuilder.append("  " + "x");
				stringBuilder.append(String.format("%8s", fcall.rightalign(frate1, 7)));
				stringBuilder.append(String.format("%18s", fcall.rightalign(totalfs1, 12)) + "\n");
			} else {
				stringBuilder.append("\n");
			}
			if (!fs2) {
				stringBuilder.append(String.format("%8s", fcall.rightalign(fslab2, 7)));
				stringBuilder.append("  " + "x");
				stringBuilder.append(String.format("%8s", fcall.rightalign(frate2, 7)));
				stringBuilder.append(String.format("%18s", fcall.rightalign(totalfs2, 12)) + "\n");
			} else {
				stringBuilder.append("\n");
			}
			*//*if (!fs3) {
				stringBuilder.append(String.format("%8s", fcall.rightalign(fslab3, 7)));
				stringBuilder.append("  " + "x");
				stringBuilder.append(String.format("%8s", fcall.rightalign(frate3, 7)));
				stringBuilder.append(String.format("%18s", fcall.rightalign(totalfs3, 12)));
			} else {
				stringBuilder.append(" ");
			}*//*
		} else {
			if (rep_slabs.equals("N") || rep_slabs.equals("n")) {
				stringBuilder.append("\n");
				*//*stringBuilder.append("\n");*//*
				stringBuilder.append(" ");
			}
		}
		analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		if (rep_slabs.equals("Y") || rep_slabs.equals("y")) {
			if (!es1) {
				stringBuilder.append(String.format("%8s", fcall.rightalign(eslab1, 7)));
				stringBuilder.append("  " + "x");
				stringBuilder.append(String.format("%8s", fcall.rightalign(erate1, 7)));
				stringBuilder.append(String.format("%18s", fcall.rightalign(totales1, 12)) + "\n");
			} else {
				stringBuilder.append("\n");
			}
			if (!es2) {
				stringBuilder.append(String.format("%8s", fcall.rightalign(eslab2, 7)));
				stringBuilder.append("  " + "x");
				stringBuilder.append(String.format("%8s", fcall.rightalign(erate2, 7)));
				stringBuilder.append(String.format("%18s", fcall.rightalign(totales2, 12)) + "\n");
			} else {
				stringBuilder.append("\n");
			}
			if (!es3) {
				stringBuilder.append(String.format("%8s", fcall.rightalign(eslab3, 7)));
				stringBuilder.append("  " + "x");
				stringBuilder.append(String.format("%8s", fcall.rightalign(erate3, 7)));
				stringBuilder.append(String.format("%18s", fcall.rightalign(totales3, 12)) + "\n");
			} else {
				stringBuilder.append("\n");
			}
			if (!es4) {
				stringBuilder.append(String.format("%8s", fcall.rightalign(eslab4, 7)));
				stringBuilder.append("  " + "x");
				stringBuilder.append(String.format("%8s", fcall.rightalign(erate4, 7)));
				stringBuilder.append(String.format("%18s", fcall.rightalign(totales4, 12)));
			} else {
				stringBuilder.append(" ");
			}
		} else {
			if (rep_slabs.equals("N") || rep_slabs.equals("n")) {
				stringBuilder.append("\n");
				stringBuilder.append("\n");
				stringBuilder.append("\n");
				stringBuilder.append(" ");
			}
		}
		analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		*//*if (!rep_subdiv_fec.equals(".00")) {
			stringBuilder.append(pay.space("FEC", 7) + ":" + " ");
			stringBuilder.append(String.format("%7s", fcall.rightalign(conms, 7)));
			stringBuilder.append("  " + "x");
			stringBuilder.append(String.format("%7s", fcall.rightalign(rep_subdiv_fec, 7)));
			stringBuilder.append(String.format("%11s", fcall.rightalign(rep_data2, 10)) + "\n");
		} else {
			stringBuilder.append("FEC" + "\n");
		}*//*
		stringBuilder.append(pay.space("Rebates/TOD", 16) + ":" + " ");
		stringBuilder.append(String.format("%19s", fcall.rightalign(rebates, 12)) + "\n");
		stringBuilder.append(pay.space("PF Penalty", 16) + ":" + " ");
		stringBuilder.append(String.format("%19s", fcall.rightalign(rep_pfpenalty, 12)));
		analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		stringBuilder.append(pay.space("MD Penalty", 16) + ":" + " ");
		stringBuilder.append(String.format("%19s", fcall.rightalign(rep_bmdpenalty, 12)) + "\n");
		stringBuilder.append(pay.space("Interest", 16) + ":" + " ");
		stringBuilder.append(String.format("%19s", fcall.rightalign(intrs, 12)) + "\n");
		stringBuilder.append(pay.space("Others", 16) + ":" + " ");
		stringBuilder.append(String.format("%19s", fcall.rightalign(rep_data1, 12)));
		analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		stringBuilder.append(pay.space("Tax", 16) + ":" + " ");
		stringBuilder.append(String.format("%19s", fcall.rightalign(taxs, 12)) + "\n");
		stringBuilder.append(pay.space("Cur Bill Amt", 16) + ":" + " ");
		stringBuilder.append(String.format("%19s", fcall.rightalign(billamnts, 12)) + "\n");
		stringBuilder.append(pay.space("Arrears", 16) + ":" + " ");
		stringBuilder.append(String.format("%19s", fcall.rightalign(arrs, 12)));
		analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		stringBuilder.append(pay.space("Credits & Adj", 16) + ":" + " ");
		stringBuilder.append(String.format("%19s", fcall.rightalign(credits, 12)) + "\n");
		stringBuilder.append(pay.space("GOK Subsidy", 16) + ":" + " ");
		stringBuilder.append(String.format("%19s", fcall.rightalign(gkpays, 12)));
		analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		stringBuilder.append(pay.space("Net Amt Due", 11) + ":" + " ");
		stringBuilder.append(String.format("%14s", fcall.rightalign(rep_payable, 12)));
		analogicsprint(address, stringBuilder.toString(), 35, tf, ALIGN_NORMAL);
		stringBuilder.setLength(0);
		stringBuilder.append(pay.space("Due Date", 16) + ":" + " ");
		stringBuilder.append(String.format("%19s", fcall.rightalign(duedate, 12)) + "\n");
		stringBuilder.append(pay.space("Printed On", 16) + ":" + " " + String.format("%19s", fcall.rightalign(fcall.currentDateandTime(), 16)));
		analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
        *//*Bluetooth_Printer_3inch_prof_ThermalAPI api = new Bluetooth_Printer_3inch_prof_ThermalAPI();
        conn.printData(api.barcode_EAN_JAN_13_VIP("1234567890123"));*//*
		stringBuilder.setLength(0);
		stringBuilder.append("\n");
		stringBuilder.append("\n");
		analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);*/


        StringBuilder stringBuilder = new StringBuilder();
        analogics_header__double_print(fcall.aligncenter("HUBLI ELECTRICITY SUPPLY COMPANY LTD", 38), 6);
        analogicsprint(fcall.aligncenter(rep_subdiv, 30), 6);
        analogicsprint(fcall.space("Sub Division", 16) + ":" + " " + divcodes, 6);
        analogicsprint(fcall.space("RRNO", 16) + ":" + " " + rep_rrno, 6);
        analogics_double_print(fcall.space("Account ID", 16) + ":" + " " + rep_consid, 6);
		analogics_48_print(fcall.aligncenter("Name and Address", 48), 6);
        analogics_48_print(rep_consname, 3);
        analogics_48_print(rep_address_1, 3);
        analogics_48_print(rep_address_2, 6);
        analogicsprint(fcall.space("Tariff", 16) + ":" + " " + tarifn, 6);
        analogicsprint(fcall.space("Sanct Load", 14) + ":" + "HP:" + fcall.alignright(load, 4) + " " + "KW:" + fcall.alignright(kw, 4), 6);
        analogicsprint(fcall.space("Billing", 8) + ":" + prevdate + "-" + readdate, 6);
        analogicsprint(fcall.space("Reading Date", 16) + ":" + " " + readdate, 6);
        analogicsprint(fcall.space("BillNo", 7) + ":" + " " + rep_consid + "-" + readdate, 6);
        analogicsprint(fcall.space("Meter SlNo.", 16) + ":" + " " + mtrs, 6);
        analogicsprint(fcall.space("Pres Rdg", 16) + ":" + " " + rep_presentrdg, 6);
        analogicsprint(fcall.space("Prev Rdg", 16) + ":" + " " + prevrdg, 6);
        analogicsprint(fcall.space("Constant", 16) + ":" + " " + mfs, 6);
        analogicsprint(fcall.space("Consumption", 16) + ":" + " " + conms, 6);
        analogicsprint(fcall.space("Average", 16) + ":" + " " + avgcons, 6);
        if (rep_pfflag.equals("2") || rep_pfflag.equals("1")) {
            analogicsprint(fcall.space("Recorded MD", 16) + ":" + " " + rep_bmdvalue, 6);
            analogicsprint(fcall.space("Power Factor", 16) + ":" + " " + rep_pfvalue, 6);
        } else if (rep_pfflag.equals("0")) {
            if (Double.parseDouble(rep_bmdvalue) > 0) {
                analogicsprint(fcall.space("Recorded MD", 16) + ":" + " " + rep_bmdvalue, 6);
            } else {
                analogicsprint(" ", 6);
            }
            analogicsprint(" ", 6);
        }
        analogicsprint(fcall.aligncenter("Fixed Charges ", 30), 6);
        if (!fs1) {
            if (rep_dlcount != 0) {
                analogicsprint(fcall.alignright(fslab1, 6) + " " + "x" + fcall.alignright(frate1, 6) + fcall.alignright(totalfs1, 16), 6);
            } else analogicsprint(fcall.alignright(fslab1, 6) + " " + "x" + fcall.alignright(frate1, 6) + fcall.alignright(totalfs1, 16), 6);
        }
        else analogicsprint(" ", 6);
        if (!fs2)
            analogicsprint(fcall.alignright(fslab2, 6) + " " + "x" + fcall.alignright(frate2, 6) + fcall.alignright(totalfs2, 16), 6);
        else analogicsprint(" ", 6);
        analogicsprint(fcall.aligncenter("Energy Charges", 30), 6);
        if (!es1)
            analogicsprint(fcall.alignright(eslab1, 6) + " " + "x" + fcall.alignright(erate1, 6) + fcall.alignright(totales1, 16), 6);
        else analogicsprint(" ", 6);
        if (!es2)
            analogicsprint(fcall.alignright(eslab2, 6) + " " + "x" + fcall.alignright(erate2, 6) + fcall.alignright(totales2, 16), 6);
        else analogicsprint(" ", 6);
        if (!es3)
            analogicsprint(fcall.alignright(eslab3, 6) + " " + "x" + fcall.alignright(erate3, 6) + fcall.alignright(totales3, 16), 6);
        else analogicsprint(" ", 6);
        if (!es4)
            analogicsprint(fcall.alignright(eslab4, 6) + " " + "x" + fcall.alignright(erate4, 6) + fcall.alignright(totales4, 16), 6);
        else analogicsprint(" ", 6);
        if (!rep_subdiv_fec.equals(".00")) {
            analogicsprint(fcall.space("FAC", 4) + ":" + " " + fcall.alignright(conms, 7) + " " + "x" +
                    fcall.alignright(rep_subdiv_fec, 4) + fcall.alignright(rep_data2, 11), 6);
        } else analogicsprint(" ", 6);
        analogicsprint(fcall.space("Rebates/TOD", 11) + "(-)" + ":" + " " + fcall.alignright(rebates, 14), 5);
        analogicsprint(fcall.space("PF Penalty", 14) + ":" + " " + fcall.alignright(rep_pfpenalty, 14), 5);
        analogicsprint(fcall.space("MD Penalty", 14) + ":" + " " + fcall.alignright(rep_bmdpenalty, 14), 5);
        analogicsprint(fcall.space("Interest", 11) + "@1%" + ":" + " " + fcall.alignright(intrs, 14), 5);
        analogicsprint(fcall.space("Others", 14) + ":" + " " + fcall.alignright(rep_data1, 14), 5);
        analogicsprint(fcall.space("Tax", 11) + "@6%" + ":" + " " + fcall.alignright(taxs, 14), 5);
        analogicsprint(fcall.space("Cur Bill Amt", 14) + ":" + " " + fcall.alignright(rep_cur_bill, 14), 6);
        analogicsprint(fcall.space("Arrears", 14) + ":" + " " + fcall.alignright(arrs, 14), 4);
        analogicsprint(fcall.space("Credits&Adj", 11) + "(-)" + ":" + " " + fcall.alignright(credits, 14), 4);
        analogicsprint(fcall.space("GOK Subsidy", 11) + "(-)" + ":" + " " + fcall.alignright(gkpays, 14), 0);
        analogics_double_print(fcall.space("Net Amt Due", 14) + ":" + " " + fcall.alignright(rep_payable, 14), 0);
        analogicsprint(fcall.space("Due Date", 14) + ":" + " " + fcall.alignright(duedate, 14), 4);
        analogicsprint(fcall.space("Billed On", 12) + ":" + " " + fcall.alignright(fcall.currentDateandTime(), 16), 6);
        if (!rep_extra1.equals("0"))
            analogicsprint(rep_extra1, 6);
        analogicsprint("" , 6);
        analogicsprint(fcall.space("MRCode", 6) + ":" + mrcodes +" "+ rep_mrname, 6);
        analogicsprint("" , 6);
        print_bar_code(rep_consid + rep_payable.substring(0, rep_payable.indexOf('.')));
        analogicsprint(fcall.space(" ", 3) + machineid + mrcodes, 6);
        stringBuilder.setLength(0);
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        analogicsprint(stringBuilder.toString(), 4);
	}

    public void analogicsprint(String Printdata, int feed_line) {
        conn.printData(api.font_Courier_30_VIP(Printdata));
        text_line_spacing(feed_line);
    }

    public void analogics_double_print(String Printdata, int feed_line) {
        conn.printData(api.font_Double_Height_On_VIP());
        analogicsprint(Printdata, feed_line);
        conn.printData(api.font_Double_Height_Off_VIP());
    }

    public void analogics_header_print(String Printdata, int feed_line) {
        conn.printData(api.font_Courier_38_VIP(Printdata));
        text_line_spacing(feed_line);
    }

    public void analogics_header__double_print(String Printdata, int feed_line) {
        conn.printData(api.font_Double_Height_On_VIP());
        analogics_header_print(Printdata, feed_line);
        conn.printData(api.font_Double_Height_Off_VIP());
    }

    public void analogics_48_print(String Printdata, int feed_line) {
        conn.printData(api.font_Courier_48_VIP(Printdata));
        text_line_spacing(feed_line);
    }

    public void text_line_spacing(int space) {
        conn.printData(api.variable_Size_Line_Feed_VIP(space));
    }

    private void print_bar_code(String msg) {
        String feeddata = "";
        feeddata = api.barcode_Code_128_Alpha_Numerics_VIP(msg);
        conn.printData(feeddata);
    }

	public void printngx() {
		mBtp.setPrinterWidth(PrinterWidth.PRINT_WIDTH_72MM);
		if (mBtp.getState() != BluetoothPrinter.STATE_CONNECTED) {
			Toast.makeText(Reportdisp.this, "Printer is not connected", Toast.LENGTH_SHORT).show();
			return;
		}
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/DroidSansMono.ttf");
		TextPaint tp = new TextPaint();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n");
		stringBuilder.append("HUBLI ELECTRICITY SUPPLY COMPANY LTD"+"\n");
		stringBuilder.append(rep_subdiv);
		tp.setTextSize(25);
		tp.setTypeface(tf);
		mBtp.addText(stringBuilder.toString(), ALIGN_CENTER, tp);
		stringBuilder.setLength(0);
		stringBuilder.append(fcall.space("Sub Division", 18) + ":" + " " + divcodes);
		tp.setTextSize(25);
		tp.setTypeface(tf);
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		stringBuilder.setLength(0);

//        StringUtils.startsWithIgnoreCase(rep_preprint, "n")

//		stringBuilder.append(fcall.space(" ", 18) + "  " + rep_rrno);
//		stringBuilder.append(fcall.space(" ", 18) + "  " + rep_consid);
        stringBuilder.append(fcall.space("RRNO", 18) + ":" + " " + rep_rrno);
        tp.setTextSize(25);
        tp.setTypeface(tf);
        mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
        stringBuilder.setLength(0);

        stringBuilder.append(fcall.space("Account ID", 15) + ":" + " " + rep_consid);
		tp.setTextSize(30);
		tp.setTypeface(Typeface.create(tf, Typeface.BOLD));
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		stringBuilder.setLength(0);

		stringBuilder.append(fcall.aligncenter("Name and Address", 44) + "\n");
		stringBuilder.append(rep_consname + "\n");
		stringBuilder.append(rep_address_1 + "\n");
		stringBuilder.append(rep_address_2);
		tp.setTextSize(20);
		tp.setTypeface(tf);
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		stringBuilder.setLength(0);

		/*stringBuilder.append(fcall.space(" ", 18) + "  " + tarifn + "\n");
		stringBuilder.append(fcall.space(" ", 18) + " ");
		stringBuilder.append("HP:" + fcall.rightalign(load, 5));
		stringBuilder.append(String.format("%1s", fcall.leftalign(" ", 1)));
		stringBuilder.append("KW:" + fcall.rightalign(kw, 5) + "\n");
		stringBuilder.append(fcall.space(" ", 16) + prevdate + "-" + readdate);*/
        stringBuilder.append(fcall.space("Tariff", 18) + ":" + " " + tarifn + "\n");
        stringBuilder.append(fcall.space("Sanct Load", 18) + ":");
        stringBuilder.append("HP:" + fcall.rightalign(load, 5));
        stringBuilder.append(String.format("%1s", fcall.leftalign(" ", 1)));
        stringBuilder.append("KW:" + fcall.rightalign(kw, 5) + "\n");
        stringBuilder.append(fcall.space("Billing Period", 15) + ":" + prevdate + "-" + readdate);
		tp.setTextSize(25);
		tp.setTypeface(tf);
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		stringBuilder.setLength(0);

//		stringBuilder.append(fcall.space(" ", 18) + "  " + readdate);
        stringBuilder.append(fcall.space("Reading Date", 18) + ":" + " " + readdate);
		tp.setTextSize(25);
		tp.setTypeface(Typeface.create(tf, Typeface.BOLD));
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		stringBuilder.setLength(0);

		/*stringBuilder.append(fcall.space(" ", 16) + rep_consid + "-" + readdate + "\n");
		stringBuilder.append(fcall.space(" ", 18) + "  " + mtrs);*/
        stringBuilder.append(fcall.space("BillNo", 14) + ":" + " " + rep_consid + "-" + readdate + "\n");
        stringBuilder.append(fcall.space("Meter SlNo.", 18) + ":" + " " + mtrs);
		tp.setTextSize(25);
		tp.setTypeface(tf);
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		stringBuilder.setLength(0);

		/*stringBuilder.append(fcall.space(" ", 18) + "  " + rep_presentrdg + "\n");
		stringBuilder.append(fcall.space(" ", 18) + "  " + prevrdg);*/
        stringBuilder.append(fcall.space("Pres Rdg", 18) + ":" + " " + rep_presentrdg + "\n");
        stringBuilder.append(fcall.space("Prev Rdg", 18) + ":" + " " + prevrdg);
		tp.setTextSize(25);
		tp.setTypeface(Typeface.create(tf, Typeface.BOLD));
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		stringBuilder.setLength(0);

		/*stringBuilder.append(fcall.space(" ", 18) + "  " + fcall.space(mfs, 7) + "\n");
		if (!rep_subdiv_fec.equals(".00"))
		    stringBuilder.append(fcall.space(" ", 18) + "  " + fcall.space(conms, 7) + " " + String.format("%9s", fcall.rightalign("FAC", 9)) + "\n");
        else stringBuilder.append(fcall.space(" ", 18) + "  " + conms + "\n");
        if (!rep_subdiv_fec.equals(".00"))
            stringBuilder.append(fcall.space(" ", 18) + "  " + fcall.space(avgcons, 7) + " " + String.format("%9s", fcall.rightalign(rep_data2, 9)) + "\n");
        else stringBuilder.append(fcall.space(" ", 18) + "  " + avgcons + "\n");
		if (rep_pfflag.equals("2") || rep_pfflag.equals("1")) {
			stringBuilder.append(fcall.space(" ", 18) + "  " + rep_bmdvalue + "\n");
			stringBuilder.append(fcall.space(" ", 18) + "  " + rep_pfvalue);
		} else if (rep_pfflag.equals("0")) {
			if (Double.parseDouble(rep_bmdvalue) > 0) {
				stringBuilder.append(fcall.space(" ", 18) + "  " + rep_bmdvalue + "\n");
			} else {
				stringBuilder.append(" " + "\n");
			}
		}*/
        stringBuilder.append(fcall.space("Constant", 18) + ":" + " " + mfs + "\n");
        stringBuilder.append(fcall.space("Consumption", 18) + ":" + " " + conms + "\n");
        stringBuilder.append(fcall.space("Average", 18) + ":" + " " + avgcons + "\n");
        if (rep_pfflag.equals("2") || rep_pfflag.equals("1")) {
            stringBuilder.append(fcall.space("Recorded MD", 18) + ":" + " " + rep_bmdvalue + "\n");
            stringBuilder.append(fcall.space("Power Factor", 18) + ":" + " " + rep_pfvalue);
        } else if (rep_pfflag.equals("0")) {
            if (Double.parseDouble(rep_bmdvalue) > 0) {
                stringBuilder.append(fcall.space("Recorded MD", 18) + ":" + " " + rep_bmdvalue + "\n");
            } else {
                stringBuilder.append(" " + "\n");
            }
        }
		tp.setTextSize(25);
		tp.setTypeface(tf);
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		stringBuilder.setLength(0);
		stringBuilder.append(fcall.aligncenter("FIXED CHARGES", 37));
		tp.setTextSize(25);
		tp.setTypeface(tf);
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		stringBuilder.setLength(0);
		if (rep_slabs.equals("Y") || rep_slabs.equals("y")) {
			if (!fs1) {
                if (rep_tariff.equals("70")) {
                    stringBuilder.append(fcall.alignright(fslab1, 6) + " " + "x " + fcall.alignright(rep_billdays, 3) + " x ");
                    stringBuilder.append(fcall.alignright("("+frate1+"/7)", 11) + fcall.alignright(totalfs1, 11) + "\n");
                } else {
                    stringBuilder.append(fcall.alignright(fslab1, 8) + "  " + "x");
                    stringBuilder.append(fcall.alignright(frate1, 8) + fcall.alignright(totalfs1, 18) + "\n");
                }
			} else {
				stringBuilder.append("\n");
			}
			if (!fs2) {
				stringBuilder.append(String.format("%8s", fcall.rightalign(fslab2, 7)));
				stringBuilder.append("  " + "x");
				stringBuilder.append(String.format("%8s", fcall.rightalign(frate2, 7)));
				stringBuilder.append(String.format("%18s", fcall.rightalign(totalfs2, 12)) + " ");
			} else {
				stringBuilder.append(" ");
			}
			/*if (!fs3) {
				stringBuilder.append(String.format("%8s", fcall.rightalign(fslab3, 7)));
				stringBuilder.append("  " + "x");
				stringBuilder.append(String.format("%8s", fcall.rightalign(frate3, 7)));
				stringBuilder.append(String.format("%18s", fcall.rightalign(totalfs3, 12)) + "\n");
			} else {
				stringBuilder.append("\n");
			}*/
		} else {
			if (rep_slabs.equals("N") || rep_slabs.equals("n")) {
				stringBuilder.append("\n");
				stringBuilder.append(" ");
//				stringBuilder.append("\n");
			}
		}
		tp.setTextSize(25);
		tp.setTypeface(tf);
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		stringBuilder.setLength(0);
		stringBuilder.append(fcall.aligncenter("ENERGY CHARGES ", 37));
		tp.setTextSize(25);
		tp.setTypeface(tf);
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		stringBuilder.setLength(0);
		if (rep_slabs.equals("Y") || rep_slabs.equals("y")) {
			if (!es1) {
				stringBuilder.append(String.format("%8s", fcall.rightalign(eslab1, 7)));
				stringBuilder.append("  " + "x");
				stringBuilder.append(String.format("%8s", fcall.rightalign(erate1, 7)));
				stringBuilder.append(String.format("%18s", fcall.rightalign(totales1, 12)) + "\n");
			} else {
				stringBuilder.append("\n");
			}
			if (!es2) {
				stringBuilder.append(String.format("%8s", fcall.rightalign(eslab2, 7)));
				stringBuilder.append("  " + "x");
				stringBuilder.append(String.format("%8s", fcall.rightalign(erate2, 7)));
				stringBuilder.append(String.format("%18s", fcall.rightalign(totales2, 12)) + "\n");
			} else {
				stringBuilder.append("\n");
			}
			if (!es3) {
				stringBuilder.append(String.format("%8s", fcall.rightalign(eslab3, 7)));
				stringBuilder.append("  " + "x");
				stringBuilder.append(String.format("%8s", fcall.rightalign(erate3, 7)));
				stringBuilder.append(String.format("%18s", fcall.rightalign(totales3, 12)) + "\n");
			} else {
				stringBuilder.append("\n");
			}
			if (!es4) {
				stringBuilder.append(String.format("%8s", fcall.rightalign(eslab4, 7)));
				stringBuilder.append("  " + "x");
				stringBuilder.append(String.format("%8s", fcall.rightalign(erate4, 7)));
				stringBuilder.append(String.format("%18s", fcall.rightalign(totales4, 12)) + "\n");
			} else {
				stringBuilder.append("\n");
			}
		} else {
			if (rep_slabs.equals("N") || rep_slabs.equals("n")) {
				stringBuilder.append("\n");
				stringBuilder.append("\n");
				stringBuilder.append("\n");
				stringBuilder.append("\n");
			}
		}
		if (!rep_subdiv_fec.equals(".00")) {
			stringBuilder.append(fcall.space("FAC", 7) + ":" + " " + fcall.alignright(conms, 7) + "  " + "x");
			stringBuilder.append(fcall.alignright(rep_subdiv_fec, 7) + fcall.alignright(rep_data2, 11) + "\n");
		} else {
			stringBuilder.append("FAC"+"\n");
		}

		/*stringBuilder.append(pay.space(" ", 20) + "(-)" + String.format("%14s", fcall.rightalign(rebates, 13)) + "\n");
		stringBuilder.append(pay.space(" ", 23) + String.format("%14s", fcall.rightalign(rep_pfpenalty, 13)) + "\n");
		stringBuilder.append(pay.space(" ", 23) + String.format("%14s", fcall.rightalign(rep_bmdpenalty, 13)) + "\n");
		stringBuilder.append(pay.space(" ", 23) + String.format("%14s", fcall.rightalign(intrs, 13)) + "\n");
		stringBuilder.append(pay.space(" ", 23) + String.format("%14s", fcall.rightalign(rep_data1, 13)) + "\n");
		stringBuilder.append(pay.space(" ", 23) + String.format("%14s", fcall.rightalign(taxs, 13)) + "\n");
		stringBuilder.append(pay.space(" ", 23) + String.format("%14s", fcall.rightalign(rep_cur_bill, 13)) + "\n");
		stringBuilder.append(pay.space(" ", 23) + String.format("%14s", fcall.rightalign(arrs, 13)) + "\n");
		stringBuilder.append(pay.space(" ", 20) + "(-)" + String.format("%14s", fcall.rightalign(credits, 13)) + "\n");
		stringBuilder.append(pay.space(" ", 20) + "(-)" + String.format("%14s", fcall.rightalign(gkpays, 13)));*/
        stringBuilder.append(fcall.space("Rebates/TOD", 18) + "(-)" + ":" + " " + fcall.alignright(rebates, 14) + "\n");
        stringBuilder.append(fcall.space("PF Penalty", 21) + ":" + " " + fcall.alignright(rep_pfpenalty, 14) + "\n");
        stringBuilder.append(fcall.space("MD Penalty", 21) + ":" + " " + fcall.alignright(rep_bmdpenalty, 14) + "\n");
        stringBuilder.append(fcall.space("Interest", 18) + "@1%" + ":" + " " + fcall.alignright(intrs, 14) + "\n");
        stringBuilder.append(fcall.space("Others", 21) + ":" + " " + fcall.alignright(rep_data1, 14) + "\n");
        stringBuilder.append(fcall.space("Tax", 18) + "@6%" + ":" + " " + fcall.alignright(taxs, 14) + "\n");
        stringBuilder.append(fcall.space("Cur Bill Amt", 21) + ":" + " " + fcall.alignright(rep_cur_bill, 14) + "\n");
        stringBuilder.append(fcall.space("Arrears", 21) + ":" + " " + fcall.alignright(arrs, 14) + "\n");
        stringBuilder.append(fcall.space("Credits & Adj", 18) + "(-)" + ":" + " " + fcall.alignright(credits, 14) + "\n");
        stringBuilder.append(fcall.space("GOK Subsidy", 18) + "(-)" + ":" + " " + fcall.alignright(gkpays, 14));
		tp.setTextSize(25);
		tp.setTypeface(tf);
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		stringBuilder.setLength(0);

//		stringBuilder.append(pay.space(" ", 19) + String.format("%12s", fcall.rightalign(rep_payable, 12)));
        stringBuilder.append(fcall.space("Net Amt Due", 17) + ":" + " " + fcall.alignright(rep_payable, 12));
		tp.setTextSize(30);
		tp.setTypeface(Typeface.create(tf, Typeface.BOLD));
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		stringBuilder.setLength(0);

//		stringBuilder.append(pay.space(" ", 23) + String.format("%14s", fcall.rightalign(duedate, 13)));
        stringBuilder.append(fcall.space("Due Date", 21) + ":" + " " + fcall.alignright(duedate, 14) + "\n");
        stringBuilder.append(fcall.space("Billed On", 16) + ":" + " " + fcall.alignright(fcall.currentDateandTime(), 19));
		tp.setTextSize(25);
		tp.setTypeface(tf);
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
        if (!rep_extra1.equals("0")) {
            stringBuilder.setLength(0);
            stringBuilder.append(rep_extra1);
            tp.setTextSize(15);
            tp.setTypeface(tf);
            mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
        }
        stringBuilder.setLength(0);
        stringBuilder.append(fcall.space("MRCode", 6) + ":" + mrcodes +" "+ rep_mrname + "\n");
        tp.setTextSize(25);
        tp.setTypeface(tf);
        mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
        stringBuilder.setLength(0);
		mBtp.print();
		mBtp.printBarcode(rep_consid + rep_payable.substring(0, rep_payable.indexOf('.')), NGXBarcodeCommands.CODE128, 45, 400);
		stringBuilder.setLength(0);
        stringBuilder.append("\n");
		stringBuilder.append(fcall.space(" ", 10) + machineid + mrcodes);
		tp.setTextSize(15);
		tp.setTypeface(tf);
		mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
		mBtp.print();
		mBtp.printUnicodeText("\n\n\n\n\n");
	}

	private void printalps() {
		printcalculationText(25);
		printTextString("", "", "", 25, false);
		printTextString("", "", "", 25, false);
		printTextString("", "", "", 25, false);
	}

	private void printTextString(String text_left, String text_centre, String text_right, int nHeight, boolean bBold) {
		m_printer.PrintLineInit(nHeight);
		m_printer.PrintLineString(text_left, nHeight, printer.PrintType.Left, bBold);
		m_printer.PrintLineString(text_centre, nHeight, printer.PrintType.Centering, bBold);
		m_printer.PrintLineString(text_right, nHeight, printer.PrintType.Right, bBold);
		m_printer.PrintLineEnd();
	}

	private void printcalculationText(int nHeight) {
		m_printer.PrintLineInit(nHeight);
		m_printer.PrintLineString("01234567890123456789012345678901234567890123456789", nHeight, 0, false);
		m_printer.PrintLineEnd();
		m_printer.PrintLineInit(nHeight);
		m_printer.PrintLineString("0xxxxxxxxx0xxxxxxxxx0xxxxxxxxx0xxxxxxxxx0xxxxxxxxx", nHeight, 0, false);
		m_printer.PrintLineEnd();
	}

	private class TaskPrint implements Runnable {
//		com.lvrenyang.io.Canvas canvas = null;
        Pos pos = null;

		/*private TaskPrint(com.lvrenyang.io.Canvas canvas) {
			this.canvas = canvas;
		}*/

        private TaskPrint(Pos pos) {
            this.pos = pos;
        }

		@Override
		public void run() {
			// TODO Auto-generated method stub
			/*final boolean bPrintResult = PrintTicket(getApplicationContext(), canvas, 576, 1600);
			final boolean bIsOpened = canvas.GetIO().IsOpened();*/
            final boolean bPrintResult = PrintTicket();//576
            final boolean bIsOpened = pos.GetIO().IsOpened();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getApplicationContext(), bPrintResult ? getResources().getString(R.string.printsuccess) : getResources().getString(R.string.printfailed), Toast.LENGTH_SHORT).show();
					if (bIsOpened)
						yaxis = 0;
					printing.dismiss();
					showdialog(EXIT_DLG);
				}
			});
		}

        public boolean PrintTicket() {
            boolean bPrintResult = false;
            int pre_normal_text_length = 21;

            pos.POS_FeedLine();
            pos.POS_S_Align(1);
            printdoubleText("HUBLI ELECTRICITY SUPPLY COMPANY LTD");
            printText(rep_subdiv);
            pos.POS_S_Align(0);
            printText(fcall.space("  Sub Division", pre_normal_text_length) + ":" + " " + divcodes);
            printText(fcall.space("  RRNO", pre_normal_text_length) + ":" + " " + rep_rrno);
            printdoubleText(fcall.space("  Account ID", pre_normal_text_length) + ":" + " " + rep_consid);
            pos.POS_S_Align(1);
            printText("Name and Address");
            pos.POS_S_Align(0);
            printText("  "+rep_consname);
            printText(rep_address_1);
            printText(rep_address_2);
            printText(fcall.space("  Tariff", pre_normal_text_length) + ":" + " " + tarifn);
            printText(fcall.space("  Sanct Load", pre_normal_text_length) + ":" + " HP:" + fcall.alignright(load, 5) + " " + "KW:" + fcall.alignright(kw, 5));
            printText(fcall.space("  Billing Period", pre_normal_text_length) + ":" + " " + prevdate + "-" + readdate);
            printText(fcall.space("  Reading Date", pre_normal_text_length) + ":" + " " + readdate);
            printText(fcall.space("  BillNo", pre_normal_text_length) + ":" + " " + rep_consid + "-" + readdate);
            printText(fcall.space("  Meter SlNo.", pre_normal_text_length) + ":" + " " + mtrs);
            printText(fcall.space("  Pres Rdg", pre_normal_text_length) + ":" + " " + rep_presentrdg);
            printText(fcall.space("  Prev Rdg", pre_normal_text_length) + ":" + " " + prevrdg);
            printText(fcall.space("  Constant", pre_normal_text_length) + ":" + " " + mfs);
            printText(fcall.space("  Consumption", pre_normal_text_length) + ":" + " " + conms);
            printText(fcall.space("  Average", pre_normal_text_length) + ":" + " " + avgcons);
            if (rep_pfflag.equals("2") || rep_pfflag.equals("1")) {
                printText(fcall.space("  Recorded MD", pre_normal_text_length) + ":" + " " + rep_bmdvalue);
                printText(fcall.space("  Power Factor", pre_normal_text_length) + ":" + " " + rep_pfvalue);
            } else if (rep_pfflag.equals("0")) {
                if (Double.parseDouble(rep_bmdvalue) > 0) {
                    printText(fcall.space("  Recorded MD", pre_normal_text_length) + ":" + " " + rep_bmdvalue);
                } else printText(" ");
                printText(" ");
            }
            pos.POS_S_Align(1);
            printText("Fixed Charges");
            pos.POS_S_Align(0);
            if (!fs1) {
                if (rep_dlcount != 0) {
                    printText("  "+fcall.alignright(rep_dl_days_count, 6) + " " + "x" + fcall.alignright(rep_dl_fslab1, 6) + " " + "x" +
                            fcall.alignright(frate1, 8) + fcall.alignright(totalfs1, 16));
                } else {
                    if (rep_tariff.equals("70"))
                        printText(fcall.alignright(fslab1, 6) + " " + "x " + fcall.alignright(rep_billdays, 3) + " " + "x" +
                                fcall.alignright("("+frate1+"/7)", 11) + fcall.alignright(totalfs1, 17));
                    else printText(fcall.alignright(fslab1, 10) + " " + "x" + fcall.alignright(frate1, 10) + fcall.alignright(totalfs1, 20));
                }
            } else printText(" ");
            if (!fs2) {
                if (rep_dlcount != 0) {
                    printText("  "+fcall.alignright(rep_dl_days_count, 6) + " " + "x" + fcall.alignright(rep_dl_fslab2, 6) + " " + "x" +
                            fcall.alignright(frate2, 8) + fcall.alignright(totalfs2, 16));
                } else printText(fcall.alignright(fslab2, 10) + " " + "x" + fcall.alignright(frate2, 10) + fcall.alignright(totalfs2, 20));
            } else printText(" ");
            pos.POS_S_Align(1);
            printText("Energy Charges");
            pos.POS_S_Align(0);
            if (!es1) {
                printText(fcall.alignright(eslab1, 10) + " " + "x" + fcall.alignright(erate1, 10) + fcall.alignright(totales1, 20));
            } else printText(" ");
            if (!es2) {
                printText(fcall.alignright(eslab2, 10) + " " + "x" + fcall.alignright(erate2, 10) + fcall.alignright(totales2, 20));
            } else printText(" ");
            if (!es3) {
                printText(fcall.alignright(eslab3, 10) + " " + "x" + fcall.alignright(erate3, 10) + fcall.alignright(totales3, 20));
            } else printText(" ");
            if (!es4) {
                printText(fcall.alignright(eslab4, 10) + " " + "x" + fcall.alignright(erate4, 10) + fcall.alignright(totales4, 20));
            } else printText(" ");
            if (!rep_subdiv_fec.equals(".00")) {
                printText(fcall.space("   FAC", 6) + ":" + " " + fcall.alignright(conms, 8) + " " + "x" + fcall.alignright(rep_subdiv_fec, 8) + fcall.alignright(rep_data2, 16));
            } else {
                printText(" ");
            }
            printText(fcall.space("   Rebates/TOD", 18) + "(-)" + ":" + " " + fcall.alignright(rebates, 19));
            printText(fcall.space("   PF Penalty", 21) + ":" + " " + fcall.alignright(rep_pfpenalty, 19));
            printText(fcall.space("   MD Penalty", 21) + ":" + " " + fcall.alignright(rep_bmdpenalty, 19));
            printText(fcall.space("   Interest", 18) + "@1%" + ":" + " " + fcall.alignright(intrs, 19));
            printText(fcall.space("   Others", 21) + ":" + " " + fcall.alignright(rep_data1, 19));
            printText(fcall.space("   Tax", 18) + "@6%" + ":" + " " + fcall.alignright(taxs, 19));
            printText(fcall.space("   Cur Bill Amt", 21) + ":" + " " + fcall.alignright(rep_cur_bill, 19));
            printText(fcall.space("   Arrears", 21) + ":" + " " + fcall.alignright(arrs, 19));
            printText(fcall.space("   Credits & Adj", 18) + "(-)" + ":" + " " + fcall.alignright(credits, 19));
            printText(fcall.space("   GOK Subsidy", 18) + "(-)" + ":" + " " + fcall.alignright(gkpays, 19));
            printdoubleText(fcall.space("   Net Amt Due", 21) + ":" + " " + fcall.alignright(rep_payable, 19));
            printText(fcall.space("   Due Date", 21) + ":" + " " + fcall.alignright(duedate, 19));
            printText(fcall.space("   Billed On", 21) + ":" + " " + fcall.alignright(fcall.currentDateandTime(), 19));
            if (!rep_extra1.equals("0"))
                printText(rep_extra1);
            pos.POS_FeedLine();
            printText(fcall.space("  MRCode", 8) + ":" + mrcodes +" "+ rep_mrname);
            pos.POS_S_SetBarcode(rep_consid + rep_payable.substring(0, rep_payable.indexOf('.')), 1, 72, 3, 60, 0, 0);
            printText(fcall.space(" ", 10) + machineid + mrcodes);
            pos.POS_FeedLine();
            pos.POS_FeedLine();
            pos.POS_FeedLine();

            bPrintResult = pos.GetIO().IsOpened();
            return bPrintResult;
        }

		/*private boolean PrintTicket(Context ctx, com.lvrenyang.io.Canvas canvas, int nPrintWidth, int nPrintHeight) {
			boolean bPrintResult = false;

			Typeface tfNumber = Typeface.createFromAsset(getAssets(), "fonts/DroidSansMono.ttf");
			canvas.CanvasBegin(nPrintWidth, nPrintHeight);
			canvas.SetPrintDirection(0);
			int small_font_height = 20;
			int normal_font_height = 24;
			int double_font_height = 32;
			int pre_normal_text_length = 21;
            int pre_double_text_length = 15;
            int pre_bill_text_length = 26;
            int pre_bill_return_text_length = 23;
            int amount_length = 12;

            yaxis = 30;
			printtext(canvas, fcall.centeralign(rep_subdiv, 38), tfNumber, 25);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Sub Division", pre_text_length) + ":" + " " + divcodes, tfNumber, normal_font_height);
			else printtext(canvas, fcall.space(" ", pre_text_length)  + "  " + divcodes, tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_normal_text_length)  + "  " + divcodes, tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("RRNO", pre_text_length) + ":" + " " + rep_rrno, tfNumber, normal_font_height);
			else printtext(canvas, fcall.space(" ", pre_text_length) + "  " + rep_rrno, tfNumber, normal_font_height);*//*
            printboldtext(canvas, fcall.space(" ", pre_double_text_length) + "  " + rep_rrno, tfNumber, double_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printboldtext(canvas, fcall.space("Account ID", pre_text_length) + ":" + " " + rep_consid, tfNumber, normal_font_height);
			else printboldtext(canvas, fcall.space(" ", pre_text_length) + "  " + rep_consid, tfNumber, normal_font_height);*//*
			printboldtext(canvas, fcall.space(" ", pre_double_text_length) + "  " + rep_consid, tfNumber, double_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Mtr.Rdr.Code", pre_text_length) + ":" + " " + mrcodes, tfNumber, normal_font_height);
			else printtext(canvas, fcall.space(" ", pre_text_length) + "  " + mrcodes, tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + mrcodes, tfNumber, normal_font_height);

			//			yaxis = yaxis + 3;
			printtext(canvas, fcall.space(" ", 10) + " ", tfNumber, small_font_height);//Name and Address
			printtext(canvas, customname, tfNumber, small_font_height);
			printtext(canvas, rep_address_1, tfNumber, small_font_height);
			printtext(canvas, rep_address_2, tfNumber, small_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Tariff", pre_text_length) + ":" + " " + tarifn, tfNumber, normal_font_height);
			else printtext(canvas, fcall.space(" ", pre_text_length) + "  " + tarifn, tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + tarifn, tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Sanct Load", pre_text_length) + ":" + " HP:" + fcall.rightalign(load, 5) + String.format("%1s", " ") + "KW:" + fcall.rightalign(kw, 5), tfNumber, normal_font_height);
			else printtext(canvas, fcall.space(" ", pre_text_length) + " " + " HP:" + fcall.rightalign(load, 5) + String.format("%1s", " ") + "KW:" + fcall.rightalign(kw, 5), tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_normal_text_length) + " " + " HP:" + fcall.rightalign(load, 5) + String.format("%1s", " ") + "KW:" + fcall.rightalign(kw, 5), tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Billing Period", 14) + ":" + prevdate + "-" + readdate, tfNumber, normal_font_height);
			else printtext(canvas, fcall.space(" ", 14) + " " + prevdate + "-" + readdate, tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", 14) + fcall.alignright(prevdate + "-" + readdate, 26), tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printboldtext(canvas, fcall.space("Reading Date", pre_text_length) + ":" + " " + readdate, tfNumber, normal_font_height);
			else printboldtext(canvas, fcall.space(" ", pre_text_length) + "  " + readdate, tfNumber, normal_font_height);*//*
			printboldtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + readdate, tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("BillNo", 14) + ":" + " " + rep_consid + "-" + readdate, tfNumber, normal_font_height);
			else printtext(canvas, fcall.space(" ", 14) + "  " + rep_consid + "-" + readdate, tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", 14) + fcall.alignright(rep_consid + "-" + readdate, 26), tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Meter SlNo.", pre_text_length) + ":" + " " + mtrs, tfNumber, normal_font_height);
			else printtext(canvas, fcall.space(" ", pre_text_length) + "  " + mtrs, tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + mtrs, tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printboldtext(canvas, fcall.space("Pres Rdg", pre_text_length) + ":" + " " + rep_presentrdg, tfNumber, normal_font_height);
			else printboldtext(canvas, fcall.space(" ", pre_text_length) + "  " + rep_presentrdg, tfNumber, normal_font_height);*//*
			printboldtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + rep_presentrdg, tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printboldtext(canvas, fcall.space("Prev Rdg", pre_text_length) + ":" + " " + prevrdg, tfNumber, normal_font_height);
            else printboldtext(canvas, fcall.space(" ", pre_text_length) + "  " + prevrdg, tfNumber, normal_font_height);*//*
			printboldtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + prevrdg, tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Constant", pre_text_length) + ":" + " " + mfs, tfNumber, normal_font_height);
            else printtext(canvas, fcall.space(" ", pre_text_length) + "  " + mfs, tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + mfs, tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Consumption", pre_text_length) + ":" + " " + conms, tfNumber, normal_font_height);
            else printtext(canvas, fcall.space(" ", pre_text_length) + "  " + conms, tfNumber, normal_font_height);*//*
            if (!rep_subdiv_fec.equals(".00"))
                printtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + fcall.space(conms, 7) + " " + String.format("%9s", fcall.rightalign("FAC", 9)), tfNumber, normal_font_height);
            else printtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + conms, tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Average", pre_text_length) + ":" + " " + avgcons, tfNumber, normal_font_height);
			else printtext(canvas, fcall.space(" ", pre_text_length) + "  " + avgcons, tfNumber, normal_font_height);*//*
            if (!rep_subdiv_fec.equals(".00"))
                printtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + fcall.space(avgcons, 7) + " " + String.format("%9s", fcall.rightalign(rep_data2, 9)), tfNumber, normal_font_height);
            else printtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + avgcons, tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			if (rep_pfflag.equals("2") || rep_pfflag.equals("1")) {
				*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
					printtext(canvas, fcall.space("Recorded MD", pre_text_length) + ":" + " " + rep_bmdvalue, tfNumber, normal_font_height);
				else printtext(canvas, fcall.space(" ", pre_text_length) + "  " + rep_bmdvalue, tfNumber, normal_font_height);*//*
				printtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + rep_bmdvalue, tfNumber, normal_font_height);
				yaxis = yaxis + 3;
				*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
					printtext(canvas, fcall.space("Power Factor", pre_text_length) + ":" + " " + rep_pfvalue, tfNumber, normal_font_height);
				else printtext(canvas, fcall.space(" ", pre_text_length) + "  " + rep_pfvalue, tfNumber, normal_font_height);*//*
				printtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + rep_pfvalue, tfNumber, normal_font_height);
			} else if (rep_pfflag.equals("0")) {
				if (Double.parseDouble(rep_bmdvalue) > 0) {
					*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
						printtext(canvas, fcall.space("Recorded MD", pre_text_length) + ":" + " " + rep_bmdvalue, tfNumber, normal_font_height);
					else printtext(canvas, fcall.space(" ", pre_text_length) + "  " + rep_bmdvalue, tfNumber, normal_font_height);*//*
					printtext(canvas, fcall.space(" ", pre_normal_text_length) + "  " + rep_bmdvalue, tfNumber, normal_font_height);
				} else {
					printtext(canvas, " ", tfNumber, normal_font_height);
				}
				yaxis = yaxis + 3;
				printtext(canvas, " ", tfNumber, normal_font_height);
			}
			yaxis = yaxis + 3;
			printtext(canvas, fcall.centeralign(" ", 38), tfNumber, small_font_height);//Fixed Charges
			yaxis = yaxis + 3;
			if (!fs1) {
				printtext(canvas, String.format("%9s", fcall.rightalign(fslab1, 8)) + " " + "x" + String.format("%9s", fcall.rightalign(frate1, 8)) + String.format("%20s", fcall.rightalign(totalfs1, 14)), tfNumber, normal_font_height);
			} else printtext(canvas, " ", tfNumber, normal_font_height);
			if (!fs2) {
				printtext(canvas, String.format("%9s", fcall.rightalign(fslab2, 8)) + " " + "x" + String.format("%9s", fcall.rightalign(frate2, 8)) + String.format("%20s", fcall.rightalign(totalfs2, 14)), tfNumber, normal_font_height);
			} else printtext(canvas, " ", tfNumber, normal_font_height);
			yaxis = yaxis + 3;
			printtext(canvas, fcall.centeralign(" ", 38), tfNumber, small_font_height);//Energy Charges
			yaxis = yaxis + 3;
			if (!es1) {
				printtext(canvas, String.format("%9s", fcall.rightalign(eslab1, 8)) + " " + "x" + String.format("%9s", fcall.rightalign(erate1, 8)) + String.format("%20s", fcall.rightalign(totales1, 14)), tfNumber, normal_font_height);
			} else printtext(canvas, " ", tfNumber, normal_font_height);
			if (!es2) {
				printtext(canvas, String.format("%9s", fcall.rightalign(eslab2, 8)) + " " + "x" + String.format("%9s", fcall.rightalign(erate2, 8)) + String.format("%20s", fcall.rightalign(totales2, 14)), tfNumber, normal_font_height);
			} else printtext(canvas, " ", tfNumber, normal_font_height);
			if (!es3) {
				printtext(canvas, String.format("%9s", fcall.rightalign(eslab3, 8)) + " " + "x" + String.format("%9s", fcall.rightalign(erate3, 8)) + String.format("%20s", fcall.rightalign(totales3, 14)), tfNumber, normal_font_height);
			} else printtext(canvas, " ", tfNumber, normal_font_height);
			if (!es4) {
				printtext(canvas, String.format("%9s", fcall.rightalign(eslab4, 8)) + " " + "x" + String.format("%9s", fcall.rightalign(erate4, 7)) + String.format("%20s", fcall.rightalign(totales4, 14)), tfNumber, normal_font_height);
			} else printtext(canvas, " ", tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Rebates/TOD", 24) + ":" + " " + String.format("%14s", fcall.rightalign(rebates, 12)), tfNumber, normal_font_height);
			else printtext(canvas, fcall.space(" ", 24) + "  " + String.format("%14s", fcall.rightalign(rebates, 12)), tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_bill_return_text_length) + "(-)" + String.format("%14s", fcall.rightalign(rebates, amount_length)), tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("PF Penalty", 24) + ":" + " " + String.format("%14s", fcall.rightalign(rep_pfpenalty, 12)), tfNumber, normal_font_height);
			else printtext(canvas, fcall.space(" ", 24) + "  " + String.format("%14s", fcall.rightalign(rep_pfpenalty, 12)), tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_bill_text_length) + String.format("%14s", fcall.rightalign(rep_pfpenalty, amount_length)), tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("MD Penalty", 24) + ":" + " " + String.format("%14s", fcall.rightalign(rep_bmdpenalty, 12)), tfNumber, normal_font_height);
            else printtext(canvas, fcall.space(" ", 24) + "  " + String.format("%14s", fcall.rightalign(rep_bmdpenalty, 12)), tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_bill_text_length) + String.format("%14s", fcall.rightalign(rep_bmdpenalty, amount_length)), tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Interest", 24) + ":" + " " + String.format("%14s", fcall.rightalign(intrs, 12)), tfNumber, normal_font_height);
            else printtext(canvas, fcall.space(" ", 24) + "  " + String.format("%14s", fcall.rightalign(intrs, 12)), tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_bill_text_length) + String.format("%14s", fcall.rightalign(intrs, amount_length)), tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Others", 24) + ":" + " " + String.format("%14s", fcall.rightalign(rep_data1, 12)), tfNumber, normal_font_height);
            else printtext(canvas, fcall.space(" ", 24) + "  " + String.format("%14s", fcall.rightalign(rep_data1, 12)), tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_bill_text_length) + String.format("%14s", fcall.rightalign(rep_data1, amount_length)), tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Tax", 24) + ":" + " " + String.format("%14s", fcall.rightalign(taxs, 12)), tfNumber, normal_font_height);
            else printtext(canvas, fcall.space(" ", 24) + "  " + String.format("%14s", fcall.rightalign(taxs, 12)), tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_bill_text_length) + String.format("%14s", fcall.rightalign(taxs, amount_length)), tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Cur Bill Amt", 24) + ":" + " " + String.format("%14s", fcall.rightalign(billamnts, 12)), tfNumber, normal_font_height);
            else printtext(canvas, fcall.space(" ", 24) + "  " + String.format("%14s", fcall.rightalign(billamnts, 12)), tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_bill_text_length) + String.format("%14s", fcall.rightalign(rep_cur_bill, amount_length)), tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Arrears", 24) + ":" + " " + String.format("%14s", fcall.rightalign(arrs, 12)), tfNumber, normal_font_height);
            else printtext(canvas, fcall.space(" ", 24) + "  " + String.format("%14s", fcall.rightalign(arrs, 12)), tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_bill_text_length) + String.format("%14s", fcall.rightalign(arrs, amount_length)), tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Credits & Adj", 24) + ":" + " " + String.format("%14s", fcall.rightalign(credits, 12)), tfNumber, normal_font_height);
            else printtext(canvas, fcall.space(" ", 24) + "  " + String.format("%14s", fcall.rightalign(credits, 12)), tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_bill_return_text_length) + "(-)" + String.format("%14s", fcall.rightalign(credits, amount_length)), tfNumber, normal_font_height);
			yaxis = yaxis + 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("GOK Subsidy", 24) + ":" + " " + String.format("%14s", fcall.rightalign(gkpays, 12)), tfNumber, normal_font_height);
			else printtext(canvas, fcall.space(" ", 24) + "  " + String.format("%14s", fcall.rightalign(gkpays, 12)), tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_bill_return_text_length) + "(-)" + String.format("%14s", fcall.rightalign(gkpays, amount_length)), tfNumber, normal_font_height);
			yaxis = yaxis - 3;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printboldtext(canvas, fcall.space("Net Amt Due", 16) + ":" + " " + String.format("%13s", fcall.rightalign(rep_payable, 12)), tfNumber, double_font_height);
            else printboldtext(canvas, fcall.space(" ", 16) + "  " + String.format("%13s", fcall.rightalign(rep_payable, 12)), tfNumber, double_font_height);*//*
			printboldtext(canvas, fcall.space(" ", pre_double_text_length) + "  " + String.format("%13s", fcall.rightalign(rep_payable, amount_length)), tfNumber, double_font_height);
			yaxis = yaxis + 2;

			*//*if (StringUtils.startsWithIgnoreCase(rep_preprint, "n"))
				printtext(canvas, fcall.space("Due Date", 24) + ":" + " " + String.format("%14s", fcall.rightalign(duedate, 12)), tfNumber, normal_font_height);
			else printtext(canvas, fcall.space(" ", 24) + "  " + String.format("%14s", fcall.rightalign(duedate, 12)), tfNumber, normal_font_height);*//*
			printtext(canvas, fcall.space(" ", pre_bill_text_length) +"  "+ fcall.alignright(duedate, amount_length), tfNumber, normal_font_height);
            if (!rep_extra1.equals("0")) {
                yaxis = yaxis + 2;
                printtext(canvas, rep_extra1, tfNumber, small_font_height);
            }
			yaxis = yaxis + 10;
			canvas.DrawBitmap(barcode, 0, yaxis, 0);
			canvas.DrawText(barcodespace(rep_barcodevalue) + "\r\n", 0, yaxis + 45, 0, tfNumber, 12, com.lvrenyang.io.Canvas.DIRECTION_LEFT_TO_RIGHT);
            canvas.CanvasPrint(1, 0);

			bPrintResult = canvas.GetIO().IsOpened();
			return bPrintResult;
		}*/

        private void printText(String msg) {
            pos.POS_S_TextOut(msg + "\r\n", 0, 0, 0, 0, 4);
        }

        private void printdoubleText(String msg) {
            pos.POS_S_TextOut(msg + "\r\n", 0, 0, 1, 0, 4);
        }
	}

	private void printtext(com.lvrenyang.io.Canvas canvas, String text, Typeface tfNumber, float textsize) {
		yaxis++;
		canvas.DrawText(text + "\r\n", 0, yaxis, 0, tfNumber, textsize, com.lvrenyang.io.Canvas.DIRECTION_LEFT_TO_RIGHT);
		if (textsize == 20) {
			yaxis = yaxis + textsize + 8;
		} else yaxis = yaxis + textsize + 6;
	}

	private void printboldtext(com.lvrenyang.io.Canvas canvas, String text, Typeface tfNumber, float textsize) {
		yaxis++;
		canvas.DrawText(text + "\r\n", 0, yaxis, 0, tfNumber, textsize, com.lvrenyang.io.Canvas.FONTSTYLE_BOLD);
		if (textsize == 20) {
			yaxis = yaxis + textsize + 8;
		} else yaxis = yaxis + textsize + 6;
	}

	private String barcodespace(String text) {
		int count = text.length();
		int value = 42 - count;
		int append = (value / 2) + 10;
		return fcall.space(" ", append) + text;
	}

	private void fulldetailsbyid() {
		c = dbh.reportbyid(custid);
		if (c != null) {
			c.moveToNext();
			try {
				getvalue();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(Reportdisp.this, "No Records Found in Billed Table", Toast.LENGTH_SHORT).show();
		}


		//Checking Present Status
		c1 = dbh.presentstatusbyid(custid);
		if (c1 != null) {
			c1.moveToFirst();
			prstat1 = c1.getString(c1.getColumnIndex("STATUS_LABEL"));
            if (prstat1.length() > 8)
                prstat1 = prstat1.substring(0, 8);
		} else {
			Toast.makeText(Reportdisp.this, "No Records Found in Billed Table", Toast.LENGTH_SHORT).show();
		}

		//Checking Previous Status
		c2 = dbh.previousstatusbyid(custid);
		if (c2 != null) {
			c2.moveToFirst();
			prstat2 = c2.getString(c2.getColumnIndex("STATUS_LABEL"));
            if (prstat2.length() > 8)
                prstat2 = prstat2.substring(0, 8);
		} else {
			Toast.makeText(Reportdisp.this, "No Records Found in Billed Table", Toast.LENGTH_SHORT).show();
		}
		getallsetvalues();
	}

	private void fulldetailsbyrrno() {
		c = dbh.report(custrrno);
		if (c != null) {
			c.moveToNext();
			try {
				getvalue();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (CursorIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(Reportdisp.this, "No Records Found in Billed Table", Toast.LENGTH_SHORT).show();
		}

		//Checking Present Status
		c1 = dbh.presentstatusbyrrno(custrrno);
		if (c1 != null) {
			c1.moveToFirst();
			prstat1 = c1.getString(c1.getColumnIndex("STATUS_LABEL"));
            if (prstat1.length() > 8)
                prstat1 = prstat1.substring(0, 8);
		} else {
			Toast.makeText(Reportdisp.this, "No Records Found in Billed Table", Toast.LENGTH_SHORT).show();
		}

		//Checking Previous Status
		c2 = dbh.previousstatusbyrrno(custrrno);
		if (c2 != null) {
			c2.moveToFirst();
			prstat2 = c2.getString(c2.getColumnIndex("STATUS_LABEL"));
            if (prstat2.length() > 8)
                prstat2 = prstat2.substring(0, 8);
		} else {
			Toast.makeText(Reportdisp.this, "No Records Found in Billed Table", Toast.LENGTH_SHORT).show();
		}
		getallsetvalues();
	}

	private void fulldetailsbyname() {
		c = dbh.reportbyname(custname);
		if (c != null) {
			c.moveToNext();
			try {
				getvalue();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(Reportdisp.this, "No Records Found in Billed Table", Toast.LENGTH_SHORT).show();
		}

		//Checking Present Status
		c1 = dbh.presentstatusbyname(custname);
		if (c1 != null) {
			c1.moveToFirst();
			prstat1 = c1.getString(c1.getColumnIndex("STATUS_LABEL"));
            if (prstat1.length() > 8)
                prstat1 = prstat1.substring(0, 8);
		} else {
			Toast.makeText(Reportdisp.this, "No Records Found in Billed Table", Toast.LENGTH_SHORT).show();
		}

		//Checking Previous Status
		c2 = dbh.previousstatusbyname(custname);
		if (c2 != null) {
			c2.moveToFirst();
			prstat2 = c2.getString(c2.getColumnIndex("STATUS_LABEL"));
            if (prstat2.length() > 8)
                prstat2 = prstat2.substring(0, 8);
		} else {
			Toast.makeText(Reportdisp.this, "No Records Found in Billed Table", Toast.LENGTH_SHORT).show();
		}
		getallsetvalues();
	}

	private void getallsetvalues() {

		//Taking Substring for Customer name
		if (rep_consname.length() > 16) {
			customname = rep_consname.substring(0, 16);
		} else {
			customname = rep_consname;
		}

		address(rep_address);

		TelephonyManager mobileid = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		machineid = mobileid.getDeviceId();
	  	
	  	//Sending values for calculation
	  	setvaluesforcalculation();
	  		
	  	//Getting array values for calculation from ClassCalculation Activity
	  	getcalculationarrayvalues();
	  		
	  	//Converting Calculation values for doubles to String
	  	converttostring();
	  		
	  	//Getting all Calculation values
	  	getcalculationvalue();
	  		
	  	//Making boolean values for calculation values
	  	booleanvalues();
	  		
	  	//setting all Final values to textviews
	  	setvalue();
	}
	
	private void settextviewtonull() {
		tvsubd.setText("");			tvrrno.setText("");			tvlegf.setText("");
		tvtariff.setText("");		tvname.setText("");			tvaddr.setText("");
		tvconsno.setText("");		tvbillno.setText("");		tvmtr.setText("");
		tvlinem.setText("");		tvload.setText("");			tvmf.setText("");
		tvkw.setText("");			tvdate1.setText("");		tvdate2.setText("");
		tvcur.setText("");			tvprv.setText("");			tvfr.setText("");
		tvir.setText("");			tvmnth1.setText("");		tvmnth2.setText("");
		tvstat1.setText("");		tvstat2.setText("");		tvconm.setText("");
		tvavgcon.setText("");		tvfc.setText("");			tvec.setText("");
		tvgkpay.setText("");		tvrebate.setText("");		tvbillamnt.setText("");
		tvarr.setText("");			tvintr.setText("");			tvintrtv.setText("");
		tvtaxe.setText("");			tvtax.setText("");			tvcredit.setText("");
		tvmrcode.setText("");		tvtel.setText("");			tvintd.setText("");
		tvasd.setText("");			tviod.setText("");			tvpay.setText("");
		tvdue.setText("");			tvcity.setText("");			tvprint.setText("");
		tvhelp.setText("");			tvmachine.setText("");
		tvver.setText("");			tvdivcode.setText("");		tvfslab1.setText("");
		tvfslab2.setText("");		tvfslab3.setText("");		tvfrate1.setText("");
		tvfrate2.setText("");		tvfrate3.setText("");		tveslab1.setText("");
		tveslab2.setText("");		tveslab3.setText("");		tveslab4.setText("");
		tverate1.setText("");		tverate2.setText("");		tverate3.setText("");
		tverate4.setText("");		tvtfs1.setText("");			tvtfs2.setText("");
		tvtfs3.setText("");			tvtes1.setText("");			tvtes2.setText("");
		tvtes3.setText("");			tvtes4.setText("");			tvst1.setText("");
		tvst2.setText("");			tvst3.setText("");			tvst4.setText("");
		tvst5.setText("");			tvst6.setText("");			tvst7.setText("");
		tveq1.setText("");			tveq2.setText("");			tveq3.setText("");
		tveq4.setText("");			tveq5.setText("");			tveq6.setText("");
		tveq7.setText("");			tvsearch.setText("Click Search icon for Reports");
		rep_rrno = "";
	}
	
	private void onexit() {
		if (!rep_display.equals("billing") || rep_display.equals("")
				|| !rep_display.equals("bill_status") || !rep_display.equals("check_bill_printed")) {
			if (StringUtils.startsWithIgnoreCase(rep_report, "Billed")) {
				finish();
			}
			else {
				Intent back = new Intent(Reportdisp.this, ConsumerBilling.class);
				back.putExtra("status", billing_status);
				back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(back);
			}
		}
		if (rep_display.equals("billing")) {
			if (ngxconnected) {
				ngxconnected = false;
				Intent back1 = new Intent(Reportdisp.this, ConsumerBilling.class);
				back1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(back1);
			} else if (analogics) {
				analogics = false;
				Intent back1 = new Intent(Reportdisp.this, ConsumerBilling.class);
				back1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(back1);
			} else if (goojprt) {
				goojprt = false;
				Intent back1 = new Intent(Reportdisp.this, ConsumerBilling.class);
				back1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(back1);
			} else {
                bill_removed = true;
				c9 = dbh.deletebillingrow(rep_consid);
				c9.moveToNext();
				Intent back1 = new Intent(Reportdisp.this, ConsumerBilling.class);
				back1.putExtra("status", billing_status);
				back1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(back1);
			}
		}
		if (rep_statusbilling.equals("Yes")) {
            if (!bill_removed) {
                if (StringUtils.startsWithIgnoreCase(rep_status, "NOT BILLED")) {
                    Intent notbilled = new Intent(Reportdisp.this, Billstatusext1.class);
                    notbilled.putExtra("status", rep_status);
                    notbilled.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(notbilled);
                } else {
                    Intent back2 = new Intent(Reportdisp.this, Billstatusext.class);
                    back2.putExtra("Report", "Billreport");
                    back2.putExtra("status", rep_status);
                    back2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(back2);
                }
            }
		}
	/*	if (rep_display.equals("normalbilled")) {
			Intent back2 = new Intent(Reportdisp.this, Billstatusext.class);
			back2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			back2.putExtra("Reportstatus", "Billreport");
			back2.putExtra("status", "BILLED");
			back2.putExtra("summart", "BILLED");
			startActivity(back2);
		}*/
		if (rep_display.equals("check_bill_printed")) {
			Intent back3 = new Intent(Reportdisp.this, BillsnotPrinted.class);
			back3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(back3);
		}
		if (rep_display.equals("tariffwisebilled")) {
			Intent back4 = new Intent(Reportdisp.this, Tariffwisebilled.class);
			back4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(back4);
		}
	}
	
	private void hidekeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
	}
	
	@Override
	public void onBackPressed() {
		cc.resetAllValues();
		onexit();
		super.onBackPressed();
	}
	
	private class Updatedata extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			try {
				rep_updateonline = invoke.invokeSendBillDetails("SENDBILLDETAILS", NAMESPACE, SOAP_ACTION, URL, 
						rep_month, "MONTH", rep_readdate, "READDATE", rep_rrno, "RRNO", mrcodes, "MRCODE", rep_ssno, "SSNO", 
						rep_consid, "CONSNO", rep_phno, "PH_NO", ""+asds1, "ASDAMT", ""+iods1, "IODAMT", ""+rep_pfvalue1, "PFVAL", 
						rep_bmdvalue, "BMDVAL", ""+intrs1, "INTEREST_AMT", rep_billdays, "BILL_DAYS", rep_tccode, "TCCODE", 
						rep_presentrdg, "PRES_RDG", rep_pres_sts, "PRES_STS", conms, "UNITS", ""+fix1, "FIX", ""+ecs1, "ENGCHG", 
						""+rebates1, "REBATE_AMOUNT", ""+taxs1, "TAX_AMOUNT", ""+rep_bmdpenalty1, "BMD_PENALTY", 
						""+rep_pfpenalty1, "PF_PENALTY", rep_payable, "PAYABLE", printdt, "BILLDATE", printtime, "BILLTIME", 
						rep_todonpeak, "TOD_CURRENT1", rep_todoffpeak, "TOD_CURRENT3", ""+gkpays1, "GOK_SUBSIDY", 
						billamnts3, "DEM_REVENUE", rep_gpslat, "GPS_LAT", rep_gpslong, "GPS_LONG", rep_image, "IMGADD", 
						rep_payreal, "PAYABLE_REAL", rep_payprofit, "PAYABLE_PROFIT", 
						rep_payloss, "PAYABLE_LOSS");
			} catch (Exception e) {
                e.printStackTrace();
			}
			return null;
		}
	}

	private void showdialog(int id) {
		switch (id) {
			case PRINT_DLG:
				final AlertDialog printdlg = new AlertDialog.Builder(this).create();
				printdlg.setTitle("Print");
                printdlg.setCancelable(false);
				LinearLayout ab1linear = (LinearLayout) getLayoutInflater().inflate(R.layout.checkprinted, null);
				printdlg.setView(ab1linear);
				final TextView print_msg = (TextView) ab1linear.findViewById(R.id.textView1);
				Button yes_btn = (Button) ab1linear.findViewById(R.id.button1);
				Button no_btn = (Button) ab1linear.findViewById(R.id.button2);
				no_btn.setText("NO");
				print_msg.setText(getResources().getString(R.string.askprint));
				yes_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (BluetoothService.printerconnected) {
							printdlg.dismiss();
							printing = ProgressDialog.show(Reportdisp.this, "Printing", "Printing Please wait to Complete");
                            DecimalFormat num = new DecimalFormat("##.00");
                            Double bill = Double.parseDouble(billamnts)+Double.parseDouble(taxs)+Double.parseDouble(rep_data1)
                                    +Double.parseDouble(intrs)+Double.parseDouble(rep_data2);
                            if (rep_tariff.equals("10") || rep_tariff.equals("40")) {
                                rep_cur_bill = num.format(bill+Double.parseDouble(totalec));
                            } else rep_cur_bill = num.format(bill);
                            if (rep_rebateflag.equals("1")) {
                                rep_cur_bill = num.format(Double.parseDouble(rep_cur_bill) - rebates1);
                            }
                            if (rep_rebateflag.equals("5")) {
                                rep_cur_bill = num.format(Double.parseDouble(rep_cur_bill) + Double.parseDouble(gkpays));
                            }
                            if (rep_tariff.equals("23")) {
                                rep_cur_bill = num.format(Double.parseDouble(rep_cur_bill) + Double.parseDouble(gkpays));
                            }
                            if (rep_tariff.equals("31")) {
                                gkpays = rep_cur_bill;
                                rep_payable = num.format(Double.parseDouble(rep_cur_bill) - Double.parseDouble(gkpays)
                                        + Double.parseDouble(arrs) - Double.parseDouble(credits));
                            }
                            String address = "", regex = "";
                            switch (rep_bluprinter) {
                                case "NGX":
                                    ngxconnected = true;
                                    address = rep_address;
                                    regex = "a-z~@#$%^&*:;<>.,/}{+";
                                    if (address.substring(0, 1).matches("[" + regex + "]+")){
                                        splchar = address.substring(0, 1);
                                    }
                                    fcall.splitString(address, 47, addresslist);
                                    rep_address_1 = addresslist.get(0);
                                    if (addresslist.size() > 1) {
                                        rep_address_2 = addresslist.get(1);
                                    }
                                    printngx();

                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            printing.dismiss();
                                            showdialog(EXIT_DLG);
                                        }
                                    }, 5000);
                                    break;

                                case "ALG":
                                    analogics = true;
                                    address = rep_address;
                                    regex = "a-z~@#$%^&*:;<>.,/}{+";
                                    if (address.substring(0, 1).matches("[" + regex + "]+")){
                                        splchar = address.substring(0, 1);
                                    }
                                    fcall.splitString(address, 47, addresslist);
                                    rep_address_1 = addresslist.get(0);
                                    if (addresslist.size() > 1) {
                                        rep_address_2 = addresslist.get(1);
                                    }
                                    printanalogics();

                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            printing.dismiss();
                                            showdialog(EXIT_DLG);
                                        }
                                    }, 3000);
                                    break;

                                case "GPT":
                                    goojprt = true;
                                    address = rep_address;
                                    regex = "a-z~@#$%^&*:;<>.,/}{+";
                                    if (address.substring(0, 1).matches("[" + regex + "]+")){
                                        splchar = address.substring(0, 1);
                                    }
                                    fcall.splitString(address, 43, addresslist);
                                    rep_barcodevalue = rep_consid+rep_payable.substring(0, rep_payable.indexOf('.'));
                                    barcode = fcall.getBitmap(rep_barcodevalue, 1, 450, 45);
                                    rep_address_1 = "  "+addresslist.get(0);
                                    if (addresslist.size() > 1) {
                                        rep_address_2 = "  "+addresslist.get(1);
                                    }
                                    es.submit(new TaskPrint(mPos));
                                    break;
                            }
						}
					}
				});
				no_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						printdlg.dismiss();
                        if (rep_statusbilling.equals("Yes")) {
                            if (StringUtils.startsWithIgnoreCase(rep_status, "NOT BILLED")) {
                                Intent notbilled = new Intent(Reportdisp.this, Billstatusext1.class);
                                notbilled.putExtra("status", rep_status);
                                notbilled.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(notbilled);
                            } else {
                                Intent back2 = new Intent(Reportdisp.this, Billstatusext.class);
                                back2.putExtra("Report", "Billreport");
                                back2.putExtra("status", rep_status);
                                back2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(back2);
                            }
                        } else {
                            Intent back1 = new Intent(Reportdisp.this, ConsumerBilling.class);
                            back1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(back1);
                        }
					}
				});
				printdlg.show();
				break;

			case PAIR_DLG:
				final AlertDialog.Builder pairdlg = new AlertDialog.Builder(this);
				pairdlg.setTitle("Device Connection");
                pairdlg.setCancelable(false);
				pairdlg.setMessage("Printer is not connected to get print..");
				pairdlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
                        Intent back1 = new Intent(Reportdisp.this, ConsumerBilling.class);
                        back1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(back1);
					}
				});
				AlertDialog dialog = pairdlg.create();
				dialog.show();
				break;

			case EXIT_DLG:
				final AlertDialog exitdlg = new AlertDialog.Builder(this).create();
				exitdlg.setTitle("Billing");
                exitdlg.setCancelable(false);
				LinearLayout rl = (LinearLayout) getLayoutInflater().inflate(R.layout.checkprinted, null);
				exitdlg.setView(rl);
				final TextView exit_txt = (TextView) rl.findViewById(R.id.textView1);
				exit_txt.setText(getResources().getString(R.string.exitbilling));
				Button exityes_btn = (Button) rl.findViewById(R.id.button1);
				Button exitreprint_btn = (Button) rl.findViewById(R.id.button2);
				exityes_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						onexit();
					}
				});
				exitreprint_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						exitdlg.dismiss();
						printing = ProgressDialog.show(Reportdisp.this, "Printing",
								"Printing Please wait to Complete");
						if (!Build.MANUFACTURER.matches("alps")) {
							switch (rep_bluprinter) {
								case "NGX":
									ngxconnected = true;
									if (rep_preprint.equals("N") || rep_preprint.equals("n")) {
										printngx();
									} else printngx();
									new Handler().postDelayed(new Runnable() {

										@Override
										public void run() {
											printing.dismiss();
											showdialog(EXIT_DLG);
										}
									}, 5000);
									break;

								case "ALG":
									analogics = true;
									printanalogics();
									new Handler().postDelayed(new Runnable() {

										@Override
										public void run() {
											printing.dismiss();
											showdialog(EXIT_DLG);
										}
									}, 3000);
									break;

								case "GPT":
									goojprt = true;
//									es.submit(new TaskPrint(mCanvas));
									es.submit(new TaskPrint(mPos));
									break;
							}
						} else {
							printalps();
						}
					}
				});
				exitdlg.show();
				break;

            case UPDATE_DLG:
                AlertDialog.Builder update_dlg = new AlertDialog.Builder(this);
                update_dlg.setCancelable(false);
                update_dlg.setTitle("Update Records");
                ScrollView update_layout = (ScrollView) getLayoutInflater().inflate(R.layout.extraparameters, null);
                update_dlg.setView(update_layout);
                final EditText et_phno = (EditText) update_layout.findViewById(R.id.et_extra_mobile_number);
                et_phno.setText(rep_phno);
                et_phno.setSelection(et_phno.getText().length());
                final EditText et_aad_no = (EditText) update_layout.findViewById(R.id.et_extra_aadhaar_number);
                et_aad_no.setText(rep_aadhaar_no);
                et_aad_no.setSelection(et_aad_no.getText().length());
                final EditText et_mail_id = (EditText) update_layout.findViewById(R.id.et_extra_email_address);
                et_mail_id.setText(rep_mail_id);
                et_mail_id.setSelection(et_mail_id.getText().length());
                final EditText et_election_no = (EditText) update_layout.findViewById(R.id.et_election_card_no);
                et_election_no.setText(rep_election_no);
                et_election_no.setSelection(et_election_no.getText().length());
                final EditText et_ration_no = (EditText) update_layout.findViewById(R.id.et_ration_card_no);
                et_ration_no.setText(rep_ration_no);
                et_ration_no.setSelection(et_ration_no.getText().length());
                final EditText et_water_no = (EditText) update_layout.findViewById(R.id.et_water_card_no);
                et_water_no.setText(rep_water_conn_no);
                et_water_no.setSelection(et_water_no.getText().length());
                final EditText et_house_no = (EditText) update_layout.findViewById(R.id.et_house_no);
                et_house_no.setText(rep_house_no);
                et_house_no.setSelection(et_house_no.getText().length());
                update_dlg.setPositiveButton("UPDATE", null);
                update_dlg.setNegativeButton("SKIP", null);
                final AlertDialog dlg_update = update_dlg.create();
                dlg_update.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button positive = dlg_update.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button negative = dlg_update.getButton(AlertDialog.BUTTON_NEGATIVE);
                        positive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rep_phno = et_phno.getText().toString();
                                if (!validate_Ph_no(rep_phno)) {
                                    et_phno.setError("Enter Valid Phone number...");
                                    return;
                                }
                                rep_aadhaar_no = et_aad_no.getText().toString();
                                if (!validate_Aad_no(rep_aadhaar_no)) {
                                    et_aad_no.setError("Enter Valid Aadhaar Number...");
                                    return;
                                }
                                rep_mail_id = et_mail_id.getText().toString();
                                if (!validate_mail_id(rep_mail_id)) {
                                    et_mail_id.setError("Enter Valid Mail Id...");
                                    return;
                                }
                                rep_election_no = et_election_no.getText().toString();
                                rep_ration_no = et_ration_no.getText().toString();
                                rep_water_conn_no = et_water_no.getText().toString();
                                rep_house_no = et_house_no.getText().toString();
                                dbh.update_Extra_Billing(rep_phno, rep_aadhaar_no, rep_mail_id, rep_election_no, rep_water_conn_no,
                                        rep_ration_no, rep_house_no);
                                dlg_update.dismiss();
                                if (rep_display.equals("billing")) {
                                    updating_billing_record();
                                }
                                showdialog(PRINT_DLG);
                                cc.resetAllValues();
                            }
                        });
                        negative.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dlg_update.dismiss();
                                if (rep_display.equals("billing")) {
                                    updating_billing_record();
                                }
                                showdialog(PRINT_DLG);
                                cc.resetAllValues();
                            }
                        });
                    }
                });
                dlg_update.show();
                break;
		}
	}

	private boolean validate_Ph_no(String ph_no) {
		return ph_no.length() == 10 || TextUtils.isEmpty(ph_no);
	}

	private boolean validate_Aad_no(String aad_no) {
		return aad_no.length() == 12 || TextUtils.isEmpty(aad_no);
	}

	private boolean validate_mail_id(String mail_id) {
		return (TextUtils.isEmpty(mail_id) || Patterns.EMAIL_ADDRESS.matcher(mail_id).matches());
	}

	public Bitmap textAsBitmap(String text, float textSize, float stroke, int color, Typeface typeface, Layout.Alignment alignment) {
		TextPaint paint = new TextPaint();
		paint.setColor(color);
		paint.setDither(false);
		paint.setTextSize(textSize);
		paint.setStrokeWidth(stroke);
		paint.setTypeface(typeface);
		paint.setAntiAlias(true);
		paint.setTextAlign(Paint.Align.LEFT);
		float baseline = (float)((int)(-paint.ascent() + 1.0F));
		StaticLayout staticLayout = new StaticLayout(text, 0, text.length(), paint, 576, alignment, 1.0F, 1.0F, false);
		int linecount = staticLayout.getLineCount();
		int height = (int)(baseline + paint.descent()) * linecount;
		Bitmap image = Bitmap.createBitmap(576, height, Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(image);
		staticLayout.draw(canvas);
		return image;
	}

	public void analogicsprint(String address, String Printdata, int text_size, Typeface typeface, Layout.Alignment alignment) {
		AnalogicsUtil utils = new AnalogicsUtil();
        /*Bluetooth_Printer_3inch_prof_ThermalAPI api = new Bluetooth_Printer_3inch_prof_ThermalAPI();
        conn.printData(api.font_Courier_38_VIP(Printdata));*/
		Bitmap bmp = textAsBitmap(Printdata, (float)text_size, 9.0F, -16711681, typeface, alignment);
		try {
			Bitmap e = bmp.copy(Bitmap.Config.ARGB_4444, true);
			byte[] c1 = utils.prepareDataToPrint(address, e);
			conn.printData(c1);
		} catch (InterruptedException var13) {
			var13.printStackTrace();
		}
	}
	
}