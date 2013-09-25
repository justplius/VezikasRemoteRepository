package com.vezikas;

import java.util.Date;

import android.content.Context;
import android.text.format.Time;

public class DriverPost {
	
	//Post data
	private float rating;	
	private int seats_available;
	private String route;
	private Time date;
	private Time leavingTime;
	private Time droppingTime;
	private int thumbnail;
			
	//Constructor
	DriverPost () {
		rating = 4.5f;
		leavingTime = new Time();
		leavingTime.getCurrentTimezone();
		leavingTime.setToNow();
		droppingTime = new Time();
		droppingTime.set(leavingTime.toMillis(true) + 1000*60*60*3);
		route = new String("Ðiauliai - Kaunas - Vilnius");
		seats_available = 4;				
		date = new Time();
		date.set(leavingTime.toMillis(true));
		thumbnail = R.drawable.ic_launcher;
	}
	
	public void setRating(float _rating){
	   	rating = _rating;
	}
			
	public void setRouteInformation(String _route){
	   	route = _route;
	}
			
	public void setDate(Time _date){
	   	date = _date;
	}
			
	public void setLeavingTime(Time _time){
		leavingTime = _time;
	}
	
	public void setDroppingTime(Time _time){
		droppingTime = _time;
	}
			
	public void setSeatsAvailable(int _seats_available){
	   	seats_available = _seats_available;
	}
	
	public void setThumbnailInt(int _thumbnail){
	   	thumbnail = _thumbnail;
	}	
	
	public float getRating(){
	   	return rating;
	}
			
	public String getRouteInformation(){
	   	return route;
	}
			
	public String getDate(){
		StringBuilder dateString = new StringBuilder();	
		if (date.month <= 9){
			dateString.append("0");
		}
		dateString.append(date.month + " " + date.monthDay);
	   	return dateString.toString();
	}
			
	public String getTime(){
		StringBuilder timeString = new StringBuilder();		
		timeString.append(leavingTime.hour + ":" + leavingTime.minute + " - ");
		timeString.append(droppingTime.hour + ":" + droppingTime.minute);
	   	return timeString.toString();
	}
			
	public String getSeatsAvailable(){
		String seatsText = "vietos";
		if (seats_available == 1){
			seatsText = "vieta";
		}
	   	return String.valueOf(seats_available) + " " + seatsText + " | ";
	}
	
	public int getThumbnailInt(){
	   	return thumbnail;
	}
	
	public String getDateTime(){
	   	return (getDate() + " | " + getTime());
	}
	
}
