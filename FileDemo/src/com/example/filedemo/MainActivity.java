
package com.example.filedemo;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private boolean isExternal = true;
    EditText edtContent;
    FileHandler fileHandler;
    private final int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtContent = (EditText) findViewById(R.id.edtContent);
        fileHandler = new FileHandler(MainActivity.this);
    }

    @SuppressLint("SdCardPath")
    public void onSave(View v) throws IOException {
        if (isExternal)
        {
            String path = "/sdcard/notes.txt";
            fileHandler
                    .saveTextFile(FileHandler.EXTERNAL_SD, path, edtContent.getText().toString());
        }
        else {
            String path = "notes.txt";
            fileHandler
                    .saveTextFile(FileHandler.INTERNAL_SD, path, edtContent.getText().toString());
        }
        edtContent.setText("");
    }

    public void onOpen(View v) throws IOException {
        if (isExternal)
        {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("file/*");
            startActivityForResult(intent, PICKFILE_RESULT_CODE);
        }
        else {
            String path = "notes.txt";
            String content = fileHandler.openTextFile(FileHandler.INTERNAL_SD, path);
            edtContent.setText(content);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    String path = data.getData().getPath();
                    try {
                        String content = fileHandler.openTextFile(FileHandler.EXTERNAL_SD, path);
                        edtContent.setText(content);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                break;
        }
    }

    public String timeStamp() {
        Date myDate = new Date();
        return (DateFormat.getDateInstance().format(myDate) + " " + DateFormat.getTimeInstance()
                .format(myDate));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.external:
                isExternal = true;
                break;
            case R.id.internal:
                isExternal = false;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
