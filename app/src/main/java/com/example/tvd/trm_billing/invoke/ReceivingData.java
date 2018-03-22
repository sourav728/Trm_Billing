package com.example.tvd.trm_billing.invoke;

import android.os.Handler;
import android.util.Log;

import com.example.tvd.trm_billing.values.FunctionCalls;
import com.example.tvd.trm_billing.values.GetSetValues;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.example.tvd.trm_billing.values.ConstantValues.BILLING_DB_FILE_DOWNLOAD;
import static com.example.tvd.trm_billing.values.ConstantValues.DOWNLOAD_FILE_NOT_FOUND;
import static com.example.tvd.trm_billing.values.ConstantValues.LOGIN_FAILURE;
import static com.example.tvd.trm_billing.values.ConstantValues.LOGIN_SUCCESS;
import static com.example.tvd.trm_billing.values.ConstantValues.MR_TRACKING_UPDATE;
import static com.example.tvd.trm_billing.values.ConstantValues.MR_TRACKING_UPDATE_FAIL;
import static com.example.tvd.trm_billing.values.ConstantValues.ONLINE_BILL_PASS_SUCCESS;

/**
 * Created by TVD on 3/22/2018.
 */

public class ReceivingData {
    private FunctionCalls functionCalls = new FunctionCalls();

    private String parseServerXML(String result) {
        String value="";
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
                                value =  parser.nextText();
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



    public void getMR_Login_status(String result, Handler handler, GetSetValues getSetValues) {
        result = parseServerXML(result);
        functionCalls.logStatus("MR_Login: "+result);
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String message = jsonObject.getString("message");
                if (StringUtils.startsWithIgnoreCase(message, "Success!")) {
                    getSetValues.setColl_mrcode(jsonObject.getString("MRCODE"));
                    getSetValues.setColl_mrname(jsonObject.getString("MRNAME"));
                    getSetValues.setColl_subdiv_code(jsonObject.getString("SUBDIVCODE"));
                    getSetValues.setApp_version(jsonObject.getString("BL_ANDR_VER"));
                    handler.sendEmptyMessage(LOGIN_SUCCESS);
                } else handler.sendEmptyMessage(LOGIN_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(LOGIN_FAILURE);
        }
    }
    public void getBilling_file_Status(String result, Handler handler) {
        result = parseServerXML(result);
        if (result.equals("Success!")) {
            handler.sendEmptyMessage(BILLING_DB_FILE_DOWNLOAD);
        } else handler.sendEmptyMessage(DOWNLOAD_FILE_NOT_FOUND);
    }

    public void mr_tracking_status(String result, Handler handler) {
        result = parseServerXML(result);
        functionCalls.logStatus("MR Tracking Status: "+result);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            if (StringUtils.startsWithIgnoreCase(jsonObject.getString("message"), "Success")) {
                handler.sendEmptyMessage(MR_TRACKING_UPDATE);
            } else handler.sendEmptyMessage(MR_TRACKING_UPDATE_FAIL);
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(MR_TRACKING_UPDATE_FAIL);
        }
    }
    public void billing_update_status(String result, Handler handler) {
        result = parseServerXML(result);
        functionCalls.logStatus("Billing Status: "+result);
        handler.sendEmptyMessage(ONLINE_BILL_PASS_SUCCESS);
    }
    public void getMR_billed_Status(String result/*, Handler handler*/) {
        result = parseServerXML(result);
        Log.d("debug", "Billed_Status: "+result);
        /*handler.sendEmptyMessage(MR_BILLED_STATUS_SUCCESS);*/
    }
}
