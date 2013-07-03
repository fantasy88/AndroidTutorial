package com.example.twitterapp;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	static String TWITTER_CONSUMER_KEY = "ZA1AXpODvP69JewPB5Ag";
    static String TWITTER_CONSUMER_SECRET = "Y8RrphmBOZMc7A0mof6T90Dm0IVPd1FUutyJi2QXg";
 
    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";
 
    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
 
    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
    
 // Twitter
    private static Twitter twitter;
    private static RequestToken requestToken;
     
    // Shared Preferences
    private static SharedPreferences mSharedPreferences;
    
    AccessToken accessToken;
    String verifier;
    Button btnLogoutTwitter;
    TextView lbUser;
    String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSharedPreferences = getApplicationContext().getSharedPreferences(
                "MyPref", 0);
		btnLogoutTwitter = (Button)findViewById(R.id.button1);
		lbUser = (TextView)findViewById(R.id.textView1);
		
		new showData().execute();
 
    }
	
	class showData extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			if (!isTwitterLoggedInAlready()) {
	            Uri uri = getIntent().getData();
	            if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
	                // oAuth verifier
	                verifier = uri
	                        .getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);
	 
	                try {
	                    // Get the access token
	                    accessToken = twitter.getOAuthAccessToken(
	                            requestToken, verifier);
	 
	                    // Shared Preferences
	                    Editor e = mSharedPreferences.edit();
	 
	                    // After getting access token, access token secret
	                    // store them in application preferences
	                    e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
	                    e.putString(PREF_KEY_OAUTH_SECRET,
	                            accessToken.getTokenSecret());
	                    // Store login status - true
	                    e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
	                    e.commit(); // save changes
	 
	                    Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
	                    
	                    // Getting user details from twitter
	                    // For now i am getting his name only
	                    long userID = accessToken.getUserId();
	                    User user = null;
	        			try {
	        				user = twitter.showUser(userID);
	        			} catch (TwitterException ex) {
	        				// TODO Auto-generated catch block
	        				ex.printStackTrace();
	        			}
	                    username = user.getName();
	                    
	                } catch (Exception e) {
	                    // Check log for login errors
	                    Log.e("Twitter Login Error", "> " + e.getMessage());
	                }
	            }
	        }
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(accessToken != null)
			{
				btnLogoutTwitter.setVisibility(View.GONE);
	            lbUser.setText(Html.fromHtml("<b>Welcome " + username + "</b>"));
			}
		}
		
	}
	
	
	private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }

	public void SignIn(View v)
	{
		if (!isTwitterLoggedInAlready()) {
            new loginData().execute();
        } else {
            // user already logged into twitter
            Toast.makeText(getApplicationContext(),
                    "Already Logged into twitter", Toast.LENGTH_LONG).show();
        }
	}
	
	class loginData extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();
             
            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();
 
            try {
                requestToken = twitter
                        .getOAuthRequestToken(TWITTER_CALLBACK_URL);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse(requestToken.getAuthenticationURL())));
            } catch (TwitterException e) {
                e.printStackTrace();
            }
			return null;
		}
		
	}

}
