package br.ufsm.dsweb.dao;

import br.ufsm.dsweb.model.Tweet;

public class TweetDAO extends ModelDAO<Tweet> {
	public TweetDAO() {
		super(new Tweet(), "tweets.csv");
	}
	public TweetDAO(Tweet tweet) {
		super(tweet, "tweets.csv");
	}
}
