package com.example.tvd.trm_billing.invoke;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.example.tvd.trm_billing.values.FunctionCalls;
import com.example.tvd.trm_billing.values.GetSetValues;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static com.example.tvd.trm_billing.values.ConstantValues.BILLING_SERVICE;
import static com.example.tvd.trm_billing.values.ConstantValues.COLLECTION_SERVICE;
import static com.example.tvd.trm_billing.values.ConstantValues.SERVICE;
import static com.example.tvd.trm_billing.values.ConstantValues.TRM_URL;

public class SendingData {
    private ReceivingData receivingData = new ReceivingData();
    private FunctionCalls functionCalls = new FunctionCalls();
    private String BASE_URL = TRM_URL + SERVICE;
    private String BASE_BILLING_URL = TRM_URL + BILLING_SERVICE;
    private String BASE_COLLECTION_URL = TRM_URL + COLLECTION_SERVICE;

    private String UrlPostConnection(String Post_Url, HashMap<String, String> datamap) throws IOException {
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
        int responseCode=conn.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line=br.readLine()) != null) {
                response+=line;
            }
        }
        else {
            response="";
        }
        return response;
    }
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
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
    private String UrlGetConnection(String Get_Url) throws IOException {
        String response = "";
        URL url = new URL(Get_Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        int responseCode=conn.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line=br.readLine()) != null) {
                response+=line;
            }
        }
        else {
            response="";
        }
        return response;
    }

    public class MR_Login extends AsyncTask<String, String, String> {
        String response="";
        Handler handler;
        GetSetValues getSetValues;

        public MR_Login(Handler handler, GetSetValues getSetValues) {
            this.handler = handler;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("MRCode", params[0]);
            datamap.put("DeviceId", params[1]);
            datamap.put("PASSWORD", params[2]);
            functionCalls.logStatus("MRCode: "+params[0] + "\n" + "DeviceID: "+params[1] + "\n" + "Password: "+params[2]);
            try {
                response = UrlPostConnection(BASE_COLLECTION_URL+"MRDetails", datamap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getMR_Login_status(result, handler, getSetValues);
        }
    }

    public class Download_Billing_datafile extends AsyncTask<String, String, String> {
        String record;
        Handler handler;

        public Download_Billing_datafile(Handler handler) {
            this.handler = handler;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("MRCODE", params[0]);
            datamap.put("DEVICE_ID", params[1]);
            datamap.put("BDate", params[2]);
            try {
                record = UrlPostConnection(BASE_BILLING_URL +"XMLtoSql", datamap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("debug", "Record: "+record);
            return record;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getBilling_file_Status(result, handler);
        }
    }

    public class MR_Track extends AsyncTask<String, String, String> {
        String response="";
        Handler handler;

        public MR_Track(Handler handler) {
            this.handler = handler;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("mrcode", params[0]);
            datamap.put("deviceid", params[1]);
            datamap.put("longitude", params[2]);
            datamap.put("latitude", params[3]);
            datamap.put("b_C", params[4]);
            functionCalls.logStatus("mrcode: "+params[0] + "\n" + "deviceid: "+params[1] + "\n" +
                    "longitude: "+params[2] + "\n" + "latitude: "+params[3] + "\n" + "b_C: "+params[4]);
            try {
                response = UrlPostConnection(BASE_URL+"Device_Location", datamap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.mr_tracking_status(result, handler);
        }
    }

    public class UpdateBilledRecords extends AsyncTask<String, String, String> {
        String response="";
        Handler handler;

        public UpdateBilledRecords(Handler handler) {
            this.handler = handler;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("MONTH", params[0]);
            datamap.put("READDATE", params[1]);
            datamap.put("RRNO", params[2]);
            datamap.put("NAME", params[3]);
            datamap.put("ADD1", params[4]);
            datamap.put("TARIFF", params[5]);
            datamap.put("MF", params[6]);
            datamap.put("PREVSTAT", params[7]);
            datamap.put("AVGCON", params[8]);
            datamap.put("LINEMIN", params[9]);
            datamap.put("SANCHP", params[10]);
            datamap.put("SANCKW", params[11]);
            datamap.put("PRVRED", params[12]);
            datamap.put("FR", params[13]);
            datamap.put("IR", params[14]);
            datamap.put("DLCOUNT", params[15]);
            datamap.put("ARREARS", params[16]);
            datamap.put("PF_FLAG", params[17]);
            datamap.put("BILLFOR", params[18]);
            datamap.put("MRCODE", params[19]);
            datamap.put("LEGFOL", params[20]);
            datamap.put("ODDEVEN", params[21]);
            datamap.put("SSNO", params[22]);
            datamap.put("CONSNO", params[23]);
            datamap.put("PH_NO", params[24]);
            datamap.put("REBATE_FLAG", params[25]);
            datamap.put("RREBATE", params[26]);
            datamap.put("EXTRA1", params[27]);
            datamap.put("DATA1", params[28]);
            datamap.put("EXTRA2", params[29]);
            datamap.put("DATA2", params[30]);
            datamap.put("DEPOSIT", params[31]);
            datamap.put("MTRDIGIT", params[32]);
            datamap.put("ASDAMT", params[33]);
            datamap.put("IODAMT", params[34]);
            datamap.put("PFVAL", params[35]);
            datamap.put("BMDVAL", params[36]);
            datamap.put("BILL_NO", params[37]);
            datamap.put("INTEREST_AMT", params[38]);
            datamap.put("CAP_FLAG", params[39]);
            datamap.put("TOD_FLAG", params[40]);
            datamap.put("TOD_PREVIOUS1", params[41]);
            datamap.put("TOD_PREVIOUS3", params[42]);
            datamap.put("INT_ON_DEP", params[43]);
            datamap.put("SO_FEEDER_TC_POLE", params[44]);
            datamap.put("TARIFF_NAME", params[45]);
            datamap.put("PREV_READ_DATE", params[46]);
            datamap.put("BILL_DAYS", params[47]);
            datamap.put("MTR_SERIAL_NO", params[48]);
            datamap.put("CHQ_DISHONOUR_DATE", params[49]);
            datamap.put("FDRNAME", params[50]);
            datamap.put("TCCODE", params[51]);
            datamap.put("MTR_FLAG", params[52]);
            datamap.put("PRES_RDG", params[53]);
            datamap.put("PRES_STS", params[54]);
            datamap.put("UNITS", params[55]);
            datamap.put("FIX", params[56]);
            datamap.put("ENGCHG", params[57]);
            datamap.put("REBATE_AMOUNT", params[58]);
            datamap.put("TAX_AMOUNT", params[59]);
            datamap.put("BMD_PENALTY", params[60]);
            datamap.put("PF_PENALTY", params[61]);
            datamap.put("PAYABLE", params[62]);
            datamap.put("BILLDATE", params[63]);
            datamap.put("BILLTIME", params[64]);
            datamap.put("TOD_CURRENT1", params[65]);
            datamap.put("TOD_CURRENT3", params[66]);
            datamap.put("GOK_SUBSIDY", params[67]);
            datamap.put("DEM_REVENUE", params[68]);
            datamap.put("GPS_LAT", params[69]);
            datamap.put("GPS_LONG", params[70]);
            datamap.put("ONLINE_FLAG", params[71]);
            datamap.put("BATTERY_CHARGE", params[72]);
            datamap.put("SIGNAL_STRENGTH", params[73]);
            datamap.put("IMGADD", params[74]);
            datamap.put("PAYABLE_REAL", params[75]);
            datamap.put("PAYABLE_PROFIT", params[76]);
            datamap.put("PAYABLE_LOSS", params[77]);
            datamap.put("BILL_PRINTED", params[78]);
            datamap.put("FSLAB1", params[79]);
            datamap.put("FSLAB2", params[80]);
            datamap.put("FSLAB3", params[81]);
            datamap.put("FSLAB4", params[82]);
            datamap.put("FSLAB5", params[83]);
            datamap.put("ESLAB1", params[84]);
            datamap.put("ESLAB2", params[85]);
            datamap.put("ESLAB3", params[86]);
            datamap.put("ESLAB4", params[87]);
            datamap.put("ESLAB5", params[88]);
            datamap.put("ESLAB6", params[89]);
            datamap.put("CHARITABLE_RBT_AMT", params[90]);
            datamap.put("SOLAR_RBT_AMT", params[91]);
            datamap.put("FL_RBT_AMT", params[92]);
            datamap.put("HANDICAP_RBT_AMT", params[93]);
            datamap.put("PL_RBT_AMT", params[94]);
            datamap.put("IPSET_RBT_AMT", params[95]);
            datamap.put("REBATEFROMCCB_AMT", params[96]);
            datamap.put("TOD_CHARGES", params[97]);
            datamap.put("PF_PENALITY_AMT", params[98]);
            datamap.put("EXLOAD_MDPENALITY", params[99]);
            datamap.put("CURR_BILL_AMOUNT", params[100]);
            datamap.put("ROUNDING_AMOUNT", params[101]);
            datamap.put("DUE_DATE", params[102]);
            datamap.put("DISCONN_DATE", params[103]);
            datamap.put("CREADJ", params[104]);
            datamap.put("PREADKVAH", params[105]);
            datamap.put("Filename", params[106]);
            datamap.put("ID", params[107]);
            datamap.put("CHQ_DISHONOUR_FLAG", params[108]);
            functionCalls.logStatus("MONTH: "+params[0]);
            functionCalls.logStatus("READDATE: "+params[1]);
            functionCalls.logStatus("RRNO: "+params[2]);
            functionCalls.logStatus("NAME: "+params[3]);
            functionCalls.logStatus("ADD1: "+params[4]);
            functionCalls.logStatus("TARIFF: "+params[5]);
            functionCalls.logStatus("MF: "+params[6]);
            functionCalls.logStatus("PREVSTAT: "+params[7]);
            functionCalls.logStatus("AVGCON: "+params[8]);
            functionCalls.logStatus("LINEMIN: "+params[9]);
            functionCalls.logStatus("SANCHP: "+params[10]);
            functionCalls.logStatus("SANCKW: "+params[11]);
            functionCalls.logStatus("PRVRED: "+params[12]);
            functionCalls.logStatus("FR: "+params[13]);
            functionCalls.logStatus("IR: "+params[14]);
            functionCalls.logStatus("DLCOUNT: "+params[15]);
            functionCalls.logStatus("ARREARS: "+params[16]);
            functionCalls.logStatus("PF_FLAG: "+params[17]);
            functionCalls.logStatus("BILLFOR: "+params[18]);
            functionCalls.logStatus("MRCODE: "+params[19]);
            functionCalls.logStatus("LEGFOL: "+params[20]);
            functionCalls.logStatus("ODDEVEN: "+params[21]);
            functionCalls.logStatus("SSNO: "+params[22]);
            functionCalls.logStatus("CONSNO: "+params[23]);
            functionCalls.logStatus("PH_NO: "+params[24]);
            functionCalls.logStatus("REBATE_FLAG: "+params[25]);
            functionCalls.logStatus("RREBATE: "+params[26]);
            functionCalls.logStatus("EXTRA1: "+params[27]);
            functionCalls.logStatus("DATA1: "+params[28]);
            functionCalls.logStatus("EXTRA2: "+params[29]);
            functionCalls.logStatus("DATA2: "+params[30]);
            functionCalls.logStatus("DEPOSIT: "+params[31]);
            functionCalls.logStatus("MTRDIGIT: "+params[32]);
            functionCalls.logStatus("ASDAMT: "+params[33]);
            functionCalls.logStatus("IODAMT: "+params[34]);
            functionCalls.logStatus("PFVAL: "+params[35]);
            functionCalls.logStatus("BMDVAL: "+params[36]);
            functionCalls.logStatus("BILL_NO: "+params[37]);
            functionCalls.logStatus("INTEREST_AMT: "+params[38]);
            functionCalls.logStatus("CAP_FLAG: "+params[39]);
            functionCalls.logStatus("TOD_FLAG: "+params[40]);
            functionCalls.logStatus("TOD_PREVIOUS1: "+params[41]);
            functionCalls.logStatus("TOD_PREVIOUS3: "+params[42]);
            functionCalls.logStatus("INT_ON_DEP: "+params[43]);
            functionCalls.logStatus("SO_FEEDER_TC_POLE: "+params[44]);
            functionCalls.logStatus("TARIFF_NAME: "+params[45]);
            functionCalls.logStatus("PREV_READ_DATE: "+params[46]);
            functionCalls.logStatus("BILL_DAYS: "+params[47]);
            functionCalls.logStatus("MTR_SERIAL_NO: "+params[48]);
            functionCalls.logStatus("CHQ_DISHONOUR_DATE: "+params[49]);
            functionCalls.logStatus("FDRNAME: "+params[50]);
            functionCalls.logStatus("TCCODE: "+params[51]);
            functionCalls.logStatus("MTR_FLAG: "+params[52]);
            functionCalls.logStatus("PRES_RDG: "+params[53]);
            functionCalls.logStatus("PRES_STS: "+params[54]);
            functionCalls.logStatus("UNITS: "+params[55]);
            functionCalls.logStatus("FIX: "+params[56]);
            functionCalls.logStatus("ENGCHG: "+params[57]);
            functionCalls.logStatus("REBATE_AMOUNT: "+params[58]);
            functionCalls.logStatus("TAX_AMOUNT: "+params[59]);
            functionCalls.logStatus("BMD_PENALTY: "+params[60]);
            functionCalls.logStatus("PF_PENALTY: "+params[61]);
            functionCalls.logStatus("PAYABLE: "+params[62]);
            functionCalls.logStatus("BILLDATE: "+params[63]);
            functionCalls.logStatus("BILLTIME: "+params[64]);
            functionCalls.logStatus("TOD_CURRENT1: "+params[65]);
            functionCalls.logStatus("TOD_CURRENT3: "+params[66]);
            functionCalls.logStatus("GOK_SUBSIDY: "+params[67]);
            functionCalls.logStatus("DEM_REVENUE: "+params[68]);
            functionCalls.logStatus("GPS_LAT: "+params[69]);
            functionCalls.logStatus("GPS_LONG: "+params[70]);
            functionCalls.logStatus("ONLINE_FLAG: "+params[71]);
            functionCalls.logStatus("BATTERY_CHARGE: "+params[72]);
            functionCalls.logStatus("SIGNAL_STRENGTH: "+params[73]);
            functionCalls.logStatus("IMGADD: "+params[74]);
            functionCalls.logStatus("PAYABLE_REAL: "+params[75]);
            functionCalls.logStatus("PAYABLE_PROFIT: "+params[76]);
            functionCalls.logStatus("PAYABLE_LOSS: "+params[77]);
            functionCalls.logStatus("BILL_PRINTED: "+params[78]);
            functionCalls.logStatus("FSLAB1: "+params[79]);
            functionCalls.logStatus("FSLAB2: "+params[80]);
            functionCalls.logStatus("FSLAB3: "+params[81]);
            functionCalls.logStatus("FSLAB4: "+params[82]);
            functionCalls.logStatus("FSLAB5: "+params[83]);
            functionCalls.logStatus("ESLAB1: "+params[84]);
            functionCalls.logStatus("ESLAB2: "+params[85]);
            functionCalls.logStatus("ESLAB3: "+params[86]);
            functionCalls.logStatus("ESLAB4: "+params[87]);
            functionCalls.logStatus("ESLAB5: "+params[88]);
            functionCalls.logStatus("ESLAB6: "+params[89]);
            functionCalls.logStatus("CHARITABLE_RBT_AMT: "+params[90]);
            functionCalls.logStatus("SOLAR_RBT_AMT: "+params[91]);
            functionCalls.logStatus("FL_RBT_AMT: "+params[92]);
            functionCalls.logStatus("HANDICAP_RBT_AMT: "+params[93]);
            functionCalls.logStatus("PL_RBT_AMT: "+params[94]);
            functionCalls.logStatus("IPSET_RBT_AMT: "+params[95]);
            functionCalls.logStatus("REBATEFROMCCB_AMT: "+params[96]);
            functionCalls.logStatus("TOD_CHARGES: "+params[97]);
            functionCalls.logStatus("PF_PENALITY_AMT: "+params[98]);
            functionCalls.logStatus("EXLOAD_MDPENALITY: "+params[99]);
            functionCalls.logStatus("CURR_BILL_AMOUNT: "+params[100]);
            functionCalls.logStatus("ROUNDING_AMOUNT: "+params[101]);
            functionCalls.logStatus("DUE_DATE: "+params[102]);
            functionCalls.logStatus("DISCONN_DATE: "+params[103]);
            functionCalls.logStatus("CREADJ: "+params[104]);
            functionCalls.logStatus("PREADKVAH: "+params[105]);
            functionCalls.logStatus("Filename: "+params[106]);
            functionCalls.logStatus("ID: "+params[107]);
            try {
                response = UrlPostConnection(BASE_BILLING_URL +"SqlBillRecord", datamap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.billing_update_status(result, handler);
        }
    }
    public class Update_Billed_record_details extends AsyncTask<String, String, String> {
        String response="";

        public Update_Billed_record_details() {
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("MRCode", params[0]);
            datamap.put("BilledRecord", params[1]);
            datamap.put("Ddate", params[2]);
            try {
                response = UrlPostConnection(BASE_URL+"BilledUnbilledDataUpdate", datamap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getMR_billed_Status(result);
        }
    }
}
