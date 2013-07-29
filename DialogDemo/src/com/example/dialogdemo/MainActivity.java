
package com.example.dialogdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final int ALERT_DIALOG = 0;
    private final int PROGRESS_DIALOG = 1;
    private final int CUSTOM_DIALOG = 2;
    private int progressState = 0;
    private long fileSize = 0;
    private Handler progressHandler = new Handler();
    private Dialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressWarnings("deprecation")
    public void onAlertDialog(View v) {
        showDialog(ALERT_DIALOG);
    }
    
    @SuppressWarnings("deprecation")
    public void onProgressDialog(View v) {
        showDialog(PROGRESS_DIALOG);
    }
    
    @SuppressWarnings("deprecation")
    public void onCustomDialog(View v) {
        showDialog(CUSTOM_DIALOG);
    }

    @SuppressLint("ShowToast")
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ALERT_DIALOG:
                Builder builder = new Builder(this);
                return builder.setMessage("Do you want to quit?")
                        .setNegativeButton("No", new OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "You choice NO", 5).show();
                            }
                        }).setPositiveButton("Yes", new OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "You choice YES", 5).show();
                            }
                        })
                        .create();
            case PROGRESS_DIALOG:
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Preparing ...");
                progressDialog.setCancelable(true);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setProgress(0);
                progressDialog.setMax(100);
                progressState = 0;
                fileSize = 0;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progressState < 100) {
                            progressState = fileDownload();
                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {
                            }
                            progressHandler.post(new Runnable() {
                                
                                @Override
                                public void run() {
                                    progressDialog.setProgress(progressState);
                                }
                            });
                        }
                        if(progressState >= 100)
                        {
                            try {
                                Thread.sleep(2000);
                            } catch (Exception e) {
                            }
                            progressDialog.dismiss();
                        }
                    }
                });
                dialog = progressDialog;
                thread.start();
                return dialog;
            case CUSTOM_DIALOG:
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Timer");
                
                TextView tx = (TextView)dialog.findViewById(R.id.lbMessage);
                tx.setText("This is Custom Dialog");
                
                return dialog;
        }
        return null;
    }
    
    private int fileDownload() {
        while (fileSize < 100) {
            fileSize ++;
            switch ((int)fileSize) {
                case 10:
                    return 10;
                case 20:
                    return 20;
                case 30:
                    return 30;
                case 40:
                    return 40;
                case 50:
                    return 50;
                case 60:
                    return 60;
                case 70:
                    return 70;
                case 80:
                    return 80;
                case 90:
                    return 90;
                case 100:
                    return 100;
            }
        }
        return 100;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
