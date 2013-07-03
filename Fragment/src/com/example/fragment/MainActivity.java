package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void buttonClick(View v)
	{
		Intent intent = new Intent(this,NewActivity.class);
	    intent.putExtra("value", "Hello ThanhND");
	    startActivity(intent);
	}

}
