package br.ufsm.dsweb.db;

import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

public class DBActions {
	private static String USER_FILENAME = "users.csv";
	private static String TWEET_FILENAME = "tweets.csv";
	
	public static void saveUser(User user) {
		DBCore.appendToFile(USER_FILENAME, user.toCSV());
	}
	public static User getUserByID(int user_id) {
		User user = new User();
		String csv = DBCore.getRowByCol(USER_FILENAME, 0, String.valueOf(user_id));
		if(!csv.equals("")) {
			user.fromCSV(csv);
		}		
		return user;
	}

	public static void saveTweet(Tweet tweet) {
		DBCore.appendToFile(TWEET_FILENAME, tweet.toCSV());
	}
	public static Tweet getTweetByID(int tweet_id) {
		Tweet tweet = new Tweet();
		String csv = DBCore.getRowByCol(TWEET_FILENAME, 0, String.valueOf(tweet_id));
		if(!csv.equals("")) {
			tweet.fromCSV(csv);
		}		
		return tweet;
	}
}
