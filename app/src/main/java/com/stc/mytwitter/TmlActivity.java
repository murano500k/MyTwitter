package com.stc.mytwitter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import icepick.Icepick;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static com.stc.mytwitter.LoginActivity.MYNAME;
import static com.stc.mytwitter.LoginActivity.MYPREFS;
import static com.stc.mytwitter.LoginActivity.MYUID;
import static com.stc.mytwitter.LoginActivity.TWITTER_KEY;
import static com.stc.mytwitter.LoginActivity.TWITTER_SECRET;

/**
 * TimelineActivity shows a full screen timeline which is useful for screenshots.
 */
public class TmlActivity extends BaseActivity {

	private static final String TAG = "TML";
	final WeakReference<Activity> activityRef = new WeakReference<Activity>(TmlActivity.this);
	long uid;
	String username;
	SwipeRefreshLayout swipeLayout;

	@Bind(R.id.rv)
	RecyclerView recyclerView;
	private MyAdapter adapter;
	private GetTweetsTask task;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweetui_swipe_timeline);
		Icepick.restoreInstanceState(this, savedInstanceState);
			preferences=getSharedPreferences(MYPREFS,MODE_PRIVATE);
			username=preferences.getString(MYNAME, null);
			uid=preferences.getLong(MYUID, 0);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			if(username==null) checkToken();
			else actionBar.setTitle(username);
		}
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
		recyclerView = (RecyclerView) findViewById(R.id.rv);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		swipeLayout.setColorSchemeResources(R.color.twitter_blue, R.color.twitter_dark);
		swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (task != null && !task.isCancelled()) task.cancel(true);
				checkToken();
				task = new GetTweetsTask();
				task.execute();
			}
		});
		showTweets(getCachedTweets());
	}
	private void showTweets(List<MyTweet> tweets) {
		if(tweets!=null && tweets.size()>0){
			Log.w(TAG, "showTweets: "+tweets.size());

			adapter=new MyAdapter(tweets);
			recyclerView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}else {
			Toast.makeText(this, "Swipe to refresh", Toast.LENGTH_SHORT).show();
			Log.w(TAG, "showTweets: ");
			adapter=new MyAdapter(new ArrayList<MyTweet>());
			recyclerView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
		swipeLayout.setRefreshing(false);
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Icepick.saveInstanceState(this, outState);
	}

	public class GetTweetsTask extends AsyncTask<Void, Void, List<MyTweet>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			swipeLayout.setRefreshing(true);
		}

		@Override
		protected List<MyTweet> doInBackground(Void... voids) {
			return getTweets(username);
		}

		@Override
		protected void onPostExecute(List<MyTweet> tweets) {
			super.onPostExecute(tweets);
			showTweets(tweets);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			swipeLayout.setRefreshing(false);
			Toast.makeText(TmlActivity.this, "CANCELLED", Toast.LENGTH_SHORT).show();
		}
	}

	public List<MyTweet> getCachedTweets() {
		swipeLayout.setRefreshing(true);
		List<MyTweet> tweets = new ArrayList<>();
		From from = new Select().from(MyTweet.class);
		Log.d(TAG, "getCachedTweets: "+from.exists());
		if(!from.exists()) return tweets;
		return from.execute();
	}

	public List<MyTweet> getTweets(String searchTerm) {
		new Delete().from(MyTweet.class).execute();
		TwitterAuthToken token = Twitter.getSessionManager().getActiveSession().getAuthToken();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey(TWITTER_KEY)
				.setOAuthConsumerSecret(TWITTER_SECRET)
				.setOAuthAccessToken(
						token.token)
				.setOAuthAccessTokenSecret(
						token.secret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter4j.Twitter twitter = tf.getInstance();
		List<MyTweet> tweets=new ArrayList<>();
		try {
			List<Status> statuses;
			Paging paging = new Paging();
			paging.setCount(100);
			paging.setPage(1);
			statuses = twitter.getUserTimeline(searchTerm, paging);
			Log.w("Status Count", statuses.size() + " Feeds");
			for (int i = 0; i < statuses.size(); i++) {
				Status status = statuses.get(i);
				MyTweet tweet = new MyTweet(
						status.getId(),
						status.getSource(),
						status.getText(),
						status.getUser().getName(),
						status.getUser().getProfileImageURL(),
						extractImageUrl(status.getMediaEntities()));
				tweet.save();
				tweets.add(tweet);
				//Log.w("Tweet " + (i + 1), tweet.toString() + "\n\n");
			}
		} catch (twitter4j.TwitterException te) {
			te.printStackTrace();
		}
		return tweets;
	}




	public String extractImageUrl(twitter4j.MediaEntity[] t){
		String res =null;
		if(t!=null){
			for(twitter4j.MediaEntity entity: t){
				Log.w(TAG, "extractImageType: "+entity.getType());
				Log.w(TAG, "extractImageUrl: "+entity.getMediaURL());
				if(entity.getType().contains("photo"))
					res = entity.getMediaURL();
				break;
			}
		}
		//Log.w("extractImgUrl", res+"");
		return res;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("LOGOUT");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Twitter.getSessionManager().clearActiveSession();
		preferences.edit().putLong(MYUID, -1L).putString(MYNAME,null).apply();
		new Delete().from(MyTweet.class).execute();
		startActivity(new Intent(this, LoginActivity.class));
		finish();
		return super.onOptionsItemSelected(item);
	}



	private void checkToken() {
		TwitterSession session=Twitter.getInstance().core.getSessionManager().getActiveSession();
		if(session!=null && session.getAuthToken().isExpired()){
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
	}
}