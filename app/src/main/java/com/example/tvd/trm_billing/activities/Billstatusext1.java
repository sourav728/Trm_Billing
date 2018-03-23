package com.example.tvd.trm_billing.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.tvd.trm_billing.R;
import com.example.tvd.trm_billing.database.Databasehelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Billstatusext1 extends Activity {
    TextView tvsts;
    String stsname, rrno, custid, name;
    Databasehelper dbh;
    Cursor c1;
    ArrayList<String> list1, list2, list3, list4, list5, list6, list7;
    ArrayList<Map<String, String>> mylist;
    HashMap<String, String> map;
    ListView lv;
    SimpleAdapter sa;
    int slno;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billstatusext1);
        tvsts = (TextView) findViewById(R.id.textView1);
        Intent in = getIntent();
        Bundle sts = in.getExtras();
        stsname = sts.getString("status");

        list1 = new ArrayList<String>();
        list2 = new ArrayList<String>();
        list3 = new ArrayList<String>();
        list4 = new ArrayList<String>();

        lv = (ListView) findViewById(R.id.listView1);
        mylist = new ArrayList<Map<String,String>>();

        dbh = new Databasehelper(this);
        dbh.openDatabase();

        if (stsname != null) {
            tvsts.setText(stsname);
        }

        c1 = dbh.notbilled();
        if (c1.getCount() > 0) {
            while(c1.moveToNext()){
                rrno = c1.getString(c1.getColumnIndex("RRNO"));
                custid = c1.getString(c1.getColumnIndex("CONSNO"));
                name = c1.getString(c1.getColumnIndex("NAME"));
                list2.add(name);
                list3.add(custid);
                list4.add(rrno);
            }

            for (int i = 0; i < list2.size(); i++) {
                slno = slno + 1;
                list1.add(""+slno);
            }

            for (int i = 0; i < list2.size(); i++) {
                map = new HashMap<String, String>();
                map.put("Value1", list1.get(i));
                map.put("Value2", list2.get(i));
                map.put("Value3", list3.get(i));
                map.put("Value4", list4.get(i));
                map.put("Value5", "---");
                map.put("Value6", "---");
                mylist.add(map);
            }

            sa = new SimpleAdapter(this, mylist, R.layout.billstat2,
                    new String[]{"Value1", "Value2", "Value3", "Value4", "Value5", "Value6"},
                    new int[]{R.id.textView1, R.id.textView3, R.id.textView5, R.id.textView7, R.id.textView9, R.id.textView11});

            lv.setAdapter(sa);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String UnBilledRRno = list3.get(position);
                    String Billing = "FromUnbilled";
                    Intent forbilling = new Intent(Billstatusext1.this, ConsumerBilling.class);
                    forbilling.putExtra("RRNO", UnBilledRRno);
                    forbilling.putExtra("billing", Billing);
                    forbilling.putExtra("STATUS", stsname);
                    startActivity(forbilling);
                }
            });
        }
        else {
            Intent send = new Intent(Billstatusext1.this, BillStatus.class);
            send.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(send);
        }
    }

    @Override
    public void onBackPressed() {
        Intent send = new Intent(Billstatusext1.this, BillStatus.class);
        send.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(send);
        super.onBackPressed();
    }

}
