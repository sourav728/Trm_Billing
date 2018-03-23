package com.example.tvd.trm_billing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.tvd.trm_billing.database.Databasehelper;
import com.example.tvd.trm_billing.services.ClassGPS;
import com.example.tvd.trm_billing.values.DataParser;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.tvd.trm_billing.values.ConstantValues.DESTINATION_LATITUDE;
import static com.example.tvd.trm_billing.values.ConstantValues.DESTINATION_LONGITUDE;


public class SubdivMaps extends FragmentActivity implements OnMapReadyCallback, DirectionCallback {
	GoogleMap mMap;
	Databasehelper dbh;
	Cursor c, c1, c2, c3;
	double maplattitude, maplongitude, curr_longitude, curr_latitude, cust_latitude, cust_longitude;
	String rrno, name, payable, tariff, lattitude, longitude, payable3, mapsstatus, amount, paymentmode, status="", cust_acctID ="",
            cust_rrno="";
	int payable2;
	BitmapDescriptor marker;
	Menu menu;
	MenuItem mapsMenuItem;
    Bundle bnd;
    ClassGPS classGPS;
	private String serverKey;
	private LatLng camera;
	private LatLng origin;
	private LatLng destination;
	Polyline line;
	ArrayList<Polyline> polylines;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.googlemaps);

        classGPS = new ClassGPS(this);

		Intent maps = getIntent();
		bnd = maps.getExtras();
		mapsstatus = bnd.getString("mapstoshow");
		/*if (mapsstatus.equals("directions")) {
			serverKey = "AIzaSyDR59Ykr0N0s3BquUEfsEm5nVXCcDuynC0";
			double source_lat = bnd.getDouble("ORIGIN_LAT");
			double source_long = bnd.getDouble("ORIGIN_LONG");
			double endpoint_lat = bnd.getDouble("DESTINATION_LAT");
			double endpoint_long = bnd.getDouble("DESTINATION_LONG");
			origin = new LatLng(source_lat, source_long);
			destination = new LatLng(endpoint_lat, endpoint_long);
			camera = origin;
		}*/

		((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

	}

    private void GPSlocation() {
        if (classGPS.canGetLocation()) {
            curr_latitude = classGPS.getLatitude();
            curr_longitude = classGPS.getLongitude();
        }
    }

	private void BillingMaps(Cursor c, boolean tariffmarker, boolean paymentmarker) {

		c = dbh.billed();
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				lattitude = c.getString(c.getColumnIndex("GPS_LAT"));
				longitude = c.getString(c.getColumnIndex("GPS_LONG"));
				rrno = c.getString(c.getColumnIndex("RRNO"));
				name = c.getString(c.getColumnIndex("NAME"));
				payable = c.getString(c.getColumnIndex("PAYABLE"));
				tariff = c.getString(c.getColumnIndex("TARIFF_NAME"));
				maplattitude = Double.parseDouble(lattitude);
				maplongitude = Double.parseDouble(longitude);
				LatLng coordinate = new LatLng(maplattitude, maplongitude);
				CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
				mMap.animateCamera(yourLocation);
				if (tariffmarker) {
					tariffmarkericon(tariff);
				}
				if (paymentmarker) {
					payable3 = payable.substring(0, payable.indexOf('.'));
					payable2 = Integer.parseInt(payable3);
					paymentmarkericon(payable2);
				}
				mMap.addMarker(new MarkerOptions().title(name).snippet("RRNO:   " + rrno + "\n" +
						"Tariff:   " + tariff + "\n" + "Payable:" + payable)
						.position(coordinate));
				mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

					@Override
					public View getInfoWindow(Marker arg0) {
						return null;
					}

					@Override
					public View getInfoContents(Marker marker) {
						View v = getLayoutInflater().inflate(R.layout.marker, null);
						TextView info = (TextView) v.findViewById(R.id.textView1);
						TextView info1 = (TextView) v.findViewById(R.id.textView2);
						info.setText(marker.getTitle().toUpperCase());
						info1.setText(marker.getSnippet());
						return v;
					}
				});
			}
		}
	}

	private void PaymentMaps(Cursor c) {

		c = dbh.collection_output();
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				lattitude = c.getString(c.getColumnIndex("GPS_LAT"));
				longitude = c.getString(c.getColumnIndex("GPS_LONG"));
				rrno = c.getString(c.getColumnIndex("RRNO"));
				name = c.getString(c.getColumnIndex("NAME"));
				amount = c.getString(c.getColumnIndex("AMOUNT"));
				String paymode = c.getString(c.getColumnIndex("MODE_PAYMENT"));
				if (paymode.equals("0")) {
					paymentmode = "Cash";
				} else if (paymode.equals("1")) {
					paymentmode = "Cheque";
				}
				maplattitude = Double.parseDouble(lattitude);
				maplongitude = Double.parseDouble(longitude);
				LatLng coordinate = new LatLng(maplattitude, maplongitude);
				CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
				mMap.animateCamera(yourLocation);
				mMap.addMarker(new MarkerOptions().title(name).snippet("RRNO:      " + rrno + "\n" +
						"Amount:    " + amount + "\n" + "PayMode: " + paymentmode)
						.position(coordinate));
				mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

					@Override
					public View getInfoWindow(Marker arg0) {
						return null;
					}

					@Override
					public View getInfoContents(Marker marker) {
						View v = getLayoutInflater().inflate(R.layout.marker, null);
						TextView info = (TextView) v.findViewById(R.id.textView1);
						TextView info1 = (TextView) v.findViewById(R.id.textView2);
						info.setText(marker.getTitle().toString().toUpperCase());
						info1.setText(marker.getSnippet());
						return v;
					}
				});
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.maps, menu);
		super.onCreateOptionsMenu(menu);
		mapsMenuItem = menu.findItem(R.id.item8);
		if (mapsstatus.equals("Map Status of Payment")) {
			mapsMenuItem.setTitle("Search by Amount");
		}
		mapsMenuItem = menu.findItem(R.id.item9);
		if (mapsstatus.equals("Map Status of Payment")) {
			mapsMenuItem.setTitle("Search by Payment Mode");
		}
		this.menu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.item2:
				mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				break;

			case R.id.item3:
				mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				break;

			case R.id.item4:
				mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				break;

			case R.id.item5:
				mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				break;

			case R.id.item7:
				if (mapsstatus.equals("Map Status of Billing")) {
					BillingMaps(c1, false, false);
				} else {
					PaymentMaps(c1);
				}
				break;

			case R.id.item8:
				if (mapsstatus.equals("Map Status of Billing")) {
					BillingMaps(c2, true, false);
				} else {
					PaymentMaps(c2);
				}
				break;

			case R.id.item9:
				if (mapsstatus.equals("Map Status of Billing")) {
					BillingMaps(c3, false, true);
				} else {
					PaymentMaps(c3);
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void paymentmarkericon(int value) {
		if (value <= 500) {
			marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
		} else if (value <= 1000 && value > 500) {
			marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
		} else if (value <= 10000 && value > 1000) {
			marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
		} else if (value <= 50000 && value > 10000) {
			marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
		} else if (value > 50000) {
			marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
		}
	}

	private void tariffmarkericon(String value) {
        switch (value) {
            case "LT-2a":
                marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                break;

            case "LT-3a":
                marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
                break;

            default:
                marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                break;
        }
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		dbh = new Databasehelper(this);
		dbh.openDatabase();

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		mMap.setMyLocationEnabled(true);

        FetchUrl FetchUrl;
        String url="";
        switch (mapsstatus) {
            case "Map Status of Billing":
                BillingMaps(c, false, false);
                break;

            case "Map Status of Payment":
                PaymentMaps(c);
                break;

            case "directions":
                cust_longitude = Double.parseDouble(bnd.getString(DESTINATION_LONGITUDE));
                cust_latitude = Double.parseDouble(bnd.getString(DESTINATION_LATITUDE));
                cust_acctID = bnd.getString("account_id");
                cust_rrno = bnd.getString("rrno");
                GPSlocation();
                mMap.addMarker(new MarkerOptions().position(new LatLng(cust_latitude, cust_longitude)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(cust_latitude, cust_longitude), 13));
                polylines = new ArrayList<>();
                url = getUrl(new LatLng(curr_latitude, curr_longitude), new LatLng(cust_latitude, cust_longitude));
                FetchUrl = new FetchUrl();
                // Start downloading json data from Google Directions API
                FetchUrl.execute(url);
                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(cust_latitude, cust_longitude)));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

                break;
        }
	}

	@Override
	public void onDirectionSuccess(Direction direction, String rawBody) {
		Toast.makeText(SubdivMaps.this, "Success with status : " + direction.getStatus(), Toast.LENGTH_SHORT).show();
		if (direction.isOK()) {
			mMap.addMarker(new MarkerOptions().position(origin));
			mMap.addMarker(new MarkerOptions().position(destination));
			ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
			mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.BLUE));
		}
	}

	@Override
	public void onDirectionFailure(Throwable t) {
		Toast.makeText(SubdivMaps.this, t.getMessage(), Toast.LENGTH_SHORT).show();
	}

	private String getUrl(LatLng origin, LatLng dest) {
		// Origin of route
		String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
		// Sensor enabled
		String sensor = "sensor=false";
		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;
		// Output format
		String output = "json";
		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
		return url;
	}

	/**
	 * A method to download json data from url
	 */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);
			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();
			// Connecting to url
			urlConnection.connect();
			// Reading data from url
			iStream = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			data = sb.toString();
			Log.d("downloadUrl", data.toString());
			br.close();
		} catch (Exception e) {
			Log.d("Exception", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches data from url passed
	private class FetchUrl extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... url) {
			// For storing data from web service
			String data = "";
			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
				Log.d("Background Task data", data.toString());
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ParserTask parserTask = new ParserTask();
			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);
		}
	}

	/**
	 * A class to parse the Google Places in JSON format
	 */
	private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;
			try {
				jObject = new JSONObject(jsonData[0]);
				Log.d("ParserTask",jsonData[0].toString());
				DataParser parser = new DataParser();
				Log.d("ParserTask", parser.toString());
				// Starts parsing data
				routes = parser.parse(jObject);
				Log.d("ParserTask","Executing routes");
				Log.d("ParserTask",routes.toString());
			} catch (Exception e) {
				Log.d("ParserTask",e.toString());
				e.printStackTrace();
			}
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points;
			PolylineOptions lineOptions = null;
			if (polylines.size() > 0) {
				polylines.get(0).remove();
				polylines.clear();
			}
			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<>();
				lineOptions = new PolylineOptions();
				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);
				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);
					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);
					points.add(position);
				}
				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(10);
				lineOptions.color(Color.RED);
				Log.d("onPostExecute","onPostExecute lineoptions decoded");
			}
			// Drawing polyline in the Google Map for the i-th route
			if(lineOptions != null) {
				line = mMap.addPolyline(lineOptions);
				polylines.add(line);
			}
			else Log.d("onPostExecute","without Polylines drawn");
		}
	}
}
