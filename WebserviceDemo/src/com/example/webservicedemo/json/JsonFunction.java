
package com.example.webservicedemo.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonFunction {

    public static final int POST = 1;
    public static final int GET = 2;
    public static final String UTF_8 = "utf-8";
    public static final String ISO_8859_1 = "iso-8859-1";

    DefaultHttpClient defaultHttpClient;
    HttpResponse httpResponse;
    HttpEntity httpEntity;
    static InputStream inputStream = null;
    static JSONObject jsonObject = null;

    public JsonFunction() {
    }

    public JSONObject makeHttpRequest(String url, int type, List<NameValuePair> params,
            String getEncoding,String charsetDecode) throws ClientProtocolException, IOException {
        String json = "";
        if (type == POST) {
            defaultHttpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            httpResponse = defaultHttpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

        } else if (type == GET) {
            defaultHttpClient = new DefaultHttpClient();
            if(getEncoding == null)
            {
                getEncoding = UTF_8;
            }
            String paramString = URLEncodedUtils.format(params, getEncoding);
            url += "?" + paramString;

            HttpGet get = new HttpGet(url);

            httpResponse = defaultHttpClient.execute(get);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        }
        if(charsetDecode == null)
        {
            charsetDecode = ISO_8859_1;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charsetDecode),8);
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line + "\n");
        }
        inputStream.close();
        json = builder.toString();
        
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
