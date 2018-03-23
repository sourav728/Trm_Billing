package com.example.tvd.trm_billing.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tvd.trm_billing.MainActivity;
import com.example.tvd.trm_billing.R;
import com.example.tvd.trm_billing.activities.ConsumerBilling;
import com.example.tvd.trm_billing.database.Databasehelper;
import com.example.tvd.trm_billing.values.FunctionCalls;
import com.example.tvd.trm_billing.values.GetSetValues;

import static com.example.tvd.trm_billing.values.ConstantValues.BILLING_CUT_OFF_TIME;


public class HomeFragment extends Fragment {
    public static final int CUST_DLG = 1;
    public static final int BILLINGSTS_DLG = 2;
    private static final int BILLING_ERROR = 3;
    private static final int BILLING_TIME_OVER = 4;
    private static final int DLG_SPOT_COLLECTION = 5;
    private static final int DLG_SPOT_COLLECTION_REPORT = 6;

    Button enter, statusbychart, statusbymaps, tcbilling, bt_htbilling, bt_spot_collection, bt_solar_roof_billing, bt_spot_collection_reports;
    TextView tvtotal;
    Databasehelper dbh;
    Cursor c, c1, c2, c3, c4, c5;
    String billingstatus="", counttobill="", billing_flag="";
    FunctionCalls fcall;
    GetSetValues getSetValues;
    boolean pack = false;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.fragment_home, container, false);
        enter = (Button) rv.findViewById(R.id.button1);
        statusbychart = (Button) rv.findViewById(R.id.button3);
        statusbymaps = (Button) rv.findViewById(R.id.button4);
        tcbilling = (Button) rv.findViewById(R.id.button5);
        bt_htbilling = (Button) rv.findViewById(R.id.ht_billing_btn);
        //bt_spot_collection = (Button) rv.findViewById(R.id.spot_collection_btn);
        bt_solar_roof_billing = (Button) rv.findViewById(R.id.solar_billing_btn);
        //bt_spot_collection_reports = (Button) rv.findViewById(R.id.spot_collection_reports_btn);
        tvtotal = (TextView) rv.findViewById(R.id.textView4);
        fcall = new FunctionCalls();
        getSetValues = ((MainActivity) getActivity()).gettersetterValues();

        dbh = new Databasehelper(getActivity());
        dbh.openDatabase();

        c5 = dbh.counttobill();
        c5.moveToNext();
        counttobill = c5.getString(c5.getColumnIndex("CUST"));
        tvtotal.setText(counttobill);

        enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (fcall.compare_end_billing_time(fcall.convertTo24Hour(BILLING_CUT_OFF_TIME))) {
                    c1 = dbh.subdivdetails();
                    c1.moveToNext();
                    billingstatus = c1.getString(c1.getColumnIndex("BILLING_STATUS"));
                    billing_flag = c1.getString(c1.getColumnIndex("COLLECTION_FLAG"));
                    if (billingstatus.equals("BO")) {
                        if (billing_flag.equals("N") || billing_flag.equals("n")) {
                            if (dbh.getData().getCount() > 0) {
                                Intent next = new Intent(getActivity(), ConsumerBilling.class);
                                startActivity(next);
                            } else showDialog(BILLING_ERROR);
                        } else fcall.showtoastatcenter(getActivity(), "Can not take Billing from this Device");
                    } else showDialog(BILLINGSTS_DLG);
                } else showDialog(BILLING_TIME_OVER);
            }
        });

        return rv;
    }

    protected void showDialog(int id) {
        Dialog d = null;
        switch (id) {
            case CUST_DLG:
                AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                ab.setTitle("Subdiv Billing Maps");
                ab.setMessage("No Billed Values");
                ab.setNeutralButton("OK", null);
                d = ab.create();
                d.show();
                break;

            case BILLINGSTS_DLG:
                AlertDialog.Builder ab1 = new AlertDialog.Builder(getActivity());
                ab1.setTitle("Subdiv Billing");
                ab1.setMessage("Billing Completed");
                ab1.setNeutralButton("OK", null);
                d = ab1.create();
                d.show();
                break;

            case BILLING_ERROR:
                AlertDialog.Builder billerror = new AlertDialog.Builder(getActivity());
                billerror.setTitle("Billing");
                billerror.setMessage("Sorry no Records found in Billing so cannot take bills..");
                billerror.setNeutralButton("OK", null);
                d = billerror.create();
                d.show();
                break;

            case BILLING_TIME_OVER:
                AlertDialog.Builder time_over = new AlertDialog.Builder(getActivity());
                time_over.setTitle("Time Over");
                time_over.setCancelable(false);
                time_over.setMessage("Billing Time is over... Can not take billing now...");
                time_over.setNeutralButton("OK", null);
                d = time_over.create();
                d.show();
                break;

            case DLG_SPOT_COLLECTION:
                AlertDialog.Builder spot_collection = new AlertDialog.Builder(getActivity());
                spot_collection.setTitle("Spot Collection");
                spot_collection.setCancelable(false);
                spot_collection.setMessage("No billed records to take collection...");
                spot_collection.setPositiveButton("OK", null);
                d = spot_collection.create();
                d.show();
                break;

            case DLG_SPOT_COLLECTION_REPORT:
                AlertDialog.Builder ab5 = new AlertDialog.Builder(getActivity());
                ab5.setTitle("Summary Collections");
                ab5.setMessage("No Payment Report Because No Payment Values");
                ab5.setNeutralButton("OK", null);
                d = ab5.create();
                d.show();
                break;
        }
    }

}
