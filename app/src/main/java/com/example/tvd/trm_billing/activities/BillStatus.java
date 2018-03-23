package com.example.tvd.trm_billing.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
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

import com.analogics.thermalprinter.AnalogicsThermalPrinter;
import com.analogics.utils.AnalogicsUtil;
import com.example.tvd.trm_billing.R;
import com.example.tvd.trm_billing.database.Databasehelper;
import com.example.tvd.trm_billing.services.BluetoothService;
import com.example.tvd.trm_billing.values.FunctionCalls;
import com.lvrenyang.io.Pos;
import com.ngx.BluetoothPrinter;
import com.ngx.PrinterWidth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static android.text.Layout.Alignment.ALIGN_CENTER;
import static android.text.Layout.Alignment.ALIGN_NORMAL;

public class BillStatus extends Activity {
    public final static String STORETXT = "billstatus.txt";
    public static final int DLG_BILLSTATUS = 1;

    private static final int PRINT_DLG = 10;
    private static final int EXIT_DLG = 11;
    private static final int PAIR_DLG = 12;

    TextView tvrdgdt;
    Cursor c, c1, c2, c3, c4, c5, printcursor;
    String reading, total, billed, unbilled, s1, s2, s3, s4, mr, custrdg, cust, billstatus_printtype, bill_status,
            bill_report, billstatus_printer ="";
    Databasehelper dbh;
    ArrayList<String> list1, list2, list3, list4;
    ArrayList<Map<String, String>> mylist;
    HashMap<String, String> map;
    ListView lv;
    SimpleAdapter sa;
    int[] s;
    FunctionCalls fcall;
    ProgressDialog printing;

    BluetoothPrinter mBtp;
    AnalogicsThermalPrinter conn = BluetoothService.conn;
    Pos mPos = BluetoothService.mPos;
    ExecutorService es = BluetoothService.es;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_status);
        tvrdgdt = (TextView) findViewById(R.id.textView2);
        lv = (ListView) findViewById(R.id.listView1);
        list1 = new ArrayList<String>();
        list2 = new ArrayList<String>();
        list3 = new ArrayList<String>();
        list4 = new ArrayList<String>();
        mylist = new ArrayList<Map<String, String>>();
        fcall = new FunctionCalls();

        dbh = new Databasehelper(this);
        dbh.openDatabase();

        c1 = dbh.collects1();
        if (c1 != null) {
            c1.moveToNext();
            reading = c1.getString(c1.getColumnIndex("COL1"));
        }

        c2 = dbh.collects2();
        if (c2 != null) {
            c2.moveToNext();
            total = c2.getString(c2.getColumnIndex("COL2"));
        }

        c3 = dbh.collects3();
        if (c3 != null) {
            c3.moveToNext();
            billed = c3.getString(c3.getColumnIndex("COL2"));
        } else {
            billed = "" + 0;
        }

        c4 = dbh.collects4();
        if (c4 != null) {
            c4.moveToNext();
            unbilled = c4.getString(c4.getColumnIndex("COL2"));
        }

        c5 = dbh.collects5();
        if (c5 != null) {
            while (c5.moveToNext()) {
                s3 = c5.getString(c5.getColumnIndex("COL1"));
                s4 = c5.getString(c5.getColumnIndex("COL2"));
                list3.add(s3);
                list4.add(s4);
            }
        }

        c = dbh.billstatus();
        if (c != null) {
            while (c.moveToNext()) {
                s1 = c.getString(c.getColumnIndex("COL1"));
                s2 = c.getString(c.getColumnIndex("COL2"));
                list1.add(s1);
                list2.add(s2);
            }
        }

        printcursor = dbh.subdivdetails();
        if (printcursor.getCount() > 0) {
            printcursor.moveToNext();
            billstatus_printtype = printcursor.getString(printcursor.getColumnIndex("PRINTER_TYPE"));
            billstatus_printer = printcursor.getString(printcursor.getColumnIndex("BT_PRINTER"));
            if (billstatus_printer.equals("NGX")) {
                mBtp = BluetoothService.getngxprinter();
            }
        }

        rdgdate();

        for (int i = 0; i < list1.size(); i++) {
            map = new HashMap<String, String>();
            map.put("Value1", list1.get(i));
            map.put("Value2", list2.get(i));
            mylist.add(map);
        }

        s = new int[]{R.id.textView1, R.id.textView3};

        sa = new SimpleAdapter(this, mylist, R.layout.billstatext,
                new String[]{"Value1", "Value2"}, s);
        //new int[]{R.id.textView1, R.id.textView3});

        lv.setAdapter(sa);

        tvrdgdt.setText(reading);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View child, int spos,
                                    long dpos) {
                String billsts = list1.get(spos);
                String value = list2.get(spos);
                if (!billsts.equals("-----------------") && !billsts.equals("NOT BILLED") && !billsts.equals("TOTAL")) {
                    if (!value.equals("0")) {
                        bill_report = "Billreport";
                        Intent next = new Intent(BillStatus.this, Billstatusext.class);
                        next.putExtra("status", billsts);
                        next.putExtra("Report", bill_report);
                        startActivity(next);
                    } else {
                        Toast.makeText(BillStatus.this, "Sorry No values to show", Toast.LENGTH_SHORT).show();
                    }
                } else if (billsts.equals("NOT BILLED")) {
                    Intent next1 = new Intent(BillStatus.this, Billstatusext1.class);
                    next1.putExtra("status", billsts);
                    startActivity(next1);
                } else if (billsts.equals("TOTAL")) {
                    Toast.makeText(BillStatus.this, "Sorry Total can not be shown", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BillStatus.this, "Sorry can not read this value", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                showdialog(PRINT_DLG);
                break;

            case R.id.item2:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void rdgdate() {
        if (reading.length() > 7) {
            mr = reading.substring(0, 7);
        } else {
            mr = reading;
        }

        if (reading.length() > 7) {
            cust = reading.substring(12);
        }

        if (billstatus_printtype.equals("3T")) {
            if (cust.length() > 28) {
                custrdg = cust.substring(0, 28);
            } else {
                custrdg = cust;
            }
        }
        if (billstatus_printtype.equals("2I")) {
            if (cust.length() > 24) {
                custrdg = cust.substring(0, 24);
            } else {
                custrdg = cust + "\r\n";
            }
        }

    }

    private void printngx() {
        mBtp.setPrinterWidth(PrinterWidth.PRINT_WIDTH_72MM);
        if (mBtp.getState() != BluetoothPrinter.STATE_CONNECTED) {
            Toast.makeText(BillStatus.this, "Printer is not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/DroidSansMono.ttf");
        TextPaint tp = new TextPaint();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("HESCOM" + "\n");
        stringBuilder.append("REPORT OF MR: "+ reading.substring(5, reading.indexOf("RDG_DATE")-1));
        tp.setTextSize(35);
        tp.setTypeface(tf);
        mBtp.addText(stringBuilder.toString(), ALIGN_CENTER, tp);

        stringBuilder.setLength(0);
        stringBuilder.append(fcall.centeralign(fcall.currentDateandTime(), 38) + "\n");
        stringBuilder.append(reading.substring(reading.indexOf("RDG_DATE"), reading.length()) + "\n");
        stringBuilder.append(fcall.line(38) + "\n");
        stringBuilder.append(printvalue("TOTAL" + " :" + " ", total, 38) + "\n");
        stringBuilder.append(printvalue("BILLED" + " :" + " ", billed, 38) + "\n");
        stringBuilder.append(printvalue("NOT BILLED" + " :" + " ", unbilled, 38) + "\n");
        stringBuilder.append("\n");
        StringBuilder sb4 = new StringBuilder();
        for (int i = 0; i < list3.size(); i++) {
            String a1 = list3.get(i).toString() + " :" + " ";
            String a2 = list4.get(i).toString();
            sb4.append(printvalue(a1, a2, 38) + "\n");
        }
        stringBuilder.append(sb4.toString());
        stringBuilder.append(fcall.line(38) + "\n");
        stringBuilder.append(printvalue("TOTAL" + " :" + " ", billed, 38) + "\n");
        stringBuilder.append(fcall.line(38));
        tp.setTextSize(25);
        tp.setTypeface(tf);
        mBtp.addText(stringBuilder.toString(), ALIGN_NORMAL, tp);
        mBtp.print();
        mBtp.printUnicodeText("\n\n\n");
    }

    private String printvalue(String firstletter, String lastletter, int linecount) {
        if (firstletter.length() > 35) {
            firstletter = firstletter.substring(0, firstletter.length() - 7) + " :" + " ";
        }
        String last = String.format("%"+(linecount-firstletter.length())+"s", fcall.rightalign(lastletter, (linecount-firstletter.length()-1)));
        return firstletter + last;
    }

    private void printanalogics() {
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/DroidSansMono.ttf");
        String address = BluetoothService.printer_address;
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("HESCOM" + "\n");
        stringBuilder.append("REPORT OF MR: "+ reading.substring(5, reading.indexOf("RDG_DATE")-1));
        analogicsprint(address, stringBuilder.toString(), 35, tf, ALIGN_CENTER);
        stringBuilder.setLength(0);

        stringBuilder.append(fcall.centeralign(fcall.currentDateandTime(), 38) + "\n");
        stringBuilder.append(reading.substring(reading.indexOf("RDG_DATE"), reading.length()) + "\n");
        stringBuilder.append(fcall.line(38) + "\n");
        analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
        stringBuilder.setLength(0);

        stringBuilder.append(printvalue("TOTAL" + " :" + " ", total, 38) + "\n");
        stringBuilder.append(printvalue("BILLED" + " :" + " ", billed, 38) + "\n");
        stringBuilder.append(printvalue("NOT BILLED" + " :" + " ", unbilled, 38) + "\n");
        analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
        stringBuilder.setLength(0);

        for (int i = 0; i < list3.size(); i++) {
            String a1 = list3.get(i).toString() + " :" + " ";
            String a2 = list4.get(i).toString();
            analogicsprint(address, printvalue(a1, a2, 38), 25, tf, ALIGN_NORMAL);
        }

        stringBuilder.append(fcall.line(38) + "\n");
        stringBuilder.append(printvalue("TOTAL" + " :" + " ", billed, 38) + "\n");
        stringBuilder.append(fcall.line(38));
        analogicsprint(address, stringBuilder.toString(), 25, tf, ALIGN_NORMAL);
        stringBuilder.setLength(0);

        stringBuilder.append("\n");
        stringBuilder.append("\n");
        analogicsprint(address, stringBuilder.toString(), 25, tf, Layout.Alignment.ALIGN_NORMAL);
    }

    private Bitmap textAsBitmap(String text, float textSize, float stroke, int color, Typeface typeface, Layout.Alignment alignment) {
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
        android.graphics.Canvas canvas = new android.graphics.Canvas(image);
        staticLayout.draw(canvas);
        return image;
    }

    private void analogicsprint(String address, String Printdata, int text_size, Typeface typeface, Layout.Alignment alignment) {
        AnalogicsUtil utils = new AnalogicsUtil();
        Bitmap bmp = textAsBitmap(Printdata, (float)text_size, 9.0F, -16711681, typeface, alignment);
        try {
            Bitmap e = bmp.copy(Bitmap.Config.ARGB_4444, true);
            byte[] c1 = utils.prepareDataToPrint(address, e);
            conn.printData(c1);
        } catch (InterruptedException var13) {
            var13.printStackTrace();
        }
    }

    private class TaskPrint implements Runnable {
        Pos pos = null;

        private TaskPrint(Pos pos) {
            this.pos = pos;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            final boolean bPrintResult = PrintTicket();//576
            final boolean bIsOpened = pos.GetIO().IsOpened();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), bPrintResult ? getResources().getString(R.string.printsuccess) : getResources().getString(R.string.printfailed), Toast.LENGTH_SHORT).show();
                    if (bIsOpened) {
                        printing.dismiss();
                        showdialog(EXIT_DLG);
                    }
                }
            });
        }

        public boolean PrintTicket() {
            boolean bPrintResult = false;
            int maxlength = 47;

            pos.POS_FeedLine();
            pos.POS_S_Align(1);
            printdoubleText("HESCOM");
            printdoubleText("REPORT OF MR: "+ reading.substring(5, reading.indexOf("RDG_DATE")-1));
            printText(fcall.currentDateandTime());
            pos.POS_S_Align(0);
            printText(reading.substring(reading.indexOf("RDG_DATE"), reading.length()));
            printText(fcall.line(maxlength));
            printText(printvalue("TOTAL" + " :" + " ", total, maxlength));
            printText(printvalue("BILLED" + " :" + " ", billed, maxlength));
            printText(printvalue("NOT BILLED" + " :" + " ", unbilled, maxlength));
            printText(" ");
            for (int i = 0; i < list3.size(); i++) {
                String a1 = list3.get(i).toString() + " :" + " ";
                String a2 = list4.get(i).toString();
                printText(printvalue(a1, a2, maxlength));
            }
            printText(fcall.line(maxlength));
            printText(printvalue("TOTAL" + " :" + " ", billed, maxlength));
            printText(fcall.line(maxlength));
            printText(" ");
            pos.POS_FeedLine();
            pos.POS_FeedLine();

            bPrintResult = pos.GetIO().IsOpened();
            return bPrintResult;
        }

        private void printText(String msg) {
            pos.POS_S_TextOut(msg + "\r\n", 0, 0, 0, 0, 4);
        }

        private void printdoubleText(String msg) {
            pos.POS_S_TextOut(msg + "\r\n", 0, 0, 1, 0, 4);
        }
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        Dialog d = null;
        switch (id) {
            case DLG_BILLSTATUS:
                AlertDialog.Builder ab = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                ab.setTitle("BillStatus");
                ab.setMessage("Will not print.. It will be implemented soon");
                ab.setNeutralButton("OK", null);
                d = ab.create();
                break;
        }
        return d;
    }

    private void showdialog(int id) {
        switch (id) {
            case PRINT_DLG:
                final AlertDialog printdlg = new AlertDialog.Builder(this).create();
                printdlg.setTitle("Bill Status");
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
                            printing = ProgressDialog.show(BillStatus.this, "Printing",
                                    "Printing Please wait to Complete");
                            if (billstatus_printer.equals("NGX")) {
                                printngx();
                            } else if (billstatus_printer.equals("ALG")) {
                                printanalogics();
                            } else if (billstatus_printer.equals("GPT")){
                                es.submit(new TaskPrint(mPos));
                            }
                            if (!billstatus_printer.equals("GPT")) {
                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        printing.dismiss();
                                        showdialog(EXIT_DLG);
                                    }
                                }, 5000);
                            }
                        } else {
                            printdlg.dismiss();
                            showdialog(PAIR_DLG);
                        }
                    }
                });
                no_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        printdlg.dismiss();
                        /*Intent back1 = new Intent(BillStatus.this, ConsumerBilling.class);
                        back1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(back1);*/
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
                        printing = ProgressDialog.show(BillStatus.this, "Printing",
                                "Printing Please wait to Complete");
                        if (billstatus_printer.equals("NGX")) {
                            printngx();
                        } else if (billstatus_printer.equals("ALG")) {
                            printanalogics();
                        } else if (billstatus_printer.equals("GPT")){
                            es.submit(new TaskPrint(mPos));
                        }
                        if (!billstatus_printer.equals("GPT")) {
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    printing.dismiss();
                                    showdialog(EXIT_DLG);
                                }
                            }, 5000);
                        }
                    }
                });
                exitdlg.show();
                break;
        }
    }
}
