package com.example.TimeTracker;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Fantasy
 * Date: 1/13/13
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimeTrackerAdapter extends CursorAdapter{

//    private ArrayList<TimeRecord> times = new ArrayList<TimeRecord>();

    public TimeTrackerAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtTime = (TextView) view.findViewById(R.id.txtTimer);
        txtTime.setText(cursor.getString(cursor.getColumnIndex("timer")));
        TextView txtNote = (TextView) view.findViewById(R.id.txtNote);
        txtNote.setText(cursor.getString(cursor.getColumnIndex("notes")));
    }

//    public TimeTrackerAdapter() {
//
////        times.add(new TimeRecord(
////                "38:23", "Feeling good!"));
////        times.add(new TimeRecord(
////                "49:01", "Tired. Needed more caffeine"));
////        times.add(new TimeRecord(
////                "26:21", "I’m rocking it!"));
////        times.add(new TimeRecord(
////                "29:42", "Lost some time on the hills, but pretty good."));
//    }

//    @Override
//    public int getCount() {
//        return times.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return getItem(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//        if(view == null)
//        {
//            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//            view = inflater.inflate(R.layout.list_item,parent,false);
//        }
//
//        TimeRecord time = times.get(position);
//
//        TextView txtTimer = (TextView)view.findViewById(R.id.txtTimer);
//        txtTimer.setText(time.getTimes());
//
//        TextView txtNote = (TextView)view.findViewById(R.id.txtNote);
//        txtNote.setText(time.getNote());
//
//        return  view;
//    }


}
