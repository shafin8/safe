package com.example.safe;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private MenuItem menuItem;
	
	protected FrameLayout frameLayout;

	protected ListView mDrawerList;

	protected String[] listArray = { "MY Location", "Nearby Police", "Nearby Hospital", "Nearby Fire Station", "Emergency Contacts" };
	
	protected static int position;
	
	private static boolean isLaunch = true;
	
	private DrawerLayout mDrawerLayout;
	
	private ActionBarDrawerToggle actionBarDrawerToggle;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_drawer_base);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
	        | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
	  
		
		frameLayout = (FrameLayout)findViewById(R.id.content_frame);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		// set a custom shadow that overlays the main content when the drawer opens
		//mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, listArray));
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				openActivity(position);
			}
		});
		
		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		// ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
		actionBarDrawerToggle = new ActionBarDrawerToggle(
				this,						/* host Activity */
				mDrawerLayout, 				/* DrawerLayout object */
				R.drawable.ic_launcher,     /* nav drawer image to replace 'Up' caret */
				R.string.open_drawer,       /* "open drawer" description for accessibility */
				R.string.close_drawer)      /* "close drawer" description for accessibility */ 
		{ 
			@Override
			public void onDrawerClosed(View drawerView) {
				getActionBar().setTitle(listArray[position]);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
			}

			@Override
			public void onDrawerStateChanged(int newState) {
				super.onDrawerStateChanged(newState);
			}
		};
		mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
		
		if(isLaunch){
			
			isLaunch = false;
			openActivity(0);
		}
	}
	
	protected void openActivity(int position) {
		
		
//		mDrawerList.setItemChecked(position, true);
//		setTitle(listArray[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
		MainActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities. 
		
		switch (position) {
		case 0:
			startActivity(new Intent(this, MapActivity.class));
			break;
		case 1:
			startActivity(new Intent(this, PoliceActivity.class));
			break;
		case 2:
			startActivity(new Intent(this, HospitalActivity.class));
			break;
		case 3:
			startActivity(new Intent(this, FireActivity.class));
			break;
		case 4:
			startActivity(new Intent(this, LocationActivity.class));
			break;

		default:
			break;
		}
		
		Toast.makeText(this, "Selected Item Position::"+position, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// The action bar home/up action should open or close the drawer. 
		// ActionBarDrawerToggle will take care of this.
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
		
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent in=new Intent(MainActivity.this, SettingActivity.class);
			startActivity(in);
			return true;
		case R.id.menu_load:
		      menuItem = item;
		      menuItem.setActionView(R.layout.action_view);
		      menuItem.expandActionView();
		      TestTask task = new TestTask();
		      task.execute("test");
		      //refresh the activity
		      finish();
		      startActivity(getIntent());
		     
		      
		      break;
		    
		default:
			return super.onOptionsItemSelected(item);
		}return true;
	}
	 private class TestTask extends AsyncTask<String, Void, String> {

		    protected String doInBackground(String... params) {
		      // Simulate something long running
		      try {
		        Thread.sleep(2000);
		      } catch (InterruptedException e) {
		        e.printStackTrace();
		      }
		      return null;
		    }
		    protected void onPostExecute(String result) {
		      menuItem.collapseActionView();
		      menuItem.setActionView(null);
		    }}
	
	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    
    /* We can override onBackPressed method to toggle navigation drawer*/
    @Override
    public void onBackPressed()
    {
        if (mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawer(Gravity.START);
        else
            super.onBackPressed();
    }
}
