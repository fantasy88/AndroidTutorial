
package com.example.readcontact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    ListView listView;
    List<HashMap<String, String>> listContacts;
    List<UserContacts> list;
    CheckBox cbAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lstContact);

        list = new ArrayList<UserContacts>();
        cbAll = (CheckBox) findViewById(R.id.cbAll);

        showContacts();
    }

    public void getAllContacts(View v) {

        StringBuffer sb = new StringBuffer();
        for (UserContacts userContacts : list) {
            if (userContacts.isSelected()) {
                if (sb.length() > 0) {
                    sb.append('\n');
                }
                sb.append(userContacts.getPhone());
            }
        }
        Toast.makeText(MainActivity.this, sb, Toast.LENGTH_SHORT).show();
    }

    private void showContacts()
    {
        listView.setAdapter(new MyAdapter(MainActivity.this, getContacts(), cbAll));
    }

    private List<UserContacts> getContacts() {
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            String phoneNumber = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            list.add(new UserContacts(name,phoneNumber));
        }
        return list;
    }

}
