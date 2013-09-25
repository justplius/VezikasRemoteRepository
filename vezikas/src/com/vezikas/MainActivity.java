package com.vezikas;
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

 
public class MainActivity extends SherlockFragmentActivity {
 
	//Often used variables 
	
	//Get data
	DriverPost dp = new DriverPost();
	String laikas = dp.getTime();
	
    // Declare Variables
    ActionBar mActionBar;
    ViewPager mPager;
    Tab tab;
 
    @SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_main);
 
        // Activate Navigation Mode Tabs
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // Locate ViewPager in activity_main.xml
        mPager = (ViewPager) findViewById(R.id.pager);
 
        // Activate Fragment Manager
        FragmentManager fm = getSupportFragmentManager();
 
        // Capture ViewPager page swipes
        ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Find the ViewPager Position
                mActionBar.setSelectedNavigationItem(position);
            }
        };
 
        mPager.setOnPageChangeListener(ViewPagerListener);
        // Locate the adapter class called ViewPagerAdapter.java
        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(fm);
        // Set the View Pager Adapter into ViewPager
        mPager.setAdapter(viewpageradapter);
 
        // Capture tab button clicks
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
 
            @Override
            public void onTabSelected(Tab tab, FragmentTransaction ft) {
                // Pass the position on tab click to ViewPager
                mPager.setCurrentItem(tab.getPosition());
            }
 
            @Override
            public void onTabUnselected(Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }
 
            @Override
            public void onTabReselected(Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }
        };
 
        // Create first Tab
        tab = mActionBar.newTab().setText("Veþëjas").setTabListener(tabListener);
        mActionBar.addTab(tab);
 
        // Create second Tab
        tab = mActionBar.newTab().setText("Keleivis").setTabListener(tabListener);
        mActionBar.addTab(tab);
        
        int Measuredwidth = 0;
        //int Measuredheight = 0;
        
        Point size = new Point();
        WindowManager w = getWindowManager();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
        	w.getDefaultDisplay().getSize(size);
            Measuredwidth = size.x;
            //Measuredheight = size.y; 
              
        } else {
            Display d = w.getDefaultDisplay(); 
            Measuredwidth = d.getWidth(); 
            //Measuredheight = d.getHeight(); 
        }
        TextView tv = (TextView)findViewById(R.id.tekstas);
        
        String orientation = getRotation(this.getApplicationContext());
        StringBuilder sb = new StringBuilder();
        //sb.append("");
        sb.append(Measuredwidth);
        //sb.append(" ");
        //sb.append(Measuredheight);
        sb.append(" ");
        sb.append(orientation);   
        sb.append(" ");
        sb.append(laikas);
        tv.setText(sb.toString());
        
        if (Measuredwidth <= 480 && (orientation.equals("landscape") || orientation.equals("reverse landscape"))){
        	mActionBar.setDisplayShowTitleEnabled(false);
        } else {
        	mActionBar.setDisplayShowTitleEnabled(true);
        }
        
    }
    
    @SuppressWarnings("deprecation")
	public String getRotation(Context context){
        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
               switch (rotation) {
                case Surface.ROTATION_0:
                    return "portrait";
                case Surface.ROTATION_90:
                    return "landscape";
                case Surface.ROTATION_180:
                    return "reverse portrait";
                default:
                    return "reverse landscape";
                }
            }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add("Skelbimai")
            .setIcon(R.drawable.ic_launcher)
            .setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);
        
        menu.add("Þinutës")
        	.setIcon(R.drawable.ic_launcher)
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        
        menu.add("Kelias")
        	.setIcon(R.drawable.ic_launcher)
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        
        menu.add("Paskyra")
        	.setIcon(R.drawable.ic_launcher)
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add("Nustatymai")
    	.setIcon(R.drawable.ic_launcher)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);


        return true;
    }
 
}