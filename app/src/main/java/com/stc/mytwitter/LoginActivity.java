package com.stc.mytwitter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    static final String TWITTER_KEY = "l1bsbNwFWSWVHj3lqvYVWfkAp";
	static final String TWITTER_SECRET = "9xJIzT78mH9U8E3SW8PKRUd8JZ2JVoEY7wpMSdm4tvVQ8hcsHN";
	static final String MYPREFS = "com.stc.mytwitter.MYPREFS";
	static final String MYUID = "com.stc.mytwitter.MUID";
	static final String MYNAME = "com.stc.mytwitter.MYNAME";
	static final String MYTOKEN = "com.stc.mytwitter.MYTOKEN";
	private TwitterLoginButton loginButton;

	private View mProgressView;
	private View mLoginFormView;
	TwitterAuthToken token;
	SharedPreferences preferences;
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		Fabric.with(this, new Twitter(authConfig));
		setContentView(R.layout.activity_login);
		preferences= getSharedPreferences(MYPREFS,MODE_PRIVATE);
		mProgressView = findViewById(R.id.login_progress);
		loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
		loginButton.setCallback(new Callback<TwitterSession>() {
			@Override
			public void success(Result<TwitterSession> result) {
				// The TwitterSession is also available through:
				// Twitter.getInstance().core.getSessionManager().getActiveSession()
				TwitterSession session = result.data;
				token = result.data.getAuthToken();
				preferences.edit().putLong(MYUID, session.getUserId()).apply();
				preferences.edit().putString(MYNAME, session.getUserName()).apply();
				showTimeline();
			}
			@Override
			public void failure(TwitterException exception) {
				Log.d("TwitterKit", "Login with Twitter failure", exception);
			}
		});
		TwitterSession session = Twitter.getSessionManager().getActiveSession();
		if(session!=null && session.getAuthToken()!=null && !session.getAuthToken().isExpired()){
			showTimeline();
		}
	}


	private void showTimeline() {
		Intent intent = new Intent(LoginActivity.this,TmlActivity.class);
		startActivity(intent);
		finish();
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Make sure that the loginButton hears the result from any
		// Activity that it triggered.
		loginButton.onActivityResult(requestCode, resultCode, data);
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime).alpha(
					show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
				}
			});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime).alpha(
					show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
				}
			});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}


}

