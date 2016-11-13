package com.stc.mytwitter;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by artem on 11/12/16.
 */

@Table(name = "MyTweet")
public class MyTweet extends Model {
	@Column(name = "TweetId")
	Long tweetId;
	@Column(name = "TweetUrl")
	String tweetUrl;

	@Column(name = "Text")
	public String text;

	@Column(name = "Username")
	public String userName;

	@Column(name = "UserImageUrl")
	public String userImageUrl;

	@Column(name = "ImageUrl")
	public String imageUrl;

	@Override
	public String toString() {
		return "MyTweet{" +
				"imageUrl='" + imageUrl + '\'' +
				", tweetId=" + tweetId +
				", tweetUrl='" + tweetUrl + '\'' +
				", text='" + text + '\'' +
				", userName='" + userName + '\'' +
				", userImageUrl='" + userImageUrl + '\'' +
				'}';
	}

	public MyTweet(Long tweetId, String tweetUrl, String text, String userName, String userImageUrl, String imageUrl) {
		this.tweetId = tweetId;
		this.tweetUrl = tweetUrl;
		this.text = text;
		this.userName = userName;
		this.userImageUrl = userImageUrl;
		this.imageUrl = imageUrl;
	}

	public String getTweetUrl() {
		return tweetUrl;
	}

	public void setTweetUrl(String tweetUrl) {
		this.tweetUrl = tweetUrl;
	}

	public Long getTweetId() {
		return tweetId;
	}

	public void setTweetId(Long _id) {
		this.tweetId = _id;
	}

	public MyTweet() {
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
/*аватар и имя автора
дата публикации
текст твита
прикрепленное изображение, если есть
*/
}
