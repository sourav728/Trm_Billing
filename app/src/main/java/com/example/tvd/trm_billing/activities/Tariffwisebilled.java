package com.example.tvd.trm_billing.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tvd.trm_billing.R;
import com.example.tvd.trm_billing.database.Databasehelper;
import com.example.tvd.trm_billing.values.GetSetValues;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Tariffwisebilled extends Activity {
    Databasehelper dbh;
    Cursor c1;
    String tariffname, tariffwisepayable, tariffwisebilled, selectedtariffname, tariffwise;
    GetSetValues getset;
    ListView detailsview;
    ArrayList<GetSetValues> tariffwisebilledlist;
    MyBaseAdapter tariffwisebilledadapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tariffwisebilled);

        detailsview = (ListView) findViewById(R.id.listView1);
        tariffwisebilledlist = new ArrayList<GetSetValues>();
        tariffwisebilledadapter = new MyBaseAdapter(this, tariffwisebilledlist);
        detailsview.setAdapter(tariffwisebilledadapter);

        dbh = new Databasehelper(this);
        dbh.openDatabase();

        tariffwise = "tariffwisebilled";

        c1 = dbh.tariffwisebilling();
        DecimalFormat num = new DecimalFormat("##.00");
        while(c1.moveToNext()) {
            getset = new GetSetValues();
            tariffname = c1.getString(c1.getColumnIndex("TARIFF_NAME"));
            tariffwisebilled = c1.getString(c1.getColumnIndex("BILLED"));
            tariffwisepayable = c1.getString(c1.getColumnIndex("PAYABLE"));
            getset.settariffname(tariffname);
            getset.settariffbilled(tariffwisebilled);
            getset.settariffpayable(num.format(Double.parseDouble(tariffwisepayable)));
            tariffwisebilledlist.add(getset);
            tariffwisebilledadapter.notifyDataSetChanged();
        }

        detailsview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View child, int spos,
                                    long dpos) {
                TextView selectedname = (TextView) child.findViewById(R.id.textView1);
                selectedtariffname = selectedname.getText().toString();
                Intent tariffdetails = new Intent(Tariffwisebilled.this, Billstatusext.class);
                tariffdetails.putExtra("Reportstatus", tariffwise);
                tariffdetails.putExtra("Tariffname", selectedtariffname);
                startActivity(tariffdetails);
            }
        });
    }

    public class MyBaseAdapter extends BaseAdapter {

        ArrayList<GetSetValues> mylist = new ArrayList<GetSetValues>();
        LayoutInflater inflater;
        Context context;

        public MyBaseAdapter (Context context, ArrayList<GetSetValues> arraylist) {
            this.mylist = arraylist;
            this.context = context;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return mylist.size();
        }

        @Override
        public Object getItem(int pos) {
            return mylist.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int pos, View view, ViewGroup parent) {
            view = inflater.inflate(R.layout.tariffdisp, null);
            TextView tvtariff = (TextView) view.findViewById(R.id.textView1);
            TextView tvbilled = (TextView) view.findViewById(R.id.textView2);
            TextView tvpayable = (TextView) view.findViewById(R.id.textView3);
            tvtariff.setText(mylist.get(pos).gettariffname());
            tvbilled.setText(mylist.get(pos).gettariffbilled());
            tvpayable.setText(mylist.get(pos).gettariffpayable());
            return view;
        }

    }

}
