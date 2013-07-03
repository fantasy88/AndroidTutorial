package com.example.TimeTracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created with IntelliJ IDEA.
 * User: Fantasy
 * Date: 1/18/13
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddTimeActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_record);
    }

    public void onHandler(View v)
    {
        switch (v.getId())
        {
            case R.id.btnSave:
                Intent intent = getIntent();

                EditText txtTimer = (EditText)findViewById(R.id.edtTimer);
                intent.putExtra("time",txtTimer.getText().toString());

                EditText txtNote = (EditText)findViewById(R.id.edtNote);
                intent.putExtra("note",txtNote.getText().toString());

                this.setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }
}