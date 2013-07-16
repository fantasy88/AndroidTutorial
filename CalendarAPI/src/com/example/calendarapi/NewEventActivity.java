
package com.example.calendarapi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class NewEventActivity extends Activity {

    EditText edtStartDate;
    EditText edtStartTime;
    EditText edtEndDate;
    EditText edtEndTime;
    EditText edtTitle;
    EditText edtLocation;
    EditText edtDescription;
    EditText edtInvites;

    DateFormat dateFormat = DateFormat.getDateTimeInstance();
    Calendar startCalendar = Calendar.getInstance();
    Calendar endCalendar = Calendar.getInstance();

    OnDateSetListener startDate = new OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, monthOfYear);
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate(edtStartDate,startCalendar);
        }
    };

    OnDateSetListener endDate = new OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, monthOfYear);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate(edtEndDate,endCalendar);
        }
    };

    OnTimeSetListener startTime = new OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            startCalendar.set(Calendar.MINUTE, minute);
            updateTime(edtStartTime,startCalendar);
        }
    };
    
    OnTimeSetListener endTime = new OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            endCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            endCalendar.set(Calendar.MINUTE, minute);
            updateTime(edtEndTime,endCalendar);
        }
    };

    private void updateDate(EditText editText,Calendar calendar) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(calendar.getTime()));
    }

    private void updateTime(EditText editText,Calendar calendar) {
        String myFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(calendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        edtStartDate = (EditText) findViewById(R.id.edtStartDate);
        edtStartTime = (EditText) findViewById(R.id.edtStartTime);
        edtEndDate = (EditText) findViewById(R.id.edtEndDate);
        edtEndTime = (EditText) findViewById(R.id.edtEndTime);
        edtTitle = (EditText)findViewById(R.id.edtTitle);
        edtLocation = (EditText)findViewById(R.id.edtLocation);
        edtDescription = (EditText)findViewById(R.id.edtDescription);
        edtInvites = (EditText)findViewById(R.id.edtList);
    }

    public void showChoiceStartDate(View v) {
        new DatePickerDialog(NewEventActivity.this, startDate,
                startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void showChoiceStartTime(View v) {
        new TimePickerDialog(NewEventActivity.this, startTime,
                startCalendar.get(Calendar.HOUR_OF_DAY),
                startCalendar.get(Calendar.MINUTE), true).show();
    }

    public void showChoiceEndDate(View v) {
        new DatePickerDialog(NewEventActivity.this, endDate,
                endCalendar.get(Calendar.YEAR),
                endCalendar.get(Calendar.MONTH),
                endCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    
    public void showChoiceEndTime(View v) {
        new TimePickerDialog(NewEventActivity.this, endTime,
                endCalendar.get(Calendar.HOUR_OF_DAY),
                endCalendar.get(Calendar.MINUTE), true).show();
    }

    public void addNewEvent(View v) {
        Intent intent = new Intent(Intent.ACTION_INSERT).setData(CalendarContract.Events.CONTENT_URI);
        String title = edtTitle.getText().toString();
        String location = edtLocation.getText().toString();
        String description = edtDescription.getText().toString();
        String invites = edtInvites.getText().toString();
        putNewEventExtras(intent, title, location, description, startCalendar, endCalendar, invites);
        startActivity(intent);
    }
    
    private void putNewEventExtras(Intent intent,String title,String location,String description,Calendar begin,Calendar end,String invites)
    {
        if(title.trim() != null)
        {
            intent.putExtra(CalendarContract.Events.TITLE, title);
        }
        if(location.trim() != null)
        {
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        }
        if(description.trim() != null)
        {
            intent.putExtra(CalendarContract.Events.DESCRIPTION, description);
        }
        if(begin != null)
        {
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin.getTimeInMillis());
        }
        if(end != null)
        {
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end.getTimeInMillis());
        }
        if(invites.trim() != null)
        {
            intent.putExtra(Intent.EXTRA_EMAIL, invites);
        }
    }

    public void cancelNewEvent(View v) {
        startActivity(new Intent(NewEventActivity.this, MainActivity.class));
        finish();
    }
}
