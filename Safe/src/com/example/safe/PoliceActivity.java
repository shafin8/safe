package com.example.safe;


import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class PoliceActivity extends MainActivity {
	GPSTracker gps; WebView myWebView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_police);
		getLayoutInflater().inflate(R.layout.activity_police, frameLayout);
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);
		 myWebView = (WebView)findViewById(R.id.webView1);
		  myWebView.getSettings().setJavaScriptEnabled(true);
		  myWebView.setWebViewClient(new WebViewClient());
		  
		  gps = new GPSTracker(PoliceActivity.this);
		    if( gps.getLocation() != null){
		    double latitude=gps.getLatitude();
		    double longitude=gps.getLongitude();
		    myWebView.loadUrl("http://www.google.com/maps/search/police/@"+latitude+","+longitude+",16z");
			}else{
				 myWebView.loadUrl("http://www.google.com");
			}
	}
}
