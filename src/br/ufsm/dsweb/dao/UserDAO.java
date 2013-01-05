package br.ufsm.dsweb.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import br.ufsm.dsweb.db.DBCore;
import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

public class UserDAO extends ModelDAO<User> {
	public UserDAO() {
		super(new User(), "users.csv");
	}

	public boolean usernameExists(String username) {
		String r = DBCore.getRowByCol(getFilename(), 2, username); 
		return r != null && !r.equals("");
	}
	
	public User getByUsername(String username) {
		User res = null;
		String csv = DBCore.getRowByCol(getFilename(), 2, username);
		if(csv != null && !csv.equals("")) {
			res = new User();
			res.fromCSV(csv);
		}
		
		return res;
	}
	
	public User login(String username, String password) {
		User res = null;
		User aux = getByUsername(username);
		if(aux != null && aux.getPassword().equals(password)) {
			res = aux;
		}
		return res;
	}
	
	public ArrayList<Tweet> getAllTweets(User user) {
		ArrayList<Tweet> tweets = new TweetDAO().getAllByCol(1, user.getID()+"");		
		Collections.sort(tweets, new Comparator<Tweet>() {
			@Override
			public int compare(Tweet lhs, Tweet rhs) {
				return rhs.getPubdate().compareTo(lhs.getPubdate());
			}
		});

		return tweets;
	}
}
