package com.example.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView tx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tx = (TextView)findViewById(R.id.lbMessage);
	}
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			String myKey = bundle.getString("myKey");
			tx.setText(tx.getText() + "Item " + myKey
					   + System.getProperty("line.separator"));
		}
		 
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Thread backThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0 ;i < 10;i++)
				{
					try {
						Thread.sleep(1000);
						Bundle bundle = new Bundle();
						bundle.putString("myKey", "My Value: " + String.valueOf(i));
						Message msg = new Message();
						msg.setData(bundle);
						handler.sendMessage(msg);
					} catch (InterruptedException e) {
						Log.v("Error", e.toString());
					}
				}
			}
		});
		backThread.start();
	}
	
	

}
