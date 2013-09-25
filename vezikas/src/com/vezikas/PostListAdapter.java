package com.vezikas;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class PostListAdapter extends ArrayAdapter<DriverPost> {

	private Context context;
	private ArrayList<DriverPost> allPersons;
	
	private LayoutInflater mInflater;
	private boolean mNotifyOnChange = true;
	
	public PostListAdapter(Context context, ArrayList<DriverPost> mPersons) {
	    super(context, R.layout.message_item);
	    this.context = context;
	    this.allPersons = new ArrayList<DriverPost>(mPersons);
	    this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
	    return allPersons .size();
	}
	
	@Override
	public DriverPost getItem(int position) {
	    return allPersons.get(position);
	}
	
	@Override
	public long getItemId(int position) {
	    return position;
	}
	
	@Override
	public int getPosition(DriverPost item) {
	    return allPersons .indexOf(item);
	}
	
	@Override
	public int getViewTypeCount() {
	    return 1; //Number of types + 1 !!!!!!!!
	}
	
	@Override
	public int getItemViewType(int position) {
	    return 1;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    final ViewHolder holder;
	    int type = getItemViewType(position);
	    if (convertView == null) {
	        holder = new ViewHolder();
	        switch (type) {
	        case 1:
	            convertView = mInflater.inflate(R.layout.message_item,parent, false);
	            holder.route_information = (TextView) convertView.findViewById(R.id.route_information);            
	            holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);	            
	            holder.seats_available = (TextView) convertView.findViewById(R.id.seats_available);	            
	            holder.date_time_information = (TextView) convertView.findViewById(R.id.date_time_information);	            
	            holder.rating = (RatingBar) convertView.findViewById(R.id.rating);
	            break;
	        }
	        convertView.setTag(holder);
	    } else {
	        holder = (ViewHolder) convertView.getTag();
	    }
	    holder.route_information.setText(allPersons.get(position).getRouteInformation());
	    holder.thumbnail.setImageResource(allPersons.get(position).getThumbnailInt());	    
	    holder.seats_available.setText(allPersons.get(position).getSeatsAvailable());
	    holder.date_time_information.setText(allPersons.get(position).getDateTime());
	    holder.rating.setRating(allPersons.get(position).getRating());
	    
	    holder.pos = position;
	    return convertView;
	}
	
	@Override
	public void notifyDataSetChanged() {
	    super.notifyDataSetChanged();
	    mNotifyOnChange = true;
	}
	
	public void setNotifyOnChange(boolean notifyOnChange) {
	    mNotifyOnChange = notifyOnChange;
	}
	
	
	//---------------static views for each row-----------//
	static class ViewHolder {
	
		TextView route_information;
		ImageView thumbnail;
		TextView seats_available;
		TextView date_time_information;
		RatingBar rating;
		int pos; //to store the position of the item within the list

	}
	     
}