package com.example.safe;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ShareActionProvider;


public class MapActivity extends MainActivity {
	
	TextView tv;Button btnShowLocation;GPSTracker gps; WebView myWebView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_map);
		getLayoutInflater().inflate(R.layout.activity_map, frameLayout);
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);
		
		btnShowLocation = (Button) findViewById(R.id.button1);
		
		 myWebView = (WebView)findViewById(R.id.webView1);
		  myWebView.getSettings().setJavaScriptEnabled(true);
		  myWebView.setWebViewClient(new WebViewClient());
		  
		  gps = new GPSTracker(MapActivity.this);
		    if( gps.getLocation() != null){
		    double latitude=gps.getLatitude();
		    double longitude=gps.getLongitude();
		    myWebView.loadUrl("http://www.google.com/maps/place/"+latitude+","+longitude);
			}else{
				 myWebView.loadUrl("http://www.google.com");
			}
		    btnShowLocation.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View arg0) {		
				
			        gps = new GPSTracker(MapActivity.this);	
			        if(gps.canGetLocation()){
			        	
			        	double latitude = gps.getLatitude();
			        	double longitude = gps.getLongitude();
			        	
			        	// \n is for new line
			        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
			        }else{gps.showSettingsAlert();}}
			});
		    
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.item, menu);
	    ShareActionProvider mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.share).getActionProvider();
	    mShareActionProvider.setShareIntent(getDefaultShareIntent());
	    return super.onCreateOptionsMenu(menu);}
	private Intent getDefaultShareIntent(){
	    Intent intent = new Intent(Intent.ACTION_SEND);
	    intent.setType("text/plain");
	    intent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
	   // String latitude = null;
		//String longitude = null;
	    gps = new GPSTracker(MapActivity.this);
	    if( gps.getLocation() != null){
	    double latitude=gps.getLatitude();
	    double longitude=gps.getLongitude();
	    String url="http://www.google.com/maps/place/"+latitude+","+longitude;
		intent.putExtra(Intent.EXTRA_TEXT,url);}else{
			intent.putExtra(Intent.EXTRA_TEXT,"I am in Danger");
		}
	    return intent;
	}

	
}
