package br.ufsm.dsweb.dao;

import java.util.ArrayList;
import java.util.List;

import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

public class TweetDAO extends ModelDAO<Tweet> {
	public TweetDAO() {
		super(Tweet.class, "Tweet");
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getRetweeters(Tweet tweet) {
		List<User> result = new ArrayList<User>();
		try {
			result = getEntityManager().createQuery(
					"select t.mRetweeters" +
					" from Tweet t" +
					" where t.mId = :mId")
					.setParameter("mId", tweet.getID())
					.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
		}
	    return result;
	}
}
