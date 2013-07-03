package com.example.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.TextView;

public class SecondActivity extends Activity {
	TextView tx;
	Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		tx = (TextView)findViewById(R.id.lbPost);	
		handler = new Handler();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					final int value = i;
					try {
						Thread.sleep(1000);
						handler.post(new Runnable() {
							public void run() {
								tx.setText(tx.getText() + "Item " + String.valueOf(value)
										   + System.getProperty("line.separator"));
							}
						  });
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}
	
	

}
