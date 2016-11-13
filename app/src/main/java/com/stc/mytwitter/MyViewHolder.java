package com.stc.mytwitter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by artem on 11/12/16.
 */
public class MyViewHolder extends RecyclerView.ViewHolder implements Target{
	View root;
	ImageView image1;
	ImageView image2;
	ImageView image3;
	ImageView image4;
	ImageView avatar;
	TextView text;
	TextView username;
	public MyViewHolder(View itemView) {
		super(itemView);
		root = itemView;
		text=(TextView)itemView.findViewById(R.id.text);
		username=(TextView)itemView.findViewById(R.id.textUsername);
		image1=(ImageView)itemView.findViewById(R.id.image1);
		image2=(ImageView)itemView.findViewById(R.id.image2);
		image3=(ImageView)itemView.findViewById(R.id.image3);
		image4=(ImageView)itemView.findViewById(R.id.image4);
		avatar=(ImageView)itemView.findViewById(R.id.avatar);
	}


	@Override
	public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
		avatar.setImageBitmap(bitmap);
	}

	@Override
	public void onBitmapFailed(Drawable errorDrawable) {

	}

	@Override
	public void onPrepareLoad(Drawable placeHolderDrawable) {

	}
}
