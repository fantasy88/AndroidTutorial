
package com.example.webservicedemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.webservicedemo.json.JsonFunction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditProduct extends Activity {

    EditText edtName;
    EditText edtPrice;
    EditText edtDes;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JsonFunction jsonParser = new JsonFunction();

    // single product url
    private final String url_product_details = "http://api.androidhive.info/android_connect/get_product_details.php";

    // url to update product
    private final String url_update_product = "http://api.androidhive.info/android_connect/update_product.php";

    // url to delete product
    private final String url_delete_product = "http://api.androidhive.info/android_connect/delete_product.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_DESCRIPTION = "description";

    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product);
        edtName = (EditText) findViewById(R.id.edtProductName);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtDes = (EditText) findViewById(R.id.edtDescription);
        pDialog = new ProgressDialog(EditProduct.this);
        pDialog.setMessage("Loading product details. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        Intent intent = getIntent();
        pid = intent.getStringExtra(TAG_PID);

        new GetProductDetails().execute();
    }

    public void onUpdateRecord(View v) {
        new EditProductDetails().execute();
    }

    public void onDeleteRecord(View v) {
        new DeleteProduct().execute();
    }

    class GetProductDetails extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<NameValuePair> values = new ArrayList<NameValuePair>();
            values.add(new BasicNameValuePair(TAG_PID, pid));

            try {
                JSONObject json = jsonParser.makeHttpRequest(url_product_details, JsonFunction.GET,
                        values, JsonFunction.UTF_8, JsonFunction.ISO_8859_1);
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    JSONArray jsonArray = json.getJSONArray(TAG_PRODUCT);
                    final JSONObject object = jsonArray.getJSONObject(0);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // display product data in EditText
                            try {
                                edtName.setText(object.getString(TAG_NAME));
                                edtPrice.setText(object.getString(TAG_PRICE));
                                edtDes.setText(object.getString(TAG_DESCRIPTION));
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                    });

                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditProduct.this, "Don't have any record",
                                    Toast.LENGTH_SHORT).show();
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

    class EditProductDetails extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String pname = edtName.getText().toString();
            String price = edtPrice.getText().toString();
            String pdes = edtDes.getText().toString();

            List<NameValuePair> values = new ArrayList<NameValuePair>();
            values.add(new BasicNameValuePair(TAG_PID, pid));
            values.add(new BasicNameValuePair(TAG_NAME, pname));
            values.add(new BasicNameValuePair(TAG_PRICE, price));
            values.add(new BasicNameValuePair(TAG_DESCRIPTION, pdes));

            try {
                JSONObject jsonObject = jsonParser.makeHttpRequest(url_update_product,
                        JsonFunction.POST, values, null, null);
                int success = jsonObject.getInt(TAG_SUCCESS);

                if (success == 1)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditProduct.this, "Update record success", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i = new Intent(getApplicationContext(), GetAllProducts.class);
                    startActivity(i);
 
                    // closing this screen
                    finish();
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditProduct.this, "Update failed", Toast.LENGTH_SHORT).show();
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

    class DeleteProduct extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<NameValuePair> values = new ArrayList<NameValuePair>();
            values.add(new BasicNameValuePair(TAG_PID, pid));

            JSONObject jsonObject;
            try {
                jsonObject = jsonParser.makeHttpRequest(url_delete_product, JsonFunction.POST,
                        values, null, null);
                int success = jsonObject.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditProduct.this, "Delete record success", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i = new Intent(getApplicationContext(), GetAllProducts.class);
                    startActivity(i);
 
                    // closing this screen
                    finish();
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Toast.makeText(EditProduct.this, "Delete failed", Toast.LENGTH_SHORT).show();
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
