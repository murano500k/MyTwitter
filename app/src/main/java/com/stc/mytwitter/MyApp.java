package com.stc.mytwitter;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by artem on 11/12/16.
 */

public class MyApp extends Application {
	private static final String TWITTER_KEY = "l1bsbNwFWSWVHj3lqvYVWfkAp";
	private static final String TWITTER_SECRET = "9xJIzT78mH9U8E3SW8PKRUd8JZ2JVoEY7wpMSdm4tvVQ8hcsHN";

	@Override
	public void onCreate() {
		super.onCreate();
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		Fabric.with(this, new Twitter(authConfig));

		ActiveAndroid.initialize(this);
	}
}
