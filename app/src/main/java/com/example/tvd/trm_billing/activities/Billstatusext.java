package com.example.tvd.trm_billing.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.analogics.thermalAPI.Bluetooth_Printer_3inch_prof_ThermalAPI;
import com.analogics.thermalprinter.AnalogicsThermalPrinter;
import com.example.tvd.trm_billing.R;
import com.example.tvd.trm_billing.Reportdisp;
import com.example.tvd.trm_billing.database.Databasehelper;
import com.example.tvd.trm_billing.services.BluetoothService;
import com.example.tvd.trm_billing.values.FunctionCalls;
import com.example.tvd.trm_billing.values.GetSetValues;
import com.lvrenyang.io.Canvas;
import com.ngx.BluetoothPrinter;
import com.ngx.PrinterWidth;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static android.text.Layout.Alignment.ALIGN_CENTER;
import static android.text.Layout.Alignment.ALIGN_NORMAL;

public class Billstatusext extends Activity {
    public static final int SHOW_DLG = 1;
    public static final int PRINT_DLG = 2;
    public static final int PAIR_DLG = 3;
    public static final int EXIT_DLG = 4;

    TextView tvsts;
    String stsname, summary, billreport, BilledRRNo, billing_acct_id="";
    String rrno, custid, name, units, payable, billstatus, billstatus_printer="", billed_ir="", billed_fr="", billed_subdiv="", billed_mr=""
            , billed_date="";
    double payable1, units1;
    String[] adapstr;
    Databasehelper dbh;
    Cursor c1, c2, printcursor;
    ArrayList<String> list1, list2, list3, list4, list5, list6;
    ArrayList<Map<String, String>> mylist;
    HashMap<String, String> map;
    ListView lv;
    SimpleAdapter sa;
    int slno=0, phiPrinter_height=0;
    float yaxis = 0;
    int[] texts;
    FunctionCalls fcall;
    GetSetValues getSetValues;
    ArrayList<GetSetValues> billed_list;
    Double total_payable=0.0;
    DecimalFormat num;
    StringBuilder stringbuilder, stringbuilder_2, stringbuilder_3, stringbuilder_4, stringbuilder_5;

    ProgressDialog printing;
    BluetoothPrinter mBtp;
    AnalogicsThermalPrinter conn = BluetoothService.conn;
    Canvas mCanvas = BluetoothService.mCanvas;
    ExecutorService es = BluetoothService.es;
    Bluetooth_Printer_3inch_prof_ThermalAPI api;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billstatusext);

        tvsts = (TextView) findViewById(R.id.textView1);
        fcall = new FunctionCalls();
        getSetValues = new GetSetValues();
        billed_list = new ArrayList<>();
        stringbuilder = new StringBuilder();
        stringbuilder_2 = new StringBuilder();
        stringbuilder_3 = new StringBuilder();
        stringbuilder_4 = new StringBuilder();
        stringbuilder_5 = new StringBuilder();

        Intent in = getIntent();
        if (in.getExtras() != null) {
            Bundle sts = in.getExtras();
            billreport = sts.getString("Report");
            if (billreport.equals("Billreport")) {
                stsname = sts.getString("status");
                if (sts.getString("summart") != null) {
                    summary = sts.getString("summart");
                }
            } else {
                if (billreport.equals("tariffwisebilled")) {
                    stsname = sts.getString("Tariffname");
                }
            }
        }

        list1 = new ArrayList<String>();
        list2 = new ArrayList<String>();
        list3 = new ArrayList<String>();
        list4 = new ArrayList<String>();
        list5 = new ArrayList<String>();
        list6 = new ArrayList<String>();
        lv = (ListView) findViewById(R.id.listView1);
        mylist = new ArrayList<Map<String, String>>();
        num = new DecimalFormat("##.00");

        dbh = new Databasehelper(this);
        dbh.openDatabase();
        api = new Bluetooth_Printer_3inch_prof_ThermalAPI();

        printcursor = dbh.subdivdetails();
        if (printcursor.getCount() > 0) {
            printcursor.moveToNext();
            billstatus_printer = printcursor.getString(printcursor.getColumnIndex("BT_PRINTER"));
            if (billstatus_printer.equals("NGX")) {
                mBtp = BluetoothService.getngxprinter();
            }
        }

        Cursor billed = dbh.billed();
        billed.moveToNext();
        billed_mr = billed.getString(billed.getColumnIndex("MRCODE"));
        billed_date = billed.getString(billed.getColumnIndex("BILLDATE"));
        billed_date = billed_date.substring(0, 2);

        Cursor division = dbh.subdivdetails();
        division.moveToNext();
        billed_subdiv = division.getString(division.getColumnIndex("SUBDIV_CODE"));

        if (billreport.equals("Billreport")) {
            c1 = billreport();
        } else {
            if (billreport.equals("tariffwisebilled")) {
                c1 = tariffreport();
            }
        }
        if (c1.getCount() > 0) {
            billed_list.clear();
            int slno = 0;
            stringbuilder.setLength(0);
            while (c1.moveToNext()) {
                getSetValues = new GetSetValues();
                slno++;
                getSetValues.setBilled_slno(""+slno);
                rrno = c1.getString(c1.getColumnIndex("RRNO"));
                custid = c1.getString(c1.getColumnIndex("CONSNO"));
                getSetValues.setBilled_account_id(custid);
                name = c1.getString(c1.getColumnIndex("NAME"));
                units1 = Double.parseDouble(c1.getString(c1.getColumnIndex("UNITS")));
                payable1 = Double.parseDouble(c1.getString(c1.getColumnIndex("PAYABLE")));
                getSetValues.setBilled_payable(num.format(payable1));
                total_payable = total_payable + payable1;
                billed_ir = c1.getString(c1.getColumnIndex("PRVRED"));
                getSetValues.setBilled_ir(billed_ir);
                billed_fr = c1.getString(c1.getColumnIndex("PRES_RDG"));
                getSetValues.setBilled_fr(billed_fr);
                list1.add("" + slno);
                list2.add(name);
                list3.add(custid);
                list4.add(rrno);
                list5.add("" + units1);
                list6.add("" + payable1);
                billed_list.add(getSetValues);
            }

            for (int i = 0; i < list2.size(); i++) {
                map = new HashMap<String, String>();
                map.put("Value1", list1.get(i));
                map.put("Value2", list2.get(i));
                map.put("Value3", list3.get(i));
                map.put("Value4", list4.get(i));
                map.put("Value5", list5.get(i));
                map.put("Value6", list6.get(i));
                mylist.add(map);
            }

            adapstr = new String[]{"Value1", "Value2", "Value3", "Value4", "Value5", "Value6"};

            texts = new int[]{R.id.textView1, R.id.textView3, R.id.textView5, R.id.textView7, R.id.textView9, R.id.textView11};

            sa = new SimpleAdapter(this, mylist, R.layout.billstat2, adapstr, texts);

            lv.setAdapter(sa);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View child, int spos,
                                        long dpos) {
                    BilledRRNo = list4.get(spos);
                    billing_acct_id = list3.get(spos);
                    if (!stsname.equals("BILLED")) {
                        c2 = dbh.statcode(stsname);
                        try {
                            c2.moveToNext();
                            String Stscode = c2.getString(c2.getColumnIndex("STATUS_LABEL"));
                            if (!Stscode.equals("Door Lock")) {
                                billedreports();
                            } else {
                                billstatus = "dlbilled";
                                showDialog(SHOW_DLG);
                            }
                        } catch (CursorIndexOutOfBoundsException e) {
                            billedreports();
                        }
                    } else {
                        billedreports();
                    }
                }
            });
        } else {
            Intent send = new Intent(Billstatusext.this, BillStatus.class);
            send.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(send);
        }

    }

    private void billedreports() {
        if (billreport.equals("Billreport")) {
            billstatus = "normalbilled";
        } else {
            if (billreport.equals("tariffwisebilled")) {
                billstatus = "tariffwisebilled";
            }
        }
        Intent nxt = new Intent(Billstatusext.this, Reportdisp.class);
        nxt.putExtra("billing", billstatus);
        nxt.putExtra("RRNO", billing_acct_id);
        nxt.putExtra("STATUS", stsname);
        nxt.putExtra("Report", billreport);
        startActivity(nxt);
    }

    private Cursor billreport() {
        Cursor value = null;

        if (stsname != null) {
            tvsts.setText(stsname);
        }

        if (summary != null) {
            tvsts.setText(summary);
        }

        if (stsname.equals("BILLED")) {
            value = dbh.billed();
        }

        if (!stsname.equals("BILLED") && !stsname.equals("NOT BILLED") && !stsname.equals("TOTAL")) {
            value = dbh.status(stsname);
        }
        return value;
    }

    @Override
    public void onBackPressed() {
        Intent send = new Intent(Billstatusext.this, BillStatus.class);
        send.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(send);
        super.onBackPressed();
    }

    private Cursor tariffreport() {
        Cursor value = null;

        if (stsname != null) {
            tvsts.setText(stsname);
            value = dbh.tariffcheckforbilling(stsname);
        }
        return value;
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        Dialog d = null;
        switch (id) {
            case SHOW_DLG:
                AlertDialog.Builder ab = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                ab.setTitle("DL Billing");
                ab.setMessage("Door Lock Billing details");
                ab.setPositiveButton("Take Bill", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent nxt = new Intent(Billstatusext.this, ConsumerBilling.class);
                        nxt.putExtra("billing", billstatus);
                        nxt.putExtra("RRNO", billing_acct_id);
                        nxt.putExtra("STATUS", stsname);
                        startActivity(nxt);
                    }
                });
                ab.setNegativeButton("View Bill", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent nxt = new Intent(Billstatusext.this, Reportdisp.class);
                        nxt.putExtra("billing", billstatus);
                        nxt.putExtra("RRNO", billing_acct_id);
                        nxt.putExtra("STATUS", stsname);
                        startActivity(nxt);
                    }
                });
                d = ab.create();
                break;

            case PRINT_DLG:
                final AlertDialog printdlg = new AlertDialog.Builder(this).create();
                printdlg.setTitle("Bill Status");
                LinearLayout ab1linear = (LinearLayout) getLayoutInflater().inflate(R.layout.checkprinted, null);
                printdlg.setView(ab1linear);
                final TextView print_msg = (TextView) ab1linear.findViewById(R.id.textView1);
                Button yes_btn = (Button) ab1linear.findViewById(R.id.button1);
                Button no_btn = (Button) ab1linear.findViewById(R.id.button2);
                no_btn.setText(R.string.no_label);
                print_msg.setText(getResources().getString(R.string.askprint));
                yes_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        new Count_Reports(stringbuilder).execute();
                        if (BluetoothService.printerconnected) {
                            printdlg.dismiss();
                            printing = ProgressDialog.show(Billstatusext.this, "Printing", "Printing Please wait to Complete");
                            if (billstatus_printer.equals("NGX")) {
                                if (mBtp.getState() != BluetoothPrinter.STATE_CONNECTED) {
                                    printing.dismiss();
                                    showDialog(PAIR_DLG);
                                    return;
                                } else printngx();
                            } else if (billstatus_printer.equals("ALG")) {
                                printanalogics();
                            } else if (billstatus_printer.equals("GPT")){
                                phiPrinter_height = 150 + (9 * 36) + (billed_list.size() * 31);
                                es.submit(new TaskPrint(mCanvas, phiPrinter_height));
                            }
                            if (!billstatus_printer.equals("GPT")) {
                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        printing.dismiss();
                                        showDialog(EXIT_DLG);
                                    }
                                }, 5000);
                            }
                        } else {
                            printdlg.dismiss();
                            showDialog(PAIR_DLG);
                        }
                    }
                });
                no_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        printdlg.dismiss();
                    }
                });
                printdlg.show();
                break;

            case PAIR_DLG:
                final AlertDialog.Builder pairdlg = new AlertDialog.Builder(this);
                pairdlg.setTitle("Device Connection");
                pairdlg.setMessage("Printer is not connected to get print..");
                pairdlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = pairdlg.create();
                dialog.show();
                break;

            case EXIT_DLG:
                final AlertDialog exitdlg = new AlertDialog.Builder(this).create();
                exitdlg.setTitle("Bill Status");
                LinearLayout rl = (LinearLayout) getLayoutInflater().inflate(R.layout.checkprinted, null);
                exitdlg.setView(rl);
                final TextView exit_txt = (TextView) rl.findViewById(R.id.textView1);
                exit_txt.setText(getResources().getString(R.string.exitbilling));
                Button exityes_btn = (Button) rl.findViewById(R.id.button1);
                Button exitreprint_btn = (Button) rl.findViewById(R.id.button2);
                exityes_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitdlg.dismiss();
                    }
                });
                exitreprint_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitdlg.dismiss();
                        printing = ProgressDialog.show(Billstatusext.this, "Printing", "Printing Please wait to Complete");
                        if (billstatus_printer.equals("NGX")) {
                            printngx();
                        } else if (billstatus_printer.equals("ALG")) {
                            printanalogics();
                        } else if (billstatus_printer.equals("GPT")){
                            es.submit(new TaskPrint(mCanvas, phiPrinter_height));
                        }
                        if (!billstatus_printer.equals("GPT")) {
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    printing.dismiss();
                                    showDialog(EXIT_DLG);
                                }
                            }, 5000);
                        }
                    }
                });
                exitdlg.show();
                break;
        }
        return d;
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
                showDialog(PRINT_DLG);
                break;

            case R.id.item2:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void printngx() {
        int maxlength = 44;
        mBtp.setPrinterWidth(PrinterWidth.PRINT_WIDTH_72MM);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/DroidSansMono.ttf");
        TextPaint tp = new TextPaint();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BILLED INSTALLATIONS" + "\n");
        stringBuilder.append("SD:"+billed_subdiv + "  " + "MR:"+billed_mr + "  " + "DAY:"+billed_date);
        tp.setTextSize(25);
        tp.setTypeface(tf);
        mBtp.addText(stringBuilder.toString(), ALIGN_CENTER, tp);
        stringBuilder.setLength(0);
        stringBuilder.append(fcall.line(maxlength)+"\n");
        stringBuilder.append(fcall.alignright("SEQ", 5)+fcall.aligncenter("ACCTID", 10)+" "+fcall.aligncenter("IR", 6)+" "+
                fcall.aligncenter("FR", 6)+" "+fcall.aligncenter("AMOUNT", 14)+"\n");
        stringBuilder.append(fcall.line(maxlength));
        tp.setTextSize(22);
        tp.setTypeface(tf);
        mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
        stringBuilder.setLength(0);
        for (int i = 0; i < billed_list.size(); i++) {
            GetSetValues getSetValues = billed_list.get(i);
            if (i == billed_list.size()-1)
                stringBuilder.append(fcall.alignright(getSetValues.getBilled_slno(), 4) + " " + fcall.space(getSetValues.getBilled_account_id(), 10) +
                        " " + fcall.space(getSetValues.getBilled_ir(), 6) + " " + fcall.space(getSetValues.getBilled_fr(), 6) + " " +
                        fcall.alignright(getSetValues.getBilled_payable(), 14));
            else stringBuilder.append(fcall.alignright(getSetValues.getBilled_slno(), 4) + " " + fcall.space(getSetValues.getBilled_account_id(), 10) +
                    " " + fcall.space(getSetValues.getBilled_ir(), 6) + " " + fcall.space(getSetValues.getBilled_fr(), 6) + " " +
                    fcall.alignright(getSetValues.getBilled_payable(), 14)+"\n");
        }
        tp.setTextSize(22);
        tp.setTypeface(tf);
        mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
        stringBuilder.setLength(0);
        stringBuilder.append(fcall.line(maxlength)+"\n");
        stringBuilder.append(fcall.space("Total : ", 8)+" "+fcall.alignright(num.format(total_payable), 35)+"\n");
        stringBuilder.append(fcall.line(maxlength));
        tp.setTextSize(22);
        tp.setTypeface(tf);
        mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
        mBtp.print();
        mBtp.printUnicodeText("\n\n\n\n");
    }

    private void printanalogics() {
        int maxlength = 44;
        StringBuilder stringBuilder = new StringBuilder();
        analogics_double_print(fcall.aligncenter("BILLED INSTALLATIONS", 30), 6);
        analogicsprint(fcall.aligncenter("SD:"+billed_subdiv + "  " + "MR:"+billed_mr + "  " + "DAY:"+billed_date, 44), 6);
        analogicsprint(fcall.line(maxlength), 6);
        analogicsprint(fcall.aligncenter("SEQ", 5)+fcall.aligncenter("ACCTID", 10)+" "+fcall.aligncenter("IR", 6)+" "+
                fcall.aligncenter("FR", 6)+" "+fcall.aligncenter("AMOUNT", 14), 6);
        analogicsprint(fcall.line(maxlength), 6);
        for (int i = 0; i < billed_list.size(); i++) {
            GetSetValues getSetValues = billed_list.get(i);
            analogicsprint(fcall.alignright(getSetValues.getBilled_slno(), 4) + " " + fcall.space(getSetValues.getBilled_account_id(), 10) +
                    " " + fcall.space(getSetValues.getBilled_ir(), 6) + " " + fcall.space(getSetValues.getBilled_fr(), 6) + " " +
                    fcall.alignright(getSetValues.getBilled_payable(), 14), 3);
        }
        analogicsprint(fcall.line(maxlength), 6);
        analogicsprint(fcall.space("Total : ", 8)+" "+fcall.alignright(num.format(total_payable), 35), 6);
        analogicsprint(fcall.line(maxlength), 6);
        stringBuilder.setLength(0);
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        analogicsprint(stringBuilder.toString(), 6);
    }

    public void analogicsprint(String Printdata, int feed_line) {
        conn.printData(api.font_Courier_44_VIP(Printdata));
        text_line_spacing(feed_line);
    }

    public void analogics_double_print(String Printdata, int feed_line) {
        conn.printData(api.font_Double_Height_On_VIP());
        conn.printData(api.font_Courier_30_VIP(Printdata));
        text_line_spacing(feed_line);
        conn.printData(api.font_Double_Height_Off_VIP());
    }

    public void text_line_spacing(int space) {
        conn.printData(api.variable_Size_Line_Feed_VIP(space));
    }

    private class TaskPrint implements Runnable {
        Canvas canvas = null;
        int print_height = 0;

        private TaskPrint(Canvas pos, int height) {
            this.canvas = pos;
            this.print_height = height;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            final boolean bPrintResult = PrintTicket(canvas, 576, print_height);
            final boolean bIsOpened = canvas.GetIO().IsOpened();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), bPrintResult ? getResources().getString(R.string.printsuccess) : getResources().getString(R.string.printfailed), Toast.LENGTH_SHORT).show();
                    if (bIsOpened) {
                        yaxis = 0;
                        printing.dismiss();
                        showDialog(EXIT_DLG);
                    }
                }
            });
        }

        private boolean PrintTicket(Canvas canvas, int nPrintWidth, int nPrintHeight) {
            boolean bPrintResult = false;
            Typeface tfNumber = Typeface.createFromAsset(getAssets(), "fonts/DroidSansMono.ttf");
            canvas.CanvasBegin(nPrintWidth, nPrintHeight);
            canvas.SetPrintDirection(0);

            int maxlength = 40;
            int small_font_height = 20;
            int normal_font_height = 24;
            int double_font_height = 32;

            printboldtext(canvas, fcall.aligncenter("BILLED INSTALLATIONS", 30), tfNumber, double_font_height, 6);
            printtext(canvas, fcall.aligncenter("SD:"+billed_subdiv + "  " + "MR:"+billed_mr + "  " + "DAY:"+billed_date, 40), tfNumber, normal_font_height, 6);
            printtext(canvas, fcall.line(maxlength), tfNumber, normal_font_height, 6);
            printtext(canvas, fcall.aligncenter("SEQ", 5)+" "+fcall.aligncenter("ACCTID", 12)+" "+fcall.aligncenter("IR", 6)+" "+
                    fcall.aligncenter("FR", 6)+" "+fcall.aligncenter("AMOUNT", 14), tfNumber, small_font_height, 6);
            printtext(canvas, fcall.line(maxlength), tfNumber, normal_font_height, 6);
            for (int i = 0; i < billed_list.size(); i++) {
                GetSetValues getSetValues = billed_list.get(i);
                printtext(canvas, fcall.alignright(getSetValues.getBilled_slno(), 4) + fcall.space(" ", 3) + fcall.space(getSetValues.getBilled_account_id(), 10) +
                        fcall.space(" ", 2) + fcall.space(getSetValues.getBilled_ir(), 6) + " " + fcall.space(getSetValues.getBilled_fr(), 6) + " " +
                        fcall.alignright(getSetValues.getBilled_payable(), 14), tfNumber, small_font_height, 3);
            }
            printtext(canvas, fcall.line(maxlength), tfNumber, normal_font_height, 6);
            printtext(canvas, fcall.space("Total : ", 8)+fcall.alignright(getResources().getString(R.string.rupee)+" "+num.format(total_payable)+" /-", 32), tfNumber, normal_font_height, 6);
            printtext(canvas, fcall.line(maxlength), tfNumber, normal_font_height, 6);
            printtext(canvas, "", tfNumber, normal_font_height, 6);
            printtext(canvas, "", tfNumber, normal_font_height, 6);

            canvas.CanvasPrint(1, 0);

            bPrintResult = canvas.GetIO().IsOpened();
            return bPrintResult;
        }

        private void printtext(Canvas canvas, String text, Typeface tfNumber, float textsize, float axis) {
            yaxis++;
            canvas.DrawText(text + "\r\n", 0, yaxis, 0, tfNumber, textsize, Canvas.DIRECTION_LEFT_TO_RIGHT);
            if (textsize == 20) {
                yaxis = yaxis + textsize + 8;
            } else yaxis = yaxis + textsize + 6;
            yaxis = yaxis + axis;
        }

        private void printboldtext(Canvas canvas, String text, Typeface tfNumber, float textsize, float axis) {
            yaxis++;
            canvas.DrawText(text + "\r\n", 0, yaxis, 0, tfNumber, textsize, Canvas.FONTSTYLE_BOLD);
            if (textsize == 20) {
                yaxis = yaxis + textsize + 8;
            } else yaxis = yaxis + textsize + 6;
            yaxis = yaxis + axis;
        }
    }

    private class Count_Reports extends AsyncTask<String, String, String> {
        StringBuilder stringBuilder;

        public Count_Reports(StringBuilder stringBuilder) {
            this.stringBuilder = stringBuilder;
        }

        @Override
        protected String doInBackground(String... params) {
            stringBuilder.setLength(0);
            for (int i = 0; i < billed_list.size(); i++) {
                GetSetValues getSetValues = billed_list.get(i);
                if (i == billed_list.size()-1)
                    stringBuilder.append(fcall.alignright(getSetValues.getBilled_slno(), 4) + " " + fcall.space(getSetValues.getBilled_account_id(), 10) +
                            " " + fcall.space(getSetValues.getBilled_ir(), 6) + " " + fcall.space(getSetValues.getBilled_fr(), 6) + " " +
                            fcall.alignright(getSetValues.getBilled_payable(), 14));
                else stringBuilder.append(fcall.alignright(getSetValues.getBilled_slno(), 4) + " " + fcall.space(getSetValues.getBilled_account_id(), 10) +
                        " " + fcall.space(getSetValues.getBilled_ir(), 6) + " " + fcall.space(getSetValues.getBilled_fr(), 6) + " " +
                        fcall.alignright(getSetValues.getBilled_payable(), 14)+"\n");
                fcall.logStatus("i: "+i);
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            fcall.logStatus(result);
        }
    }
}
