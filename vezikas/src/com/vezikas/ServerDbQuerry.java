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

import android.util.Log;

public class ServerDbQuerry {

    //query the database through json   
    private String result = "";
    private InputStream is = null;    
     
    public ServerDbQuerry (ArrayList<NameValuePair> nvp, String url){    	
    	
	    //http post
	    try{
	    	HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(url);
	        httppost.setEntity(new UrlEncodedFormEntity(nvp, "UTF-8"));
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();	
	    } catch(Exception e){
	        Log.e("log_tag", "Error in http connection "+e.toString());
	    }
	    
	    //convert response to string
	    try{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
	        StringBuilder sb1 = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	            sb1.append(line + "\n");
	        }
	        is.close();
	            
	        result=sb1.toString();	        
	    } catch(Exception e){
	        Log.e("log_tag", "Error converting result "+e.toString());
	    }
	    
    }
    
    public String returnJSON (){
    	return result;
    }
}
