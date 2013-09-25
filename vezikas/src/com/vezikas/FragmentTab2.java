package com.vezikas;
 
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.internal.widget.IcsAdapterView;
import com.actionbarsherlock.internal.widget.IcsAdapterView.OnItemSelectedListener;
 
public class FragmentTab2 extends SherlockFragment {
 
	//View elements
	private TextView route;
	private Spinner spinner_city;
	private Button add_button;
	private Button remove_button;
	private CheckBox roundtrip;
	private CheckBox take_from_region;
	private CheckBox drop_of_in_region;
	private DatePicker leaving_day;
	private TimePicker leaving_time;
	private SeekBar leaving_time_seekbar;
	private TextView leaving_time_flexible_value;
	private DatePicker dropping_day;
	private SeekBar dropping_time_seekbar;
	private TimePicker dropping_time;
	private TextView dropping_time_flexible_value;	
	private EditText seats_available;
	private EditText price;
	private EditText phone;
	private EditText message;
	private Button confirm_button;
	
	//various variables
	private String selected_city = null;
	private ArrayList<String> cities = new ArrayList<String>(5);
	private boolean spinnerFlag = false;
	private ArrayList<NameValuePair> nameValuePairs;
	private int leaving_seekbar_hours = 0;
	private int leaving_seekbar_minutes = 30;
	private int returning_seekbar_hours = 0;
	private int returning_seekbar_minutes = 30;
	
	//database elements	
	private int db_user_id;
	private int db_route_id;
	private int db_phone_id;
	private int db_seats_available;
	private String db_message;
	private String db_leaving_date;
	private String db_leaving_time_from;
	private String db_leaving_time_to;
	private String db_returning_date;
	private String db_returning_time_to;
	private String db_returning_time_from;
	private int db_price;
	private int db_roundtrip = 0;
	private int db_take_from_region;
	private int db_drop_of_in_region; 

	
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Get the view from fragmenttab2.xml    	
        View view = inflater.inflate(R.layout.add_driver_post, container, false);
        
        route = (TextView) view.findViewById(R.id.route);
        spinner_city = (Spinner) view.findViewById(R.id.spinner_city);
    	add_button = (Button) view.findViewById(R.id.add_button);
    	remove_button = (Button) view.findViewById(R.id.remove_button);
    	//roundtrip = (CheckBox) view.findViewById(R.id.roundtrip);
    	//take_from_region = (CheckBox) view.findViewById(R.id.take_from_region);
    	//drop_of_in_region = (CheckBox) view.findViewById(R.id.drop_of_in_region);
    	leaving_day = (DatePicker) view.findViewById(R.id.leaving_day);
    	leaving_time = (TimePicker) view.findViewById(R.id.leaving_time);
    	leaving_time_seekbar = (SeekBar) view.findViewById(R.id.leaving_time_seekbar);
    	leaving_time_flexible_value = (TextView)view.findViewById(R.id.leaving_time_flexible_value);
    	//dropping_day = (DatePicker) view.findViewById(R.id.dropping_day);
    	//dropping_time_seekbar = (SeekBar) view.findViewById(R.id.dropping_time_seekbar);
    	//dropping_time = (TimePicker) view.findViewById(R.id.dropping_time);
    	//dropping_time_flexible_value = (TextView)view.findViewById(R.id.dropping_time_flexible_value);
    	seats_available = (EditText) view.findViewById(R.id.seats_available);
    	price = (EditText) view.findViewById(R.id.price);
    	phone = (EditText) view.findViewById(R.id.phone);
    	message = (EditText) view.findViewById(R.id.message);    
    	confirm_button = (Button) view.findViewById(R.id.confirm_button);
        
    	//add city to route click handler   
    	add_button.setOnClickListener(new OnClickListener (){
			@Override
			public void onClick(View v) {
				boolean not_listed = true;
				if (cities.size() > 0) {			
					for(String item : cities){
						if (item.equals(selected_city)){
							not_listed = false;
						}
					}
				}
				if (not_listed && cities.size() <= 4){
					Context context = getActivity().getApplicationContext();
	        		CharSequence text = "ok!";
		        	int duration = Toast.LENGTH_LONG;
		
		        	Toast toast = Toast.makeText(context, text, duration);
		        	toast.show();
		        	cities.add(selected_city);
		        	updateRouteView();
				} else{
					Context context = getActivity().getApplicationContext();
	        		CharSequence text = "bad!";
		        	int duration = Toast.LENGTH_LONG;
		
		        	Toast toast = Toast.makeText(context, text, duration);
		        	toast.show();
				}
				
			}});
    	
    	//remove city from route click handler   
    	remove_button.setOnClickListener(new OnClickListener (){
			@Override
			public void onClick(View v) {
				boolean is_listed = false;
				if (cities.size() > 0) {			
					for(String item : cities){
						if (item.equals(selected_city)){
							is_listed = true;
						}
					}
				}
				if (is_listed){
					Context context = getActivity().getApplicationContext();
	        		CharSequence text = "ok!";
		        	int duration = Toast.LENGTH_LONG;
		
		        	Toast toast = Toast.makeText(context, text, duration);
		        	toast.show();
		        	cities.remove(selected_city);
		        	updateRouteView();
				} else{
					Context context = getActivity().getApplicationContext();
	        		CharSequence text = "bad!";
		        	int duration = Toast.LENGTH_LONG;
		
		        	Toast toast = Toast.makeText(context, text, duration);
		        	toast.show();		        	
				}
				
			}});
    	
        leaving_time.setIs24HourView(true);          
        
        //leaving seek bar change handler
        leaving_time_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){           
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress * 30;
                leaving_seekbar_hours = (int) Math.ceil(progress / 60);
                leaving_seekbar_minutes = progress - leaving_seekbar_hours * 60;
                if (leaving_seekbar_hours >= 1){
                	leaving_time_flexible_value.setText("+- " + leaving_seekbar_hours + " val. " + leaving_seekbar_minutes + " min.");
                } else {
                	leaving_time_flexible_value.setText("+- " + leaving_seekbar_minutes + " min.");
                }
                
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });        
        
        /*dropping_time.setIs24HourView(true);
        
        dropping_time_seekbar.setProgress(0);
        dropping_time_seekbar.setMax(10);        

        //dropping seek bar change handler
        dropping_time_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress * 30;
                returning_seekbar_hours = (int) Math.ceil(progress / 60);
                returning_seekbar_minutes = progress - returning_seekbar_hours * 60;
                if (returning_seekbar_hours >= 1){
                	dropping_time_flexible_value.setText("+- " + returning_seekbar_hours + " val. " + returning_seekbar_minutes + " min.");
                } else {
                	dropping_time_flexible_value.setText("+- " + returning_seekbar_minutes + " min.");
                }
                
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        */
        //populate spinner with string adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.cities_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
        spinner_city.setAdapter(adapter);
        
        //instantiate selected_city
        if (selected_city == null){
        	selected_city = (String) spinner_city.getItemAtPosition(0);
        }
        
        //city list spinner on click listener
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
        	@Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
        		if (spinnerFlag == true) {
        			Context context = getActivity().getApplicationContext();
        			CharSequence text = "Hello toast!" + position;
	        		int duration = Toast.LENGTH_LONG;
	
	        		Toast toast = Toast.makeText(context, text, duration);
	        		toast.show();
	        		selected_city = (String) spinner_city.getItemAtPosition(position);
	        		
        		}        		
        		
        		if (!spinnerFlag) {
                    // from this moment on the selection will be accepted
                    spinnerFlag = true;
                    return;
                }
            }
        	
        	@Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                
            }
        });
        
        
        
        //confirm post click handler   
    	confirm_button.setOnClickListener(new OnClickListener (){
			@Override
			public void onClick(View v) {
				boolean correct_route = true;
				boolean correct_date_time = true;
				boolean correct_seats_number = true;
				boolean correct_price = true;
				Context context = getActivity().getApplicationContext();
    			StringBuilder text = new StringBuilder();
        		int duration = Toast.LENGTH_LONG;
        		JSONArray jArray;
        		String url;
                ServerDbQuerry sdq;
                String result;
                JSONObject json_data;

                //correct route
        		if (cities.size() <= 1){
					text.append(getString(R.string.error_inserting_driver_post_route));
					correct_route = false;
				}
        		
        		//correct seats number
        		if(seats_available.getText().toString().equals("")){
        			correct_seats_number = false;        				
    				text.append(getString(R.string.error_inserting_driver_post_seats_number));
        		} else {
        			db_seats_available = 0;
        			try {
        				db_seats_available = Integer.parseInt(seats_available.getText().toString());
        			} catch (NumberFormatException e){ 
        				correct_seats_number = false; 
        			}           			
        			if (db_seats_available == 0 || db_seats_available >= 10){
        				correct_seats_number = false;        				
        				text.append(getString(R.string.error_inserting_driver_post_seats_number));
        			}
        		}
        		
        		//correct price
        		if(price.getText().toString().equals("")){
        			correct_price = false;        				
    				text.append(getString(R.string.error_inserting_driver_post_price));
        		} else {
        			db_price = 0;
        			try {
        				db_price = Integer.parseInt(price.getText().toString());
        			} catch (NumberFormatException e){      
        				correct_price = false; 
        			}           			
        			if (db_price < 0 || db_price >= 300){
        				correct_price = false;        				
        				text.append(getString(R.string.error_inserting_driver_post_price));
        			}
        		}
        		
        		//correct date and time
        		Calendar leaving_calendar_from = Calendar.getInstance();  
        		leaving_calendar_from.set(Calendar.MINUTE, leaving_time.getCurrentMinute());
        		leaving_calendar_from.set(Calendar.HOUR_OF_DAY, leaving_time.getCurrentHour());
        		leaving_calendar_from.set(Calendar.DAY_OF_MONTH, leaving_day.getDayOfMonth());
        		leaving_calendar_from.set(Calendar.MONTH, leaving_day.getMonth());
        		leaving_calendar_from.set(Calendar.YEAR, leaving_day.getYear());
        		leaving_calendar_from.add(Calendar.HOUR_OF_DAY, -leaving_seekbar_hours);
        		leaving_calendar_from.add(Calendar.MINUTE, -leaving_seekbar_minutes);
        		Calendar leaving_calendar_to = Calendar.getInstance();  
        		leaving_calendar_to.set(Calendar.MINUTE, leaving_time.getCurrentMinute());
        		leaving_calendar_to.set(Calendar.HOUR_OF_DAY, leaving_time.getCurrentHour());
        		leaving_calendar_to.set(Calendar.DAY_OF_MONTH, leaving_day.getDayOfMonth());
        		leaving_calendar_to.set(Calendar.MONTH, leaving_day.getMonth());
        		leaving_calendar_to.set(Calendar.YEAR, leaving_day.getYear());
        		leaving_calendar_to.add(Calendar.HOUR_OF_DAY, leaving_seekbar_hours);
        		leaving_calendar_to.add(Calendar.MINUTE, leaving_seekbar_minutes);        		
        		Calendar current_calendar = Calendar.getInstance();
        		        		
        		if (leaving_calendar_to.getTimeInMillis() < current_calendar.getTimeInMillis()){
        			correct_date_time = false;        				
    				text.append(getString(R.string.error_inserting_driver_post_date_time));
    			}else{
    				db_leaving_date = leaving_calendar_from.get(Calendar.YEAR) + "-" +  leaving_calendar_from.get(Calendar.MONTH) + "-" + leaving_calendar_from.get(Calendar.DAY_OF_MONTH);
            		db_leaving_time_from = leaving_calendar_from.get(Calendar.HOUR_OF_DAY) + ":" + leaving_calendar_from.get(Calendar.MINUTE) + ":" + leaving_calendar_from.get(Calendar.SECOND);
            		db_leaving_time_to = leaving_calendar_to.get(Calendar.HOUR_OF_DAY) + ":" + leaving_calendar_to.get(Calendar.MINUTE) + ":" + leaving_calendar_to.get(Calendar.SECOND);
        		}
        		        		
        		//if all data is correct insert it to db
        		if (correct_route && correct_date_time && correct_seats_number && correct_price){        			
        			boolean successful_communication_with_database = true;
        			//preparing values to fit the database table
        			//prepare db_user_id
            		db_user_id = 1; //TODO
            		
            		//prepare db_route_id;        		
            		nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("route", route.getText().toString()));

                    //check whether such route description exists, if not insert new one into db 
                    url = getString(R.string.url_select_from_route);
                    sdq = new ServerDbQuerry(nameValuePairs, url);
                    result = sdq.returnJSON();                    
                    
                    //if this is new route, insert it to db
            	    try{
            	        jArray = new JSONArray(result);
            	        for(int i=0;i<jArray.length();i++){
            	            json_data = jArray.getJSONObject(i);	  
            	            if(json_data.getInt("how_many") == 0){
            	            	//insert new route to db
			            	    url = getString(R.string.url_insert_to_route);
			                    sdq = new ServerDbQuerry(nameValuePairs, url);	
            	            }
            	        }
            	    }catch(JSONException e){
            	    	Log.e("log_tag", "selectFromRoute.php or insertToRoute.php error: "+e.toString());
            	    	successful_communication_with_database = false;
            	    }
                         
            	    //retrieve route_id
            	    url = getString(R.string.url_select_from_route);
                    sdq = new ServerDbQuerry(nameValuePairs, url);
                    result = sdq.returnJSON();                    
                    
            	    try{
            	        jArray = new JSONArray(result);
            	        for(int i=0;i<jArray.length();i++){
            	            json_data = jArray.getJSONObject(i);	  
            	            db_route_id = json_data.getInt("id");            	           
            	        }
            	    }catch(JSONException e){
            	    	Log.e("log_tag", "selectFromRoute.php get route_id error: "+e.toString());
            	    	successful_communication_with_database = false;
            	    }
            	   
            	    //prepare db_phone_id            	   
            	    if (!phone.getText().toString().equals("86") && !phone.getText().toString().equals("")) { 
            	    	nameValuePairs = new ArrayList<NameValuePair>(); 	    	
	                    nameValuePairs.add(new BasicNameValuePair("phone", phone.getText().toString()));
	                    
	            	    //check whether such phone exists, if not insert new one into db 
	                    url = getString(R.string.url_select_from_phone);
	                    sdq = new ServerDbQuerry(nameValuePairs, url);
	                    result = sdq.returnJSON();
	                    
	                    //if this is new route, insert it to db
	            	    try{
	            	        jArray = new JSONArray(result);
	            	        for(int i=0;i<jArray.length();i++){
	            	            json_data = jArray.getJSONObject(i);	  
	            	            if(json_data.getInt("how_many") == 0){
	            	            	//insert new route to db
				            	    url = getString(R.string.url_insert_to_phone);
				                    sdq = new ServerDbQuerry(nameValuePairs, url);	
	            	            }
	            	        }
	            	    }catch(JSONException e){
	            	    	Log.e("log_tag", "selectFromPhone.php or insertToPhone.php error: "+e.toString());
	            	    	successful_communication_with_database = false;
	            	    }
	                         
	            	    //retrieve phone_id
	            	    url = getString(R.string.url_select_from_phone);
	                    sdq = new ServerDbQuerry(nameValuePairs, url);
	                    result = sdq.returnJSON();
	            	    try{
	            	        jArray = new JSONArray(result);
	            	        for(int i=0;i<jArray.length();i++){
	            	            json_data = jArray.getJSONObject(i);	  
	            	            db_phone_id = json_data.getInt("id");            	           
	            	        }
	            	    }catch(JSONException e){
	            	    	Log.e("log_tag", "selectFromPhone.php get phone_id error: "+e.toString());
	            	    	successful_communication_with_database = false;
	            	    }
            	    }
            		
            	    //prepare db_seats_available
            	    //checked in precious step	                    
	            	   
            	    //prepare db_message
            	    db_message = message.getText().toString();
            	    
            		//db_leaving_date;
            	    //checked in precious step
            	    
            		//db_leaving_time_from;
            	    //checked in precious step
            	    
            		//db_leaving_time_to;
            	    //checked in precious step
            		
            	    //prepare db_returning_date
            	    db_returning_date = null;
            	    
            		//prepare db_returning_time_from
            	    db_returning_time_from = null;
            	    
            	    //prepare db_returning_time_to
            	    db_returning_time_to = null;         	    
            		            		
            	    //prepare db_price
            	    //checked in precious step
            	    
            	    //prepare db_roundtrip
            	    db_roundtrip = 0;
            		
            	    //prepare db_take_from_region
            	    db_take_from_region = 0;
            	    
            		//prepare db_drop_of_in_region
            	    db_drop_of_in_region = 0;     
        			
        			//if data is correct and database is communicating insert record to db
        			if (successful_communication_with_database){
        			
	            	    //insert mew record to db
	            	    nameValuePairs = new ArrayList<NameValuePair>(); 	    	
	            	    nameValuePairs.add(new BasicNameValuePair("user_id", Integer.toString(db_user_id)));
	            	    nameValuePairs.add(new BasicNameValuePair("route_id", Integer.toString(db_route_id)));
	            	    nameValuePairs.add(new BasicNameValuePair("phone_id", Integer.toString(db_phone_id)));
	            	    nameValuePairs.add(new BasicNameValuePair("seats_available", Integer.toString(db_seats_available)));
	            	    nameValuePairs.add(new BasicNameValuePair("message", db_message));
	            	    nameValuePairs.add(new BasicNameValuePair("leaving_date", db_leaving_date));
	            	    nameValuePairs.add(new BasicNameValuePair("leaving_time_from", db_leaving_time_from));
	            	    nameValuePairs.add(new BasicNameValuePair("leaving_time_to", db_leaving_time_to));
	            	    nameValuePairs.add(new BasicNameValuePair("returning_date", db_returning_date));
	            	    nameValuePairs.add(new BasicNameValuePair("returning_time_from", db_returning_time_from));
	            	    nameValuePairs.add(new BasicNameValuePair("returning_time_to", db_returning_time_to));
	            	    nameValuePairs.add(new BasicNameValuePair("price", Integer.toString(db_price)));
	            	    nameValuePairs.add(new BasicNameValuePair("roundtrip", Integer.toString(db_roundtrip)));
	            	    nameValuePairs.add(new BasicNameValuePair("take_from_region", Integer.toString(db_take_from_region)));
	            	    nameValuePairs.add(new BasicNameValuePair("drop_of_in_region", Integer.toString(db_drop_of_in_region)));                    
	                    
	            	    //check whether such phone exists, if not insert new one into db 
	                    url = getString(R.string.url_insert_to_driverpost);
	                    sdq = new ServerDbQuerry(nameValuePairs, url);
	                    result = sdq.returnJSON();
	                    
	                    //if this is new route, insert it to db
	            	    try{
	            	        jArray = new JSONArray(result);
	            	        for(int i=0;i<jArray.length();i++){
	            	            json_data = jArray.getJSONObject(i);	  
	            	            if(json_data.getInt("id") > 0){
	            	            	text.append(getString(R.string.message_successful_insert_post));
	            	            } else {
	            	            	text.append(getString(R.string.message_unsuccessful_insert_post));
	            	            }
	            	        }
	            	        
	            	    }catch(JSONException e){
	            	    	Log.e("log_tag", "insertToDriverPost.php error: "+e.toString());
	            	    	text.append(getString(R.string.message_unsuccessful_insert_post));
	            	    }            
        			} else {
        				text.append(getString(R.string.message_unsuccessful_insert_post));
        			}
	        	}    
        		Toast toast = Toast.makeText(context, text, duration);
        		toast.show();	
			}});
        
        return view;    	
    }
    
    
 
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }
    
    //Update route TextView
    public void updateRouteView(){
    	StringBuilder sb = new StringBuilder();
    	if (cities.size() == 0){
    		route.setText(R.string.route);
    	} else {
    		sb.append(cities.get(0));
    		for (int i = 1; i < cities.size(); i++){
    			sb.append(" - " + cities.get(i));
    		}
    		route.setText(sb.toString());
    	}    	
    }
 
}