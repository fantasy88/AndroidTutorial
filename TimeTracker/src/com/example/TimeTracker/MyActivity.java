package com.example.TimeTracker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    TimeTrackerOpenHelper timeTrackerOpenHelper;
    TimeTrackerAdapter timeTrackerAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        timeTrackerOpenHelper  = new TimeTrackerOpenHelper(this);
//        Cursor cursor = timeTrackerOpenHelper.getAllRecords();
//        if (cursor.moveToFirst()) {
//            do {
//                String times = cursor.getString(1);
//                String notes = cursor.getString(2);
//            } while (cursor.moveToNext());
//        }
//        if (!cursor.isClosed()) {
//            cursor.close();
//        }

        ListView lstView = (ListView) findViewById(R.id.lstView);
        timeTrackerAdapter = new TimeTrackerAdapter(this,timeTrackerOpenHelper.getAllRecords());
        lstView.setAdapter(timeTrackerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.time_list_menu,menu);
        return true;
    }
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.new_time:
                Intent intent = new Intent(this,AddTimeActivity.class);
                startActivityForResult(intent,1);
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            String times = data.getStringExtra("time");
            String notes = data.getStringExtra("note");
            timeTrackerOpenHelper.SaveTimeRecord(times,notes);
            timeTrackerAdapter.changeCursor(timeTrackerOpenHelper.getAllRecords());
        }
    }
}
