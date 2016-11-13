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

	@Column(name = "ImageUrl1")
	public String imageUrl1;

	@Column(name = "ImageUrl2")
	public String imageUrl2;
	@Column(name = "ImageUrl3")
	public String imageUrl3;
	@Column(name = "ImageUrl4")
	public String imageUrl4;

	@Override
	public String toString() {
		return "MyTweet{" +
				"tweetId=" + tweetId +
				", tweetUrl='" + tweetUrl + '\'' +
				", text='" + text + '\'' +
				", userName='" + userName + '\'' +
				", userImageUrl='" + userImageUrl + '\'' +
				", imageUrl1='" + imageUrl1 + '\'' +
				", imageUrl2='" + imageUrl2 + '\'' +
				", imageUrl3='" + imageUrl3 + '\'' +
				", imageUrl4='" + imageUrl4 + '\'' +
				'}';
	}

	public MyTweet(Long tweetId, String tweetUrl, String text, String userName, String userImageUrl) {
		this.tweetId = tweetId;
		this.tweetUrl = tweetUrl;
		this.text = text;
		this.userName = userName;
		this.userImageUrl = userImageUrl;
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

	public String getImageUrl1() {
		return imageUrl1;
	}

	public String getImageUrl2() {
		return imageUrl2;
	}

	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}

	public String getImageUrl3() {
		return imageUrl3;
	}

	public void setImageUrl3(String imageUrl3) {
		this.imageUrl3 = imageUrl3;
	}

	public String getImageUrl4() {
		return imageUrl4;
	}

	public void setImageUrl4(String imageUrl4) {
		this.imageUrl4 = imageUrl4;
	}

	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
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
