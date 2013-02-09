package br.ufsm.dsweb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.ufsm.dsweb.dao.TweetDAO;
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
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Your tweet was successfully save!");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public boolean isTweetFromUser(Tweet tweet, User user) {
		boolean yes = false;
		for(Tweet twt : new TweetDAO().getAllByCol("user_id", user.getID()+"")) {
			if(twt.getID() == tweet.getID()) {
				yes = true;
				break;
			}
		}
		return yes;
	}
	
	public boolean isRetweetFromUser(Tweet tweet, User user) {
		boolean is_rt = false;
		/*for(Tweet twt : new RetweetDAO().getRetweetsOf(user)) {
			if(twt.getID() == tweet.getID()) {
				is_rt = true;
				break;
			}
		}*/
		return is_rt;
	}
	
	public boolean isRetweet(Tweet tweet, User user) {
		boolean is_rt = true;
		/*if(!isTweetFromUser(tweet, user)) {
			for(User followed : new FollowsDAO().getFollowing(user)) {
				if(isTweetFromUser(tweet, followed)) {
					is_rt = false;
					break;
				}
			}
		} else {
			is_rt = false;
		}*/
		return is_rt;		
	}

	public void toggleRetweet(Tweet tweet, User user) {
		/*Retweet rt = new Retweet();
		rt.setUser(user);
		rt.setTweet(tweet);
		if(!isRetweetFromUser(tweet, user)) {
			new RetweetDAO().save(rt);
		} else {
			HashMap<String, String> col_vals = new HashMap<String, String>();
			col_vals.put("user_id", user.getID()+"");
			col_vals.put("tweet_id", tweet.getID()+"");
			new RetweetDAO().removeByCols(col_vals);
		}*/
	}
	
	public ArrayList<User> getRetweetersOf(Tweet tweet) {
		return new ArrayList<User>();
		//return new RetweetDAO().getRetweeters(tweet);
	}
	
	public int numRetweets(Tweet tweet) {
		return 0;
		//return new RetweetDAO().getRetweeters(tweet).size();
	}
	
	public Tweet getTweet() {
		return mTweet;
	}
	public void setTweet(Tweet tweet) {
		mTweet = tweet;
	}
}
