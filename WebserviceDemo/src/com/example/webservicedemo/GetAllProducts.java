
package com.example.webservicedemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.webservicedemo.json.JsonFunction;

public class GetAllProducts extends Activity {
    
    ListView listView;
    private ProgressDialog pDialog;

    private final String url_all_products = "http://api.androidhive.info/android_connect/get_all_products.php";
    JsonFunction jsonFunction = new JsonFunction();

    List<HashMap<String, String>> listProducts;
    JSONArray products = null;

    // JSON Node names
    private final String TAG_SUCCESS = "success";
    private final String TAG_PRODUCTS = "products";
    private final String TAG_PID = "pid";
    private final String TAG_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_all_products);
        listView = (ListView)findViewById(R.id.lstProducts);
        listProducts = new ArrayList<HashMap<String, String>>();
        new LoadAllProducts().execute();       
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
                String pid = ((TextView)view.findViewById(R.id.lbId)).getText().toString();
                Intent intent = new Intent(GetAllProducts.this,EditProduct.class);
                intent.putExtra(TAG_PID, pid);
                startActivityForResult(intent, 100);
            }
            
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100)
        {
            Intent intent = getIntent();
            startActivity(intent);
            finish();
        }
    }



    class LoadAllProducts extends AsyncTask<Void, Void, Void> {
        
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(GetAllProducts.this);
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();

            try {
                JSONObject jsonObject = jsonFunction.makeHttpRequest(url_all_products,
                        JsonFunction.GET, valuePairs, JsonFunction.UTF_8, JsonFunction.ISO_8859_1);

                int success = jsonObject.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    // products found
                    // Getting Array of Products
                    products = jsonObject.getJSONArray(TAG_PRODUCTS);
                    // looping through All Products
                    for (int i = 0; i < products.length(); i++)
                    {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);

                        // adding HashList to ArrayList
                        listProducts.add(map);
                    }
                } else {
                    // no products found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            AddNew.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(GetAllProducts.this, listProducts, R.layout.product_item,
                            new String[] {
                                    TAG_PID,
                                    TAG_NAME
                            },
                            new int[] {
                                    R.id.lbId, R.id.lbName
                            });
                    // updating listview
                    listView.setAdapter(adapter);
                }
            });
 
        }
        
    }
}
