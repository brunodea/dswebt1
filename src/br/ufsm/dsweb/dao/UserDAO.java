package br.ufsm.dsweb.dao;

import java.util.ArrayList;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import br.ufsm.dsweb.db.DBCore;
import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

@Named("userDAO")
@RequestScoped
public class UserDAO extends ModelDAO<User> {
	public UserDAO() {
		super(new User(), "users.csv");
	}

	public boolean usernameExists(String username) {
		String r = DBCore.getRowByCol(getFilename(), 2, username); 
		return r != null && !r.equals("");
	}
	
	public User login(String username, String password) {
		User res = null;
		String csv = DBCore.getRowByCol(getFilename(), 2, username);
		if(csv != null && !csv.equals("")) {
			User aux = new User();
			aux.fromCSV(csv);
			if(aux.getPassword().equals(password)) {
				res = aux;
			}
		}
		return res;
	}
	
	public ArrayList<Tweet> getAllTweets(User user) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		for(String csv : DBCore.getAllRowsByCol(getFilename(), 1, user.getID()+"")) {
			Tweet tweet = new Tweet();
			tweet.fromCSV(csv);
			
			tweets.add(tweet);
		}

		return tweets;
	}
}
