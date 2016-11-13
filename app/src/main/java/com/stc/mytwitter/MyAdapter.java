package com.stc.mytwitter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
		loadImage(holder.avatar, tweet.getUserImageUrl());
		loadImage(holder.image1, tweet.getImageUrl1());
		loadImage(holder.image2, tweet.getImageUrl2());
		loadImage(holder.image3, tweet.getImageUrl3());
		loadImage(holder.image4, tweet.getImageUrl4());

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
	public void loadImage(ImageView imageView, String url){
		PabloPicasso.with(imageView.getContext()).cancelTag(imageView);
		if (url != null) PabloPicasso.with(imageView.getContext())
				.load(url)
				.tag(imageView)
				.into(imageView);
	}


	@Override
	public int getItemCount() {
		return items.size();
	}

}

