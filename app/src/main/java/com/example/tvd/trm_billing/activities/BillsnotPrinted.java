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

import com.example.tvd.trm_billing.R;
import com.example.tvd.trm_billing.Reportdisp;
import com.example.tvd.trm_billing.database.Databasehelper;
import com.example.tvd.trm_billing.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BillsnotPrinted extends Activity {
    ListView lv;
    SimpleAdapter sa;
    Databasehelper dbh;
    Cursor c;
    ArrayList<String> list1, list2, list3, list4;
    ArrayList<Map<String, String>> mylist;
    HashMap<String, String> map;
    String billedname, billedrrno, billedpayment, billprinted, printed;
    double payment;
    int slno;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billsnot_printed);
        lv = (ListView) findViewById(R.id.listView1);
        list1 = new ArrayList<String>();
        list2 = new ArrayList<String>();
        list3 = new ArrayList<String>();
        list4 = new ArrayList<String>();
        mylist = new ArrayList<Map<String,String>>();
        billprinted = "check_bill_printed";

        dbh = new Databasehelper(BillsnotPrinted.this);
        dbh.openDatabase();
        c = dbh.billed();
        while(c.moveToNext()) {
            printed = c.getString(c.getColumnIndex("BILL_PRINTED"));
            if (printed.equals("N")) {
                billedrrno = c.getString(c.getColumnIndex("CONSNO"));
                billedname = c.getString(c.getColumnIndex("NAME"));
                billedpayment = c.getString(c.getColumnIndex("PAYABLE"));
                payment = Double.parseDouble(billedpayment);
                list2.add(billedname);
                list3.add(billedrrno);
                list4.add(""+payment);
            }
        }

	/*    if (!printed.equals("N")) {
	    	Intent back = new Intent(BillsnotPrinted.this, TabActivity.class);
			back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(back);
		}*/

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
            mylist.add(map);
        }

        sa = new SimpleAdapter(this, mylist, R.layout.billsnotprinted1,
                new String[]{"Value1", "Value2", "Value3", "Value4"},
                new int[]{R.id.textView6, R.id.textView1, R.id.textView3, R.id.textView5});

        lv.setAdapter(sa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View child, int spos,
                                    long dpos) {
                String rrno = list3.get(spos);
                Intent nxt = new Intent(BillsnotPrinted.this, Reportdisp.class);
                nxt.putExtra("billing", billprinted);
                nxt.putExtra("RRNO", rrno);
                nxt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(nxt);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(BillsnotPrinted.this, HomeFragment.class);
        back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(back);
        super.onBackPressed();
    }

}
