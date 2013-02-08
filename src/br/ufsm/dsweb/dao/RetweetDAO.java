package br.ufsm.dsweb.dao;

import java.util.ArrayList;

import br.ufsm.dsweb.model.Retweet;
import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

public class RetweetDAO extends ModelDAO<Retweet> {
	public RetweetDAO() {
		super(Retweet.class, "retweet");
	}
	
	public ArrayList<Tweet> getRetweetsOf(User user) {
		ArrayList<Tweet> retweets = new ArrayList<Tweet>();
		for(Retweet rt : getAllByCol("user_id", user.getID())) {
			retweets.add(rt.getTweet());
		}
		return retweets;
	}
	
	public ArrayList<User> getRetweeters(Tweet tweet) {
		ArrayList<User> users = new ArrayList<User>();
		for(Retweet rt : getAllByCol("tweet_id", tweet.getID())) {
			users.add(rt.getUser());
		}
		return users;
	}
}
