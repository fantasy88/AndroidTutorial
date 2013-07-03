package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class NewActivity extends FragmentActivity {
	
	Fragment picture = new Picture_Fragment();
	Fragment slide = new Slide_Fragment();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_frame);
		
//		Intent intent = getIntent();
//		TextView out = (TextView)findViewById(R.id.lbName);		
//		out.setText(intent.getStringExtra("value"));
		
		FragmentManager fragM = getSupportFragmentManager();
		FragmentTransaction fragmentT = fragM.beginTransaction();
		Fragment fragS = new Slide_Fragment();
		
		fragmentT.replace(R.id.frame1, fragS,"Slide");
		
		fragmentT.commit();
	}
	
	public void swapFrag(View v)
	{
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		Fragment current = fragmentManager.findFragmentByTag("Slide");
		
		if(current.isVisible())
		{
			fragmentTransaction.replace(R.id.frame1, picture,"Pic");
		}else {
			fragmentTransaction.replace(R.id.frame1, slide,"Side");
		}
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
	
}
