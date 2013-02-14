package br.ufsm.dsweb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.ufsm.dsweb.dao.TweetDAO;
import br.ufsm.dsweb.dao.UserDAO;
import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

@ManagedBean(name="tweetController")
@ViewScoped
public class TweetController implements Serializable {
	private Tweet mTweet;
	
	public TweetController() {
		mTweet = new Tweet();
	}
	
	public void doTweet(User user) {
		mTweet.setUser(user);
		mTweet.setPubdate(new Date());
		new TweetDAO().save(mTweet);
		mTweet = new Tweet();
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Your tweet was successfully save!");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public boolean isTweetFromUser(Tweet tweet, User user) {
		boolean yes = false;
		for(Tweet twt : new UserDAO().tweets(user)) {// new TweetDAO().getAllByCol("mUser.mId", user.getID())) {
			if(twt.getID() == tweet.getID()) {
				yes = true;
				break;
			}
		}
		return yes;
	}
	
	public boolean isRetweetFromUser(Tweet tweet, User user) {
		boolean is_rt = false;
		for(Tweet twt : new UserDAO().retweets(user)) {
			if(twt.getID() == tweet.getID()) {
				is_rt = true;
				break;
			}
		}
		return is_rt;
	}
	
	public boolean isRetweet(Tweet tweet, User user) {
		boolean is_rt = true;
		if(!isTweetFromUser(tweet, user)) {
			for(User followed : new UserDAO().following(user)) {
				if(isTweetFromUser(tweet, followed)) {
					is_rt = false;
					break;
				}
			}
		} else {
			is_rt = false;
		}
		return is_rt;		
	}

	public void toggleRetweet(Tweet tweet, User user) {
		user.setRetweets(new UserDAO().retweets(user));
		if(!isRetweetFromUser(tweet, user)) {
			new UserDAO().retweet(user, tweet);
		} else {
			new UserDAO().cancelRetweet(user, tweet);
		}
	}
	
	public ArrayList<User> getRetweetersOf(Tweet tweet) {
		return new ArrayList<User>(new UserDAO().retweeters(tweet));
	}
	
	public int numRetweets(Tweet tweet) {
		return new UserDAO().retweeters(tweet).size();
	}
	
	public Tweet getTweet() {
		return mTweet;
	}
	public void setTweet(Tweet tweet) {
		mTweet = tweet;
	}
}
