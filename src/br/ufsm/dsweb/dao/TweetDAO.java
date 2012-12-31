package br.ufsm.dsweb.dao;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import br.ufsm.dsweb.model.Tweet;

@Named("tweetDAO")
@RequestScoped
public class TweetDAO extends ModelDAO<Tweet> {
	public TweetDAO() {
		super(new Tweet(), "tweets.csv");
	}
}
