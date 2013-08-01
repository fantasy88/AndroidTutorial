
package com.example.webservicedemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.webservicedemo.json.JsonFunction;

public class AddNew extends Activity {
    
    EditText edtName;
    EditText edtPrice;
    EditText edtDes;
    
    JsonFunction jsonFunction = new JsonFunction();
    private ProgressDialog pDialog;
    // url to create new product
    private final String url_create_product = "http://api.androidhive.info/android_connect/create_product.php";
 
    // JSON Node names
    private final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new);
        edtName = (EditText)findViewById(R.id.edtProductName);
        edtPrice = (EditText)findViewById(R.id.edtPrice);
        edtDes = (EditText)findViewById(R.id.edtDescription);
    }
    
    public void onSave(View v) {
        new AddNewProduct().execute();
    }
    
    class AddNewProduct extends AsyncTask<Void, Void, Void>
    {
        

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(AddNew.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String name = edtName.getText().toString();
            String price = edtPrice.getText().toString();
            String description = edtDes.getText().toString();
            
            List<NameValuePair> values = new ArrayList<NameValuePair>();
            values.add(new BasicNameValuePair("name", name));
            values.add(new BasicNameValuePair("price", price));
            values.add(new BasicNameValuePair("description", description));
            
            try {
                JSONObject jsonObject = jsonFunction.makeHttpRequest(url_create_product, JsonFunction.POST, values, null, null);
                int success = jsonObject.getInt(TAG_SUCCESS);
                
                if (success == 1) {
                    // successfully created product
                    runOnUiThread(new Runnable() { 
                        @Override
                        public void run() {
                            Toast.makeText(AddNew.this, "Added new product", Toast.LENGTH_SHORT).show();
                        }
                    });
                    
                    Intent i = new Intent(getApplicationContext(), GetAllProducts.class);
                    startActivity(i);
 
                    // closing this screen
                    finish();
                } else {
                    runOnUiThread(new Runnable() { 
                        @Override
                        public void run() {
                            Toast.makeText(AddNew.this, "Can't create new product", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pDialog.dismiss();
        }
        
        
        
    }

}
