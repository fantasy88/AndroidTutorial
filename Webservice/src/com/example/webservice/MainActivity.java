package com.example.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	private final String NAMESPACE = "http://www.webserviceX.NET/";
    private final String URL = "http://www.webservicex.net/ConvertWeight.asmx";
    private final String SOAP_ACTION = "http://www.webserviceX.NET/ConvertWeight";
    private final String METHOD_NAME = "ConvertWeight";
    
    String resultWeb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new showData().execute();
	}
	
	class showData extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			
			String weight = "78000";
	        String fromUnit = "Grams";
	        String toUnit = "Kilograms";
	        PropertyInfo weightProp =new PropertyInfo();
	        weightProp.setName("Weight");
	        weightProp.setValue(weight);
	        weightProp.setType(double.class);
	        request.addProperty(weightProp);
	          
	        PropertyInfo fromProp =new PropertyInfo();
	        fromProp.setName("FromUnit");
	        fromProp.setValue(fromUnit);
	        fromProp.setType(String.class);
	        request.addProperty(fromProp);
	          
	        PropertyInfo toProp =new PropertyInfo();
	        toProp.setName("ToUnit");
	        toProp.setValue(toUnit);
	        toProp.setType(String.class);
	        request.addProperty(toProp);
	         
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet = true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	  
	        try {
	            androidHttpTransport.call(SOAP_ACTION, envelope);
	            SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	            Log.i("myApp", response.toString());
	            resultWeb = response.toString();
	            
	  
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(MainActivity.this, resultWeb, Toast.LENGTH_SHORT).show();
		}
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
