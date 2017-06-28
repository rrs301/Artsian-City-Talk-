package com.innocruts.artisans;

/**
 * Created by Rahul on 10/6/2016.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Belal on 7/22/2015.
 */
public class CustomList extends ArrayAdapter<String> {

    private String[] desc;
    private ArrayList<String> names;
    private ArrayList<String> addr;
    private ArrayList<String> phone;
    private Integer[] imageid;
    private Activity context;

    public CustomList(Activity context, ArrayList<String> names, ArrayList<String> addr, ArrayList<String> phone) {
        super(context, R.layout.listviewmodern, names);
        this.context = context;
        this.names = names;
        this.desc = desc;
        this.addr=addr;
        this.phone=phone;
        this.imageid = imageid;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.listviewmodern, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView address = (TextView) listViewItem.findViewById(R.id.address);
        TextView phoneText = (TextView) listViewItem.findViewById(R.id.phone);

        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);

        textViewName.setText(names.get(position));
        address.setText(addr.get(position));
        phoneText.setText("Contact Number: "+phone.get(position));
       // image.setImageResource(imageid[position]);
        return  listViewItem;
    }
}
