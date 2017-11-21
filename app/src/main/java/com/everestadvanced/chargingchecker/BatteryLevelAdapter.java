package com.everestadvanced.chargingchecker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 11/20/2017.
 */

public class BatteryLevelAdapter extends BaseAdapter
{
    public static Context context;
    public static LayoutInflater inflter;
    public static ArrayList<String> myList;


    public BatteryLevelAdapter(Context applicationContext,ArrayList<String> data) {

        this.context = applicationContext;
        this.myList=data;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int i) {
        return  myList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.batterylevelapdapter, null);
        TextView names = (TextView) view.findViewById(R.id.txtnumber);

        names.setText(myList.get(position) + "%");
        return view;
    }

}