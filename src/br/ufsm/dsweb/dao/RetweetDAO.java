package br.ufsm.dsweb.dao;

import java.util.ArrayList;

import br.ufsm.dsweb.model.Retweet;
import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

public class RetweetDAO extends ModelDAO<Retweet> {
	public RetweetDAO() {
		super(new Retweet(), "retweets.csv");
	}
	
	public ArrayList<Tweet> getRetweetsOf(User user) {
		ArrayList<Tweet> retweets = new ArrayList<Tweet>();
		for(Retweet rt : getAllByCol(0, user.getID()+"")) {
			retweets.add(rt.getTweet());
		}
		return retweets;
	}
	
	public ArrayList<User> getRetweeters(Tweet tweet) {
		ArrayList<User> users = new ArrayList<User>();
		for(Retweet rt : getAllByCol(1, tweet.getID()+"")) {
			users.add(rt.getUser());
		}
		return users;
	}
}
