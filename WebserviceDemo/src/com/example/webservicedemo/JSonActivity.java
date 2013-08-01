
package com.example.webservicedemo;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class JSonActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
    }

    public void onClickButton(View v) {
        switch (v.getId()) {
            case R.id.btnGetAll:
                Intent getAll = new Intent(v.getContext(),GetAllProducts.class);
                startActivity(getAll);
                break;
            case R.id.btnAddNew:
                Intent addNew = new Intent(v.getContext(),AddNew.class);
                startActivity(addNew);
                break;
            default:
                break;
        }
    }

}
