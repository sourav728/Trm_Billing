package com.example.tvd.trm_billing.values;

import android.bluetooth.BluetoothDevice;
import android.database.Cursor;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class GetSetValues implements Serializable {

	private String filename="", filesize="", rrno="", custid="", name="", legfelio="", deviceid="", address="", arrears="", tccode="", poleno="",
			billamount="", paidamount="", mode="", receiptno="", receiptdate="", chequedate="", reporttype="", villagename="",
			chequeno="", bankname="", count="", amt="", amountinwords="", receipttime="", transactionid="", tarrifnames="",
			chqdishonourflag="", chqdishonourdate="", bankname1="", mapsbillingname="", date="", total="", billed="", unbilled="",
			mapsbillingrrno="", mapsbillingtariff="", mapsbillingpayable="", totalamount="", role="", mapspaymentname="", mapspaymentrrno="",
            mapspaymentamount="", receiptcount="", disstype="", mtrreading="", mapspaymentmode="", units="", taxonecrate="", intrrate="",
            chqamount="", tariff="", chequebank="", slno="", tariffname="", tariffbilled="", tariffpayable="", cashcollecteddate="",
            payamount="", DeviceName="", DeviceAddress="", FTP_HOST="", FTP_USER="", FTP_PASS="", IMEINumber="", Phnumber="", calltype="",
            calldate="", callduration="", comp_mrcode="", comp_name="", comp_desp="", comp_image="", comp_datetime="", comp_id="",
            comp_status="", comp_remarks="", comp_resolved_by="", pay_customer_name="", pay_customer_id="", pay_customer_rrno="",
			pay_customer_recpt_no="", pay_customer_bill_amount="", pay_customer_paid_amount="", pay_amount_in_words_1="",
            pay_amount_in_words_2="", upload_failure_status="", billed_account_id="", billed_ir="", billed_fr="", billed_payable="",
            billed_slno="", bt_printers="", app_version="", coll_mrcode="", coll_mrname ="", coll_flag="", coll_start_time="",
            coll_end_time="", coll_limit="", coll_date="", coll_subdiv_code="", pay_re_slno="", pay_re_custid="", pay_re_recpt="",
            pay_re_amount="", collection_started="", coll_recpt_no="", billing_file_name="", ht_account_id, ht_rrno, ht_legfolio,
            ht_consumer_name, ht_address, ht_tariff, ht_mf, ht_contractdemand, ht_prevkwh, ht_prevkvah, ht_prevkvah3, ht_fdrname, ht_tc_code,
            ht_so, ht_rrbtflag, ht_month_year, ht_date, ht_prev_rdg_date, ht_slabs, ht_arrears, ht_tod_flag, ht_tod_previous1,
            ht_tod_previous3, ht_tod_previous2, ht_tod_previous4, ht_billing_month="", ht_billing_readdate="", ht_billing_rrno="",
            ht_billing_name="", ht_billing_add1="", ht_billing_tariff="", ht_billing_mf="", ht_billing_prevstat="", ht_billing_avgcon="",
            ht_billing_linemin="", ht_billing_sanchp="", ht_billing_sanckw="", ht_billing_prvred="", ht_billing_fr="", ht_billing_ir="",
            ht_billing_dlcount="", ht_billing_arrears="", ht_billing_pf_flag="", ht_billing_billfor="", ht_billing_mrcode="",
            ht_billing_legfol="", ht_billing_oddeven="", ht_billing_ssno="", ht_billing_consno="", ht_billing_ph_no="",
            ht_billing_rebate_flag="", ht_billing_rrebate="", ht_billing_extra1="", ht_billing_data1="", ht_billing_extra2="",
            ht_billing_data2="", ht_billing_deposit="", ht_billing_mtrdigit="", ht_billing_asdamt="", ht_billing_iodamt="",
            ht_billing_pfval="", ht_billing_bmdval="", ht_billing_bill_no="", ht_billing_interest_amt="", ht_billing_cap_flag="",
            ht_billing_tod_flag="", ht_billing_tod_previous1="", ht_billing_tod_previous3="", ht_billing_int_on_dep="",
            ht_billing_so_feeder_tc_pole="", ht_billing_tariff_name="", ht_billing_prev_read_date="", ht_billing_bill_days="",
            ht_billing_mtr_serial_no="", ht_billing_chq_disshonour_flag="", ht_billing_chq_dishonour_date="", ht_billing_fdrname="",
            ht_billing_tccode="", ht_billing_mtr_flag="", ht_billing_pres_rdg="", ht_billing_pres_sts="", ht_billing_units="",
            ht_billing_fix="", ht_billing_engchg="", ht_billing_rebate_amount="", ht_billing_tax_amount="", ht_billing_bmd_penalty="",
            ht_billing_pf_penalty="", ht_billing_payable="", ht_billing_billdate="", ht_billing_billtime="", ht_billing_tod_current1="",
            ht_billing_tod_current3="", ht_billing_gok_subsidy="", ht_billing_dem_revenue="", ht_billing_gps_lat="", ht_billing_gps_long="",
            ht_billing_online_flag="", ht_billing_battery_charge="", ht_billing_signal_strength="", ht_billing_imgadd="",
            ht_billing_payable_real="", ht_billing_payable_profit="", ht_billing_payable_loss="", ht_billing_bill_printed="",
            ht_billing_fslab1="", ht_billing_fslab2="", ht_billing_fslab3="", ht_billing_fslab4="", ht_billing_fslab5="",
            ht_billing_eslab1="", ht_billing_eslab2="", ht_billing_eslab3="", ht_billing_eslab4="", ht_billing_eslab5="",
            ht_billing_eslab6="", ht_billing_charitable_rbt_amt="", ht_billing_solar_rbt_amt="", ht_billing_fl_rbt_amt="",
            ht_billing_handicap_rbt_amt="", ht_billing_pl_rbt_amt="", ht_billing_ipset_rbt_amt="", ht_billing_rebatefromccb_amt="",
            ht_billing_tod_charges="", ht_billing_pf_penality_amt="", ht_billing_exload_mdpenality="", ht_billing_curr_bill_amount="",
            ht_billing_rounding_amount="", ht_billing_due_date="", ht_billing_disconn_date="", ht_billing_creadj="", ht_billing_preadkvah="",
            disconnection_success_result="", reconnection_result="", disconnection_longitude="", disconnection_latitude="",
            lt_billing_month, lt_billing_readdate, lt_billing_rrno, lt_billing_name, lt_billing_add1, lt_billing_tariff, lt_billing_mf,
            lt_billing_prevstat, lt_billing_avgcon, lt_billing_linemin, lt_billing_sanchp, lt_billing_sanckw, lt_billing_prvred,
            lt_billing_fr, lt_billing_ir, lt_billing_dlcount, lt_billing_arrears, lt_billing_pf_flag, lt_billing_billfor, lt_billing_mrcode,
            lt_billing_legfol, lt_billing_oddeven, lt_billing_ssno, lt_billing_consno, lt_billing_ph_no, lt_billing_rebate_flag,
            lt_billing_rrebate, lt_billing_extra1, lt_billing_data1, lt_billing_extra2, lt_billing_data2, lt_billing_deposit,
            lt_billing_mtrdigit, lt_billing_asdamt, lt_billing_iodamt, lt_billing_pfval, lt_billing_bmdval, lt_billing_bill_no,
            lt_billing_interest_amt, lt_billing_cap_flag, lt_billing_tod_flag, lt_billing_tod_previous1, lt_billing_tod_previous3,
            lt_billing_int_on_dep, lt_billing_so_feeder_tc_pole, lt_billing_tariff_name, lt_billing_prev_read_date, lt_billing_bill_days,
            lt_billing_mtr_serial_no, lt_billing_chq_disshonour_flag, lt_billing_chq_dishonour_date, lt_billing_fdrname, lt_billing_tccode,
            lt_billing_mtr_flag, lt_billing_pres_rdg, lt_billing_pres_sts, lt_billing_units, lt_billing_fix, lt_billing_engchg,
            lt_billing_rebate_amount, lt_billing_tax_amount, lt_billing_bmd_penalty, lt_billing_pf_penalty, lt_billing_payable,
            lt_billing_billdate, lt_billing_billtime, lt_billing_tod_current1, lt_billing_tod_current3, lt_billing_gok_subsidy,
            lt_billing_dem_revenue, lt_billing_gps_lat, lt_billing_gps_long, lt_billing_online_flag, lt_billing_battery_charge,
            lt_billing_signal_strength, lt_billing_imgadd, lt_billing_payable_real, lt_billing_payable_profit, lt_billing_payable_loss,
            lt_billing_bill_printed, lt_billing_fslab1, lt_billing_fslab2, lt_billing_fslab3, lt_billing_fslab4, lt_billing_fslab5,
            lt_billing_eslab1, lt_billing_eslab2, lt_billing_eslab3, lt_billing_eslab4, lt_billing_eslab5, lt_billing_eslab6,
            lt_billing_charitable_rbt_amt, lt_billing_solar_rbt_amt, lt_billing_fl_rbt_amt, lt_billing_handicap_rbt_amt,
            lt_billing_pl_rbt_amt, lt_billing_ipset_rbt_amt, lt_billing_rebatefromccb_amt, lt_billing_tod_charges, lt_billing_pf_penality_amt,
            lt_billing_exload_mdpenality, lt_billing_curr_bill_amount, lt_billing_rounding_amount, lt_billing_due_date,
            lt_billing_disconn_date, lt_billing_creadj, lt_billing_preadkvah, lt_billing_aadhaar, lt_billing_mail, lt_billing_mtr_digit,
            lt_billing_id, coll_posting_receipt_no="", coll_app_version="", solar_cust_name="", solar_cust_rrno="", solar_im_prev_read="",
            solar_ex_prev_read="", solar_date="", solar_flag="", dis_prev_read="", coll_reports_slno="", coll_reports_acc_id="",
            coll_reports_recpt_no="", coll_reports_amount="";
    private JSONObject collection_result;
	private int FTP_PORT;
	private double latitude, longitude, EC;
	private BluetoothDevice BluDevice;
	private Cursor query;
	private boolean upload_status_success = false, upload_status_failure = false;
    private String login_role="", customer_name="", customer_rrno="", customer_accid="", customer_tariff="", customer_bill_amount="",
            customer_LF_no="", posting_unique_id="", pay_amount_in_words_3="", coll_re_slno="", coll_re_custid="", coll_re_recpt="",
            coll_re_amount="";
    private double amount = 0;
    private ArrayList<GetSetValues> arrayList;

	public GetSetValues() {
	}

	/*public ArrayList<Pay> children;*/

	public void setserial(String value) {
		this.slno = value;
	}

	public String getserial() {
		return slno;
	}
	
	public void setpayamount(String value){
		this.payamount = value;
	}
	
	public String getpayamount(){
		return payamount;
	}
	
	public void setdeviceid(String value) {
		this.deviceid = value;
	}

	public String getdeviceid() {
		return deviceid;
	}

	public void setlatitude(double value) {
		this.latitude = value;
	}

	public double getlatitude() {
		return latitude;
	}

	public void setlongitude(double value) {
		this.longitude = value;
	}

	public double getlongitude() {
		return longitude;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public void setname(String value) {
		this.name = value;
	}

	public String getname() {
		return name;
	}

	public void setrrno(String value) {
		this.rrno = value;
	}

	public String getrrno() {
		return rrno;
	}

	public void setcustid(String value) {
		this.custid = value;
	}

	public String getcustid() {
		return custid;
	}

	public void setlegfelio(String value) {
		this.legfelio = value;
	}

	public String getlegfelio() {
		return legfelio;
	}

	public void setbillamount(String value) {
		this.billamount = value;
	}

	public String getbillamount() {
		return billamount;
	}

	public void setpaidamount(String value) {
		this.paidamount = value;
	}

	public String getpaidamount() {
		return paidamount;
	}

	public void setmode(String value) {
		this.mode = value;
	}

	public String getmode() {
		return mode;
	}

	public void setreceiptno(String value) {
		this.receiptno = value;
	}

	public String getreceiptno() {
		return receiptno;
	}

	public void setreceiptdate(String value) {
		this.receiptdate = value;
	}

	public String getreceiptdate() {
		return receiptdate;
	}

	public void setreceipttime(String value) {
		this.receipttime = value;
	}

	public String getreceipttime() {
		return receipttime;
	}
	
	public void setreceiptcount(String value) {
		this.receiptcount = value;
	}

	public String getreceiptcount() {
		return receiptcount;
	}

	public void setchequedate(String value) {
		this.chequedate = value;
	}

	public String getchequedate() {
		return chequedate;
	}
	
	public void setchequebank(String value) {
		this.chequebank = value;
	}

	public String getchequebank() {
		return chequebank;
	}

	public void setchequeno(String value) {
		this.chequeno = value;
	}

	public String getchequeno() {
		return chequeno;
	}

	public void setbankname(String value) {
		this.bankname = value;
	}

	public String getbankname() {
		return bankname;
	}

	public void setcount(String value) {
		this.count = value;
	}

	public String getcount() {
		return count;
	}

	public void setamt(String value) {
		this.amt = value;
	}

	public String getamt() {
		return amt;
	}

	public void setchequeamount(String value) {
		this.chqamount = value;
	}

	public String getchequeamount() {
		return chqamount;
	}

	public void setamountinwords(String value) {
		this.amountinwords = value;
	}

	public String getamountinwords() {
		return amountinwords;
	}

	public void setchqdishonourflag(String value) {
		this.chqdishonourflag = value;
	}

	public String getchqdishonourflag() {
		return chqdishonourflag;
	}

	public void setchqdishonourdate(String value) {
		this.chqdishonourdate = value;
	}

	public String getchqdishonourdate() {
		return chqdishonourdate;
	}

	public void setbankname1(String value) {
		this.bankname1 = value;
	}

	public String getbankname1() {
		return bankname1;
	}

	public void setmapsbillingname(String value) {
		this.mapsbillingname = value;
	}

	public String getmapsbillingname() {
		return mapsbillingname;
	}

	public void setmapsbillingrrno(String value) {
		this.mapsbillingrrno = value;
	}

	public String getmapsbillingrrno() {
		return mapsbillingrrno;
	}

	public void setmapsbillingtariff(String value) {
		this.mapsbillingtariff = value;
	}

	public String getmapsbillingtariff() {
		return mapsbillingtariff;
	}

	public void setmapsbillingpayable(String value) {
		this.mapsbillingpayable = value;
	}

	public String getmapsbillingpayable() {
		return mapsbillingpayable;
	}

	public void setmapspaymentname(String value) {
		this.mapspaymentname = value;
	}

	public String getmapspaymentname() {
		return mapspaymentname;
	}

	public void setmapspaymentrrno(String value) {
		this.mapspaymentrrno = value;
	}

	public String getmapspaymentrrno() {
		return mapspaymentrrno;
	}

	public void setmapspaymentamount(String value) {
		this.mapspaymentamount = value;
	}

	public String getmapspaymentamount() {
		return mapspaymentamount;
	}

	public void setmapspaymentmode(String value) {
		this.mapspaymentmode = value;
	}

	public String getmapspaymentmode() {
		return mapspaymentmode;
	}

	public void setEC(double Ec) {
		this.EC = Ec;
	}

	public double getEC() {
		return EC;
	}

	public void setunits(String units) {
		this.units = units;
	}

	public String getunits() {
		return units;
	}

	public void settaxonecrate(String rate) {
		this.taxonecrate = rate;
	}

	public String gettaxonecrate() {
		return taxonecrate;
	}

	public void setintrrate(String rate) {
		this.intrrate = rate;
	}

	public String getintrrate() {
		return intrrate;
	}

	public void settariff(String rate) {
		this.tariff = rate;
	}

	public String gettariff() {
		return tariff;
	}
	
	public void settariffname(String tariffname) {
		this.tariffname = tariffname;
	}

	public String gettariffname() {
		return tariffname;
	}
	
	public void settariffbilled(String tariffbilled) {
		this.tariffbilled = tariffbilled;
	}

	public String gettariffbilled() {
		return tariffbilled;
	}
	
	public void settariffpayable(String tariffpayable) {
		this.tariffpayable = tariffpayable;
	}

	public String gettariffpayable() {
		return tariffpayable;
	}
	
	public void settransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public String gettransactionid() {
		return transactionid;
	}
	
	public void setcashcollecteddate(String cashdate) {
		this.cashcollecteddate = cashdate;
	}

	public String getcashcollecteddate() {
		return cashcollecteddate;
	}
	
	public void setsumamount(String amount) {
		this.totalamount = amount;
	}

	public String getsumamount() {
		return totalamount;
	}
	
	/*public ArrayList<Pay> getChildren()
    {
        return children;
    }
     
    public void setChildren(ArrayList<Pay> children)
    {
        this.children = children;
    }*/
    
    public void setDeviceName(String devicename){
		this.DeviceName = devicename;
	}
	
	public String getDeviceName(){
		return this.DeviceName;
	}
	
	public void setDeviceAddress(String deviceadd){
		this.DeviceAddress = deviceadd;
	}
	
	public String getDeviceAddress(){
		return this.DeviceAddress;
	}
	
	public void setDevice(BluetoothDevice device){
		this.BluDevice = device;
	}
	
	public BluetoothDevice getDevice(){
		return this.BluDevice;
	}
	
	public void setaddress(String address){
		this.address = address;
	}
	
	public String getaddress(){
		return this.address;
	}
	
	public void setarrears(String arrears){
		this.arrears = arrears;
	}
	
	public String getarrears(){
		return this.arrears;
	}
	
	public void settccode(String tccode){
		this.tccode = tccode;
	}
	
	public String gettccode(){
		return this.tccode;
	}
	
	public void setpoleno(String poleno){
		this.poleno = poleno;
	}
	
	public String getpoleno(){
		return this.poleno;
	}
	
	public void setreporttype(String reporttype){
		this.reporttype = reporttype;
	}
	
	public String getreporttype(){
		return this.reporttype;//
	}
	
	public void setvillagename(String villagename){
		this.villagename = villagename;
	}
	
	public String getvillagename(){
		return this.villagename;
	}
	
	public void settarrifnames(String tarrifnames){
		this.tarrifnames = tarrifnames;
	}
	
	public String gettarrifnames(){
		return this.tarrifnames;
	}
	
	public void setdate(String date){
		this.date = date;
	}
	
	public String getdate(){
		return this.date;
	}
	
	public void settotal(String total){
		this.total = total;
	}
	
	public String gettotal(){
		return this.total;
	}
	
	public void setbilled(String billed){
		this.billed = billed;
	}
	
	public String getbilled(){
		return this.billed;
	}
	
	public void setunbilled(String unbilled){
		this.unbilled = unbilled;
	}
	
	public String getunbilled(){
		return this.unbilled;
	}
	
	public void setmtrreading(String mtrreading){
		this.mtrreading = mtrreading;
	}
	
	public String getmtrreading(){
		return this.mtrreading;
	}
	
	public void setdisstype(String disstype){
		this.disstype = disstype;
	}
	
	public String getdisstype(){
		return this.disstype;
	}
	
	public void setrole(String role){
		this.role = role;
	}
	
	public String getrole(){
		return this.role;
	}
	
	public void setcursor(Cursor query){
		this.query = query;
	}
	
	public Cursor getcursor(){
		return this.query;
	}
	
	public void setFTP_HOST(String FTP_HOST) {
		this.FTP_HOST = FTP_HOST;
	}

	public String getFTP_HOST() {
		return FTP_HOST;
	}
	
	public void setFTP_USER(String FTP_USER) {
		this.FTP_USER = FTP_USER;
	}

	public String getFTP_USER() {
		return FTP_USER;
	}
	
	public void setFTP_PASS(String FTP_PASS) {
		this.FTP_PASS = FTP_PASS;
	}

	public String getFTP_PASS() {
		return FTP_PASS;
	}
	
	public void setFTP_PORT(int FTP_PORT) {
		this.FTP_PORT = FTP_PORT;
	}

	public int getFTP_PORT() {
		return FTP_PORT;
	}
	
	public void setIMEINumber(String IMEINumber) {
		this.IMEINumber = IMEINumber;
	}

	public String getIMEINumber() {
		return IMEINumber;
	}

	public GetSetValues(String phnumber, String calltype, String calldate, String callduration) {
		Phnumber = phnumber;
		this.calltype = calltype;
		this.calldate = calldate;
		this.callduration = callduration;
	}

	public String getPhnumber() {
		return Phnumber;
	}

	public String getCalltype() {
		return calltype;
	}

	public String getCalldate() {
		return calldate;
	}

	public String getCallduration() {
		return callduration;
	}

	public GetSetValues(String comp_mrcode, String comp_name, String comp_desp, String comp_image, String comp_datetime,
                        String comp_id, String comp_status, String comp_remarks, String comp_resolved_by) {
		this.comp_mrcode = comp_mrcode;
		this.comp_name = comp_name;
		this.comp_desp = comp_desp;
		this.comp_image = comp_image;
		this.comp_datetime = comp_datetime;
		this.comp_id = comp_id;
		this.comp_status = comp_status;
		this.comp_remarks = comp_remarks;
		this.comp_resolved_by = comp_resolved_by;
	}

	public String getComp_mrcode() {
		return comp_mrcode;
	}

	public void setComp_mrcode(String comp_mrcode) {
		this.comp_mrcode = comp_mrcode;
	}

	public String getComp_name() {
		return comp_name;
	}

	public void setComp_name(String comp_name) {
		this.comp_name = comp_name;
	}

	public String getComp_desp() {
		return comp_desp;
	}

	public void setComp_desp(String comp_desp) {
		this.comp_desp = comp_desp;
	}

	public String getComp_image() {
		return comp_image;
	}

	public void setComp_image(String comp_image) {
		this.comp_image = comp_image;
	}

	public String getComp_datetime() {
		return comp_datetime;
	}

	public void setComp_datetime(String comp_datetime) {
		this.comp_datetime = comp_datetime;
	}

	public String getComp_id() {
		return comp_id;
	}

	public void setComp_id(String comp_id) {
		this.comp_id = comp_id;
	}

	public String getComp_status() {
		return comp_status;
	}

	public void setComp_status(String comp_status) {
		this.comp_status = comp_status;
	}

	public String getComp_remarks() {
		return comp_remarks;
	}

	public void setComp_remarks(String comp_remarks) {
		this.comp_remarks = comp_remarks;
	}

	public String getComp_resolved_by() {
		return comp_resolved_by;
	}

	public void setComp_resolved_by(String comp_resolved_by) {
		this.comp_resolved_by = comp_resolved_by;
	}

	public boolean isUpload_status_success() {
		return upload_status_success;
	}

	public void setUpload_status_success(boolean upload_status_success) {
		this.upload_status_success = upload_status_success;
	}

	public boolean isUpload_status_failure() {
		return upload_status_failure;
	}

	public void setUpload_status_failure(boolean upload_status_failure) {
		this.upload_status_failure = upload_status_failure;
	}

	public String getPay_customer_name() {
		return pay_customer_name;
	}

	public void setPay_customer_name(String pay_customer_name) {
		this.pay_customer_name = pay_customer_name;
	}

	public String getPay_customer_id() {
		return pay_customer_id;
	}

	public void setPay_customer_id(String pay_customer_id) {
		this.pay_customer_id = pay_customer_id;
	}

	public String getPay_customer_rrno() {
		return pay_customer_rrno;
	}

	public void setPay_customer_rrno(String pay_customer_rrno) {
		this.pay_customer_rrno = pay_customer_rrno;
	}

	public String getPay_customer_recpt_no() {
		return pay_customer_recpt_no;
	}

	public void setPay_customer_recpt_no(String pay_customer_recpt_no) {
		this.pay_customer_recpt_no = pay_customer_recpt_no;
	}

	public String getPay_customer_bill_amount() {
		return pay_customer_bill_amount;
	}

	public void setPay_customer_bill_amount(String pay_customer_bill_amount) {
		this.pay_customer_bill_amount = pay_customer_bill_amount;
	}

	public String getPay_customer_paid_amount() {
		return pay_customer_paid_amount;
	}

	public void setPay_customer_paid_amount(String pay_customer_paid_amount) {
		this.pay_customer_paid_amount = pay_customer_paid_amount;
	}

	/*public String getPay_amount_in_words() {
		return pay_amount_in_words;
	}

	public void setPay_amount_in_words(String pay_amount_in_words) {
		this.pay_amount_in_words = pay_amount_in_words;
	}*/

    public String getPay_amount_in_words_1() {
        return pay_amount_in_words_1;
    }

    public void setPay_amount_in_words_1(String pay_amount_in_words_1) {
        this.pay_amount_in_words_1 = pay_amount_in_words_1;
    }

    public String getPay_amount_in_words_2() {
        return pay_amount_in_words_2;
    }

    public void setPay_amount_in_words_2(String pay_amount_in_words_2) {
        this.pay_amount_in_words_2 = pay_amount_in_words_2;
    }

    public String getBilled_account_id() {
        return billed_account_id;
    }

    public void setBilled_account_id(String billed_account_id) {
        this.billed_account_id = billed_account_id;
    }

    public String getBilled_ir() {
        return billed_ir;
    }

    public void setBilled_ir(String billed_ir) {
        this.billed_ir = billed_ir;
    }

    public String getBilled_fr() {
        return billed_fr;
    }

    public void setBilled_fr(String billed_fr) {
        this.billed_fr = billed_fr;
    }

    public String getBilled_payable() {
        return billed_payable;
    }

    public void setBilled_payable(String billed_payable) {
        this.billed_payable = billed_payable;
    }

    public String getBilled_slno() {
        return billed_slno;
    }

    public void setBilled_slno(String billed_slno) {
        this.billed_slno = billed_slno;
    }

    public String getBt_printers() {
        return bt_printers;
    }

    public void setBt_printers(String bt_printers) {
        this.bt_printers = bt_printers;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getUpload_failure_status() {
        return upload_failure_status;
    }

    public void setUpload_failure_status(String upload_failure_status) {
        this.upload_failure_status = upload_failure_status;
    }

    public String getColl_mrcode() {
        return coll_mrcode;
    }

    public void setColl_mrcode(String coll_mrcode) {
        this.coll_mrcode = coll_mrcode;
    }

    public String getColl_mrname() {
        return coll_mrname;
    }

    public void setColl_mrname(String coll_mrname) {
        this.coll_mrname = coll_mrname;
    }

    public String getColl_flag() {
        return coll_flag;
    }

    public void setColl_flag(String coll_flag) {
        this.coll_flag = coll_flag;
    }

    public String getColl_start_time() {
        return coll_start_time;
    }

    public void setColl_start_time(String coll_start_time) {
        this.coll_start_time = coll_start_time;
    }

    public String getColl_end_time() {
        return coll_end_time;
    }

    public void setColl_end_time(String coll_end_time) {
        this.coll_end_time = coll_end_time;
    }

    public String getColl_limit() {
        return coll_limit;
    }

    public void setColl_limit(String coll_limit) {
        this.coll_limit = coll_limit;
    }

    public String getColl_date() {
        return coll_date;
    }

    public void setColl_date(String coll_date) {
        this.coll_date = coll_date;
    }

    public String getColl_subdiv_code() {
        return coll_subdiv_code;
    }

    public void setColl_subdiv_code(String coll_subdiv_code) {
        this.coll_subdiv_code = coll_subdiv_code;
    }

    public String getPay_re_slno() {
        return pay_re_slno;
    }

    public void setPay_re_slno(String pay_re_slno) {
        this.pay_re_slno = pay_re_slno;
    }

    public String getPay_re_custid() {
        return pay_re_custid;
    }

    public void setPay_re_custid(String pay_re_custid) {
        this.pay_re_custid = pay_re_custid;
    }

    public String getPay_re_recpt() {
        return pay_re_recpt;
    }

    public void setPay_re_recpt(String pay_re_recpt) {
        this.pay_re_recpt = pay_re_recpt;
    }

    public String getPay_re_amount() {
        return pay_re_amount;
    }

    public void setPay_re_amount(String pay_re_amount) {
        this.pay_re_amount = pay_re_amount;
    }

    public String getCollection_started() {
        return collection_started;
    }

    public void setCollection_started(String collection_started) {
        this.collection_started = collection_started;
    }

    public JSONObject getCollection_result() {
        return collection_result;
    }

    public void setCollection_result(JSONObject collection_result) {
        this.collection_result = collection_result;
    }

    public String getColl_recpt_no() {
        return coll_recpt_no;
    }

    public void setColl_recpt_no(String coll_recpt_no) {
        this.coll_recpt_no = coll_recpt_no;
    }

    public String getBilling_file_name() {
        return billing_file_name;
    }

    public void setBilling_file_name(String billing_file_name) {
        this.billing_file_name = billing_file_name;
    }

    public String getHt_account_id() {
        return ht_account_id;
    }

    public void setHt_account_id(String ht_account_id) {
        this.ht_account_id = ht_account_id;
    }

    public String getHt_rrno() {
        return ht_rrno;
    }

    public void setHt_rrno(String ht_rrno) {
        this.ht_rrno = ht_rrno;
    }

    public String getHt_legfolio() {
        return ht_legfolio;
    }

    public void setHt_legfolio(String ht_legfolio) {
        this.ht_legfolio = ht_legfolio;
    }

    public String getHt_consumer_name() {
        return ht_consumer_name;
    }

    public void setHt_consumer_name(String ht_consumer_name) {
        this.ht_consumer_name = ht_consumer_name;
    }

    public String getHt_address() {
        return ht_address;
    }

    public void setHt_address(String ht_address) {
        this.ht_address = ht_address;
    }

    public String getHt_tariff() {
        return ht_tariff;
    }

    public void setHt_tariff(String ht_tariff) {
        this.ht_tariff = ht_tariff;
    }

    public String getHt_mf() {
        return ht_mf;
    }

    public void setHt_mf(String ht_mf) {
        this.ht_mf = ht_mf;
    }

    public String getHt_contractdemand() {
        return ht_contractdemand;
    }

    public void setHt_contractdemand(String ht_contractdemand) {
        this.ht_contractdemand = ht_contractdemand;
    }

    public String getHt_prevkwh() {
        return ht_prevkwh;
    }

    public void setHt_prevkwh(String ht_prevkwh) {
        this.ht_prevkwh = ht_prevkwh;
    }

    public String getHt_prevkvah() {
        return ht_prevkvah;
    }

    public void setHt_prevkvah(String ht_prevkvah) {
        this.ht_prevkvah = ht_prevkvah;
    }

    public String getHt_prevkvah3() {
        return ht_prevkvah3;
    }

    public void setHt_prevkvah3(String ht_prevkvah3) {
        this.ht_prevkvah3 = ht_prevkvah3;
    }

    public String getHt_fdrname() {
        return ht_fdrname;
    }

    public void setHt_fdrname(String ht_fdrname) {
        this.ht_fdrname = ht_fdrname;
    }

    public String getHt_tc_code() {
        return ht_tc_code;
    }

    public void setHt_tc_code(String ht_tc_code) {
        this.ht_tc_code = ht_tc_code;
    }

    public String getHt_so() {
        return ht_so;
    }

    public void setHt_so(String ht_so) {
        this.ht_so = ht_so;
    }

    public String getHt_rrbtflag() {
        return ht_rrbtflag;
    }

    public void setHt_rrbtflag(String ht_rrbtflag) {
        this.ht_rrbtflag = ht_rrbtflag;
    }

    public String getHt_month_year() {
        return ht_month_year;
    }

    public void setHt_month_year(String ht_month_year) {
        this.ht_month_year = ht_month_year;
    }

    public String getHt_date() {
        return ht_date;
    }

    public void setHt_date(String ht_date) {
        this.ht_date = ht_date;
    }

    public String getHt_prev_rdg_date() {
        return ht_prev_rdg_date;
    }

    public void setHt_prev_rdg_date(String ht_prev_rdg_date) {
        this.ht_prev_rdg_date = ht_prev_rdg_date;
    }

    public String getHt_slabs() {
        return ht_slabs;
    }

    public void setHt_slabs(String ht_slabs) {
        this.ht_slabs = ht_slabs;
    }

    public String getHt_arrears() {
        return ht_arrears;
    }

    public void setHt_arrears(String ht_arrears) {
        this.ht_arrears = ht_arrears;
    }

    public String getHt_tod_flag() {
        return ht_tod_flag;
    }

    public void setHt_tod_flag(String ht_tod_flag) {
        this.ht_tod_flag = ht_tod_flag;
    }

    public String getHt_tod_previous1() {
        return ht_tod_previous1;
    }

    public void setHt_tod_previous1(String ht_tod_previous1) {
        this.ht_tod_previous1 = ht_tod_previous1;
    }

    public String getHt_tod_previous3() {
        return ht_tod_previous3;
    }

    public void setHt_tod_previous3(String ht_tod_previous3) {
        this.ht_tod_previous3 = ht_tod_previous3;
    }

    public String getHt_tod_previous2() {
        return ht_tod_previous2;
    }

    public void setHt_tod_previous2(String ht_tod_previous2) {
        this.ht_tod_previous2 = ht_tod_previous2;
    }

    public String getHt_tod_previous4() {
        return ht_tod_previous4;
    }

    public void setHt_tod_previous4(String ht_tod_previous4) {
        this.ht_tod_previous4 = ht_tod_previous4;
    }

    public String getHt_billing_month() {
        return ht_billing_month;
    }

    public void setHt_billing_month(String ht_billing_month) {
        this.ht_billing_month = ht_billing_month;
    }

    public String getHt_billing_readdate() {
        return ht_billing_readdate;
    }

    public void setHt_billing_readdate(String ht_billing_readdate) {
        this.ht_billing_readdate = ht_billing_readdate;
    }

    public String getHt_billing_rrno() {
        return ht_billing_rrno;
    }

    public void setHt_billing_rrno(String ht_billing_rrno) {
        this.ht_billing_rrno = ht_billing_rrno;
    }

    public String getHt_billing_name() {
        return ht_billing_name;
    }

    public void setHt_billing_name(String ht_billing_name) {
        this.ht_billing_name = ht_billing_name;
    }

    public String getHt_billing_add1() {
        return ht_billing_add1;
    }

    public void setHt_billing_add1(String ht_billing_add1) {
        this.ht_billing_add1 = ht_billing_add1;
    }

    public String getHt_billing_tariff() {
        return ht_billing_tariff;
    }

    public void setHt_billing_tariff(String ht_billing_tariff) {
        this.ht_billing_tariff = ht_billing_tariff;
    }

    public String getHt_billing_mf() {
        return ht_billing_mf;
    }

    public void setHt_billing_mf(String ht_billing_mf) {
        this.ht_billing_mf = ht_billing_mf;
    }

    public String getHt_billing_prevstat() {
        return ht_billing_prevstat;
    }

    public void setHt_billing_prevstat(String ht_billing_prevstat) {
        this.ht_billing_prevstat = ht_billing_prevstat;
    }

    public String getHt_billing_avgcon() {
        return ht_billing_avgcon;
    }

    public void setHt_billing_avgcon(String ht_billing_avgcon) {
        this.ht_billing_avgcon = ht_billing_avgcon;
    }

    public String getHt_billing_linemin() {
        return ht_billing_linemin;
    }

    public void setHt_billing_linemin(String ht_billing_linemin) {
        this.ht_billing_linemin = ht_billing_linemin;
    }

    public String getHt_billing_sanchp() {
        return ht_billing_sanchp;
    }

    public void setHt_billing_sanchp(String ht_billing_sanchp) {
        this.ht_billing_sanchp = ht_billing_sanchp;
    }

    public String getHt_billing_sanckw() {
        return ht_billing_sanckw;
    }

    public void setHt_billing_sanckw(String ht_billing_sanckw) {
        this.ht_billing_sanckw = ht_billing_sanckw;
    }

    public String getHt_billing_prvred() {
        return ht_billing_prvred;
    }

    public void setHt_billing_prvred(String ht_billing_prvred) {
        this.ht_billing_prvred = ht_billing_prvred;
    }

    public String getHt_billing_fr() {
        return ht_billing_fr;
    }

    public void setHt_billing_fr(String ht_billing_fr) {
        this.ht_billing_fr = ht_billing_fr;
    }

    public String getHt_billing_ir() {
        return ht_billing_ir;
    }

    public void setHt_billing_ir(String ht_billing_ir) {
        this.ht_billing_ir = ht_billing_ir;
    }

    public String getHt_billing_dlcount() {
        return ht_billing_dlcount;
    }

    public void setHt_billing_dlcount(String ht_billing_dlcount) {
        this.ht_billing_dlcount = ht_billing_dlcount;
    }

    public String getHt_billing_arrears() {
        return ht_billing_arrears;
    }

    public void setHt_billing_arrears(String ht_billing_arrears) {
        this.ht_billing_arrears = ht_billing_arrears;
    }

    public String getHt_billing_pf_flag() {
        return ht_billing_pf_flag;
    }

    public void setHt_billing_pf_flag(String ht_billing_pf_flag) {
        this.ht_billing_pf_flag = ht_billing_pf_flag;
    }

    public String getHt_billing_billfor() {
        return ht_billing_billfor;
    }

    public void setHt_billing_billfor(String ht_billing_billfor) {
        this.ht_billing_billfor = ht_billing_billfor;
    }

    public String getHt_billing_mrcode() {
        return ht_billing_mrcode;
    }

    public void setHt_billing_mrcode(String ht_billing_mrcode) {
        this.ht_billing_mrcode = ht_billing_mrcode;
    }

    public String getHt_billing_legfol() {
        return ht_billing_legfol;
    }

    public void setHt_billing_legfol(String ht_billing_legfol) {
        this.ht_billing_legfol = ht_billing_legfol;
    }

    public String getHt_billing_oddeven() {
        return ht_billing_oddeven;
    }

    public void setHt_billing_oddeven(String ht_billing_oddeven) {
        this.ht_billing_oddeven = ht_billing_oddeven;
    }

    public String getHt_billing_ssno() {
        return ht_billing_ssno;
    }

    public void setHt_billing_ssno(String ht_billing_ssno) {
        this.ht_billing_ssno = ht_billing_ssno;
    }

    public String getHt_billing_consno() {
        return ht_billing_consno;
    }

    public void setHt_billing_consno(String ht_billing_consno) {
        this.ht_billing_consno = ht_billing_consno;
    }

    public String getHt_billing_ph_no() {
        return ht_billing_ph_no;
    }

    public void setHt_billing_ph_no(String ht_billing_ph_no) {
        this.ht_billing_ph_no = ht_billing_ph_no;
    }

    public String getHt_billing_rebate_flag() {
        return ht_billing_rebate_flag;
    }

    public void setHt_billing_rebate_flag(String ht_billing_rebate_flag) {
        this.ht_billing_rebate_flag = ht_billing_rebate_flag;
    }

    public String getHt_billing_rrebate() {
        return ht_billing_rrebate;
    }

    public void setHt_billing_rrebate(String ht_billing_rrebate) {
        this.ht_billing_rrebate = ht_billing_rrebate;
    }

    public String getHt_billing_extra1() {
        return ht_billing_extra1;
    }

    public void setHt_billing_extra1(String ht_billing_extra1) {
        this.ht_billing_extra1 = ht_billing_extra1;
    }

    public String getHt_billing_data1() {
        return ht_billing_data1;
    }

    public void setHt_billing_data1(String ht_billing_data1) {
        this.ht_billing_data1 = ht_billing_data1;
    }

    public String getHt_billing_extra2() {
        return ht_billing_extra2;
    }

    public void setHt_billing_extra2(String ht_billing_extra2) {
        this.ht_billing_extra2 = ht_billing_extra2;
    }

    public String getHt_billing_data2() {
        return ht_billing_data2;
    }

    public void setHt_billing_data2(String ht_billing_data2) {
        this.ht_billing_data2 = ht_billing_data2;
    }

    public String getHt_billing_deposit() {
        return ht_billing_deposit;
    }

    public void setHt_billing_deposit(String ht_billing_deposit) {
        this.ht_billing_deposit = ht_billing_deposit;
    }

    public String getHt_billing_mtrdigit() {
        return ht_billing_mtrdigit;
    }

    public void setHt_billing_mtrdigit(String ht_billing_mtrdigit) {
        this.ht_billing_mtrdigit = ht_billing_mtrdigit;
    }

    public String getHt_billing_asdamt() {
        return ht_billing_asdamt;
    }

    public void setHt_billing_asdamt(String ht_billing_asdamt) {
        this.ht_billing_asdamt = ht_billing_asdamt;
    }

    public String getHt_billing_iodamt() {
        return ht_billing_iodamt;
    }

    public void setHt_billing_iodamt(String ht_billing_iodamt) {
        this.ht_billing_iodamt = ht_billing_iodamt;
    }

    public String getHt_billing_pfval() {
        return ht_billing_pfval;
    }

    public void setHt_billing_pfval(String ht_billing_pfval) {
        this.ht_billing_pfval = ht_billing_pfval;
    }

    public String getHt_billing_bmdval() {
        return ht_billing_bmdval;
    }

    public void setHt_billing_bmdval(String ht_billing_bmdval) {
        this.ht_billing_bmdval = ht_billing_bmdval;
    }

    public String getHt_billing_bill_no() {
        return ht_billing_bill_no;
    }

    public void setHt_billing_bill_no(String ht_billing_bill_no) {
        this.ht_billing_bill_no = ht_billing_bill_no;
    }

    public String getHt_billing_interest_amt() {
        return ht_billing_interest_amt;
    }

    public void setHt_billing_interest_amt(String ht_billing_interest_amt) {
        this.ht_billing_interest_amt = ht_billing_interest_amt;
    }

    public String getHt_billing_cap_flag() {
        return ht_billing_cap_flag;
    }

    public void setHt_billing_cap_flag(String ht_billing_cap_flag) {
        this.ht_billing_cap_flag = ht_billing_cap_flag;
    }

    public String getHt_billing_tod_flag() {
        return ht_billing_tod_flag;
    }

    public void setHt_billing_tod_flag(String ht_billing_tod_flag) {
        this.ht_billing_tod_flag = ht_billing_tod_flag;
    }

    public String getHt_billing_tod_previous1() {
        return ht_billing_tod_previous1;
    }

    public void setHt_billing_tod_previous1(String ht_billing_tod_previous1) {
        this.ht_billing_tod_previous1 = ht_billing_tod_previous1;
    }

    public String getHt_billing_tod_previous3() {
        return ht_billing_tod_previous3;
    }

    public void setHt_billing_tod_previous3(String ht_billing_tod_previous3) {
        this.ht_billing_tod_previous3 = ht_billing_tod_previous3;
    }

    public String getHt_billing_int_on_dep() {
        return ht_billing_int_on_dep;
    }

    public void setHt_billing_int_on_dep(String ht_billing_int_on_dep) {
        this.ht_billing_int_on_dep = ht_billing_int_on_dep;
    }

    public String getHt_billing_so_feeder_tc_pole() {
        return ht_billing_so_feeder_tc_pole;
    }

    public void setHt_billing_so_feeder_tc_pole(String ht_billing_so_feeder_tc_pole) {
        this.ht_billing_so_feeder_tc_pole = ht_billing_so_feeder_tc_pole;
    }

    public String getHt_billing_tariff_name() {
        return ht_billing_tariff_name;
    }

    public void setHt_billing_tariff_name(String ht_billing_tariff_name) {
        this.ht_billing_tariff_name = ht_billing_tariff_name;
    }

    public String getHt_billing_prev_read_date() {
        return ht_billing_prev_read_date;
    }

    public void setHt_billing_prev_read_date(String ht_billing_prev_read_date) {
        this.ht_billing_prev_read_date = ht_billing_prev_read_date;
    }

    public String getHt_billing_bill_days() {
        return ht_billing_bill_days;
    }

    public void setHt_billing_bill_days(String ht_billing_bill_days) {
        this.ht_billing_bill_days = ht_billing_bill_days;
    }

    public String getHt_billing_mtr_serial_no() {
        return ht_billing_mtr_serial_no;
    }

    public void setHt_billing_mtr_serial_no(String ht_billing_mtr_serial_no) {
        this.ht_billing_mtr_serial_no = ht_billing_mtr_serial_no;
    }

    public String getHt_billing_chq_disshonour_flag() {
        return ht_billing_chq_disshonour_flag;
    }

    public void setHt_billing_chq_disshonour_flag(String ht_billing_chq_disshonour_flag) {
        this.ht_billing_chq_disshonour_flag = ht_billing_chq_disshonour_flag;
    }

    public String getHt_billing_chq_dishonour_date() {
        return ht_billing_chq_dishonour_date;
    }

    public void setHt_billing_chq_dishonour_date(String ht_billing_chq_dishonour_date) {
        this.ht_billing_chq_dishonour_date = ht_billing_chq_dishonour_date;
    }

    public String getHt_billing_fdrname() {
        return ht_billing_fdrname;
    }

    public void setHt_billing_fdrname(String ht_billing_fdrname) {
        this.ht_billing_fdrname = ht_billing_fdrname;
    }

    public String getHt_billing_tccode() {
        return ht_billing_tccode;
    }

    public void setHt_billing_tccode(String ht_billing_tccode) {
        this.ht_billing_tccode = ht_billing_tccode;
    }

    public String getHt_billing_mtr_flag() {
        return ht_billing_mtr_flag;
    }

    public void setHt_billing_mtr_flag(String ht_billing_mtr_flag) {
        this.ht_billing_mtr_flag = ht_billing_mtr_flag;
    }

    public String getHt_billing_pres_rdg() {
        return ht_billing_pres_rdg;
    }

    public void setHt_billing_pres_rdg(String ht_billing_pres_rdg) {
        this.ht_billing_pres_rdg = ht_billing_pres_rdg;
    }

    public String getHt_billing_pres_sts() {
        return ht_billing_pres_sts;
    }

    public void setHt_billing_pres_sts(String ht_billing_pres_sts) {
        this.ht_billing_pres_sts = ht_billing_pres_sts;
    }

    public String getHt_billing_units() {
        return ht_billing_units;
    }

    public void setHt_billing_units(String ht_billing_units) {
        this.ht_billing_units = ht_billing_units;
    }

    public String getHt_billing_fix() {
        return ht_billing_fix;
    }

    public void setHt_billing_fix(String ht_billing_fix) {
        this.ht_billing_fix = ht_billing_fix;
    }

    public String getHt_billing_engchg() {
        return ht_billing_engchg;
    }

    public void setHt_billing_engchg(String ht_billing_engchg) {
        this.ht_billing_engchg = ht_billing_engchg;
    }

    public String getHt_billing_rebate_amount() {
        return ht_billing_rebate_amount;
    }

    public void setHt_billing_rebate_amount(String ht_billing_rebate_amount) {
        this.ht_billing_rebate_amount = ht_billing_rebate_amount;
    }

    public String getHt_billing_tax_amount() {
        return ht_billing_tax_amount;
    }

    public void setHt_billing_tax_amount(String ht_billing_tax_amount) {
        this.ht_billing_tax_amount = ht_billing_tax_amount;
    }

    public String getHt_billing_bmd_penalty() {
        return ht_billing_bmd_penalty;
    }

    public void setHt_billing_bmd_penalty(String ht_billing_bmd_penalty) {
        this.ht_billing_bmd_penalty = ht_billing_bmd_penalty;
    }

    public String getHt_billing_pf_penalty() {
        return ht_billing_pf_penalty;
    }

    public void setHt_billing_pf_penalty(String ht_billing_pf_penalty) {
        this.ht_billing_pf_penalty = ht_billing_pf_penalty;
    }

    public String getHt_billing_payable() {
        return ht_billing_payable;
    }

    public void setHt_billing_payable(String ht_billing_payable) {
        this.ht_billing_payable = ht_billing_payable;
    }

    public String getHt_billing_billdate() {
        return ht_billing_billdate;
    }

    public void setHt_billing_billdate(String ht_billing_billdate) {
        this.ht_billing_billdate = ht_billing_billdate;
    }

    public String getHt_billing_billtime() {
        return ht_billing_billtime;
    }

    public void setHt_billing_billtime(String ht_billing_billtime) {
        this.ht_billing_billtime = ht_billing_billtime;
    }

    public String getHt_billing_tod_current1() {
        return ht_billing_tod_current1;
    }

    public void setHt_billing_tod_current1(String ht_billing_tod_current1) {
        this.ht_billing_tod_current1 = ht_billing_tod_current1;
    }

    public String getHt_billing_tod_current3() {
        return ht_billing_tod_current3;
    }

    public void setHt_billing_tod_current3(String ht_billing_tod_current3) {
        this.ht_billing_tod_current3 = ht_billing_tod_current3;
    }

    public String getHt_billing_gok_subsidy() {
        return ht_billing_gok_subsidy;
    }

    public void setHt_billing_gok_subsidy(String ht_billing_gok_subsidy) {
        this.ht_billing_gok_subsidy = ht_billing_gok_subsidy;
    }

    public String getHt_billing_dem_revenue() {
        return ht_billing_dem_revenue;
    }

    public void setHt_billing_dem_revenue(String ht_billing_dem_revenue) {
        this.ht_billing_dem_revenue = ht_billing_dem_revenue;
    }

    public String getHt_billing_gps_lat() {
        return ht_billing_gps_lat;
    }

    public void setHt_billing_gps_lat(String ht_billing_gps_lat) {
        this.ht_billing_gps_lat = ht_billing_gps_lat;
    }

    public String getHt_billing_gps_long() {
        return ht_billing_gps_long;
    }

    public void setHt_billing_gps_long(String ht_billing_gps_long) {
        this.ht_billing_gps_long = ht_billing_gps_long;
    }

    public String getHt_billing_online_flag() {
        return ht_billing_online_flag;
    }

    public void setHt_billing_online_flag(String ht_billing_online_flag) {
        this.ht_billing_online_flag = ht_billing_online_flag;
    }

    public String getHt_billing_battery_charge() {
        return ht_billing_battery_charge;
    }

    public void setHt_billing_battery_charge(String ht_billing_battery_charge) {
        this.ht_billing_battery_charge = ht_billing_battery_charge;
    }

    public String getHt_billing_signal_strength() {
        return ht_billing_signal_strength;
    }

    public void setHt_billing_signal_strength(String ht_billing_signal_strength) {
        this.ht_billing_signal_strength = ht_billing_signal_strength;
    }

    public String getHt_billing_imgadd() {
        return ht_billing_imgadd;
    }

    public void setHt_billing_imgadd(String ht_billing_imgadd) {
        this.ht_billing_imgadd = ht_billing_imgadd;
    }

    public String getHt_billing_payable_real() {
        return ht_billing_payable_real;
    }

    public void setHt_billing_payable_real(String ht_billing_payable_real) {
        this.ht_billing_payable_real = ht_billing_payable_real;
    }

    public String getHt_billing_payable_profit() {
        return ht_billing_payable_profit;
    }

    public void setHt_billing_payable_profit(String ht_billing_payable_profit) {
        this.ht_billing_payable_profit = ht_billing_payable_profit;
    }

    public String getHt_billing_payable_loss() {
        return ht_billing_payable_loss;
    }

    public void setHt_billing_payable_loss(String ht_billing_payable_loss) {
        this.ht_billing_payable_loss = ht_billing_payable_loss;
    }

    public String getHt_billing_bill_printed() {
        return ht_billing_bill_printed;
    }

    public void setHt_billing_bill_printed(String ht_billing_bill_printed) {
        this.ht_billing_bill_printed = ht_billing_bill_printed;
    }

    public String getHt_billing_fslab1() {
        return ht_billing_fslab1;
    }

    public void setHt_billing_fslab1(String ht_billing_fslab1) {
        this.ht_billing_fslab1 = ht_billing_fslab1;
    }

    public String getHt_billing_fslab2() {
        return ht_billing_fslab2;
    }

    public void setHt_billing_fslab2(String ht_billing_fslab2) {
        this.ht_billing_fslab2 = ht_billing_fslab2;
    }

    public String getHt_billing_fslab3() {
        return ht_billing_fslab3;
    }

    public void setHt_billing_fslab3(String ht_billing_fslab3) {
        this.ht_billing_fslab3 = ht_billing_fslab3;
    }

    public String getHt_billing_fslab4() {
        return ht_billing_fslab4;
    }

    public void setHt_billing_fslab4(String ht_billing_fslab4) {
        this.ht_billing_fslab4 = ht_billing_fslab4;
    }

    public String getHt_billing_fslab5() {
        return ht_billing_fslab5;
    }

    public void setHt_billing_fslab5(String ht_billing_fslab5) {
        this.ht_billing_fslab5 = ht_billing_fslab5;
    }

    public String getHt_billing_eslab1() {
        return ht_billing_eslab1;
    }

    public void setHt_billing_eslab1(String ht_billing_eslab1) {
        this.ht_billing_eslab1 = ht_billing_eslab1;
    }

    public String getHt_billing_eslab2() {
        return ht_billing_eslab2;
    }

    public void setHt_billing_eslab2(String ht_billing_eslab2) {
        this.ht_billing_eslab2 = ht_billing_eslab2;
    }

    public String getHt_billing_eslab3() {
        return ht_billing_eslab3;
    }

    public void setHt_billing_eslab3(String ht_billing_eslab3) {
        this.ht_billing_eslab3 = ht_billing_eslab3;
    }

    public String getHt_billing_eslab4() {
        return ht_billing_eslab4;
    }

    public void setHt_billing_eslab4(String ht_billing_eslab4) {
        this.ht_billing_eslab4 = ht_billing_eslab4;
    }

    public String getHt_billing_eslab5() {
        return ht_billing_eslab5;
    }

    public void setHt_billing_eslab5(String ht_billing_eslab5) {
        this.ht_billing_eslab5 = ht_billing_eslab5;
    }

    public String getHt_billing_eslab6() {
        return ht_billing_eslab6;
    }

    public void setHt_billing_eslab6(String ht_billing_eslab6) {
        this.ht_billing_eslab6 = ht_billing_eslab6;
    }

    public String getHt_billing_charitable_rbt_amt() {
        return ht_billing_charitable_rbt_amt;
    }

    public void setHt_billing_charitable_rbt_amt(String ht_billing_charitable_rbt_amt) {
        this.ht_billing_charitable_rbt_amt = ht_billing_charitable_rbt_amt;
    }

    public String getHt_billing_solar_rbt_amt() {
        return ht_billing_solar_rbt_amt;
    }

    public void setHt_billing_solar_rbt_amt(String ht_billing_solar_rbt_amt) {
        this.ht_billing_solar_rbt_amt = ht_billing_solar_rbt_amt;
    }

    public String getHt_billing_fl_rbt_amt() {
        return ht_billing_fl_rbt_amt;
    }

    public void setHt_billing_fl_rbt_amt(String ht_billing_fl_rbt_amt) {
        this.ht_billing_fl_rbt_amt = ht_billing_fl_rbt_amt;
    }

    public String getHt_billing_handicap_rbt_amt() {
        return ht_billing_handicap_rbt_amt;
    }

    public void setHt_billing_handicap_rbt_amt(String ht_billing_handicap_rbt_amt) {
        this.ht_billing_handicap_rbt_amt = ht_billing_handicap_rbt_amt;
    }

    public String getHt_billing_pl_rbt_amt() {
        return ht_billing_pl_rbt_amt;
    }

    public void setHt_billing_pl_rbt_amt(String ht_billing_pl_rbt_amt) {
        this.ht_billing_pl_rbt_amt = ht_billing_pl_rbt_amt;
    }

    public String getHt_billing_ipset_rbt_amt() {
        return ht_billing_ipset_rbt_amt;
    }

    public void setHt_billing_ipset_rbt_amt(String ht_billing_ipset_rbt_amt) {
        this.ht_billing_ipset_rbt_amt = ht_billing_ipset_rbt_amt;
    }

    public String getHt_billing_rebatefromccb_amt() {
        return ht_billing_rebatefromccb_amt;
    }

    public void setHt_billing_rebatefromccb_amt(String ht_billing_rebatefromccb_amt) {
        this.ht_billing_rebatefromccb_amt = ht_billing_rebatefromccb_amt;
    }

    public String getHt_billing_tod_charges() {
        return ht_billing_tod_charges;
    }

    public void setHt_billing_tod_charges(String ht_billing_tod_charges) {
        this.ht_billing_tod_charges = ht_billing_tod_charges;
    }

    public String getHt_billing_pf_penality_amt() {
        return ht_billing_pf_penality_amt;
    }

    public void setHt_billing_pf_penality_amt(String ht_billing_pf_penality_amt) {
        this.ht_billing_pf_penality_amt = ht_billing_pf_penality_amt;
    }

    public String getHt_billing_exload_mdpenality() {
        return ht_billing_exload_mdpenality;
    }

    public void setHt_billing_exload_mdpenality(String ht_billing_exload_mdpenality) {
        this.ht_billing_exload_mdpenality = ht_billing_exload_mdpenality;
    }

    public String getHt_billing_curr_bill_amount() {
        return ht_billing_curr_bill_amount;
    }

    public void setHt_billing_curr_bill_amount(String ht_billing_curr_bill_amount) {
        this.ht_billing_curr_bill_amount = ht_billing_curr_bill_amount;
    }

    public String getHt_billing_rounding_amount() {
        return ht_billing_rounding_amount;
    }

    public void setHt_billing_rounding_amount(String ht_billing_rounding_amount) {
        this.ht_billing_rounding_amount = ht_billing_rounding_amount;
    }

    public String getHt_billing_due_date() {
        return ht_billing_due_date;
    }

    public void setHt_billing_due_date(String ht_billing_due_date) {
        this.ht_billing_due_date = ht_billing_due_date;
    }

    public String getHt_billing_disconn_date() {
        return ht_billing_disconn_date;
    }

    public void setHt_billing_disconn_date(String ht_billing_disconn_date) {
        this.ht_billing_disconn_date = ht_billing_disconn_date;
    }

    public String getHt_billing_creadj() {
        return ht_billing_creadj;
    }

    public void setHt_billing_creadj(String ht_billing_creadj) {
        this.ht_billing_creadj = ht_billing_creadj;
    }

    public String getHt_billing_preadkvah() {
        return ht_billing_preadkvah;
    }

    public void setHt_billing_preadkvah(String ht_billing_preadkvah) {
        this.ht_billing_preadkvah = ht_billing_preadkvah;
    }

    public String getLogin_role() {
        return login_role;
    }

    public void setLogin_role(String login_role) {
        this.login_role = login_role;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_rrno() {
        return customer_rrno;
    }

    public void setCustomer_rrno(String customer_rrno) {
        this.customer_rrno = customer_rrno;
    }

    public String getCustomer_accid() {
        return customer_accid;
    }

    public void setCustomer_accid(String customer_accid) {
        this.customer_accid = customer_accid;
    }

    public String getCustomer_tariff() {
        return customer_tariff;
    }

    public void setCustomer_tariff(String customer_tariff) {
        this.customer_tariff = customer_tariff;
    }

    public String getCustomer_bill_amount() {
        return customer_bill_amount;
    }

    public void setCustomer_bill_amount(String customer_bill_amount) {
        this.customer_bill_amount = customer_bill_amount;
    }

    public String getCustomer_LF_no() {
        return customer_LF_no;
    }

    public void setCustomer_LF_no(String customer_LF_no) {
        this.customer_LF_no = customer_LF_no;
    }

    public String getPosting_unique_id() {
        return posting_unique_id;
    }

    public void setPosting_unique_id(String posting_unique_id) {
        this.posting_unique_id = posting_unique_id;
    }

    public String getPay_amount_in_words_3() {
        return pay_amount_in_words_3;
    }

    public void setPay_amount_in_words_3(String pay_amount_in_words_3) {
        this.pay_amount_in_words_3 = pay_amount_in_words_3;
    }

    public String getColl_re_slno() {
        return coll_re_slno;
    }

    public void setColl_re_slno(String coll_re_slno) {
        this.coll_re_slno = coll_re_slno;
    }

    public String getColl_re_custid() {
        return coll_re_custid;
    }

    public void setColl_re_custid(String coll_re_custid) {
        this.coll_re_custid = coll_re_custid;
    }

    public String getColl_re_recpt() {
        return coll_re_recpt;
    }

    public void setColl_re_recpt(String coll_re_recpt) {
        this.coll_re_recpt = coll_re_recpt;
    }

    public String getColl_re_amount() {
        return coll_re_amount;
    }

    public void setColl_re_amount(String coll_re_amount) {
        this.coll_re_amount = coll_re_amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDisconnection_success_result() {
        return disconnection_success_result;
    }

    public void setDisconnection_success_result(String disconnection_success_result) {
        this.disconnection_success_result = disconnection_success_result;
    }

    public String getReconnection_result() {
        return reconnection_result;
    }

    public void setReconnection_result(String reconnection_result) {
        this.reconnection_result = reconnection_result;
    }

    public String getDisconnection_longitude() {
        return disconnection_longitude;
    }

    public void setDisconnection_longitude(String disconnection_longitude) {
        this.disconnection_longitude = disconnection_longitude;
    }

    public String getDisconnection_latitude() {
        return disconnection_latitude;
    }

    public void setDisconnection_latitude(String disconnection_latitude) {
        this.disconnection_latitude = disconnection_latitude;
    }

    public String getLt_billing_month() {
        return lt_billing_month;
    }

    public void setLt_billing_month(String lt_billing_month) {
        this.lt_billing_month = lt_billing_month;
    }

    public String getLt_billing_readdate() {
        return lt_billing_readdate;
    }

    public void setLt_billing_readdate(String lt_billing_readdate) {
        this.lt_billing_readdate = lt_billing_readdate;
    }

    public String getLt_billing_rrno() {
        return lt_billing_rrno;
    }

    public void setLt_billing_rrno(String lt_billing_rrno) {
        this.lt_billing_rrno = lt_billing_rrno;
    }

    public String getLt_billing_name() {
        return lt_billing_name;
    }

    public void setLt_billing_name(String lt_billing_name) {
        this.lt_billing_name = lt_billing_name;
    }

    public String getLt_billing_add1() {
        return lt_billing_add1;
    }

    public void setLt_billing_add1(String lt_billing_add1) {
        this.lt_billing_add1 = lt_billing_add1;
    }

    public String getLt_billing_tariff() {
        return lt_billing_tariff;
    }

    public void setLt_billing_tariff(String lt_billing_tariff) {
        this.lt_billing_tariff = lt_billing_tariff;
    }

    public String getLt_billing_mf() {
        return lt_billing_mf;
    }

    public void setLt_billing_mf(String lt_billing_mf) {
        this.lt_billing_mf = lt_billing_mf;
    }

    public String getLt_billing_prevstat() {
        return lt_billing_prevstat;
    }

    public void setLt_billing_prevstat(String lt_billing_prevstat) {
        this.lt_billing_prevstat = lt_billing_prevstat;
    }

    public String getLt_billing_avgcon() {
        return lt_billing_avgcon;
    }

    public void setLt_billing_avgcon(String lt_billing_avgcon) {
        this.lt_billing_avgcon = lt_billing_avgcon;
    }

    public String getLt_billing_linemin() {
        return lt_billing_linemin;
    }

    public void setLt_billing_linemin(String lt_billing_linemin) {
        this.lt_billing_linemin = lt_billing_linemin;
    }

    public String getLt_billing_sanchp() {
        return lt_billing_sanchp;
    }

    public void setLt_billing_sanchp(String lt_billing_sanchp) {
        this.lt_billing_sanchp = lt_billing_sanchp;
    }

    public String getLt_billing_sanckw() {
        return lt_billing_sanckw;
    }

    public void setLt_billing_sanckw(String lt_billing_sanckw) {
        this.lt_billing_sanckw = lt_billing_sanckw;
    }

    public String getLt_billing_prvred() {
        return lt_billing_prvred;
    }

    public void setLt_billing_prvred(String lt_billing_prvred) {
        this.lt_billing_prvred = lt_billing_prvred;
    }

    public String getLt_billing_fr() {
        return lt_billing_fr;
    }

    public void setLt_billing_fr(String lt_billing_fr) {
        this.lt_billing_fr = lt_billing_fr;
    }

    public String getLt_billing_ir() {
        return lt_billing_ir;
    }

    public void setLt_billing_ir(String lt_billing_ir) {
        this.lt_billing_ir = lt_billing_ir;
    }

    public String getLt_billing_dlcount() {
        return lt_billing_dlcount;
    }

    public void setLt_billing_dlcount(String lt_billing_dlcount) {
        this.lt_billing_dlcount = lt_billing_dlcount;
    }

    public String getLt_billing_arrears() {
        return lt_billing_arrears;
    }

    public void setLt_billing_arrears(String lt_billing_arrears) {
        this.lt_billing_arrears = lt_billing_arrears;
    }

    public String getLt_billing_pf_flag() {
        return lt_billing_pf_flag;
    }

    public void setLt_billing_pf_flag(String lt_billing_pf_flag) {
        this.lt_billing_pf_flag = lt_billing_pf_flag;
    }

    public String getLt_billing_billfor() {
        return lt_billing_billfor;
    }

    public void setLt_billing_billfor(String lt_billing_billfor) {
        this.lt_billing_billfor = lt_billing_billfor;
    }

    public String getLt_billing_mrcode() {
        return lt_billing_mrcode;
    }

    public void setLt_billing_mrcode(String lt_billing_mrcode) {
        this.lt_billing_mrcode = lt_billing_mrcode;
    }

    public String getLt_billing_legfol() {
        return lt_billing_legfol;
    }

    public void setLt_billing_legfol(String lt_billing_legfol) {
        this.lt_billing_legfol = lt_billing_legfol;
    }

    public String getLt_billing_oddeven() {
        return lt_billing_oddeven;
    }

    public void setLt_billing_oddeven(String lt_billing_oddeven) {
        this.lt_billing_oddeven = lt_billing_oddeven;
    }

    public String getLt_billing_ssno() {
        return lt_billing_ssno;
    }

    public void setLt_billing_ssno(String lt_billing_ssno) {
        this.lt_billing_ssno = lt_billing_ssno;
    }

    public String getLt_billing_consno() {
        return lt_billing_consno;
    }

    public void setLt_billing_consno(String lt_billing_consno) {
        this.lt_billing_consno = lt_billing_consno;
    }

    public String getLt_billing_ph_no() {
        return lt_billing_ph_no;
    }

    public void setLt_billing_ph_no(String lt_billing_ph_no) {
        this.lt_billing_ph_no = lt_billing_ph_no;
    }

    public String getLt_billing_rebate_flag() {
        return lt_billing_rebate_flag;
    }

    public void setLt_billing_rebate_flag(String lt_billing_rebate_flag) {
        this.lt_billing_rebate_flag = lt_billing_rebate_flag;
    }

    public String getLt_billing_rrebate() {
        return lt_billing_rrebate;
    }

    public void setLt_billing_rrebate(String lt_billing_rrebate) {
        this.lt_billing_rrebate = lt_billing_rrebate;
    }

    public String getLt_billing_extra1() {
        return lt_billing_extra1;
    }

    public void setLt_billing_extra1(String lt_billing_extra1) {
        this.lt_billing_extra1 = lt_billing_extra1;
    }

    public String getLt_billing_data1() {
        return lt_billing_data1;
    }

    public void setLt_billing_data1(String lt_billing_data1) {
        this.lt_billing_data1 = lt_billing_data1;
    }

    public String getLt_billing_extra2() {
        return lt_billing_extra2;
    }

    public void setLt_billing_extra2(String lt_billing_extra2) {
        this.lt_billing_extra2 = lt_billing_extra2;
    }

    public String getLt_billing_data2() {
        return lt_billing_data2;
    }

    public void setLt_billing_data2(String lt_billing_data2) {
        this.lt_billing_data2 = lt_billing_data2;
    }

    public String getLt_billing_deposit() {
        return lt_billing_deposit;
    }

    public void setLt_billing_deposit(String lt_billing_deposit) {
        this.lt_billing_deposit = lt_billing_deposit;
    }

    public String getLt_billing_mtrdigit() {
        return lt_billing_mtrdigit;
    }

    public void setLt_billing_mtrdigit(String lt_billing_mtrdigit) {
        this.lt_billing_mtrdigit = lt_billing_mtrdigit;
    }

    public String getLt_billing_asdamt() {
        return lt_billing_asdamt;
    }

    public void setLt_billing_asdamt(String lt_billing_asdamt) {
        this.lt_billing_asdamt = lt_billing_asdamt;
    }

    public String getLt_billing_iodamt() {
        return lt_billing_iodamt;
    }

    public void setLt_billing_iodamt(String lt_billing_iodamt) {
        this.lt_billing_iodamt = lt_billing_iodamt;
    }

    public String getLt_billing_pfval() {
        return lt_billing_pfval;
    }

    public void setLt_billing_pfval(String lt_billing_pfval) {
        this.lt_billing_pfval = lt_billing_pfval;
    }

    public String getLt_billing_bmdval() {
        return lt_billing_bmdval;
    }

    public void setLt_billing_bmdval(String lt_billing_bmdval) {
        this.lt_billing_bmdval = lt_billing_bmdval;
    }

    public String getLt_billing_bill_no() {
        return lt_billing_bill_no;
    }

    public void setLt_billing_bill_no(String lt_billing_bill_no) {
        this.lt_billing_bill_no = lt_billing_bill_no;
    }

    public String getLt_billing_interest_amt() {
        return lt_billing_interest_amt;
    }

    public void setLt_billing_interest_amt(String lt_billing_interest_amt) {
        this.lt_billing_interest_amt = lt_billing_interest_amt;
    }

    public String getLt_billing_cap_flag() {
        return lt_billing_cap_flag;
    }

    public void setLt_billing_cap_flag(String lt_billing_cap_flag) {
        this.lt_billing_cap_flag = lt_billing_cap_flag;
    }

    public String getLt_billing_tod_flag() {
        return lt_billing_tod_flag;
    }

    public void setLt_billing_tod_flag(String lt_billing_tod_flag) {
        this.lt_billing_tod_flag = lt_billing_tod_flag;
    }

    public String getLt_billing_tod_previous1() {
        return lt_billing_tod_previous1;
    }

    public void setLt_billing_tod_previous1(String lt_billing_tod_previous1) {
        this.lt_billing_tod_previous1 = lt_billing_tod_previous1;
    }

    public String getLt_billing_tod_previous3() {
        return lt_billing_tod_previous3;
    }

    public void setLt_billing_tod_previous3(String lt_billing_tod_previous3) {
        this.lt_billing_tod_previous3 = lt_billing_tod_previous3;
    }

    public String getLt_billing_int_on_dep() {
        return lt_billing_int_on_dep;
    }

    public void setLt_billing_int_on_dep(String lt_billing_int_on_dep) {
        this.lt_billing_int_on_dep = lt_billing_int_on_dep;
    }

    public String getLt_billing_so_feeder_tc_pole() {
        return lt_billing_so_feeder_tc_pole;
    }

    public void setLt_billing_so_feeder_tc_pole(String lt_billing_so_feeder_tc_pole) {
        this.lt_billing_so_feeder_tc_pole = lt_billing_so_feeder_tc_pole;
    }

    public String getLt_billing_tariff_name() {
        return lt_billing_tariff_name;
    }

    public void setLt_billing_tariff_name(String lt_billing_tariff_name) {
        this.lt_billing_tariff_name = lt_billing_tariff_name;
    }

    public String getLt_billing_prev_read_date() {
        return lt_billing_prev_read_date;
    }

    public void setLt_billing_prev_read_date(String lt_billing_prev_read_date) {
        this.lt_billing_prev_read_date = lt_billing_prev_read_date;
    }

    public String getLt_billing_bill_days() {
        return lt_billing_bill_days;
    }

    public void setLt_billing_bill_days(String lt_billing_bill_days) {
        this.lt_billing_bill_days = lt_billing_bill_days;
    }

    public String getLt_billing_mtr_serial_no() {
        return lt_billing_mtr_serial_no;
    }

    public void setLt_billing_mtr_serial_no(String lt_billing_mtr_serial_no) {
        this.lt_billing_mtr_serial_no = lt_billing_mtr_serial_no;
    }

    public String getLt_billing_chq_disshonour_flag() {
        return lt_billing_chq_disshonour_flag;
    }

    public void setLt_billing_chq_disshonour_flag(String lt_billing_chq_disshonour_flag) {
        this.lt_billing_chq_disshonour_flag = lt_billing_chq_disshonour_flag;
    }

    public String getLt_billing_chq_dishonour_date() {
        return lt_billing_chq_dishonour_date;
    }

    public void setLt_billing_chq_dishonour_date(String lt_billing_chq_dishonour_date) {
        this.lt_billing_chq_dishonour_date = lt_billing_chq_dishonour_date;
    }

    public String getLt_billing_fdrname() {
        return lt_billing_fdrname;
    }

    public void setLt_billing_fdrname(String lt_billing_fdrname) {
        this.lt_billing_fdrname = lt_billing_fdrname;
    }

    public String getLt_billing_tccode() {
        return lt_billing_tccode;
    }

    public void setLt_billing_tccode(String lt_billing_tccode) {
        this.lt_billing_tccode = lt_billing_tccode;
    }

    public String getLt_billing_mtr_flag() {
        return lt_billing_mtr_flag;
    }

    public void setLt_billing_mtr_flag(String lt_billing_mtr_flag) {
        this.lt_billing_mtr_flag = lt_billing_mtr_flag;
    }

    public String getLt_billing_pres_rdg() {
        return lt_billing_pres_rdg;
    }

    public void setLt_billing_pres_rdg(String lt_billing_pres_rdg) {
        this.lt_billing_pres_rdg = lt_billing_pres_rdg;
    }

    public String getLt_billing_pres_sts() {
        return lt_billing_pres_sts;
    }

    public void setLt_billing_pres_sts(String lt_billing_pres_sts) {
        this.lt_billing_pres_sts = lt_billing_pres_sts;
    }

    public String getLt_billing_units() {
        return lt_billing_units;
    }

    public void setLt_billing_units(String lt_billing_units) {
        this.lt_billing_units = lt_billing_units;
    }

    public String getLt_billing_fix() {
        return lt_billing_fix;
    }

    public void setLt_billing_fix(String lt_billing_fix) {
        this.lt_billing_fix = lt_billing_fix;
    }

    public String getLt_billing_engchg() {
        return lt_billing_engchg;
    }

    public void setLt_billing_engchg(String lt_billing_engchg) {
        this.lt_billing_engchg = lt_billing_engchg;
    }

    public String getLt_billing_rebate_amount() {
        return lt_billing_rebate_amount;
    }

    public void setLt_billing_rebate_amount(String lt_billing_rebate_amount) {
        this.lt_billing_rebate_amount = lt_billing_rebate_amount;
    }

    public String getLt_billing_tax_amount() {
        return lt_billing_tax_amount;
    }

    public void setLt_billing_tax_amount(String lt_billing_tax_amount) {
        this.lt_billing_tax_amount = lt_billing_tax_amount;
    }

    public String getLt_billing_bmd_penalty() {
        return lt_billing_bmd_penalty;
    }

    public void setLt_billing_bmd_penalty(String lt_billing_bmd_penalty) {
        this.lt_billing_bmd_penalty = lt_billing_bmd_penalty;
    }

    public String getLt_billing_pf_penalty() {
        return lt_billing_pf_penalty;
    }

    public void setLt_billing_pf_penalty(String lt_billing_pf_penalty) {
        this.lt_billing_pf_penalty = lt_billing_pf_penalty;
    }

    public String getLt_billing_payable() {
        return lt_billing_payable;
    }

    public void setLt_billing_payable(String lt_billing_payable) {
        this.lt_billing_payable = lt_billing_payable;
    }

    public String getLt_billing_billdate() {
        return lt_billing_billdate;
    }

    public void setLt_billing_billdate(String lt_billing_billdate) {
        this.lt_billing_billdate = lt_billing_billdate;
    }

    public String getLt_billing_billtime() {
        return lt_billing_billtime;
    }

    public void setLt_billing_billtime(String lt_billing_billtime) {
        this.lt_billing_billtime = lt_billing_billtime;
    }

    public String getLt_billing_tod_current1() {
        return lt_billing_tod_current1;
    }

    public void setLt_billing_tod_current1(String lt_billing_tod_current1) {
        this.lt_billing_tod_current1 = lt_billing_tod_current1;
    }

    public String getLt_billing_tod_current3() {
        return lt_billing_tod_current3;
    }

    public void setLt_billing_tod_current3(String lt_billing_tod_current3) {
        this.lt_billing_tod_current3 = lt_billing_tod_current3;
    }

    public String getLt_billing_gok_subsidy() {
        return lt_billing_gok_subsidy;
    }

    public void setLt_billing_gok_subsidy(String lt_billing_gok_subsidy) {
        this.lt_billing_gok_subsidy = lt_billing_gok_subsidy;
    }

    public String getLt_billing_dem_revenue() {
        return lt_billing_dem_revenue;
    }

    public void setLt_billing_dem_revenue(String lt_billing_dem_revenue) {
        this.lt_billing_dem_revenue = lt_billing_dem_revenue;
    }

    public String getLt_billing_gps_lat() {
        return lt_billing_gps_lat;
    }

    public void setLt_billing_gps_lat(String lt_billing_gps_lat) {
        this.lt_billing_gps_lat = lt_billing_gps_lat;
    }

    public String getLt_billing_gps_long() {
        return lt_billing_gps_long;
    }

    public void setLt_billing_gps_long(String lt_billing_gps_long) {
        this.lt_billing_gps_long = lt_billing_gps_long;
    }

    public String getLt_billing_online_flag() {
        return lt_billing_online_flag;
    }

    public void setLt_billing_online_flag(String lt_billing_online_flag) {
        this.lt_billing_online_flag = lt_billing_online_flag;
    }

    public String getLt_billing_battery_charge() {
        return lt_billing_battery_charge;
    }

    public void setLt_billing_battery_charge(String lt_billing_battery_charge) {
        this.lt_billing_battery_charge = lt_billing_battery_charge;
    }

    public String getLt_billing_signal_strength() {
        return lt_billing_signal_strength;
    }

    public void setLt_billing_signal_strength(String lt_billing_signal_strength) {
        this.lt_billing_signal_strength = lt_billing_signal_strength;
    }

    public String getLt_billing_imgadd() {
        return lt_billing_imgadd;
    }

    public void setLt_billing_imgadd(String lt_billing_imgadd) {
        this.lt_billing_imgadd = lt_billing_imgadd;
    }

    public String getLt_billing_payable_real() {
        return lt_billing_payable_real;
    }

    public void setLt_billing_payable_real(String lt_billing_payable_real) {
        this.lt_billing_payable_real = lt_billing_payable_real;
    }

    public String getLt_billing_payable_profit() {
        return lt_billing_payable_profit;
    }

    public void setLt_billing_payable_profit(String lt_billing_payable_profit) {
        this.lt_billing_payable_profit = lt_billing_payable_profit;
    }

    public String getLt_billing_payable_loss() {
        return lt_billing_payable_loss;
    }

    public void setLt_billing_payable_loss(String lt_billing_payable_loss) {
        this.lt_billing_payable_loss = lt_billing_payable_loss;
    }

    public String getLt_billing_bill_printed() {
        return lt_billing_bill_printed;
    }

    public void setLt_billing_bill_printed(String lt_billing_bill_printed) {
        this.lt_billing_bill_printed = lt_billing_bill_printed;
    }

    public String getLt_billing_fslab1() {
        return lt_billing_fslab1;
    }

    public void setLt_billing_fslab1(String lt_billing_fslab1) {
        this.lt_billing_fslab1 = lt_billing_fslab1;
    }

    public String getLt_billing_fslab2() {
        return lt_billing_fslab2;
    }

    public void setLt_billing_fslab2(String lt_billing_fslab2) {
        this.lt_billing_fslab2 = lt_billing_fslab2;
    }

    public String getLt_billing_fslab3() {
        return lt_billing_fslab3;
    }

    public void setLt_billing_fslab3(String lt_billing_fslab3) {
        this.lt_billing_fslab3 = lt_billing_fslab3;
    }

    public String getLt_billing_fslab4() {
        return lt_billing_fslab4;
    }

    public void setLt_billing_fslab4(String lt_billing_fslab4) {
        this.lt_billing_fslab4 = lt_billing_fslab4;
    }

    public String getLt_billing_fslab5() {
        return lt_billing_fslab5;
    }

    public void setLt_billing_fslab5(String lt_billing_fslab5) {
        this.lt_billing_fslab5 = lt_billing_fslab5;
    }

    public String getLt_billing_eslab1() {
        return lt_billing_eslab1;
    }

    public void setLt_billing_eslab1(String lt_billing_eslab1) {
        this.lt_billing_eslab1 = lt_billing_eslab1;
    }

    public String getLt_billing_eslab2() {
        return lt_billing_eslab2;
    }

    public void setLt_billing_eslab2(String lt_billing_eslab2) {
        this.lt_billing_eslab2 = lt_billing_eslab2;
    }

    public String getLt_billing_eslab3() {
        return lt_billing_eslab3;
    }

    public void setLt_billing_eslab3(String lt_billing_eslab3) {
        this.lt_billing_eslab3 = lt_billing_eslab3;
    }

    public String getLt_billing_eslab4() {
        return lt_billing_eslab4;
    }

    public void setLt_billing_eslab4(String lt_billing_eslab4) {
        this.lt_billing_eslab4 = lt_billing_eslab4;
    }

    public String getLt_billing_eslab5() {
        return lt_billing_eslab5;
    }

    public void setLt_billing_eslab5(String lt_billing_eslab5) {
        this.lt_billing_eslab5 = lt_billing_eslab5;
    }

    public String getLt_billing_eslab6() {
        return lt_billing_eslab6;
    }

    public void setLt_billing_eslab6(String lt_billing_eslab6) {
        this.lt_billing_eslab6 = lt_billing_eslab6;
    }

    public String getLt_billing_charitable_rbt_amt() {
        return lt_billing_charitable_rbt_amt;
    }

    public void setLt_billing_charitable_rbt_amt(String lt_billing_charitable_rbt_amt) {
        this.lt_billing_charitable_rbt_amt = lt_billing_charitable_rbt_amt;
    }

    public String getLt_billing_solar_rbt_amt() {
        return lt_billing_solar_rbt_amt;
    }

    public void setLt_billing_solar_rbt_amt(String lt_billing_solar_rbt_amt) {
        this.lt_billing_solar_rbt_amt = lt_billing_solar_rbt_amt;
    }

    public String getLt_billing_fl_rbt_amt() {
        return lt_billing_fl_rbt_amt;
    }

    public void setLt_billing_fl_rbt_amt(String lt_billing_fl_rbt_amt) {
        this.lt_billing_fl_rbt_amt = lt_billing_fl_rbt_amt;
    }

    public String getLt_billing_handicap_rbt_amt() {
        return lt_billing_handicap_rbt_amt;
    }

    public void setLt_billing_handicap_rbt_amt(String lt_billing_handicap_rbt_amt) {
        this.lt_billing_handicap_rbt_amt = lt_billing_handicap_rbt_amt;
    }

    public String getLt_billing_pl_rbt_amt() {
        return lt_billing_pl_rbt_amt;
    }

    public void setLt_billing_pl_rbt_amt(String lt_billing_pl_rbt_amt) {
        this.lt_billing_pl_rbt_amt = lt_billing_pl_rbt_amt;
    }

    public String getLt_billing_ipset_rbt_amt() {
        return lt_billing_ipset_rbt_amt;
    }

    public void setLt_billing_ipset_rbt_amt(String lt_billing_ipset_rbt_amt) {
        this.lt_billing_ipset_rbt_amt = lt_billing_ipset_rbt_amt;
    }

    public String getLt_billing_rebatefromccb_amt() {
        return lt_billing_rebatefromccb_amt;
    }

    public void setLt_billing_rebatefromccb_amt(String lt_billing_rebatefromccb_amt) {
        this.lt_billing_rebatefromccb_amt = lt_billing_rebatefromccb_amt;
    }

    public String getLt_billing_tod_charges() {
        return lt_billing_tod_charges;
    }

    public void setLt_billing_tod_charges(String lt_billing_tod_charges) {
        this.lt_billing_tod_charges = lt_billing_tod_charges;
    }

    public String getLt_billing_pf_penality_amt() {
        return lt_billing_pf_penality_amt;
    }

    public void setLt_billing_pf_penality_amt(String lt_billing_pf_penality_amt) {
        this.lt_billing_pf_penality_amt = lt_billing_pf_penality_amt;
    }

    public String getLt_billing_exload_mdpenality() {
        return lt_billing_exload_mdpenality;
    }

    public void setLt_billing_exload_mdpenality(String lt_billing_exload_mdpenality) {
        this.lt_billing_exload_mdpenality = lt_billing_exload_mdpenality;
    }

    public String getLt_billing_curr_bill_amount() {
        return lt_billing_curr_bill_amount;
    }

    public void setLt_billing_curr_bill_amount(String lt_billing_curr_bill_amount) {
        this.lt_billing_curr_bill_amount = lt_billing_curr_bill_amount;
    }

    public String getLt_billing_rounding_amount() {
        return lt_billing_rounding_amount;
    }

    public void setLt_billing_rounding_amount(String lt_billing_rounding_amount) {
        this.lt_billing_rounding_amount = lt_billing_rounding_amount;
    }

    public String getLt_billing_due_date() {
        return lt_billing_due_date;
    }

    public void setLt_billing_due_date(String lt_billing_due_date) {
        this.lt_billing_due_date = lt_billing_due_date;
    }

    public String getLt_billing_disconn_date() {
        return lt_billing_disconn_date;
    }

    public void setLt_billing_disconn_date(String lt_billing_disconn_date) {
        this.lt_billing_disconn_date = lt_billing_disconn_date;
    }

    public String getLt_billing_creadj() {
        return lt_billing_creadj;
    }

    public void setLt_billing_creadj(String lt_billing_creadj) {
        this.lt_billing_creadj = lt_billing_creadj;
    }

    public String getLt_billing_preadkvah() {
        return lt_billing_preadkvah;
    }

    public void setLt_billing_preadkvah(String lt_billing_preadkvah) {
        this.lt_billing_preadkvah = lt_billing_preadkvah;
    }

    public String getLt_billing_aadhaar() {
        return lt_billing_aadhaar;
    }

    public void setLt_billing_aadhaar(String lt_billing_aadhaar) {
        this.lt_billing_aadhaar = lt_billing_aadhaar;
    }

    public String getLt_billing_mail() {
        return lt_billing_mail;
    }

    public void setLt_billing_mail(String lt_billing_mail) {
        this.lt_billing_mail = lt_billing_mail;
    }

    public String getLt_billing_mtr_digit() {
        return lt_billing_mtr_digit;
    }

    public void setLt_billing_mtr_digit(String lt_billing_mtr_digit) {
        this.lt_billing_mtr_digit = lt_billing_mtr_digit;
    }

    public String getLt_billing_id() {
        return lt_billing_id;
    }

    public void setLt_billing_id(String lt_billing_id) {
        this.lt_billing_id = lt_billing_id;
    }

    public String getColl_posting_receipt_no() {
        return coll_posting_receipt_no;
    }

    public void setColl_posting_receipt_no(String coll_posting_receipt_no) {
        this.coll_posting_receipt_no = coll_posting_receipt_no;
    }

    public String getColl_app_version() {
        return coll_app_version;
    }

    public void setColl_app_version(String coll_app_version) {
        this.coll_app_version = coll_app_version;
    }

    public ArrayList<GetSetValues> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<GetSetValues> arrayList) {
        this.arrayList = arrayList;
    }

    public String getSolar_cust_name() {
        return solar_cust_name;
    }

    public void setSolar_cust_name(String solar_cust_name) {
        this.solar_cust_name = solar_cust_name;
    }

    public String getSolar_cust_rrno() {
        return solar_cust_rrno;
    }

    public void setSolar_cust_rrno(String solar_cust_rrno) {
        this.solar_cust_rrno = solar_cust_rrno;
    }

    public String getSolar_im_prev_read() {
        return solar_im_prev_read;
    }

    public void setSolar_im_prev_read(String solar_im_prev_read) {
        this.solar_im_prev_read = solar_im_prev_read;
    }

    public String getSolar_ex_prev_read() {
        return solar_ex_prev_read;
    }

    public void setSolar_ex_prev_read(String solar_ex_prev_read) {
        this.solar_ex_prev_read = solar_ex_prev_read;
    }

    public String getSolar_date() {
        return solar_date;
    }

    public void setSolar_date(String solar_date) {
        this.solar_date = solar_date;
    }

    public String getSolar_flag() {
        return solar_flag;
    }

    public void setSolar_flag(String solar_flag) {
        this.solar_flag = solar_flag;
    }

    public String getDis_prev_read() {
        return dis_prev_read;
    }

    public void setDis_prev_read(String dis_prev_read) {
        this.dis_prev_read = dis_prev_read;
    }

    public String getColl_reports_slno() {
        return coll_reports_slno;
    }

    public void setColl_reports_slno(String coll_reports_slno) {
        this.coll_reports_slno = coll_reports_slno;
    }

    public String getColl_reports_acc_id() {
        return coll_reports_acc_id;
    }

    public void setColl_reports_acc_id(String coll_reports_acc_id) {
        this.coll_reports_acc_id = coll_reports_acc_id;
    }

    public String getColl_reports_recpt_no() {
        return coll_reports_recpt_no;
    }

    public void setColl_reports_recpt_no(String coll_reports_recpt_no) {
        this.coll_reports_recpt_no = coll_reports_recpt_no;
    }

    public String getColl_reports_amount() {
        return coll_reports_amount;
    }

    public void setColl_reports_amount(String coll_reports_amount) {
        this.coll_reports_amount = coll_reports_amount;
    }
}
