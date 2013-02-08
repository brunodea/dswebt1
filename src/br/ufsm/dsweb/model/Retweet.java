package br.ufsm.dsweb.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="retweet")
public class Retweet extends Model {

	@ManyToOne
	@JoinColumn(name="user_id")
	private User mUser;
	@ManyToOne
	@JoinColumn(name="tweet_id")
	private Tweet mTweet;
	
	public Tweet getTweet() {
		return mTweet;
	}

	public void setTweet(Tweet tweet) {
		this.mTweet = tweet;
	}

	public User getUser() {
		return mUser;
	}

	public void setUser(User user) {
		this.mUser = user;
	}
}
