package com.example.tvd.trm_billing.invoke;
import com.example.tvd.trm_billing.values.FunctionCalls;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Datainvoke {
    FunctionCalls functionCalls = new FunctionCalls();

    public String invokeBilledUnbilledMRwise(String methName, String Namespace, String soapaction, String url,
                                             String mrcode, String deviceid) {
        SoapObject request = new SoapObject(Namespace, methName);
        request.addProperty("mrCode", mrcode);
        request.addProperty("Device_Id", deviceid);
        functionCalls.logStatus("mrCode: "+mrcode);
        functionCalls.logStatus("Device_Id: "+deviceid);
        functionCalls.logStatus("URL: "+url);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
        try {
            // Invole web service
            androidHttpTransport.call(soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();

            return result;

        } catch (Exception e) {
            return e.toString();
        }

    }

    public String Downloaddatafile(String methName, String Namespace, String soapaction, String url,
                                   String fromdate, String todate, String mrcode, String deviceid) {
        SoapObject request = new SoapObject(Namespace, methName);
        request.addProperty("FromDate", fromdate);
        request.addProperty("ToDate", todate);
        request.addProperty("Mrcode", mrcode);
        request.addProperty("DEVICE_ID", deviceid);
        functionCalls.logStatus("FromDate: "+fromdate);
        functionCalls.logStatus("ToDate: "+todate);
        functionCalls.logStatus("Mrcode: "+mrcode);
        functionCalls.logStatus("DEVICE_ID: "+deviceid);
        functionCalls.logStatus("URL: "+url);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
        try {
            // Invole web service
            androidHttpTransport.call(soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();

            return result;

        } catch (Exception e) {
            return e.toString();
        }

    }

    public String Downloadxmldatafile(String methName, String Namespace, String soapaction, String url, String mrcode, String deviceid, String date) {
        SoapObject request = new SoapObject(Namespace, methName);
        request.addProperty("MRCODE", mrcode);
        request.addProperty("DEVICE_ID", deviceid);
        request.addProperty("BDate", date);
        functionCalls.logStatus("MRCODE: "+mrcode);
        functionCalls.logStatus("DEVICE_ID: "+deviceid);
        functionCalls.logStatus("BDate: "+date);
        functionCalls.logStatus("URL: "+url);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
        try {
            // Invoke web service
            androidHttpTransport.call(soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }

    }

    // Method which invoke Web Method...........
    public String invokeJSONWS(String methName, String Namespace, String soapaction, String url, String batch,
                               String requestrecord) {
        SoapObject request = new SoapObject(Namespace, methName);
        PropertyInfo paramPI = new PropertyInfo();
        paramPI.setName(requestrecord);
        paramPI.setValue(batch);
        paramPI.setType(int.class);
        request.addProperty(paramPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
        try {
            // Invole web service
            androidHttpTransport.call(soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();

            return result;

        } catch (Exception e) {
            return e.toString();
        }

    }

    // Method which invoke Web Method...........
    public String GetJSONDetails(String methName, String Namespace, String soapaction, String url, String batch,
                                 String requestrecord, String deviceid, String requestdeviceid) {
        SoapObject request = new SoapObject(Namespace, methName);
        request.addProperty(requestrecord, batch);
        request.addProperty(requestdeviceid, deviceid);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
        try {
            // Invole web service
            androidHttpTransport.call(soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();

            return result;

        } catch (Exception e) {
            return e.toString();
        }

    }

    // Method which invoke Web Method...........
    public String invokeJSON(String methName, String Namespace, String soapaction, String url) {
        SoapObject request = new SoapObject(Namespace, methName);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
        try {
            // Invole web service
            androidHttpTransport.call(soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();

            return result;

        } catch (Exception e) {
            return e.toString();
        }

    }

    // Method which invoke Send Web Method...........
    public String invokeRequestMRDetails(String methName, String Namespace, String soapaction, String url,
                                         String mrname, String requestmrname, String mrcode, String requestmrcode, String mrdeviceid,
                                         String requestmrdeviceid) {
        SoapObject request = new SoapObject(Namespace, methName);
        request.addProperty(requestmrname, mrname);
        request.addProperty(requestmrcode, mrcode);
        request.addProperty(requestmrdeviceid, mrdeviceid);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
        try {
            // Invole web service
            androidHttpTransport.call(soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();

            return result;

        } catch (Exception e) {
            return e.toString();
        }

    }

    public String invokeSendBillDetails(String methName, String Namespace, String soapaction, String url,
                                        String MONTH, String requestMONTH,
                                        String READDATE, String requestREADDATE,
                                        String RRNO, String requestRRNO,
                                        String MRCODE, String requestMRCODE,
                                        String SSNO, String requestSSNO,
                                        String CONSNO, String requestCONSNO,
                                        String PH_NO, String requestPH_NO,
                                        String ASDAMT, String requestASDAMT,
                                        String IODAMT, String requestIODAMT,
                                        String PFVAL, String requestPFVAL,
                                        String BMDVAL, String requestBMDVAL,
                                        String INTEREST_AMT, String requestINTEREST_AMT,
                                        String BILL_DAYS, String requestBILL_DAYS,
                                        String TCCODE, String requestTCCODE,
                                        String PRES_RDG, String requestPRES_RDG,
                                        String PRES_STS, String requestPRES_STS,
                                        String UNITS, String requestUNITS,
                                        String FIX, String requestFIX,
                                        String ENGCHG, String requestENGCHG,
                                        String REBATE_AMOUNT, String requestREBATE_AMOUNT,
                                        String TAX_AMOUNT, String requestTAX_AMOUNT,
                                        String BMD_PENALTY, String requestBMD_PENALTY,
                                        String PF_PENALTY, String requestPF_PENALTY,
                                        String PAYABLE, String requestPAYABLE,
                                        String BILLDATE, String requestBILLDATE,
                                        String BILLTIME, String requestBILLTIME,
                                        String TOD_CURRENT1, String requestTOD_CURRENT1,
                                        String TOD_CURRENT3, String requestTOD_CURRENT3,
                                        String GOK_SUBSIDY, String requestGOK_SUBSIDY,
                                        String DEM_REVENUE, String requestDEM_REVENUE,
                                        String GPS_LAT, String requestGPS_LAT,
                                        String GPS_LONG, String requestGPS_LONG,
                                        String IMGADD, String requestIMGADD,
                                        String PAYABLE_REAL, String requestPAYABLE_REAL,
                                        String PAYABLE_PROFIT, String requestPAYABLE_PROFIT,
                                        String PAYABLE_LOSS, String requestPAYABLE_LOSS) {
        SoapObject request = new SoapObject(Namespace, methName);
        request.addProperty(requestMONTH, MONTH);
        request.addProperty(requestREADDATE, READDATE);
        request.addProperty(requestRRNO, RRNO);
        request.addProperty(requestMRCODE, MRCODE);
        request.addProperty(requestSSNO, SSNO);
        request.addProperty(requestCONSNO, CONSNO);
        request.addProperty(requestPH_NO, PH_NO);
        request.addProperty(requestASDAMT, ASDAMT);
        request.addProperty(requestIODAMT, IODAMT);
        request.addProperty(requestPFVAL, PFVAL);
        request.addProperty(requestBMDVAL, BMDVAL);
        request.addProperty(requestINTEREST_AMT, INTEREST_AMT);
        request.addProperty(requestBILL_DAYS, BILL_DAYS);
        request.addProperty(requestTCCODE, TCCODE);
        request.addProperty(requestPRES_RDG, PRES_RDG);
        request.addProperty(requestPRES_STS, PRES_STS);
        request.addProperty(requestUNITS, UNITS);
        request.addProperty(requestFIX, FIX);
        request.addProperty(requestENGCHG, ENGCHG);
        request.addProperty(requestREBATE_AMOUNT, REBATE_AMOUNT);
        request.addProperty(requestTAX_AMOUNT, TAX_AMOUNT);
        request.addProperty(requestBMD_PENALTY, BMD_PENALTY);
        request.addProperty(requestPF_PENALTY, PF_PENALTY);
        request.addProperty(requestPAYABLE, PAYABLE);
        request.addProperty(requestBILLDATE, BILLDATE);
        request.addProperty(requestBILLTIME, BILLTIME);
        request.addProperty(requestTOD_CURRENT1, TOD_CURRENT1);
        request.addProperty(requestTOD_CURRENT3, TOD_CURRENT3);
        request.addProperty(requestGOK_SUBSIDY, GOK_SUBSIDY);
        request.addProperty(requestDEM_REVENUE, DEM_REVENUE);
        request.addProperty(requestGPS_LAT, GPS_LAT);
        request.addProperty(requestGPS_LONG, GPS_LONG);
        request.addProperty(requestIMGADD, IMGADD);
        request.addProperty(requestPAYABLE_REAL, PAYABLE_REAL);
        request.addProperty(requestPAYABLE_PROFIT, PAYABLE_PROFIT);
        request.addProperty(requestPAYABLE_LOSS, PAYABLE_LOSS);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
        try {
            // Invole web service
            androidHttpTransport.call(soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();

            return result;

        } catch (Exception e) {
            return e.toString();
        }

    }

    public String invokeDisconnectionMRwise(String methName, String Namespace, String soapaction, String url,
                                            String fromdate, String todate, String mrcode, String selection, String deviceid) {
        SoapObject request = new SoapObject(Namespace, methName);
        request.addProperty("fromdate", fromdate);
        request.addProperty("todate", todate);
        request.addProperty("mrcode", mrcode);
        request.addProperty("arrears_demand", selection);
        request.addProperty("Device_Id", deviceid);
        functionCalls.logStatus("fromdate: "+fromdate);
        functionCalls.logStatus("todate: "+todate);
        functionCalls.logStatus("mrcode: "+mrcode);
        functionCalls.logStatus("arrears_demand: "+selection);
        functionCalls.logStatus("Device_Id: "+deviceid);
        functionCalls.logStatus("URL: "+url);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
        try {
            // Invole web service
            androidHttpTransport.call(soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();

            return result;

        } catch (Exception e) {
            return e.toString();
        }

    }

    public String sendbilledfile(String methName, String Namespace, String IMEINumber, String requestIMEINumber,
                                 String URL, String Soapaction) {
        SoapObject request = new SoapObject(Namespace, methName);
        //Create property
        request.addProperty(requestIMEINumber, IMEINumber);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            // Invole web service
            androidHttpTransport.call(Soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();

            return result;

        } catch (Exception e) {
            return e.toString();
        }

    }

    public String sendbilleddata(String methName, String Namespace, String billeddata, String URL, String Soapaction) {
        SoapObject request = new SoapObject(Namespace, methName);
        //Create property
        request.addProperty("Data", billeddata);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            // Invole web service
            androidHttpTransport.call(Soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

    public String sendDisconnectiondetails(String methName, String Namespace, String URL, String Soapaction, String... params) {
        SoapObject request = new SoapObject(Namespace, methName);
        //Create property
        request.addProperty("Consid", params[0]);
        request.addProperty("Type", params[1]);
        request.addProperty("LastReading", params[2]);
        request.addProperty("LastReadingDate", params[3]);
        request.addProperty("Remarks", params[4]);
        request.addProperty("Subdivcode", params[5]);
        request.addProperty("MMyyyy", params[6]);
        functionCalls.logStatus("Consid: "+params[0]);
        functionCalls.logStatus("Type: "+params[1]);
        functionCalls.logStatus("LastReading: "+params[2]);
        functionCalls.logStatus("LastReadingDate: "+params[3]);
        functionCalls.logStatus("Remarks: "+params[4]);
        functionCalls.logStatus("Subdivcode: "+params[5]);
        functionCalls.logStatus("MMyyyy: "+params[6]);
        functionCalls.logStatus("URL: "+URL);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            // Invole web service
            androidHttpTransport.call(Soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

    public String sendReconnectiondetails(String methName, String Namespace, String url, String Soapaction, String... params) {
        SoapObject request = new SoapObject(Namespace, methName);
        //Create property
        request.addProperty("Consid", params[0]);
        request.addProperty("SubDivCode", params[1]);
        request.addProperty("MMyyyy", params[2]);
        functionCalls.logStatus("Consid: "+params[0]);
        functionCalls.logStatus("SubDivCode: "+params[1]);
        functionCalls.logStatus("MMyyyy: "+params[2]);
        functionCalls.logStatus("URL: "+url);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
        try {
            // Invole web service
            androidHttpTransport.call(Soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

    public String searchCustomerdetails(String methName, String Namespace, String url, String Soapaction, String... params) {
        SoapObject request = new SoapObject(Namespace, methName);
        //Create property
        request.addProperty("RRNO", params[0]);
        request.addProperty("CONSID", params[1]);
        functionCalls.logStatus("RRNO: "+params[0]);
        functionCalls.logStatus("CONSID: "+params[1]);
        functionCalls.logStatus("URL: "+url);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
        try {
            // Invole web service
            androidHttpTransport.call(Soapaction + methName, envelope);
            //Got Webservice response
            String result = envelope.getResponse().toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

}
