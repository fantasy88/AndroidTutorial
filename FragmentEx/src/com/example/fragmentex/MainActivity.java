
package com.example.fragmentex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] array = getResources().getStringArray(R.array.list_menu);
        ListView listView = (ListView) findViewById(R.id.lstMenu);

        listView.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, array));
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                switch (pos) {
                    case 0:
                        startNewActivity(StaticsActivity.class);
                        break;
                    case 1:
                        startNewActivity(DynamicActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    private void startNewActivity(Class<?> newClass) {
        Intent intent = new Intent(MainActivity.this,newClass);
        startActivity(intent);
    }

}
