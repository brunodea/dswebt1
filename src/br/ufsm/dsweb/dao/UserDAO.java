package br.ufsm.dsweb.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.ufsm.dsweb.db.DBCore;
import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

public class UserDAO extends ModelDAO<User> {
	public UserDAO() {
		super(User.class, "user");
	}

	public boolean usernameExists(String username) {
		return getByUsername(username) != null;
	}
	
	public User getByUsername(String username) {
		return getFirstByCol("username", username);
	}
	
	public User login(String username, String password) {
		User res = null;
		User aux = getByUsername(username);
		if(aux != null && aux.getPassword().equals(password)) {
			res = aux;
		}
		return res;
	}
	
	public List<Tweet> getAllTweets(User user) {
		List<Tweet> tweets = new TweetDAO().getAllByCol("user_id", user.getID());		
		Collections.sort(tweets, new Comparator<Tweet>() {
			@Override
			public int compare(Tweet lhs, Tweet rhs) {
				return rhs.getPubdate().compareTo(lhs.getPubdate());
			}
		});

		return tweets;
	}
}
