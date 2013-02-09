package br.ufsm.dsweb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TwitcherUser")
public class User extends Model implements Serializable {
	@Column(name="full_name", nullable=false)
	private String mFullName;
	@Column(name="username", nullable=false)
	private String mUsername;
	@Column(name="password", nullable=false)
	private String mPassword;
	@Column(name="image_path", nullable=true)
	private String mImagePath;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true,mappedBy="mUser")
	private List<Tweet> mTweets;
	
	@ManyToMany
	@JoinTable(name="user_retweet", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="tweet_id"))
	private List<Tweet> mRetweets;
	
	@ManyToMany
	@JoinTable(name="user_follows", joinColumns=@JoinColumn(name="follower_id"), inverseJoinColumns=@JoinColumn(name="followed_id"))
	private List<User> mFollowing;
	
	@ManyToMany(mappedBy="mFollowing")
	private List<User> mFollowers;
	
	public User() {
		mImagePath = "../imgs/default.jpg";
	}
	
	public String getFullname() {
		return mFullName;
	}

	public void setFullname(String fullname) {
		this.mFullName = fullname;
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String username) {
		this.mUsername = username;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		this.mPassword = password;
	}
	
	public String getImagePath() {
		return mImagePath;
	}
	
	public void setImagePath(String imagepath) {
		mImagePath = imagepath;
	}
	
	public List<Tweet> getTweets() {
		return mTweets;
	}
	public void setTweets(List<Tweet> tweets) {
		mTweets = tweets;
	}
	
	public List<Tweet> getRetweets() {
		return mRetweets;
	}
	public void setRetweets(List<Tweet> retweets) {
		mRetweets = retweets;
	}
	
	public List<User> getFollowing() {
		return mFollowing;
	}
	public void setFollowing(List<User> following) {
		mFollowing = following;
	}
	
	public List<User> getFollowers() {
		return mFollowers;
	}
	public void setFollowers(List<User> followers) {
		mFollowers = followers;
	}
}
