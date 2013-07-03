package com.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class MainActivity extends Activity{
	
	SearchView searchView;
	ListView lstView;
	String[] menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		lstView = (ListView)findViewById(R.id.lstTest);
		menu = getResources().getStringArray(R.array.list_menu);
		lstView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu));
		lstView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				switch (pos) {
				case 0:
					startActivity(new Intent(MainActivity.this,SearchWidgetActivity.class));
					break;
				case 1:
					startActivity(new Intent(MainActivity.this,OtherActivity.class));
					break;
				default:
					break;
				}
			}
		});
		
		
	}


}
