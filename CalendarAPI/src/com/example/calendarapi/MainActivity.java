
package com.example.calendarapi;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.lbMessage);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("");
        actionBar.setHomeButtonEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnShowCalendar:
                showCalendar();
                break;
            case R.id.btnExit:
                finish();
                break;
            case R.id.create_new:
                createNewEvent();
                break;
            case R.id.showListCalendar:
                listAllCalendars();
                break;
            case R.id.find_calendar:
                findCalendar();
                break;
            case R.id.showListEvents:
                listAllEvents();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCalendar() {
        Calendar calendar = Calendar.getInstance();
        long current = calendar.getTimeInMillis();
        Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, current);
        Uri uri = builder.build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }

    private void createNewEvent() {
        startActivity(new Intent(MainActivity.this, NewEventActivity.class));
    }

    private void findCalendar() {
        // Uri uri =
        // ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI,
        // eventId);
        // Intent intent = new Intent(Intent.ACTION_EDIT).setData(uri);
        // startActivity(intent);
        long CalendarId = findCalendarItem("duythanh28@gmail.com", "com.google",
                "duythanh28@gmail.com");
        result.setText(String.format("Calendar Id %d", CalendarId));

    }

    private long findCalendarItem(String accountName, String accountType, String displayName) {
        ContentResolver resolver = getContentResolver();
        Cursor cursor = null;

        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND " +
                "(" + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND " +
                "(" + CalendarContract.Calendars.CALENDAR_DISPLAY_NAME + " = ?))";

        String[] returnColumns = new String[] {
                CalendarContract.Calendars._ID
        };
        String[] selectionArgs = new String[] {
                accountName, accountType, displayName
        };

        cursor = resolver.query(CalendarContract.Calendars.CONTENT_URI, returnColumns, selection,
                selectionArgs, null);

        long ID = -1;

        if (cursor.moveToNext()) {
            ID = cursor.getLong(0);
        }

        return ID;
    }

    private void listAllCalendars() {
        String[] returnColumns = new String[] {
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.ACCOUNT_TYPE,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
        };

        Cursor cursor = null;
        ContentResolver resolver = getContentResolver();

        cursor = resolver.query(CalendarContract.Calendars.CONTENT_URI, returnColumns, null, null,
                null);

        String kq = "";
        while (cursor.moveToNext()) {
            kq += String.format("ID=%d Name=%s Type=%s CalendarName=%s",
                    cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3));
        }
        result.setText(kq);
        cursor.close();
    }

    private void listAllEvents() {
        String[] returnColumns = new String[] {
                CalendarContract.Events._ID,
                CalendarContract.Events.ACCOUNT_NAME,
                CalendarContract.Events.ACCOUNT_TYPE,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.EVENT_LOCATION,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.CALENDAR_DISPLAY_NAME
        };

        Cursor cursor = null;
        ContentResolver resolver = getContentResolver();

        cursor = resolver.query(CalendarContract.Events.CONTENT_URI, returnColumns, null, null,
                null);

        String kq = "";
        while (cursor.moveToNext()) {
            kq += String.format("ID=%d Name=%s Type=%s Title=%s Location=%s CalendarName=%s",
                    cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4),
                    cursor.getString(5));
        }
        result.setText(kq);
        cursor.close();
    }
}
