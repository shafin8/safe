package com.example.safe;



import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class LocationActivity extends MainActivity {
Button b;WebView myWebView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_location);
		getLayoutInflater().inflate(R.layout.activity_location, frameLayout);
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);
		myWebView = (WebView)findViewById(R.id.webView11);
		  myWebView.getSettings().setJavaScriptEnabled(true);
		  myWebView.setWebViewClient(new WebViewClient());
		  myWebView.loadUrl("http://www.police.gov.bd/unitandcontactnew.php");
	b=(Button) findViewById(R.id.sms);
	b.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent in=new Intent(LocationActivity.this, SmsActivity.class);	
			
			startActivity(in);
		}
	});
	}
}
