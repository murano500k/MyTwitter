package com.stc.mytwitter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by artem on 11/12/16.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
	List<MyTweet>items;

	public MyAdapter(List<MyTweet> items) {
		this.items = items;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.tweet_list_item, parent, false);

		return new MyViewHolder(v);
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {

		Log.d("TAG", "Element " + position + " set.");
		final MyTweet tweet = items.get(position);
		holder.text.setText(tweet.text);
		holder.username.setText(tweet.userName);
		if (tweet.getUserImageUrl() != null) PabloPicasso.with(holder.avatar.getContext())
				.load(tweet.getUserImageUrl())
				.into(holder.avatar);
		if (tweet.getImageUrl() != null) PabloPicasso.with(holder.image.getContext())
				.load(tweet.getImageUrl())
				.tag(holder.image)
				.into(holder.image);
		holder.root.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = tweet.getTweetUrl();
				Log.w("SHARE", url + "");
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_TEXT, url);
				v.getContext().startActivity(Intent.createChooser(shareIntent, "Share link using"));
			}
		});
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

}
