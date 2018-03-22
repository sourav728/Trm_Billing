package com.example.tvd.trm_billing.values;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore.Images;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.tvd.trm_billing.database.Databasehelper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import static com.example.tvd.trm_billing.values.ConstantValues.FILE_ZIPPING_COMPLETED;
import static com.example.tvd.trm_billing.values.ConstantValues.LOGIN_DATE_EQUALS;
import static com.example.tvd.trm_billing.values.ConstantValues.LOGIN_DATE_LESS;
import static com.example.tvd.trm_billing.values.ConstantValues.LOGIN_DATE_MORE;


public class FunctionCalls {


    public EditText edittext_id(View view, int id) {
        return (EditText) view.findViewById(id);
    }

    public Button btn_id(View view, int id) {
        return (Button) view.findViewById(id);
    }

    public String getCursorValue(Cursor data, String column_name) {
        return data.getString(data.getColumnIndexOrThrow(column_name));
    }
    public String space1(String s, int length) {
        int temp;
        StringBuilder spaces = new StringBuilder();
        temp = length - s.length();
        for (int i = 0; i < temp; i++) {
            spaces.append(" ");
        }
        return (s + spaces);
    }

    public String dateSet() throws ParseException {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int mnth2 = month + 1;
        String present_date1 = day + "/" + mnth2 + "/" + "" + year;
        Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(present_date1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        c.setTime(date);
        return sdf.format(c.getTime());
    }

    public String updatedate(String pre_date) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int mnth2 = month + 1;
        String present_date1 = year + "-" + mnth2 + "-" + "" +pre_date;
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(present_date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        c.setTime(date);
        return sdf.format(c.getTime());
    }

    public String getMonthYear() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int mnth2 = month + 1;
        String present_date1 = null;
        if (String.valueOf(mnth2).length() == 1)
            present_date1 = "0"+mnth2 + ""+year;
        else present_date1 = mnth2 + ""+year;
        Date date = null;
        try {
            date = new SimpleDateFormat("MMyyyy", Locale.US).parse(present_date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MMyyyy", Locale.US);
        c.setTime(date);
        return sdf.format(c.getTime());
    }

    public String getPrevMonthYear() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        String present_date1 = null;
        if (String.valueOf(month).length() == 1)
            present_date1 = "0"+month +"-"+ ""+year;
        else present_date1 = month +"-"+ ""+year;
        Date date = null;
        try {
            date = new SimpleDateFormat("MM-yyyy", Locale.US).parse(present_date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy", Locale.US);
        c.setTime(date);
        return sdf.format(c.getTime());
    }

    public String downloaddbdate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int mnth2 = month + 1;
        String present_date1 = day + mnth2 + "" + year;
        Date date = null;
        try {
            date = new SimpleDateFormat("ddMMyyyy", Locale.US).parse(present_date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy", Locale.US);
        c.setTime(date);
        return sdf.format(c.getTime());
    }

    public String recept_db_date() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int mnth2 = month + 1;
        String present_date1 = day + mnth2 + "" + year;
        Date date = null;
        try {
            date = new SimpleDateFormat("ddMMyy", Locale.US).parse(present_date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy", Locale.US);
        c.setTime(date);
        return sdf.format(c.getTime());
    }

    public String dateSetfordbfile() throws ParseException {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int mnth2 = month + 1;
        String date1 = day + "/" + mnth2 + "/" + "" + year;
        Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(date1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        c.setTime(date);
        String date2 = sdf.format(c.getTime());
        String dd = date2.substring(0, 2);
        String MM = date2.substring(3, 5);
        String yyyy = date2.substring(6, 10);
        String date3 = dd + MM + yyyy;
        return date3;
    }

    public String dateSetforupdbfile() throws ParseException {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int mnth2 = month + 1;
        String date1 = day + "/" + mnth2 + "/" + "" + year + " " + "" + hour + ":" + "" + min;
        Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(date1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
        c.setTime(date);
        String date2 = sdf.format(c.getTime());
        String dd = date2.substring(0, 2);
        String MM = date2.substring(3, 5);
        String yyyy = date2.substring(6, 10);
        String HH = date2.substring(11, 13);
        String mm = date2.substring(14, 16);
        return dd + MM + yyyy + "_" + HH + mm;
    }

    //Getting the duedate
    public String duedate(String s, int duedate) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(s);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, duedate);
        return sdf.format(c.getTime());
    }

    public String datepickerdlgformat(String dateformat) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(dateformat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return sdf.format(c.getTime());
    }

    public String currentDateandTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
        return sdf.format(new Date());
    }

    public String currentDateTimeforBilling() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss", Locale.US);
        return sdf.format(new Date());
    }

    public String currentRecpttime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.US);
        return sdf.format(new Date());
    }

    public String recptDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.US);
        return sdf.format(new Date());
    }

    public String getuploadfilename(String IMEI) {
        String date = null;
        try {
            date = dateSetfordbfile();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return IMEI + "_" + date + "_UP";
    }

    public String getdownloadfilename(String IMEI) {
        String date = null;
        try {
            date = dateSetfordbfile();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return IMEI + "_" + date + "_DWN";
    }

    public String Appfoldername() {
        return "TRM_Smart_Billing" + File.separator + "data" + File.separator + "files";
    }

    public String filepath(String value) {
        File dir = new File(android.os.Environment.getExternalStorageDirectory(), Appfoldername()
                + File.separator + value);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.toString();
    }

    public File filestore(String value) {
        File dir = new File(android.os.Environment.getExternalStorageDirectory(), Appfoldername()
                + File.separator + value);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public File filestorepath(String value, String file) {
        File dir = new File(android.os.Environment.getExternalStorageDirectory(), Appfoldername()
                + File.separator + value);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, File.separator + file);
    }

    public String trm_backup1() {
        return ".TRM_Backup1" + File.separator + "data" + File.separator + "files";
    }

    public String trm_backup1_filepath(String value) {
        File dir = new File(android.os.Environment.getExternalStorageDirectory(), trm_backup1()
                + File.separator + value);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.toString();
    }

    public File trm_backup1_filestorepath(String value, String file) {
        File dir = new File(android.os.Environment.getExternalStorageDirectory(), trm_backup1()
                + File.separator + value);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, File.separator + file);
    }

    public String trm_backup2() {
        return ".TRM_Backup2" + File.separator + "data" + File.separator + "files";
    }

    public String trm_backup2_filepath(String value) {
        File dir = new File(android.os.Environment.getExternalStorageDirectory(), trm_backup2()
                + File.separator + value);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.toString();
    }

    public File trm_backup2_filestorepath(String value, String file) {
        File dir = new File(android.os.Environment.getExternalStorageDirectory(), trm_backup2()
                + File.separator + value);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, File.separator + file);
    }

    public String deletepath(String value) {
        File dir = new File(android.os.Environment.getExternalStorageDirectory(), Appfoldername()
                + File.separator + value);
        if (dir.exists()) {
            dir.delete();
        }
        return "" + dir;
    }

    public File deletefile(String value) {
        File dir = new File(android.os.Environment.getExternalStorageDirectory(), Appfoldername()
                + File.separator + value);
        if (!dir.exists()) {
            dir.delete();
        }
        Log.d("debug", "File deleting: " + "" + dir);
        return dir;
    }

    public String changedateformat(String datevalue, String changedivider) {
        Date date = null;
        if ((datevalue.substring(datevalue.length() - 5, datevalue.length() - 4)).equals("/")) {
            try {
                date = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(datevalue);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            if ((datevalue.substring(datevalue.length() - 5, datevalue.length() - 4)).equals("-")) {
                try {
                    date = new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(datevalue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                if (datevalue.length() == 8) {
                    try {
                        date = new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(datevalue.substring(0, 2) + "-"
                                + datevalue.substring(2, 4) + "-" + datevalue.substring(4));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String format = sdf.format(c.getTime());
        return format.substring(0, 2) + changedivider + format.substring(3, 5) + changedivider + format.substring(6, 10);
    }

    public void showtoastatcenter(Context context, String Message) {
        Toast toast = Toast.makeText(context, Message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void showtoastatbottom(Context context, String Message) {
        Toast toast = Toast.makeText(context, Message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    public void showtoast(Context context, String Message) {
        Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
    }

    public String inserttextfile() {
        String filename = "TextReport.txt";
        return filename;
    }

    public void checkimage_and_delete(String foldername, String ConsumerID) {
        String folderpath = filepath(foldername);
        int considlength = ConsumerID.length();
        File imagefiledir = new File(folderpath);
        File[] files = imagefiledir.listFiles();
        for (int i = 0; i < files.length; i++) {
            String filepath = files[i].getName();
            String findimage = filepath.substring(0, considlength);
            if (findimage.equals(ConsumerID)) {
                File file = new File(folderpath + File.separator + filepath);
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    public boolean checkuploadedfile() {
        boolean fileuploaded = false;
        String foldername = "UploadFolder";
        String folderpath = filepath(foldername);
        String currentdate = "";
        try {
            currentdate = dateSet();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String MM = currentdate.substring(3, 5);
        String yy = currentdate.substring(6, 10);
        String curdate = MM + yy;
        File uploadfiles = new File(folderpath);
        File[] files = uploadfiles.listFiles();
        if (files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                String getfilename = files[i].getName();
                String getfilemonth = getfilename.substring(18, 24);
                if (getfilemonth.equals(curdate)) {
                    fileuploaded = true;
                    break;
                }
            }
        }
        return fileuploaded;
    }

    public String month(String date) {
        String s1 = date.substring(2, 3);
        String[] months = new String[]{"JAN", "FEB", "MAR",
                "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        String s3;
        if (s1.equals("/") || s1.equals("-")) {
            String s2 = date.substring(3, 5);
            int m1 = Integer.parseInt(s2);
            s3 = months[m1 - 1];
        } else {
            String s2 = date.substring(5, 7);
            int m1 = Integer.parseInt(s2);
            s3 = months[m1 - 1];
        }
        return s3;
    }

    public Date selectiondate(String date) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public String dateformat(String date, String separationformat) {
        String a1 = date.substring(0, 2);
        String a2 = date.substring(3, 5);
        String a3 = date.substring(6, 10);
        String a4 = a1 + separationformat + a2 + separationformat + a3;
        return a4;
    }

    public String changedateview(String date) {
        String s1 = date.substring(2, 3);
        String s3;
        if (s1.equals("/") || s1.equals("-")) {
            String a1 = s1.substring(0, 2);
            String a2 = s1.substring(3, 5);
            String a3 = s1.substring(6, 10);
            s3 = a3 + "-" + a2 + "-" + a1;
        } else {
            String a1 = s1.substring(0, 4);
            String a2 = s1.substring(5, 7);
            String a3 = s1.substring(8, 10);
            s3 = a3 + "-" + a2 + "-" + a1;
        }
        return s3;
    }

    public String monthview(String date) {
        String s1 = date.substring(3, 5);
        String[] months = new String[]{"JANUARY", "FEBRUARY", "MARCH",
                "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        int m1 = Integer.parseInt(s1);
        String a2 = months[m1 - 1];
        return a2;
    }

    public String dateview(String date) {
        String s1 = date.substring(2, 3);
        String[] months = new String[]{"JAN", "FEB", "MAR",
                "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        String s3;
        if (s1.equals("/") || s1.equals("-")) {
            String a1 = date.substring(0, 2);
            String s2 = date.substring(3, 5);
            int m1 = Integer.parseInt(s2);
            String a2 = months[m1 - 1];
            String a3 = date.substring(6, 10);
            s3 = a1 + "-" + a2 + "-" + a3;
        } else {
            String a1 = date.substring(0, 4);
            String s2 = date.substring(5, 7);
            int m1 = Integer.parseInt(s2);
            String a2 = months[m1 - 1];
            String a3 = date.substring(8, 10);
            s3 = a3 + "-" + a2 + "-" + a1;
        }
        return s3;
    }

    @SuppressLint("DefaultLocale")
    public String convertdateview(String date, String format, String separation) {
        String s1, s2, s3, s4, s5="";
        if (date.length() == 10) {
            s1 = date.substring(0, 2);
            s2 = date.substring(3, 5);
            s3 = date.substring(6, 10);
            if (format.equals("DD") || format.equals("dd")) {
                s5 = s1 + separation + s2 + separation + s3;
            } else {
                s5 = s3 + separation + s2 + separation + s1;
            }
        } else if (date.length() == 9) {
            s4 = date.substring(1, 2);
            try {
                int i1 = Integer.parseInt(s4);
                s1 = date.substring(0, 2);
                s2 = date.substring(3, 4);
                s3 = date.substring(5, 9);
                if (format.equals("DD") || format.equals("dd")) {
                    s5 = s1 + separation + "0"+s2 + separation + s3;
                } else {
                    s5 = s3 + separation + "0"+s2 + separation + s1;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                s1 = date.substring(0, 1);
                s2 = date.substring(2, 4);
                s3 = date.substring(5, 9);
                if (format.equals("DD") || format.equals("dd")) {
                    s5 = "0"+s1 + separation + s2 + separation + s3;
                } else {
                    s5 = s3 + separation + s2 + separation + "0"+s1;
                }
            }
        } else if (date.length() == 8) {
            s1 = date.substring(0, 1);
            s2 = date.substring(2, 3);
            s3 = date.substring(4, 8);
            if (format.equals("DD") || format.equals("dd")) {
                s5 = "0"+s1 + separation + "0"+s2 + separation + s3;
            } else {
                s5 = s3 + separation + "0"+s2 + separation + "0"+s1;
            }
        }
        return s5;
    }

    public String consumernumber(String number) {
        int consnum = number.length();
        String cons1 = null;
		/*if (consnum > 7) {
			cons1 = number.substring(7);
		}
		else {
			cons1 = number;
		}*/
        cons1 = number;
        String cons2 = null;
        String cons3 = null;
        for (int i = 0; i < cons1.length(); i++) {
            cons2 = cons1.substring(i, (i + 1));
            if (cons2.equals("0")) {
                cons3 = cons1.substring(i + 1);
            } else {
                cons3 = cons1.substring(i);
                break;
            }
        }
        return cons3;
    }

    public String considappend(String s, int length, String space_value) {
        int temp;
        StringBuilder spaces = new StringBuilder();
        temp = length - s.length();
        for (int i = 0; i < temp; i++) {
            spaces.append(space_value);
        }
        return (spaces + s);
    }

    public void showprogressdialog(String Message, ProgressDialog dialog) {
        dialog.setTitle("Downloading");
        dialog.setMessage(Message);
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.show();
    }

    public void showprogressdialog(String Message, ProgressDialog dialog, String Title) {
        dialog.setTitle(Title);
        dialog.setMessage(Message);
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.show();
    }

    //Making right alignment for values
    public String rightalign(String s1, int len) {
        for (int i = 0; i < len - s1.length(); i++) {
            s1 = " " + s1;
        }
        return (s1);
    }

    public String alignright(String msg, int len) {
        for (int i = 0; i < len - msg.length(); i++) {
            msg = " " + msg;
        }
        msg = String.format("%" + len + "s", msg);
        return msg;
    }

    public String aligncenter(String msg, int len) {
        int count = msg.length();
        int value = len - count;
        int append = (value / 2);
        return space(" ", append) + msg + space(" ", append);
    }

    //Making left alignment for values
    public String leftalign(String s1, int len) {
        for (int i = 0; i < len - s1.length(); i++) {
            s1 = s1 + " ";
        }
        return (s1);
    }

    public String centeralign(String text, int width) {
        int count = text.length();
        int value = width - count;
        int append = (value / 2);
        return space(" ", append) + text;
    }

    //Dotted line
    public String line(int length) {
        StringBuilder sb5 = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb5.append("-");
        }
        return (sb5.toString());
    }

    public String spaceright(String s, int length) {
        int temp;
        StringBuilder spaces = new StringBuilder();
        temp = length - s.length();
        for (int i = 0; i < temp; i++) {
            spaces.append(" ");
        }
        return (spaces + s);
    }

    public String space(String s, int len) {
        int temp;
        StringBuilder spaces = new StringBuilder();
        temp = len - s.length();
        for (int i = 0; i < temp; i++) {
            spaces.append(" ");
        }
        return (s + spaces);
    }

    public String blank(int length) {
        StringBuilder sb6 = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb6.append(" ");
        }
        return (sb6.toString());
    }

    public Bitmap getImage(String path, Context con) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
        int[] newWH = new int[2];
        newWH[0] = srcWidth / 2;
        newWH[1] = (newWH[0] * srcHeight) / srcWidth;

        int inSampleSize = 1;
        while (srcWidth / 2 >= newWH[0]) {
            srcWidth /= 2;
            srcHeight /= 2;
            inSampleSize *= 2;
        }

        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inSampleSize = inSampleSize;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap sampledSrcBitmap = BitmapFactory.decodeFile(path, options);
        ExifInterface exif = new ExifInterface(path);
        String s = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        System.out.println("Orientation>>>>>>>>>>>>>>>>>>>>" + s);
        Matrix matrix = new Matrix();
        float rotation = rotationForImage(con, Uri.fromFile(new File(path)));
        if (rotation != 0f) {
            matrix.preRotate(rotation);
        }

        Bitmap pqr = Bitmap.createBitmap(
                sampledSrcBitmap, 0, 0, sampledSrcBitmap.getWidth(), sampledSrcBitmap.getHeight(), matrix, true);


        return pqr;
    }


    public float rotationForImage(Context context, Uri uri) {
        if (uri.getScheme().equals("content")) {
            String[] projection = {Images.ImageColumns.ORIENTATION};
            Cursor c = context.getContentResolver().query(
                    uri, projection, null, null, null);
            if (c.moveToFirst()) {
                return c.getInt(0);
            }
        } else if (uri.getScheme().equals("file")) {
            try {
                ExifInterface exif = new ExifInterface(uri.getPath());
                int rotation = (int) exifOrientationToDegrees(
                        exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_NORMAL));
                return rotation;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return 0f;
    }

    public static float exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public String pdcalculation(String pdrecorded, Cursor c, String sanctionload, String weeksts) {
        double pdcal = 0;
        double pd = Double.parseDouble(pdrecorded);
        double load = Double.parseDouble(sanctionload);
        double pddiff = pd - load;
        if (pddiff > 0) {
            c.moveToNext();
            if (weeksts.equals("1")) {
                String frate = c.getString(c.getColumnIndex("FRATE1"));
                double frate1 = Double.parseDouble(frate);
                pdcal = frate1 * pddiff * 2;
            } else {
                if (weeksts.equals("2")) {
                    String frate = c.getString(c.getColumnIndex("FRATE2"));
                    double frate1 = Double.parseDouble(frate);
                    pdcal = frate1 * pddiff * 2;
                } else {
                    if (weeksts.equals("3")) {
                        String frate = c.getString(c.getColumnIndex("FRATE3"));
                        double frate1 = Double.parseDouble(frate);
                        pdcal = frate1 * pddiff * 2;
                    } else {
                        if (weeksts.equals("4")) {
                            String frate = c.getString(c.getColumnIndex("FRATE4"));
                            double frate1 = Double.parseDouble(frate);
                            pdcal = frate1 * pddiff * 2;
                        } else {
                            if (weeksts.equals("5")) {
                                String frate = c.getString(c.getColumnIndex("FRATE5"));
                                double frate1 = Double.parseDouble(frate);
                                pdcal = frate1 * pddiff * 2;
                            }
                        }
                    }
                }
            }
        }
        return decimalroundoff(pdcal);
    }

    public String decimalroundoff(double value) {
        BigDecimal a = new BigDecimal(value);
        BigDecimal roundOff = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return "" + roundOff;
    }

    public String decimalround(double value) {
        String val = String.valueOf(value);
        String val1 = val.substring(val.indexOf('.')+1, val.length());
        if (val1.length() > 2) {
            String val3 = val1.substring(2, 3);
            BigDecimal a = new BigDecimal(value);
            BigDecimal roundOff;
            BigDecimal roundOff1;
            BigDecimal a1;
            if (val1.length() > 3) {
                String val4 = val1.substring(3, 4);
                if (Integer.parseInt(val4) < 5) {
                    roundOff1 = a.setScale(3, BigDecimal.ROUND_HALF_EVEN);
                    a1 = new BigDecimal(String.valueOf(roundOff1));
                    roundOff = a1.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                } else {
                    roundOff1 = a.setScale(3, BigDecimal.ROUND_UP);
                    a1 = new BigDecimal(String.valueOf(roundOff1));
                    roundOff = a1.setScale(2, BigDecimal.ROUND_UP);
                }
            } else {
                if (Integer.parseInt(val3) < 5) {
                    roundOff = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                } else roundOff = a.setScale(2, BigDecimal.ROUND_UP);
            }
            return String.valueOf(roundOff);
        } else return String.valueOf(value);
    }

    public String taxamount(double value) {
        String tax = ""+value;
        String amt = tax.substring(0, tax.lastIndexOf('.'));
        String amt1 = tax.substring(tax.lastIndexOf('.')+1, tax.length());
        if (amt1.length() > 2) {
            amt1 = amt1.substring(0, 2);
            return amt+"."+amt1;
        } else if (amt1.length() == 1) {
            amt1 = amt1 + "0";
            return amt+"."+amt1;
        } else return amt+"."+amt1;
    }

    public String tripleroundoff(double value) {
        BigDecimal a = new BigDecimal(value);
        BigDecimal roundOff = a.setScale(3, BigDecimal.ROUND_HALF_EVEN);
        double againroundoff = Double.parseDouble(roundOff.toString());
        BigDecimal a2 = new BigDecimal(againroundoff);
        BigDecimal roundOff2 = a2.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return "" + roundOff2;
    }

    public String CalculateDays(String Prev_date, String Pres_date) {
        Prev_date = changedateformat(Prev_date, "/");
        Pres_date = changedateformat(Pres_date, "/");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date Date1 = null;
        Date Date2 = null;
        try {
            Date1 = format.parse(Prev_date);
            Date2 = format.parse(Pres_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long result = (Date2.getTime() - Date1.getTime()) / (24 * 60 * 60 * 1000);
        return "" + result;
    }

    public void showtoastnormal(Context context, String Message) {
        Toast toast = Toast.makeText(context, Message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    public void logStatus(String message) {
        Log.d("debug", message);
    }

    public String monthnumber(String date) {
        return date.substring(3, 5);
    }

    // ////////////////----------Copy Database to SD Card for Uploading// -------------\\\\\\\\\\\\\\\\\\\\\\\

    public void copyDBtoSDcard(String mobileuploadfilepath, String filename, String fileformat, Context context)
            throws IOException {
        File tmp = new File(mobileuploadfilepath + "full_" + filename + fileformat);
        Databasehelper myDbHelper = new Databasehelper(context);
        if (tmp.exists()) {
            tmp.delete();
            myDbHelper.copyDBtoSD(mobileuploadfilepath, "full_" + filename, fileformat);
        } else {
            myDbHelper.copyDBtoSD(mobileuploadfilepath, "full_" + filename, fileformat);
        }
    }

    public void zipdbandupload(String mobileuploadfilepath, String filename, String fileformat, Context context, Handler handler)
            throws IOException {
        logStatus("Zip_Upload_1");
        logStatus("Zip_Upload_uploadpath: "+mobileuploadfilepath);
        logStatus("Zip_Upload_filename: "+filename);
        logStatus("Zip_Upload_format: "+fileformat);
        File tmp = new File(mobileuploadfilepath + filename + fileformat);
        logStatus("Zip_File_Upload: "+tmp.toString());
        logStatus("Zip_Upload_2");
        Databasehelper upload = new Databasehelper(context);
        logStatus("Zip_Upload_3");
        if (tmp.exists()) {
            logStatus("Zip_Upload_File Exists");
            tmp.delete();
            logStatus("Zip_Upload_File Deleted");
            upload.copyDBtoSD(mobileuploadfilepath, filename, fileformat);
            logStatus("Zip_Upload_Zipped");
        } else {
            logStatus("Zip_Upload_File not found");
            upload.copyDBtoSD(mobileuploadfilepath, filename, fileformat);
            logStatus("Zip_Upload_Zipped");
        }
        String backup_zip_path = trm_backup1_filepath("UploadZips") + File.separator;
        File backup_tmp = new File(backup_zip_path + filename + fileformat);
        logStatus("Backup_Zip_File_Upload: "+backup_tmp.toString());
        if (backup_tmp.exists()) {
            logStatus("Backup_Zip_Upload_File Exists");
            backup_tmp.delete();
            logStatus("Backup_Zip_Upload_File Deleted");
            upload.copyDBtoSD(backup_zip_path, filename, fileformat);
            logStatus("Backup_Zip_Upload_Zipped");
            handler.sendEmptyMessage(FILE_ZIPPING_COMPLETED);
        } else {
            logStatus("Backup_Zip_Upload_File not found");
            upload.copyDBtoSD(backup_zip_path, filename, fileformat);
            logStatus("Backup_Zip_Upload_Zipped");
            handler.sendEmptyMessage(FILE_ZIPPING_COMPLETED);
        }
    }

    public boolean checkInternetConnection(Context context) {
        CheckInternetConnection cd = new CheckInternetConnection(context.getApplicationContext());
        return cd.isConnectingToInternet();
    }

    public final boolean isInternetOn(Context activity) {
        ConnectivityManager connect = (ConnectivityManager) activity.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
        if (connect.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connect.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                connect.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                connect.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else if (connect.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                connect.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

    public void reload(Activity activity) {
        Intent intent = activity.getIntent();
        activity.overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.finish();
        activity.overridePendingTransition(0, 0);
        activity.startActivity(intent);
    }

    public String parseServerXML(String result) {
        String value = "";
        XmlPullParserFactory pullParserFactory;
        InputStream res;
        try {
            res = new ByteArrayInputStream(result.getBytes());
            pullParserFactory = XmlPullParserFactory.newInstance();
            pullParserFactory.setNamespaceAware(true);
            XmlPullParser parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(res, null);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        switch (name) {
                            case "string":
                                value = parser.nextText();
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void updateApp(Context context, File Apkfile) {
        Uri path;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            path = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", Apkfile);
        } else path = Uri.fromFile(Apkfile);
        Intent objIntent = new Intent(Intent.ACTION_VIEW);
        objIntent.setDataAndType(path, "application/vnd.android.package-archive");
        objIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        objIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(objIntent);
    }

    public void splitString(String msg, int lineSize, ArrayList<String> arrayList) {
        arrayList.clear();
        Pattern p = Pattern.compile("\\b.{0," + (lineSize - 1) + "}\\b\\W?");
        Matcher m = p.matcher(msg);
        while (m.find()) {
            arrayList.add(m.group().trim());
        }
    }

    public Bitmap getBitmap(String barcode, int barcodeType, int width, int height) {
        Bitmap barcodeBitmap = null;
        BarcodeFormat barcodeFormat = convertToZXingFormat(barcodeType);
        try {
            barcodeBitmap = encodeAsBitmap(barcode, barcodeFormat, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return barcodeBitmap;
    }

    private BarcodeFormat convertToZXingFormat(int format) {
        switch (format) {
            case 8:
                return BarcodeFormat.CODABAR;
            case 1:
                return BarcodeFormat.CODE_128;
            case 2:
                return BarcodeFormat.CODE_39;
            case 4:
                return BarcodeFormat.CODE_93;
            case 32:
                return BarcodeFormat.EAN_13;
            case 64:
                return BarcodeFormat.EAN_8;
            case 128:
                return BarcodeFormat.ITF;
            case 512:
                return BarcodeFormat.UPC_A;
            case 1024:
                return BarcodeFormat.UPC_E;
            default:
                return BarcodeFormat.CODE_128;
        }
    }

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    private Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        if (contents == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contents);
        if (encoding != null) {
            hints = new EnumMap(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contents, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    public boolean isDeviceSupportCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public String urlPostConnection(String Post_Url, HashMap<String, String> datamap) throws IOException {
        String response = "";
        URL url = new URL(Post_Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(getPostDataString(datamap));
        writer.flush();
        writer.close();
        os.close();
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
                response += line;
            }
        } else {
            response = "";
        }
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public String urlGetConnection(String Get_Url) throws IOException {
        String response = "";
        URL url = new URL(Get_Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
                response += line;
            }
        } else {
            response = "";
        }
        return response;
    }

    public String encodeImage(Bitmap bitmap, int quality) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }

    public void showtitleprogressdialog(String Message, ProgressDialog dialog, String Title) {
        dialog.setTitle(Title);
        dialog.setMessage(Message);
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.show();
    }

    public String normalisedVersion(String version) {
        return normalisedVersion(version, ".", 4);
    }

    private String normalisedVersion(String version, String sep, int maxWidth) {
        String[] split = Pattern.compile(sep, Pattern.LITERAL).split(version);
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(String.format("%" + maxWidth + 's', s));
        }
        return sb.toString();
    }

    public String Parse_Date2(String time) {
        String input = "yyyy-MM-d";
        String output = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(input, Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat(output, Locale.US);

        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String Parse_date1(String time) {
        String inputPattern = "dd-MM-yyyy HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.US);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String convertTo24Hour(String Time) {
        String convert = Time.substring(Time.length()-2);
//        convert = convert.substring(0, 1)+"."+convert.substring(1, 2)+".";
        convert = convert.toUpperCase();
        Time = Time.substring(0, Time.length()-2)+ " " + convert;
        String formattedDate="";
        try {
            SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa", Locale.US);
            SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm", Locale.US);
            formattedDate = outFormat.format(inFormat.parse(Time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        logStatus("Converted: "+formattedDate);
        return formattedDate;
    }

    public Date collectiontime(String date) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("HH:mm", Locale.US).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public String currentTime() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String present_date1 = hour + ":" + minute;
        Date date = null;
        try {
            date = new SimpleDateFormat("HH:mm", Locale.US).parse(present_date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
        c.setTime(date);
        return sdf.format(c.getTime());
    }

    public boolean compare_Times(Date fromdate, Date todate) {
        boolean result = false;
        Date currentime = collectiontime(currentTime());
        if (currentime.after(fromdate)) {
            logStatus("more");
            if (currentime.before(todate)) {
                logStatus("less");
                result = true;
            } else result = false;
        }
        return result;
    }

    public boolean compare_end_billing_time(String end_time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
        Date todate = null;
        try {
            todate = sdf.parse(end_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentime = collectiontime(currentTime());
        if (currentime.before(todate)) {
            logStatus("less");
            return true;
        } else return false;
    }

    public boolean compare(String v1, String v2) {
        String s1 = normalisedVersion(v1);
        String s2 = normalisedVersion(v2);
        int cmp = s1.compareTo(s2);
        String cmpStr = cmp < 0 ? "<" : cmp > 0 ? ">" : "==";
        return cmpStr.equals("<");
    }

    public String reversedate(String date) {
        String s1 = date.substring(0, 2);
        String s2 = date.substring(3, 5);
        String s3 = date.substring(6);
        return s2+"/"+s1+"/"+s3;
    }

    public String convert_collection_date(String date) {
        String s1 = date.substring(0, 2);
        String s2 = date.substring(3, 5);
        String s3 = date.substring(6);
        return s3;
    }

    public String reversedate(String date, String separation) {
        String s1 = date.substring(0, 2);
        String s2 = date.substring(3, 5);
        String s3 = date.substring(6);
        return s2+separation+s1+separation+s3;
    }

    public String rupeeFormat(String value) {
        value = value.replace(",", "");
        char lastDigit = value.charAt(value.length() - 1);
        String result = "";
        int len = value.length() - 1;
        int nDigits = 0;
        for (int i = len - 1; i >= 0; i--) {
            result = value.charAt(i) + result;
            nDigits++;
            if (((nDigits % 2) == 0) && (i > 0)) {
                result = "," + result;
            }
        }
        return (result + lastDigit);
    }

    public int monthdiff(String date) {
        String currdate="";
        try {
            currdate = dateSet();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date = changedateformat(date, "/");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(date);
            d2 = format.parse(currdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date startDate = d1;
        Date endDate = d2;
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(startDate);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        return diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    }

    public String getMonthtoint(String readate, String separation) {
        String finaldate="", month="", s1="", s2="";
        s1 = readate.substring(0, 2);
        s2 = readate.substring(7);
        month = readate.substring(3, 6).toUpperCase();
        switch (month) {
            case "JAN":
                finaldate = s1+separation+"01"+separation+s2;
                break;

            case "FEB":
                finaldate = s1+separation+"02"+separation+s2;
                break;

            case "MAR":
                finaldate = s1+separation+"03"+separation+s2;
                break;

            case "APR":
                finaldate = s1+separation+"04"+separation+s2;
                break;

            case "MAY":
                finaldate = s1+separation+"05"+separation+s2;
                break;

            case "JUN":
                finaldate = s1+separation+"06"+separation+s2;
                break;

            case "JUL":
                finaldate = s1+separation+"07"+separation+s2;
                break;

            case "AUG":
                finaldate = s1+separation+"08"+separation+s2;
                break;

            case "SEP":
                finaldate = s1+separation+"09"+separation+s2;
                break;

            case "OCT":
                finaldate = s1+separation+"10"+separation+s2;
                break;

            case "NOV":
                finaldate = s1+separation+"11"+separation+s2;
                break;

            case "DEC":
                finaldate = s1+separation+"12"+separation+s2;
                break;
        }
        return finaldate;
    }

    public String receipt_date() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int mnth2 = month + 1;
        String present_date1 = year + "-" + mnth2 + "-" + "" + day;
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(present_date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        c.setTime(date);
        return sdf.format(c.getTime());
    }

    public String receipt_date_time() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int mnth2 = month + 1;
        String present_date1 = day + "/" + mnth2 + "/" + "" + year + " " + ""+hour + ":"+ ""+minute;
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(present_date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
        c.setTime(date);
        return sdf.format(c.getTime());
    }

    public boolean compare_Times(String start_time, String end_time) {
        boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
        Date fromdate = null, todate = null;
        try {
            fromdate = sdf.parse(start_time);
            todate = sdf.parse(end_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentime = collectiontime(currentTime());
        if (currentime.after(fromdate)) {
            logStatus("more");
            if (currentime.before(todate)) {
                logStatus("less");
                result = true;
            } else result = false;
        }
        return result;
    }

    public void check_login_collection_date(String login_date, String exist_date, Handler handler) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date login=null, exist=null;
        try {
            login = sdf.parse(login_date);
            exist = sdf.parse(exist_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (login.equals(exist)) {
            handler.sendEmptyMessage(LOGIN_DATE_EQUALS);
        } else if (login.after(exist)) {
            handler.sendEmptyMessage(LOGIN_DATE_MORE);
        } else handler.sendEmptyMessage(LOGIN_DATE_LESS);
    }
}