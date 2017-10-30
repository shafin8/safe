package com.example.safe;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
 
public class SmsActivity extends SettingActivity {
 
	Button buttonSend;
	final Context context = this;
	private Button button;
	
  Button btnShowLocation;
  GPSTracker gps;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms);
		 btnShowLocation = (Button) findViewById(R.id.toast);
		 button = (Button) findViewById(R.id.buttonCall);
		 

			// add PhoneStateListener
			PhoneCallListener phoneListener = new PhoneCallListener();
			TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
			telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
	 
			// add button listener
			button.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
	 
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:"+getPhone()+""));
					startActivity(callIntent);
	 
				}
	 
			});
	        btnShowLocation.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {		
			        gps = new GPSTracker(SmsActivity.this);	
			        if(gps.canGetLocation()){
			        double latitude = gps.getLatitude();
			        double longitude = gps.getLongitude();
			        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
			        }else{gps.showSettingsAlert(); }}});
		
		
		buttonSend = (Button) findViewById(R.id.buttonSend);
		buttonSend.setOnClickListener(new OnClickListener() {
 
			public void onClick(View v) {
				gps = new GPSTracker(SmsActivity.this);	
		        if(gps.canGetLocation()){
		        double latitude = gps.getLatitude();
		        double longitude = gps.getLongitude();
		        String s= "My Location is : ("+"http://www.google.com/maps/place/" + latitude +","+ longitude+")";	
		        
				
			  try {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(getPhone(), null, getName()+","+getSms()+"\n"+s, null, null);
				Toast.makeText(getApplicationContext(), "SMS Sent!",
							Toast.LENGTH_LONG).show();
			  } catch (Exception e) {
				Toast.makeText(getApplicationContext(),
					"SMS faild, please try again later!",
					Toast.LENGTH_LONG).show();
				e.printStackTrace();
			  }
 
			}}
		});
	}
	//monitor phone call activities
		private class PhoneCallListener extends PhoneStateListener {
	 
			private boolean isPhoneCalling = false;
	 
			String LOG_TAG = "LOGGING 123";
	 
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
	 
				if (TelephonyManager.CALL_STATE_RINGING == state) {
					// phone ringing
					Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
				}
	 
				if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
					// active
					Log.i(LOG_TAG, "OFFHOOK");
	 
					isPhoneCalling = true;
				}
	 
				if (TelephonyManager.CALL_STATE_IDLE == state) {
					// run when class initial and phone call ended, 
					// need detect flag from CALL_STATE_OFFHOOK
					Log.i(LOG_TAG, "IDLE");
	 
					if (isPhoneCalling) {
	 
						Log.i(LOG_TAG, "restart app");
	 
						// restart app
						Intent i = getBaseContext().getPackageManager()
							.getLaunchIntentForPackage(
								getBaseContext().getPackageName());
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
	 
						isPhoneCalling = false;
					}
	 
				}
			}
		}
	 
	}
